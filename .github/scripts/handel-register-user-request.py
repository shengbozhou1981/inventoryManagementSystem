import os
import re
from github import Github
from git import Repo
import yaml
class Dumper(yaml.Dumper):
    def increase_indent(self, flow=False, *args, **kwargs):
        return super().increase_indent(flow=flow, indentless=False)
    
def extract_field(match, default_value):
    return match.group(1).strip() if match else default_value
def update_state_in_yaml(content, state_change):
    yaml_content = yaml.safe_load(content)
    
    for item in yaml_content:
        if "bigip_pool" in item:
            if "state" in item["bigip_pool"]:
                item["bigip_pool"]["state"] = state_change
            else:
                item["bigip_pool"]["state"] = "absent"
    
    updated_content = yaml.dump(yaml_content, default_flow_style=False)
    return updated_content
def extract_info_from_issue(issue_body):

# retrieve info from submit Issue
    data_center = extract_field(re.search(r'### Datacenter\s*([^\n]*)', issue_body), "")
    device_pair = extract_field(re.search(r'### Device Pair\s*([^\n]*)', issue_body), "")
    pool_partition = extract_field(re.search(r'### Pool Partition\s*([^\n]*)', issue_body, re.DOTALL), "Common")
    pool_name = extract_field(re.search(r'### Pool Name\s*([^\n]*)', issue_body), "")
    pool_state = extract_field(re.search(r'### Pool state\s*([^\n]*)', issue_body), "present")
    lb_method = extract_field(re.search(r'### Load Balancing Method\s*([^\n]*)', issue_body), "Round Robin")
    monitors = extract_field(re.search(r'### Health monitors([\s\S]*?)(?=###|$)', issue_body), "")
    pool_member_state = extract_field(re.search(r'### Pool member state\s*([^\n]*)', issue_body), "present")
    pool_members_match = re.findall(r'### Pool Member Information in the pool([\s\S]*?)(?=###|$)', issue_body)
    pool_members = pool_members_match[0].strip() if pool_members_match else []
    virtual_server = extract_field(re.search(r'### Virtual Server Name\s*([^\n]*)', issue_body), "")
    vs_state = extract_field(re.search(r'### Virtual server state\s*([^\n]*)', issue_body), "enabled")
    vs_destination = extract_field(re.search(r'### Virtual Server destination\s*([^\n]*)', issue_body), "")
    vs_port = extract_field(re.search(r'### Virtual Server port\s*([^\n]*)', issue_body), "")
    vs_default_pool = extract_field(re.search(r'### Default Pool name\s*([^\n]*)', issue_body), "")
    vs_snat = extract_field(re.search(r'### Source network address policy\s*([^\n]*)', issue_body), "")
    vs_profile_matches = re.findall(r'### profiles on virtual serve([\s\S]*?)(?=###|$)', issue_body)
    vs_profile = [match.strip() for match in vs_profile_matches]

    vs_irule_matches = re.findall(r'### irules on virtual server([\s\S]*?)(?=###|$)', issue_body)
    vs_irule = [match.strip() for match in vs_irule_matches]
    vs_default_persistence_profile = extract_field(re.search(r'### persistence profile\s*([^\n]*)', issue_body), "")
    vs_fallback_persistence_profile = extract_field(re.search(r'### fallback_persistence_profile\s*([^\n]*)', issue_body), "")    
                
    return data_center,device_pair,pool_name,pool_partition,pool_state,lb_method, monitors, pool_members,pool_member_state,virtual_server,vs_state,vs_destination,vs_port,vs_default_pool,vs_snat,vs_profile,vs_irule,vs_default_persistence_profile,vs_fallback_persistence_profile

def convert_pool_members_string_to_list(pool_members_string_list,pool_member_state):
    pool_members_list = []
    pool_members_string = "".join(pool_members_string_list)
    lines = pool_members_string.strip().split('\n')
    current_host = None
    current_port = None
    current_state = None
    for line in lines:
        if line.strip().startswith("- host:"):
            current_host = line.strip().split()[2]
        elif line.strip().startswith("port:"):
            current_port = line.strip().split()[1]
        elif line.strip().startswith("state:"):
            current_state = line.strip().split()[1]
        if current_host is not None and current_port is not None:
            if current_state is None:
                current_state = pool_member_state
            pool_members_list.append((current_host, current_port, current_state))
            current_host = None
            current_port = None
            current_state = None
    return pool_members_list

def create_pool_playbook(data_center,device_pair,pool_name,pool_partition,pool_state,lb_method, monitors, pool_members,pool_member_state):
    playbook_file_path = f"devices/{device_pair}/{pool_partition}/pools/{pool_name}.yml"
    
    pool_members_list = convert_pool_members_string_to_list(pool_members,pool_member_state)
    monitors_section = "    monitors:\n" + "\n".join(["    - " + monitor.strip() for monitor in monitors.split("\n") if monitor.strip()]) + "\n"

    playbook_pool = (
        "- name: create pool " + pool_name + "\n"
        "  bigip_pool:\n"
        "    provider: \"{{ provider }}\"\n"
        "    name: " + pool_name + "\n"
        "    lb_method: " + lb_method + "\n"
        + monitors_section + 
        "  delegate_to: localhost\n"
    )

    playbooks_pool_member = [
        (
            "- name: create pool member " + pool_name + " - " + host + ":" + port + "\n"
            "  bigip_pool_member:\n"
            "    provider: \"{{ provider }}\"\n"
            "    pool: " + pool_name + "\n"
            "    host: \"" + host + "\"\n"
            "    port: \"" + port + "\"\n"
            "    state: \"" + state + "\"\n"
            "  delegate_to: localhost\n"
        )
        for host, port, state in pool_members_list
    ]

    full_playbook = playbook_pool + "\n".join(playbooks_pool_member)
    
    if os.path.exists(playbook_file_path):
        # if file exist, read file
        with open(playbook_file_path, 'r') as f:
            existing_content = f.read()
        
        # Check if existing_content exists in full_playbook
        if existing_content in full_playbook:
            # Remove existing_content from full_playbook
            full_playbook = full_playbook.replace(existing_content, "")
    
    # write to file
    os.makedirs(os.path.dirname(playbook_file_path), exist_ok=True)
    with open(playbook_file_path, 'w') as f:
        f.write(full_playbook)
    
    return full_playbook
def handle_edit_pool(data_center,device_pair,pool_name,pool_partition,pool_state,lb_method, monitors,pool_members,pool_member_state):
    playbook_file_path = f"devices/{device_pair}/{pool_partition}/pools/{pool_name}.yaml"
    content = {}  # Initialize content as a dictionary
    
    if os.path.exists(playbook_file_path):
        # If file exists, read YAML content as a list of dictionaries
        with open(playbook_file_path, 'r') as f:
            content = yaml.safe_load(f)
        # Iterate through each playbook in the content
            for playbook in content:
                # Check if this playbook is related to the pool
                if "bigip_pool" in playbook and "name" in playbook["bigip_pool"] and playbook["bigip_pool"]["name"] == pool_name:
                    # Update pool state if provided in the issue request
                    if pool_state:
                        playbook["bigip_pool"]["state"] = pool_state.split(" ", 1)[0]
                    
                    # Update load balancing method if provided in the issue request
                    if lb_method:
                        playbook["bigip_pool"]["lb_method"] = lb_method
                    
                    # Update monitors if provided in the issue request
                    if monitors:
                        # Convert monitors list to a string with YAML list format
                        monitors_list = monitors.splitlines()
                        formatted_monitors = [monitor.strip() for monitor in monitors_list if monitor.strip()]
                        playbook["bigip_pool"]["monitors"] = formatted_monitors
                        
    return yaml.dump(content, sort_keys=False,Dumper=Dumper).replace("'", "\"")
def handle_delete_pool(data_center, device_pair, pool_name, pool_partition, pool_state):
    playbook_file_path = f"devices/{device_pair}/{pool_partition}/pools/{pool_name}.yaml"
    state_change = "state: absent"
    content = {}  # Initialize content as a dictionary

    if os.path.exists(playbook_file_path):
        # If file exists, read YAML content as a dictionary
        with open(playbook_file_path, 'r') as f:
            content = yaml.safe_load(f)
        # Update the dictionary with the new state or add the state field if missing
        if 'bigip_pool' in content[0]:
            if 'state' in content[0]['bigip_pool']:
                content[0]['bigip_pool']['state'] = 'absent'
            else:
                content[0]['bigip_pool']['state'] = 'absent'

        # Write the modified content back to the file
        with open(playbook_file_path, 'w') as f:
            yaml.dump(content, f, sort_keys=False)

    return yaml.dump(content, sort_keys=False).replace("'", "\"")
def handle_add_member_to_pool(data_center,device_pair,pool_name,pool_partition,pool_members,pool_member_state):
    playbook_file_path = f"devices/{device_pair}/{pool_partition}/pools/{pool_name}.yaml"
    content = {}  # Initialize content as a dictionary
    
    pool_members_list = convert_pool_members_string_to_list(pool_members,pool_member_state)
    playbooks_pool_member = [
        {
            "name": f"create pool member {pool_name} - {host}:{port}",
            "bigip_pool_member": {
                "provider": "{{ provider }}",
                "pool": pool_name,
                "host": host,
                "port": port,
                "state": state
            },
            "delegate_to": "localhost"
        }
        for host, port, state in pool_members_list
    ]
    
    if os.path.exists(playbook_file_path):
        # If file exists, read YAML content as a dictionary
        with open(playbook_file_path, 'r') as f:
            content = yaml.safe_load(f)
        # Append new playbooks for creating new pool members
        content.extend(playbooks_pool_member)

    return yaml.dump(content, sort_keys=False).replace("'", "\"")
def handle_update_member_from_pool(data_center,device_pair,pool_name,pool_partition,pool_state,pool_members,pool_member_state):
    playbook_file_path = f"devices/{device_pair}/{pool_partition}/pools/{pool_name}.yaml"
    content = {}  # Initialize content as a dictionary
    
    pool_members_list = convert_pool_members_string_to_list(pool_members,pool_member_state)
    #here i need to open the file and look up corresponding pool member definition according to the pool members and then update its state field accoring to pool state selection
    if os.path.exists(playbook_file_path):
        # If file exists, read YAML content as a list of dictionaries
        with open(playbook_file_path, 'r') as f:
            content = yaml.safe_load(f)
        
        # Update pool member definitions in content based on pool_members_list
        for host, port, state in pool_members_list:
            for playbook in content:
                if "name" in playbook and "bigip_pool_member" in playbook:
                    playbook_name = playbook["name"]
                    playbook_member = playbook["bigip_pool_member"]
                    if (f"create pool member {host}:{port}" in playbook_name and playbook_member["host"] == host and str(playbook_member["port"]) == port):
                        playbook_member["state"] = state
        

    return yaml.dump(content, sort_keys=False).replace("'", "\"")

def main():
    # issue_title = os.environ.get("ISSUE_TITLE")
    # issue_body = os.environ.get("ISSUE_BODY")
    # read env from GitHub Action
    github_token = os.getenv("gittoken")
    issue_title = os.getenv("ISSUE_TITLE")
    issue_body = os.getenv("ISSUE_BODY")
    repo_name = os.getenv("REPO_NAME")
    base_branch = main
#     issue_title = "Edit pool"
#     issue_body = """
#     ### Contact Details
  
#   tony.zhou@telus.com
  
#   ### Datacenter
  
#   QIDC (Default)
  
#   ### Device Pair
  
#   fn94001-fn94002
  
#   ### Pool Name
  
#   AAA_test_pool
  
#   ### Pool state
  
#   present
  
#   ### Pool Partition
  
#   Common
  
#   ### Load Balancing Method
  
#   Ratio member
  
#   ### Health monitors
  
#   tcp
#   http
#   https
  
#   ### Pool Member Information in the pool
  
#   ex.
#   - host: 142.63.34.100
#     port: 31186
#     state: enabled
#     """
    data_center,device_pair,pool_name,pool_partition,pool_state,lb_method, monitors, pool_members,pool_member_state, virtual_server, vs_state, vs_destination, vs_port, vs_default_pool, vs_snat, vs_profile, vs_irule, vs_default_persistence_profile, vs_fallback_persistence_profile = extract_info_from_issue(issue_body)
    playbook_file_path = f"devices/{device_pair}/{pool_partition}/pools/{pool_name}.yaml"  
    if "Create pool" in issue_title:
        # print(data_center,device_pair,pool_name,pool_partition,pool_state,lb_method, monitors,pool_members)
        full_playbook = create_pool_playbook(data_center,device_pair,pool_name,pool_partition,pool_state,lb_method, monitors, pool_members,pool_member_state)
        # print(f"::set-output name=full_playbook::{full_playbook}")
        
        # Write playbook section to the playbook file
        playbook_file_path = f"devices/{device_pair}/{pool_partition}/pools/{pool_name}.yml"
        new_feature_change = f"feature-new-pool-{pool_name}"
        print(playbook_file_path)
        # print(new_feature_change)
        with open(playbook_file_path, "w") as file:
            file.write(full_playbook)        
    elif "Edit pool" in issue_title:
        full_playbook = handle_edit_pool(data_center,device_pair,pool_name,pool_partition,pool_state,lb_method, monitors,pool_members,pool_member_state)     
        print(playbook_file_path)
        # print(new_feature_change)
        with open(playbook_file_path, "w") as file:
            file.write(full_playbook)    
    elif "Delete pool" in issue_title:
        full_playbook = handle_delete_pool(data_center,device_pair,pool_name,pool_partition,pool_state)     
        print(playbook_file_path)
        # print(new_feature_change)
        with open(playbook_file_path, "w") as file:
            file.write(full_playbook)    

    elif "Add pool member" in issue_title:
        full_playbook = handle_add_member_to_pool(data_center,device_pair,pool_name,pool_partition,pool_members,pool_member_state)     
        print(playbook_file_path)
        # print(new_feature_change)
        with open(playbook_file_path, "w") as file:
            file.write(full_playbook)    
    elif "Update pool member" in issue_title:
        full_playbook = handle_update_member_from_pool(data_center,device_pair,pool_name,pool_partition,pool_state,pool_members,pool_member_state)     
        print(playbook_file_path)
        # print(new_feature_change)
        with open(playbook_file_path, "w") as file:
            file.write(full_playbook)    
    
if __name__ == "__main__":
    main()
