<template>
  <div class="app-container home">
    <!-- <h6>{{ usertime }},王小娟</h6> -->
    <h6>{{ usertime }},{{ username }}</h6>
    <hr />
    <el-row>
      <el-col :span="17">
        <div class="title-header">
          <div class="left">进行中的项目</div>
          <div class="right">全部项目</div>
        </div>
        <div class="grid-content bg-purple" v-for="o in 2" :key="o">
          <el-row>
            <el-col :span="7" v-for="o in 3" :key="o"
              ><div class="grid-content bg-purple">
                <el-card class="box-card" shadow="hover">
                  <div slot="header" class="clearfix">
                    <b>Hexo</b>
                  </div>
                  <div class="card-content">
                    <div class="left">
                      <div class="top">
                        <p>昨日投入：</p>
                        <p>80小时10人天</p>
                      </div>
                      <div class="bottom">
                        <p>总投入：</p>
                        <p>800小时20人天</p>
                      </div>
                    </div>
                    <div class="right">
                      <div class="top">
                        <p>预算：500人天</p>
                      </div>
                      <div class="bottom">
                        <el-progress
                          type="circle"
                          :width="80"
                          :percentage="25"
                        ></el-progress>
                      </div>
                    </div>
                  </div>
                  <el-divider />
                  <div class="card-footer">
                    <div class="projectpeople">
                      <span>项目经理：段小誉</span>
                      <span>4月13日22:22</span>
                    </div>
                  </div>
                </el-card>
              </div></el-col
            >
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
          <el-descriptions title="今日工时提交情况">
            <el-descriptions-item label="今日提交">10人</el-descriptions-item>
            <el-descriptions-item label="未提交">5人</el-descriptions-item>
          </el-descriptions>

          <div class="">
            <el-card class="box-card" shadow="hover">
              <div slot="header" class="clearfix">
                <span>最新动态</span>
              </div>
              <div v-for="o in 12" :key="o" class="text item">
                8月10日 16:11 张三 登录系统
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
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.6.0",
      usertime: "上午好",
      username: "段铭锟",
      user: {},
      roleGroup: {},
      postGroup: {}
    };
  },
  created() {
    this.getUser();
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
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
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
    font-size: 8px;
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
  width: 93%;
  height: 56px;
  margin: 0 auto;
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
</style>
