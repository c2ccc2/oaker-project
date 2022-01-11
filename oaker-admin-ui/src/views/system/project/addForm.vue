<template>
  <div class="main">
    <el-dialog title="创建项目" width="40%" :visible.sync="dialogFormVisible">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="120px"
        class="demo-ruleForm"
      >
        <el-form-item label="项目名称" prop="projectName">
          <el-input
            v-model="ruleForm.projectName"
            placeholder="请输入项目名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="项目编码" prop="projectCode">
          <el-input
            v-model="ruleForm.projectCode"
            @input="validateJyh(ruleForm.projectCode)"
            placeholder="请输入项目编码"
          ></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="startDate">
          <div class="block">
            <el-date-picker
              v-model="ruleForm.startDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="开始日期"
              @change="showdate"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="结束时间">
          <div class="block">
            <el-date-picker
              v-model="ruleForm.endDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="结束日期"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="预计投入" prop="duration">
          <el-input
            v-model.number="ruleForm.duration"
            maxlength="5"
            style="width:50%"
            placeholder="请输入预计投入"
          ></el-input>
          <el-select
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
        <el-form-item label="项目经理" prop="projectManager">
          <!-- <el-input v-model="ruleForm.projectManager"></el-input> -->
          <el-select
            v-model="ruleForm.projectManager"
            clearable
            placeholder="请选择项目经理"
          >
            <el-option
              v-for="item in projectManagerArr"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目描述" prop="remark">
          <el-input
            type="textarea"
            v-model="ruleForm.remark"
            maxlength="200"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="提示">
          <el-input
            type="text"
            :disabled="true"
            :placeholder="placeholderTips"
          ></el-input>
        </el-form-item>
        <!-- <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeForm">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')"
          >立即创建</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createProject, getBox } from "@/api/system/project";
export default {
  name: "addform",
  data() {
    return {
      placeholderTips: `1人月=22人天，1人天=${this.$store.state.user.appconfig.workTime}工时`,
      dialogFormVisible: false,
      formLabelWidth: "120px",
      projectManagerArr: [],
      ruleForm: {
        projectName: "",
        projectCode: "",
        startDate: "",
        endDate: "",
        duration: "",
        projectManager: "",
        remark: ""
      },
      rules: {
        projectName: [
          { required: true, message: "请输入项目名称", trigger: "blur" },
          { min: 1, max: 20, message: "长度在20 个字符以内", trigger: "blur" }
        ],
        projectCode: [
          { required: true, message: "请输入项目编码", trigger: "blur" },
          { min: 4, max: 64, message: "长度在 4 到 64 个字符", trigger: "blur" }
        ],
        startDate: [
          {
            type: "string",
            required: true,
            message: "请选择日期",
            trigger: "change"
          }
        ],
        duration: [
          { required: true, message: "请输入工时", trigger: "blur" },
          { type: "number", message: "工时必须为数字值" }
        ],
        projectManager: [
          { required: true, message: "请输入项目经理", trigger: "blur" }
        ]
      },
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
    this.getprojectManager();
  },
  methods: {
    showdate(value) {
      // console.log(value);
    },
    open() {
      this.getprojectManager();
      this.dialogFormVisible = true;
      this.gettady();
    },
    closeForm() {
      this.resetForm("ruleForm");
      this.dialogFormVisible = false;
    },
    gettady() {
      let date = new Date();
      let yyyy = date.getFullYear();
      let mm = date.getMonth() + 1;
      let dd = date.getDate();
      if (mm < 10) {
        mm = "0" + mm;
      }
      if (dd < 10) {
        dd = "0" + dd;
      }
      let timetaday = yyyy + "-" + mm + "-" + dd;
      // console.log(timetaday);
      this.ruleForm.startDate = timetaday;
    },
    getprojectManager() {
      getBox().then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.projectManagerArr = res.data;
        }
      });
    },
    edithourUnit() {
      if (this.hourUnit == 0) {
        this.ruleForm.duration =
          this.ruleForm.duration * this.$store.state.user.appconfig.workTime;
        // console.log(this.ruleForm.duration);
        return this.ruleForm.duration;
      } else if (this.hourUnit == 1) {
        this.ruleForm.duration =
          this.ruleForm.duration *
          this.$store.state.user.appconfig.workTime *
          this.$store.state.user.appconfig.workDay;
        // console.log(this.ruleForm.duration);
        return this.ruleForm.duration;
      }
    },
    lookduration(value) {
      // 工时检验
      // console.log(value);
    },
    validateJyh(projectCode) {
      // 项目编码校验规则
      let codeReg = new RegExp("[A-Za-z0-9]+"), //正则 英文+数字；
        len = projectCode.length,
        str = "";
      for (var i = 0; i < len; i++) {
        if (codeReg.test(projectCode[i])) {
          str += projectCode[i];
        }
      }
      this.ruleForm.projectCode = str;
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // let temphour = this.ruleForm.duration;
          this.ruleForm.temp = this.ruleForm.duration;
          // console.log("temphour", this.ruleForm.temp);
          this.edithourUnit();
          // alert("submit!");

          // console.log(this.ruleForm);
          createProject(this.ruleForm)
            .then(res => {
              // console.log(res);
              if (res.code == 200) {
                this.resetForm(formName);
                this.dialogFormVisible = false;
                this.$parent.init();
              }
            })
            .catch(error => {
              //  if (res.code == 500) {
              this.ruleForm.duration = this.ruleForm.temp;
              this.$message.warning("工时还原为" + this.ruleForm.temp);
              // console.log("temphour", this.ruleForm.temp);
              // }
              // console.log(error);
            });
        } else {
          // console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    showSartDate(starDate) {
      // console.log(111);
      // console.log(starDate);
      // console.log(minDate)
    },
    showEndDate(endDate) {
      // console.log(endDate);
    }
  }
};
</script>

<style scoped lang="scss"></style>
