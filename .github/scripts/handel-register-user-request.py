import os
import re
import yaml
from github import Github

# 获取 GitHub 访问令牌
github_token = os.environ['GITHUB_TOKEN']
github_repo = os.environ['GITHUB_REPOSITORY']

# 获取 Issue 内容
issue_number = int(os.environ['ISSUE_NUMBER'])
g = Github(github_token)
repo = g.get_repo(github_repo)
issue = repo.get_issue(issue_number)
issue_body = issue.body

# 提取用户信息
user_info = re.findall(r'- username: (.*?)\n\s+email: (.*?)\n', issue_body, re.DOTALL)

# 读取 authorizedUser.yaml 文件
file_path = '/authorizedUser.yaml'
with open(file_path, 'r') as file:
    data = yaml.safe_load(file)

# 将用户信息添加到 authorizedUser.yaml
if 'authorizedUsers' not in data:
    data['authorizedUsers'] = []

for username, email in user_info:
    data['authorizedUsers'].append({'name': username, 'email': email})

# 写回 authorizedUser.yaml 文件
with open(file_path, 'w') as file:
    yaml.dump(data, file)

# 创建 Pull Request
pr_title = f"Add authorized user(s) from Issue #{issue_number}"
pr_body = f"This PR adds authorized user(s) from Issue #{issue_number}."
pr = repo.create_pull(title=pr_title, body=pr_body, base="main", head=f"pull-request-branch-{issue_number}")

print(f"Pull Request created: {pr.html_url}")
