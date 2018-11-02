<template>
  <div class="code">
    <Card :padding="10" style="width:320px">
      <div class="bd">
        <h3>修改密码 / Reset Code</h3>
        <Form ref="formList" :model="formList" :rules="ruleList">
          <FormItem prop="password">
            <Input type="password" size="large" v-model="formList.password" placeholder="请输入新密码">
              <i class="iconfont" style="color: #aaa;" slot="prepend">&#xe63d;</i>
            </Input>
          </FormItem>
          <FormItem prop="passwordCom">
            <Input type="password" size="large" v-model="formList.passwordCom" placeholder="再次输入密码">
              <i class="iconfont" style="color: #aaa;" slot="prepend">&#xe63d;</i>
            </Input>
          </FormItem>
          <FormItem>
            <Button type="primary" size="large" long @click="submit('formList')">确 认</Button>
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
  name: 'code',
  data () {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请填写密码'))
      } else {
        if (this.formList.passwordCom !== '') {
          // 对第二个密码框单独验证
          this.$refs.formList.validateField('passwordCom')
        }
        callback()
      }
    }
    const validatePassCheck = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.formList.password) {
        callback(new Error('两次密码不一致，请重新输入'))
      } else {
        callback()
      }
    }
    return {
      formList: {
        password: '',
        passwordCom: ''
      },
      ruleList: {
        password: [
          { validator: validatePass, trigger: 'blur' },
          { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ],
        passwordCom: [
          { validator: validatePassCheck, trigger: 'blur' },
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
          io.post(api.admin.code, {
            password: this.formList.password
          }, (res) => {
            this.$Message.success('密码修改成功！')
            window.location.href = window.location.origin + '' + api.sign.loginOut
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
.code {
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
