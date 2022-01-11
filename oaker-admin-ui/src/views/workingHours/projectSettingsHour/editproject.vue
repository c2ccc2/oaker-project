<template>
  <div class="main">
    <el-dialog
      title="项目概要设置"
      :visible.sync="dialogFormVisible"
      style="width:60%;margin:auto;overflow:hidden"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="140px"
        class="demo-ruleForm"
        style="width:100%"
      >
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="ruleForm.projectName"></el-input>
        </el-form-item>
        <el-form-item label="项目经理" prop="projectManager">
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
        <el-form-item label="项目编码" prop="projectCode">
          <el-input v-model="ruleForm.projectCode"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="startDate">
          <div class="block">
            <el-date-picker
              v-model="ruleForm.startDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="开始日期"
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
        <!-- <el-form-item label="预计投入工时" prop="manHour">
          <el-input v-model="ruleForm.manHour"></el-input>
        </el-form-item> -->

        <el-form-item label="项目描述">
          <el-input type="textarea" v-model="ruleForm.remark"></el-input>
        </el-form-item>
        <!-- <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item> -->
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getBox } from "@/api/system/project";
import { updateModify } from "@/api/system/projectSettings";

export default {
  data() {
    return {
      dialogFormVisible: false,
      formdata: {},
      ruleForm: {
        projectName: "",
        projectManager: "",
        projectCode: "",
        startDate: "",
        endDate: "",
        // manHour: "",
        remark: ""
      },
      startDate: "",
      endDate: "",
      rules: {
        projectName: [
          { required: true, message: "请输入项目名称", trigger: "blur" },
          { min: 0, max: 64, message: "长度在 0 到 64 个字符", trigger: "blur" }
        ],
        projectManager: [
          { required: true, message: "请选择项目经理", trigger: "change" }
        ],
        projectCode: [
          { required: true, message: "请选择项目编码", trigger: "change" }
        ],
        startDate: [
          {
            type: "string",
            required: true,
            message: "请选择日期",
            trigger: "change"
          }
        ]
        // manHour: [{ required: true, message: "请输入工时", trigger: "change" }]
      },
      projectManagerArr: []
    };
  },
  methods: {
    open() {
     // console.log(this.formdata);
      this.getprojectManager();
      this.ruleForm.projectId = this.formdata.projectId;
      this.ruleForm.projectName = this.formdata.projectName;
      this.ruleForm.projectManager = this.formdata.projectManager;
      this.ruleForm.projectCode = this.formdata.projectCode;
      // this.ruleForm.manHour = this.formdata.manHour;
      this.ruleForm.remark = this.formdata.remark;
      this.ruleForm.startDate = this.formdata.startDate;
      this.ruleForm.endDate = this.formdata.endDate;
      this.dialogFormVisible = true;
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          //   alert("submit!");

          let data = this.ruleForm;
          updateModify(data).then(res => {
           // console.log(res);
            if (res.code == 200) {
              this.dialogFormVisible = false;
              this.$parent.init();
            }
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
    getprojectManager() {
      getBox().then(res => {
       // console.log(res);
        if (res.code == 200) {
          this.projectManagerArr = res.data;
        }
      });
    }
  }
};
</script>

<style></style>
