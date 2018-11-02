<template>
  <div class="wrapper">
    <Location>资助管理 > 资助名单推荐</Location>
    <div class="con">
      <Card :padding="10">
        <div class="form-con">
          <Row :gutter="10">
            <Col span="2"><p class="form-label">项目选择：</p></Col>
            <Col span="6">
              <Cascader :data="dataItems" v-model="projectItem" trigger="hover" placeholder="请选择" style="width: 300px;"></Cascader>
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
            <Button type="primary" @click="agreeMore()">批量通过</Button>
          </div>
        </div>

        <v-table v-if="listData.length" :items="listData" :header="[]">
          <template slot="t-header">
            <th slot="t-check" id="all-check" style="width: 30px;"><input type="checkbox" style="margin-top: 5px;"></th>
            <th class="left"><p><span>排名</span></p></th>
            <th class="center"><p sort-key="stuName"><span>姓名</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="gender"><span>性别</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="age"><span>年龄</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="stuno"><span>学号</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="grade"><span>年级</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="clazz"><span>班级</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="zone"><span>学段</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="diffLevelName"><span>困难等级</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="score"><span>智能得分</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 200px;"><p><span>审核</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td style="width: 30px;" class="check-box"><input type="checkbox" :data-id="props.data.stu_id" style="margin-top: 5px;"></td>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.stuName}}</td>
              <td class="center">{{props.data.gender}}</td>
              <td class="center">{{props.data.age}}</td>
              <td class="center">{{props.data.stuno}}</td>
              <td class="center">{{props.data.grade}}</td>
              <td class="center">{{props.data.clazz}}</td>
              <td class="center">{{props.data.zone}}</td>
              <td class="center">{{props.data.diffLevelName}}</td>
              <td class="center">{{props.data.score}}</td>
              <td class="center"><Button type="primary" @click="verifyAgree(props.data.stu_id)">通过</Button>&nbsp;&nbsp;&nbsp;<Button type="error" @click="verifyReject(props.data.stu_id)">调整</Button></td>
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
          <Modal v-model="rejectModal" title="调整" :ok-text="'调整'" @on-ok="rejectInfo" @on-cancel="rejectModal = false">
            <div class="edit-con">
              <Form ref="rejectEdit" :model="rejectEdit" :label-width="100" style="padding-top: 15px;">
                <FormItem label="调整困难等级" style="margin-bottom: 20px;">
                  <Select v-model="rejectEdit.diffLevel" placeholder="请选择" style="width: 300px;">
                    <Option value="0">不困难</Option>
                    <Option value="1">困难</Option>
                    <Option value="2">一般困难</Option>
                    <Option value="3">特别困难</Option>
                  </Select>
                </FormItem>
                <FormItem label="调整理由" style="margin-bottom: 20px;">
                  <Input v-model="rejectEdit.content" type="textarea" :autosize="{minRows: 5,maxRows: 20}" placeholder="请输入..." style="width: 300px;"></Input>
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
      formList: {
        name: '',
        idcard: '',
        grade: '',
        clazz: ''
      },
      gradeClazz: {},
      projectItem: [],
      dataItems: [],
      headData: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },

      // 审核意见
      editId: '',
      isMore: false,
      rejectEdit: {
        content: '',
        diffLevel: ''
      },
      rejectModal: false,

      verifyData: []
    }
  },
  components: { vTable, Location, Nodata },
  methods: {
    // 获取列表
    getList () {
      let _data = _.merge({}, this.page, {
        schFundId: this.projectItem[1]
      })

      io.post(api.fundProcess.listMy, _data, (res) => {
        let data = res.data
        this.headData = data.header
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
    // 单个审核通过
    verifyAgree (id) {
      this.isMore = false
      this.editId = id
      this.agreeInfo()
    },
    // 单个审核调整
    verifyReject (id) {
      this.rejectModal = true
      this.editId = id
    },
    // 通过
    agreeInfo () {
      if (!this.projectItem.length) {
        this.$Message.error('通过前请先选择项目')
        return
      }

      let ids = this.isMore ? this.verifyData : [this.editId]

      io.post(api.fundProcess.audit, {
        'pass': true,
        'schFundId': this.projectItem[1],
        'stuIds': ids
      }, (res) => {
        this.$Message.info(res.data.msg)
        this.getList()
      })
    },
    // 单个调整
    rejectInfo () {
      if (this.rejectEdit.diffLevel === '') {
        this.$Message.error('请选择困难等级')
        return
      }

      io.post(api.fundProcess.adjust, {
        'stuIds': [this.editId],
        'diffLevel': this.rejectEdit.diffLevel,
        'note': this.rejectEdit.content
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
    },
    // 获取班级
    getClazz () {
      io.get(api.school.querySchGradeClazzList, (res) => {
        let data = res.data
        this.gradeClazz = data.page
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
    this.getProjectList()
    this.addCheck()
    this.getClazz()
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
    .money {
      border: none;
      background-color: #f7f7f7;
      width: 80px;
      padding-left: 5px;
      padding-right: 5px;
      text-align: center;
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
