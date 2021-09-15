<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-table
                :data="tableData"
                border
                style="width: 100%">
            <el-table-column
                    fixed
                    prop="orderId"
                    label="Order Id"
                    >
            </el-table-column>
            <el-table-column
                    prop="customerName"
                    label="Customer Name"
                    >
            </el-table-column>
            <el-table-column
                    prop="terminalId"
                    label="Terminal Id"
                    >
            </el-table-column>
            <el-table-column
                    prop="orderAmount"
                    label="Total Price"
                    >
            </el-table-column>
            <el-table-column
                    prop="orderStatus"
                    label="Order Status"
                    >
<!--                <template slot-scope="scope">
                    {{transformOrderStatus(scope.row.orderStatus)}}
                </template>-->
            </el-table-column>
            <el-table-column
                    prop="payStatus"
                    label="Payment Status"
                    >
<!--                <template slot-scope="scope">
                    {{transformPayStatus(scope.row.payStatus)}}
                </template>-->
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    label="create Time"
                    >
<!--                <template slot-scope="scope">
                    {{dateFormat(scope.row.createTime)}}
                </template>-->
            </el-table-column>
            <el-table-column label="Operations" >
                <template slot-scope="scope">
                    <el-button
                            :disabled="scope.row.orderStatus==='completed'"
                            size="mini"
                            type="danger"
                            @click="cancel(scope.row)">Cancel</el-button>
                    <el-button
                            :disabled="scope.row.payStatus!=='paid'||scope.row.orderStatus==='completed'"
                            size="mini"
                            type="success"
                            @click="finish(scope.row)">Finish</el-button>
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
                total:15,
            }
        },
        methods:{
            /*           transformOrderStatus(status){
                           switch (status){
                               case 0: return 'new order';break;
                               case 1: return 'already complete';break;
                               case 2: return 'already cancelled';break;
                           }
                       },
                       transformPayStatus(status){
                           switch (status){
                               case 0: return 'not pay yet';break;
                               case 1: return 'already pay';break;
                           }
                       },*/
            dateFormat(time) {
                var date = new Date(time);
                var year=date.getFullYear();
                var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
                var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
                var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
                var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
                var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
                return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
            },
            page(currentPage){
                const _this = this
                axios.get(' https://localhost:8084/order-service/orderHead/orderListByPage/'+currentPage+'/'+_this.pageSize).then(function (resp) {
                    _this.tableData = resp.data.data.content
                    _this.pageSize = resp.data.data.size
                    _this.total = resp.data.data.total
                })
            },
            cancel(row) {
                const _this = this
                axios.put('https://localhost:8084/order-service/orderHead/updateOrderStatus/'+row.orderId).then(function (resp) {
                    if(resp.data.code == -1){
                        _this.$alert('order【'+row.orderId+'】status abnormal', 'Error', {
                            confirmButtonText: 'confirm'
                        });
                    }
                    if(resp.data.code == 200){
                        _this.$alert('order【'+row.orderId+'】already cancelled', 'Success', {
                            confirmButtonText: 'Confirm',
                            callback: action => {
                                location.reload()
                            }
                        });
                    }
                })
            },
            finish(row) {
                if(row.payStatus !== "paid"){
                    this.$alert('order【'+row.orderId+'】not pay yet，can not complete this order', 'Error', {
                        confirmButtonText: 'Confirm'
                    });
                }else{
                    const _this = this
                    axios.put('https://localhost:8084/order-service/orderHead/updateOrderStatus/'+row.orderId).then(function (resp) {
                        if(resp.data.code == -1){
                            _this.$alert('order【'+row.orderId+'】status abnormal', 'Error', {
                                confirmButtonText: 'Confirm'
                            });
                        }
                        if(resp.data.code == 0){
                            _this.$alert('order【'+row.orderId+'】already completed', 'Success', {
                                confirmButtonText: 'Confirm',
                                callback: action => {
                                    location.reload()
                                }
                            });
                        }
                    })
                }
            }
        },
        created() {
            const _this = this
            axios.get(' https://localhost:8084/order-service/orderHead/orderListByPage/1/'+_this.pageSize).then(function (resp) {
                console.log(resp.data)
                _this.tableData = resp.data.data.content
                _this.pageSize = resp.data.data.size
                _this.total = resp.data.data.total
            })
        }
    }

</script>
<style scoped>
    .el-pagination {
        text-align: center;
    }
</style>
