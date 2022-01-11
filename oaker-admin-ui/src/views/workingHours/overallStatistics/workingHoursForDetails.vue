<template>
  <div class="main">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <div class="header-title">
          项目工时上报记录
        </div>
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
          >
          </el-date-picker>
          <el-row style="margin-left:20px">
            <el-button type="primary" @click="init">查询</el-button>
            <el-button type="primary" @click="getdatetaday">今日</el-button>
            <el-button type="primary" @click="getyesterday">上一日</el-button>
          </el-row>
        </div>
      </div>
      <div class="titleInfo">
        <el-descriptions title="统计：" :column="4">
          <el-descriptions-item label="上报人数"
            >{{ headerInfo.fillNum }}/{{
              headerInfo.totalPeople
            }}</el-descriptions-item
          >
          <el-descriptions-item label="请假人数">{{
            headerInfo.leaveNum
          }}</el-descriptions-item>
          <el-descriptions-item label="缺报人数"
            >{{ headerInfo.unFillNum }}
          </el-descriptions-item>
          <el-descriptions-item label="总工时"
            >{{ headerInfo.totalHour }} 工时</el-descriptions-item
          >
        </el-descriptions>
      </div>
      <div class="">
        <div class="tipTitle">
          已上报
        </div>
        <div class="">
          <template>
            <el-table :data="tableDatafills" style="width: 100%">
              <el-table-column type="expand">
                <template slot-scope="props">
                  <el-form
                    label-position="left"
                    :inline="true"
                    class="demo-table-expand"
                  >
                    <el-form-item
                      v-for="item in props.row.projectDetails"
                      :key="item.projectId"
                      :label="item.projectName + ':'"
                    >
                      <span>{{ item.useHour }}小时</span>
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column type="index" label="序号" width="180">
              </el-table-column>
              <el-table-column prop="nickName" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="postName" label="职位" width="180">
              </el-table-column>
              <el-table-column prop="totalHour" label="工时" width="180">
                <template slot-scope="scope">
                  <p>{{ scope.row.totalHour }}小时</p>
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="更新时间">
              </el-table-column>
              <el-table-column prop="fillTime" label="上报时间">
              </el-table-column>
            </el-table>
            <pagination
              v-show="filltotal > 0"
              :total="filltotal"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="init"
            />
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
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span>缺报人员</span>
                    </div>
                    <div class="text item">
                      <el-table
                        :data="tableDatalackNewspaper"
                        style="width: 100%"
                      >
                        <el-table-column type="index" label="序号" width="180">
                        </el-table-column>
                        <el-table-column
                          prop="nickName"
                          label="姓名"
                          width="180"
                        >
                        </el-table-column>
                        <el-table-column prop="postName" label="职位">
                        </el-table-column>
                      </el-table>
                      <pagination
                        v-show="lackNewspapertotal > 0"
                        :total="lackNewspapertotal"
                        :page.sync="queryParamslackNewspaper.pageNum"
                        :limit.sync="queryParamslackNewspaper.pageSize"
                        @pagination="init"
                      />
                    </div>
                  </el-card></div
              ></el-col>
              <el-col :span="12"
                ><div class="grid-content bg-purple-light">
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span>请假人员</span>
                    </div>
                    <div class="text item">
                      <el-table
                        :data="tableDataaskForLeave"
                        style="width: 100%"
                      >
                        <el-table-column type="index" label="序号" width="180">
                        </el-table-column>
                        <el-table-column
                          prop="nickName"
                          label="姓名"
                          width="180"
                        >
                        </el-table-column>
                        <el-table-column prop="postName" label="职位">
                        </el-table-column>
                      </el-table>
                      <pagination
                        v-show="askForLeavetotal > 0"
                        :total="askForLeavetotal"
                        :page.sync="queryParamsaskForLeave.pageNum"
                        :limit.sync="queryParamsaskForLeave.pageSize"
                        @pagination="init"
                      />
                    </div>
                  </el-card></div
              ></el-col>
            </el-row>
          </template>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import {
  getOverallDetail,
  getOverallDetailStat,
  getOverallDetailMiss,
  getOverallDetailLeave
} from "@/api/workingHour/overallStatistics";
export default {
  data() {
    return {
      date: "今日",
      yesterdayStep: 1,
      tableDatafills: [],
      tableDatalackNewspaper: [],
      tableDataaskForLeave: [],
      headerInfo: {},
      projectId: "",
      totalHour: "",
      taday: "",
      filltotal: 0,
      lackNewspapertotal: 0,
      askForLeavetotal: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      queryParamslackNewspaper: {
        pageNum: 1,
        pageSize: 10
      },
      queryParamsaskForLeave: {
        pageNum: 1,
        pageSize: 10
      },
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
    this.projectId = this.$route.query.projectId;
    this.taday = this.$route.query.date;
   // console.log(this.projectId);
    // this.getdatetaday();
    this.init();
  },
  methods: {
    init() {
     // console.log(this.taday);
      // let params = {
      //   date: this.taday,
      //   projectId: this.projectId
      // };

      this.queryParams.localDate = this.taday;
      this.queryParamslackNewspaper.localDate = this.taday;
      this.queryParamsaskForLeave.localDate = this.taday;
      getOverallDetail(this.queryParams).then(res => {
       // console.log("getOverallDetail", res);
        if (res.code == 200) {
          this.tableDatafills = res.rows;
          this.filltotal = res.total;
        }
      });
      let dataStat = { localDate: this.taday };
      getOverallDetailStat(dataStat).then(res => {
       // console.log("dataStat", res);
        if (res.code == 200) {
          this.headerInfo = res.data;
          this.headerInfo.totalPeople =
            this.headerInfo.fillNum +
            this.headerInfo.leaveNum +
            this.headerInfo.unFillNum;
        }
      });
      getOverallDetailMiss(this.queryParamslackNewspaper).then(res => {
       // console.log("getOverallDetailMiss", res);
        if (res.code == 200) {
          this.tableDatalackNewspaper = res.rows;
          this.lackNewspapertotal = res.total;
        }
      });
      getOverallDetailLeave(this.queryParamsaskForLeave).then(res => {
       // console.log("getOverallDetailLeave", res);
        if (res.code == 200) {
          this.tableDataaskForLeave = res.rows;
          this.askForLeavetotal = res.total;
        }
      });

     
    },
    getdatetaday() {
      this.yesterdayStep = 1;
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
      this.init();
     // console.log(newdate);
    },
    getyesterday() {
      let day = new Date(this.taday);
     // console.log(this.yesterdayStep, "step");
      day.setTime(day.getTime() - 24 * 60 * 60 * 1000 );
      let year = day.getFullYear();
      let month = day.getMonth() + 1;
      let days = day.getDate();
      if (month < 10) {
        month = "0" + month;
      }
      if (days < 10) {
        days = "0" + days;
      }
      let s1 = year + "-" + month + "-" + days;
      this.taday = s1;
      this.init();
    },
    setheaderInfo() {
     // console.log(this.headerInfo);
      if (this.headerInfo.unFills != null) {
      } else {
        this.headerInfo.unFills = [];
      }
      if (this.headerInfo.fills != null) {
      } else {
        this.headerInfo.fills = [];
      }
      this.headerInfo.totalPeople =
        this.headerInfo.fills.length + this.headerInfo.unFills.length;
      this.headerInfo.fillLength = this.headerInfo.fills.length;
      this.headerInfo.totalHours = 0;
      this.headerInfo.fills.forEach(
        el => (this.headerInfo.totalHours += el.useHour)
      );
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
