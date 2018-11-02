<template>
  <div class="wrapper">
    <div class="con">
      <m-body></m-body>
    </div>
  </div>
</template>

<script>
import mBody from './body'

export default {
  name: 'app',
  data () {
    return {}
  },
  components: { mBody },
  methods: {},
  mounted () {
  }
}
</script>

<style lang="scss" scoped>
html,
body {
  width: 100%;
}

.wrapper {
  width: 100%;
  background-color: #fff;
  .con {
    width: 100%;
  }
}
</style>
