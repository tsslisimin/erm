<template>
  <div class="wrapper">
    <Location>资助管理 > 指标管理</Location>
    <div class="con">
      <Card :padding="10">
        <div class="do-some clearfix">
          <div class="f-fl">
            <Button type="primary" @click="addCondition()">添加指标</Button>
          </div>
        </div>

        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="[]">
          <template slot="t-header">
            <th class="left"><p><span>序号</span></p></th>
            <th class="left"><p><span>指标名称</span></p></th>
            <th class="left"><p><span>指标权重</span></p></th>
            <th class="center"><p><span>指标类型</span></p></th>
            <th class="center"><p><span>是否可用</span></p></th>
            <th class="center" style="width: 200px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="left">{{props.data.ermFieldEntity.fieldName}}</td>
              <td class="left">
                <span>
                  <Poptip trigger="hover" placement="right">
                    <Button type="text" @click="editCondition(props.data.id)">{{props.data.ermFieldEntity.weight}}</Button>
                    <div slot="content">
                      <p v-if="props.data.ermFieldEntity.fieldVal.length" v-for="(el, $index) in props.data.ermFieldEntity.fieldVal" :key="$index"><span>{{el.valVal}}：</span><span>{{el.valWeight}}</span></p>
                      <p v-if="!props.data.ermFieldEntity.fieldVal.length"><span>{{props.data.ermFieldEntity.fieldName}}：</span><span>{{props.data.ermFieldEntity.weight}}</span></p>
                    </div>
                  </Poptip>
                </span>
              </td>
              <td class="center">{{props.data.ermFieldEntity.type == 1 ? '文本' : props.data.ermFieldEntity.type == 2 ? '数字' : props.data.ermFieldEntity.type == 3 ? '单选' : '多选'}}</td>
              <td class="center">{{props.data.flag == 1 ? '可用' : '不可用'}}</td>
              <td class="center"><Button type="primary" @click="editCondition(props.data.id)">编辑</Button>&nbsp;&nbsp;&nbsp;<Button type="error" v-if="props.data.flag == 1" @click="delCondition(props.data.id)">禁用</Button><Button type="ghost" v-if="props.data.flag == 0" @click="startCondition(props.data.id)">启用</Button></td>
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
                    <Option value="3">单选</Option>
                    <Option value="4">多选</Option>
                  </Select>
                </FormItem>
                <FormItem v-if="formEdit.formType == 1 || formEdit.formType == 2" label="指标权重" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.formWeight" placeholder="请输入" style="width: 300px;"></Input>
                </FormItem>
                <FormItem v-if="formEdit.formType == 3 || formEdit.formType == 4" label="指标权重" style="margin-bottom: 20px;">
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

      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import vTable from 'js/module/components/table/table'
import Nodata from 'js/module/components/nodata/nodata'
import _ from 'lodash'

let subItem = {
  valVal: '',
  valWeight: ''
}

export default {
  name: 'zhibiao',
  data () {
    return {
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

      subBase: []
    }
  },
  components: { Location, vTable, Nodata },
  methods: {
    // 获取指标列表
    getList () {
      io.get(api.schoolKpi.list, this.page, (res) => {
        let data = res.data

        this.listData = data.page.list
        this.total = data.page.totalCount
      })
    },
    // 分页操作
    pageChange (page) {
      this.page.page = page
      this.getList()
    },
    // 添加指标保存
    updateInfo () {
      let _data = {}

      _data['fieldName'] = this.formEdit.formName
      _data['type'] = this.formEdit.formType
      _data['weight'] = 0
      _data['fieldVal'] = []

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

      io.post(api.schoolKpi.save, _data, (res) => {
        this.$Message.success('添加成功！')
        this.getList()
      })
    },
    // 添加指标
    addCondition () {
      this.modalEdit = true
    },
    // 禁用指标
    delCondition (id) {
      io.post(api.schoolKpi.delete, [id], (res) => {
        this.$Message.success('禁用成功！')
        this.getList()
      })
    },
    // 启用指标
    startCondition (id) {
      io.post(api.schoolKpi.active, [id], (res) => {
        this.$Message.success('启用成功！')
        this.getList()
      })
    },
    // 添加子选项
    addChild () {
      let _subItem = _.clone(subItem)
      this.subBase.push(_subItem)
    },
    // 修改指标
    editCondition (id) {
      let item = _.find(this.listData, (o) => o.id === id)
      this.fieldForm = item.ermFieldEntity
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

      io.post(api.schoolKpi.edit, this.fieldForm, (res) => {
        this.$Message.success('编辑指标成功！')
        this.getList()
      })
    }
  },
  mounted () {
    this.getList()
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
  }
}
</style>
