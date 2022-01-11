<template>
  <div class="main">
    <!-- <h1>上传原型</h1> -->
    <el-dialog title="原型上传" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="form" label-width="90px" class="cusForm">
        <el-form-item label="原型名称：">
          <el-input v-model="form.name" placeholder="请输入原型名称"></el-input>
        </el-form-item>
        <el-form-item label="所属项目：">
          <el-select
          style="width:100%"
            v-model="value4"
            clearable
            placeholder="请选择"
            @change="getList(value4)"
          >
            <el-option
              v-for="item in options"
              :key="item.projectId"
              :label="item.projectName"
              :value="item.projectId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="相关文档：" v-if="changePermi">
          <el-upload
            class="upload-demo"
            ref="uploadDoc"
            multiple
            :data="dataDoc"
            :headers="Headers"
            :action="fileUrl"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-upload="handleBeforeDoc"
            :on-success="handleSuccess"
            :file-list="fileListDOC"
            :auto-upload="false"
          >
            <el-button
              style="padding:6px"
              slot="trigger"
              size="small"
              type="primary"
              >选取文件</el-button
            >
            <el-button
              v-show="false"
              style="margin-left: 10px;height:26px"
            ></el-button>
            <div slot="tip" class="el-upload__tip">
              文件格式仅限doc,docx,xls,xlsx,ppt,pptx,txt,rar,zip,gz,bz2,pdf等格式。
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="原型文件：" v-if="changePermi">
          <el-upload
            class="upload-demo"
            ref="uploadZip"
            :limit="1"
            :data="datazip"
            :headers="Headers"
            :action="fileUrl"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-upload="handleBeforeZip"
            :on-success="handleSuccess"
            :file-list="fileListZIP"
            :auto-upload="false"
          >
            <el-button
              style="padding:6px"
              slot="trigger"
              size="small"
              type="primary"
              >选取文件</el-button
            >
            <!-- <el-button
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload"
            >上传文件</el-button
          > -->
            <div slot="tip" class="el-upload__tip">
              文件格式仅限包含start.html文件的ZIP压缩包，并且只能上传一个。
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="效果图管理:" >
          <el-upload
            class="upload-demo"
            ref="uploadImg"
            multiple
            :data="dataImg"
            :headers="Headers"
            :action="fileImgUrl"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-upload="handleBeforeImg"
            :on-success="handleSuccess"
            :file-list="fileListIMG"
            :auto-upload="false"
          >
            <el-button
              style="padding:6px"
              slot="trigger"
              size="small"
              type="primary"
              >选取文件</el-button
            >
            <el-button
              v-show="false"
              style="margin-left: 10px;height:26px"
            ></el-button>
            <div slot="tip" class="el-upload__tip">
              文件格式仅限doc,docx,xls,xlsx,ppt,pptx,txt,rar,zip,gz,bz2,pdf等格式。
            </div>
          </el-upload>
        </el-form-item>

        <el-form-item align="center">
          <el-button @click="close">返回</el-button>
          <!-- <el-button type="primary" @click="onSubmit">提交</el-button> -->
          <el-button type="primary" @click="onSubmit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { createProto } from "@/api/prototype/prototype";
import { getIndexProjectStat } from "@/api/dashboard";

import { getToken } from "@/utils/auth";
export default {
  data() {
    return {
      dialogFormVisible: false,
      id: "",
      value4: "",
      options: [],
      form: {
        name: "",
        projectId: null
      },
      dataDoc: {
        upType: "doc",
        prototypeId: null
      },
      datazip: {
        upType: "proto",
        prototypeId: null
      },
      dataImg:{
        prototypeId: null
      },
      Headers: {
        Authorization: "Bearer " + getToken()
      },
      fileListDOC: [],
      fileListZIP: [],
      fileListIMG: [],
      fileUrl: process.env.VUE_APP_BASE_API + "/pr/proto/upload",
      fileImgUrl: process.env.VUE_APP_BASE_API + "/pr/proto/upload",
      changePermi: false
    };
  },
  created() {
    this.permiChange();
    this.form.projectId = parseInt(this.id);

    this.init();
  },
  methods: {
    open() {
      this.dialogFormVisible = true;
      // console.log('permissions',this.$store.state)
    },
    close() {
      // this.$parent.init()
      this.$parent.value4 = parseInt(this.value4);
      this.$parent.getList(this.value4);
      this.dialogFormVisible = false;
      this.fileListDOC = [];
      this.fileListZIP = [];
      this.fileListIMG = [];
      this.form = { name: "", projectId: "" };
      // console.clear();
    },
    init() {
      // console.log(this.id);
      // this.value4 = this.id;
      // this.$nextTick(() => {
      getIndexProjectStat().then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.options = res.data;

          // this.value4 = parseInt(this.id);
          // console.log("value4", this.value4);

          this.getList(this.value4);
        }
      });
      // });
    },
    getList(id) {
      // console.log(id);
      // this.params.projectId = id;
      this.id = id;
      this.form.projectId = id;
      // console.log(this.id);
      // console.log(this.params);
    },
    onSubmit() {
      // console.log(this.form);
      // console.log("submit!");
      if (this.form.name) {
        createProto(this.form).then(res => {
          // console.log(res);
          if (res.code == 200) {
            this.dataDoc.prototypeId = res.id;
            this.datazip.prototypeId = res.id;
            this.dataImg.prototypeId = res.id;
            // console.log(this.dataDoc.prototypeId);
            this.$message.success("成功创建！" + this.form.name);
            if (this.changePermi) {
              this.submitUpload();
            }
            this.close();
            // this.$router.push("projectPrototype");
          }
        });
      } else {
        this.$message.error("请输入项目名称！");
      }
    },
    submitUpload() {
      // this.$message.success("成功创建！" + this.form.name);
      if (this.id) {
        this.$refs.uploadDoc.submit();
        this.$refs.uploadZip.submit();
        this.$refs.uploadImg.submit();
      } else {
        this.$message.error("请选择项目，并输入项目名称");
      }
    },
    handleBeforeDoc(file) {
      // console.log("handleBefore", file);
      var FileExt = file.name.replace(/.+\./, "");
      if (
        [
          "doc",
          "docx",
          "xls",
          "xlsx",
          "ppt",
          "pptx",
          "txt",
          "rar",
          "zip",
          "gz",
          "bz2",
          "pdf"
        ].indexOf(FileExt.toLowerCase()) === -1
      ) {
        this.$message({
          type: "warning",
          message:
            "需求文档请上传后缀名为[doc,docx,xls,xlsx,ppt,pptx,txt,rar,zip,gz,bz2,pdf]的文件！"
        });
        return false;
      }
    },
    handleBeforeZip(file) {
      // console.log("handleBefore", file);
      var FileExt = file.name.replace(/.+\./, "");
      if (["zip"].indexOf(FileExt.toLowerCase()) === -1) {
        this.$message({
          type: "warning",
          message: "原型文件请上传后缀名为[zip]的文件！"
        });
        return false;
      }
    },
    handleBeforeImg(file) {
      // console.log("handleBefore", file);
      var FileExt = file.name.replace(/.+\./, "");
      if (["zip"].indexOf(FileExt.toLowerCase()) === -1) {
        this.$message({
          type: "warning",
          message: "效果图文件请上传后缀名为[zip]的文件！"
        });
        return false;
      }
    },
    handleSuccess(res, file, fileList) {
      // console.log("handleSuccess", res, file, fileList);
      if (res.code == 200) {
        this.$message.success("上传成功");
        this.close();
        // this.$router.push("projectPrototype");
      }
    },
    handleRemove(file, fileList) {
      // console.log("handleRemove", file, fileList);
    },
    handlePreview(file) {
      // console.log("file", file);
    },
    permiChange() {
      // console.log("permissions", this.$store.state);
      let arrPer = this.$store.state.user.permissions;
      let indexPer = arrPer.indexOf("pr:proto:upload");
      // console.log("indexPer", indexPer);
      if (indexPer != -1) {
        // console.log('我有上传权限')
        this.changePermi = true;
      } else {
        // console.log('我不能上传')
        this.changePermi = false;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.cusForm {
  width: 95%;
  margin: 0 auto;
  ::v-deep .el-upload--text {
    float: right;
  }
  ::v-deep .el-upload-list--text {
    border: 1px solid #d0d3db;
    height: 100px;
    overflow: auto;
  }
}
::v-deep .el-upload__tip {
  height: 36px;
}
</style>
