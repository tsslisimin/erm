<template>
  <div class="wrapper">
    <Location>统计报表</Location>
    <div class="con">
      <Card :padding="10">
        <div class="count-num">
          <Row :gutter="30">
            <Col span="8">
              <div class="count-con">
                <h3>{{overview.fund || 0}}</h3>
                <p>资助项目个数</p>
              </div>
            </Col>
            <Col span="8">
              <div class="count-con">
                <h3>{{overview.student || 0}}</h3>
                <p>资助人数</p>
              </div>
            </Col>
            <Col span="8">
              <div class="count-con">
                <h3>{{overview.money || 0}}</h3>
                <p>资助总金额（元）</p>
              </div>
            </Col>
          </Row>
        </div>
        <div class="form-con">
          <Row :gutter="10">
            <Col span="2"><p class="form-label">项目：</p></Col>
            <Col span="6">
              <Cascader :data="dataItems" v-model="formList.fund" trigger="hover" placeholder="请选择"></Cascader>
            </Col>
            <Col span="2"><p class="form-label">年份：</p></Col>
            <Col span="6">
              <Select v-model="formList.year" placeholder="请选择">
                <Option v-for="item in yearList" :value="item" :key="item">{{item}}</Option>
              </Select>
            </Col>
            <Col span="2"><p class="form-label">报表类型：</p></Col>
            <Col span="6">
              <Select v-model="formList.type" placeholder="请选择">
                <Option value="4">按资助项目类型统计</Option>
                <Option value="5">按资助项目统计</Option>
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

        <v-table v-if="listData.length" :items="listData" :header="[]">
          <template slot="t-header">
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="type"><span>年份</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="code"><span>报表名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.reportYear}}</td>
              <td class="center" v-if="props.data.reportType == 4"><router-link :to="{ name: 'count/xiangmutype' }">{{props.data.reportName}}</router-link></td>
              <td class="center" v-if="props.data.reportType == 5"><router-link :to="{ name: 'count/xiangmu', params: { fundId: props.data.fundId, year: props.data.reportYear } }">{{props.data.reportName}}</router-link></td>
              <td class="center" v-if="props.data.reportType == 6"><router-link :to="{ name: 'count/xiangmu', params: { fundId: props.data.fundId, year: props.data.reportYear } }">{{props.data.reportName}}</router-link></td>
            </tr>
          </template>
        </v-table>

        <div class="page clearfix" v-if="total > 10">
          <div class="f-fr">
            <Page :total="total" show-total></Page>
          </div>
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

import Xiangmutype from './source/xiangmutype'
import Xiangmu from './source/xiangmu'
import Renyuan from './source/renyuan'

export default {
  name: 'count',
  data () {
    return {
      formList: {
        type: '',
        year: '',
        fund: []
      },
      overview: {
        fund: '',
        student: '',
        money: ''
      },
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      dataItems: [],
      yearList: [],
      queryData: {}
    }
  },
  components: { vTable, Location, Xiangmutype, Xiangmu, Renyuan },
  methods: {
    // 获取列表
    getList () {
      let searchData = _.merge({}, this.page, {
        fundedId: this.formList.fund[1],
        year: this.formList.year,
        reportTypes: this.formList.type
      })

      io.get(api.report.queryReport, searchData, (res) => {
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
    // 获取年份列表
    getYearList () {
      io.get(api.report.years, (res) => {
        let data = res.data
        this.yearList = data.years
      })
    },
    // 获取概览数据
    getOverview () {
      io.get(api.report.overview, (res) => {
        let data = res.data
        this.overview.fund = data.overview.funded_count
        this.overview.student = data.overview.stu_count
        this.overview.money = data.overview.money_sum
      })
    }
  },
  mounted () {
    this.getList()
    this.getProjectList()
    this.getYearList()
    this.getOverview()
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
    .count-num {
      margin-top: 20px;
      margin-bottom: 50px;
      .count-con {
        height: 90px;
        border: 1px solid $border-default-color;
        border-radius: 3px;
        border-left: 3px solid $color-primary-blue;
        h3 {
          margin-left: 30px;
          margin-top: 10px;
          font-size: 32px;
          font-weight: normal;
        }
        p {
          margin-left: 30px;
        }
      }
    }
  }
}
</style>
