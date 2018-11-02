<template>
  <div class="wrapper">
    <div class="code-con">
      <Code></Code>
    </div>
  </div>
</template>

<script>
import Code from './code'

export default {
  name: 'app',
  data () {
    return {}
  },
  components: { Code },
  methods: {},
  mounted () {
  }
}
</script>

<style lang="scss" scoped>
html,
body {
  height: 100%;
  width: 100%;
  background-color: #368AE2;
}

.wrapper {
  background: #368AE2 url('./bg.png') no-repeat center top;
  background-size: contain;
  height: 100%;
  width: 100%;
  position: relative;
  .code-con {
    position: absolute;
    left: 50%;
    top: 50%;
  }
}
</style>
