<template>
  <div class="wrapper">
    <Location>日常办公 > 业务流程</Location>
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
              <Button type="primary" @click="getStatus()">查 询</Button>
            </Col>
          </Row>
        </div>
        <div v-for="(value, key) in statusMap" :key="key">
          <p class="school">{{key}}</p>
          <Steps :current="value.status">
            <Step :title="'已完成'" v-if="$index < value.status" :content="item.name" v-for="(item, $index) in value.nodes" :key="$index"></Step>
            <Step :title="'进行中'" v-if="$index == value.status" :content="item.name" v-for="(item, $index) in value.nodes" :key="$index"></Step>
            <Step :title="'待进行'" v-if="$index > value.status" :content="item.name" v-for="(item, $index) in value.nodes" :key="$index"></Step>
          </Steps>
        </div>

        <Nodata v-if="statusMap == null"></Nodata>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import _ from 'lodash'
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'dailyYewu',
  data () {
    return {
      projectItem: [],
      dataItems: [],
      status: 0,
      statusData: [],
      statusMap: null
    }
  },
  components: { Location, Nodata },
  methods: {
    // 获取项目列表
    getProjectList () {
      io.get(api.funded.list, (res) => {
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
    // 查询
    getStatus () {
      io.get(api.fundProcess.status + '/' + this.projectItem[1], (res) => {
        let data = res.data
        this.statusMap = data.fundFlowMap
        for (let key in data.fundFlowMap) {
          if (data.fundFlowMap[key].nodes) {
            _.forEach(data.fundFlowMap[key].nodes, (item, idx) => {
              if (item.status == 2) {
                data.fundFlowMap[key]['status'] = idx
              }
            })
            // 项目流程全部结束，没有2的情况处理
            if (typeof data.fundFlowMap[key]['status'] === 'undefined') {
              data.fundFlowMap[key]['status'] = data.fundFlowMap[key].nodes.length
            }
          }
        }
      })
    }
  },
  mounted () {
    this.getProjectList()
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
    .school {
      font-weight: bold;
      color: $color-primary-blue;
      margin-top: 10px;
      margin-bottom: 10px;
    }
  }
}
</style>
