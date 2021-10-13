<template>
  <div class="main">
    <div class="infoOne">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>填报工时</span>
          <div style="float: right; padding: 3px 0" type="text">
            <el-date-picker
              v-model="value2"
              align="right"
              type="date"
              placeholder="选择日期"
              :picker-options="pickerOptions"
            >
            </el-date-picker>
          </div>
        </div>
        <div
          v-for="(item, index) in projectList"
          :key="index"
          class="text item"
        >
          <span>
            {{ item.name }}
          </span>
          <span>
            <!-- {{ item.time }} -->
            <el-input placeholder="请输入内容" v-model="item.time" clearable>
            </el-input>
          </span>
          <span>
            {{ "小时" }}
          </span>
        </div>
        <div class="comeTo item">
          <span>
            总 计
          </span>
          <span>
            <el-input
              placeholder="请输入内容"
              :disabled="true"
              v-model="totalTime"
              clearable
            >
            </el-input>
          </span>
          <span>
            {{ "小时" }}
          </span>
        </div>
        <div class="footer-btn item">
          <el-button round @click="handleCannel">取消</el-button>
          <el-button type="primary" round>提交</el-button>
        </div>
      </el-card>
    </div>
    <div class="infoTwo"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      projectList: [
        { name: "项目A", time: 2 },
        { name: "项目B", time: 4 },
        { name: "项目C", time: 2 }
      ],
      totalTime: null,
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
      value2: ""
    };
  },
  created() {
    this.gettotalTime();
  },
  methods: {
    gettotalTime() {
      this.projectList.forEach(el => {
        this.totalTime += el.time;
      });
    },
    handleCannel() {
      this.$router.go(-1);
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
  width: 60%;
  margin: 0 auto;
}
</style>
