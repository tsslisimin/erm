<template>
  <div class="project">
    <div class="form-con">
      <Row :gutter="10">
        <Col span="2"><p class="form-label">年份：</p></Col>
        <Col span="6">
          <DatePicker v-model="formList.year" type="year" placeholder="请选择" style="width: 100%;"></DatePicker>
        </Col>
        <Col span="2"><p class="form-label">状态：</p></Col>
        <Col span="6">
          <Select v-model="formList.status" filterable placeholder="请选择">
            <Option v-for="(item, $index) in statusData" :value="item.statusCode" :key="$index">{{item.statusName}}</Option>
          </Select>
        </Col>
      </Row>
    </div>

    <div class="form-con">
      <Row :gutter="10">
        <Col span="2"><p class="form-label">项目名称：</p></Col>
        <Col span="6">
          <Select v-model="formList.fundName" filterable placeholder="请选择">
            <Option v-for="(item, $index) in fundData" :value="item.id" :key="$index">{{item.fundedName}}</Option>
          </Select>
        </Col>
      </Row>
    </div>

    <div class="form-con" style="padding-bottom: 20px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
      <Row :gutter="10">
        <Col span="2">&nbsp;</Col>
        <Col span="6">
          <Button type="primary" @click="getList()">查 询</Button>
        </Col>
      </Row>
    </div>

    <!-- <div class="fund-tips">
      <h4>操作说明：</h4>
      <p>1.首先进行自定义指标的配置， 完成配置后，可启动项目，项目启动后，进入财政厅放款流程环节；</p>
      <p>2.财政厅放款后，可填写或备案项目资助总金额(注意：金额一旦填写将不可修改)；</p>
      <p>3.财政厅放款完成后，即进入资助申报期，此时可下载[学生资助申请表]并通知班主任进行受助学生推选；</p>
      <p>4.项目完成后，可通过[上传]功能上传公示照片以备案存档。</p>
    </div> -->

    <div class="do-some clearfix">
      <div class="f-fl">
        <Button type="primary" @click="doConfig()">考核指标配置</Button>
      </div>
    </div>

    <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="[]">
      <template slot="t-header">
        <th class="left" style="width: 50px;"><p><span>序号</span></p></th>
        <th class="left" style="width: 150px;"><p sort-key="fundedName"><span>项目名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="left" style="width: 250px;"><p><span>贫困考核指标</span></p></th>
        <th class="center"><p sort-key="year"><span>年份</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="count"><span>资助名额</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="left"><p sort-key="totalMoney"><span>项目总金额</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="status"><span>状态</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <!-- <th class="center" style="width: 200px;"><p><span>操作</span></p></th> -->
      </template>
      <template slot="t-body" slot-scope="props">
        <tr>
          <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
          <td class="left">{{props.data.fundedName}}</td>
          <td class="left">
            <div style="max-height: 100px;overflow: auto;">
              <p class="field" v-for="(item, $index) in props.data.schFundFields" :key="$index">
                <Poptip trigger="hover" placement="right">
                  <Button type="text">{{item.ermFieldEntity.fieldName}}({{item.ermFieldEntity.weight}})</Button>
                  <div slot="content">
                    <p v-if="item.ermFieldEntity.fieldVal.length" v-for="(el, $index) in item.ermFieldEntity.fieldVal" :key="$index"><span>{{el.valVal}}：</span><span>{{el.valWeight}}</span></p>
                    <p v-if="!item.ermFieldEntity.fieldVal.length"><span>{{item.ermFieldEntity.fieldName}}：</span><span>{{item.ermFieldEntity.weight}}</span></p>
                  </div>
                </Poptip>
              </p>
            </div>
          </td>
          <td class="center">{{props.data.year}}</td>
          <td class="center">{{props.data.count}}</td>
          <td class="left"><input type="text" placeholder="请输入" v-if="props.data.status == 5" class="money" :class="'money-' + props.data.id" :value="props.data.totalMoney"><Poptip confirm placement="top-end" @on-ok="changeMoney(props.data.id)">
              <Button v-if="props.data.status == 5" type="info">确认</Button>
              <div slot="title">确认后就不能修改金额</div>
            </Poptip><span v-if="props.data.status != 5">{{props.data.totalMoney}}</span></td>
          <td class="center"><span class="status">{{props.data.statusName}}</span></td>
          <!-- <td class="center">
            <Button style="margin-right: 10px;" type="primary" v-if="props.data.status <= 4" @click="addCondition(props.data.id)">指标配置</Button><Tooltip content="启动之后便不可进行指标配置！" placement="bottom-end">
              <Button v-if="props.data.status == 4" type="success" @click="start(props.data.id)">启动</Button>
            </Tooltip><Tooltip content="通知班主任推选并下载学生申请表" placement="bottom-end">
              <Button v-if="props.data.status == 5 || props.data.status == 6" type="ghost" @click="downExl(props.data.id)">下载</Button>
            </Tooltip><Poptip placement="top-end">
              <Button style="margin-left: 10px;" v-if="props.data.status >= 5" type="info">上传</Button>
              <div slot="title">上传公示照片</div>
              <div class="upload" slot="content">
                <input type="file" :id="'files' + props.data.id" accept="*" name="file" @change="getPhoto(props.data.id)">
              </div>
            </Poptip>
          </td> -->
        </tr>
      </template>
    </v-table>

    <div class="page clearfix" v-if="total > 10">
      <div class="f-fr">
        <Page :total="total" show-total @on-change="pageChange"></Page>
      </div>
    </div>

    <Nodata v-if="!listData.length"></Nodata>

    <!-- <div class="edit">
      <Modal v-model="fieldEdit" title="修改权重" @on-ok="updateField" @on-cancel="fieldEdit = false">
        <div class="edit-con">
          <Form ref="fieldForm" :model="fieldForm" :label-width="250" style="padding-top: 15px;">
            <FormItem v-if="fieldForm.type == 1 || fieldForm.type == 2" :label="fieldForm.fieldName" style="margin-bottom: 20px;">
              <Input v-model="fieldForm.weight" placeholder="请输入" style="width: 50px;"></Input>
            </FormItem>
            <FormItem v-if="fieldForm.type == 3" v-for="(item, $index) in fieldForm.fieldVal" :key="$index" :label="item.valVal" style="margin-bottom: 20px;">
              <Input v-model="item.valWeight" placeholder="请输入" style="width: 50px;"></Input>
            </FormItem>
          </Form>
        </div>
      </Modal>
    </div> -->

    <!-- <div class="edit">
      <Modal v-model="modalEdit" title="添加指标" @on-ok="updateInfo" @on-cancel="modalEdit = false">
        <div class="edit-con">
          <Form ref="formEdit" :model="formEdit" :rules="ruleEdit" :label-width="80" style="padding-top: 15px;">
            <FormItem label="指标名称" prop="formName" style="margin-bottom: 20px;">
              <Input v-model="formEdit.formName" placeholder="请输入" style="width: 300px;"></Input>
            </FormItem>
            <FormItem label="指标类型" prop="formType" style="margin-bottom: 20px;">
              <Select v-model="formEdit.formType" placeholder="请选择" style="width: 300px;">
                <Option value="1">文本</Option>
                <Option value="2">数字</Option>
                <Option value="3">选择</Option>
              </Select>
            </FormItem>
            <FormItem v-if="formEdit.formType == 1 || formEdit.formType == 2" label="指标权重" style="margin-bottom: 20px;">
              <Input v-model="formEdit.formWeight" placeholder="请输入" style="width: 300px;"></Input>
            </FormItem>
            <FormItem v-if="formEdit.formType == 3" label="指标权重" style="margin-bottom: 20px;">
              <div v-if="subBase.length">
                <Row v-for="(item, $index) in subBase" :key="$index">
                  <Col span="6"><p class="form-label" style="text-align: center;">选项名：</p></Col>
                  <Col span="6">
                    <Input v-model="item.valVal" placeholder="请输入"></Input>
                  </Col>
                  <Col span="6"><p class="form-label" style="text-align: center;">权重：</p></Col>
                  <Col span="6">
                    <Input v-model="item.valWeight" placeholder="请输入"></Input>
                  </Col>
                </Row>
              </div>
              <Button type="ghost" @click="addChild()">添加选项</Button>
            </FormItem>
          </Form>
        </div>
      </Modal>
    </div> -->

  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import vTable from 'js/module/components/table/table'
import _ from 'lodash'
import $ from 'jquery'
import Nodata from 'js/module/components/nodata/nodata'
import { domain } from 'js/module/api/domain'

// let subItem = {
//   valVal: '',
//   valWeight: ''
// }

export default {
  name: 'project',
  props: {
    'proType': {
      type: Number
    },
    'hasNew': {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      formList: {
        year: '',
        status: '',
        fundName: ''
      },
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      statusData: [],
      fundData: []
      // fundId: '',

      // 添加指标
      // formEdit: {
      //   formName: '',
      //   formType: 1,
      //   formWeight: ''
      // },
      // ruleEdit: {
      //   formName: [{ required: true, message: '请输入名称', trigger: 'blur' }],
      //   formType: [{ required: true, message: '请选择类型', trigger: 'change' }]
      // },
      // modalEdit: false,

      // 编辑指标
      // fieldForm: {},
      // fieldId: '',
      // fieldEdit: false,

      // 动态数据
      // dynamicList: [],
      // subBase: [],
      // fileData: null
    }
  },
  components: { vTable, Nodata },
  methods: {
    // 提交
    // submit (name) {
    //   this.$refs[name].validate(valid => {
    //     if (valid) {
    //       // let _data = _.map(this.dynamicList, (item) => {
    //       //   return {
    //       //     fundId: this.formList.itemName,
    //       //     fieldId: item.id,
    //       //     weight: item.weight
    //       //   }
    //       // })

    //       // io.post(api.schfundfield.batchSave, _data, (res) => {
    //       //   this.$Message.success('保存提交成功！')
    //       // })
    //     } else {
    //       this.$Message.error('表单验证失败！')
    //     }
    //   })
    // },
    // 获取当前资助列表
    getList () {
      let searchData = _.merge({}, this.page, {
        type: this.proType,
        year: this.formList.year ? new Date(this.formList.year).getFullYear() : '',
        id: this.formList.fundName,
        status: this.formList.status
      })

      io.get(api.schoolFunded.fundList, searchData, (res) => {
        let data = res.data
        this.listData = data.page.list
      })
    },
    // 获取状态
    getStatus () {
      io.get(api.schoolFunded.fundStatus, (res) => {
        let data = res.data
        this.statusData = data.fundStatus
      })
    },
    // 获取项目名称
    getFund () {
      io.get(api.schoolFunded.listSimple, {
        type: this.proType
      }, (res) => {
        let data = res.data
        this.fundData = data.page.list
      })
    },
    // 添加指标保存
    // updateInfo () {
    //   let _data = {}

    //   _data['fieldName'] = this.formEdit.formName
    //   _data['type'] = this.formEdit.formType
    //   _data['weight'] = 0
    //   _data['fieldVal'] = []
    //   _data['fundId'] = this.fundId

    //   if (this.formEdit.formType != 3) {
    //     _data['weight'] = this.formEdit.formWeight
    //   } else {
    //     _.forEach(this.subBase, (item) => {
    //       if (item.valVal) {
    //         _data['fieldVal'].push({
    //           valVal: item.valVal,
    //           valWeight: item.valWeight
    //         })
    //       }
    //       if (item.valWeight) {
    //         _data['weight'] += parseInt(item.valWeight)
    //       }
    //     })
    //   }

    //   io.post(api.field.save, _data, (res) => {
    //     this.$Message.success('添加成功！')
    //     this.getList()
    //   }, (e) => {
    //     this.$Message.info(e.data.msg)
    //   }, (e) => {
    //     this.$Message.error(e.data.msg)
    //   })
    // },
    // 添加指标
    // addCondition (id) {
    //   this.modalEdit = true
    //   this.fundId = id
    // },
    // 编辑指标
    // editCondition (id, dynamicList) {
    //   let item = _.find(dynamicList, (o) => o.id === id)
    //   this.fieldForm = item
    //   this.fieldEdit = true
    //   this.fieldId = id
    // },
    // 编辑指标保存
    // updateField () {
    //   if (this.fieldForm.type == 3) {
    //     this.fieldForm.weight = 0

    //     _.forEach(this.fieldForm.fieldVal, (item) => {
    //       if (item.valWeight) {
    //         this.fieldForm.weight += parseInt(item.valWeight)
    //       }
    //     })
    //   }

    //   io.post(api.field.edit, this.fieldForm, (res) => {
    //     this.$Message.success('编辑指标成功！')
    //     this.getList()
    //   })
    // },
    // 删除指标
    // delCondition (id) {
    //   io.post(api.field.delete, [id], (res) => {
    //     this.$Message.success('删除成功！')
    //     this.getList()
    //   })
    // },
    // 添加子选项
    // addChild () {
    //   let _subItem = _.clone(subItem)
    //   this.subBase.push(_subItem)
    // },
    // 更改金额
    changeMoney (id) {
      io.post(api.fundProcess.money, {
        id: id,
        totalMoney: $('.money-' + id).val()
      }, (res) => {
        this.$Message.success(res.data.msg)
        this.getList()
      })
    },
    // 启动项目
    // start (id) {
    //   io.post(api.fundProcess.fundStart + '/' + id, (res) => {
    //     this.$Message.success(res.data.statusName)
    //     this.getList()
    //   })
    // },
    // 下载excel
    // downExl (id) {
    //   io.get(domain.host + '/static/资助申请表.docx', (res) => {
    //     let blob = res
    //     let reader = new FileReader()
    //     reader.readAsDataURL(blob)
    //     reader.onload = (e) => {
    //       let a = document.createElement('a')
    //       a.download = '资助申请表.docx'
    //       a.href = e.target.result
    //       $('body').append(a)
    //       a.click()
    //       $(a).remove()
    //     }
    //   }, (e) => {}, (e) => {}, { responseType: 'blob' })

    //   // $(`<form action="http://erm.comezx.com/static/资助申请表.docx" method="get"></form>`).appendTo('body').submit().remove()
    // },
    // 上传图片
    // getPhoto (id) {
    //   let _id = 'files' + id
    //   let file = document.getElementById(_id)

    //   this.fileData = new FormData()
    //   this.fileData.append('file', file.files[0])

    //   io.post(api.file.upload, this.fileData, (res) => {
    //     let _url = res.data

    //     io.post(api.schoolFunded.edit, {
    //       id: id,
    //       publicUrl: _url
    //     }, (res) => {
    //       this.$Message.success('公示照片上传成功')
    //     })
    //   }, (e) => {
    //     this.$Message.error('文件上传出错！')
    //   }, (e) => {
    //     this.$Message.error('文件上传出错！')
    //   })
    // }
    // 考核指标配置
    doConfig () {
      this.$router.push({ name: 'fund/zhibiao' })
      window.location.reload()
    }
  },
  mounted () {
    this.getStatus()
    this.getFund()
    this.getList()
  }
}
</script>

<style lang="scss" scoped>
@import '../../../../../module/components/main';
.project {
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
  .fund-tips {
    background-color: #f5f5f5;
    margin-bottom: 10px;
    padding: 10px;
    h4 {
      color: $color-error;
      font-weight: normal;
      margin-bottom: 5px;
    }
  }
  .form-con {
    margin-top: 15px;
    margin-bottom: 15px;
    .form-label {
      color: $form-label-color;
      height: 32px;
      line-height: 32px;
      text-align: right;
      font-size: 12px;
    }
    .else {
      position: relative;
      &::after {
        content: '-';
        position: absolute;
        right: -6px;
        top: 5px;
      }
    }
  }
  .money {
    border: none;
    background-color: #f7f7f7;
    width: 80px;
    padding:4px 5px;
    text-align: center;
    border-radius: 3px;
    border: 1px dashed $color-error;
    margin-right: 5px;
  }
  .status {
    color: $color-success;
  }
  .field {
    background-color: #f5f5f5;
    padding: 0 5px;
    i {
      color: $color-error;
      cursor: pointer;
    }
  }
  .dynamic {
    margin-bottom: 10px;
    .form-item {
      height: 40px;
      line-height: 40px;
      padding-left:15px;
      padding-right:15px;
      border: 1px dashed $color-warning-active;
      background-color: #f5f5f5;
      border-radius: 3px;
      margin-right: 10px;
      color: $color-primary-blue;
      font-size: 14px;
      font-weight: bold;
      position: relative;
      i {
        color: $color-error;
        position: absolute;
        right: 5px;
        top: -12px;
        cursor: pointer;
      }
    }
    a {
      color: $color-primary-blue;
      font-size: 12px;
    }
  }
}
</style>
