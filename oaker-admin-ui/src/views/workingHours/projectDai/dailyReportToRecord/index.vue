<template>
  <div class="main">
    <el-dialog
      title="每日上报记录"
      :visible.sync="DialogVisible"
      width="65%"
      @close="beforeClose"
    >
      <!-- <h1>{{ headersDate }}</h1> -->
      <el-row>
        <el-col :span="24"
          ><div class="grid-content bg-purple-dark">
            <el-date-picker
              v-model="queryDate"
              align="right"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
              :picker-options="pickerOptions"
              @change="setqueryDate"
            >
            </el-date-picker
            ><span class="btns">
              <el-button type="primary" @click="toQuery">查询</el-button>
              <el-button type="primary" @click="getYesterday">上一日</el-button>
              <el-button type="primary" @click="getToday">今日</el-button>
              <el-button type="primary" @click="getLastday">下一日</el-button>
              </span
            >
          </div></el-col
        >
      </el-row>
      <hr />
      <el-row>
        <el-col :span="24"
          ><div class="grid-content bg-purple-dark">
            <div class="headersTitle">{{ dailyInfo.projectName }}-项目日报</div>
          </div>
          <el-form ref="form" label-width="80px">
            <el-form-item
              ><label for="">时间：</label>{{ headersDate }}
              <span>
                <el-tag v-if="!dailyInfo.festivals" type="success"
                  >工作日</el-tag
                >
                <el-tag v-else type="danger">休息日</el-tag>
              </span>
            </el-form-item>
            <el-form-item>
            <div style="width:30%">
                <el-descriptions title="统计" :column="2">
                <el-descriptions-item label="上报："
                  >{{ fillsInfo.length }}人</el-descriptions-item
                >
                <el-descriptions-item label="投入："
                  >{{ totalHours }} 小时 -
                  {{
                    (totalHours / $store.state.user.appconfig.workTime).toFixed(
                      2
                    )
                  }}
                  人天</el-descriptions-item
                >
                <el-descriptions-item label="请假："
                  >{{ leaveNumber }}人</el-descriptions-item
                >
                <el-descriptions-item label="未上报：">
                  {{ unFillsInfo.length }}人
                </el-descriptions-item>
              </el-descriptions>
            </div>
            </el-form-item>
            <el-form-item>
              <el-descriptions title="记录" :column="1">
                <el-descriptions-item>
                  <el-table :data="tableData" stripe style="width: 100%">
                    <el-table-column type="index" label="序号" >
                    </el-table-column>
                    <el-table-column prop="nickName" label="人员" >
                    </el-table-column>
                    <el-table-column prop="postName" label="职位" >
                    </el-table-column>
                    <el-table-column prop="everyday" label="所属">
                      <template slot-scope="scope">
                        <div class="">
                          <el-tag v-if="scope.row.everyday">每日上报</el-tag>
                          <el-tag v-else type="success">临时上报</el-tag>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column prop="fillStatus" label="状态">
                      <template slot-scope="scope">
                        <div class="">
                          <span v-if="scope.row.fillStatus == 1">上报</span>
                          <span v-else-if="scope.row.fillStatus == 5"
                            >请假</span
                          >
                          <span v-else>调休</span>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column prop="useHour" label="工时">
                    </el-table-column>
                    <el-table-column prop="daily" label="工作内容" width="300">
                    </el-table-column>
                  </el-table>
                </el-descriptions-item>
              </el-descriptions>
            </el-form-item>
            <el-form-item>
              <el-descriptions title="未上报(每日上报)" :column="1">
                <el-descriptions-item>
                  <el-tag v-for="(item, index) in unFillsInfo" :key="index">{{
                    item.nickName
                  }}</el-tag>
                </el-descriptions-item>
              </el-descriptions>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { getProjectHourdailyCalendarDeatil } from "@/api/system/project";
export default {
  data() {
    return {
      DialogVisible: false,
      headersDate: "",
      projectId: null,
      lastdateval: 1,
      yesterdateval: 1,
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
      queryDate: "",
      tableData: [],
      dailyInfo: {},
      fillsInfo: [],
      unFillsInfo: []
    };
  },
  computed: {
    totalHours() {
      let totalHours = 0;
      this.fillsInfo.forEach(el => {
        totalHours += el.useHour;
      });
      return totalHours;
    },
    leaveNumber() {
      let leaveNumbers = 0;
      this.fillsInfo.forEach(el => {
        if (el.fillStatus == 5) {
          leaveNumbers++;
        }
      });
      return leaveNumbers;
    }
  },
  created() {
    // this.queryDate = this.$route.query.date;
    // this.headersDate = this.$route.query.date;
    // this.projectId = this.$route.query.id;
    // this.init();
  },
  methods: {
    open() {
      // this.queryDate = this.$route.query.date;
      // this.headersDate = this.$route.query.date;
      // this.projectId = this.$route.query.id;
      this.init();
      this.DialogVisible=true
    },
    init() {
      console.log(this.headersDate, this.projectId);
      getProjectHourdailyCalendarDeatil(this.setparams()).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.headersDate = this.queryDate;
          this.dailyInfo = res.data;
          this.fillsInfo = res.data.fills;
          this.unFillsInfo = res.data.unFills;
          this.tableData = this.fillsInfo;
        }
      });
    },
    setparams() {
      return {
        localDate: this.queryDate,
        projectId: this.projectId
      };
    },
    setqueryDate() {
      console.log(this.queryDate);
    },
    toQuery() {
      console.log("toQuery", this.setparams());

      this.init();
    },
    getToday() {
      console.log("getToday", this.getday(0));
      this.queryDate = this.getday(new Date(), 0);
      this.init();
    },
    getLastday() {
      if (new Date(this.queryDate) >= new Date()) {
        this.$message.error("未到时间，暂无数据");
      } else {
        console.log("getLastday");
        this.queryDate = this.getday(this.queryDate, this.yesterdateval);
        this.init();
      }
      // this.yesterdateval++
    },
    beforeClose() {},
    getYesterday() {
      console.log("getYesterday", this.getday(-this.lastdateval));
      this.queryDate = this.getday(this.queryDate, -this.lastdateval);
      this.init();
      // this.lastdateval++
    },
    getday(mydate, count) {
      // 获取今日，昨日，明日
      //count -1:昨天  0：今天   1：明天
      let dd = new Date(mydate);
      dd.setDate(dd.getDate() + count); //获取AddDayCount天后的日期
      let y = dd.getFullYear();
      let m = dd.getMonth() + 1; //获取当前月份的日期
      let d = dd.getDate();
      m = m < 10 ? "0" + m : m;
      d = d < 10 ? "0" + d : d;
      return y + "-" + m + "-" + d;
    }
  }
};
</script>

<style lang="scss" scoped>
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
hr {
  padding: 0.1px;
  background-color: gray;
}
.headersTitle {
  width: 100%;
  text-align: center;
  font-size: 16px;
  font-weight: 700;
  line-height: 52px;
}
.el-tag--medium {
  margin: auto 5px;
}
::v-deep .el-form-item__content{
  margin-left: 0 !important;
}
::v-deep .el-descriptions-item__label.has-colon::after {
  opacity: 0 !important;
}
::v-deep .el-dialog{
  margin-bottom: 15vh;
}
::v-deep .el-dialog__header{
  padding: 50px;
  padding-bottom: 0px;
}
::v-deep .el-dialog__body{
  padding: 30px 50px;
  padding-bottom: 30px !important;
}
::v-deep .el-form-item{
  margin-bottom: 0 !important;
}
</style>
