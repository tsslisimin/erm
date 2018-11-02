<template>
  <div class="m-main" :style="bodyStyle">
    <div class="m-main-header" :style="headerStyle">
      <slot name="header">
        <m-header></m-header>
      </slot>
    </div>
    <div class="m-main-body">
      <slot></slot>
    </div>
    <div class="m-main-footer" :style="footerStyle">
      <slot name="footer">
        <m-footer></m-footer>
      </slot>
    </div>
  </div>
</template>
<script>
import mHeader from 'js/module/components/header/header'
import mFooter from 'js/module/components/footer/footer'

export default {
  name: 'layout',
  data () {
    return {
      // app data states
    }
  },
  props: {
    headHeight: {
      type: Number,
      default: 60
    },
    footerHeight: {
      type: Number,
      default: 30
    }
  },
  computed: {
    headerStyle () {
      return {
        height: this.headHeight + 'px'
      }
    },
    bodyStyle () {
      return {
        paddingTop: this.headHeight + 'px',
        paddingBottom: this.footerHeight + 'px'
      }
    },
    footerStyle () {
      return {
        height: this.footerHeight + 'px'
      }
    }
  },
  components: {
    mHeader,
    mFooter
  }
}
</script>
<style lang="scss" scoped>
.m-main {
  min-height: 100%;
  min-width: 1200px;
  position: relative;
  .m-main-header {
    position: fixed;
    top: 0;
    width: 100%;
    z-index: 90;
  }

  .m-main-body {
    height: 100%;
    position: relative;
  }

  .m-main-footer {
    position: absolute;
    width: 100%;
    bottom: 0;
    z-index: 90;
  }
}
</style>
