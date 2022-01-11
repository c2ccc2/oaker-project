<template>
  <div class="app-container home">
    <!-- <h6>{{ usertime }},王小娟</h6> -->
    <!-- <h6>{{ usertime }},{{ username }}</h6>
    <hr /> -->
    <el-row>
      <el-col :span="15">
        <div class="title-header">
          <div class="left header-left">进行中的项目</div>
          <div class="right">全部项目</div>
        </div>
        <div class="grid-content ">
          <el-row class="myprocard">
            <el-col
              :span="7"
              v-for="item in peojectcardInfo"
              :key="item.projectId"
              ><div class="grid-content ">
                <el-card class="box-card" shadow="hover">
                  <div slot="header" class="clearfix">
                    <b style="font-size:16px">{{ item.projectName }}</b>
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
                        <p style="width:100%;font-weight:600;font-size:14px;">
                          总投入：
                        </p>
                        <p style="width:100%;font-weight:600;font-size:12px;">
                          {{ item.useHour }}小时({{
                            (item.useHour / workTime).toFixed(2)
                          }}人天)
                        </p>
                      </div>
                    </div>
                    <!-- <div class="right">
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
                    </div> -->
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
            <el-col :span="7"><div class="grid-content "></div></el-col>
            <el-col :span="7"><div class="grid-content "></div></el-col>
            <el-col :span="7"><div class="grid-content "></div></el-col>
          </el-row>
        </div>
        <div class="grid-content ">
          <div class="Tips">
            <div class="header-title">
              <strong>使用说明</strong>
              <el-divider />
            </div>
            <div class="tipItem">
              <p class="tag-group__title">管理/人事</p>
              <div class="tag-group">
                <el-tag effect="plain" class="tagafer">添加人员 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">设置岗位角色 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">设置人员成本 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">完成 </el-tag>
              </div>
            </div>
            <div class="tipItem">
              <p class="tag-group__title">项目总监</p>
              <div class="tag-group">
                <el-tag effect="plain" class="tagafer">创建项目 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">设置项目经理 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">添加项目成员 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">查看工时统计 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">查看成本投入 </el-tag>
              </div>
            </div>
            <div class="tipItem">
              <p class="tag-group__title">项目经理</p>
              <div class="tag-group">
                <el-tag effect="plain" class="tagafer">维护团队 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">核查工时 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">完成项目 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">项目归档 </el-tag>
              </div>
            </div>
            <div class="tipItem">
              <p class="tag-group__title">开发人员</p>
              <div class="tag-group">
                <el-tag effect="plain" class="tagafer">填报 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">更新工时 </el-tag>
                <p class="tipsLine"></p>
                <el-tag effect="plain" class="tagafer">完成 </el-tag>
              </div>
            </div>
          </div>
        </div></el-col
      >
      <el-col :span="8"
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
                :disabled="IndexFillInfo.fillStatus != 2"
                >提交</el-button
              >
            </div>
            <div class="text item mycardbox">
              <div>
                <span>本月：</span
                ><span>
                  <p>已提交{{ IndexFillInfo.monthFill }}次</p>
                  <p>缺勤{{ IndexFillInfo.monthMissFill }}次</p>
                  <p>请假{{ IndexFillInfo.leaveNum }}次</p>
                  <p>倒休{{ IndexFillInfo.offDutyNum }}次</p>
                </span>
              </div>
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
                <el-table
                  :data="IndexLoginUserInfo"
                  style="width: 100%;font-size:12px"
                >
                  <el-table-column prop="loginDate" label="日期" align="center">
                  </el-table-column>
                  <el-table-column prop="nickName" label="动态" align="center">
                    <template slot-scope="scope">
                      <p>{{ scope.row.nickName }}</p>
                      <p>登录系统</p>
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
      usertime: "",
      username: "",
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
      IndexLoginUserInfo: [],
      workTime: null
    };
  },
  created() {
    // console.clear();
    this.workTime = this.$store.state.user.appconfig.workTime;
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
        // console.log(res);
        if (res.code == 200) {
          this.peojectcardInfo = res.data;
          this.peojectcardInfo.forEach(el => {
            if (el.manHour < el.useHour) {
              el.cardStatus = false;
            } else {
              el.cardStatus = true;
            }
          });
          // console.log("peojectcardInfo", this.peojectcardInfo);
        }
      });
      getIndexFillInfo().then(res => {
        // console.log("getIndexFillInfo", res);
        if (res.code == 200) {
          this.IndexFillInfo = res.data;
          if (this.IndexFillInfo.fillStatus == 1) {
            this.IndexFillInfo.subtype = "danger";
            this.IndexFillInfo.tagName = "已上报";
          } else if (this.IndexFillInfo.fillStatus == 2) {
            this.IndexFillInfo.tagName = "未上报";
            this.IndexFillInfo.subtype = "success";
          } else if (this.IndexFillInfo.fillStatus == 3) {
            this.IndexFillInfo.tagName = "无需上报";
            this.IndexFillInfo.subtype = "";
          }else if (this.IndexFillInfo.fillStatus == 4) {
            this.IndexFillInfo.tagName = "节假日";
            this.IndexFillInfo.subtype = "";
          }else if (this.IndexFillInfo.fillStatus == 5) {
            this.IndexFillInfo.tagName = "请假";
            this.IndexFillInfo.subtype = "";
          }else if (this.IndexFillInfo.fillStatus == 6) {
            this.IndexFillInfo.tagName = "无需上报";
            this.IndexFillInfo.subtype = "";
          } else {
            this.IndexFillInfo.tagName = "未知状态，联系管理员";
            this.IndexFillInfo.subtype = "warning";
          }
        }
      });
      getIndexLoginUser(this.queryParams).then(res => {
        // console.log("getIndexLoginUser", res);
        if (res.code == 200) {
          this.IndexLoginUserInfo = res.rows;
          // console.log(" this.IndexLoginUserInfo", this.IndexLoginUserInfo);
        }
      });
    },
    submitWorkingHours() {
      // console.log("提交工时");
      this.$router.push({ path: "/workingHours/myWorkingHours" });
    },
    checkWorkingHours() {
      // console.log("查看工时");
      // this.$router.push({ path: "/workingHours/myStatistics" });
      this.$router.push({ path: "/workingHours/fillInWorkingHours" });
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
  p {
    width: auto;
  }
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
      .top {
        font-size: 14px;
      }
    }
    .header-left {
      font-size: 16px;
      font-weight: 800;
    }
    .right {
      width: 45%;
      .top {
        text-align: center;
        font-size: 14px;
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
.tag-group {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.tipsLine {
  display: inline-block;
  width: 50px;
  height: 2px;
  background-color: #009ccd;
  margin: auto 0 !important;
}
</style>
