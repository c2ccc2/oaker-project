<template>
  <div class="main">
    <el-dialog
      title="添加人员"
      :visible.sync="centerDialogVisible"
      width="60%"
      @close="beforeClose"
      center
    >
      <template>
        <!-- <p style="text-align: center; margin: 0 0 20px">
          使用 render-content 自定义数据项
        </p> -->
        <!-- <div style="text-align: center">
          <el-transfer
            style="text-align: left; display: inline-block"
            v-model="value"
            filterable
            :left-default-checked="[2, 3]"
            :right-default-checked="[1]"
            :render-content="renderFunc"
            :titles="['Source', 'Target']"
            :button-texts="['到左边', '到右边']"
            :format="{
              noChecked: '${total}',
              hasChecked: '${checked}/${total}'
            }"
            @change="handleChange"
            :data="data"
          >
            <el-button class="transfer-footer" slot="left-footer" size="small"
              >操作</el-button
            >
            <el-button class="transfer-footer" slot="right-footer" size="small"
              >操作</el-button
            >
          </el-transfer>
        </div> -->
        <!-- <p style="text-align: center; margin: 50px 0 20px">
          使用 scoped-slot 自定义数据项
        </p> -->
        <!-- <div style="text-align: center">
          <el-transfer
            style="text-align: left; display: inline-block"
            v-model="value4"
            filterabl
            :titles="['所有', '已选']"
            :format="{
              noChecked: '${total}',
              hasChecked: '${checked}/${total}'
            }"
            @change="handleChange"
            :data="data"
          >
            <span slot-scope="{ option }"
              >{{ option.key }} - {{ option.label }}</span
            >
            <el-button class="transfer-footer" slot="left-footer" size="small"
              >操作</el-button
            >
            <el-button class="transfer-footer" slot="right-footer" size="small"
              >操作</el-button
            >
          </el-transfer>
        </div> -->
        <!-- <el-transfer
          v-model="checked"
          :data="transferData"
          filterable
          :filter-method="filterMethod"
          filter-placeholder="请输入姓名查询"
          :titles="['全选', '全选']"
          @change="getObject"
        >
          <span slot-scope="{ option }"
            >{{ option.nickName }} - {{ option.postName }}
            <span> - {{ option.email }}</span>
          </span>
        </el-transfer> -->
        <el-transfer
          v-model="checked"
          :data="transferData"
          filterable
          :filter-method="filterMethod"
          filter-placeholder="请输入姓名查询"
          :titles="['全选', '全选']"
          @change="getObject"
        >
          <span slot-scope="{ option }" @click="clickspan(option)"
            >{{ option.nickName }} - {{ option.postName }}
            <span> - {{ option.email }}</span>
          </span>
          <!-- <span slot-scope="{ option }"
            ><p>{{ option.nickName }} - {{ option.postName }}</p>
            <p>{{ option.email }}</p>
          </span> -->
        </el-transfer>
      </template>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleclose">取 消</el-button>
        <el-button type="primary" @click="handleclick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { userSelectList, addProjectUsers } from "@/api/system/projectSettings";
export default {
  data() {
    // const generateData = _ => {
    //   const data = [];
    //   for (let i = 1; i <= 15; i++) {
    //     data.push({
    //       key: i,
    //       label: `工程师 ${i}`,
    //       disabled: i % 4 === 0
    //     });
    //   }
    //   return data;
    // };
    return {
      centerDialogVisible: false,
      projectId: "",
      // data: generateData(),
      transferData: [],
      checked: [],
      renderFunc(h, option) {
        return (
          <span>
            {option.key} - {option.label}
          </span>
        );
      },
      filterMethod(query, item) {
        return item.nickName.indexOf(query) > -1;
      }
    };
  },
  created() {
    // this.getObject()
  },
  methods: {
    clickspan(row) {
      console.log(row);
      let flag = this.checked.findIndex(el => el == row.key);
      console.log(flag);
      if (flag >= 0) {
        this.checked.splice(flag, 1);
        // this.transferData.unshift(row)
        console.log(row);
      } else {
        this.checked.push(row.key);
      }
    },
    open() {
      console.log(this.data);
      setTimeout(() => {
        console.log("addform", this.projectId);
        this.settransfer();
        this.getObject();
        this.centerDialogVisible = true;
      }, 100);
    },
    getObject() {
      console.log("1111");
      userSelectList(this.projectId).then(res => {
        console.log(res);
        if (res.code == 200) {
          const allData = res.data;
          const data = [];
          for (let i = 0; i < allData.length; i++) {
            data.push({
              // key:i,
              key: allData[i].userId,
              nickName: allData[i].nickName,
              email: allData[i].email,
              postName: allData[i].postName,
              userId: allData[i].userId,
              ruleDesc: allData[i].ruleDesc
            });
          }
          this.transferData = data;
        }
      });
    },
    settransfer() {
      setTimeout(() => {
        let transferPanel = document.querySelectorAll(".el-transfer-panel");
        let transfer = document.querySelector(".el-transfer");
        transfer.style.display = "flex";
        transfer.style.justifyContent = "center";
        transfer.style.alignItems = "center";
        transferPanel.forEach(el => (el.style.width = "38%"));
        console.log(transferPanel);
      }, 100);
    },
    handleChange(value, direction, movedKeys) {
      console.log(value, direction, movedKeys);
    },
    beforeClose() {
      this.checked = [];

      // console.log('close',111)
      // this.$confirm("是否关闭？", "提示！", {
      //   confirmButtonText: "确定",
      //   cancelButtonText: "取消",
      //   type: "warning"
      // })
      //   .then(() => {
      //     this.checked = [];
      //   })
      //   .catch(() => {
      //     done()
      //     this.centerDialogVisible=true
      //   });
    },
    handleclose() {
      this.centerDialogVisible = false;
      this.checked = [];
    },
    handleclick() {
      console.log(111);
      console.log(this.checked);
      let data = {
        projectId: this.projectId,
        users: this.checked
      };
      addProjectUsers(data).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.$parent.init();
          this.$parent.showCard = 2;
          this.checked = [];
          this.centerDialogVisible = false;
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.transfer-footer {
  margin-left: 20px;
  padding: 6px 5px;
  display: flex;
  justify-content: center;
  align-items: center;
}
/* 穿梭框外框高宽度 */
::v-deep .el-transfer-panel {
  // width: 600px;
  // height: 150%;
}
/* 穿梭框内部展示列表的高宽度 */
::v-deep .el-transfer-panel__list {
  width: 300px;
  // height: 100%;
}
</style>
