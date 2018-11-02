<template>
  <p class="location"><i class="iconfont" style="font-size: 14px;">&#xe60a;</i> 当前位置：<span><slot></slot></span></p>
</template>

<script>
  export default {
    name: 'location'
  }
</script>

<style lang="scss" scoped>
  @import "../main";

  .location {
    font-size: 14px;
    color: $font-tips-color;
    padding-top: 10px;
    padding-bottom: 8px;
    margin-bottom: 10px;
    span {
      color: $font-default-color;
    }
    border-bottom: 1px dashed $border-default-color;
  }
</style>
