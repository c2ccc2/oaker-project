<template>
  <div class="main">
    <div class="infoOne">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>查看工时详情</span>
          <div style="float: right; padding: 3px 0" type="text">
            {{ $route.query.fillDate }}
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
            <el-input
              v-model.number="item.useHour"
              max="8"
              maxlength="1"
              placeholder="工时"
              style="width:60%"
              @input="lookinput(item.hour)"
            ></el-input
            >小时
          </el-form-item>
          <el-form-item label="总计">
            <el-input
              v-model.number="useHourTotal"
              :max="8"
              autocomplete="off"
              :disabled="true"
              style="width:60%"
            ></el-input
            >小时
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
          <el-button type="primary" round @click="handleDetailHour"
            >修改</el-button
          >
        </div>
      </el-card>
    </div>
    <div class="infoTwo"></div>
  </div>
</template>

<script>
import { getMyHourDetailt, updateHour } from "@/api/system/project";
export default {
  data() {
    return {
      projectList: [],
      id: null,
      useHourTotal: 0,
      hourId: ""
    };
  },
  created() {
    console.log(this.$route.query);
    this.id = this.$route.query.id;
    this.getmyhourdetail(this.id);
  },
  methods: {
    handleCannel() {
      this.$router.go(-1);
    },
    getmyhourdetail(id) {
      getMyHourDetailt(id).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.projectList = res.data;
          this.hourId = res.data[0].hourId;
          this.projectList.forEach(el => {
            this.useHourTotal += el.useHour;
          });
        }
      });
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
    handleDetailHour() {
      console.log("修改工时");
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
          projectId: el.projectId
        };
        data.projectHours.push(temp);
      });
      console.log(data);
      if (this.useHourTotal > 8) {
        alert("总计不可超过8小时");
      } else {
        // alert('用了工时'+ this.useHourTotal)
        updateHour(data).then(res => {
          console.log(res);
          if (res.code == 200) {
            this.$message.success(res.msg);
            _this.$router.go(-1);
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
  margin-bottom: 18px;
  display: flex;
  justify-content: space-around;
  align-items: center;
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
  width: 30%;
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
</style>
