<template>
  <div class="main">
    <!-- <h1>更新</h1> -->
    <template>
      <el-tabs
        tab-position="left"
        style="width:70%;margin:0 auto;"
      >
        <el-tab-pane label="名称">
          <el-form ref="form" :model="form" label-width="80px" class="cusForm">
            <el-form-item label="名称">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button>取消</el-button>
              <el-button type="primary" @click="onSubmit">修改</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="需求文档">
          <el-form ref="form" :model="form" label-width="120px" class="cusForm">
            <el-form-item label="">
              <el-upload
                class="upload-demo"
                ref="uploadDoc"
                action="https://jsonplaceholder.typicode.com/posts/"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileListDoc"
                :auto-upload="false"
              >
                <el-button
                  slot="trigger"
                  size="small"
                  type="primary"
                  style="padding:6px"
                  >选取文件</el-button
                >
                <!-- <el-button
              style="margin-left: 10px;"
              size="small"
              type="success"
              @click="submitUpload"
              >上传到服务器</el-button
            > -->
                <div slot="tip" class="el-upload__tip">
                  文件格式仅限DOC/DOCX/TXT,RAR,ZIP等格式。
                </div>
              </el-upload>
            </el-form-item>
            <el-form-item>
              <el-button>取消</el-button>
              <el-button type="primary" @click="onSubmit">修改</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="原型文件">
          <el-form ref="form" :model="form" label-width="120px" class="cusForm">
            <el-form-item label="">
              <el-upload
                class="upload-demo"
                ref="uploadZip"
                action="https://jsonplaceholder.typicode.com/posts/"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileListZip"
                :auto-upload="false"
              >
                <el-button
                  slot="trigger"
                  size="small"
                  type="primary"
                  style="padding:6px"
                  >选取文件</el-button
                >
                <!-- <el-button
              style="margin-left: 10px;"
              size="small"
              type="success"
              @click="submitUpload"
              >上传到服务器</el-button
            > -->
                <div slot="tip" class="el-upload__tip">
                  文件格式仅限DOC/DOCX/TXT,RAR,ZIP等格式。
                </div>
              </el-upload>
            </el-form-item>

            <el-form-item>
              <el-button>取消</el-button>
              <el-button type="primary" @click="onSubmit">修改</el-button>
            </el-form-item>
            <el-form-item>
              <div class="footer-tip">
                <p>上传新原型压缩包后，会将新压缩文件覆盖到上一份记录中。</p>
                <p>如需全新上传，请先删除原有记录。</p>
              </div>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </template>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        name: ""
      },
      fileListDoc: [
        {
          name: "food.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        },
        {
          name: "food2.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        }
      ],
      fileListZip: [
        {
          name: "food.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        },
        {
          name: "food2.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        }
      ]
    };
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
     // console.log(file, fileList);
    },
    handlePreview(file) {
     // console.log(file);
    },
    onSubmit() {
     // console.log("submit!");
    }
  }
};
</script>

<style lang="scss" scoped>
.cusForm {
  width: 80%;
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
</style>
