<template>
  <div class="wrapper">
    <div class="sign-con">
      <SignUp></SignUp>
    </div>
  </div>
</template>

<script>
import SignUp from './signUp'

export default {
  name: 'app',
  data () {
    return {}
  },
  components: { SignUp },
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
  .sign-con {
    position: absolute;
    left: 50%;
    top: 50%;
  }
}
</style>
