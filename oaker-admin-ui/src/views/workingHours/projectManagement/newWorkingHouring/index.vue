<template>
  <div class="main">
    <!-- <h1>工时上报记录</h1> -->
    <el-card class="box-card" shadow="never">
      <div slot="header" class="clearfix">
        <!-- <span>卡片名称</span> -->
        <el-row :gutter="20">
          <el-col :span="5"
            ><div class="grid-content bg-purple-dark">
              <div class="block">
                <el-date-picker
                  v-model="Datemonth"
                  type="month"
                  value-format="yyyy-MM"
                  placeholder="选择月份"
                  :editable="false"
                  @change="showDateMonth"
                >
                </el-date-picker>
              </div></div
          ></el-col>
          <el-col :span="18"
            ><div class="grid-content bg-purple-light">
              <!-- <el-button type="primary" @click="veckans">本周</el-button> -->
              <!-- <el-button type="primary" @click="lastWeek">上一周</el-button> -->
              <el-button type="primary" @click="instant">本月</el-button>
              <el-button type="primary" @click="lastMonth">上月</el-button>
              <el-button type="primary" @click="nextMonth">下月</el-button>
              <el-button type="primary" @click="setTable">{{
                setTableText
              }}</el-button>
              <!-- <el-button type="primary" @click="allData">所有</el-button> -->
            </div></el-col
          >
        </el-row>
        <!-- <el-button style="float: right; padding: 3px 0" type="text"
          >操作按钮</el-button
        > -->
      </div>
      <div class="text item" v-if="showpicker">
        <template>
          <Datepicker
            :options="calendarArr"
            class="calendar"
            ref="Datepicker"
            @handleClickDay="handleClickDay"
            @handlePrevMonth="handlePrevMonth"
            @handleNextMonth="handleNextMonth"
            @handleGoMyMonth="handleGoMyMonth"
          />
        </template>
      </div>
      <div class="text item tablestyle" v-else>
        <template>
          <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="statDate" label="日期" width="180">
            </el-table-column>
            <el-table-column prop="week" label="星期" width="180">
            </el-table-column>
            <el-table-column
              prop="everydayFill"
              label="每日上报组(上报)"
              width="180"
            >
            </el-table-column>
            <el-table-column prop="everydayMissNum" label="每日上报组(缺报)">
            </el-table-column>
            <el-table-column prop="allFill" label="全部上报"> </el-table-column>
            <el-table-column prop="totalHour" label="总上报工时">
            </el-table-column>
            <!-- <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="primary"
                  @click="handleEdit(scope.$index, scope.row)"
                  >查看详情</el-button
                >
              </template>
            </el-table-column> -->
          </el-table>
        </template>
      </div>
    </el-card>
  </div>
</template>

<script>
import {
  getHourStatFill,
  getProjectHourStatCalendar
} from "@/api/workingHour/projectManagement";
export default {
  data() {
    return {
      showpicker: true,
      setTableText: "表格",
      Datemonth: "",
      Dateweek: "",
      projectId: "",
      tableData: [],
      lastwevalwekval: 1,
      lastmonthStep: 1,
      weekArr: [
        "星期日",
        "星期一",
        "星期二",
        "星期三",
        "星期四",
        "星期五",
        "星期六"
      ],
      calendarArr: {
        type: "combination",
        headStyle: {
          todayBtn: "right",
          combination: "center",
          checkBtn: "right"
        },
        viewStyle: {
          day: "right"
        },
        calendarData: [],
        monthDate: "",
        hourDetail: [],
        showcontent: 2,
        lastMonthval: 0
      }
    };
  },
  created() {
    this.projectId = this.$route.query.projectId;
    this.showdta();
    this.setWeekDay();
    this.init();
    // this.setDateMonthday();
  },
  methods: {
    showdta() {
      // console.log("projectId ", this.projectId);
    },
    init() {
      // this.Datemonth = this.Dateweek.localDate;
      getHourStatFill(this.Dateweek, this.projectId).then(res => {
        // console.log("res", res);
        if (res.code == 200) {
          this.tableData = res.data;
          // this.$refs.Datepicker.handleGoMyMonth(this.monthDate);
          // this.$refs.Datepicker.init();
          this.tableData.forEach(el => {
            let newdate = new Date(Date.parse(el.statDate.replace(/-/g, "/")));
            el.week = this.weekArr[newdate.getDay()];
          });
        }
      });
      this.setProjectHourStatCalendar();
    },
    setProjectHourStatCalendar() {
      getProjectHourStatCalendar(this.Dateweek, this.projectId).then(res => {
        if (res.code == 200) {
          console.log("getProjectHourStatCalendar", res);
          // this.calendarArr.hourDetail = this.tableData;

          this.$refs.Datepicker.handleGoMyMonth(this.Dateweek);
          this.calendarArr.hourDetail = res.data;
          this.$refs.Datepicker.init();
        }
      });
    },
    gettabledata() {
      getHourStatFill(this.Dateweek, this.projectId).then(res => {
        // console.log("res", res);
        if (res.code == 200) {
          this.tableData = res.data;
          // this.$refs.Datepicker.handleGoMyMonth(this.monthDate);
          // this.$refs.Datepicker.init();
          this.tableData.forEach(el => {
            let newdate = new Date(Date.parse(el.statDate.replace(/-/g, "/")));
            el.week = this.weekArr[newdate.getDay()];
          });
        }
      });
    },
    showDateMonth(monthtime) {
      // console.log(monthtime);
      if (monthtime == null) {
        this.veckans();
      } else {
        let date = monthtime.split("-");
        let year = date[0];
        let mm = date[1] 
        // mm = mm < 10 ? "0" + mm : mm;
        // console.log(date, year, mm);
        // console.log(this.theWeek()[mm - 1]);
        let data = {};
        console.log("mm", mm);
        data.localDate = year + "-" + mm + "-" + "01";
        // data.endDate = year + "-" + mm + "-" + this.theWeek()[mm - 1];
        data.projectId = this.projectId;
        this.Dateweek = data.localDate;
        // console.log(this.Dateweek);
        this.init();
      }
    },
    veckans() {
      // alert("本周");

      this.lastwevalwekval = 1;
      this.setWeekDay();
      this.init();
    },
    lastWeek() {
      // alert("上一周");
      let data = {};
      data.localDate = this.getMonday("s", -this.lastwevalwekval);
      // data.endDate = this.getMonday("e", -this.lastwevalwekval);
      data.projectId = this.projectId;
      this.Dateweek = data;
      // console.log(this.Dateweek);
      this.init();
      this.lastwevalwekval++;
    },
    instant() {
      // this.lastMonthval=0;
      // alert("本月");
      let date = new Date();
      let year = date.getFullYear();
      let mm = date.getMonth() + 1;
      mm = mm < 10 ? "0" + mm : mm;
      // console.log(year, mm);
      // console.log(this.theWeek()[mm - 1]);
      let data = {};
      // data.localDate = year + "-" + mm + "-" + "01";
      data.localDate = this.getMonth("s", 0);
      this.lastMonthval = 0;
      // data.endDate = year + "-" + mm + "-" + this.theWeek()[mm - 1];
      data.projectId = this.projectId;
      this.Dateweek = data.localDate;
      this.Datemonth = data.localDate;
      // console.log(this.Dateweek);
      this.init();
    },
    allData() {
      alert("所有");
    },
    lastMonth() {
      console.log("上月", this.Datemonth);
      this.lastMonthval++;
      let data = {};
      data.startDate = this.getMonth("s", -this.lastMonthval);
      // let temp = this.Datemonth.split("-");
      // temp[1] = parseInt(temp[1]) - this.lastmonthStep;
      console.log("上月", this.getMonth("s", -this.lastMonthval));
      this.Dateweek = data.startDate;
      this.Datemonth = data.startDate;
      this.gettabledata();
      this.setProjectHourStatCalendar();
      this.$refs.Datepicker.handlePrevMonth();
      // this.lastMonthval++;
      console.log(this.lastMonthval);
      // this.lastmonthStep++
    },
    nextMonth() {
      console.log("下月");
      this.lastMonthval--;
      console.log(this.lastMonthval);
      let data = {};
      data.startDate = this.getMonth("s", -this.lastMonthval);
      // let temp = this.Datemonth.split("-");
      // temp[1] = parseInt(temp[1]) + this.lastmonthStep;
      console.log("下月", data);
      this.Dateweek = data.startDate;
      this.Datemonth = data.startDate;
      this.gettabledata();
      this.setProjectHourStatCalendar();

      this.$refs.Datepicker.handleNextMonth();
    },
    setTable() {
      console.log("表格");
      this.showpicker = !this.showpicker;
      if (this.showpicker) {
        this.setTableText = "表格";
      } else {
        this.setTableText = "返回";
      }
    },
    handleEdit(index, row) {
      // console.log(index, row);
      let projectId = this.projectId;
      let statDate = row.statDate;
      this.$router.push({
        path: "/projectManagement/tadayForHour",
        query: { projectId, statDate }
      });
    },
    setWeekDay() {
      //  设置本周起止时间
      // let data = {};
      // let date = new Date();
      // let year = date.getFullYear();
      // let mm = date.getMonth() + 1;
      // mm = mm < 10 ? "0" + mm : mm;

      // data.localDate = year + "-" + mm + "-" + "01";
      let data = {};
      data.startDate = this.getMonth("s", 0);
      data.endDate = this.getMonth("e", 0);
      this.lastMonthval = 0;
      data.projectId = this.projectId;
      this.Dateweek = data.startDate;
      this.Datemonth = data.startDate;
      // console.log(this.Dateweek);
    },
    theWeek() {
      let totalDays = 0;
      let now = new Date();
      let years = now.getYear();
      if (years < 1000) years += 1900;
      let days = new Array(12);
      days[0] = 31;
      days[2] = 31;
      days[3] = 30;
      days[4] = 31;
      days[5] = 30;
      days[6] = 31;
      days[7] = 31;
      days[8] = 30;
      days[9] = 31;
      days[10] = 30;
      days[11] = 31;
      //判断是否为闰年，针对2月的天数进行计算
      if (Math.round(now.getYear() / 4) == now.getYear() / 4) {
        days[1] = 29;
      } else {
        days[1] = 28;
      }
      // if (now.getMonth() == 0) {
      //   totalDays = totalDays + now.getDate();
      // } else {
      //   let curMonth = now.getMonth();
      //   for (let count = 1; count <= curMonth; count++) {
      //     totalDays = totalDays + days[count - 1];
      //   }
      //   totalDays = totalDays + now.getDate();
      // }
      //得到第几周
      // let week = Math.round(totalDays / 7);
      return days;
    },
    setDateMonthday() {
      // 测试获取本周时间
      // this.getMonday(type, dates); //type为字符串类型，有两种选择，"s"代表开始,"e"代表结束，dates为数字类型，不传或0代表本周，-1代表上周，1代表下周
      this.getMonday("s", 0); //得到下周一的yyyy-mm-dd格式日期
      this.getMonday("e", 0); //得到下周日的yyyy-mm-dd格式日期
      // console.log(this.getMonday("s", -2), this.getMonday("e", -2));
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
    handleClickDay() {},
    handlePrevMonth() {},
    handleNextMonth() {},
    handleGoMyMonth() {},
    getMonth(type, months) {
      // 新的获取月的起止时间
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
    }
  }
};
</script>

<style lang="scss" scoped>
.text {
  font-size: 14px;
}

.item {
  width: 65%;
  margin: 0 auto;
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
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.tablestyle {
  width: 100%;
}
</style>
