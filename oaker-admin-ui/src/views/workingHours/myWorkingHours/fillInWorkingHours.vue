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
            :clearable="false"
            :editable="false"
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
          :disabled="selectDatePicker"
          @click="getMonth"
          >查询</el-button
        >
        <el-button type="primary" @click="getToday">今日</el-button>
      </div>
      <div class="text item">
        <el-descriptions title="统计" :column="6">
          <el-descriptions-item label="上报天数"
            >{{ fillNum }}天</el-descriptions-item
          >
          <el-descriptions-item label="应报"
            >{{ mustFillNum }}天（含加班）</el-descriptions-item
          >
          <el-descriptions-item label="缺报"
            >{{ missFillNum }}天</el-descriptions-item
          >
          <el-descriptions-item label="请假"
            >{{ leaveNum }}天</el-descriptions-item
          >
          <el-descriptions-item label="调休"
            >{{ paidLeave }}天</el-descriptions-item
          >
          <el-descriptions-item label="总计"
            >{{
              (totalHour / $store.state.user.appconfig.workTime).toFixed(2)
            }}人天（{{ totalHour }}工时）</el-descriptions-item
          >
        </el-descriptions>
      </div>
      <div class="text item">
        <el-button-group>
          <el-button
            type="primary"
            plain
            @click="showdetail"
            v-hasPermi="['mh:hour:stat:detail']"
            >日历模式</el-button
          >

          <el-button
            type="primary"
            plain
            @click="showStateProject"
            v-hasPermi="['mh:hour:stat']"
            >按项目统计</el-button
          >
          <el-button
            type="primary"
            plain
            @click="showstreamline"
            v-hasPermi="['mh:hour:stat']"
            >表格模式</el-button
          >
          <el-button
            type="primary"
            plain
            @click="ExportStat"
            v-hasPermi="['mh:hour:stat']"
            >导出本月记录</el-button
          >
        </el-button-group>
      </div>
      <div class="text item">
        <div v-if="showStreamline" class="streamline">
          <template>
            <el-table :data="projectList" border style="width: 100%">
              <el-table-column type="index" label="序号" align="center">
              </el-table-column>
              <el-table-column prop="date" label="日期" align="center">
              </el-table-column>
              <!-- <el-table-column prop="nickName" label="人员" align="center">
              </el-table-column> -->
              <!-- <el-table-column prop="postName" label="岗位" align="center">
              </el-table-column> -->
              <el-table-column prop="status" label="状态" align="center">
                <template slot-scope="scope">
                  <div>
                    <el-tag v-if="scope.row.status == 1"
                      ><i class="el-icon-check"></i> 已填报</el-tag
                    >
                    <el-tag type="danger" v-else-if="scope.row.status == 2"
                      ><i class="el-icon-close"></i> 缺报</el-tag
                    >
                    <el-tag type="info" v-else-if="scope.row.status == 3"
                      ><i class="el-icon-minus"></i> 无需填报</el-tag
                    >
                    <el-tag type="success" v-else-if="scope.row.status == 4"
                      ><i class="el-icon-magic-stick"></i> 节假日</el-tag
                    >
                    <el-tag type="warning" v-else-if="scope.row.status == 5"
                      ><i class="el-icon-close-notification"></i> 请假</el-tag
                    >
                    <el-tag type="warning" v-else-if="scope.row.status == 6"
                      ><i class="el-icon-refresh"></i> 调休</el-tag
                    >
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                prop="useHour"
                label="项目/工时/日志"
                width="400"
                align="center"
              >
                <template slot-scope="scope" class="table-item">
                  <div>
                    <el-table
                      :data="scope.row.projectHours"
                      style="width: 100%"
                      :border="false"
                      :show-header="false"
                    >
                      <el-table-column
                        prop="projectName"
                        label="项目"
                        align="center"
                      >
                      </el-table-column>
                      <el-table-column
                        prop="useHour"
                        label="工时"
                        align="center"
                      >
                        <template slot-scope="scope">
                          <span>{{ scope.row.useHour }}小时</span>
                        </template>
                      </el-table-column>
                      <el-table-column prop="daily" label="日志" align="center">
                      </el-table-column>
                    </el-table>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                prop="fillTime"
                label="提交时间"
                width="150"
                align="center"
              >
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <el-button
                    v-if="scope.row.id"
                    @click="handleClick(scope.row, scope.$index)"
                    type="text"
                    size="small"
                    >查看详情</el-button
                  >
                </template>
              </el-table-column>
              <!-- <el-table-column prop="address" label="地址"> </el-table-column> -->
            </el-table>
          </template>
        </div>
        <div class="detail" v-else>
          <template>
            <Datepicker
              :options="calendarArr"
              class="calendar"
              ref="Datepicker"
              @handleClickDay="handleClickDay"
              @handlePrevMonth="handlePrevMonth"
              @handleNextMonth="handleNextMonth"
              @handleGoMyMonth="handleGoMyMonth"
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

    <!-- <template>
      <div>
        <vxe-toolbar ref="xToolbar" print></vxe-toolbar>
        <vxe-table
          border
          resizable
          show-footer
          ref="xTable"
          align="center"
          :print-config="{}"
          :merge-cells="mergeCells"
          :data="detailInfo"
        >
          <vxe-column type="seq" title="序号"></vxe-column>
          <vxe-column field="nickName" title="人员"> </vxe-column>
          <vxe-column field="postName" title="岗位"> </vxe-column>
          <vxe-column field="fillDate" title="日期"> </vxe-column>
          <vxe-column field="projectName" title="项目"> </vxe-column>
          <vxe-column
            field="everyday"
            title="项目所属工作组"
            :formatter="formatterEvery"
          >
          </vxe-column>
          <vxe-column field="useHour" title="工时"> </vxe-column>
          <vxe-column field="createTime" title="提交时间"> </vxe-column>
        </vxe-table>
      </div>
    </template> -->
    <el-dialog
      title="项目工时详情"
      :visible.sync="projectDialog"
      width="30%"
      :before-close="handleClose"
    >
      <template>
        <el-table :data="StatProject" style="width: 100%">
          <el-table-column type="index" label="序号" align="center">
          </el-table-column>
          <el-table-column prop="projectName" label="项目名称" align="center">
          </el-table-column>
          <el-table-column prop="useHour" label="工时" align="center">
          </el-table-column>

          <!-- <el-table-column prop="address" label="地址"> </el-table-column> -->
        </el-table>
      </template>
      <span slot="footer" class="dialog-footer">
        <el-button @click="projectDialog = false">取 消</el-button>
        <el-button type="primary" @click="projectDialog = false"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <SelectPeoject ref="SelectPeoject" />
    <workingInfoForm ref="workingInfoForm"></workingInfoForm>
  </div>
</template>

<script>
import {
  getHourStat,
  getHourStatDetail,
  getStatExport,
  getProjectHourCalendar,
  getStatProject
} from "@/api/system/project";
import SelectPeoject from "./selectPeoject.vue";
import workingInfoForm from "./workingInfo";

export default {
  components: {
    SelectPeoject,
    workingInfoForm
  },
  data() {
    return {
      projectDialog: false,
      selectDatePicker: false,
      showEvents: true,
      dataMonth: "",
      dataMonthtemp: "",
      showStreamline: false,
      datetayMonth: "",
      stat: [],
      // peopleday: "",
      projectList: [],
      Calendar: new Date(),
      detailInfo: [],
      demoEvents: [],
      showEvemts: false,
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
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
        showcontent: 1
      },
      StatProject: [],
      tableData: [],
      mergeCells: [],
      mergeFooterItems: [],
      EveryList: [
        {
          label: "临时上报",
          value: false
        },
        {
          label: "每日上报",
          value: true
        }
      ],
      onQuery: true
    };
  },
  created() {
    // this.$nextTick(() => {
    //   // 手动将表格和工具栏进行关联
    //   this.$refs.xTable.connect(this.$refs.xToolbar);
    // });
    // this.loadList();
    // console.log(object)
    this.gettayMonth();
    this.showdetail();
    this.init();
  },
  computed: {
    fillNum() {
      return this.stat.filter(item => item.status == 1).length;
    },
    mustFillNum() {
      return this.stat.filter(item => item.status != 4).length;
    },
    missFillNum() {
      return this.stat.filter(item => item.status == 2).length;
    },
    leaveNum() {
      return this.stat.filter(item => item.status == 5).length;
    },
    paidLeave() {
      return this.stat.filter(item => item.status == 6).length;
    },
    totalHour() {
      let totalHours = 0;
      this.stat.forEach(el => {
        if (el.projectHours != null) {
          el.projectHours.forEach(dl => (totalHours += dl.useHour));
        }
      });
      return totalHours;
    }
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
      getProjectHourCalendar(date).then(res => {
        console.log("getProjectHourCalendar", res);
        if (res.code == 200) {
          this.calendarArr.hourDetail = res.data;
          console.log("日历数据", this.calendarArr.hourDetail);
        }
      });
      getHourStat(date).then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.stat = res.data;
          this.projectList = res.data;
          // this.peopleday = res.data.projectHours.length;
          console.log(this.stat);
        }
      });
      getStatProject(date).then(res => {
        console.log("getStatProject", res);
        if (res.code == 200) {
          this.StatProject = res.data;
        }
      });
    },
    getHourDetail() {
      this.queryParams.date = this.datetayMonth;
      getHourStatDetail(this.queryParams).then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.detailInfo = res.rows;
          this.total = res.total;
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
      console.log(time);
      if (time == null) {
        this.$$message.error("必须选择日期");
        this.selectDatePicker = true;
      } else {
        this.selectDatePicker = false;
      }
      this.dataMonth = time;
      this.dataMonthtemp = Object.assign("", JSON.parse(JSON.stringify(time)));
    },
    getToday() {
      console.log("getToday");
      this.gettayMonth();
      let todate = this.dataMonth.replace(/-/g, "/");
      //  this.$EventCalendar.toDate(todate);
      this.$refs.Datepicker.handleToday();
      this.init();
    },
    getMonth() {
      // this.init()
      // console.log(this.dataMonth);
      if (this.dataMonth) {
        console.log("有", this.dataMonth);
        let todate = this.dataMonth.replace(/-/g, "/");
        this.$EventCalendar.toDate(todate);
        // this.handleGoMyMonth();
        if (this.onQuery) {
          this.$refs.Datepicker.handleGoMyMonth(this.dataMonth);
        }
        // this.handleMerge()
      } else {
        console.log("没有");
        this.dataMonth = this.dataMonthtemp;
        alert("已显示当前月");
        return 0;
      }
      // let date = this.dataMonth;
      this.init();
    },
    showStateProject() {
      this.showStreamline = false;
      this.onQuery = false;
      this.projectDialog = true;
    },
    showstreamline() {
      this.showStreamline = true;
      this.onQuery = false;
    },
    showdetail() {
      this.showStreamline = false;
      this.onQuery = true;
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    loadList() {
      const list = [];
      for (let index = 0; index < 15; index++) {
        list.push({
          a: "a" + index,
          b: "b" + index,
          c: "c" + index,
          d: "d" + index,
          e: "e" + index,
          f: "f" + index,
          g: "g" + index,
          h: "h" + index,
          i: "i" + index,
          j: "j" + index,
          k: "k" + index,
          l: "l" + index,
          m: "m" + index,
          n: "n" + index,
          o: "o" + index,
          p: "p" + index,
          q: "q" + index,
          r: "r" + index,
          s: "s" + index,
          t: "t" + index,
          u: "u" + index,
          v: "v" + index,
          w: "w" + index
        });
      }
      this.tableData = list;
      this.handleMerge();
    },
    handleMerge() {
      console.log("根据行数据计算合并规则");
      // 根据行数据计算合并规则
      const mergeCells = [
        { row: 0, col: 1, rowspan: 2, colspan: 1 },
        { row: 0, col: 2, rowspan: 2, colspan: 1 },
        { row: 0, col: 3, rowspan: 2, colspan: 1 },
        { row: 0, col: 7, rowspan: 2, colspan: 1 },
        { row: 2, col: 1, rowspan: 2, colspan: 1 },
        { row: 2, col: 2, rowspan: 2, colspan: 1 },
        { row: 2, col: 3, rowspan: 2, colspan: 1 },
        { row: 2, col: 7, rowspan: 2, colspan: 1 },
        { row: 4, col: 1, rowspan: 2, colspan: 1 },
        { row: 4, col: 2, rowspan: 2, colspan: 1 },
        { row: 4, col: 3, rowspan: 2, colspan: 1 },
        { row: 4, col: 7, rowspan: 2, colspan: 1 }
        // { row: 0, col: 8, rowspan: 2, colspan: 1 },
        // { row: 0, col: 9, rowspan: 2, colspan: 1 },
        // { row: 0, col: 15, rowspan: 10, colspan: 1 },
        // { row: 0, col: 16, rowspan: 10, colspan: 1 },
        // { row: 0, col: 18, rowspan: 2, colspan: 1 },
        // { row: 0, col: 19, rowspan: 2, colspan: 1 },
        // { row: 0, col: 20, rowspan: 2, colspan: 1 },
        // { row: 0, col: 21, rowspan: 2, colspan: 1 },
        // { row: 1, col: 11, rowspan: 1, colspan: 4 },
        // { row: 2, col: 0, rowspan: 4, colspan: 2 },
        // { row: 2, col: 2, rowspan: 4, colspan: 1 },
        // { row: 2, col: 3, rowspan: 4, colspan: 1 },
        // { row: 2, col: 6, rowspan: 4, colspan: 1 },
        // { row: 2, col: 7, rowspan: 4, colspan: 1 },
        // { row: 2, col: 8, rowspan: 4, colspan: 1 },
        // { row: 2, col: 9, rowspan: 4, colspan: 1 },
        // { row: 2, col: 17, rowspan: 4, colspan: 1 },
        // { row: 2, col: 18, rowspan: 3, colspan: 1 },
        // { row: 2, col: 19, rowspan: 3, colspan: 1 },
        // { row: 2, col: 20, rowspan: 3, colspan: 2 },
        // { row: 3, col: 4, rowspan: 2, colspan: 2 },
        // { row: 4, col: 11, rowspan: 2, colspan: 4 },
        // { row: 5, col: 18, rowspan: 1, colspan: 4 },
        // { row: 6, col: 0, rowspan: 3, colspan: 1 },
        // { row: 6, col: 1, rowspan: 3, colspan: 1 },
        // { row: 6, col: 4, rowspan: 3, colspan: 1 },
        // { row: 6, col: 5, rowspan: 3, colspan: 1 },
        // { row: 6, col: 6, rowspan: 3, colspan: 1 },
        // { row: 6, col: 7, rowspan: 3, colspan: 1 },
        // { row: 6, col: 8, rowspan: 3, colspan: 1 },
        // { row: 6, col: 9, rowspan: 3, colspan: 1 },
        // { row: 6, col: 18, rowspan: 3, colspan: 1 },
        // { row: 6, col: 19, rowspan: 3, colspan: 1 },
        // { row: 6, col: 20, rowspan: 3, colspan: 1 },
        // { row: 6, col: 21, rowspan: 3, colspan: 1 },
        // { row: 6, col: 2, rowspan: 1, colspan: 2 },
        // { row: 8, col: 2, rowspan: 1, colspan: 2 },
        // { row: 9, col: 0, rowspan: 1, colspan: 15 },
        // { row: 11, col: 5, rowspan: 4, colspan: 12 }
      ];
      // 根据行数据计算表尾合并规则
      const mergeFooterItems = [
        // { row: 0, col: 1, rowspan: 1, colspan: 2 },
        // { row: 0, col: 6, rowspan: 1, colspan: 2 },
        // { row: 0, col: 14, rowspan: 2, colspan: 5 },
        // { row: 1, col: 4, rowspan: 1, colspan: 8 }
      ];
      this.mergeCells = mergeCells;
      this.mergeFooterItems = mergeFooterItems;
    },
    formatterEvery({ cellValue }) {
      let item = this.EveryList.find(item => item.value === cellValue);
      return item ? item.label : "";
    },
    footerMethod({ columns }) {
      return [
        columns.map((column, index) => index),
        columns.map((column, index) => 1000 + index)
      ];
    },
    handleClickDay(row) {
      console.log("handleClickDay");
      console.log("1111", row);
      if (!row.showday) {
        return 0;
      }
      let id = null;
      row.projectData.forEach(el => {
        id = el.id;
      });
      if (row.mystatus == 5 || row.mystatus == 6) {
        this.$message.warning("已请假/调休，无记录");
        return 0;
      }
      if (row.status == 2) {
        this.$message.warning("未填报，无记录");
      } else {
        this.$refs.workingInfoForm.id = id;
        this.$refs.workingInfoForm.fillDate = row.mydate;
        this.$refs.workingInfoForm.open();
        // this.$router.push({
        //   path: "/workingHours/workingInfo",
        //   query: { id, fillDate: row.mydate }
        // });
      }
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
    handleClick(row, index) {
      console.log("handleClickDay", index);
      console.log("data", row);
      this.$refs.workingInfoForm.id = row.id;
      this.$refs.workingInfoForm.fillDate = row.mydate;
      this.$refs.workingInfoForm.open();
      // this.$router.push({
      //   path: "/workingHours/workingInfo",
      //   query: { id: row.id, date: row.date }
      // });
    },
    ExportStat() {
      this.$refs.SelectPeoject.localDate = this.datetayMonth;
      this.$refs.SelectPeoject.open();
      // let params = {};
      // params.localDate = this.datetayMonth;
      // params.projectId = 32;
      // getStatExport(params).then(res => {
      //   console.log(res);
      // });
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
  width: 65%;
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
::v-deep .el-table .cell {
  padding-right: 0 !important;
  padding-left: 0 !important;
}
::v-deep .el-table--medium th,
.el-table--medium td {
  padding: 0 !important;
}
::v-deep .el-table__row td{
  padding: 0 !important;
  line-height: 32px !important;
}
::v-deep .el-table__row .cell{
  line-height: 32px !important;
}
</style>
