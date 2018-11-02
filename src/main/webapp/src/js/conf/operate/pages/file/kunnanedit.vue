<template>
  <div class="wrapper">
    <Location>困难学生修改</Location>
    <div class="con">
      <Card :padding="10">
        <Form ref="formList" :model="formList" :rules="ruleList" :label-width="0" style="padding-top: 15px;">
          <FormItem style="margin-bottom: 20px;">
            <Row>
              <Col span="12">
                <FormItem :label-width="140" prop="name" label="学生姓名" style="margin-bottom: 20px;">
                  <Input v-model="formList.name" disabled placeholder="请输入" style="width: 300px;"></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem :label-width="140" prop="idcard" label="学生身份证号" style="margin-bottom: 20px;">
                  <Input v-model="formList.idcard" disabled placeholder="请输入" style="width: 300px;"></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem :label-width="140" prop="isPoor" label="贫困类型" style="margin-bottom: 20px;">
                  <Select v-model="formList.isPoor" placeholder="请选择" style="width: 300px;">
                    <Option v-for="(item, $index) in isPoorData" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                  </Select>
                </FormItem>
              </Col>
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
            </Row>
          </FormItem>
          <FormItem :label-width="140" style="margin-bottom: 20px;">
            <Button type="primary" @click="submit('formList')">保 存</Button>
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
      stuId: '',
      dataItems: [],
      formList: {
        name: '',
        idcard: '',
        isPoor: ''
      },
      ruleList: {
        name: [{ required: true, message: ' ', trigger: 'blur' }],
        idcard: [{ required: true, message: ' ', trigger: 'blur' }],
        isPoor: [{ required: true, message: ' ', trigger: 'change' }]
      },
      isPoorData: [],
      modalFile: false,
      formDatas: []
    }
  },
  components: { Location },
  methods: {
    // 提交
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let _data = {
            id: this.stuId,
            idcard: this.formList.idcard,
            name: this.formList.name,
            isPoor: this.formList.isPoor,
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

          io.post(api.student.edit, _data, (res) => {
            this.$Message.success(res.data.msg)
          }, (res) => {
            this.$Message.info(res.data.msg)
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取项目的动态表单
    getForm () {
      io.get(api.student.diffStuInfo, {
        stuId: this.stuId
      }, (res) => {
        let data = res.data
        this.formDatas = data.diffStuInfo.diffInfo
        this.formList.name = data.diffStuInfo.stuInfo.name
        this.formList.idcard = data.diffStuInfo.stuInfo.idcard
        this.formList.isPoor = data.diffStuInfo.stuInfo.isPoor
      })
    },
    // 获取下拉数据
    getSubList () {
      io.get(api.dict.list, (res) => {
        let data = res.data.dict

        this.isPoorData = data['8']
      })
    }
  },
  mounted () {
    this.stuId = this.$route.params.stuId || ''
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
