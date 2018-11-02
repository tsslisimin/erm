<template>
  <div class="notice">
    <Form ref="formList" :model="formList" :rules="ruleList" :label-width="80" style="padding-top: 15px;">
      <FormItem label="标题" prop="title" style="margin-bottom: 20px;">
        <Input v-model="formList.title" placeholder="请输入" style="width: 300px;"></Input>
      </FormItem>
      <FormItem label="内容" prop="content" style="margin-bottom: 20px;">
        <Input v-model="formList.content" type="textarea" :autosize="{minRows: 5,maxRows: 20}" placeholder="请输入..." style="width: 300px;"></Input>
      </FormItem>
      <FormItem label="推送对象" style="margin-bottom: 20px;">
        <Checkbox v-model="formList.toFamily">向家长发送信息</Checkbox>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="submit('formList')">提 交</Button>
      </FormItem>
    </Form>
  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'

export default {
  name: 'notice',
  data () {
    return {
      formList: {
        title: '',
        content: '',
        toFamily: false
      },
      ruleList: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
      },
      formData: null
    }
  },
  components: {},
  methods: {
    // 提交
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          io.post(api.notice.save, {
            title: this.formList.title,
            describ: this.formList.content,
            type: this.formList.toFamily ? 3 : 4
          }, (res) => {
            this.$Message.success('新增通知成功！')
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    }
  },
  mounted () {
  }
}
</script>

<style lang="scss" scoped>
  .notice {
    margin-bottom: 10px;
  }
</style>
