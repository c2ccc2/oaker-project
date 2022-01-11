<template>
  <div class="main">
    <!-- <h1>系统名称: "{{ loginVersion.系统名称 }}"</h1> -->
    <h1>version: "{{ loginVersion.version }}"</h1>
  </div>
</template>

<script>
import { loginversion } from "@/api/login";

export default {
  data() {
    return {
      loginVersion: []
    };
  },
  created() {
      this.init()
  },
  methods: {
    init() {
      loginversion().then(res => {
       // console.log(res);
        this.loginVersion = res;
       // console.log(this.loginVersion);
       // console.log(this.loginVersion.date);
      });
    }
  }
};
</script>

<style></style>
