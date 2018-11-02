<template>
  <div class="footer">
    <p>Copyright ©2017 版权所有 湘ICP备12312312号 湘公网安备35622674356456号</p>
  </div>
</template>

<script>
  export default {
    name: 'footer'
  }
</script>

<style lang="scss" scoped>
  @import '../main';

  .footer {
    margin-left: 200px;
    background-color: $color-primary-blue-active;
    p {
      color: #fff;
      height: 30px;
      line-height: 30px;
      text-align: center;
    }
  }
</style>
