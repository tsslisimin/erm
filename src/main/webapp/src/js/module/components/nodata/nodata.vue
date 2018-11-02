<template>
  <div class="no-data">
    <p><i class="iconfont">&#xe642;</i></p>
    <p>对不起，暂无数据 ~ </p>
  </div>
</template>

<script>
export default {
  name: 'nodata'
}
</script>

<style lang="scss" scoped>
.no-data {
  margin-top: 30px;
  margin-bottom: 30px;
  p {
    text-align: center;
    font-size: 16px;
    margin-top: 10px;
    i {
      font-size: 40px;
    }
  }
}
</style>
