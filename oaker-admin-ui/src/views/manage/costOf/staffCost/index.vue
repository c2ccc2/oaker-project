<template>
  <el-dialog
    title="人员成本"
    :visible.sync="dialogVisible"
    width="60%"
    :before-close="handleClose"
  >
    <div>
      <el-card class="box-card" shadow="never">
        <div slot="header" class="clearfix">
          <span
            ><el-input
              style="width:30%;margin-right:20px"
              placeholder="请输入人员名称"
              v-model="inputPost"
              clearable
            >
            </el-input
          ></span>
          <el-button type="primary" size="mini" @click="tosearch"
            >搜索</el-button
          >
          <el-button type="primary" size="mini" @click="showBatchEdit"
            >批量修改</el-button
          >
          <el-button type="primary" size="mini" @click="init">刷新</el-button>
        </div>
        <div class="text item">
          <template>
            <el-table :data="tableData" v-loading="loading" style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                width="60"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="nickName"
                label="姓名"
                align="center"
                width="180"
              >
              </el-table-column>
              <el-table-column prop="postName" label="职位名称" width="180">
              </el-table-column>
              <el-table-column label="成本" width="180">
                <template slot-scope="scope">
                  <template>
                    <div>
                      <el-input
                        maxlength="8"
                        v-model="scope.row.cost"
                      ></el-input>
                    </div>
                  </template>
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)"
                    >修改</el-button
                  >
                  <el-button
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
                    >清空</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </template>
        </div>
      </el-card>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="params.pageNum"
        :limit.sync="params.pageSize"
        @pagination="init"
      />
    </div>
    <!-- <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </span> -->
    <batchEdit ref="batchEdit"></batchEdit>
  </el-dialog>
</template>

<script>
import { getCostUser, setUserCost } from "@/api/manage/costOf";

import batchEdit from "./batchEdit";
export default {
  data() {
    return {
      loading: true,
      dialogVisible: false,
      inputPost: "",
      total: 0,
      tableData: [],
      form: {
        name: ""
      },
      params: {
        pageNum: 1,
        pageSize: 10,
        nickName: undefined
      }
    };
  },
  components: {
    batchEdit
  },
  methods: {
    open() {
      this.dialogVisible = true;
      this.init();
    },
    init() {
      getCostUser(this.params).then(res => {
        // console.log("usercost", res);
        if (res.code == 200) {
          this.tableData = res.rows;
          this.loading = false;
          this.total = res.total;
        }
      });
    },
    tosearch() {
      let flag = this.inputPost != "";
      // console.log("flag", flag);
      if (flag) {
        this.params.nickName = this.inputPost;
        // console.log(this.params.nickName);
        this.init();
      } else {
        this.params.nickName = undefined;
        this.init();
      }
    },
    showBatchEdit() {
      this.$refs.batchEdit.open();
    },
    handleEdit(index, row) {
      // console.log(index, row, 2222);
      let data = [];
      let temp = {
        cost: parseFloat(row.cost),
        userId: row.userId
      };
      data.push(temp);
      setUserCost(data).then(res => {
        // console.log("setUserCost", res);
        if (res.code == 200) {
          this.$message.success("修改成功");
        }
      });
    },
    handleDelete(index, row) {
      // console.log(index, row);
      this.tableData[index].cost = 0;
      let data = [];
      let temp = {
        cost: 0,
        userId: row.userId
      };
      data.push(temp);
      setUserCost(data).then(res => {
        // console.log("setUserCost", res);
        if (res.code == 200) {
          this.$message.success("清零成功");
        }
      });
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          this.inputPost=""
          this.tosearch()
          this.init()
          done();
        })
        .catch(_ => {});
    }
  }
};
</script>

<style lang="scss" scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 100%;
}
</style>
