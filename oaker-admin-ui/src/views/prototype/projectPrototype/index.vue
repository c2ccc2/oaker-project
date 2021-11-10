<template>
  <div class="main">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span
          ><template>
            <el-select
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
            </el-select> </template
        ></span>
        <el-button
          @click="addProrotype"
          size="small"
          style="float: right;"
          type="primary"
          v-hasPermi="['pr:proto:add']"
          >创建原型</el-button
        >
      </div>
      <div class="text item">
        <template>
          <el-table :border="false" :data="tableData" style="width: 100%">
            <el-table-column
              fixed
              prop="name"
              label="名称"
              width="200"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="docList.docName"
              label="文档"
              width="250"
              align="center"
            >
              <template slot-scope="scope">
                <div class="">
                  <p v-if="scope.row.docList.length == 0">
                    <el-button type="text" size="small" :disabled="true"
                      >暂无文档</el-button
                    >
                  </p>
                  <p
                    v-else
                    class="doc"
                    v-for="(item, index) in scope.row.docList"
                    v-show="index < 5"
                    :key="item.id"
                    @click="download(item.docUrl)"
                  >
                    {{ item.docName }}
                  </p>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="prototypeUrl"
              label="访问原型"
              width="120"
              align="center"
            >
              <template slot-scope="scope">
                <a v-if="scope.row.prototypeUrl == 0">
                  <el-button type="text" size="small" :disabled="true"
                    >暂无原型</el-button
                  >
                </a>
                <a v-else :href="scope.row.prototypeUrl" target="_blank">
                  <el-button
                    type="text"
                    size="small"
                    style="text-decoration:underline"
                    >立即访问</el-button
                  >
                </a>
              </template>
            </el-table-column>
            <el-table-column
              prop="createTime"
              label="更新时间"
              width="200"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="createUserName"
              label="操作人"
              width="120"
              align="center"
            >
            </el-table-column>

            <!-- <el-table-column prop="" label="分享" width="120" align="center">
              <template>
                <el-button type="text" size="small">公开分享</el-button>
              </template>
            </el-table-column> -->
            <el-table-column
              fixed="right"
              label="管理"
              width="350"
              align="center"
            >
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="small"
                  plain
                  @click="handleDoc(scope.$index, scope.row)"
                  v-hasPermi="['pr:doc:query']"
                  >文档管理</el-button
                >
                <el-button
                  type="primary"
                  size="small"
                  plain
                  @click="handlePrototype(scope.$index, scope.row)"
                  v-hasPermi="['pr:proto:record:list']"
                  >原型管理</el-button
                >
                <el-button
                  type="primary"
                  size="small"
                  plain
                  @click="handleEditName(scope.$index, scope.row)"
                  v-hasPermi="['pr:proto:update']"
                  >编辑</el-button
                >
                <el-button
                  type="danger"
                  size="small"
                  plain
                  @click="handleDelete(scope.$index, scope.row)"
                  v-hasPermi="['pr:proto:delete']"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
    </el-card>
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="params.pageNum"
      :limit.sync="params.pageSize"
      @pagination="init"
    />
    <Edit ref="edit"></Edit>
    <AddPrototype ref="addPrototype"></AddPrototype>
    <DocManage ref="docManage"></DocManage>
    <ProtoManage ref="protoManage"></ProtoManage>
  </div>
</template>

<script>
import Edit from "../edit";
import AddPrototype from "../addPrototype";
import DocManage from "../docManage";
import ProtoManage from "../protoManage";
import { getIndexProjectStat } from "@/api/dashboard";
import {
  listProto,
  deleteProto,
  delProtoRecord
} from "@/api/prototype/prototype";
export default {
  data() {
    return {
      i: 10,
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10
      },
      tableData: [],
      options: [],
      value4: ""
    };
  },
  components: {
    Edit,
    DocManage,
    ProtoManage,
    AddPrototype
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      // console.log(this.i);
      getIndexProjectStat().then(res => {
        console.log(res);
        if (res.code == 200) {
          this.options = res.data;
          this.setselect();
        }
      });
    },
    setselect() {
      setTimeout(() => {
        this.value4 = this.options[0].projectId;
        this.getList(this.value4);
      }, 500);
    },
    getList(id) {
      console.log(id);
      this.params.projectId = id;
      console.log(this.params);
      listProto(this.params).then(res => {
        console.log("listProto", res);
        if (res.code == 200) {
          this.tableData = res.rows;
          this.total = res.total;
          this.tableData.forEach(el => {
            if (el.prototypeUrl != null) {
              el.prototypeUrl = process.env.VUE_APP_BASE_API + el.prototypeUrl;
            } else {
              el.prototypeUrl = 0;
            }
          });
        }
      });
    },
    addProrotype() {
      console.log("创建原型");
      console.log(this.value4);
      this.$refs.addPrototype.id = this.value4;
      this.$refs.addPrototype.value4 = this.value4;
      this.$refs.addPrototype.form.projectId = this.value4;
      this.$refs.addPrototype.open();
      // this.$router.push({ path: "addPrototype", query: { id: this.value4 } });
    },
    download(url) {
      console.log('down',url);
      window.open(process.env.VUE_APP_BASE_API + url);
    },
    handleUpdate(index, row) {
      console.log(index, row);
      this.$router.push({ path: "update" });
    },
    handlePrototype(index, row) {
      console.log("原型管理");
      this.$refs.protoManage.tempselectid = this.value4;
      this.$refs.protoManage.recordId = row.recordId;
      this.$refs.protoManage.arrIndex = index;
      this.$refs.protoManage.id = this.value4;
      this.$refs.protoManage.protoId = row.id;
      this.$refs.protoManage.open();

      // this.$router.push({
      //   path: "protoManage",
      //   query: { id: this.value4, protoId: row.id, arrIndex: index }
      // });
    },
    handleDoc(index, row) {
      console.log("文档管理", row, index);
      this.$refs.docManage.tempselectid = this.value4;
      this.$refs.docManage.arrIndex = index;
      this.$refs.docManage.dataDoc.prototypeId = row.id;
      this.$refs.docManage.protoId = row.id;
      // this.$refs.docManage.dialogFormVisible=true
      this.$refs.docManage.open();
      // this.$router.push({
      //   path: "docManage",
      //   query: { id: this.value4, protoId: row.id, arrIndex: index }
      // });
    },
    handleEditName(inde, row) {
      console.log("编辑名字", row);
      this.$refs.edit.id = row.id;
      this.$refs.edit.form.name = row.name;
      this.$refs.edit.open();
    },
    handleDelete(index, row) {
      console.log(index, row);
      this.$confirm("确定要删除该条内容？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(_ => {
          deleteProto(row.id).then(res => {
            if (res.code == 200) {
              this.$message.success("删除成功！");
              // this.tableData.splice(index, 1);
              this.init();
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
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 100%;
}
.el-card.is-always-shadow,
.el-card.is-hover-shadow:focus,
.el-card.is-hover-shadow {
  box-shadow: none;
}
.doc {
  /* height: 86px; */
  text-align: center;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  cursor: pointer;
}
</style>
