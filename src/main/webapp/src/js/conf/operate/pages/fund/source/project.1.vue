<template>
  <div class="project">
    <Form ref="formList" :model="formList" :rules="ruleList" :label-width="80" style="padding-top: 15px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
      <FormItem label="项目选择" prop="itemName" style="margin-bottom: 20px;">
        <Select v-model="formList.itemName" filterable placeholder="请选择" style="width: 300px;">
          <Option v-for="item in itemList" :value="item.schFundId" :key="item.schFundId">{{item.name}}</Option>
        </Select>
      </FormItem>
      <FormItem label="项目描述" style="margin-bottom: 20px;">
        <Input v-model="formList.itemContent" type="textarea" :autosize="{minRows: 5,maxRows: 10}" style="width: 300px;"></Input>
      </FormItem>
      <FormItem label="指标设置" style="margin-bottom: 20px;">
        <div v-if="dynamicList.length" class="dynamic clearfix">
          <span class="form-item f-fl" v-for="(item, $index) in dynamicList" :key="$index">{{item.fieldName}}
            <Poptip trigger="hover" placement="right">
              <Button type="text" @click="editCondition(item.id)">权重({{item.weight}})</Button>
              <div slot="content">
                <p v-if="item.fieldVal.length" v-for="(el, $index) in item.fieldVal" :key="$index"><span>{{el.valVal}}：</span><span>{{el.valWeight}}</span></p>
                <p v-if="!item.fieldVal.length"><span>{{item.fieldName}}：</span><span>{{item.weight}}</span></p>
              </div>
            </Poptip>
            <i @click="delCondition(item.id)" title="删除">x</i>
          </span>
        </div>
        <Button type="ghost" @click="addCondition()">添加指标</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="submit('formList')">保存并提交</Button>
      </FormItem>
    </Form>

    <div class="do-some clearfix">
      <div class="f-fr">
        <Input placeholder="搜索" style="width: 300px">
          <Button slot="append"><i class="iconfont" style="font-size: 12px;">&#xe60c;</i></Button>
        </Input>
      </div>
    </div>

    <v-table v-if="listData.length" :items="listData" :header="[]">
      <template slot="t-header">
        <th class="left"><p><span>序号</span></p></th>
        <th class="left"><p sort-key="name"><span>项目名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="creator"><span>创建者</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="describ"><span>项目描述</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="status"><span>状态</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
      </template>
      <template slot="t-body" slot-scope="props">
        <tr>
          <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
          <td class="left">{{props.data.name}}</td>
          <td class="center">{{props.data.creator}}</td>
          <td class="center">{{props.data.describ}}</td>
          <td class="center">
            <p v-if="props.data.status === 1 || props.data.status === 2"><Button type="ghost" @click="statusChange(props.data.id)">启动</Button></p>
            <p v-if="props.data.status !== 1 && props.data.status !== 2">{{props.data.statusName}}</p>
          </td>
        </tr>
      </template>
    </v-table>

    <div class="page clearfix" v-if="total > 10">
      <div class="f-fr">
        <Page :total="total" show-total @on-change="pageChange"></Page>
      </div>
    </div>

    <Nodata v-if="!listData.length"></Nodata>

    <div class="edit">
      <Modal v-model="fieldEdit" title="修改权重" @on-ok="updateField" @on-cancel="fieldEdit = false">
        <div class="edit-con">
          <Form ref="fieldForm" :model="fieldForm" :label-width="80" style="padding-top: 15px;">
            <FormItem v-if="fieldForm.type == 1 || fieldForm.type == 2" :label="fieldForm.fieldName" style="margin-bottom: 20px;">
              <Input v-model="fieldForm.weight" placeholder="请输入" style="width: 300px;"></Input>
            </FormItem>
            <FormItem v-if="fieldForm.type == 3" v-for="(item, $index) in fieldForm.fieldVal" :key="$index" :label="item.valVal" style="margin-bottom: 20px;">
              <Input v-model="item.valWeight" placeholder="请输入" style="width: 300px;"></Input>
            </FormItem>
          </Form>
        </div>
      </Modal>
    </div>

    <div class="edit">
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
    </div>

  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import vTable from 'js/module/components/table/table'
import _ from 'lodash'
import Nodata from 'js/module/components/nodata/nodata'

let subItem = {
  valVal: '',
  valWeight: ''
}

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
        itemName: '',
        itemContent: ''
      },
      ruleList: {
        itemName: [{ required: true, type: 'number', message: '请选择项目', trigger: 'change' }]
      },
      itemList: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },

      // 添加指标
      formEdit: {
        formName: '',
        formType: 1,
        formWeight: ''
      },
      ruleEdit: {
        formName: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        formType: [{ required: true, message: '请选择类型', trigger: 'change' }]
      },
      modalEdit: false,

      // 编辑指标
      fieldForm: {},
      fieldId: '',
      fieldEdit: false,

      // 动态数据
      dynamicList: [],
      subBase: []
    }
  },
  components: { vTable, Nodata },
  methods: {
    // 提交
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let _data = _.map(this.dynamicList, (item) => {
            return {
              fundId: this.formList.itemName,
              fieldId: item.id,
              weight: item.weight
            }
          })

          io.post(api.schfundfield.batchSave, _data, (res) => {
            this.$Message.success('保存提交成功！')
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取当前资助列表
    getList () {
      io.get(api.funded.schoolFundedlist, {
        type: this.proType,
        status: '1,2'
      }, (res) => {
        let data = res.data
        this.itemList = data.page.list
        this.listData = data.page.list
      })
    },
    // 添加指标保存
    updateInfo () {
      let _data = {}

      _data['fieldName'] = this.formEdit.formName
      _data['type'] = this.formEdit.formType
      _data['weight'] = 0
      _data['fieldVal'] = []
      _data['fundId'] = this.formList.itemName

      if (this.formEdit.formType != 3) {
        _data['weight'] = this.formEdit.formWeight
      } else {
        _.forEach(this.subBase, (item) => {
          if (item.valVal) {
            _data['fieldVal'].push({
              valVal: item.valVal,
              valWeight: item.valWeight
            })
          }
          if (item.valWeight) {
            _data['weight'] += parseInt(item.valWeight)
          }
        })
      }

      io.post(api.field.save, _data, (res) => {
        this.$Message.success('添加成功！')
        this.getIndexList(this.formList.itemName)
      })
    },
    // 添加指标
    addCondition () {
      this.modalEdit = true
    },
    // 编辑指标
    editCondition (id) {
      let item = _.find(this.dynamicList, (o) => o.id === id)
      this.fieldForm = item
      this.fieldEdit = true
      this.fieldId = id
    },
    // 编辑指标保存
    updateField () {
      if (this.fieldForm.type == 3) {
        this.fieldForm.weight = 0

        _.forEach(this.fieldForm.fieldVal, (item) => {
          if (item.valWeight) {
            this.fieldForm.weight += parseInt(item.valWeight)
          }
        })
      }

      io.post(api.field.edit, this.fieldForm, (res) => {
        this.$Message.success('编辑指标成功！')
        this.getIndexList(this.formList.itemName)
      })
    },
    // 删除指标
    delCondition (id) {
      io.post(api.field.delete, [id], (res) => {
        this.$Message.success('删除成功！')
        this.getIndexList(this.formList.itemName)
      })
    },
    // 添加子选项
    addChild () {
      let _subItem = _.clone(subItem)
      this.subBase.push(_subItem)
    },
    // 获取当前项目指标
    getIndexList (id) {
      let item = _.find(this.itemList, (o) => o.schFundId === id)
      this.formList.itemContent = item.describ

      io.get(api.field.list, {
        fundId: id
      }, (res) => {
        let data = res.data
        this.dynamicList = data.page.list
      })
    }
  },
  watch: {
    'formList.itemName' (o) {
      this.getIndexList(o)
    }
  },
  mounted () {
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
