<template>
  <div class="wrapper">
    <Location>资助管理 > 认定资助学生</Location>
    <div class="con">
      <Card :padding="10">
        <div class="form-con">
          <Row :gutter="10">
            <Col span="2"><p class="form-label">姓名：</p></Col>
            <Col span="6">
              <Input v-model="formList.name" placeholder="请输入"></Input>
            </Col>
            <Col span="2"><p class="form-label">性别：</p></Col>
            <Col span="6">
              <Select v-model="formList.gender" placeholder="请选择">
                <Option value="">全部</Option>
                <Option value="1">男</Option>
                <Option value="2">女</Option>
              </Select>
            </Col>
          </Row>
        </div>
        <div class="form-con">
          <Row :gutter="10">
            <Col span="2"><p class="form-label">身份证号：</p></Col>
            <Col span="6">
              <Input v-model="formList.idcard" placeholder="请输入"></Input>
            </Col>
            <Col span="2"><p class="form-label">年龄：</p></Col>
            <Col span="3">
              <Input v-model="formList.minAge" placeholder="请输入" class="else"></Input>
            </Col>
            <Col span="3">
              <Input v-model="formList.maxAge" placeholder="请输入"></Input>
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

        <div class="do-some clearfix">
          <div class="f-fl">
            <Button type="primary" @click="agreeMore()">批量认定</Button>
            <span class="warn"><i class="iconfont" style="margin-right:5px;font-size:13px;">&#xe60f;</i>注意：认定成功的学生并不会提交到教育局审批，请到【资助审核】模块再次审查确认！</span>
          </div>
        </div>

        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="[]">
          <template slot="t-header">
            <th slot="t-check" id="all-check" style="width: 30px;"><input type="checkbox" style="margin-top: 5px;"></th>
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="name"><span>姓名</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="idcard"><span>身份证号</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="stuno"><span>学号</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 100px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td style="width: 30px;" class="check-box"><input type="checkbox" :data-id="props.data.id" style="margin-top: 5px;"></td>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.name}}</td>
              <td class="center">{{props.data.idcard}}</td>
              <td class="center">{{props.data.stuno}}</td>
              <td class="center"><Button type="primary" @click="verifyAgree(props.data.id)">认定</Button></td>
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
          <Modal v-model="agreeModal" title="认定为受助对象" :ok-text="'确定'" @on-ok="agreeInfo" @on-cancel="agreeModal = false">
            <div class="edit-con">
              <Form ref="agreeEdit" :model="agreeEdit" :label-width="80" style="padding-top: 15px;">
                <FormItem label="项目选择" style="margin-bottom: 20px;">
                  <Cascader :data="dataItems" v-model="agreeEdit.fund" trigger="hover" placeholder="请选择" style="width: 350px;"></Cascader>
                </FormItem>
                <FormItem label="意见" style="margin-bottom: 20px;">
                  <Input v-model="agreeEdit.content" type="textarea" :autosize="{minRows: 5,maxRows: 20}" placeholder="请输入..." style="width: 350px;"></Input>
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
import _ from 'lodash'
import $ from 'jquery'
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'msgSearch',
  data () {
    return {
      formList: {
        name: '',
        gender: '',
        minAge: '',
        maxAge: '',
        idcard: ''
      },
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      dataItems: [],

      // 审核意见
      editId: '',
      agreeEdit: {
        fund: [],
        content: ''
      },
      agreeModal: false,

      verifyData: [],
      isMore: false
    }
  },
  components: { vTable, Location, Nodata },
  methods: {
    // 获取列表
    getList () {
      let searchData = _.merge({}, this.page, {
        name: this.formList.name,
        gender: this.formList.gender,
        minAge: this.formList.minAge,
        maxAge: this.formList.maxAge,
        idcard: this.formList.idcard
      })

      io.get(api.student.list, searchData, (res) => {
        let data = res.data
        this.listData = data.page.list
        this.total = data.page.totalCount

        this.verifyData = []
        $('#all-check input').prop('checked', false)
        $('.check-box input').prop('checked', false)
      })
    },
    // 分页操作
    pageChange (page) {
      this.page.page = page
      this.getList()
    },
    // 认定弹框
    verifyAgree (id) {
      this.agreeModal = true
      this.isMore = false
      this.editId = id
    },
    // 认定同意
    agreeInfo () {
      if (!this.agreeEdit.fund.length) {
        return
      }

      if (this.isMore) {
        io.post(api.fundedinfo.batchSave, {
          stuIds: this.verifyData.join(),
          fundedId: this.agreeEdit.fund[1],
          suggestion: this.agreeEdit.content
        }, (res) => {
          this.$Message.info(res.data.msg)
          this.verifyData = []
          this.getList()
        })
      } else {
        io.post(api.fundedinfo.save, {
          stuId: this.editId,
          fundedId: this.agreeEdit.fund[1],
          suggestion: this.agreeEdit.content
        }, (res) => {
          this.$Message.info(res.data.msg)
          this.verifyData = []
          this.getList()
        })
      }
    },
    // 批量认定
    agreeMore () {
      if (!this.verifyData.length) {
        return
      }

      this.agreeModal = true
      this.isMore = true
    },
    // 获取项目列表
    getProjectList () {
      io.get(api.funded.schoolFundedlist, (res) => {
        let data = res.data.page.list
        let casData = []
        let typeA = { value: '1', label: '', children: [] }
        let typeB = { value: '2', label: '', children: [] }
        let typeC = { value: '3', label: '', children: [] }
        let typeD = { value: '4', label: '', children: [] }

        _.forEach(data, (o) => {
          if (o.type === '1') {
            typeA.label = o.typeName
            typeA.children.push({
              value: o.schFundId,
              label: o.name
            })
          }
          if (o.type === '2') {
            typeB.label = o.typeName
            typeB.children.push({
              value: o.schFundId,
              label: o.name
            })
          }
          if (o.type === '3') {
            typeC.label = o.typeName
            typeC.children.push({
              value: o.schFundId,
              label: o.name
            })
          }
          if (o.type === '4') {
            typeD.label = o.typeName
            typeD.children.push({
              value: o.schFundId,
              label: o.name
            })
          }
        })

        if (typeA.children.length) casData.push(typeA)
        if (typeB.children.length) casData.push(typeB)
        if (typeC.children.length) casData.push(typeC)
        if (typeD.children.length) casData.push(typeD)

        this.dataItems = casData
      })
    },
    // 添加checkbox
    addCheck () {
      $('.con').on('change', '#all-check input', (e) => {
        this.verifyData = []
        if ($(e.target).is(':checked')) {
          this.listData.forEach((item) => {
            this.verifyData.push(item.id)
          })
          $('.check-box input').prop('checked', true)
        } else {
          $('.check-box input').prop('checked', false)
        }
      })

      $('.con').on('change', '.check-box input', (e) => {
        let _id = $(e.target).data('id')
        let _idx = this.verifyData.indexOf(_id)

        if ($(e.target).is(':checked')) {
          if (_idx === -1) {
            this.verifyData.push(_id)
          }
        } else {
          this.verifyData.splice(_idx, 1)
        }
      })
    }
  },
  watch: {
    verifyData (o) {
      if (o.length === this.listData.length) {
        $('#all-check input').prop('checked', true)
      } else {
        $('#all-check input').prop('checked', false)
      }
    }
  },
  mounted () {
    this.getList()
    this.getProjectList()
    this.addCheck()
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
    .warn {
      margin-left: 20px;
      color: $color-error;
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
  }
}
</style>
