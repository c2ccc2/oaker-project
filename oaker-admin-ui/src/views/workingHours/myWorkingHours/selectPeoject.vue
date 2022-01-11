<template>
  <div class="main">
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <span
        ><template>
          <el-radio-group v-model="radio">
            <el-radio
              v-for="item in myProject"
              :key="item.projectId"
              :label="item.projectId"
              @change="showradio"
              >{{ item.projectName }}</el-radio
            >
          </el-radio-group>
        </template></span
      >
      <span slot="footer" class="dialog-footer">
        <el-button @click="Close">取 消</el-button>

        <download-excel
          class="btn btn-default"
          :data="json_data"
          :fields="json_fields"
          :type="type"
          name="工作日志.xls"
          :before-finish="myclick"
        >
          <!-- <span>确 定</span> -->
          <el-button type="primary">确 定</el-button>
        </download-excel>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getMyProjectAll, getStatExport } from "@/api/system/project";
export default {
  data() {
    return {
      dialogVisible: false,
      radio: null,
      localDate: "",
      type: "xlsx",
      myProject: [],
      json_fields: {
        //生成excel的字段：导出数据的字段
        人员姓名: "nickName",
        服务项目: "projectName",
        技术类型: "postName",
        日期: "date",
        工作内容: "daily",
        时长: {
          callback: value => {
            return `${value.useHour}小时`;
          }
        },
        考勤: "attendance"
      },
      json_data: []
    };
  },
  methods: {
    open() {
      getMyProjectAll().then(res => {
        if (res.code == 200) {
          this.myProject = res.data;
          this.dialogVisible = true;
        }
      });
    },
    Close() {
      this.radio = null;
      this.dialogVisible = false;
    },
    handleExport() {
      console.log(this.radio);
      let params = {};
      params.localDate = this.localDate;
      params.projectId = this.radio;
      getStatExport(params).then(res => {
        // console.log(res);
        if (res.code == 200) {
          console.log(this.radio);
          res.data.fillInfos.forEach(el => {
            el.nickName = res.data.nickName;
            el.postName = res.data.postName;
            el.projectId = res.data.projectId;
            el.projectName = res.data.projectName;
            if (el.status == 1) {
              el.attendance = "已填报";
            }
            if (el.status == 2) {
              el.attendance = "未填报";
            }
            if (el.status == 3) {
              el.attendance = "无需填报";
            }
            if (el.status == 4) {
              el.attendance = "节假日";
            }
            if (el.status == 5) {
              el.attendance = "请假";
            }
            if (el.status == 6) {
              el.attendance == "调休";
            }
            if (el.useHour == null) {
              el.useHour = 0;
            }
          });
          this.json_data = res.data.fillInfos;
          console.log(this.json_data);
          //   this.radio = null;
          //   this.dialogVisible = false;
        }
      });
    },
    showradio(value) {
      console.log(value);
      console.log(this.radio);
      this.handleExport();
    },
    myclick() {
      //   console.log("我的事件");
      this.radio = null;
      this.dialogVisible = false;
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

<style lang="scss" scoped>
.btn.btn-default {
  display: inline-block;
  margin-left: 8px;
}
</style>
