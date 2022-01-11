<template>
  <div class="main">
    <el-dialog
      title="添加人员"
      :visible.sync="centerDialogVisible"
      width="40%"
      @close="beforeClose"
      center
    >
      <!-- <template>
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
        </el-transfer>
      </template> -->
      <template>
        <el-table
          :data="
            transferData.filter(
              data =>
                !search ||
                data.nickName.toLowerCase().includes(search.toLowerCase())
            )
          "
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="index" label="序号" prop="nickName">
          </el-table-column>
          <el-table-column label="姓名" prop="nickName">
            <template slot-scope="scope">
              <div>
                <p>{{ scope.row.nickName }}</p>
                <p>{{ scope.row.email }}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="postName"
            ><template slot="header">
              <el-input
                v-model="search"
                size="mini"
                clearable
                placeholder="输入姓名搜索"
              />
            </template>
          </el-table-column>
          <el-table-column type="selection" align="center">
            <!-- <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >Edit</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                >Delete</el-button
              >
            </template> -->
          </el-table-column>
        </el-table>
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
    return {
      centerDialogVisible: false,
      projectId: "",
      transferData: [],
      search: "",
      multipleSelection: [],
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
      let selectData = [];
      row.forEach(el => selectData.push(el.key));
      return selectData;
    },
    open() {
      // console.log(this.data);
      setTimeout(() => {
        // console.log("addform", this.projectId);
        this.settransfer();
        this.getObject();
        this.centerDialogVisible = true;
      }, 100);
    },
    getObject() {
      // console.log("1111");
      userSelectList(this.projectId).then(res => {
        // console.log(res);
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
        // console.log(transferPanel);
      }, 100);
    },
    handleChange(value, direction, movedKeys) {
      // console.log(value, direction, movedKeys);
    },
    beforeClose() {
      this.multipleSelection = [];
    },
    handleclose() {
      this.centerDialogVisible = false;
      this.multipleSelection = [];
    },
    handleclick() {
      // console.log(111);
      // console.log(this.multipleSelection);
      let data = {
        projectId: this.projectId,
        users: this.multipleSelection
      };
      addProjectUsers(data).then(res => {
        // console.log(res);
        if (res.code == 200) {
          this.$parent.init();
          this.$parent.showCard = 2;
          this.multipleSelection = [];
          this.centerDialogVisible = false;
        }
      });
    },
    handleSelectionChange(val) {
      // console.log(val)
      this.multipleSelection = this.clickspan(val);
      // console.log(this.multipleSelection)
    }
  }
};
</script>
<style lang="scss" scoped>
p {
  margin: 0;
}
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
}
</style>
