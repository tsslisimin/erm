<template>
  <div class="header clearfix">
    <div class="h-l f-fl">
      <!-- <p><span>后台管理系统</span></p> -->
      <p><span>慈利县学生资助管理系统</span></p>
    </div>
    <div class="h-r f-fr">
      <p><span class="author">[{{user}}]</span><span class="user"><i class="iconfont">&#xe630;</i> <span class="u-name">{{username}}</span></span> <a class="sign-out" href="/erm/api/auth/logout"><i class="iconfont" style="font-size:18px;">&#xe651;</i> 退出</a> <a class="sign-out" href="/code/index.html"><i class="iconfont" style="margin-left:10px;">&#xe631;</i> 修改密码</a></p>
    </div>
  </div>
</template>

<script>
  import io from 'js/module/api/io'
  import { api } from 'js/module/api/url'

  export default {
    name: 'header',
    data () {
      return {
        username: '',
        user: ''
      }
    },
    components: {},
    methods: {
      getUser () {
        io.get(api.sign.user, (res) => {
          let data = res.data
          this.username = data.user.username
          if (data.school) {
            if (data.user.authorities[0]['authority'] === 'ROLE_SCH') {
              this.user = data.school.name + ' -- 校长'
            } else {
              this.user = data.school.name
            }
          } else {
            this.user = '教育局'
          }
        })
      }
    },
    mounted () {
      this.getUser()
    }
  }
</script>

<style lang="scss" scoped>
  @import '../main';

  .header {
    width: 100%;
    height: 60px;
    background-color: $color-white;
    -moz-box-shadow:0px 2px 3px $box-shadow;
    -webkit-box-shadow:0px 2px 3px $box-shadow;
    box-shadow:0px 2px 3px $box-shadow;
    .h-l {
      height: 60px;
      line-height: 60px;
      p {
        padding-left: 50px;
        font-size: 24px;
        color: $font-primary-color;
        background: url('./logo.png') no-repeat 10px 13px;
      }
    }
    .h-r {
      height: 60px;
      line-height: 60px;
      p {
        padding-right: 10px;
        font-size: 14px;
        color: $font-tips-color;
        .user {
          margin-left: 10px;
          margin-right: 10px;
          color: $font-describe-color;
          .u-name {
            color: $color-primary-blue;
            font-weight: bold;
          }
        }
        .sign-out {
          color: $font-describe-color;
        }
        .author {
          font-size: 12px;
          color: $color-warning;
        }
      }
    }
  }
</style>
