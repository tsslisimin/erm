<template>
  <div class="wrapper">
    <Location>统计报表</Location>
    <div class="con">
      <Card :padding="10">
        <div class="xiangmu">
          <div class="chart" v-if="listData.length">
            <Row :gutter="10">
              <Col span="12">
                <div id="chart-con-1" style="height: 400px;"></div>
              </Col>
              <Col span="12">
                <div id="chart-con-2" style="height: 400px;"></div>
              </Col>
            </Row>
          </div>

          <div class="do-some clearfix" v-if="listData.length">
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
              <tr @click="goWhere(props.data)">
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
import { bar, pie } from 'js/lib/echarts/index'
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'xiangmu',
  data () {
    return {
      headData: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      }
    }
  },
  components: { vTable, Nodata, Location },
  methods: {
    // 获取列表
    getList () {
      let searchData = _.merge({}, this.page)

      io.get(api.report.type, searchData, (res) => {
        let data = res.data
        this.headData = data.header
        this.listData = data.page.list
        this.total = data.page.totalCount

        this.$nextTick(() => {
          pie('chart-con-1', {
            series: [
              {
                name: '资助人数',
                type: 'pie',
                data: data.echartPie
              }
            ]
          })

          bar('chart-con-2', {
            xAxis: [{
              data: data.echart.xData
            }],
            series: [
              {
                name: '资助金额',
                type: 'bar',
                data: data.echart.yData
              }
            ]
          })
        })
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
        reportType: 'fundedType'
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

      // $(`<form action="http://erm.comezx.com${api.report.export}" method="post">${this.generatorInput(params)}</form>`).appendTo('body').submit().remove()
    },
    // 切换
    goWhere (o) {
      this.$router.push({ name: 'count/renyuan', params: { year: o.year } })
    }
  },
  mounted () {
    this.getList()
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
    .xiangmu {
      .chart {
        height: 400px;
        margin-bottom: 10px;
      }
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
