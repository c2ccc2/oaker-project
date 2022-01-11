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
          <el-button
            style="float:right"
            size="mini"
            type="primary"
            @click="changeShowHoursub"
            >返回</el-button
          >
        </div>
      </div>
      <div class="text item item-two">
        <!-- <div class="detailInformation">
          <div class="detailInformation-item">
            <span>总计：&nbsp;</span>&nbsp;<span
              >&nbsp;{{ totalPeople }}人&nbsp;</span
            >&nbsp;<span>&nbsp;{{ (totalHours / 8).toFixed(2) }}人天&nbsp;</span
            ><span>{{ totalHours }}工时</span>
          </div>
        </div> -->
        <div class="selectRows">
          <el-form
            :model="queryParams"
            ref="queryParams"
            :inline="true"
            v-show="showSearch"
          >
            <el-form-item label="" prop="monthDate">
              <el-date-picker
                v-model="queryParams.month"
                value-format="yyyy-MM"
                type="month"
                size="mini"
                placeholder="选择月"
                @change="showmonthDate"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item label="" prop="dateTime">
              <el-select
                v-model="queryParams.date"
                size="mini"
                :disabled="showDay"
                placeholder="请选择日"
                @change="showdateTime"
              >
                <el-option
                  v-for="item in day"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="value">
              <el-select
                v-model="queryParams.projectStatus"
                size="mini"
                clearable
                placeholder="请选择阶段"
              >
                <el-option
                  v-for="item in phase"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="personnelvalue">
              <el-select
                v-model="queryParams.userId"
                clearable
                size="mini"
                placeholder="请选择人员"
              >
                <el-option
                  v-for="item in personnel"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="positionvalue">
              <el-select
                v-model="queryParams.postId"
                clearable
                placeholder="请选择岗位"
                size="mini"
              >
                <el-option
                  v-for="item in position"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              ><el-button
                type="primary"
                icon="el-icon-search"
                size="mini"
                @click="toSearch"
                >搜索</el-button
              >
              <el-button
                icon="el-icon-refresh"
                size="mini"
                @click="resetQuery('queryParams')"
                >重置</el-button
              ></el-form-item
            >
          </el-form>
        </div>
      </div>
      <template>
        <!-- <vue-event-calendar
          :events="demoEvents"
          @monthChanged=""
          @dayChanged=""
        ></vue-event-calendar> -->
        <el-table :data="monthInfo" style="width: 100%">
          <el-table-column type="index" label="序号" width="60">
          </el-table-column>

          <el-table-column prop="projectStatus" label="阶段" width="120">
            <template slot-scope="scope">
              <div>
                <p v-if="scope.row.projectStatus == 'a'">
                  <el-tag type="primary">进行中</el-tag>
                </p>
                <p v-else-if="scope.row.projectStatus == 'b'">
                  <el-tag type="warning">运维中</el-tag>
                </p>
                <p v-else-if="scope.row.projectStatus == 'c'">
                  <el-tag type="success">已结束</el-tag>
                </p>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="人员"> </el-table-column>
          <el-table-column prop="postName" label="岗位"> </el-table-column>
          <el-table-column prop="everyday" label="所属工作组">
            <template slot-scope="scope">
              <div>
                <p v-if="scope.row.everyday"><el-tag>每日上报</el-tag></p>
                <p v-else><el-tag type="success">临时上报</el-tag></p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="useHour"
            align="center"
            label="工时"
            width="120"
          >
          </el-table-column>
          <el-table-column
            v-if="showcostDate"
            prop="costDate"
            label="上报时间"
            width="180"
          >
          </el-table-column>
          <el-table-column prop="createTime" label="操作时间" width="180">
          </el-table-column>
        </el-table>
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getsearch"
        />
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
import {
  getCostProjectPosts,
  getCostProjectUsers
} from "@/api/workingHour/projectManagement";
export default {
  data() {
    return {
      showcostDate: false,
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
      demoEvents: [],
      total: 0,
      tableDataSearch: [],
      showSearch: true,
      showDay: true,
      day: [],
      phase: [],
      personnel: [],
      personnelvalue: "",
      position: [],
      positionvalue: "",
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: "",
        date: undefined,
        month: undefined,
        postId: undefined,
        projectStatus: undefined,
        userId: undefined
      }
    };
  },
  created() {
    this.projectId = this.$route.query.projectId;
    this.queryParams.projectId = this.$route.query.projectId;
    this.setDay();
    this.gettaday();
    this.setPhase();
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
      projectHourUser(this.projectId).then(res => {
       // console.log(res);
        if (res.code == 200) {
          // this.tableData = res.data;
          this.tableDataPeople = res.data;
          // this.tableDataPeople.forEach(el=>this.totalHours+=el.useHour)
         // console.log("data", this.tableDataPeople);

          this.peopleInfo = [];
          // this.tableDataPeople.forEach(el=>{
          // })
        }
        // getCostQuery(this.queryParams).then(res => {
         // console.log("getCostQuery", res);
        //   if (res.code == 200) {

        //     this.monthInfo = res.rows;
        //     this.total = res.total;
        //     this.monthInfo.forEach(el => {
        //       if (el.useHour == null) {
        //         el.useHour = 0;
        //       }
             // console.log(el.costDate)
        //       if (el.costDate) {
        //         this.showcostDate = true;
        //       } else {
        //         this.showcostDate = false;
        //       }
        //       this.totalHour += el.useHour;
        //       this.toatalCost += el.userCost;
        //     });
        //   }
        // });
      });

      projectHourMonth(this.projectId).then(res => {
       // console.log(res);
        this.totalHours = 0;
        if (res.code == 200) {
          this.tableData = res.data;
          this.totalPeople = 0;
          this.tableData.forEach(el => {
            el.usersLength = el.users.length;
            this.totalPeople += el.usersLength;
          });
          this.tableData.forEach(el => {
            el.totalPeople =
              el.useHour / this.$store.state.user.appconfig.workTime;
            this.totalHours += el.useHour;
          });
        }
      });
    },
    searchform() {
      getCostProjectUsers(this.projectId).then(res => {
       // console.log("projectId", res);
        if (res.code == 200) {
          this.personnel = res.data;
        }
      });
      getCostProjectPosts(this.projectId).then(res => {
       // console.log("getCostProjectPosts", res);
        if (res.code == 200) {
          this.position = res.data;
        }
      });
    },
    handleClick(row) {
     // console.log(row);
    },
    initForm() {
      // this.queryParams = {};
      // this.queryParams.month=this.queryParams.month+"-01"
      this.queryParams = Object.assign(
        {},
        JSON.parse(JSON.stringify(this.queryParams))
      );
    },
    toSearch() {
      this.initForm();
     // console.log(this.queryParams);
      // this.init();
      // this.changeMonth();
      this.getsearch();
    },
    changeMonth(row) {
     // console.log("切换详情", row);
  
      this.searchform();
      let todate = row.month.replace(/-/g, "/") + "/01";
     // console.log("date", todate);

      setTimeout(() => {
        this.showEvents = false;
        this.showEvemts = true;
      }, 200);
      this.nowMonth = row.month;
      this.queryParams.month = row.month;
      // this.queryParams.nowMonth = row.month;
      // delete this.queryParams.month;
     // console.log("queryParams", this.queryParams);
      projectHourMonthDetail(this.queryParams).then(res => {
       // console.log("projectHourMonthDetail", res);
        if (res.code == 200) {
          this.monthInfo = res.rows;
          this.monthInfo.forEach(el => {
            if (el.costDate) {
              this.showcostDate = true;
            } else {
              this.showcostDate = false;
            }
          });
          this.total = res.total;
        }
      });
      this.showHoursub = false;
    },
    getsearch() {
      projectHourMonthDetail(this.queryParams).then(res => {
       // console.log("projectHourMonthDetail", res);
        if (res.code == 200) {
          this.monthInfo = res.rows;
          this.total = res.total;
        }
      });
    },
    gettaday() {
      let date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      if (month < 10) month = "0" + month;
      let newdate = year + "-" + month;
      return newdate;
    },
    changeShowHoursub() {
     // console.log(111);
      this.init();
      this.showHoursub = true;
      this.showcardMonth = true;
      this.showcardPeople = false;
      this.showEvemts = false;
    },
    changePeople() {
     // console.log("changePeople");
      this.totalHours = 0;
      // this.totalPeople = 0;
      this.totalPeople = this.tableDataPeople.length;
     // console.log("this.tableDataPeople", this.tableDataPeople);
      this.tableDataPeople.forEach(el => (this.totalHours += el.useHour));
      this.showcardMonth = false;
      this.showcardPeople = true;
    },
    dayChange(day) {
     // console.log(day);
    },
    setDay() {
      let date = [];
      for (let i = 1; i <= 31; i++) {
        let temp = { label: i, value: i };
        date.push(temp);
      }
      this.day = date;
    },
    setPhase() {
      let data = [
        { label: "进行中", value: "a" },
        { label: "运维中", value: "b" },
        { label: "已结束", value: "c" }
      ];
      this.phase = data;
    },
    monthChange(month) {
     // console.log(month);
    },
    showmonthDate(data) {
     // console.log(data, "---", this.monthDate);
      // this.initForm();
      if (data != null) {
        this.showDay = false;
      } else {
        this.showDay = true;
      }
     // console.log(this.queryParams);
      // this.init();
    },
    showdateTime(data) {
     // console.log(data);
      // this.initForm();
      if (data < 10) {
        data = "0" + data;
      }
      this.queryParams.date = this.queryParams.month + "-" + data;
     // console.log(this.queryParams);
      // this.init();
    },
    resetQuery() {
      this.showDay = true;
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        projectId: this.$route.query.projectId,
        date: undefined,
        month: undefined,
        postId: undefined,
        projectStatus: undefined,
        userId: undefined
      };
      this.getsearch();
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
