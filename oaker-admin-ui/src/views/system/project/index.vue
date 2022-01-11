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
            <el-button
              style="float: right;"
              type="primary"
              size="mini"
              @click="newProject"
              v-hasPermi="['system:project:add']"
              >创建项目</el-button
            >
          </el-row></span
        >
      </div>
      <div class="text item">
        <template>
          <el-table :data="tableData" border style="width: 100%">
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
            <el-table-column prop="projectCode" label="项目编码" align="center">
            </el-table-column>
            <el-table-column
              prop="projectManagerName"
              label="项目经理"
              width="120"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="createTime"
              custom
              label="创建时间"
              align="center"
            >
            </el-table-column>

            <el-table-column prop="projectStatus" label="状态" align="center">
              <template slot-scope="scope">
                <p>
                  <el-tag v-if="scope.row.projectStatus == 'a'">进行中</el-tag>
                  <el-tag
                    type="warning"
                    v-else-if="scope.row.projectStatus == 'b'"
                    >运维中</el-tag
                  >
                  <el-tag
                    type="success"
                    v-else-if="scope.row.projectStatus == 'c'"
                    >已结束</el-tag
                  >
                  <el-tag
                    type="info"
                    v-else-if="scope.row.projectStatus != 'a' || 'b' || 'c'"
                    >未知状态</el-tag
                  >
                </p>
              </template>
            </el-table-column>

            <el-table-column label="操作" width="260" align="center">
              <template slot-scope="scope">
                <el-button
                  @click="handleClick(scope.row)"
                  type="primary"
                  size="small"
                  plain
                  v-hasPermi="['system:project:query']"
                  >项目设置</el-button
                >
                <el-button
                  @click="handlearchive(scope.row)"
                  :type="scope.row.projectStatus == 'c' ? 'info' : 'success'"
                  :disabled="scope.row.projectStatus == 'c'"
                  size="small"
                  plain
                  >归档</el-button
                >
                <el-button
                  @click="handleDelete(scope.row.projectId)"
                  type="danger"
                  size="small"
                  plain
                  >删除</el-button
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
    <addForm ref="addform"></addForm>
  </div>
</template>

<script>
// import {listProject} from "@/api/project"
import {
  listProject,
  delProject,
  getDicttype,
  getProjectStatus,
  updaProjectStatus
} from "@/api/system/project";
import addForm from "./addForm.vue";
// import func from "vue-editor-bridge";
export default {
  components: {
    addForm
  },
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
    this.setdata();
    this.getdata();
  },
  methods: {
    setdata() {
      var time = new Date();
      var ji = new Date(time).getDay();
      // console.log(time);
      // console.log("ji", ji);
    },
    // 获取本周，上周，本月，上月的起止日期
    getdata() {
      var now = new Date(); //当前日期
      var nowDayOfWeek = now.getDay(); //今天本周的第几天
      var nowDay = now.getDate(); //当前日
      var nowMonth = now.getMonth(); //当前月
      var nowYear = now.getYear(); //当前年
      nowYear += nowYear < 2000 ? 1900 : 0; //

      var lastMonthDate = new Date(); //上月日期
      lastMonthDate.setDate(1);
      lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
      var lastYear = lastMonthDate.getYear();
      var lastMonth = lastMonthDate.getMonth();
      // /格式化日期：yyyy-MM-dd
      function formatDate(date) {
        var myyear = date.getFullYear();
        var mymonth = date.getMonth() + 1;
        var myweekday = date.getDate() + 1;

        if (mymonth < 10) {
          mymonth = "0" + mymonth;
        }
        if (myweekday < 10) {
          myweekday = "0" + myweekday;
        }
        return myyear + "-" + mymonth + "-" + myweekday;
      }

      //获得某月的天数
      // function getMonthDays(myMonth) {
      //   var monthStartDate = new Date(nowYear, myMonth, 1);
      //   var monthEndDate = new Date(nowYear, myMonth + 1, 1);
      //   var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
      //   return days;
      // }

      //获得本季度的开始月份
      function getQuarterStartMonth() {
        var quarterStartMonth = 0;
        if (nowMonth < 3) {
          quarterStartMonth = 0;
        }
        if (2 < nowMonth && nowMonth < 6) {
          quarterStartMonth = 3;
        }
        if (5 < nowMonth && nowMonth < 9) {
          quarterStartMonth = 6;
        }
        if (nowMonth > 8) {
          quarterStartMonth = 9;
        }
        return quarterStartMonth;
      }

      //获得本周的开始日期
      function getWeekStartDate() {
        var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
        // console.log("weekStartDate", weekStartDate);
        return formatDate(weekStartDate);
      }
      // console.log(getWeekStartDate());

      //获得本周的结束日期
      function getWeekEndDate() {
        var weekEndDate = new Date(
          nowYear,
          nowMonth,
          nowDay + (6 - nowDayOfWeek)
        );
        return formatDate(weekEndDate);
      }
      // console.log(getWeekEndDate());

      //获得本月的开始日期
      function getMonthStartDate() {
        var monthStartDate = new Date(nowYear, nowMonth, 1);
        return formatDate(monthStartDate);
      }

      //获得本月的结束日期
      function getMonthEndDate() {
        var days = getMonthDays(nowMonth); //获取当月总共有多少天
        var monthEndDate = new Date(nowYear, nowMonth, days);
        return formatDate(monthEndDate); //返回当月结束时间
      }

      // 注意：因为上面这个方法里的getMonthDays(nowMonth)方法 我尝试使用失败，我不知道是什么原因 所以我用了另外个方法如下（获得某月的天数,需要配合文章开头的公式 ）：

      //获得某月的天数 （与上面有重复可删除，不然本月结束日期报错）
      function getMonthDays(nowyear, myMonth) {
        var lastMonthStartDate = new Date(nowyear, lastMonth, 1);
        var lastMonthEndDate = new Date(nowyear, lastMonth + 1, 1);
        var days =
          (lastMonthEndDate - lastMonthStartDate) / (1000 * 60 * 60 * 24); //格式转换
        alert(days);
      }

      //获得上月开始时间
      function getLastMonthStartDate() {
        var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
        return formatDate(lastMonthStartDate);
      }

      //获得上月结束时间
      function getLastMonthEndDate() {
        var lastMonthEndDate = new Date(
          nowYear,
          lastMonth,
          getMonthDays(lastMonth)
        );
        return formatDate(lastMonthEndDate);
      }

      //获得本季度的开始日期
      function getQuarterStartDate() {
        var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1);
        return formatDate(quarterStartDate);
      }

      //或的本季度的结束日期
      function getQuarterEndDate() {
        var quarterEndMonth = getQuarterStartMonth() + 2;
        var quarterStartDate = new Date(
          nowYear,
          quarterEndMonth,
          getMonthDays(quarterEndMonth)
        );
        return formatDate(quarterStartDate);
      }

      //获取本年开始-当前时间

      var currentYear = now.getFullYear(); //获得当前年份4位年

      var currentYearFirstDate = new Date(currentYear, 0, 1); //本年第一天

      var startTime =
        currentYearFirstDate.getFullYear() +
        "-" +
        (currentYearFirstDate.getMonth() + 1) +
        "-" +
        currentYearFirstDate.getDate() +
        "" +
        currentYearFirstDate.getHours() +
        ":" +
        currentYearFirstDate.getMinutes() +
        ":" +
        currentYearFirstDate.getSeconds(); //格式化本年第一天日期

      var currentYearEndDate = now; //当前时间
    },
    // 获取本周，上周，本月，上月的起止日期

    init() {
      getDicttype("mh_project_status").then(res => {
        // console.log(res);
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
      listProject(this.addDateRange(this.queryParams, this.dateRange)).then(
        res => {
          // console.log(res);
          if (res.code == 200) {
            // this.$message.success(res.msg);
            this.total = res.total;
            this.tableData = res.rows;
          } else {
            this.$message.error(res.msg);
          }
        }
      );
    },
    getList() {
      this.loading = true;
      this.init();
    },
    lookSearch(dictValue) {
      // console.log(dictValue);
      getProjectStatus(dictValue).then(res => {
        // console.log(res);
        if (res.code == 200) {
          // this.$message.success(res.msg);
          this.total = res.total;
          this.tableData = res.rows;
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    lookDate() {
      this.init();
    },
    newProject() {
      // console.log("创建项目");
      this.$refs.addform.open();
    },
    handleClick(row) {
      // console.log(row.projectId);
      let projectId = row.projectId;
      this.$router.push({
        path: "/mange/project-set/projectSettings",
        query: { projectId }
      });
    },

    handlearchive(row) {
      // console.log(row);
      updaProjectStatus(row.projectId, "c").then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.init();
        }
      });
    },
    handleDelete(id) {
      // console.log(id);
      delProject(id).then(res => {
        // console.log(res);
        if (res.code) {
          this.$message.success("删除成功");
          this.init();
        } else {
          this.$message.error("删除失败！");
          this.init();
        }
      });
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
