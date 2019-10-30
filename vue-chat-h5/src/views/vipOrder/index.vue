<template>
  <div class="app-container">
    <div class="toolbar">
      <el-input placeholder="请输入用户手机号" v-model="query.phone" clearable style="width: 360px">
        <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
      </el-input>
      <el-button icon="el-icon-plus" @click="onOrderAdd()">新增</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading">
      <el-table-column label="用户手机">
        <template slot-scope="scope">
          {{ scope.row.phone }}
        </template>
      </el-table-column>
      <el-table-column label="充值金额">
        <template slot-scope="scope">
          {{ scope.row.amount }}
        </template>
      </el-table-column>
      <el-table-column label="客服电话">
        <template slot-scope="scope">
          {{ scope.row.servicePhone }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间">
        <template slot-scope="scope">
          {{ scope.row.createdDt }}
        </template>
      </el-table-column>
      <!--<el-table-column label="操作" width="110" align="center">-->
      <!--<template slot-scope="scope">-->
      <!--<i title="编辑" v-if="scope.row.status == 0" class="el-icon-edit" style="cursor: pointer" @click="onEdit(scope.row)"></i>-->
      <!--</template>-->
      <!--</el-table-column>-->
    </el-table>
    <div class="block" style="float: right;margin-top: 5px;">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNo"
        :page-sizes="[5,10,15,20,50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next"
        :total="totalCount">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import * as vipOrder from '@/api/vipOrder'

  export default {
    name: 'VipOrderList',
    filters: {
      colorFilter(type) {
        const arr = ['primary', 'success', 'warning'];
        return arr[type]
      },
      statusFilter(value) {
        const arr = ['未审核', '通过', '不通过'];
        return arr[value]
      },
    },
    data() {
      return {
        list: [
          // {
          //   id: "1",
          //   phone: "13899998888",
          //   amount: 1000,
          //   servicePhone: "13899998888",
          //   createdDt: "2019-08-04 12:12:12",
          // },
        ],
        listLoading: false,
        totalCount: 1,
        pageNo: 1,
        pageSize: 10,
        query: {
          phone: ''
        },
        cityList: [
          {
            id: '1',
            name: '北京'
          }
        ],
      }
    },
    created() {
      this.fetchVipOrderList();
    },
    methods: {
      search() {
        this.fetchVipOrderList();
      },
      async fetchVipOrderList() {
        this.listLoading = true
        try {
          let response = await vipOrder.list({pageNo: this.pageNo, pageSize: this.pageSize, phone: this.query.phone})
          let {list, totalCount} = response.data
          this.list = list
          this.totalCount = totalCount
          this.listLoading = false
        } catch (e) {
          console.error(e)
          this.listLoading = false
        }
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.fetchVipOrderList();
      },
      handleCurrentChange(val) {
        this.pageNo = val;
        this.fetchVipOrderList();
      },
      onOrderAdd() {
        this.$router.push({path: '/recharge/vipOrder/create'})
      },
      onEdit(row) {
        this.$router.push({path: `/recharge/vipOrder/edit/${row.id}`})
      },
      onDel(id) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    }
  }
</script>
