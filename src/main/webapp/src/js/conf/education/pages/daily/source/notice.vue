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
        <Select v-model="formList.target" filterable multiple style="width: 300px;">
          <Option v-for="(item, $index) in listData" :value="item.id" :key="$index">{{item.name}}</Option>
        </Select>
      </FormItem>
      <FormItem label="添加附件" style="margin-bottom: 20px;">
        <input type="file" id="files" style="line-height: 20px;" accept="*" name="file" @change="getFile()">
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
        target: []
      },
      ruleList: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
      },
      listData: [],
      formData: null
    }
  },
  components: {},
  methods: {
    // 提交
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          if (this.formData != null) {
            io.post(api.file.upload, this.formData, (res) => {
              io.post(api.notice.save, {
                title: this.formList.title,
                describ: this.formList.content,
                url: res.data,
                type: 1,
                schools: this.formList.target.join()
              }, (res) => {
                this.$Message.success('新增通知成功！')
              })
            }, (e) => {
              this.$Message.error('文件上传出错！')
            }, (e) => {
              this.$Message.error('文件上传出错！')
            })
          } else {
            io.post(api.notice.save, {
              title: this.formList.title,
              describ: this.formList.content,
              type: 1,
              schools: this.formList.target.join()
            }, (res) => {
              this.$Message.success('新增通知成功！')
            })
          }
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取学校列表
    getList () {
      io.get(api.school.list, (res) => {
        let data = res.data
        this.listData = data.page.list
      })
    },
    // 上传文件
    getFile () {
      let file = document.getElementById('files')

      this.formData = new FormData()
      this.formData.append('file', file.files[0])
    }
  },
  mounted () {
    this.getList()
  }
}
</script>

<style lang="scss" scoped>
  .notice {
    margin-bottom: 10px;
  }
</style>
