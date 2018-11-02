<template>
  <div class="wrapper">
    <Location>资助管理 > 公示</Location>
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
          <div class="f-fl" v-if="publicStatus == 0">
            <Button type="primary" @click="dataOut()">导出公示</Button>
            <span style="margin-left: 10px;color: #f68f70;font-size: 16px;font-weight: 700;">* 首次导出信息进行公示，5个工作日后才能进行上传公示照片并提交到教育局审核</span>
          </div>
          <div class="f-fl" v-if="publicStatus == 1">
            <span style="font-size: 12px;margin-left: 15px;">上传公示照片：</span>
            <input type="file" id="files" style="line-height: 20px;" accept="*" name="file" @change="getFile()">
            <Button type="primary" v-if="publicImg != ''" @click="shenhe()">提交审核</Button>
            <Button type="primary" disabled v-else>提交审核</Button>
          </div>
        </div>

        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="headData">
          <th slot="t-check" id="all-check" style="width: 30px;"><input type="checkbox" style="margin-top: 5px;"></th>
          <template slot="d-header" slot-scope="props">
            <th :class="{left:props.idx == 0,center:props.idx > 0}"><p :sort-key="props.data.colCode"><span>{{props.data.colName}}</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td style="width: 30px;" class="check-box"><input type="checkbox" :data-id="props.data.stu_id" style="margin-top: 5px;"></td>
              <td :class="{left:$index == 0,center:$index > 0}" v-for="(el, $index) in headData" :key="$index">{{props.data[el.colCode]}}</td>
            </tr>
          </template>
        </v-table>

        <div class="page clearfix" v-if="total > 10">
          <div class="f-fr">
            <Page :total="total" show-total @on-change="pageChange"></Page>
          </div>
        </div>

        <Nodata v-if="!listData.length"></Nodata>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import vTable from 'js/module/components/table/table'
import $ from 'jquery'
import _ from 'lodash'
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'manageGongshi',
  data () {
    return {
      projectItem: [],
      schFundList: [],
      publicStatus: 0,
      publicImg: '',
      dataItems: [],
      headData: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      verifyData: []
    }
  },
  components: { vTable, Location, Nodata },
  methods: {
    // 获取列表
    getList () {
      let _data = _.merge({}, this.page, {
        schFundId: this.projectItem[1],
        status: 9
      })

      let _item = _.find(this.schFundList, (o) => o.schFundId == this.projectItem[1])

      if (typeof _item !== 'undefined') {
        this.publicStatus = _item.publicStatus
        if (_item.publicImg) {
          this.publicImg = _item.publicImg
        }
      }

      io.post(api.fundProcess.list, _data, (res) => {
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
    // 获取项目列表
    getProjectList () {
      io.get(api.funded.schoolFundedlist, (res) => {
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

        this.getList()
      })
    },
    // 导出公示
    dataOut () {
      if (!this.projectItem.length) {
        this.$Message.error('请选择项目')
        return
      }

      io.post(api.report.exportPublicStuInfo, {
        schFundId: this.projectItem[1]
      }, (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = '项目公示导出.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })

      // $(`<form action="http://erm.comezx.com${api.report.export}" method="post">${this.generatorInput(params)}</form>`).appendTo('body').submit().remove()
    },
    // 上传文件
    getFile () {
      let file = document.getElementById('files')

      this.fileData = new FormData()
      this.fileData.append('file', file.files[0])

      io.post(api.file.upload, this.fileData, (res) => {
        let _imgUrl = res.data

        io.post(api.schoolFunded.updatePublicImg, {
          id: this.projectItem[1],
          publicImg: _imgUrl
        }, (resData) => {
          this.getProjectList()
        })
      }, (e) => {
        this.$Message.error('文件上传出错！')
      }, (e) => {
        this.$Message.error('文件上传出错！')
      })
    },
    // 审核
    shenhe () {
      io.post(api.fundProcess.audit, {
        'pass': true,
        'schFundId': this.projectItem[1],
        'stuIds': this.verifyData,
        'toPublic': true
      }, (res) => {
        this.$Message.info(res.data.msg)
        this.getList()
      }, (e) => {
        this.$Message.info(e.data.msg)
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
