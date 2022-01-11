<template>
  <div class="main">
    <el-row>
      <el-col :span="24"
        ><div class="grid-content">
          <div class="headerBtn">
            <el-date-picker
              v-model="monthDate"
              type="month"
              value-format="yyyy-MM-dd"
              placeholder="选择月"
              :editable="false"
              @change="setDate"
            >
            </el-date-picker>
            <template>
              <el-select
                v-model="ProjectId"
                placeholder="请选择项目"
                @change="setProjectId"
              >
                <el-option
                  v-for="item in options"
                  :key="item.projectId"
                  :label="item.projectName"
                  :value="item.projectId"
                >
                </el-option>
              </el-select>
              <span class="btns">
                <el-button type="primary" @click="toQuery">查询</el-button>
                <el-button type="primary" @click="getTotoday">今日</el-button>
                <el-button
                  type="primary"
                  @click="changepicker"
                  :disabled="showtable"
                  >{{ tableTipbtn }}</el-button
                ></span
              >
            </template>
          </div>
        </div></el-col
      >
    </el-row>
    <el-row v-if="showDatepicker">
      <el-col :span="24" v-if="showdateData"
        ><div class="grid-content detail">
          <Datepicker
            :options="calendarArr"
            class="calendar"
            ref="Datepicker"
            @handleClickDay="handleClickDay"
            @handlePrevMonth="handlePrevMonth"
            @handleNextMonth="handleNextMonth"
            @handleGoMyMonth="handleGoMyMonth"
          /></div
      ></el-col>
      <el-col v-else><div class="grid-content">暂无数据</div></el-col>
    </el-row>
    <el-row v-else>
      <el-col :span="24"
        ><div class="grid-content">
          <template>
            <el-table :data="tableData" style="width: 100%">
              <el-table-column type="index" label="序号" width="180">
              </el-table-column>
              <el-table-column prop="statDate" label="日期" width="180">
              </el-table-column>
              <el-table-column prop="week" label="星期" width="180">
              </el-table-column>
              <el-table-column prop="everydayFill" label="每日上报组(上报)">
              </el-table-column>
              <el-table-column prop="everydayMissNum" label="每日上报组(缺报)">
              </el-table-column>
              <el-table-column prop="allFill" label="全部上报">
              </el-table-column>
              <el-table-column prop="totalHour" label="总上报工时">
              </el-table-column>

              <el-table-column prop="address" label="操作">
                <template slot-scope="scope">
                  <el-button
                    @click="handleClick(scope.row)"
                    type="text"
                    size="small"
                    >查看详情</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </template>
        </div></el-col
      >
    </el-row>
    <dailyReportToRecordForm ref="dailyReportToRecordForm"></dailyReportToRecordForm>
  </div>
</template>

<script>
import {
  getMyActorProject,
  getProjectHourdailyCalendar,
  getProjectHourDailyList
} from "@/api/system/project";

import dailyReportToRecordForm from './dailyReportToRecord'
export default {
  components:{
    dailyReportToRecordForm
  },
  data() {
    return {
      options: [],
      ProjectId: null,
      showdateData: false,
      monthDate: "",
      tableTipbtn: "表格形式",
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
        showcontent: 2
      },
      showDatepicker: true,
      tableData: [],
      showtable: true
    };
  },
  created() {
    this.initDate();
    this.init();
  },
  methods: {
    init() {
      getMyActorProject().then(res => {
        console.log(res);
        if (res.code == 200) {
          this.options = res.data;
        }
      });
      if (this.ProjectId != null) {
        getProjectHourdailyCalendar(this.setparms()).then(res => {
          console.log("getProjectHourdailyCalendar", res);
          if (res.code == 200) {
            if (res.data != null) {
              this.showdateData = true;
            } else {
              this.showdateData = false;
            }
            this.calendarArr.hourDetail = res.data;
            console.log("res", res);
            this.$refs.Datepicker.handleGoMyMonth(this.monthDate);
            this.$refs.Datepicker.init();
          }
        });
        getProjectHourDailyList(this.setparms()).then(res => {
          console.log("getProjectHourDailyList", res);
          if (res.code == 200) {
            this.tableData = res.data;
             this.tableData.forEach(el => {
           
            let newdate = new Date(Date.parse(el.statDate.replace(/-/g, "/")));
            el.week = this.weekArr[newdate.getDay()];
          });
          }
        });
      }
    },

    initDate() {
      let date = new Date();
      let y = date.getFullYear();
      let m = date.getMonth() + 1;
      let d = "01";
      m = m < 10 ? "0" + m : m;
      this.monthDate = `${y}-${m}-${d}`;
      console.log(y, m, d);
    },
    setDate() {
      console.log(this.monthDate);
      console.log("我更改了日期");
    },
    setProjectId() {
      console.log(this.ProjectId);
      this.showtable = false;
      // console.log("我更改了项目");
    },
    setparms() {
      return {
        localDate: this.monthDate,
        projectId: this.ProjectId
      };
    },
    toQuery() {
      console.log(this.monthDate);
      console.log(this.ProjectId);
      if (this.ProjectId == null) {
        this.$message.error("请选择项目");
      } else {
        this.init();
      }
    },
    getTotoday() {
      console.log(222);
      this.initDate();
      this.init()
      this.$refs.Datepicker.handleToday();
    },
    handleClickDay(data) {
      console.log("handleClickDay");
      console.log("data", data);
      this.$refs.dailyReportToRecordForm.queryDate=data.mydate
      this.$refs.dailyReportToRecordForm.headersDate=data.mydate
      this.$refs.dailyReportToRecordForm.projectId=this.ProjectId
      this.$refs.dailyReportToRecordForm.open()
      // this.$router.push({
      //   path: "/workingHours/dailyReportToRecord",
      //   query: { date: data.mydate, id: this.ProjectId }
      // });
    },
    handlePrevMonth() {
      console.log("handlePrevMonth");
    },
    handleNextMonth() {
      console.log("handleNextMonth");
    },
    handleGoMyMonth() {
      console.log("fill-handleGoMyMonth");
    },
    changepicker() {
      this.showDatepicker = !this.showDatepicker;
      if (this.showDatepicker) {
        this.tableTipbtn = "表格形式";
      } else {
        this.tableTipbtn = "返回";
      }
      console.log("关闭日历");
    },
    handleClick(row) {
      console.log(row);
       this.$refs.dailyReportToRecordForm.queryDate=row.statDate
      this.$refs.dailyReportToRecordForm.headersDate=row.statDate
      this.$refs.dailyReportToRecordForm.projectId=this.ProjectId
      this.$refs.dailyReportToRecordForm.open()
      // this.$router.push({
      //   path: "/workingHours/dailyReportToRecord",
      //   query: { date: row.statDate, id: this.ProjectId }
      // });
    }
  }
};
</script>

<style lang="scss" scoped>
.detail {
  width: 65%;
  margin: 0 auto;
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
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.btns {
  padding: 0 12px;
}
.el-select.el-select--medium {
  margin: 0 8px;
}
</style>
