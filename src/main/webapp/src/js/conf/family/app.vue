<template>
  <div class="wrapper">
    <div class="con">
      <Family></Family>
    </div>
  </div>
</template>

<script>
import Family from './family'

export default {
  name: 'app',
  data () {
    return {}
  },
  components: { Family },
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
