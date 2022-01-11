<template>
  <div class="main">
    <el-row :gutter="24">
      <el-col :span="2"
        ><div class="grid-content">
          <!-- <div
            :class="[{ liststyle: item.showcolor }]"
            v-for="item in options"
            :key="item.projectId"
            @click="changeproject(item)"
          >
            <span>{{ item.projectName }}</span>
            <el-divider></el-divider>
          </div> -->
          <el-tree
            :data="options"
            :props="options"
            :load="loadNode"
            highlight-current
            @node-click="nodeClick"
            @check-change="handleCheckChange"
          >
          </el-tree></div
      ></el-col>
      <el-col :span="22"
        ><div class="grid-content bg-purple-light">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span
                ><template>
                  <!-- <el-select
                    style="width:30%"
                    v-model="value4"
                    clearable
                    placeholder="请选择项目"
                    @change="getList(value4)"
                  >
                    <el-option
                      v-for="item in options"
                      :key="item.projectId"
                      :label="item.projectName"
                      :value="item.projectId"
                    >
                    </el-option>
                  </el-select>  -->
                </template></span
              >
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
                    type="index"
                    label="序号"
                    width="50"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="name"
                    label="名称"
                    
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="docList.docName"
                    label="文档"
                    
                    align="center"
                  >
                    <template slot-scope="scope">
                      <div class="">
                        <div v-if="scope.row.docList.length == 0">
                          <p>
                            <el-button type="text" size="small" :disabled="true"
                              >暂无文档</el-button
                            >
                          </p>
                        </div>
                        <div
                          class="docf"
                          v-else
                          v-for="(item, index) in scope.row.docList"
                          v-show="index < 5"
                          :key="item.id"
                        >
                          <p class="doc" @click="download(item.docUrl)">
                            {{ item.docName }}
                          </p>
                          <el-button
                            style="padding:3px;"
                            size="mini"
                            type="text"
                            @click="download(item.docUrl)"
                            >下载</el-button
                          >
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="prototypeUrl"
                    label="效果图"
                    
                    align="center"
                  >
                    <template slot-scope="scope">
                      <a v-if="scope.row.sketchId == null">
                        <el-button type="text" size="small" :disabled="true"
                          >暂无效果图</el-button
                        >
                      </a>
                      <a v-else @click="goShowImg(scope.row)">
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
                    prop="prototypeUrl"
                    label="访问原型"
                   
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
                  <!-- <el-table-column
                    prop="createTime"
                    label="更新时间"
                    width="200"
                    align="center"
                  >
                  </el-table-column> -->
                  <!-- <el-table-column
                    prop="createUserName"
                    label="操作人"
                    width="120"
                    align="center"
                  >
                  </el-table-column> -->

                  <!-- <el-table-column prop="" label="分享" width="120" align="center">
              <template>
                <el-button type="text" size="small">公开分享</el-button>
              </template>
            </el-table-column> -->
                  <el-table-column
                   
                    label="管理"
                    width="430"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <el-button
                        type="primary"
                        size="mini"
                        plain
                        @click="handleDoc(scope.$index, scope.row)"
                        v-hasPermi="['pr:doc:query']"
                        >文档管理</el-button
                      >
                      <el-button
                        type="primary"
                        size="mini"
                        plain
                        @click="handlePrototype(scope.$index, scope.row)"
                        v-hasPermi="['pr:proto:record:list']"
                        >原型管理</el-button
                      >
                      <el-button
                        type="primary"
                        size="mini"
                        plain
                        @click="handleImgmange(scope.$index, scope.row)"
                        v-hasPermi="['pr:sketch:upload']"
                        >效果图管理</el-button
                      >
                      <el-button
                        type="primary"
                        size="mini"
                        plain
                        @click="handleEditName(scope.$index, scope.row)"
                        v-hasPermi="['pr:proto:update']"
                        >编辑</el-button
                      >
                      <el-button
                        type="danger"
                        size="mini"
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
          /></div
      ></el-col>
    </el-row>

    <Edit ref="edit"></Edit>
    <AddPrototype ref="addPrototype"></AddPrototype>
    <DocManage ref="docManage"></DocManage>
    <ProtoManage ref="protoManage"></ProtoManage>
    <ImgMange ref="imgMange"></ImgMange>
  </div>
</template>

<script>
import Edit from "../edit";
import AddPrototype from "../addPrototype";
import DocManage from "../docManage";
import ProtoManage from "../protoManage";
import ImgMange from "../imgMange";
// import { getIndexProjectStat } from "@/api/dashboard";
import {
  listProto,
  deleteProto,
  delProtoRecord,
  getUserProjectAll
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
      value4: "",
      props: {
        label: "name",
        children: "zones"
      },
      count: 1
    };
  },
  components: {
    Edit,
    DocManage,
    ProtoManage,
    AddPrototype,
    ImgMange
  },
  created() {
    this.init();
  },
  methods: {
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate);
    },
    handleNodeClick(data) {
      console.log(data);
    },
    loadNode(node, resolve) {
      // console.log("loadNode", node, resolve);
      // if (node.level === 0) {
      //   return resolve([{ name: "region1" }, { name: "region2" }]);
      // }
      // if (node.level > 3) return resolve([]);
      // var hasChild;
      // if (node.data.name === "region1") {
      //   hasChild = true;
      // } else if (node.data.name === "region2") {
      //   hasChild = false;
      // } else {
      //   hasChild = Math.random() > 0.5;
      // }
      // setTimeout(() => {
      //   var data;
      //   if (hasChild) {
      //     data = [
      //       {
      //         name: "zone" + this.count++
      //       },
      //       {
      //         name: "zone" + this.count++
      //       }
      //     ];
      //   } else {
      //     data = [];
      //   }
      //   resolve(data);
      // }, 500);
    },
    nodeClick(node) {
      console.log(node);
      this.getList(node.id);
      this.value4 = node.id;
    },
    goShowImg(row) {
      console.log("goShowImg", row);

      let newpage = this.$router.resolve({
        name: "ShowImg",
        query: {
          id: row.sketchId
        }
      });
      window.open(newpage.href, "_blank");
    },
    init() {
      // console.log(this.i);
      getUserProjectAll().then(res => {
        // console.log(res);
        if (res.code == 200) {
          res.data.forEach(el => {
            el.showcolor = false;
            el.label = el.projectName;
            el.id = el.projectId;
          });
          this.options = res.data;
          this.options[0].showcolor = true;
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
      // console.log(id);
      this.params.projectId = id;
      // console.log(this.params);
      listProto(this.params).then(res => {
        // console.log("listProto", res);
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
    changeproject(item) {
      // console.log(item);
      this.$forceUpdate();
      this.options.forEach(el => (el.showcolor = false));
      this.$set(item, "showcolor", !item.showcolor);
      this.getList(item.projectId);
    },
    addProrotype() {
      // console.log("创建原型");
      // console.log(this.value4);
      this.$refs.addPrototype.id = this.value4;
      this.$refs.addPrototype.value4 = this.value4;
      this.$refs.addPrototype.form.projectId = this.value4;
      this.$refs.addPrototype.open();
      // this.$router.push({ path: "addPrototype", query: { id: this.value4 } });
    },
    download(url) {
      // console.log("down", url);
      window.open(process.env.VUE_APP_BASE_API + url);
    },
    handleUpdate(index, row) {
      // console.log(index, row);
      this.$router.push({ path: "update" });
    },
    handlePrototype(index, row) {
      // console.log("原型管理");
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
      // console.log("文档管理", row, index);
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
    handleImgmange(index, row) {
      this.$refs.imgMange.tempselectid = this.value4;
      this.$refs.imgMange.arrIndex = index;
      this.$refs.imgMange.dataDoc.prototypeId = row.id;
      this.$refs.imgMange.protoId = row.id;
      this.$refs.imgMange.open();
    },
    handleEditName(inde, row) {
      // console.log("编辑名字", row);
      this.$refs.edit.id = row.id;
      this.$refs.edit.form.name = row.name;
      this.$refs.edit.open();
    },
    handleDelete(index, row) {
      // console.log(index, row);
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
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
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
.docf {
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-between;
}
.doc {
  /* height: 86px; */
  // width: 120px;
  text-align: center;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  cursor: pointer;
}
.liststyle {
  color: #1890ff;
}
</style>
