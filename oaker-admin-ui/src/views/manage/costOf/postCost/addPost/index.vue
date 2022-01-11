<template>
  <div class="main">
    <el-dialog
      title="新增"
      :visible.sync="dialogFormVisible"
      :before-close="handleClose"
      width="40%"
      append-to-body
    >
      <el-form :model="form">
        <el-form-item label="岗位成本" :label-width="formLabelWidth">
          <el-input v-model="form.cost" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="岗位名称" :label-width="formLabelWidth">
          <el-select v-model="form.postId" placeholder="请选择岗位名称">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import {getCostProjectPosts} from '@/api/manage/costOf'
export default {
  data() {
    return {
      dialogFormVisible: false,
      form: {
        cost: "",
        postId: ""
      },
      formLabelWidth: "120px"
    };
  },
  methods: {
    open() {
      this.dialogFormVisible = true;
    },
    init(){
        getCostProjectPosts().then(res=>{
            // console.log('getCostProjectPosts',res)
        })
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    }
  }
};
</script>

<style></style>
