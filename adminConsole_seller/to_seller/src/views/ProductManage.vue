<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-table
                :data="tableData"
                border
                style="width: 100%">
            <el-table-column
                    fixed
                    prop="id"
                    label="Id"
                    >
            </el-table-column>
            <el-table-column
                    prop="productName"
                    label="Product Name"
                    >
            </el-table-column>
            <el-table-column
                    prop="productId"
                    label="Product Id"
                    >
            </el-table-column>
            <el-table-column
                    prop="upc"
                    label="UPC"
                    >
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="Product Price"
                    >
            </el-table-column>
            <el-table-column
                    prop="inventory"
                    label="Product Stock"
                    >
            </el-table-column>

<!--            <el-table-column label="online" width="160">

                <template slot-scope="scope">
                    <el-switch
                            style="height: 20px"
                            v-model="scope.row.status"
                            active-color="#13ce66"
                            active-text="online"
                            inactive-color="#ff4949"
                            inactive-text="offline"
                            @change="changeSwitch(scope.row)">
                    </el-switch>
                </template>

            </el-table-column>-->

            <el-table-column label="Operations">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="edit(scope.row)">Edit</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="del(scope.row)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination style="margin-top: 20px;display: inline-block;float: none "
                       background
                       layout="prev, pager, next"
                       :page-size="pageSize"
                       :total="total"
                       @current-change="page">
        </el-pagination>
    </div>

</template>

<script>
    export default {
        data() {
            return {
                tableData:null,
                pageSize:5,
                total:15
            }
        },

        methods:{
            page(currentPage){
                const _this = this
                axios.get('https://localhost:8084/product-service/product/listByPage/'+currentPage+'/'+_this.pageSize).then(function (resp) {
                    _this.tableData = resp.data.data.content
                    _this.pageSize = resp.data.data.size
                    _this.total = resp.data.data.total
                })
            },

            changeSwitch (data) {
                const _this = this
                axios.put('http://localhost:8383/product-service/seller/product/updateStatus/'+data.id+'/'+data.status).then(function (resp) {
                    if(resp.data.data == true){
                        _this.$message({
                            showClose: true,
                            message: '【'+data.name+'】already online',
                            type: 'success'
                        })
                    }else{
                        _this.$message({
                            showClose: true,
                            message: '【'+data.name+'】already offline',
                            type: 'error'
                        })
                    }
                })
            }
        },

        created() {
            const _this = this
            axios.get('https://localhost:8084/product-service/product/listByPage/1/'+_this.pageSize).then(function (resp) {
                _this.tableData = resp.data.data.content
                _this.pageSize = resp.data.data.size
                _this.total = resp.data.data.total
            })
        }
    }
</script>
<style scoped>
    #el-pagination {
        text-align: center;
    }
</style>
