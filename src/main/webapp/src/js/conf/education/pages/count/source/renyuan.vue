<template>
  <div class="wrapper">
    <Location>统计报表</Location>
    <div class="con">
      <Card :padding="10">
        <div class="xuexiao">
          <div class="form-con">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">项目类型：</p></Col>
              <Col span="6">
                <Select v-model="formList.fundType" placeholder="请选择">
                  <Option value="1">国家资助</Option>
                  <Option value="2">地方政府资助</Option>
                  <Option value="3">学校资助</Option>
                  <Option value="4">社会资助</Option>
                </Select>
              </Col>
              <Col span="2"><p class="form-label">学段：</p></Col>
              <Col span="6">
                <Select v-model="formList.schoolZone" placeholder="请选择">
                  <Option v-for="(item, $index) in zoneData" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
              </Col>
            </Row>
          </div>
          <div class="form-con">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">学校：</p></Col>
              <Col span="6">
                <Select v-model="formList.school" filterable placeholder="请选择">
                  <Option v-for="item in schoolList" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
              </Col>
              <Col span="2"><p class="form-label">年份：</p></Col>
              <Col span="6">
                <Select v-model="formList.year" placeholder="请选择">
                  <Option v-for="item in yearList" :value="item" :key="item">{{item}}</Option>
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
  name: 'renyuan',
  data () {
    return {
      formList: {
        school: '',
        schoolZone: '',
        fundType: '',
        year: '',
        schoolType: ''
      },
      headData: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      schoolList: [],
      yearList: [],
      zoneData: []
    }
  },
  components: { vTable, Nodata, Location },
  methods: {
    // 获取列表
    getList () {
      let searchData = _.merge({}, this.page, {
        schoolId: this.formList.school,
        schzone: this.formList.schoolZone,
        fundType: this.formList.fundType,
        year: this.formList.year,
        schoolType: this.formList.schoolType
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
    // 获取学校列表
    getSchoolList () {
      io.get(api.school.list, (res) => {
        let data = res.data
        this.schoolList = data.page.list
      })
    },
    // 获取年份列表
    getYearList () {
      io.get(api.report.years, (res) => {
        let data = res.data
        this.yearList = data.years
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
    // 数据导出
    dataOut () {
      io.post(api.report.export, {
        reportType: 'student',
        schoolId: this.formList.school,
        schzone: this.formList.schoolZone,
        fundType: this.formList.fundType,
        year: this.formList.year,
        schoolType: this.formList.schoolType
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
    }
  },
  mounted () {
    this.formList.year = this.$route.params.year || ''
    this.formList.schoolType = this.$route.query.schoolType || ''
    this.getList()
    this.getSchoolList()
    this.getYearList()
    this.getZone()
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
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
          text-align: center;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
