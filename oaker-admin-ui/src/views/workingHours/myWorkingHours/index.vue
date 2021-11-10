<template>
  <div class="main">
    <!-- <h3>我的工时</h3> -->
    <el-row :gutter="20">
      <el-col :span="16"
        ><div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span
                ><div class="Btngroups">
                  <div class="btns">
                    <el-row>
                      <el-button
                        type="primary"
                        plain
                        @click="init"
                        :disabled="bzclick"
                        >本周</el-button
                      >
                      <el-button type="primary" plain @click="lastWeek"
                        >上周</el-button
                      >
                      <el-button
                        type="primary"
                        plain
                        @click="myMonth"
                        :disabled="byclick"
                        >本月</el-button
                      >
                      <el-button type="primary" plain @click="lastMonth"
                        >上月</el-button
                      >
                      <!-- <el-button type="primary" plain>未填报</el-button> -->
                    </el-row>
                  </div>
                  <div class="btn">
                    <!-- <el-row>
                      <el-button
                        type="primary"
                        plain
                        @click="editFillInWorkingHours"
                        >填报工时</el-button
                      >
                    </el-row> -->
                  </div>
                </div></span
              >
            </div>
            <div class="text item">
              <el-table
                :data="tableData"
                style="width: 100%"
                :show-header="false"
              >
                <el-table-column prop="date" label="日期"> </el-table-column>
                <el-table-column prop="week" label="周期"> </el-table-column>
                <el-table-column
                  prop="createTime"
                  label="提交时间"
                  width="150"
                  align="center"
                >
                  <template slot-scope="scope">
                    <p
                      v-show="!(scope.row.status == 3)"
                      v-if="scope.row.createTime == null"
                    >
                      未填报
                    </p>
                    <p v-else>{{ scope.row.createTime }}</p>
                  </template>
                </el-table-column>
                <!-- <el-table-column prop="address" label="详情">
                  <template slot-scope="scope">
                    <el-button
                      type="primary"
                      size="mini"
                      @click="lookTimeInfo(scope.row)"
                      v-show="!(scope.row.status != 1)"
                      round
                      >查看详情</el-button
                    >
                  </template>
                </el-table-column> -->
                <el-table-column
                  prop="address"
                  width="200"
                  label="提交"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-button
                      type="primary"
                      size="mini"
                      @click="lookTimeInfo(scope.row)"
                      v-show="!(scope.row.status != 1)"
                      
                      round
                      v-hasPermi="['mh:hour:detail']"
                      >查看详情</el-button
                    >
                    <el-button
                      type="primary"
                      size="mini"
                      round
                      v-show="!(scope.row.status == 3 || scope.row.status == 1)"
                      
                      :disabled="scope.row.substatus"
                      @click="submitWorking(scope.row)"
                      v-hasPermi="['mh:hour:add']"
                      >提交工时</el-button
                    >
                    <!-- <el-button
                      type="primary"
                      size="mini"
                      round
                      v-show="!(scope.row.status == 3 || scope.row.status == 1)"
                      :disabled="scope.row.substatus"
                      @click="askForLeave(scope.row)"
                      >请假</el-button
                    > -->
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </div></el-col
      >
      <el-col :span="8" v-hasPermi="['mh:project:user:all']"
        ><div class="grid-content bg-purple-dark">
          <el-card class="box-card">
            <div slot="header" class="clearfix ">
              <span>我参加的项目：</span>
              <el-select
                v-model="value"
                size="mini"
                placeholder="请选择"
                class="myheader"
                @change="changeStatus(value)"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div
              v-for="item in myProject"
              :key="item.projectId"
              class="text item projectPanel"
            >
              <!-- {{ item.projectName + item.projectStatus }} -->
              <div class="projectName">{{ item.projectName }}</div>
              <div class="projectStatus">
                <el-tag :type="item.type">{{ item.name }}</el-tag>
              </div>
            </div>
          </el-card>
        </div></el-col
      >
    </el-row>
    <el-dialog
      title="工时填报"
      :visible.sync="dialogFormVisible"
      style="width:100%;margin:0 auto"
    >
      <el-form :model="form" status-icon :rules="rules">
        <el-form-item
          v-for="item in form.projectHours"
          :key="item.projectId"
          :label="item.projectName"
          :label-width="formLabelWidth"
        >
          <el-input
            v-model.number="item.hour"
            max="8"
            maxlength="1"
            autocomplete="off"
            style="width:50%"
            @input="lookinput(item.hour)"
          ></el-input>
          <span>小时</span>
        </el-form-item>
        <el-form-item label="Tips:" :label-width="formLabelWidth">
          <p class="tips">{{ submitTips }}</p>
        </el-form-item>
        <!-- <el-form-item label="参与项目" :label-width="formLabelWidth">
          <el-select v-model="form.projectId" placeholder="请选择参与项目">
            <el-option
              v-for="(item, index) in projectArr"
              :key="index"
              :label="item.projectName"
              :value="item.projectId"
            ></el-option>
          </el-select>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button :disabled="sunmitflag" type="primary" @click="handleSubmit"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getMyProjectAll,
  getMyProjectStatus,
  getMyHourList,
  getMyActorProject,
  createHour
} from "@/api/system/project";
export default {
  data() {
    var checkAge = (rule, value, callback) => {
      // if (!value) {
      //   return callback(new Error("工时不能为空!"));
      // }
      // setTimeout(() => {
      //   if (!Number.isInteger(value)) {
      //     callback(new Error("请输入数字值!"));
      //   } else {
      //     if (value >= 9) {
      //       callback(new Error("每天不能超过8小时！"));
      //     } else {
      //       callback();
      //     }
      //   }
      // }, 1000);
    };
    return {
      submitTips: "",
      sunmitflag: true,
      weekArr: [
        "星期日",
        "星期一",
        "星期二",
        "星期三",
        "星期四",
        "星期五",
        "星期六"
      ],
      tableData: [],
      bzclick: true,
      byclick: false,
      options: [
        {
          value: "a",
          label: "正在进行"
        },
        {
          value: "b",
          label: "正在运维"
        },
        {
          value: "c",
          label: "已经归档"
        }
      ],
      value: "",
      dialogFormVisible: false,
      form: {
        date: "",
        projectHours: []
      },
      // form: {
      //   hour: "",
      //   projectId: ""
      // },
      formLabelWidth: "120px",
      myProject: [],
      dateArr: {
        weekStartDate: "",
        weekEndDate: "",
        monthStartDate: "",
        monthEndDate: "",
        lastMonthStartDate: "",
        lastMonthEndDate: ""
      },
      thisMonth: null,
      monthInfo: [],
      weekinfo: null,
      dateInfos: [],
      projectArr: [],
      hourTime: "",
      rules: {
        hour: [{ validator: checkAge, trigger: "blur" }]
      }
    };
  },
  created() {
    this.init();
  },
  methods: {
    init(flag) {
      this.getdate();
      this.bzclick = true;
      this.byclick = false;
      this.dateInfos[this.getTayWeek()];
      // console.log("this.weekinfo", this.weekinfo);
      let dateinfo = this.dateInfos[this.weekinfo];
      // console.log("dateinfo", dateinfo);
      let tempdate = new Date();
      this.thisMonth = parseInt(tempdate.getMonth());
      // console.log("thisMonth", this.thisMonth);
      this.dateArr.weekStartDate = dateinfo.startDate;
      this.dateArr.weekEndDate = dateinfo.endDate;
      getMyHourList(this.dateArr.weekEndDate, this.dateArr.weekStartDate).then(
        res => {
          // console.log(res);
          if (res.code == 200) {
            // console.log('flag',flag)
            if (flag == true) {
              // return ;
              // console.log('init不赋值')
            } else {
              // console.log('init赋值')
              this.tableData = res.data;
            }
            this.tableData.forEach(el => {
              // if (el.status == '2' || '4') {
              if (el.status == "2" || el.status == "4") {
                el.substatus = false;
              } else {
                el.substatus = true;
              }
              let newdate = new Date(Date.parse(el.date.replace(/-/g, "/")));
              el.week = this.weekArr[newdate.getDay()];
              if (el.createTime != null) {
                el.createTime = el.createTime.substr(0, 19).replace(/T/, " ");
              }
              // el.createTime=el.createTime.slice(12,16)
            });
            // console.log("this.tableData", this.tableData);
          }
        }
      );
      getMyProjectAll().then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.myProject = res.data;
          this.myProject.forEach(el => {
            if (el.projectStatus == "进行中") {
              el.type = "";
              el.name = "正在进行";
            } else if (el.projectStatus == "运维") {
              el.type = "warning";
              el.name = "正在运维";
            } else if (el.projectStatus == "归档") {
              el.type = "success";
              el.name = "已经归档";
            } else {
              el.type = "info";
              el.name = "未知状态";
            }
          });
          // console.log('this.myProject',this.myProject)
          // for (let i = 0; i < 9; i++) {
          //   this.myProject.push({
          //     projectId: i,
          //     projectName: "取经" + i,
          //     projectStatus: "进行中"
          //   });
          // }
        }
      });
    },
    lastWeek() {
      this.bzclick = false;
      this.byclick = false;
      this.weekinfo--;
      let dateinfo = this.dateInfos[this.weekinfo];
      this.dateArr.weekStartDate = dateinfo.startDate;
      this.dateArr.weekEndDate = dateinfo.endDate;
      getMyHourList(this.dateArr.weekEndDate, this.dateArr.weekStartDate).then(
        res => {
          // console.log(res);
          if (res.code == 200) {
            this.tableData = res.data;
            this.tableData.forEach(el => {
              let newdate = new Date(Date.parse(el.date.replace(/-/g, "/")));
              // el.createTime = el.createTime.substr(5, 11).replace(/T/, " ");
              // console.log(el.createTime);
              el.week = this.weekArr[newdate.getDay()];
            });
            // console.log(res);
          }
        }
      );
    },
    myMonth() {
      this.init(true);
      this.bzclick = false;
      this.byclick = true;
      // console.log("this.monthInfo", this.thisMonth, this.monthInfo);
      this.dateArr.monthEndDate = this.monthInfo[this.thisMonth].endDonthDate;
      this.dateArr.monthStartDate = this.monthInfo[
        this.thisMonth
      ].startDonthDate;
      getMyHourList(
        this.dateArr.monthEndDate,
        this.dateArr.monthStartDate
      ).then(res => {
        // console.log(res);
        if (res.code == 200) {
          setTimeout(() => {
            this.tableData = res.data;
            this.tableData.forEach(el => {
              let newdate = new Date(Date.parse(el.date.replace(/-/g, "/")));
              el.week = this.weekArr[newdate.getDay()];
            });
            // console.log(res);
          }, 200);
        }
      });
    },
    lastMonth() {
      this.bzclick = false;
      this.byclick = false;
      this.thisMonth--;
      this.dateArr.lastMonthEndDate = this.monthInfo[
        this.thisMonth
      ].endDonthDate;
      this.dateArr.lastMonthStartDate = this.monthInfo[
        this.thisMonth
      ].startDonthDate;
      getMyHourList(
        this.dateArr.lastMonthEndDate,
        this.dateArr.lastMonthStartDate
      ).then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.tableData = res.data;
          this.tableData.forEach(el => {
            let newdate = new Date(Date.parse(el.date.replace(/-/g, "/")));
            el.week = this.weekArr[newdate.getDay()];
          });
          // console.log(res);
        }
      });
    },
    getdate() {
      var now = new Date(); //当前日期
      var nowDayOfWeek = now.getDay(); //今天本周的第几天
      var nowDay = now.getDate(); //当前日
      var nowMonth = now.getMonth(); //当前月
      var nowYear = now.getYear(); //当前年
      nowYear += nowYear < 2000 ? 1900 : 0; //
      this.getDateByWeekly(nowYear);

      var lastMonthDate = new Date(); //上月日期
      lastMonthDate.setDate(1);
      lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
      var lastYear = lastMonthDate.getYear();
      var lastMonth = lastMonthDate.getMonth();
      // /格式化日期：yyyy-MM-dd
      function formatDate(date) {
        var myyear = date.getFullYear();
        var mymonth = date.getMonth() + 1;
        var myweekday = date.getDate();

        if (mymonth < 10) {
          mymonth = "0" + mymonth;
        }
        if (myweekday < 10) {
          myweekday = "0" + myweekday;
        }
        return myyear + "-" + mymonth + "-" + myweekday;
      }

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
        // return days;
        // let newmonthEndDate=this.getMonthStartDate()
      }

      //获得某月的天数 （与上面有重复可删除，不然本月结束日期报错）
      function getMonthDays(nowyear, myMonth) {
        var lastMonthStartDate = new Date(nowyear, lastMonth, 1);
        var lastMonthEndDate = new Date(nowyear, lastMonth + 1, 1);
        var days =
          (lastMonthEndDate - lastMonthStartDate) / (1000 * 60 * 60 * 24); //格式转换
        // alert(days);
        return days;
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
      this.dateArr.weekStartDate = getWeekStartDate();
      this.dateArr.weekEndDate = getWeekEndDate();
      this.dateArr.monthStartDate = getMonthStartDate();
      this.dateArr.monthEndDate = getMonthEndDate();
      this.dateArr.lastMonthStartDate = getLastMonthStartDate();
      this.dateArr.lastMonthEndDate = getLastMonthEndDate();
    },
    setWeek(dateweek, status) {
      let newdate = dateweek.split("-");
      if (status == 1) {
        newdate[2] = parseInt(newdate[2]) - 7;
        // console.log(newdate[2]);
        // console.log(newdate);
        if (newdate[2] < 10) {
          newdate[2] = "0" + newdate[2];

          // return newdate.join("-");
        }
      } else {
        newdate[2] = parseInt(newdate[2]) + 1;
        // return newdate.join("-");
      }
      // newdate[2] = parseInt(newdate[2]) + 1;
      return newdate.join("-");
    },

    lookTimeInfo(row) {
      // console.log("查看工时详情", row.id);
      this.$router.push({
        path: "/workingHours/workingInfo",
        query: { id: row.id, fillDate: row.date }
      });
    },
    editFillInWorkingHours() {
      // console.log("填报工时");
      this.$router.push({ path: "myWorkingHours/fillInWorkingHours" });
    },
    submitWorking(row) {
      console.log("提交工时", row);
      // this.$nextTick(()=>{
      this.getprojectArr(row.date);
      this.hourTime = row.date;
      this.dialogFormVisible = true;
      // });
    },
    lookinput(value) {
      let hoursum = 0;
      this.form.projectHours.forEach(el => (hoursum += el.hour));

      if (!value && value == " ") {
        this.sunmitflag = true;
        this.submitTips = "工时不能为空";
        // value = 0;
        if (value == 0) {
          this.submitTips = "可以提交!";
          this.sunmitflag = false;
        }
        // console.log(value)
        // this.$message.error("工时不能为空");
        // flag = false;
      } else {
        if (!Number.isInteger(value)) {
          this.sunmitflag = true;
          // this.$message.error("工时请输入数字值!");
          this.submitTips = "工时请输入数字值!";

          // flag = false;
        } else {
          if (value >= 9 || value < 0) {
            this.sunmitflag = true;
            // this.$message.error("工时范围为0-8小时!");
            this.submitTips = "工时范围为0-8小时!";

            // flag = false;
          } else {
            if (hoursum < 9) {
              this.sunmitflag = false;
              this.submitTips = "可以提交！";
              // console.log(value);
            } else {
              this.sunmitflag = true;
              // this.$message.error("总工时不能大于8小时!");
              this.submitTips = "总工时不能大于8小时!当前为" + hoursum + "小时";
            }
          }
        }
      }
    },
    handleSubmit() {
      // console.log("sub1", this.form);
      // let data = {
      //   date: this.hourTime,
      //   projectHours: this.form
      // };
      this.form.date = this.hourTime;
      let hoursum = 0;
      let flag = true;
      this.form.projectHours.forEach(el => {
        hoursum += el.hour;
        if (el.projectName) {
          // if (!el.hour && el.hour == 0) {
          //   this.$message.error(el.projectName + "工时不能为空或0");
          //   flag = false;
          // } else {
          //   if (!Number.isInteger(el.hour)) {
          //     this.$message.error(el.projectName + "工时请输入数字值!");
          //     flag = false;
          //   } else {
          //     if (el.hour >= 9) {
          //       this.$message.error(el.projectName + "不能超过8小时!");
          //       flag = false;
          //     } else {
          //       this.sunmitflag=false
          //      // console.log(el.hour);
          //     }
          //   }
          // }

          delete el.projectName;
        }
        if (el.projectStatus) {
          delete el.projectStatus;
        }
      });
      // console.log("hoursum", hoursum);
      if (hoursum > 8) {
        this.$message.error("工时之和不得超过8小时，当前为" + hoursum + "小时");
        flag = false;
      }
      // console.log("sub2", this.form);
      // console.log("flag", flag);
      if (flag) {
        // console.log("我请求了", this.form);
        createHour(this.form).then(res => {
          // console.log(res);
          if (res.code == 200) {
            this.init();
            this.dialogFormVisible = false;
            this.$message.success("工时提交成功");
          }
        });
      }
    },
    changeStatus(value) {
      // console.log(value);
      getMyProjectStatus(value).then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.myProject = res.data;
          this.myProject.forEach(el => {
            if (el.projectStatus == "进行中") {
              el.type = "";
              el.name = "正在进行";
            } else if (el.projectStatus == "运维") {
              el.type = "warning";
              el.name = "正在运维";
            } else if (el.projectStatus == "归档") {
              el.type = "success";
              el.name = "已经归档";
            } else {
              el.type = "info";
              el.name = "未知状态";
            }
          });
        }
      });
    },
    getTayWeek() {
      var totalDays = 0,
        now = new Date(),
        years = now.getYear();
      if (years < 1000) years += 1900;
      var days = new Array(12);
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
      if (now.getMonth() == 0) {
        totalDays = totalDays + now.getDate();
      } else {
        var curMonth = now.getMonth();
        for (var count = 1; count <= curMonth; count++) {
          totalDays = totalDays + days[count - 1];
        }
        totalDays = totalDays + now.getDate();
      }
      this.monthInfo = days;
      for (let i = 0; i < days.length; i++) {
        this.monthInfo[i] = {
          year: years,
          month: i + 1,
          day: days[i],
          startDonthDate: `${years}-${i + 1}-01`,
          endDonthDate: `${years}-${i + 1}-${days[i]}`
        };
      }
      this.monthInfo.forEach(el => {
        if (el.month < 10) {
          el.month = "0" + el.month;
          (el.startDonthDate = `${el.year}-${el.month}-01`),
            (el.endDonthDate = `${el.year}-${el.month}-${el.day}`);
        }
      });
      // console.log(this.monthInfo)
      //得到第几周
      var week = Math.round(totalDays / 7);
      this.weekinfo = week;
      return week;
    },
    getDateByWeekly(year) {
      var d = new Date(year, 0, 1);
      // console.log(year+"年第一天:"+d);
      //第一天不是周一时
      while (d.getDay() != 1) {
        d.setDate(d.getDate() + 1);
      }
      // console.log(d);
      var to = new Date(year + 1, 0, 1);
      // console.log((year+1)+"年第一天:"+to);
      var week = 2; //默认第一周
      var startDate = year + "-01-01",
        // endDate = year + "-" + (d.getMonth() + 1) + "-" + (d.getDate() - 1); //起止日期
        endDate = [year, d.getMonth() + 1, d.getDate() - 1]; //起止日期
      var date = startDate + "," + endDate;
      var dateInfos = [
        {
          year: year,
          week: week,
          date: date
        }
      ];
      for (var from = d; from < to; ) {
        // document.write(
        //   year +
        //     "年第" +
        //     week +
        //     "周 " +
        //     (from.getMonth() + 1) +
        //     "月" +
        //     from.getDate() +
        //     "日 - "
        // );
        // startDate = year + "-" + (from.getMonth() + 1) + "-" + from.getDate();
        startDate = [year, from.getMonth() + 1, from.getDate()];
        from.setDate(from.getDate() + 6);
        if (from < to) {
          // document.write(
          //   from.getMonth() + 1 + "月" + from.getDate() + "日<br / >"
          // );
          // endDate = year + "-" + (from.getMonth() + 1) + "-" + from.getDate();
          endDate = [year, from.getMonth() + 1, from.getDate()];

          from.setDate(from.getDate() + 1);
        } else {
          to.setDate(to.getDate() - 1);
          // document.write(to.getMonth() + 1 + "月" + to.getDate() + "日<br / >");
          // endDate = year + "-" + (to.getMonth() + 1) + "-" + to.getDate();
          endDate = [year, to.getMonth() + 1, to.getDate()];
        }
        week++;
        // startDate.forEach(el => {
        //   if (el < 10) {
        //     el = "0" + `${el}`;
        //   }
        // });
        // endDate.forEach(el => {
        //   if (el < 10) {
        //     el = "0" + `${el}`;
        //   }
        // });
        function setarr(arr) {
          for (let i = 0; i < arr.length; i++) {
            if (arr[i] < 10) {
              arr[i] = "0" + arr[i];
            }
          }
        }
        setarr(startDate);
        setarr(endDate);
        dateInfos.push({
          year: year,
          week: week,
          startMonth: startDate[1],
          endMonth: endDate[1],
          startDate: startDate[0] + "-" + startDate[1] + "-" + startDate[2],
          endDate: endDate[0] + "-" + endDate[1] + "-" + endDate[2],
          date:
            startDate[0] +
            "-" +
            startDate[1] +
            "-" +
            startDate[2] +
            "," +
            endDate[0] +
            "-" +
            endDate[1] +
            "-" +
            endDate[2]
        });
      }

      // console.log(JSON.stringify(dateInfos));
      // console.log(dateInfos);
      this.dateInfos = dateInfos;
      if (this.dateInfos[0].week == 1) {
        let week0 = {
          year: year,
          week: 1,
          date: "*"
        };
        this.dateInfos.unshift(week0);
      }
      // console.log(this.dateInfos);
    },
    getprojectArr(date) {
      getMyActorProject(date).then(res => {
        // console.log(res);
        this.form.projectHours = [];
        if (res.code == 200) {
          this.projectArr = res.data;
          this.projectArr.forEach(el => (el.hour = 0));
          // console.log("projectArr", this.projectArr);
          // this.form.projectHours = Object.assign(
          //   [],
          //   JSON.parse(JSON.stringify(this.projectArr))
          // );
          // this.form.projectHours=this.projectArr
          // this.form.projectHours=JSON.parse(JSON.stringify(Object.assign([],this.projectArr)))
          this.projectArr.forEach(el =>
            this.form.projectHours.push(
              Object.assign({}, JSON.parse(JSON.stringify(el)))
            )
          );
          // console.log("this.form", this.form);
          // console.log(this.projectArr);
        }
      });
    },
    askForLeave() {
      alert("不批，滚蛋！");
    }
  }
};
</script>

<style scoped lang="scss">
.main {
  width: 95%;
  margin: 0 auto;
  margin-top: 18px;
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
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  // background: #d3dce6;
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
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}
.projectPanel {
  width: 100%;
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(80, 81, 85, 0.267);
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
  .myheader {
    width: 40%;
    margin-left: 10%;
  }
}
.Btngroups {
  height: 56px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.tips {
  // text-align: center;
  color: red;
  font-size: 12px;
  margin: 0 auto;
}
::v-deep .el-dialog__wrapper .el-dialog {
  width: 35%;
}
</style>
