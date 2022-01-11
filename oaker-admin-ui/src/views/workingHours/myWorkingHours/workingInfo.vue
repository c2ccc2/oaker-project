<template>
  <div class="main">
    <el-dialog
      title="工时详情"
      :visible.sync="centerDialogVisible"
      width="46%"
      @close="beforeClose"
    >
      <div class="infoOne">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span>查看工时详情</span>
            <div style="float: right; padding: 3px 0" type="text">
              {{ fillDate }}
            </div>
          </div>
          <!-- <div
          v-for="(item, index) in projectList"
          :key="index"
          class="text item comeTo"
        >
          <span class="tableOne">
            {{ item.projectName }}
          </span>
          <span class="tabletwo">
            <el-input
              v-model.number="item.useHour"
              :max="8"
              autocomplete="off"
            ></el-input>
          </span>
          <span class="tablethree">
            {{ "小时" }}
          </span>
        </div> -->
          <el-form :inline="false" label-width="120px">
            <el-form-item
              v-for="(item, index) in projectList"
              :key="index"
              :label="item.projectName"
              prop="item.projectName"
            >
              <div
                style="display: flex;
    justify-content: flex-end;"
              >
                <el-input
                  v-model.number="item.useHour"
                  type="number"
                  :max="24"
                  maxlength="2"
                  placeholder="工时"
                  style="width:20%"
                  @input="lookinput(item.useHour)"
                ></el-input
                ><span style="margin:0 10px">小时</span>
              </div>
              <hr />
              <el-input
                style="margin-top:5px;width:100%"
                v-model="item.daily"
                type="textarea"
                :rows="2"
                maxlength="200"
                show-word-limit
                :autosize="{ minRows: 2, maxRows: 3 }"
                placeholder="请填写日志"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="总计">
              <div
                style="display: flex;
    justify-content: flex-end;"
              >
                <el-input
                  v-model.number="useHourTotal"
                  :max="24"
                  autocomplete="off"
                  :disabled="true"
                  style="width:20%"
                ></el-input
                ><span style="margin:0 10px">小时</span>
              </div>
            </el-form-item>
            <el-form-item label="提示:">
              <p class="tips">{{ submitTips }}</p>
            </el-form-item>
          </el-form>
          <!-- <div class="text comeTo item">
          <span class="tableOne">
            总计
          </span>
          <span class="tabletwo">
            <el-input
              v-model.number="useHourTotal"
              :max="8"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </span>
          <span class="tablethree">
            {{ "小时" }}
          </span>
        </div> -->
          <div class="footer-btn item">
            <el-button round @click="handleCannel">返回</el-button>
            <el-button
              :disabled="sunmitflag"
              type="primary"
              round
              @click="handleDetailHour"
              >修改</el-button
            >
          </div>
        </el-card>
      </div>
      <div class="infoTwo"></div>
    </el-dialog>
  </div>
</template>

<script>
import { getMyHourDetailt, updateHour } from "@/api/system/project";
import { geApptConfig } from "@/api/manage/appsSett";

export default {
  data() {
    return {
      centerDialogVisible: false,
      projectList: [],
      id: null,
      fillDate: null,
      settiingsdata: {},
      useHourTotal: 0,
      hourId: 0,
      sunmitflag: false,
      submitTips: ""
    };
  },
  created() {
    // console.log(this.$route.query);

    // this.id = this.$route.query.id;
    this.setTings();
    // this.getmyhourdetail(this.id);
  },
  methods: {
    open() {
      if (this.id == null) {
        this.$message.error("未填报，请重新选择");
        // this.$router.go(-1);
        this.centerDialogVisible = false;
        return;
      }
      console.log(this.id);
      this.getmyhourdetail(this.id);
      this.centerDialogVisible = true;
    },
    setTings() {
      geApptConfig().then(res => {
        // console.log("settings", res);
        if (res.code == 200) {
          this.settiingsdata = res.data;
        }
      });
    },
    beforeClose() {
      this.useHourTotal = 0;
    },
    handleCannel() {
      this.useHourTotal = 0;
      this.projectList = [];
      this.centerDialogVisible = false;
      // this.$router.go(-1);
    },
    getmyhourdetail(id) {
      getMyHourDetailt(id).then(res => {
        console.log("getMyHourDetailt", res);
        if (res.code == 200) {
          this.submitTips = "可以提交";
          if (res.data == []) {
            this.hourId = 0;
          } else {
            this.hourId = res.data[0].hourId;
          }
          this.projectList = res.data;
          this.projectList.forEach(el => {
            this.useHourTotal += el.useHour;
          });
        }
        if (res.code == 500) return;
      });
    },
    lookinput(value) {
      let hoursum = 0;
      this.projectList.forEach(el => (hoursum += el.useHour + 0));
      if (value === "") {
        console.log(value);
        this.sunmitflag = true;
        this.submitTips = "工时不能为空";
      } else if (value === 0 && hoursum <= this.settiingsdata.workTime) {
        this.submitTips = "可以提交!";
        this.sunmitflag = false;
      } else {
        console.log("到了", hoursum);
        if (this.settiingsdata.overtimeAllow && hoursum <= 24) {
          this.sunmitflag = false;
          this.submitTips = "可以提交!（加班）";
        } else {
          if (hoursum > 24) {
            this.sunmitflag = true;
            this.submitTips = `工时最大范围为0-24小时!`;
          } else if (hoursum > this.settiingsdata.workTime) {
            this.sunmitflag = true;
            this.submitTips = `工时范围为0-${this.settiingsdata.workTime}小时!`;
          } else if (hoursum <= this.settiingsdata.workTime) {
            this.sunmitflag = false;
            this.submitTips = "可以提交！";
          }
        }
      }
    },
    handleDetailHour() {
      // console.log("修改工时");
      let _this = this;

      let data = {
        hourId: this.hourId,
        projectHours: []
      };
      this.useHourTotal = 0;
      this.projectList.forEach(el => {
        this.useHourTotal += el.useHour;
        let temp = {
          detailId: el.id,
          hour: el.useHour,
          projectId: el.projectId,
          daily: el.daily
        };
        data.projectHours.push(temp);
      });
      // console.log(data);
      if (this.settiingsdata.overtimeAllow) {
        updateHour(data).then(res => {
          // console.log(res);
          if (res.code == 200) {
            this.$message.success(res.msg);
            // _this.$router.go(-1);
            this.centerDialogVisible = false;
            // this.$router.push({path:"/workingHours/myWorkingHours"})
          } else {
            this.$message.danger(res.msg);
          }
        });
      } else if (this.useHourTotal > this.settiingsdata.workTime) {
        alert(`总计不可超过${this.settiingsdata.workTime}小时`);
      } else {
        // alert('用了工时'+ this.useHourTotal)
        updateHour(data).then(res => {
          // console.log(res);
          if (res.code == 200) {
            this.$message.success(res.msg);
            // _this.$router.go(-1);
            this.useHourTotal = 0;
            this.centerDialogVisible = false;

            // this.$router.push({path:"/workingHours/myWorkingHours"})
          } else {
            this.$message.danger(res.msg);
          }
        });
      }
      // updateHour(data).then(res => {
      //   console.log(res);
      //   if (res.code == 200) {
      //     this.$message.success(res.msg);
      //   } else {
      //     this.$message.danger(res.msg);
      //   }
      // });
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
.text {
  font-size: 14px;
}

.item {
  // margin-bottom: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.footer-btn.item {
  margin-bottom: 0;
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
  margin: 0 auto;
}
.tableOne {
  width: 30%;
}
.tabletwo {
  width: 30%;
}
.tablethree {
  width: 30%;
}
.tips {
  // text-align: center;
  color: red;
  font-size: 12px;
  margin: 0 auto;
}
::v-deep .el-dialog {
  margin-bottom: 15vh;
}
::v-deep .el-dialog__body {
  padding-bottom: 0 !important;
}
::v-deep .el-form-item {
  margin-bottom: 28px;
}
</style>
