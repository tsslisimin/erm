<template>
  <m-layout>
    <m-header slot="header"></m-header>
    <m-body></m-body>
  </m-layout>
</template>

<script>
import mLayout from 'js/module/components/base/layout'
import mHeader from 'js/module/components/header/header'
import mBody from './body'

export default {
  name: 'app',
  data () {
    return {}
  },
  components: { mLayout, mHeader, mBody },
  methods: {},
  mounted () {
    this.$Message.config({
      duration: 3
    })
  }
}
</script>

<style lang="scss" scoped>
html,
body {
  height: 100%;
  width: 100%;
}
</style>
