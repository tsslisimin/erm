<template>
  <div class="wrapper">
    <Location>资助管理 > 审核资助项目</Location>
    <div class="con">
      <Card :padding="10">
        <Form :label-width="80" style="padding-top: 15px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
          <FormItem label="选择项目" style="margin-bottom: 20px;">
            <Cascader :data="dataItems" v-model="projectItem" trigger="hover" placeholder="请选择" style="width: 300px;"></Cascader>
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

        <v-table v-if="listData.length" :items="listData" :header="[]">
          <template slot="t-header">
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="schoolId"><span>学校名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="typeName"><span>项目类型</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="name"><span>项目名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="statusName"><span>状态</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 100px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center"><router-link :to="{ name: 'shenheDetail', params: { schId: props.data.schoolId, fundId: props.data.id }}">{{props.data.schoolName}}</router-link></td>
              <td class="center">{{props.data.typeName}}</td>
              <td class="center">{{props.data.name}}</td>
              <td class="center">{{props.data.statusName}}</td>
              <td class="left"><Poptip confirm placement="top-end" title="一键通过还是拒绝？" ok-text="通过" cancel-text="拒绝" @on-ok="oneAgree(props.data.schFundId)" @on-cancel="oneReject(props.data.schFundId)"><Button type="primary">一键审核</Button></Poptip></td>
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
import _ from 'lodash'
import $ from 'jquery'
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'manageShenheitem',
  data () {
    return {
      projectItem: [],
      dataItems: [],
      schoolName: '',
      schoolList: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      }
    }
  },
  components: { vTable, Location, Nodata },
  methods: {
    // 获取列表
    getList () {
      let searchData = _.merge({}, {
        fundedId: this.projectItem[1],
        schoolId: this.schoolName
      }, this.page)

      io.get(api.fundProcess.fundList, searchData, (res) => {
        let data = res.data
        this.listData = data.page.list
      })
    },
    // 分页操作
    pageChange (page) {
      this.page.page = page
      this.getList()
    },
    // 一键通过
    oneAgree (id) {
      io.post(api.fundProcess.auditByFund, {
        schFundId: id,
        auditStatus: 1
      }, (res) => {
        this.$Message.info(res.data.msg)
        this.getList()
      })
    },
    // 一键拒绝
    oneReject (id) {
      io.post(api.fundProcess.auditByFund, {
        schFundId: id,
        auditStatus: 0
      }, (res) => {
        this.$Message.info(res.data.msg)
        this.getList()
      })
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
    }
  },
  watch: {
    projectItem (o) {
      this.schoolName = ''
      this.getSchoolList()
    }
  },
  mounted () {
    this.getProjectList()
    this.getList()
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
