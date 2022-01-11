<template>
  <div class="main">
    <!-- <h1>项目成本统计</h1> -->
    <el-card class="box-card" shadow="never">
      <div slot="header" class="clearfix">
        <span>
          <!-- <div class="headerTotal">
            总计：<span>{{ totalHour / 8 }}</span
            >人天 <span>{{ toatalCost }}元</span>
            <hr />
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
                  clearable
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
        </span>
        <hr />
      </div>
      <div class="text item">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column type="index" label="序号" width="60">
          </el-table-column>
          <el-table-column prop="costDate" label="时间" width="180">
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
          <el-table-column prop="nickName" label="人员" width="120">
          </el-table-column>
          <el-table-column prop="postName" label="岗位" width="180">
          </el-table-column>
          <el-table-column prop="useHour" label="工时" width="120">
          </el-table-column>
          <el-table-column prop="postCost" label="岗位成本" width="180">
          </el-table-column>
          <el-table-column prop="userCost" label="人员成本"> </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import {
  getCostQuery,
  getCostProjectPosts,
  getCostProjectUsers
} from "@/api/workingHour/costStatistics";
export default {
  data() {
    return {
      projectId: "",
      totalHour: 0,
      toatalCost: 0,
      showDay: true,
      day: [],
      phase: [],
      personnel: [],
      personnelvalue: "",
      position: [],
      positionvalue: "",
      value: "",
      // 显示搜索条件
      showSearch: true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: "",
        date: undefined,
        month: undefined,
        postId: undefined,
        projectStatus: undefined,
        userId: undefined
      },
      initqueryParams: { pageNum: 1, pageSize: 10, projectId: "" },
      tableData: []
    };
  },
  created() {
    this.projectId = this.$route.query.projectId;
    this.queryParams.projectId = this.$route.query.projectId;
    this.initqueryParams.projectId = this.$route.query.projectId;
    this.setDay();
    this.setPhase();
    this.init();
  },
  methods: {
    init() {
      getCostQuery(this.queryParams).then(res => {
       // console.log("getCostQuery", res);
        if (res.code == 200) {
          this.tableData = res.rows;
          this.totalHour = 0;
          this.toatalCost = 0;
          this.tableData.forEach(el => {
            if (el.useHour == null) {
              el.useHour = 0;
            }
            this.totalHour += el.useHour;
            this.toatalCost += el.userCost;
          });
        }
      });

      getCostProjectPosts(this.projectId).then(res => {
       // console.log("getCostProjectPosts", res);
        if (res.code == 200) {
          this.position = res.data;
        }
      });
      getCostProjectUsers(this.projectId).then(res => {
       // console.log("projectId", res);
        if (res.code == 200) {
          this.personnel = res.data;
        }
      });
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
    initForm() {
      // this.queryParams = {};
      this.queryParams = Object.assign(
        {},
        JSON.parse(JSON.stringify(this.queryParams))
      );
    },
    toSearch() {
      this.initForm();
     // console.log(this.queryParams);
      this.init();
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
    showpersonnel() {
      // this.initForm();
      this.queryParams.userId = this.personnelvalue;
     // console.log(this.queryParams);
      // this.init();
    },
    showposition() {
      // this.initForm();
      this.queryParams.postId = this.positionvalue;
     // console.log(this.queryParams);
      // this.init();
    },
    resetQuery(formName) {
      this.showDay=true
      this.resetForm(formName);
      // this.$refs[formName].resetFields();
      let form = {
        pageNum: 1,
        pageSize: 10,
        projectId: this.projectId,
        date: undefined,
        month: undefined,
        postId: undefined,
        projectStatus: undefined,
        userId: undefined
      };
      this.queryParams = form;

     // console.log(this.queryParams);
      this.init();
    }
  }
};
</script>

<style lang="scss" scoped>
.selectRows {
  // width: 70%;
  // display: flex;
  // justify-content: space-around;
  // align-items: center;
  // .el-input--medium {
  //   width: 17%;
  // }
  // .el-select--medium {
  //   width: 17%;
  // }
}
p {
  margin: 0;
}
</style>
