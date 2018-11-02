<template>
  <div class="wrapper">
    <Location>统计报表</Location>
    <div class="con">
      <Card :padding="10">
        <div class="count-num">
          <Row :gutter="30">
            <Col span="6">
              <div class="count-con">
                <h3>{{overview.school || 0}}</h3>
                <p>资助学校个数</p>
              </div>
            </Col>
            <Col span="6">
              <div class="count-con">
                <h3>{{overview.fund || 0}}</h3>
                <p>资助项目个数</p>
              </div>
            </Col>
            <Col span="6">
              <div class="count-con">
                <h3>{{overview.student || 0}}</h3>
                <p>资助人数</p>
              </div>
            </Col>
            <Col span="6">
              <div class="count-con">
                <h3>{{overview.money || 0}}</h3>
                <p>资助总金额（元）</p>
              </div>
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
            <Col span="2"><p class="form-label">报表类型：</p></Col>
            <Col span="6">
              <Select v-model="formList.type" placeholder="请选择">
                <Option value="1">按资助学校统计</Option>
                <Option value="2">按资助学段类型统计</Option>
                <Option value="3">按资助项目类型统计</Option>
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
        <!--<div class="do-some clearfix" v-if="listData.length">-->
          <!--<div class="f-fl">-->
            <!--<Button type="primary" @click="dataOut()">导出数据</Button>-->
            <!--&lt;!&ndash; <Modal v-model="modalFile" title="导入数据" :ok-text="'确定导入'" @on-ok="dataIn" @on-cancel="modalFile = false">-->
              <!--<div class="data-in">-->
                <!--<h3>请按以下步骤进行数据导入</h3>-->
                <!--<p><span>1.</span>下载模板，以便导入数据 <Button type="primary" size="small" @click="downFile()">下载模板</Button></p>-->
                <!--<p><span>2.</span>已填好数据，导入数据 <input type="file" id="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" @change="getFile()"></p>-->
              <!--</div>-->
            <!--</Modal> &ndash;&gt;-->
          <!--</div>-->
        <!--</div>-->
        <v-table v-if="listData.length" :items="listData" :header="[]">
          <template slot="t-header">
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="type"><span>年份</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="name"><span>学校名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="code"><span>报表名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.reportYear}}</td>
              <td class="center">{{props.data.schoolName}}</td>
              <td class="center" v-if="props.data.reportType == 1"><router-link :to="{ name: 'count/xuexiao', params: { schId: props.data.schId, year: props.data.reportYear }}">{{props.data.reportName}}</router-link></td>
              <td class="center" v-if="props.data.reportType == 2"><router-link :to="{ name: 'count/xuequ' }">{{props.data.reportName}}</router-link></td>
              <td class="center" v-if="props.data.reportType == 3"><router-link :to="{ name: 'count/xiangmu' }">{{props.data.reportName}}</router-link></td>
            </tr>
          </template>
        </v-table>

        <div class="page clearfix" v-if="total > 10">
          <div class="f-fr">
            <Page :total="total" show-total @on-change="pageChange"></Page>
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

import Xuexiao from './source/xuexiao'
import Xuequ from './source/xuequ'
import Xiangmu from './source/xiangmu'
import Renyuan from './source/renyuan'

export default {
  name: 'count',
  data () {
    return {
      formList: {
        school: '',
        year: '',
        type: ''
      },
      overview: {
        school: '',
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
      schoolList: [],
      yearList: [],
      queryData: {}
    }
  },
  components: { vTable, Location, Xuexiao, Xuequ, Xiangmu, Renyuan },
  methods: {
    // 获取列表
    getList () {
      let searchData = _.merge({}, this.page, {
        schoolId: this.formList.school,
        year: this.formList.year,
        type: this.formList.type
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
    // 获取概览数据
    getOverview () {
      io.get(api.report.overview, (res) => {
        let data = res.data
        this.overview.school = data.overview.school_count
        this.overview.fund = data.overview.funded_count
        this.overview.student = data.overview.stu_count
        this.overview.money = data.overview.money_sum
      })
    },
    dataOut(){
      io.post(api.total.export, {
      }, (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = '项目报表导出.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })
    }
  },
  mounted () {
    this.getList()
    this.getOverview()
    this.getSchoolList()
    this.getYearList()
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
