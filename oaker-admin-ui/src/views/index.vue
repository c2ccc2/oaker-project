<template>
  <div class="app-container home">
    <!-- <h6>{{ usertime }},王小娟</h6> -->
    <!-- <h6>{{ usertime }},{{ username }}</h6>
    <hr /> -->
    <el-row>
      <el-col :span="17">
        <div class="title-header">
          <div class="left header-left">进行中的项目</div>
          <div class="right">全部项目</div>
        </div>
        <div class="grid-content bg-purple ">
          <el-row class="myprocard">
            <el-col
              :span="7"
              v-for="item in peojectcardInfo"
              :key="item.projectId"
              ><div class="grid-content bg-purple">
                <el-card class="box-card" shadow="hover">
                  <div slot="header" class="clearfix">
                    <b style="font-size:20px">{{ item.projectName }}</b>
                    <el-tag
                      v-if="item.cardStatus"
                      style="float: right;"
                      size="medium"
                      >正常</el-tag
                    >
                    <el-tag
                      v-else
                      style="float: right;"
                      type="danger"
                      size="medium"
                      >超出</el-tag
                    >
                  </div>

                  <div class="card-content">
                    <div class="left">
                      <div class="top">
                        <p>今日上报：</p>
                        <p
                          v-if="item.dayHour == '-1'"
                          style="width:100%;font-weight:600;font-size:16px;"
                        >
                          未上报
                        </p>
                        <p
                          v-else
                          style="width:100%;font-weight:600;font-size:16px;"
                        >
                          {{ item.dayHour }}工时
                        </p>
                      </div>
                      <div class="bottom">
                        <p>总投入：</p>
                        <p style="width:100%;font-weight:600;font-size:16px;">
                          {{ item.useHour }}小时({{
                            (item.useHour / 8).toFixed(2)
                          }}人天)
                        </p>
                      </div>
                    </div>
                    <div class="right">
                      <div class="top">
                        <p>昨日上报：</p>
                        <p
                          v-if="item.yesHour == '-1'"
                          style="width:100%;font-weight:600;font-size:16px;"
                        >
                          未上报
                        </p>
                        <p
                          v-else
                          style="width:100%;font-weight:600;font-size:16px;"
                        >
                          {{ item.yesHour }}工时
                        </p>
                      </div>
                      <!-- <div class="bottom">
                        <el-progress
                          type="circle"
                          :width="80"
                          :percentage="25"
                        ></el-progress>
                      </div> -->
                    </div>
                  </div>
                  <el-divider />
                  <div class="card-footer">
                    <div class="projectpeople">
                      <span>项目经理：{{ item.projectManagerName }}</span>
                      <!-- <span>4月13日22:22</span> -->
                    </div>
                  </div>
                </el-card>
              </div></el-col
            >
            <el-col :span="7"
              ><div class="grid-content bg-purple"></div
            ></el-col>
            <el-col :span="7"
              ><div class="grid-content bg-purple"></div
            ></el-col>
            <el-col :span="7"
              ><div class="grid-content bg-purple"></div
            ></el-col>
          </el-row>
        </div>
        <div class="grid-content bg-purple">
          <div class="Tips">
            <div class="header-title">
              <strong>使用说明</strong>
              <el-divider />
            </div>
            <div class="tipItem">
              <div class="position"><b>项目总监</b></div>
              <div class="process">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                  <el-breadcrumb-item>创建项目</el-breadcrumb-item>
                  <el-breadcrumb-item>设置项目经理</el-breadcrumb-item>
                  <el-breadcrumb-item>添加项目成员</el-breadcrumb-item>
                  <el-breadcrumb-item>查看工时统计</el-breadcrumb-item>
                  <el-breadcrumb-item>项目归档</el-breadcrumb-item>
                </el-breadcrumb>
              </div>
            </div>
            <div class="tipItem">
              <div class="position"><b>项目经理</b></div>
              <div class="process">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                  <el-breadcrumb-item>维护团队</el-breadcrumb-item>
                  <el-breadcrumb-item>核查工时</el-breadcrumb-item>
                  <el-breadcrumb-item>完成项目</el-breadcrumb-item>
                  <el-breadcrumb-item>项目归档</el-breadcrumb-item>
                </el-breadcrumb>
              </div>
            </div>
            <div class="tipItem">
              <div class="position"><b>开发人员</b></div>
              <div class="process">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                  <el-breadcrumb-item>填报日志</el-breadcrumb-item>
                  <el-breadcrumb-item>更新工时</el-breadcrumb-item>
                  <el-breadcrumb-item>更新工时</el-breadcrumb-item>
                </el-breadcrumb>
              </div>
            </div>
          </div>
        </div></el-col
      >
      <el-col :span="6"
        ><div class="grid-content bg-purple-light">
          <!-- <el-descriptions title="今日工时提交情况"> -->
          <!-- <el-descriptions-item label="今日提交">10人</el-descriptions-item>
            <el-descriptions-item label="未提交">5人</el-descriptions-item> -->
          <el-card class="box-card" shadow="hover" style="margin:0 auto 30px">
            <div slot="header" class="clearfix">
              <span style="font-weight:600">今日工时提交情况</span>
            </div>
            <div class="text item mycardbox">
              <p>今日：{{ IndexFillInfo.tagName }}</p>
              <!-- <p>
                今日：<el-tag :type="IndexFillInfo.subtype">{{
                  IndexFillInfo.tagName
                }}</el-tag>
              </p> -->
              <el-button
                @click="submitWorkingHours"
                size="mini"
                style="height:35px;float: right;font-size:14px"
                type="primary"
                :disabled="IndexFillInfo.dayFill != 1"
                >提交</el-button
              >
            </div>
            <div class="text item mycardbox">
              <p>
                本月：已提交{{ IndexFillInfo.monthFill }}次，缺报{{
                  IndexFillInfo.monthMissFill
                }}次
              </p>
              <!-- <p>
                本月：<el-tag
                  >已提交{{ IndexFillInfo.monthFill }}次，缺报{{
                    IndexFillInfo.monthMissFill
                  }}次</el-tag
                >
              </p> -->
              <el-button
                @click="checkWorkingHours"
                size="mini"
                style="height:35px;float: right;font-size:14px"
                type="primary"
                >查看</el-button
              >
            </div>
          </el-card>
          <!-- </el-descriptions> -->

          <div class="">
            <el-card class="box-card" shadow="hover">
              <div slot="header" class="clearfix">
                <span style="font-weight:600">最新动态</span>
              </div>
              <div class="text item">
                <el-table :data="IndexLoginUserInfo" style="width: 100%">
                  <el-table-column prop="loginDate" label="日期" align="center">
                  </el-table-column>
                  <el-table-column prop="nickName" label="姓名" align="center">
                    <template slot-scope="scope">
                      <p>{{ scope.row.nickName }}——登录系统</p>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </el-card>
          </div>
        </div></el-col
      >
    </el-row>
    <el-divider />
  </div>
</template>

<script>
import { getUserProfile } from "@/api/system/user";
import {
  getIndexProjectStat,
  getIndexFillInfo,
  getIndexLoginUser
} from "@/api/dashboard";
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.6.0",
      usertime: "上午好",
      username: "段铭锟",
      cardStatus: true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined,
        status: undefined
      },
      user: {},
      roleGroup: {},
      postGroup: {},
      peojectcardInfo: [],
      IndexFillInfo: {},
      IndexLoginUserInfo: []
    };
  },
  created() {
    this.getUser();
    this.init();
  },
  methods: {
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.roleGroup = response.roleGroup;
        this.postGroup = response.postGroup;
        if (this.postGroup === "董事长") {
          this.username = this.postGroup;
        } else {
          this.username = this.user.nickName;
        }
        let datetime = Date().slice(16, 18);
        if (datetime < 13) {
          this.usertime = "上午好";
        } else {
          this.usertime = "下午好";
        }
      });
    },
    init() {
      getIndexProjectStat().then(res => {
        console.log(res);
        if (res.code == 200) {
          this.peojectcardInfo = res.data;
          this.peojectcardInfo.forEach(el => {
            if (el.manHour < el.useHour) {
              el.cardStatus = false;
            } else {
              el.cardStatus = true;
            }
          });
          console.log("peojectcardInfo", this.peojectcardInfo);
        }
      });
      getIndexFillInfo().then(res => {
        console.log("getIndexFillInfo", res);
        if (res.code == 200) {
          this.IndexFillInfo = res.data;
          if (this.IndexFillInfo.dayFill == 1) {
            this.IndexFillInfo.subtype = "danger";
            this.IndexFillInfo.tagName = "未上报";
          } else if (this.IndexFillInfo.dayFill == 2) {
            this.IndexFillInfo.tagName = "已上报";
            this.IndexFillInfo.subtype = "success";
          } else if (this.IndexFillInfo.dayFill == 3) {
            this.IndexFillInfo.tagName = "无需上报";
            this.IndexFillInfo.subtype = "";
          } else {
            this.IndexFillInfo.tagName = "未知状态";
            this.IndexFillInfo.subtype = "warning";
          }
        }
      });
      getIndexLoginUser(this.queryParams).then(res => {
        console.log("getIndexLoginUser", res);
        if (res.code == 200) {
          this.IndexLoginUserInfo = res.rows;
          console.log(" this.IndexLoginUserInfo", this.IndexLoginUserInfo);
        }
      });
    },
    submitWorkingHours() {
      console.log("提交工时");
      this.$router.push({ path: "/workingHours/myWorkingHours" });
    },
    checkWorkingHours() {
      console.log("查看工时");
      this.$router.push({ path: "/workingHours/myStatistics" });
    },
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  font-size: 18px !important;
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;

    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}

// card
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}
.mycardbox {
  display: flex;
  justify-content: space-between;
  align-content: center;
}

// .clearfix:before,
// .clearfix:after {
//   display: table;
//   content: "";
// }
.clearfix:after {
  clear: both;
}
.el-row::before,
.el-row::after {
  display: none;
}

.box-card {
  width: 100%;
  ::v-deep .el-card__body {
    padding: 10px;
  }
  .card-content {
    display: flex;
    justify-content: space-between;
    flex-wrap: nowrap;
    .left {
      width: 55%;
    }
    .header-left {
      font-size: 16px;
      font-weight: 800;
    }
    .right {
      width: 45%;
      .top {
        text-align: center;
      }
      .bottom {
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }
  .card-footer {
    font-size: 16px;
    .projectpeople {
      display: flex;
      justify-content: space-between;
      white-space: nowrap;
    }
  }
}
// cardover
// el-row
.el-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  // border-radius: 4px;
  margin-bottom: 30px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  // background: #d3dce6;
}
.bg-purple-light {
  // background: #e5e9f2;
  margin-top: 56px;
  height: 100%;
  ::v-deep .el-descriptions-row {
    display: flex;
    justify-content: space-between;
  }
}
.grid-content {
  // border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
// el-row over
h6 {
  font-weight: 600;
}
.title-header {
  width: 100%;
  height: 56px;
  margin: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .right {
    color: black;
    font-weight: 600;
  }
}
.tipItem {
  padding: 8px;
  .position {
    font-size: 18px;
    padding: 8px;
    padding-left: 0px;
  }
}
.myprocard {
  // padding: 0 20px;
  display: flex;
  // justify-content: space-between !important;
  align-items: center;
  flex-wrap: wrap;
}
</style>
