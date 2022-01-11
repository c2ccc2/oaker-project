<template>
  <div class="main">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <div class="selectBtn">
          <el-date-picker
            v-model="taday"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            default-value
            default-time
            align="right"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptions"
            @change="setDatePicker"
          >
          </el-date-picker>
          <el-row style="margin-left:20px">
            <el-button type="primary" @click="init">查询</el-button>
            <el-button type="primary" @click="getdatetaday">今日</el-button>
            <el-button type="primary" @click="getyesterday">上一日</el-button>
            <el-button type="primary" @click="weekSearch">按周查询</el-button>
          </el-row>
        </div>
      </div>
      <div class="titleInfo">
        <el-descriptions title="统计：">
          <el-descriptions-item label="每日填报组"
            >{{ 0 + fillsT.length }}/{{
              0 + fillsT.length + unFillsT.length
            }}</el-descriptions-item
          >
          <el-descriptions-item label="上报人数">{{
            0 + fills.length
          }}</el-descriptions-item>
          <el-descriptions-item label="今日工时投入"
            >{{ totalHour }} 小时</el-descriptions-item
          >
        </el-descriptions>
      </div>
      <div class="">
        <div class="tipTitle">
          已上报
        </div>
        <div class="">
          <template>
            <el-row :gutter="24">
              <el-col :span="12"
                ><div class="grid-content bg-purple">
                  <el-card class="box-card" shadow="hover">
                    <div slot="header" class="clearfix">
                      <span>每日上报组:{{ fillsT.length }}人</span>
                      <!-- <el-button
                        style="float: right; padding: 3px 0"
                        type="text"
                        >操作按钮</el-button
                      > -->
                    </div>
                    <div class="text item">
                      <el-table
                        :data="fillsT"
                        :show-header="false"
                        style="width: 100%"
                      >
                        <el-table-column type="index" label="序号" width="60">
                        </el-table-column>
                        <el-table-column label="姓名" width="120">
                          <template slot-scope="scope">
                            <p>{{ scope.row.nickName }}</p>
                            <p>{{ scope.row.postName }}</p>
                          </template>
                        </el-table-column>
                        <el-table-column
                          prop="createTime"
                          label="时间"
                          width="160"
                        >
                        </el-table-column>
                        <el-table-column prop="useHour" label="工时">
                          <template slot-scope="scope">
                            <p>{{ scope.row.useHour }}小时</p>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </el-card>
                </div></el-col
              >
              <el-col :span="12"
                ><div class="grid-content bg-purple-light">
                  <el-card class="box-card" shadow="hover">
                    <div slot="header" class="clearfix">
                      <span>其他组:{{ fillsF.length }}人</span>
                      <!-- <el-button
                        style="float: right; padding: 3px 0"
                        type="text"
                        >操作按钮</el-button
                      > -->
                    </div>
                    <div class="text item">
                      <el-table
                        :data="fillsF"
                        :show-header="false"
                        style="width: 100%"
                      >
                        <el-table-column type="index" label="序号" width="60">
                        </el-table-column>
                        <el-table-column label="姓名" width="120">
                          <template slot-scope="scope">
                            <p>{{ scope.row.nickName }}</p>
                            <p>{{ scope.row.postName }}</p>
                          </template>
                        </el-table-column>
                        <el-table-column
                          prop="createTime"
                          label="时间"
                          width="160"
                        >
                        </el-table-column>
                        <el-table-column prop="useHour" label="工时">
                          <template slot-scope="scope">
                            <p>{{ scope.row.useHour }}小时</p>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </el-card>
                </div></el-col
              >
            </el-row>
          </template>
        </div>
      </div>
      <div class="">
        <div class="tipTitle">
          未上报
        </div>
        <div class="">
          <template>
            <el-row :gutter="24">
              <el-col :span="12"
                ><div class="grid-content bg-purple">
                  <el-card class="box-card" shadow="hover">
                    <div slot="header" class="clearfix">
                      <span
                        >每日上报组({{ unFillsT.length }}人)：{{
                          unFillsT.length - qjStatus
                        }}人未报，{{ qjStatus }}人请假</span
                      >
                      <!-- <el-button
                        style="float: right; padding: 3px 0"
                        type="text"
                        >操作按钮</el-button
                      > -->
                    </div>
                    <div class="text item">
                      <el-table
                        :data="unFillsT"
                        :show-header="false"
                        style="width: 100%"
                      >
                        <el-table-column type="index" label="序号" width="60">
                        </el-table-column>
                        <el-table-column label="姓名" width="120">
                          <template slot-scope="scope">
                            <p>{{ scope.row.nickName }}</p>
                            <p>{{ scope.row.postName }}</p>
                          </template>
                        </el-table-column>
                        <el-table-column
                          prop="fillStatus"
                          label="时间"
                          width="160"
                        >
                          <template slot-scope="scope">
                            <div>
                              <p v-if="scope.row.fillStatus == 5">
                                <el-tag type="warning">请假</el-tag>
                              </p>
                              <p v-else>
                                <el-tag type="danger">未填报</el-tag>
                              </p>
                            </div>
                          </template>
                        </el-table-column>
                        <el-table-column prop="useHour" label="工时">
                          <template slot-scope="scope">
                            <p>{{ scope.row.useHour }}小时</p>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </el-card>
                </div></el-col
              >
              <el-col :span="12"
                ><div class="grid-content bg-purple-light">
                  <el-card class="box-card" shadow="hover">
                    <div slot="header" class="clearfix">
                      <span>其他组：{{ unFillsF.length }}人</span>
                      <!-- <el-button
                        style="float: right; padding: 3px 0"
                        type="text"
                        >操作按钮</el-button
                      > -->
                    </div>
                    <div class="text item">
                      <el-table
                        :data="unFillsF"
                        :show-header="false"
                        style="width: 100%"
                      >
                        <el-table-column type="index" label="序号" width="60">
                        </el-table-column>
                        <el-table-column label="姓名" width="120">
                          <template slot-scope="scope">
                            <p>{{ scope.row.nickName }}</p>
                            <p>{{ scope.row.postName }}</p>
                          </template>
                        </el-table-column>
                        <el-table-column
                          prop="createTime"
                          label="时间"
                          width="160"
                        >
                        </el-table-column>
                        <el-table-column prop="useHour" label="工时">
                          <template slot-scope="scope">
                            <p>{{ scope.row.useHour }}小时</p>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </el-card>
                </div></el-col
              >
            </el-row>
          </template>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { projectHourStatFillDetail } from "@/api/system/project";
import { getHourStatFillDetail } from "@/api/workingHour/projectManagement";

export default {
  data() {
    return {
      date: "今日",
      headerInfo: {},
      yesterdayStep: 1,
      tableDatafills: [],
      tableDataunFills: [],
      unFills: [],
      fills: [],
      totalHour: 0,
      qjStatus: 0,
      projectId: "",
      taday: "",
      queryparams: {},
      dayVal: 1,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            }
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            }
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            }
          }
        ]
      },
      value1: "",
      yesterday: ""
    };
  },
  created() {
    // alert("开发中");
    this.queryparams.projectId = this.$route.query.projectId;
    if (this.$route.query.statDate) {
      this.taday = this.$route.query.statDate;
      this.queryparams.date = this.$route.query.statDate;
     // console.log("111");
    } else {
      this.getdatetaday();
     // console.log("222");
    }
    this.init();
  },
  computed: {
    fillsT: function() {
      return this.fills.filter(item => item.everyday == true);
    },
    fillsF: function() {
      return this.fills.filter(item => item.everyday == false);
    },
    unFillsT: function() {
      return this.unFills.filter(item => item.everyday == true);
    },
    unFillsF: function() {
      return this.unFills.filter(item => item.everyday == false);
    }
  },
  methods: {
    init() {
      getHourStatFillDetail(this.queryparams).then(res => {
       // console.log("getHourStatFillDetail", res);
        if (res.code == 200) {
          if (res.data.fills == null && res.data.unFills == null) {
            this.fills = [];
            this.unFills = [];
          } else {
            this.fills = res.data.fills;
            this.unFills = res.data.unFills;
          }
          this.totalHour = 0;
          this.qjStatus = 0;
          this.fills.forEach(el => {
            if (el.useHour == null) {
              el.useHour = 0;
            }
            this.totalHour += el.useHour;
          });
          this.unFills.forEach(el => {
            if (el.useHour == null) {
              el.useHour = 0;
            }
            if (el.fillStatus == 5) {
              this.qjStatus++;
            }
          });
        }
      });
    },
    setDatePicker(date) {
     // console.log(date);
      this.queryparams.date = date;
    },
    getdatetaday() {
      this.yesterdayStep = 1;
      this.dayVal = 1;
      let date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      if (month < 10) {
        month = "0" + month;
      }
      if (day < 10) {
        day = "0" + day;
      }
      let newdate = year + "-" + month + "-" + day;
      this.taday = newdate;
      this.queryparams.date = newdate;
      this.init();
     // console.log(newdate);
    },
    getyesterday() {
      let newdate = this.setyesterday(-this.dayVal);
      this.queryparams.date = newdate;
      this.taday = newdate;
      this.dayVal++;
      this.init();
    },
    setyesterday(dates) {
      let dd = new Date();
      let n = dates || 0;
      dd.setDate(dd.getDate() + n);
      let y = dd.getFullYear();
      let m = dd.getMonth() + 1;
      let d = dd.getDate();
      m = m < 10 ? "0" + m : m;
      d = d < 10 ? "0" + d : d;
      let day = y + "-" + m + "-" + d;
      return day;
    },
    weekSearch() {
      let projectId = this.queryparams.projectId;
      this.$router.push({
        path: "/projectManagement/newWorkingHouring",
        query: { projectId }
      });
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
.selectBtn {
  // width: 50%;
  line-height: 56px;
  display: flex;
  // justify-content: space-between;
}
.titleInfo {
  width: 100%;
  border-bottom: 1px solid #e6ebf5;
  ::v-deep .el-descriptions-row {
    font-size: 16px;
  }
}
.tipTitle {
  line-height: 56px;
  font-weight: 600;
}
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
</style>
