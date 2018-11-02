<template>
  <div class="wrapper">
    <Location>学生管理 > 困难学生录入</Location>
    <div class="con">
      <Card :padding="10">
        <Form ref="formList" :model="formList" :rules="ruleList" :label-width="0" style="padding-top: 15px;">
          <FormItem style="margin-bottom: 20px;">
            <Row>
              <Col span="12">
                <FormItem :label-width="140" prop="name" label="学生姓名" style="margin-bottom: 20px;">
                  <Input v-model="formList.name" placeholder="请输入" style="width: 300px;"></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem :label-width="140" prop="idcard" label="学生身份证号" style="margin-bottom: 20px;">
                  <Input v-model="formList.idcard" placeholder="请输入" style="width: 300px;"></Input>
                </FormItem>
              </Col>
              <!--<Col span="12">-->
                <!--<FormItem :label-width="140" prop="isPoor" label="贫困类型" style="margin-bottom: 20px;">-->
                  <!--<Select v-model="formList.isPoor" placeholder="请选择" style="width: 300px;">-->
                    <!--<Option v-for="(item, $index) in isPoorData" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
                  <!--</Select>-->
                <!--</FormItem>-->
              <!--</Col>-->
              <Col span="12" v-for="(item, $index) in formDatas" :key="$index">
                <FormItem :label-width="140" :label="item.ermFieldEntity.fieldName" style="margin-bottom: 20px;">
                  <Input v-if="item.ermFieldEntity.type == 1" v-model="item.ermFieldEntity.value" placeholder="请输入" style="width: 300px;"></Input>
                  <Input v-if="item.ermFieldEntity.type == 2" v-model="item.ermFieldEntity.value" placeholder="请输入" style="width: 300px;"></Input>
                  <Select v-if="item.ermFieldEntity.type == 3" filterable v-model="item.ermFieldEntity.value" placeholder="请选择" style="width: 300px;">
                    <Option v-for="el in item.ermFieldEntity.fieldVal" :key="el.id" :value="el.valKey">{{el.valVal}}</Option>
                  </Select>
                  <Select v-if="item.ermFieldEntity.type == 4" multiple filterable v-model="item.ermFieldEntity.value" placeholder="请选择" style="width: 300px;">
                    <Option v-for="el in item.ermFieldEntity.fieldVal" :key="el.id" :value="el.valKey">{{el.valVal}}</Option>
                  </Select>
                </FormItem>
              </Col>
              <!--<Col span="12">-->
              <!--<FormItem :label-width="140" prop="helper" label="帮扶负责人" style="margin-bottom: 20px;">-->
                <!--<Input v-model="formList.helper" placeholder="请输入" style="width: 300px;"></Input>-->
              <!--</FormItem>-->
              <!--</Col>-->
            </Row>
          </FormItem>
          <FormItem :label-width="140" style="margin-bottom: 20px;">
            <Button type="primary" @click="submit('formList')">提 交</Button>&nbsp;&nbsp;&nbsp;
            <Button type="ghost" @click="modalFile = true">导入数据</Button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span v-if="formUrl">家庭经济情况调查表：<a :href="formUrl" target="_blank">{{formUrl}}</a></span>
            <Modal v-model="modalFile" title="导入数据" :ok-text="'确定导入'" @on-ok="dataIn" @on-cancel="modalFile = false">
              <div class="data-in">
                <h3>请按以下步骤进行数据导入</h3>
                <p><span>1.</span>下载模板，以便导入数据 <Button type="primary" size="small" @click="downFile()">下载模板</Button></p>
                <p><span>2.</span>已填好数据，导入数据 <input type="file" id="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" @change="getFile()"></p>
              </div>
            </Modal>
          </FormItem>
        </Form>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import _ from 'lodash'
import $ from 'jquery'
import { domain } from 'js/module/api/domain'

export default {
  name: 'manageStudent',
  data () {
    return {
      dataItems: [],
      formList: {
        name: '',
        idcard: '',
        // isPoor: '',
      },
      ruleList: {
        name: [{ required: true, message: ' ', trigger: 'blur' }],
        idcard: [
          { required: true, message: ' ', trigger: 'blur' },
          { type: 'string', min: 18, message: ' ', trigger: 'blur' }
        ],
        // isPoor: [{ required: true, message: ' ', trigger: 'change' }],
       // helper: [{ required: true, message: ' ', trigger: 'blur' }]
      },
      isPoorData: [],
      modalFile: false,
      formDatas: [],
      formUrl: '',
      fileDataMore: null
    }
  },
  components: { Location },
  methods: {
    // 提交
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let _data = {
            idcard: this.formList.idcard,
            name: this.formList.name,
            // isPoor: this.formList.isPoor,
            // helper:this.formList.helper,
            stuValues: []
          }

          _.forEach(this.formDatas, (item) => {
            if (item.ermFieldEntity.value) {
              _data.stuValues.push({
                fieldId: item.fieldId,
                fieldValVal: item.ermFieldEntity.type == 1 || item.ermFieldEntity.type == 2 ? item.ermFieldEntity.value : '',
                fieldValKey: item.ermFieldEntity.type == 3 ? item.ermFieldEntity.value : '',
                fieldValKeyList: item.ermFieldEntity.type == 4 ? item.ermFieldEntity.value : []
              })
            }
          })

          io.post(api.student.saveDiffStudent, _data, (res) => {
            this.$Message.success(res.data.msg)
          }, (res) => {
            this.$Message.info(res.data.msg)
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取下拉数据
    getSubList () {
      io.get(api.dict.list, (res) => {
        let data = res.data.dict

        this.isPoorData = data['8']
      })
    },
    // 获取项目的动态表单
    getForm (id) {
      io.get(api.school.querySchField, (res) => {
        let data = res.data.page
        this.formDatas = data
        this.formUrl = 'http://home.xiaoxuezha.com/family/index.html?schId=' + this.formDatas[0]['schId']
      })
    },
    // input 工具方法
    generatorInput (obj) {
      let result = ''
      for (var key in obj) {
        result += '<input type="hidden" name = "' + key + '" value="' + obj[key] + '">'
      }
      return result
    },
    // 下载模板
    downFile () {
      io.get(api.schfundfield.template, (res) => {
        let url = res.data.fileName

        io.post(api.file.download, {
          fileName: res.data.fileName
        }, (res) => {
          let blob = res
          let reader = new FileReader()
          reader.readAsDataURL(blob)
          reader.onload = (e) => {
            let a = document.createElement('a')
            a.download = url
            a.href = e.target.result
            $('body').append(a)
            a.click()
            $(a).remove()
          }
        }, (e) => {}, (e) => {}, { responseType: 'blob' })

        // $(`<form action="http://erm.comezx.com${api.file.download}" method="get">${this.generatorInput({ fileName: res.data.fileName })}</form>`).appendTo('body').submit().remove()
      })
    },
    // 上传文件
    getFile () {
      let file = document.getElementById('file')
      let reg = /\.xl.{1,2}$/
      let fileName = file.files[0].name

      if (!fileName.match(reg)) {
        this.$Message.error('文件格式错误！')
        return
      }

      this.formData = new FormData()
      this.formData.append('file', file.files[0])
    },
    // 数据导入
    dataIn () {
      this.$Spin.show()
      io.post(api.student.importAudit, this.formData, (res) => {
        this.$Spin.hide()
        this.$Message.info('导入数据成功' + res.data.resultData.successNum + '条，失败' + res.data.resultData.failNum + '条')
        this.getList()

        if (res.data.resultData.failList && res.data.resultData.failList.length) {
          res.data.resultData.failList.forEach(item => {
            this.$Message.error({
              content: item,
              duration: 0,
              closable: true
            })
          })
        }
      }, (e) => {
        this.$Spin.hide()
      }, (e) => {
        this.$Spin.hide()
      })
    }
  },
  mounted () {
    this.getSubList()
    this.getForm()
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
    .page {
      margin-top: 10px;
    }
    .do-some {
      margin-bottom: 10px;
    }
    .form-label {
      color: $form-label-color;
      text-align: right;
      font-size: 12px;
    }
  }
}

.data-in {
  h3 {
    font-weight: normal;
    font-size: 16px;
    text-align: center;
    color: $color-warning;
  }
  p {
    margin-top: 20px;
    font-size: 14px;
    color: $font-primary-color;
  }
}
</style>
