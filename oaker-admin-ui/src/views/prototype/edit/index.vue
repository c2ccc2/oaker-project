<template>
  <div class="main">
    <el-dialog title="编辑" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form">
        <el-form-item label="名称:" :label-width="formLabelWidth">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEdit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { updateProto } from "@/api/prototype/prototype";
export default {
  data() {
    return {
      form: {
        id: null,
        name: ""
      },
      dialogFormVisible: false,
      formLabelWidth: "60px"
    };
  },
  methods: {
    open() {
      this.dialogFormVisible = true;
    },
    handleEdit() {
      // console.log(this.form)
      this.form.id = this.id;
      updateProto(this.form).then(res => {
        // console.log(res)
        if (res.code == 200) {
          this.$parent.init();
          this.$message.success("修改成功！");
          this.form = {};
        }
      });
      this.dialogFormVisible = false;
    }
  }
};
</script>

<style></style>
