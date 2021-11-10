<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'jxGit',
  data() {
    return {
      url: 'https://gitee.com/nineoak/oaker-project-manager.git'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>