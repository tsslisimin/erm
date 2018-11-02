<template>
  <div class="sign-up">
    <Card :padding="10" style="width:320px">
      <div class="bd">
        <h3>用户登录 / User Login</h3>
        <Form ref="formList" :model="formList" :rules="ruleList">
          <FormItem prop="user">
            <Input type="text" size="large" v-model="formList.user" placeholder="用户名">
              <i class="iconfont" style="color: #aaa;" slot="prepend">&#xe630;</i>
            </Input>
          </FormItem>
          <FormItem prop="password">
            <Input type="password" size="large" v-model="formList.password" placeholder="密码">
              <i class="iconfont" style="color: #aaa;" slot="prepend">&#xe63d;</i>
            </Input>
          </FormItem>
          <FormItem>
            <Button type="primary" size="large" long @click="submit('formList')">登 录</Button>
          </FormItem>
        </Form>
      </div>
    </Card>
  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import cookie from 'js/lib/util/cookie'

export default {
  name: 'signUp',
  data () {
    return {
      formList: {
        user: '',
        password: ''
      },
      ruleList: {
        user: [{ required: true, message: '请填写用户名', trigger: 'blur' }],
        password: [
          { required: true, message: '请填写密码', trigger: 'blur' },
          { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ]
      }
    }
  },
  components: {},
  methods: {
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          // 获取token
          io.post(api.sign.login, {
            headers: {
              'Content-Type': 'application/json',
              'X-Requested-With': 'XMLHttpRequest'
            },
            username: this.formList.user,
            password: this.formList.password
          }, (res) => {
            let data = res.data
            let token = 'Bearer ' + data.token

            cookie.set('token', token, { path: '/' })
            // 校验用户
            io.get(api.sign.user, (res) => {
              let data = res.data
              let authority = data.user.authorities[0]['authority']

              if (authority === 'ROLE_ADMIN' || authority === 'ROLE_EB') {
                window.location.href = '../education/index.html'
              }
              if (authority === 'ROLE_SCH') {
                window.location.href = '../school/index.html'
              }
              if (authority === 'ROLE_OPER') {
                window.location.href = '../operate/index.html'
              }
            })
          }, (e) => {
            this.$Message.error(e.message)
          }, (e) => {
            this.$Message.error(e.message)
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    }
  },
  mounted () {}
}
</script>

<style lang="scss" scoped>
.sign-up {
  margin-left: 50px;
  margin-top: -135px;
  .bd {
    h3 {
      text-align: center;
      font-size: 18px;
      font-weight: normal;
      margin-top: 20px;
      margin-bottom: 20px;
    }
    form {
      margin-left: 10px;
      margin-right: 10px;
    }
  }
}
</style>
