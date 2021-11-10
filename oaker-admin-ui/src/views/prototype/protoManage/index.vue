<template>
  <div class="main">
    <!-- <h1>docManage</h1> -->
    <el-dialog title="原型" :visible.sync="dialogFormVisible">
      <el-form ref="form" label-width="170px" class="cusForm">
        <el-form-item
          label="原型管理(含start.html)："
          v-hasPermi="['pr:proto:upload']"
        >
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
            <el-button
              v-show="false"
              style="margin-left: 10px;height:26px"
            ></el-button>
            <div slot="tip" class="el-upload__tip">
              文件格式仅限包含HTML文件的ZIP压缩包，并且只能上传一个。
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button
            style="width:100%;padding:6px;float:right;"
            size="small"
            type="success"
            @click="onSubmit"
            v-hasPermi="['pr:proto:upload']"
            >立即上传</el-button
          >
          <!-- <el-button>取消</el-button> -->
        </el-form-item>
        <el-form-item label="上传记录：">
          <template>
            <el-table :data="tableData" style="width: 100%">
              <el-table-column type="index" label="序号" width="80">
                <template slot-scope="scope">
                  <el-badge
                    v-if="scope.row.flag"
                    value="当前"
                    class="item"
                  >
                    <!-- <el-button size="small">评论</el-button> -->
                    <!-- {{ scope.row.id }} -->
                    {{ scope.$index+1}}
                  </el-badge>
                  <el-badge v-else class="item">
                    <!-- <el-button size="small">评论</el-button> -->
                    <!-- {{ scope.row.id }} -->
                    {{ scope.$index+1}}
                  </el-badge>
                </template>
              </el-table-column>

              <el-table-column prop="createTime" label="更新时间" width="180">
              </el-table-column>
              <el-table-column prop="createUserName" label="操作">
              </el-table-column>
              <el-table-column prop="fileName" label="原型访问" width="180">
                <template slot-scope="scope">
                  <!-- <el-badge v-if="scope.$index=='0'" value="当前" class="item"> -->
                  <!-- <el-button size="small">评论</el-button> -->
                  <a :href="scope.row.prototypeUrl" target="_blank">
                    <el-button
                      type="text"
                      size="small"
                      style="text-decoration:underline"
                      >立即访问</el-button
                    >
                  </a>
                  <!-- </el-badge> -->
                  <!-- <el-badge v-else class="item"> -->
                  <!-- <el-button size="small">评论</el-button> -->
                  <!-- <a :href="scope.row.prototypeUrl" target="_blank"
                    >立即访问
                    <el-button type="text" size="small">立即访问</el-button>
                  </a> -->
                  <!-- </el-badge> -->
                </template>
              </el-table-column>
              <el-table-column label="管理" fixed="right">
                <template slot-scope="scope">
                  <!-- <el-button
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)"
                  >下载</el-button
                > -->
                  <el-button
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
                    v-hasPermi="['pr:proto:record:delete']"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
            <!-- <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="params.pageNum"
            :limit.sync="params.pageSize"
            @pagination="init"
          /> -->
          </template>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { listProtoRecord, delProtoRecord } from "@/api/prototype/prototype";
import { getToken } from "@/utils/auth";
export default {
  data() {
    return {
      dialogFormVisible: false,
      recordId: null,
      oldUrl:"",
      id: null,
      arrIndex: null,
      protoId: null,
      // 查询参数
      params: {
        pageNum: 1,
        pageSize: 10
      },
      // 总条数
      total: 0,
      tableData: [],
      datazip: {
        upType: "proto",
        prototypeId: null
      },
      Headers: {
        Authorization: "Bearer " + getToken()
      },
      fileListZIP: [],
      fileUrl: process.env.VUE_APP_BASE_API + "/pr/proto/upload"
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.arrIndex = this.$route.query.arrIndex;
    this.protoId = this.$route.query.protoId;
    // this.init();
  },
  methods: {
    open() {
      // this.datazip.prototypeId = this.protoId;
      console.log('oldurl',this.oldUrl)
      this.dialogFormVisible = true;
      this.datazip.prototypeId = this.protoId;
      listProtoRecord(this.protoId).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.tableData = res.data;
          this.total = res.data.length;
          this.tableData.forEach(el => {
            el.prototypeUrl = process.env.VUE_APP_BASE_API + el.prototypeUrl;
            if(this.recordId==el.id){
              el.flag=true
            }else{
              el.flag=false
            }
          });
        }
      });
    },
    // open() {
    //   this.dialogFormVisible = true;
    //   this.datazip.prototypeId = this.protoId;
    // },
    close() {
      this.dialogFormVisible = false;
      this.fileListZIP = [];
      // this.$parent.init();
    },
    onSubmit() {
      console.log("submit!");
      this.$refs.uploadZip.submit();
    },
    handleBeforeZip(file) {
      console.log("handleBefore", file);
      var FileExt = file.name.replace(/.+\./, "");
      if (["zip"].indexOf(FileExt.toLowerCase()) === -1) {
        this.$message({
          type: "warning",
          message: "原型文件请上传后缀名为[zip]的文件！"
        });
        return false;
      }
    },
    handleSuccess(res, file) {
      console.log("handleSuccess", res);
      if (res.code == 200) {
        this.$message.success("上传成功");
        // this.init();
        this.$parent.init();
        this.close();
        this.$parent.value4 = parseInt(this.tempselectid);
        this.$parent.getList(this.tempselectid);
      }
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
      this.$confirm("确定要删除该条内容？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(_ => {
          delProtoRecord(row.id).then(res => {
            if (res.code == 200) {
              this.$message.success("删除成功！");
              this.tableData.splice(index, 1);
              this.$parent.init();
            }
          });
        })
        .catch(_ => {
          this.$message.warning("取消删除！");
        });
    }
  }
};
</script>

<style lang="scss" scoped>
.item {
  margin-top: 10px;
  margin-right: 40px;
}
.cusForm {
  width: 90%;
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
