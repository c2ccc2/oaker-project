<template>
  <div class="main">
    <el-dialog
      title="岗位成本批量修改"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose"
      append-to-body
    >
      <span>
        <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center">
          </el-table-column>
          <el-table-column type="index" label="序号" width="60" align="center">
          </el-table-column>
          <el-table-column prop="postName" label="职位名称" >
          </el-table-column>
          <el-table-column prop="costOf" label="成本" >
            <template slot-scope="scope">
              <div>
                <el-input maxlength="8" v-model="scope.row.cost"></el-input>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="params.pageNum"
          :limit.sync="params.pageSize"
          @pagination="init"
        />
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="danger" @click="allReset">全部清零</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getCostPost, setPostCost } from "@/api/manage/costOf";

export default {
  data() {
    return {
      dialogVisible: false,
      total: 0,
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        postName: undefined
      },
      multipleSelection: []
    };
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
    allReset() {
      // this.dialogVisible = false
      // console.log('0000')
      this.$confirm("确认全部清零？", { type: "error" })
        .then(_ => {
          // done();
          this.reset();
        })
        .catch(_ => {
          this.$message.warning("取消清零");
        });
    },
    reset() {
      this.tableData.forEach(el => {
        el.cost = 0;
      });
      let data = Object.assign([], JSON.parse(JSON.stringify(this.tableData)));
      data.forEach(el => {
        delete el.createTime;
        delete el.createUser;
        delete el.postName;
        delete el.updateTime;
        delete el.updateUser;
      });
      if (data == 0) {
        this.$message.error("必须选择最少一个");
      } else {
        setPostCost(data).then(res => {
          if (res.code == 200) {
            this.$message.success("已经清零");
            this.dialogVisible = false;
          }
        });
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // console.log(this.multipleSelection);
    },
    submit() {
      // console.log(this.multipleSelection);
      this.multipleSelection.forEach(el => {
        delete el.createTime;
        delete el.createUser;
        delete el.postName;
        delete el.updateTime;
        delete el.updateUser;
      });
      if (this.multipleSelection.length == 0) {
        this.$message.error("必须选择最少一个");
      } else {
        setPostCost(this.multipleSelection).then(res => {
          if (res.code == 200) {
            this.$message.success("批量修改成功");
            this.dialogVisible = false;
          }
        });
      }
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

<style></style>
