<template>
  <div class="main">
    <!-- <h3>系统设置111</h3> -->
    <el-row :gutter="20">
      <el-col :span="6"
        ><div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>系统logo:尺寸建议为21*21</span>
            </div>
            <div class="text-center">
              <el-upload
                class="avatar-uploader"
                :action="fileUrl"
                :headers="Headers"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <img v-if="logoImg" :src="logoImg" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </div>
          </el-card></div
      ></el-col>
      <el-col :span="18"
        ><div class="grid-content bg-purple-light">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>系统设置</span>
            </div>
            <div class="text item">
              <el-form ref="form" :model="user" label-width="80px">
                <el-form-item label="登录名称" prop="loginName">
                  <el-input v-model="user.loginName" maxlength="30" />
                </el-form-item>
                <el-form-item label="系统名称" prop="appName">
                  <el-input v-model="user.appName" maxlength="11" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" size="mini" @click="submit"
                    >保存</el-button
                  >
                  <el-button type="danger" size="mini" @click="close"
                    >关闭</el-button
                  >
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </div></el-col
      >
    </el-row>
  </div>
</template>

<script>
import { getConfig, editConfig } from "@/api/manage/setEditor";
import { getToken } from "@/utils/auth";

export default {
  data() {
    return {
      name: "系统设置",
      logoImg: "https://picsum.photos/id/433/200/200",
      fileUrl: process.env.VUE_APP_BASE_API + "/system/info/config/logo/upload",
      Headers: {
        Authorization: "Bearer " + getToken()
      },
      user: { loginName: "", appName: "" },
      rules: {
        loginName: [
          { required: true, message: "用户姓名不能为空", trigger: "blur" }
        ],
        systemName: [
          { required: true, message: "用户姓名不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      // console.log("我是系统设置！");
      getConfig().then(res => {
        // console.log("getconfig", res);
        if (res.code == 200) {
          // console.log(res.data);
          this.user.loginName = res.data.loginName;
          this.user.appName = res.data.appName;
          this.logoImg = process.env.VUE_APP_BASE_API + res.data.logoImg;
          // this.logoImg=process.env.VUE_APP_BASE_API+res.data.logoImg
        }
      });
    },
    handleAvatarSuccess(res, file) {
      // console.log("成功", res);
      if (res.code == 200) {
        this.$message.success("修改成功，请刷新页面！");
      }
      this.logoImg = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      // console.log(file)
      const isJPG =
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/jpg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传logo图片只能是 JPG/PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传logo图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    submit() {
      // alert("目前处于开发中！");
      // console.log("user", this.user);
      editConfig(this.user).then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.$message.success("修改成功！");
          this.$router.go(0);
        }
      });
    },
    close() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped>
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
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
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
::v-deep .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
