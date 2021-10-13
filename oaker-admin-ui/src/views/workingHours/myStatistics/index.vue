<template>
  <div class="main">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span
          ><el-date-picker
            v-model="datetayMonth"
            format="yyyy-MM"
            value-format="yyyy-MM-dd"
            default-value
            default-time
            type="month"
            placeholder="选择月"
            @change="changeMonth(datetayMonth)"
          >
          </el-date-picker
        ></span>
        <el-button
          style="margin-left:1%;"
          type="primary"
          size="medium"
          @click="getMonth"
          >查询</el-button
        >
      </div>
      <div class="text item">
        <el-descriptions title="统计" :column="4">
          <el-descriptions-item label="上报天数"
            >{{ stat.fillNum }}天</el-descriptions-item
          >
          <el-descriptions-item label="应报"
            >{{ stat.mustFillNum }}天（含加班）</el-descriptions-item
          >
          <el-descriptions-item label="缺报"
            >{{ stat.missFillNum }}天</el-descriptions-item
          >
          <el-descriptions-item label="总计"
            >{{ (stat.totalHour/8).toFixed(2) }}人天（{{
              stat.totalHour
            }}工时）</el-descriptions-item
          >
        </el-descriptions>
      </div>
      <div class="text item">
        <el-button-group>
          <el-button type="primary" @click="showstreamline">精简模式</el-button>
          <el-button type="primary" @click="showdetail">详细模式</el-button>
        </el-button-group>
      </div>
      <div class="text item">
        <div v-if="showStreamline" class="streamline">
          <!-- <el-button type="primary">精简模式</el-button> -->
          <!-- <div
            v-for="item in projectList"
            :key="item.projectId"
            class="text item"
          >
            <span>
              {{ item.projectName }}
            </span>
            ------
            <span>
              {{ item.useHour }}
            </span>
            <span>
              {{ "小时" }}
            </span>
          </div> -->
          <template>
            <el-table
              :data="projectList"
              style="width: 30%"
              :show-header="false"
            >
              <el-table-column
                prop="projectName"
                label="项目名字"
                align="center"
              >
              </el-table-column>
              <el-table-column prop="useHour" label="工时" align="center">
                <template slot-scope="scope">
                  <p>{{ scope.row.useHour }}小时</p>
                </template>
              </el-table-column>
              <!-- <el-table-column prop="address" label="地址"> </el-table-column> -->
            </el-table>
          </template>
        </div>
        <div v-show="showEvemts" class="detail" v-loading="showEvents">
          <template>
            <vue-event-calendar
              :events="demoEvents"
              @monthChanged=""
              @dayChanged=""
            ></vue-event-calendar>
          </template>
          <!-- <template>
            <vue-event-calendar :events="demoEvents">
              <template scope="props">
                <div
                  v-for="(event, index) in props.showEvents"
                  class="event-item"
                >
                  {{ event }}
                </div>
              </template>
            </vue-event-calendar>
          </template> -->
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getHourStat, getHourStatDetail } from "@/api/system/project";
export default {
  data() {
    return {
      showEvents: true,
      dataMonth: "",
      showStreamline: true,
      datetayMonth: "",
      stat: {},
      peopleday: "",
      projectList: [],
      Calendar: new Date(),
      detailInfo: [],
      demoEvents: [],
      showEvemts: false
    };
  },
  created() {
    // console.log(object)
    this.gettayMonth();
    this.init();
  },
  methods: {
    monthChange(month) {
      // console.log(month);
    },
    dayChange(day) {
      // console.log(day);
    },
    init() {
      // console.log("time", this.Calendar);
      let date = this.datetayMonth;
      getHourStat(date).then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.stat = res.data;
          this.projectList = res.data.projectHours;
          this.peopleday = res.data.projectHours.length;
          // console.log(this.stat);
        }
      });
      getHourStatDetail(date).then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.detailInfo = res.data;
          let detailArr = [];
          this.detailInfo.forEach(el => {
            let date = el.date.replace(/-/g, "/");
            if (el.projectHours != null) {
              el.projectHours.forEach(dl => {
                let title = dl.projectName;
                let desc = "本项目所用工时：" + dl.useHour + "小时";
                let data = {
                  date,
                  title,
                  desc
                };
                detailArr.push(data);
              });
              // let title = el.projectHours.projectName;
              // let desc = "本项目所用工时：" + el.projectHours.useHour + "小时";
            }
          });
          this.demoEvents = detailArr;
          // console.log(detailArr);
        }
      });
    },
    gettayMonth() {
      let date = new Date();
      let yy = date.getFullYear();
      let mm = date.getMonth() + 1;
      let dd = "01";
      if (mm < 10) {
        mm = "0" + mm;
      }
      let newdate = yy + "-" + mm + "-" + dd;
      this.datetayMonth = newdate;
      // console.log(newdate);
    },
    changeMonth(time) {
      this.dataMonth = time;
    },
    getMonth() {
      // this.init()
      let todate = this.dataMonth.replace(/-/g, "/");
      // console.log(todate);
      this.$EventCalendar.toDate(todate);
      // console.log("查阅月", this.dataMonth);
      let date = this.dataMonth;
      // console.log(date);
      this.init();
      // console.log(time);
    },
    showstreamline() {
      this.showStreamline = true;
      this.showEvemts = false;
    },
    showdetail() {
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

      this.showStreamline = false;
    }
  }
};
</script>

<style scoped lang="scss">
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
.is-selected {
  color: #1989fa;
}
.detail {
  width: 100%;
  margin: 0 auto;
}
::v-deep .__vev_calendar-wrapper {
  height: 100%;
  .cal-wrapper {
    padding: 0 50px;
  }
}
// ::v-deep .events-wrapper{
//   height: 410px;
//   padding: 100px 0;
// }
::v-deep .myevents-wrapper {
  background: #fff;
}
</style>
