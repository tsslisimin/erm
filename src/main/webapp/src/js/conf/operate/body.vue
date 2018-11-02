<template>
  <div class="main-body">
    <e-menu :user="'ROLE_OPER'"></e-menu>
    <div class="main-r">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import eMenu from 'js/module/components/menu/menu'

export default {
  data () {
    return {

    }
  },
  components: { eMenu },
  methods: {},
  mounted () {
  }
}
</script>

<style lang="scss" scoped>
  .main-body {
    .main-r {
      margin-left: 200px;
    }
  }
</style>
