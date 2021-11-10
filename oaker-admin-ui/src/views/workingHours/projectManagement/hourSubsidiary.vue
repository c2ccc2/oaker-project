<template>
  <div class="main">
    <!-- <h3>项目工时明细</h3> -->
    <el-card v-if="showHoursub" class="box-card">
      <div slot="header" class="clearfix">
        <div class="header-title">
          项目工时明细
        </div>
        <span>累计投入：{{ totalPeople }}人/({{ totalHours }}工时)</span>
      </div>
      <div class="text item">
        <div class="">
          <el-row>
            <el-tag style="margin-right:2%;">明细详情</el-tag>
            <el-button type="primary" size="mini" @click="changeShowHoursub"
              >按月统计</el-button
            >
            <el-button type="primary" size="mini" @click="changePeople"
              >按人统计</el-button
            >
          </el-row>
        </div>
      </div>
      <template>
        <el-table
          v-show="showcardMonth"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column type="index" label="序号" align="center" width="50">
          </el-table-column>
          <el-table-column prop="month" label="月份" align="center">
            <!-- <template slot-scope="scope">
              <p>{{ scope.row.month }}月</p>
            </template> -->
          </el-table-column>
          <el-table-column prop="users" label="投入人数" align="center">
            <template slot-scope="scope">
              <p>{{ scope.row.users.length }}人</p>
            </template>
          </el-table-column>
          <el-table-column prop="totalPeople" label="总投入人天" align="center">
            <template slot-scope="scope">
              <p>{{ scope.row.totalPeople.toFixed(2) }}人天</p>
            </template>
          </el-table-column>
          <el-table-column prop="useHour" label="总投入工时" align="center">
            <!-- <template slot-scope="scope">
              <p>{{ scope.row.hourTotal }}工时</p>
            </template> -->
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                @click="changeMonth(scope.row)"
                type="primary"
                size="small"
                >查看</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-table
          v-show="showcardPeople"
          :data="tableDataPeople"
          border
          style="width: 100%"
        >
          <el-table-column type="index" label="序号" align="center" width="50">
          </el-table-column>
          <el-table-column prop="nickName" label="名字" align="center">
          </el-table-column>
          <el-table-column prop="postName" label="职位" align="center">
          </el-table-column>
          <el-table-column prop="useHour" label="总投入工时" align="center">
          </el-table-column>
        </el-table>
      </template>
    </el-card>
    <el-card v-show="showEvemts" class="box-card" v-loading="showEvents">
      <div slot="header" class="clearfix">
        <div class="header-title">
          项目工时明细
        </div>
      </div>
      <div class="text item item-two">
        <div class="detailInformation">
          <div class="detailInformation-item">
            <!-- <span>{{ nowMonth }}总计：&nbsp;</span>&nbsp;<span -->
            <span>总计：&nbsp;</span>&nbsp;<span
              >&nbsp;{{ totalPeople }}人&nbsp;</span
            >&nbsp;<span>&nbsp;{{ (totalHours / 8).toFixed(2) }}人天&nbsp;</span
            ><span>{{ totalHours }}工时</span>
          </div>
          <!-- <div class="detailInformation-item">
            <span>张三</span>
            <span>20人天</span>
            <span>160人天</span>
          </div> -->
          <!-- <div class="detailInformation-item">
            <span>王晓</span>
            <span>20人天</span>
            <span>160人天</span>
          </div> -->
          <!-- <div class="detailInformation-item">
            <span>李四</span>
            <span>20人天</span>
            <span>160人天</span>
          </div> -->
        </div>
        <div class="btn">
          <el-button type="primary" @click="changeShowHoursub">返回</el-button>
        </div>
      </div>
      <template>
        <vue-event-calendar
          :events="demoEvents"
          @monthChanged=""
          @dayChanged=""
        ></vue-event-calendar>
      </template>
    </el-card>
  </div>
</template>

<script>
import {
  projectHourMonth,
  projectHourMonthDetail,
  projectHourUser
} from "@/api/system/project";
export default {
  data() {
    return {
      showEvemts: false,
      showEvents: true,
      totalPeople: 0,
      totalHours: 0,
      showHoursub: true,
      tableData: [],
      nowMonth: "",
      projectId: "",
      monthInfo: [],
      peopleInfo: [],
      tableDataPeople: [],
      showcardMonth: true,
      showcardPeople: false,
      demoEvents: []
    };
  },
  created() {
    this.projectId = this.$route.query.projectId;
    this.init();
  },
  methods: {
    monthChange(month) {
      console.log(month);
    },
    dayChange(day) {
      console.log(day);
    },
    init() {
      let date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      if (month < 10) month = "0" + month;
      let newdate = year + "-" + month;
      projectHourUser(this.projectId, newdate).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.tableDataPeople = res.data;
          this.totalPeople = res.data.length;
          // this.tableDataPeople.forEach(el=>this.totalHours+=el.useHour)
          console.log("data", this.tableDataPeople);

          this.peopleInfo = [];
          // this.tableDataPeople.forEach(el=>{
          // })
        }
      });
      projectHourMonth(this.projectId).then(res => {
        console.log(res);
        this.totalHours = 0;
        if (res.code == 200) {
          this.tableData = res.data;
          this.tableData.forEach(el => (el.usersLength = el.users.length));
          this.tableData.forEach(el => {
            el.totalPeople = el.useHour / 8;
            this.totalHours += el.useHour;
          });
        }
      });
    },
    handleClick(row) {
      console.log(row);
    },
    changeMonth(row) {
      console.log("切换详情", row);
      let todate = row.month.replace(/-/g, "/") + "/01";
      console.log("date", todate);
      this.$EventCalendar.toDate(todate);

      setTimeout(() => {
        let eventwrapper = document.querySelector(".events-wrapper");
        let eventdate = document.querySelector(".date");
        eventwrapper.style.background = "#fff";
        eventdate.style.background = "#fff";
        eventdate.style.color = "#000";
        // console.log("eventdom", eventwrapper);
      }, 100);

      setTimeout(() => {
        this.showEvents = false;
        this.showEvemts = true;
      }, 200);
      this.nowMonth = row.month;
      projectHourMonthDetail(this.projectId, this.nowMonth).then(res => {
        console.log("date", res);
        if (res.code == 200) {
          this.monthInfo = res.data;
          setTimeout(() => {
            let detailArr = [];
            this.monthInfo.forEach(el => {
              let date = el.date.replace(/-/g, "/");
              if (el.userStatHours != null) {
                el.userStatHours.forEach(dl => {
                  let title = dl.nickName;
                  let desc = dl.nickName + "投入" + dl.useHour + "工时";
                  let data = {
                    date,
                    title,
                    desc
                  };
                  detailArr.push(data);
                });
              }
            });

            this.demoEvents = detailArr;
            console.log("detailArr", detailArr);
          }, 200);
        }
      });
      this.showHoursub = false;
    },
    changeShowHoursub() {
      console.log(111);
      this.init();
      this.showHoursub = true;
      this.showcardMonth = true;
      this.showcardPeople = false;
      this.showEvemts = false;
    },
    changePeople() {
      // console.log("changePeople");
      this.totalHours = 0;
      this.tableDataPeople.forEach(el => (this.totalHours += el.useHour));
      this.showcardMonth = false;
      this.showcardPeople = true;
    },
    dayChange(day) {
      console.log(day);
    },
    monthChange(month) {
      console.log(month);
    }
  }
};
</script>

<style scoped lang="scss">
.header-title {
  width: 100%;
  margin: 0 auto;
  text-align: center;
  font-weight: 600;
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}
.item-two {
  display: flex;
  justify-content: space-between;
}
.detailInformation {
  width: 30%;
  .detailInformation-item {
    width: 100%;
    line-height: 36px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
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
::v-deep .__vev_calendar-wrapper {
  height: 100%;
  .cal-wrapper {
    padding: 0 50px;
    .cal-header {
      display: flex;
      justify-content: center;
      .l,
      .r {
        display: none;
      }
    }
  }
}
</style>
