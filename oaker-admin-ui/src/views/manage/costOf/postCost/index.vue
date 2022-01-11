<template>
  <el-dialog
    title="岗位成本"
    :visible.sync="dialogVisible"
    width="50%"
    :before-close="handleClose"
  >
    <div>
      <el-card class="box-card" shadow='never'>
        <div slot="header" class="clearfix">
          <span
            ><el-input
              style="width:30%;margin-right:20px"
              placeholder="请输入职位名称"
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
          <!-- <el-button type="primary" size="mini" @click="addPost"
            >新增</el-button
          > -->
        </div>
        <div class="text item">
          <template>
            <el-table :data="tableData" style="width: 100%">
              <el-table-column type="index"  label="序号">
                <!-- <template slot-scope="scope">
                  {{
                    params.pageSize * (params.pageNum - 1) +
                      (scope.$index + 1)
                  }}
                </template> -->
              </el-table-column>
              <el-table-column prop="postName" label="职位名称" width="180">
              </el-table-column>
              <el-table-column label="成本" width="180">
                <template slot-scope="scope">
                  <div>
                    <el-input maxlength="8" v-model="scope.row.cost"></el-input>
                  </div>
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
                    @click="allReset(scope.$index, scope.row)"
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
    <addPost ref="addPost"></addPost>
  </el-dialog>
</template>

<script>
import { getCostPost, setPostCost } from "@/api/manage/costOf";

import batchEdit from "./batchEdit";
import addPost from "./addPost";
export default {
  data() {
    return {
      dialogVisible: false,
      inputPost: "",
      tableData: [],
      total: 0,
      form: {
        name: ""
      },
      params: {
        pageNum: 1,
        pageSize: 10,
        postName: undefined
      }
    };
  },
  components: {
    batchEdit,
    addPost
  },
  methods: {
    open() {
      this.dialogVisible = true;
      this.init();
    },
    init() {
      getCostPost(this.params).then(res => {
        // console.log("postcost", res);
        if (res.code == 200) {
          this.tableData = res.rows;
          this.total = res.total;
        }
      });
    },
    tosearch() {
      let flag = this.inputPost != "";
      // console.log("flag", flag);
      if (flag) {
        this.params.postName = this.inputPost;
        // console.log(this.params.postName);
        this.init();
      } else {
        this.params.postName = undefined;
        this.init();
      }
    },
    showBatchEdit() {
      this.$refs.batchEdit.open();
    },
    addPost() {
      // alert('新增')
      // console.log(111);
      this.$refs.addPost.open();
    },
    handleEdit(index, row) {
      // console.log(index, row);
      let data = [];
      let temp = {
        cost: parseFloat(row.cost),
        postId: row.postId
      };
      data.push(temp);

      // console.log("data", data);
      setPostCost(data).then(res => {
        // console.log("setPostCost", res);
        if (res.code == 200) {
          this.$message.success(res.msg);
        }
      });
    },
    allReset(index, row) {
      // console.log(index, row);
      this.tableData[index].cost = 0;
      let data = [];
      let temp = {
        cost: 0,
        postId: row.postId
      };
      data.push(temp);
      setPostCost(data).then(res => {
        // console.log("setPostCost", res);
        if (res.code == 200) {
          this.$message.success(res.msg);
        }
      });
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
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
