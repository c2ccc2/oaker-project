<template>
  <div class="main">
    <!-- <h3>项目管理</h3> -->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span
          ><el-row :gutter="10" class="mb8">
            <el-col v-for="item in statusBtns" :key="item.dictSort" :span="1.5">
              <el-button
                :type="item.type"
                plain
                :icon="item.icon"
                size="mini"
                @click="lookSearch(item.dictValue)"
                >{{ item.dictLabel }}</el-button
              >
            </el-col>
            <!-- <el-col :span="1.5">
              <el-button type="warning" plain icon="el-icon-truck" size="mini"
                >运维中</el-button
              >
            </el-col> -->
            <!-- <el-col :span="1.5">
              <el-button
                type="success"
                plain
                icon="el-icon-folder-checked"
                size="mini"
                >已归档</el-button
              >
            </el-col> -->
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-folder-opened"
                size="mini"
                @click="lookDate"
                >所有</el-button
              >
            </el-col>
            <!-- <el-button
              style="float: right;"
              type="primary"
              size="mini"
              @click="newProject"
              >创建项目</el-button
            > -->
          </el-row></span
        >
      </div>
      <div class="text item">
        <template>
          <el-table
            :data="tableData"
            border
            style="width: 100%"
            :default-sort="{ prop: 'createTime' }"
          >
            <el-table-column
              fixed
              type="index"
              label="序号"
              width="50"
              align="center"
            >
            </el-table-column>
            <el-table-column prop="projectName" label="项目名称" align="center">
            </el-table-column>
            <el-table-column prop="projectStatus" label="阶段" align="center">
              <template slot-scope="scope">
                <p>
                  <el-tag v-if="scope.row.projectStatus == '进行中'">{{
                    scope.row.projectStatus
                  }}</el-tag>
                  <el-tag
                    type="warning"
                    v-else-if="scope.row.projectStatus == '运维'"
                    >{{ scope.row.projectStatus }}</el-tag
                  >
                  <el-tag
                    type="success"
                    v-else-if="scope.row.projectStatus == '归档'"
                    >{{ scope.row.projectStatus }}</el-tag
                  >
                  <el-tag
                    type="info"
                    v-else-if="scope.row.projectStatus != 'a' || 'b' || 'c'"
                    >未知状态</el-tag
                  >
                </p>
              </template>
            </el-table-column>
            <el-table-column
              prop="projectManagerName"
              label="项目经理"
              width="120"
              align="center"
            >
            </el-table-column>
            <el-table-column prop="trjd" label="投入进度" align="center">
            </el-table-column>
            <el-table-column prop="" label="工时总计" align="center">
              <template slot-scope="scope">
                <p>{{ scope.row.useHour }}/{{ scope.row.totalHour }}</p>
              </template>
            </el-table-column>

            <el-table-column prop="projectStatus" label="状态" align="center">
              <template slot-scope="scope">
                <p v-if="scope.row.projectStatus == '归档'"> <el-tag type="danger">结束</el-tag></p>
                <p v-else>
                  <el-tag type="success" v-if="scope.row.status">正常</el-tag>
                  <el-tag type="warning" v-else>已超出</el-tag>
                </p>
                
              </template>
            </el-table-column>
            <!-- <el-table-column prop="createTime" label="昨天上报" align="center">
              <template slot-scope="scope">
                <p>{{ scope.row.yesFill }}/{{ scope.row.yesMustFill }}</p>
              </template>
            </el-table-column> -->
            <el-table-column prop="createTime" label="今天上报" align="center">
              <template slot-scope="scope">
                <p>{{ scope.row.todayFill }}/{{ scope.row.todayMustFill }}</p>
              </template>
            </el-table-column>

            <el-table-column label="操作" width="300" align="center">
              <template slot-scope="scope">
                <el-button
                  @click="handlerecord(scope.row)"
                  type="primary"
                  size="small"
                  plain
                  v-hasPermi="['system:project:stat:fill:detail']"
                  >上报记录</el-button
                >
                <el-button
                  @click="handledetail(scope.row)"
                  type="primary"
                  size="small"
                  plain
                  v-hasPermi="['system:project:stat:query']"
                  >工时明细</el-button
                >
                <el-button
                  @click="handleClick(scope.row)"
                  type="primary"
                  size="small"
                  plain
                  v-hasPermi="['system:project:query']"
                  >项目管理</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </template>
      </div>
    </el-card>
  </div>
</template>

<script>
// import {listProject} from "@/api/project"
import {
  listProject,
  delProject,
  getDicttype,
  getProjectStatus,
  updaProjectStatus,
  projectHourStat,
  projectHourStatStatus
} from "@/api/system/project";
export default {
  data() {
    return {
      tableData: [],
      // 查询按钮
      statusBtns: [],
      //总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined,
        status: undefined
      }
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      getDicttype("mh_project_status").then(res => {
        console.log(res);
        if (res.code == 200) {
          this.statusBtns = res.data;
          this.statusBtns.forEach(el => {
            if (el.dictSort == 0) {
              el.type = "primary";
              el.icon = "el-icon-folder-add";
            } else if (el.dictSort == 1) {
              el.type = "warning";
              el.icon = "el-icon-truck";
            } else if ((el.dictSort = 2)) {
              el.type = "success";
              el.icon = "el-icon-folder-checked";
            } else {
              el.type = "info";
              el.icon = "el-icon-message-solid";
            }
          });
        }
      });
      // listProject(this.addDateRange(this.queryParams, this.dateRange)).then(
      //   res => {
      //     console.log(res);
      //     if (res.code == 200) {
      //       this.total = res.total;
      //       this.tableData = res.rows;
      //     } else {
      //       this.$message.error(res.msg);
      //     }
      //   }
      // );
      projectHourStat().then(res => {
        console.log("projectHourStat", res);
        if (res.code == 200) {
          this.total = res.total;
          this.tableData = res.rows;
          this.tableData.forEach(el => {
            el.trjd = this.setPercentage(el.useHour, el.totalHour);
            el.status = this.judgeState(el.useHour, el.totalHour);
          });
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    getList() {
      this.loading = true;
      this.init();
    },
    lookSearch(dictValue) {
      console.log(dictValue);
      // getProjectStatus(dictValue).then(res => {
      //   console.log(res);
      //   if (res.code == 200) {
      //     // this.$message.success(res.msg);
      //     this.total = res.total;
      //     this.tableData = res.rows;
      //   } else {
      //     this.$message.error(res.msg);
      //   }
      // });
      projectHourStatStatus(dictValue).then(res => {
        console.log("status", res);
        if (res.code == 200) {
          // this.$message.success(res.msg);
          this.total = res.total;
          this.tableData = res.rows;
          this.tableData.forEach(el => {
            el.trjd = this.setPercentage(el.useHour, el.totalHour);
            el.status = this.judgeState(el.useHour, el.totalHour);
          });
        }else if(res.code==0){
         if(res.rows==null){
           this.tableData = [];
         }
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    lookDate() {
      this.init();
    },
    newProject() {
      console.log("创建项目");
      this.$refs.addform.open();
    },
    handleClick(row) {
      console.log(row);
      let projectId = row.projectId;
      console.log(projectId);
      this.$router.push({ path: "/projectManagement/projectSettingsHour", query: { projectId } });
    },

    handlerecord(row) {
      console.log(row);
      let projectId = row.projectId;
      let totalHour = row.totalHour;
      console.log(projectId);
      this.$router.push({
        path: "/projectManagement/workingHoursForDetails",
        query: { projectId, totalHour: row.useHour }
      });
    },
    handledetail(row) {
      let projectId = row.projectId;
      this.$router.push({
        path: "/projectManagement/hourSubsidiary",
        query: { projectId }
      });
    },
    setPercentage(num, total) {
      // console.log("setPercentage", num, total);
      num = parseFloat(num);
      total = parseFloat(total);
      if (isNaN(num) || isNaN(total)) {
        return "-";
      }
      return total <= 0
        ? "0%"
        : Math.round((num / total) * 10000) / 100.0 + "%";
    },
    judgeState(num, total) {
      if (num > total) {
        return false;
      } else {
        return true;
      }
    }
  }
};
</script>

<style>
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
