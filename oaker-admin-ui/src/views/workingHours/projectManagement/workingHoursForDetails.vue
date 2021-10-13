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
          <el-row>
            <el-button type="primary" @click="init">查询</el-button>
            <el-button type="primary" @click="getdatetaday">今日</el-button>
            <el-button type="primary" @click="getyesterday">上一日</el-button>
          </el-row>
        </div>
      </div>
      <div class="titleInfo">
        <el-descriptions title="统计：">
          <el-descriptions-item label="上报人数"
            >{{ headerInfo.fillLength }}/{{
              headerInfo.totalPeople
            }}</el-descriptions-item
          >
          <el-descriptions-item label="工时"
            >{{ headerInfo.totalHours }} 工时</el-descriptions-item
          >
          <el-descriptions-item label="累计投入"
            >{{ (totalHour/8).toFixed(2) }} 人天 ({{
              totalHour
            }}
            工时）</el-descriptions-item
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
              <el-table-column type="index" label="序号" width="180">
              </el-table-column>
              <el-table-column prop="nickName" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="postName" label="职位" width="180">
              </el-table-column>
              <el-table-column prop="useHour" label="工时" width="180">
                <template slot-scope="scope">
                  <p>{{ scope.row.useHour }}小时</p>
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="上报时间">
              </el-table-column>
            </el-table>
          </template>
        </div>
      </div>
      <div class="">
        <div class="tipTitle">
          未上报
        </div>
        <div class="">
          <template>
            <el-table :data="tableDataunFills" style="width: 50%">
              <el-table-column type="index" label="序号" width="180">
              </el-table-column>
              <el-table-column prop="nickName" label="姓名"> </el-table-column>
              <el-table-column prop="postName" label="职位"> </el-table-column>
              <!-- <el-table-column prop="workingHours" label="工时" width="180">
              </el-table-column> -->
              <!-- <el-table-column prop="reportTime" label="上报时间"> </el-table-column> -->
            </el-table>
          </template>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { projectHourStatFillDetail } from "@/api/system/project";
export default {
  data() {
    return {
      date: "今日",
      yesterdayStep:1,
      tableDatafills: [],
      tableDataunFills: [],
      headerInfo: {},
      projectId: "",
      totalHour: "",
      taday: "",
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
    this.totalHour = this.$route.query.totalHour;
    console.log(this.projectId);
    this.getdatetaday();
    this.init();
  },
  methods: {
    init() {
      console.log(this.taday);
      let params = {
        date: this.taday,
        projectId: this.projectId
      };
      console.log(params);
      projectHourStatFillDetail(params).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.headerInfo = res.data;
          this.tableDatafills = res.data.fills;
          this.tableDataunFills = res.data.unFills;

          if (this.tableDatafills != null) {
            this.tableDatafills.forEach(el => {
              // el.createTime = el.createTime.slice(0, 19);
              // el.createTime = el.createTime.replace(/T/g, " ");
            });
            if (this.tableDataunFills != null) {
              this.tableDataunFills.forEach(el => {
                // el.createTime = el.createTime.slice(0, 19);
                // el.createTime = el.createTime.replace(/T/g, " ");
              });
            } else {
              this.tableDataunFills = [];
            }
          }else{
              this.tableDatafills = [];

          }
          this.setheaderInfo();
        }
      });
    },
    getdatetaday() {
      this.yesterdayStep=1;
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
      console.log(newdate);
    },
    getyesterday() {
      let day = new Date();
      console.log(this.yesterdayStep,'step')
      day.setTime(day.getTime() - 24 * 60 * 60 * 1000*this.yesterdayStep);
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
      this.yesterdayStep++;
      this.init();
    },
    setheaderInfo() {
      console.log(this.headerInfo);
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
  width: 30%;
  line-height: 56px;
  display: flex;
  justify-content: space-between;
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
</style>
