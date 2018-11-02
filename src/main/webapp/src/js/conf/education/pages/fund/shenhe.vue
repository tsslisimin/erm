<template>
  <div class="wrapper">
    <Location>资助管理 > 审核资助名单</Location>
    <div class="con">
      <Card :padding="10">
        <Form :label-width="80" style="padding-top: 15px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
          <FormItem label="选择项目" style="margin-bottom: 20px;">
            <Cascader :data="dataItems" v-model="projectItem" trigger="hover" placeholder="请选择" style="width: 300px;"></Cascader>
          </FormItem>
          <FormItem label="选择档次" v-if="subType == 113" style="margin-bottom: 20px;">
            <Select v-model="level" placeholder="请选择" style="width: 300px;">
              <Option v-for="(item, $index) in levelList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="选择学段" v-if="subType == 112" style="margin-bottom: 20px;">
            <Select v-model="schzone" placeholder="请选择" style="width: 300px;">
              <Option v-for="(item, $index) in schzoneList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="选择学校" style="margin-bottom: 20px;">
            <Select v-model="schoolName" filterable placeholder="请选择" style="width: 300px;">
              <Option v-for="item in schoolList" :value="item.id" :key="item.id">{{item.name}}</Option>
            </Select>
          </FormItem>
          <FormItem>
            <Button type="primary" @click="getList()">查 询</Button>
          </FormItem>
        </Form>

        <div class="do-some clearfix">
          <div class="f-fl">
            <Button type="primary" @click="agreeMore()">批量同意</Button>&nbsp;
            <Button type="primary" @click="rejectMore()">批量拒绝</Button>
          </div>
        </div>

        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="headData">
          <th slot="t-check" id="all-check" style="width: 30px;"><input type="checkbox" style="margin-top: 5px;"></th>
          <template slot="d-header" slot-scope="props">
            <th :class="{left:props.idx == 0,center:props.idx > 0}"><p :sort-key="props.data.colCode"><span>{{props.data.colName}}</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-header">
            <th class="center" style="width: 200px;"><p><span>审核</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td style="width: 30px;" class="check-box"><input type="checkbox" :data-id="props.data.stu_id" :data-fundid="props.data.funded_id" style="margin-top: 5px;"></td>
              <td :class="{left:$index == 0,center:$index > 0}" v-for="(el, $index) in headData" :key="$index">{{props.data[el.colCode]}}</td>
              <td class="center"><Button type="primary" @click="verifyAgree(props.data.stu_id)">同意</Button>&nbsp;&nbsp;&nbsp;<Button type="error" @click="verifyReject(props.data.stu_id)">拒绝</Button></td>
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
          <Modal v-model="agreeModal" title="同意" :ok-text="'同意'" @on-ok="agreeInfo" @on-cancel="agreeModal = false">
            <div class="edit-con">
              <Form ref="agreeEdit" :model="agreeEdit" :label-width="80" style="padding-top: 15px;">
                <FormItem label="审核意见" style="margin-bottom: 20px;">
                  <Input v-model="agreeEdit.content" type="textarea" :autosize="{minRows: 5,maxRows: 20}" placeholder="请输入..." style="width: 350px;"></Input>
                </FormItem>
              </Form>
            </div>
          </Modal>
        </div> -->

        <div class="edit">
          <Modal v-model="rejectModal" title="拒绝" :ok-text="'拒绝'" @on-ok="rejectInfo" @on-cancel="rejectModal = false">
            <div class="edit-con">
              <Form ref="rejectEdit" :model="rejectEdit" :label-width="80" style="padding-top: 15px;">
                <FormItem label="审核意见" style="margin-bottom: 20px;">
                  <Input v-model="rejectEdit.content" type="textarea" :autosize="{minRows: 5,maxRows: 20}" placeholder="请输入..." style="width: 350px;"></Input>
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
  name: 'manageShenhe',
  data () {
    return {
      fundedId: '',
      projectItem: [],
      dataItems: [],
      schoolName: '',
      schoolList: [],
      headData: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      level: '',
      schzone: '',
      levelList: [],
      schzoneList: [],
      subType: '',
      schFundList: [],

      // 审核意见
      editId: '',
      isMore: false,
      rejectEdit: {
        content: ''
      },
      rejectModal: false,

      verifyData: []
    }
  },
  components: { vTable, Location, Nodata },
  methods: {
    // 获取列表
    getList () {
      if (!this.schoolName && !this.projectItem.length) {
        return
      }

      if (this.subType == 112 && !this.schzone) {
        return
      }

      if (this.subType == 113 && !this.level) {
        return
      }

      let _data = _.merge({}, this.page, {
        schId: this.schoolName,
        fundId: this.projectItem[1],
        level: this.subType == 113 ? this.level : '',
        schzone: this.subType == 112 ? this.schzone : '',
        status: 10
      })

      io.post(api.fundProcess.list, _data, (res) => {
        let data = res.data
        this.headData = data.header
        this.listData = data.page.list
        this.total = data.page.totalCount

        if (this.listData.length) {
          this.fundedId = this.listData[0]['funded_id']
        }

        this.verifyData = []
        $('#all-check input').prop('checked', false)
        $('.check-box input').prop('checked', false)
      }, (res) => {
        this.$Message.error(res.data.msg)
      })
    },
    // 分页操作
    pageChange (page) {
      this.page.page = page
      this.getList()
    },
    // 单个审核通过
    verifyAgree (id) {
      this.isMore = false
      this.editId = id
      this.agreeInfo()
    },
    // 单个审核拒绝
    verifyReject (id) {
      this.isMore = false
      this.rejectModal = true
      this.editId = id
    },
    // 同意
    agreeInfo () {
      if (!this.projectItem.length) {
        this.$Message.error('通过前请先选择项目')
        return
      }

      let ids = this.isMore ? this.verifyData : [this.editId]

      io.post(api.fundProcess.audit, {
        'pass': true,
        'schFundId': this.fundedId,
        'stuIds': ids
      }, (res) => {
        this.$Message.info(res.data.msg)
        this.getList()
      })
    },
    // 拒绝
    rejectInfo () {
      if (!this.rejectEdit.content) {
        this.$Message.error('请输入拒绝的理由')
        return
      }

      let ids = this.isMore ? this.verifyData : [this.editId]

      io.post(api.fundProcess.audit, {
        'pass': false,
        'schFundId': this.fundedId,
        'stuIds': [this.editId],
        'remark': this.rejectEdit.content
      }, (res) => {
        this.$Message.info(res.data.msg)
        this.getList()
      })
    },
    // 批量同意
    agreeMore () {
      this.isMore = true
      this.agreeInfo()
    },
    // 批量拒绝
    rejectMore () {
      this.isMore = true
      this.rejectModal = true
    },
    // 获取学校列表
    getSchoolList () {
      io.get(api.school.listByFundedId, {
        fundedId: this.projectItem[1]
      }, (res) => {
        let data = res.data
        this.schoolList = data.page.list
      })
    },
    // 获取项目列表
    getProjectList () {
      io.get(api.funded.list, (res) => {
        let data = res.data.page.list
        let casData = []
        let typeA = { value: '1', label: '', children: [] }
        let typeB = { value: '2', label: '', children: [] }
        let typeC = { value: '3', label: '', children: [] }
        let typeD = { value: '4', label: '', children: [] }

        this.schFundList = data

        _.forEach(data, (o) => {
          if (o.type === '1') {
            typeA.label = o.typeName
            typeA.children.push({
              value: o.id,
              label: o.name
            })
          }
          if (o.type === '2') {
            typeB.label = o.typeName
            typeB.children.push({
              value: o.id,
              label: o.name
            })
          }
          if (o.type === '3') {
            typeC.label = o.typeName
            typeC.children.push({
              value: o.id,
              label: o.name
            })
          }
          if (o.type === '4') {
            typeD.label = o.typeName
            typeD.children.push({
              value: o.id,
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
    // 获取学区
    getZone () {
      io.get(api.dict.fundDict, {
        type: 25
      }, (res) => {
        let data = res.data
        this.schzoneList = data.dict['25']
      })
    },
    // 获取资助水平
    getLevel () {
      io.get(api.dict.fundDict, {
        type: 24
      }, (res) => {
        let data = res.data
        this.levelList = data.dict['24']
      })
    },
    // 添加checkbox
    addCheck () {
      $('.con').on('change', '#all-check input', (e) => {
        this.verifyData = []
        if ($(e.target).is(':checked')) {
          this.listData.forEach((item) => {
            this.verifyData.push(item.stu_id)
          })
          $('.check-box input').prop('checked', true)
        } else {
          $('.check-box input').prop('checked', false)
        }
      })

      $('.con').on('change', '.check-box input', (e) => {
        let _id = +$(e.target).attr('data-id')
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
    projectItem (o) {
      let _item = _.find(this.schFundList, (n) => o[1] == n.id)
      this.subType = _item.subtype
      this.level = ''
      this.schzone = ''
      this.getSchoolList()
    },
    verifyData (o) {
      if (o.length === this.listData.length) {
        $('#all-check input').prop('checked', true)
      } else {
        $('#all-check input').prop('checked', false)
      }
    }
  },
  mounted () {
    this.getProjectList()
    this.addCheck()
    this.getZone()
    this.getLevel()
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
