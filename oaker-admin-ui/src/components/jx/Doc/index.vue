<template>
  <div>
    <svg-icon icon-class="question" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'jxDoc',
  data() {
    return {
      // url: 'http://192.168.0.21/#/index'
      url: 'http://www.jxkj.com'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>