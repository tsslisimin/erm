<template>
  <div class="xuexiao">
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
        <Col span="2"><p class="form-label">身份证号：</p></Col>
        <Col span="6">
          <Input v-model="formList.idcard" placeholder="请输入"></Input>
        </Col>
      </Row>
    </div>
    <div class="form-con">
      <Row :gutter="10">
        <Col span="2"><p class="form-label">学段：</p></Col>
        <Col span="6">
          <Select v-model="formList.schoolZone" placeholder="请选择">
            <Option v-for="(item, $index) in zoneData" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
          </Select>
        </Col>
        <Col span="2"><p class="form-label">贫困类型：</p></Col>
        <Col span="6">
          <Select v-model="formList.poor" placeholder="请选择">
            <Option v-for="(item, $index) in poorData" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
          </Select>
        </Col>
        <Col span="2"><p class="form-label">家庭住址：</p></Col>
        <Col span="6">
          <Input v-model="formList.address" placeholder="请输入"></Input>
        </Col>
      </Row>
    </div>
    <div class="form-con">
      <Row :gutter="10">
        <Col span="2"><p class="form-label">资助级别：</p></Col>
        <Col span="6">
          <Select v-model="formList.level" placeholder="请选择">
            <Option v-for="(item, $index) in levelData" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
          </Select>
        </Col>
        <Col span="2"><p class="form-label">项目选择：</p></Col>
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

    <div class="do-some clearfix">
      <div class="f-fl">
        <Button type="primary" @click="dataOut()">导出数据</Button>
        <!-- <Modal v-model="modalFile" title="导入数据" :ok-text="'确定导入'" @on-ok="dataIn" @on-cancel="modalFile = false">
          <div class="data-in">
            <h3>请按以下步骤进行数据导入</h3>
            <p><span>1.</span>下载模板，以便导入数据 <Button type="primary" size="small" @click="downFile()">下载模板</Button></p>
            <p><span>2.</span>已填好数据，导入数据 <input type="file" id="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" @change="getFile()"></p>
          </div>
        </Modal> -->
      </div>
    </div>

    <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="headData">
      <template slot="d-header" slot-scope="props">
        <th style="width: 150px;" :class="{left:props.idx == 0,center:props.idx > 0}"><p :sort-key="props.data.colCode"><span>{{props.data.colName}}</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
      </template>
      <template slot="t-body" slot-scope="props">
        <tr>
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
  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import vTable from 'js/module/components/table/table'
import _ from 'lodash'
import $ from 'jquery'
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'xuexiao',
  props: ['type'],
  data () {
    return {
      formList: {
        school: '',
        year: '',
        name: '',
        gender: '',
        idcard: '',
        schoolZone: '',
        poor: '',
        address: '',
        level: '',
        fundName: ''
      },
      headData: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      zoneData: [],
      poorData: [],
      levelData: [],
      fundData: []
    }
  },
  components: { vTable, Nodata },
  methods: {
    // 获取列表
    getList () {
      let searchData = _.merge({}, this.page, {
        studentType: this.type,
        schoolId: this.formList.school,
        year: this.formList.year,
        name: this.formList.name,
        gender: this.formList.gender,
        idcard: this.formList.idcard,
        schzone: this.formList.schoolZone,
        isPoor: this.formList.poor,
        address: this.formList.address,
        level: this.formList.level,
        schFundId: this.formList.fundName
      })

      io.get(api.report.student, searchData, (res) => {
        let data = res.data
        this.headData = data.header
        this.listData = data.page.list
        this.total = data.page.totalCount
      })
    },
    // 分页操作
    pageChange (page) {
      this.page.page = page
      this.getList()
    },
    // input 工具方法
    generatorInput (obj) {
      let result = ''
      for (var key in obj) {
        result += '<input type="hidden" name = "' + key + '" value="' + obj[key] + '">'
      }
      return result
    },
    // 数据导出
    dataOut () {
      io.post(api.report.export, {
        studentType: this.type,
        reportType: 'student',
        schoolId: this.formList.school,
        year: this.formList.year,
        name: this.formList.name,
        gender: this.formList.gender,
        idcard: this.formList.idcard,
        schzone: this.formList.schoolZone,
        isPoor: this.formList.poor,
        address: this.formList.address,
        level: this.formList.level,
        schFundId: this.formList.fundName
      }, (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = '人员报表导出.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })

      // $(`<form action="http://erm.comezx.com${api.report.export}" method="post">${this.generatorInput(params)}</form>`).appendTo('body').submit().remove()
    },
    // 获取学区
    getZone () {
      io.get(api.dict.fundDict, {
        type: 25
      }, (res) => {
        let data = res.data
        this.zoneData = data.dict['25']
      })
    },
    // 获取贫困类型
    getPoor () {
      io.get(api.dict.fundDict, {
        type: 8
      }, (res) => {
        let data = res.data
        this.poorData = data.dict['8']
      })
    },
    // 获取资助级别
    getLevel () {
      io.get(api.dict.fundDict, {
        type: 24
      }, (res) => {
        let data = res.data
        this.levelData = data.dict['24']
      })
    },
    // 获取项目名称
    getFund () {
      io.get(api.schoolFunded.listSimple, {
        schoolId: this.formList.school
      }, (res) => {
        let data = res.data
        this.fundData = data.page.list
      })
    }
  },
  mounted () {
    this.formList.year = this.$route.params.year || ''
    this.formList.school = this.$route.params.schId || ''
    this.getList()
    this.getZone()
    this.getPoor()
    this.getLevel()
    this.getFund()
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../../../module/components/main";

.xuexiao {
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
  }
}
</style>
