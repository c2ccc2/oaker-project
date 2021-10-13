<template>
  <div class="main">
    <el-form class="demo-form-inline">
      <el-form-item>
        <el-row>
          <el-button type="primary" @click="showCard = 1">项目概要</el-button>
          <el-button type="primary" @click="showCard = 2">人员管理</el-button>
          <el-button type="primary" @click="showCard = 3">工时设置</el-button>
          <el-button type="primary" @click="showCard = 4">项目管理</el-button>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-card class="box-card cardone" v-if="showCard == '1'">
          <div slot="header" class="clearfix">
            <span style="font-size:22px"
              >项目名称:{{ projectProfile.projectName }}</span
            >
            <el-button
              style="float: right"
              type="primary"
              @click="editprojectdetail"
              >编辑</el-button
            >
          </div>
          <div class="text item">
            <div class="content">
              <span v-if="projectProfile.projectStatusName == '进行中'"
                >项目阶段:
                <el-tag>{{ projectProfile.projectStatusName }}</el-tag></span
              >
              <span v-if="projectProfile.projectStatusName == '归档'"
                >项目阶段:
                <el-tag type="success">{{
                  projectProfile.projectStatusName
                }}</el-tag></span
              >
              <span v-if="projectProfile.projectStatusName == '运维'"
                >项目阶段:
                <el-tag type="warning">{{
                  projectProfile.projectStatusName
                }}</el-tag></span
              >
            </div>
            <div class="content">
              <span>项目简介:</span><span>{{ projectProfile.remark }}</span>
            </div>
            <div class="content">
              <span>项目经理:{{ projectProfile.projectManagerName }}</span>
            </div>
            <div class="content">
              <span>创建时间:{{ projectProfile.createTime }}</span>
            </div>
          </div>
        </el-card>
        <el-card class="box-card cardtwo" v-else-if="showCard == '2'">
          <div slot="header" class="clearfix">
            <span style="font-size:22px">
              <!-- <span>项目成员:{{ peopleTotal }}人 &nbsp;</span>
              <span>前端人员:10人</span>
              <span>后端人员:10人</span> -->
              <el-descriptions title="项目成员信息">
                <template slot="extra">
                  <!-- <el-button type="primary" size="small">操作</el-button> -->
                  <el-button
                    style="float: right"
                    type="primary"
                    @click="addpeople"
                    >添加人员</el-button
                  >
                </template>
                <el-descriptions-item label="项目成员"
                  >{{ peopleTotal }}人</el-descriptions-item
                >
                <el-descriptions-item
                  v-for="(item, index) in peopleTotalInfo"
                  :key="index"
                  :label="item.postName"
                  >{{ item.value }}人</el-descriptions-item
                >
              </el-descriptions>
            </span>
            <!-- <el-button style="float: right" type="primary" @click="addpeople"
              >添加人员</el-button
            > -->
          </div>
          <div class="text item">
            <template>
              <el-table :data="tableData" style="width: 100%">
                <el-table-column
                  fixed
                  type="index"
                  prop="date"
                  label="序号"
                  width="150"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="avatar"
                  label="头像"
                  width="120"
                  align="center"
                >
                  <template slot-scope="scope">
                    <div class="userImg">
                      <div class="demo-image">
                        <div v-if="scope.row.avatar != ''" class="block">
                          <el-image :src="scope.row.avatar"></el-image>
                        </div>
                        <div v-else class="block">
                          <el-image
                            :src="require('../../../assets/images/OAK_defalutImg.gif')"
                          ></el-image>
                        </div>
                      </div>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="nickName" label="姓名" align="center">
                  <template slot-scope="scope">
                    <div class="">
                      <p>{{ scope.row.nickName }}</p>
                      <p>{{ scope.row.email }}</p>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="postName" label="职位" align="center">
                  <template slot-scope="scope">
                    <div class="">
                      <el-tag>{{ scope.row.postName }}</el-tag>
                      <p>加入时间：{{ scope.row.joinTime }}</p>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  fixed="right"
                  label="操作"
                  width="120"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-button
                      @click.native.prevent="
                        deleteRow(scope.$index, tableData, scope.row)
                      "
                      type="text"
                      size="small"
                    >
                      移除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </div>
        </el-card>
        <el-card class="box-card cardthree" v-else-if="showCard == '3'">
          <div slot="header" class="clearfix">
            <span style="font-size:22px">预计投入工时：{{ manHour }}小时</span>
            <el-button style="float: right" type="primary" @click="eaitManHour"
              >编辑</el-button
            >
          </div>
          <!-- <div class="text item"></div> -->
        </el-card>
        <el-card class="box-card cardfour" v-else-if="showCard == '4'">
          <div class="text item">
            <el-row>
              <el-button type="primary" @click="handlearchive('b')"
                >转为运维</el-button
              >
              <el-button type="primary" @click="handlearchive('c')"
                >归档项目</el-button
              >
            </el-row>
          </div>
        </el-card>
      </el-form-item>
    </el-form>
    <addForm ref="addPeopleForm"></addForm>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form
        :model="numberValidateForm"
        ref="numberValidateForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item
          label="预计工时"
          prop="manHour"
          :rules="[
            { required: true, message: '不能为空' },
            { type: 'number', message: '必须为数字值' }
          ]"
        >
          <el-input
            type="manHour"
            v-model.number="numberValidateForm.manHour"
            autocomplete="off"
            style="width:50%"
          ></el-input
          ><el-select
            v-model="hourUnit"
            placeholder="请选择单位"
            style="width:30%;margin-left:5%"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item>
          <el-button type="primary" @click="submitForm('numberValidateForm')"
            >提交</el-button
          >
          <el-button @click="resetForm('numberValidateForm')">重置</el-button>
        </el-form-item> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm('numberValidateForm')"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <editForm ref="editForm"></editForm>
  </div>
</template>

<script>
import addForm from "./addPeopleForm";
import editForm from "./editproject.vue";
import { updaProjectStatus } from "@/api/system/project";
import { removeProjectUser } from "@/api/system/projectSettings";
import {
  projectDetail,
  userList,
  updateHour
} from "@/api/system/projectSettings";
export default {
  components: {
    addForm,
    editForm
  },
  data() {
    return {
      tableData: [],
      showCard: 1,
      dialogVisible: false,
      numberValidateForm: {
        manHour: ""
      },
      projectId: 0,
      projectProfile: {},
      manHour: 528,
      peopleTotal: "",
      peopleInfo: [],
      peopleTotalInfo: [],
      options: [
        {
          value: "0",
          label: "人天"
        },
        {
          value: "1",
          label: "人月"
        }
      ],
      hourUnit: "0"
    };
  },
  created() {
    if (this.$route.query.projectId) {
      this.projectId = this.$route.query.projectId;
      console.log(this.projectId);
      this.init();
    } else {
      this.$message.warning("请先选择项目");
      this.$router.push("projectManagement");
    }
  },
  methods: {
    deleteRow(index, rows, row) {
      rows.splice(index, 1);
      console.log(index, rows, row);
      let userIds = [];
      userIds = row.userId;
      let data = {
        projectId: this.projectId,
        userIds
      };
      removeProjectUser(data).then(res => {
        console.log(res);
      });
    },
    addpeople() {
      // this.$refs.addPeopleForm.centerDialogVisible=true
      this.$refs.addPeopleForm.open();
      this.$refs.addPeopleForm.projectId = this.projectId;
    },
    init() {
      this.showCard = 1;
      if (this.projectId == 0 || undefined) {
        this.$message.warning("请先选择项目");
      } else {
        projectDetail(this.projectId).then(res => {
          console.log(res);
          this.projectProfile = res.data;
          this.manHour = this.projectProfile.manHour;
        });
        userList(this.projectId).then(res => {
          console.log(res);
          this.tableData = res.data;
          this.tableData.forEach(el => {
            if(el.avatar!=''){
              el.avatar = "http://192.168.1.21:8080" + el.avatar;
            }
          });
          console.log(this.tableData)
          this.peopleTotal = this.tableData.length;
          this.peopleInfo = this.tableData;
          this.peopleTotalInfo = [];
          this.peopleInfo.forEach(el => {
            let data = { postName: el.postName, value: 0 };
            this.peopleInfo.forEach(elt => {
              if (el.postName == elt.postName) {
                data.value += 1;
              }
            });
            this.peopleTotalInfo.push(data);
          });
          console.log(this.peopleTotalInfo);

          // for (let i = 0; i < this.peopleTotalInfo.length - 1; i++) {
          //   let tempname = this.peopleTotalInfo[i].postName;
          //   console.log(tempname);
          //   // var indexof=null;
          //   for (let j = i + 1; j < this.peopleTotalInfo.length; j++) {
          //     // console.log(tempname == this.peopleTotalInfo[j].postName);
          //     // let ifls=tempname==this.peopleTotalInfo[j].postName
          //     if (tempname == this.peopleTotalInfo[j].postName) {
          //       console.log(j);
          //       // indexof=j
          //       // console.log(this.peopleTotalInfo[j])
          //       // delete this.peopleTotalInfo[j];
          //       this.peopleTotalInfo.splice(j, 1);
          //     }
          //   }
          // }
          // this.setarray(this.peopleTotalInfo)
          // this.peopleTotalInfo.splice(-1, 1);
          this.peopleTotalInfo = this.unique(this.peopleTotalInfo);

          console.log(this.peopleTotalInfo);
        });
      }
    },
    unique(newarr) {
      let arr = newarr;
      let result = {};
      let finalResult = [];
      for (let i = 0; i < arr.length; i++) {
        //利用对象的键名无法重复的特点，cppostName是唯一区别的属性值
        result[arr[i].postName]
          ? ""
          : (result[arr[i].postName] = true && finalResult.push(arr[i]));
      }
      return finalResult;
    },
    editprojectdetail() {
      this.$refs.editForm.formdata = this.projectProfile;
      this.$refs.editForm.open();
    },
    handlearchive(state) {
      console.log(state);
      updaProjectStatus(this.projectId, state).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.init();
        }
      });
    },
    eaitManHour() {
      this.dialogVisible = true;
    },
    cancel() {
      this.dialogVisible = false;
      this.resetForm("numberValidateForm");
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          this.resetForm("numberValidateForm");
          done();
        })
        .catch(_ => {});
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // alert("submit!");
          this.manHour = this.numberValidateForm.manHou;
          let data = {
            manHour: this.numberValidateForm.manHour,
            projectId: this.projectId
          };
          if (this.hourUnit == 0) {
            data.manHour = data.manHour * 8;
          } else if (this.hourUnit == 1) {
            data.manHour = data.manHour * 8 * 22;
          }
          console.log(data);
          updateHour(data).then(res => {
            console.log(res);
            if (res.code == 200) {
              // this.manHour = this.numberValidateForm.manHou;
              this.init();
              this.showCard = 3;
            }
          });
          this.resetForm(formName);
          this.dialogVisible = false;
        } else {
          console.log("error submit!!");
          this.resetForm(formName);
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
    // setarray(arr) {
    //   const map = {};
    //   // 1、把数组元素作为对象的键存起来（这样就算有重复的元素，也会相互替换掉）
    //   arr.forEach(item => (map[JSON.stringify(item)] = item));

    //   // 2、再把对象的值抽成一个数组返回即为不重复的集合
    //   return Object.keys(map).map(key => map[key]);
    // }
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
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
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
.userImg {
  display: flex;
  justify-content: center;
  align-items: center;
  .demo-image {
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 100%;
    overflow: hidden;
    .block {
      display: flex;
      justify-content: center;
      align-items: center;
      ::v-deep .el-image {
        width: 100px;
        height: 100px;
      }
    }
  }
}
</style>
