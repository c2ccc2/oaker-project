<template>
  <div class="main">
    <!-- <h1>成本管理</h1> -->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>成本管理</span>
      </div>
      <div class="text item">
        <template>
          <el-table :data="tableData" style="width: 100%">
            <el-table-column type="index" label="序号"></el-table-column>
            <el-table-column prop="type" label="类型" width="180">
            </el-table-column>
            <el-table-column label="状态" width="180">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.status"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  :active-value="true"
                  :inactive-value="false"
                  @change="changeStatus(scope.row)"
                >
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="primary"
                  @click="handleEdit(scope.$index, scope.row)"
                  >设置</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
    </el-card>
    <postCost ref="postCost"></postCost>
    <userCost ref="userCost"></userCost>
  </div>
</template>

<script>
import { geCosConf, editCost } from "@/api/manage/costOf";
import postCost from "./postCost";
import userCost from "./staffCost";
export default {
  data() {
    return {
      tableData: [
        {
          type: "岗位成本",
          status: true,
          typeName: "postCost"
        },
        {
          type: "人员成本",
          status: false,
          typeName: "userCost"
        }
      ]
    };
  },
  components: {
    postCost,
    userCost
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      geCosConf().then(res => {
        // console.log("getcost", res);
        if (res.code == 200) {
          this.tableData.forEach(el => {
            // console.log(el);
            if (el.typeName == "postCost") {
              el.status = res.data.postCost;
            }
            if (el.typeName == "userCost") {
              el.status = res.data.userCost;
            }
          });
        }
      });
    },
    handleEdit(index, row) {
      // console.log(index, row);
      // console.log(row.typeName);
      let dialog = row.typeName;
      this.$refs[dialog].open();
    },
    handleSet(index, row) {
      // console.log(index, row);
    },
    changeStatus(row) {
      // console.log(row);
      let flag = row.status;
      if (flag) {
        // console.log("打开");
      } else {
        // console.log("关闭");
      }
      let costNmae = row.typeName;
      let params = {};
      // params[costNmae] = flag;
      params.postCost= flag;
      params.userCost = flag;
      // console.log(params);
      editCost(params).then(res => {
        if (res.code == 200) {
          this.tableData.forEach(el=>{
            el.status=flag
          })
          this.$message.success(row.type + "状态修改成功");
        }
      });
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
