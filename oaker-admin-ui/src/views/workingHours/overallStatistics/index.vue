<template>
  <div class="main">
    <!-- <h1>总体统计</h1> -->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>
          <div class="Btngroups">
            <div class="btns">
              <el-row>
                <el-button type="primary" plain @click="veckans"
                  >本周</el-button
                >
                <el-button type="primary" plain @click="lastWeek"
                  >上周</el-button
                >
                <el-button type="primary" plain @click="instant"
                  >本月</el-button
                >
                <el-button type="primary" plain @click="lastMonth"
                  >上月</el-button
                >
              </el-row>
            </div>
          </div></span
        >
      </div>
      <div class="text item">
        <el-table
          :data="tableData"
          style="width: 100%"
        >
          <el-table-column prop="date" label="日期" width="180">
          </el-table-column>
          <el-table-column prop="week" label="星期" width="180">
          </el-table-column>
          <el-table-column prop="fillNum" label="上报人数" width="180">
          </el-table-column>
          <el-table-column prop="leaveNum" label="请假人数" width="180">
          </el-table-column>
          <el-table-column prop="totalHour" label="总工时" width="180">
          </el-table-column>
          <el-table-column prop="unFillNum" label="缺报人数"> </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                type="primary"
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >查看详情</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getOverallStat } from "@/api/workingHour/overallStatistics";
export default {
  data() {
    return {
      Dateweek: {},
      lastwevalwekval: 1,
      lastMonthval: 1,
      tableData: [],
      weekArr: [
        "星期日",
        "星期一",
        "星期二",
        "星期三",
        "星期四",
        "星期五",
        "星期六"
      ]
    };
  },
  created() {
    this.setWeekDay();
    this.init();
  },
  methods: {
    init() {
     // console.log("总体统计");
      getOverallStat(this.Dateweek).then(res => {
       // console.log(res);
        if (res.code == 200) {
          this.tableData = res.data;
          this.tableData.forEach(el => {
            let newdate = new Date(Date.parse(el.date.replace(/-/g, "/")));
            el.week = this.weekArr[newdate.getDay()];
          });
        }
      });
    },
    veckans() {
      // alert("本周");
      this.lastwevalwekval=1
      this.setWeekDay();
      this.init();
    },
    lastWeek() {
      // alert("上一周");
      let data = {};
      data.startDate = this.getMonday("s", -this.lastwevalwekval);
      data.endDate = this.getMonday("e", -this.lastwevalwekval);
      this.Dateweek = data;
     // console.log(this.Dateweek);
      this.init();
      this.lastwevalwekval++;
    },
    instant() {
      // alert("本月");
      this.lastMonthval=1
      let data = {};
      data.startDate = this.getMonth("s", 0);
      data.endDate = this.getMonth("e", 0);
      this.Dateweek = data;
     // console.log(this.Dateweek);
      this.init();
    },
    lastMonth() {
      // alert("上月");
      let data = {};
      data.startDate = this.getMonth("s", -this.lastMonthval);
      data.endDate = this.getMonth("e", -this.lastMonthval);
      this.Dateweek = data;
     // console.log(this.Dateweek);
      this.init();
      this.lastMonthval++;
    },
    setWeekDay() {
      //  设置本周起止时间
      let data = {};
      data.startDate = this.getMonday("s", 0);
      data.endDate = this.getMonday("e", 0);
      this.Dateweek = data;
     // console.log(this.Dateweek);
    },
    getMonth(type, months) {
      let d = new Date();
      let year = d.getFullYear();
      let month = d.getMonth() + 1;
      if (Math.abs(months) > 12) {
        months = months % 12;
      }
      if (months != 0) {
        if (month + months > 12) {
          year++;
          month = (month + months) % 12;
        } else if (month + months < 1) {
          year--;
          month = 12 + month + months;
        } else {
          month = month + months;
        }
      }
      month = month < 10 ? "0" + month : month;
      let date = d.getDate();
      let firstday = year + "-" + month + "-" + "01";
      let lastday = "";
      if (
        month == "01" ||
        month == "03" ||
        month == "05" ||
        month == "07" ||
        month == "08" ||
        month == "10" ||
        month == "12"
      ) {
        lastday = year + "-" + month + "-" + 31;
      } else if (month == "02") {
        if (
          (year % 4 == 0 && year % 100 != 0) ||
          (year % 100 == 0 && year % 400 == 0)
        ) {
          lastday = year + "-" + month + "-" + 29;
        } else {
          lastday = year + "-" + month + "-" + 28;
        }
      } else {
        lastday = year + "-" + month + "-" + 30;
      }
      let day = "";
      if (type == "s") {
        day = firstday;
      } else {
        day = lastday;
      }
      return day;
    },
    getMonday(type, dates) {
      // 获取周的起止时间
      let now = new Date();
      let nowTime = now.getTime();
      let day = now.getDay();
      let longTime = 24 * 60 * 60 * 1000;
      let n = longTime * 7 * (dates || 0);
      let dd = null;
      if (type == "s") {
        dd = nowTime - (day - 1) * longTime + n;
      }
      if (type == "e") {
        dd = nowTime + (7 - day) * longTime + n;
      }
      dd = new Date(dd);
      let y = dd.getFullYear();
      let m = dd.getMonth() + 1;
      let d = dd.getDate();
      m = m < 10 ? "0" + m : m;
      d = d < 10 ? "0" + d : d;
      let days = y + "-" + m + "-" + d;
      return days;
    },
    handleEdit(index, row) {
     // console.log(index, row);
      this.$router.push({
        path: "/overallStatistics/workingHoursForDetails",
        query: { projectId: 38,date:row.date }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
::v-deep .el-table .warning-row {
  background: oldlace;
}

::v-deep .el-table .success-row {
  background: #f0f9eb;
}
</style>
