<template>
  <div class="mian">
    <!-- <div class="demo-image__lazy">
      <el-image
        v-for="url in urls"
        :key="url"
        :src="url"
        lazy
        :preview-src-list="srcList"
      ></el-image>
    </div> -->
    <div class="slide_pc">
      <el-carousel
        
        height="37vw"
        trigger="click"
        @change="loadAnimate"
        arrow="always"
        :autoplay="false"
      >
        <el-carousel-item v-for="(item, index) in urls" :key="index">
          <figure>
            <img :src="item.fileUrl" class="elImg" />
          </figure>
        </el-carousel-item>
      </el-carousel>
      <!-- <slider-show :slides="sliders" :inv="invTime"></slider-show> -->
    </div>
    <el-divider></el-divider>
    <div class="nav-btn">
      <span>导航：</span>
      <span><el-button type="primary" size="mini">全部下载</el-button></span>
    </div>
    <div class="demo-image">
      <div class="block" v-for="(fit,index) in urls" :key="index">
        <span class="demonstration">{{ fit.fileName }}</span>
        <el-image
          style="width: 100px; height: 100px"
          :src="fit.fileUrl"
        ></el-image>
      </div>
    </div>
  </div>
</template>

<script>
import { listProtoSketchFiles } from "@/api/prototype/prototype";
export default {
  data() {
    return {
      urls: [],
      id: null
    };
  },
  mounted() {
    this.setStyle();
  },
  created() {
    // this.setStyle();
    this.id = this.$route.query.id;
    this.init();
  },
  methods: {
    setStyle() {
      let leftNode = document.getElementsByClassName("sidebar-container");
      let headerNode = document.getElementsByClassName("fixed-header");
      let contentNode = document.getElementsByClassName("hasTagsView");
      let appmainNode = document.getElementsByClassName("app-main");
      leftNode[0].style.display = "none";
      headerNode[0].style.display = "none";
      contentNode[0].style.marginLeft = "0px";
      appmainNode[0].style.paddingTop = "0px";
      // console.log(headerNode);
    },
    loadAnimate() {
      console.log("loadAnimate");
    },
    init() {
      console.log(this.id);
      listProtoSketchFiles(this.id).then(res => {
        console.log("listProtoSketch", res);
        if (res.code == 200) {
          this.urls = res.data;
          this.urls.forEach(ele => {
            ele.fileUrl = process.env.VUE_APP_BASE_API + ele.fileUrl;
          });
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
::v-deep .sidebar-container.has-logo {
  display: none !important;
}
::v-deep .main-container.hasTagsView {
  margin-left: 200px !important;
}
figure {
  display: flex;
  justify-content: center;
  align-items: center;
}
.elImg {
  width: auto;
  height: 100%;
}
.demo-image {
  display: flex;
  .block {
    margin: 0 5px;
  }
}
.nav-btn {
  width: 90%;
  margin: 0 auto;
  padding-bottom: 20px;
}
</style>
