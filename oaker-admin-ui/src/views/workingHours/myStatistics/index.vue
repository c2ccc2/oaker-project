<template>
  <div class="main">
    <el-card class="box-card" shadow="never">
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
        <el-descriptions title="统计" :column="5">
          <el-descriptions-item label="上报天数"
            >{{ stat.fillNum }}天</el-descriptions-item
          >
          <el-descriptions-item label="应报"
            >{{ stat.mustFillNum }}天（含加班）</el-descriptions-item
          >
          <el-descriptions-item label="缺报"
            >{{ stat.missFillNum }}天</el-descriptions-item
          >
          <el-descriptions-item label="请假"
            >{{ stat.leaveNum }}天</el-descriptions-item
          >
          <el-descriptions-item label="总计"
            >{{ (stat.totalHour / 8).toFixed(2) }}人天（{{
              stat.totalHour
            }}工时）</el-descriptions-item
          >
        </el-descriptions>
      </div>
      <div class="text item">
        <el-button-group>
          <el-button
            type="primary"
            @click="showstreamline"
            v-hasPermi="['mh:hour:stat']"
            >精简模式</el-button
          >
          <el-button
            type="primary"
            @click="showdetail"
            v-hasPermi="['mh:hour:stat:detail']"
            >详细模式</el-button
          >
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
              type="index"
                label="序号"
                align="center"
              >
              </el-table-column>
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
        <div class="detail" v-else>
          <template>
            <!-- <vue-event-calendar
              :events="demoEvents"
              @monthChanged=""
              @dayChanged=""
            ></vue-event-calendar> -->
            <!-- <el-table :data="detailInfo" style="width: 100%"> -->
            <el-table :data="detailInfo">
              <el-table-column type="index" label="序号" width="60">
              </el-table-column>
              <el-table-column prop="fillDate" label="日期" width="180">
              </el-table-column>
              <el-table-column prop="nickName" label="人员" width="120">
              </el-table-column>
              <el-table-column prop="postName" label="岗位"> </el-table-column>
              <el-table-column prop="projectName" label="项目">
              </el-table-column>
              <el-table-column prop="everyday" label="项目所属工作组">
                 <template slot-scope="scope">
                    <div class="">
                      <el-tag v-if="scope.row.everyday">每日上报</el-tag>
                      <el-tag v-else type="success">临时上报</el-tag>
                    </div>
                  </template>
              </el-table-column>
              <el-table-column prop="useHour" label="工时" width="60"> </el-table-column>
              <el-table-column prop="createTime" label="提交时间">
              </el-table-column>
            </el-table>
            <pagination
              v-show="total > 0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="init"
            />
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
      showStreamline: false,
      datetayMonth: "",
      stat: {},
      peopleday: "",
      projectList: [],
      Calendar: new Date(),
      detailInfo: [],
      demoEvents: [],
      showEvemts: false,
      total:0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    };
  },
  created() {
    // console.log(object)
    this.gettayMonth();
    this.showdetail();
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
      this.getHourDetail();
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
    },
    getHourDetail() {
      this.queryParams.date = this.datetayMonth;
      getHourStatDetail(this.queryParams).then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.detailInfo = res.rows;
          this.total=res.total
          // let detailArr = [];
          // this.detailInfo.forEach(el => {
          //   let date = el.date.replace(/-/g, "/");
          //   if (el.projectHours != null) {
          //     el.projectHours.forEach(dl => {
          //       let title = dl.projectName;
          //       let desc = "本项目所用工时：" + dl.useHour + "小时";
          //       let useHour = dl.useHour;
          //       let data = {
          //         date,
          //         title,
          //         desc,
          //         useHour
          //       };
          //       detailArr.push(data);
          //     });
          //     // let title = el.projectHours.projectName;
          //     // let desc = "本项目所用工时：" + el.projectHours.useHour + "小时";
          //   }
          // });
          // this.demoEvents = detailArr;
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
      // console.log(this.dataMonth);
      if (this.dataMonth) {
        // console.log("有");
        let todate = this.dataMonth.replace(/-/g, "/");
        this.$EventCalendar.toDate(todate);
      } else {
        // console.log("没有");
        alert("已显示当前月");
      }
      // let date = this.dataMonth;
      this.init();
    },
    showstreamline() {
      this.showStreamline = true;
      this.showEvemts = false;
    },
    showdetail() {
      this.getHourDetail();
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
// ::v-deep .events-wrapper{
//   height: 410px;
//   padding: 100px 0;
// }
::v-deep .myevents-wrapper {
  background: #fff;
}
// ::v-deep .cal-header {
//   display: flex;
// }
</style>
