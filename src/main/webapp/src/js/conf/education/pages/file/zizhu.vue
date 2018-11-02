<template>
  <div class="wrapper">
    <Location>电子档案 > 资助学生名单</Location>
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
            <Button type="primary" @click="dataOut()">导出数据</Button>  <Button type="primary" @click="modalFile = true">信息对比</Button>
          </div>
        </div>

        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="headData">
          <template slot="d-header" slot-scope="props">
            <th :class="{left:props.idx == 0,center:props.idx > 0}"><p :sort-key="props.data.colCode"><span>{{props.data.colName}}</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td :class="{left:$index == 0,center:$index > 0}" v-for="(el, $index) in headData" :key="$index"><span v-if="el.colCode != 'stuName'">{{props.data[el.colCode]}}</span><router-link v-if="el.colCode == 'stuName'" :to="{ name: 'detail', params: { stuId: props.data.stu_id, fundId: props.data.funded_id } }">{{props.data[el.colCode]}}</router-link></td>
            </tr>
          </template>
        </v-table>

        <div class="page clearfix" v-if="total > 10">
          <div class="f-fr">
            <Page :total="total" show-total @on-change="pageChange"></Page>
          </div>
        </div>

        <Nodata v-if="!listData.length"></Nodata>

        <Modal v-model="modalFile" title="导入数据" :ok-text="'确定导入'" @on-ok="dataIn" @on-cancel="modalFile = false">
          <div class="data-in">
            <h3>请按以下步骤进行数据导入</h3>
            <p><span>1.</span>下载模板，以便导入数据 <Button type="primary" size="small" @click="downFile()">下载模板</Button></p>
            <p><span>2.</span>已填好数据，导入数据 <input type="file" id="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" @change="getFile()"></p>
          </div>
        </Modal>
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
  name: 'fundCard',
  data () {
    return {
      modalFile: false,
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
      schFundList: []
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
        status: 31
      })

      io.post(api.fundProcess.queryApply, _data, (res) => {
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


    // 下载模板
    // 下载模板
    // 数据导出
    downFile () {
      io.post(api.student.checkTemp, {
      }, (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = '学生信息比对模板.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })

      // $(`<form action="http://erm.comezx.com${api.student.export}" method="post"></form>`).appendTo('body').submit().remove()
    },
    // 上传文件
    getFile () {
      let file = document.getElementById('file')
      let reg = /\.xl.{1,2}$/
      let fileName = file.files[0].name

      if (!fileName.match(reg)) {
        this.$Message.error('文件格式错误！')
        return
      }

      this.formData = new FormData()
      this.formData.append('file', file.files[0])
    },

    dataIn(){
      this.$Spin.show()
      io.post(api.student.importcheck, this.formData, (res) => {
        this.$Spin.hide()

        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = '学生信息比对导出.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })
    }
    ,
    // 数据导出
    dataOut () {
      if (!this.schoolName && !this.projectItem.length) {
        return
      }

      if (this.subType == 112 && !this.schzone) {
        return
      }

      if (this.subType == 113 && !this.level) {
        return
      }

      io.post(api.fundProcess.exportStudentApply, {
        schId: this.schoolName,
        fundId: this.projectItem[1],
        level: this.subType == 113 ? this.level : '',
        schzone: this.subType == 112 ? this.schzone : '',
        status: 31
      }, (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = '资助学生名单导出.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })

      // $(`<form action="http://erm.comezx.com${api.student.export}" method="post"></form>`).appendTo('body').submit().remove()
    }
  },
  watch: {
    projectItem (o) {
      let _item = _.find(this.schFundList, (n) => o[1] == n.id)
      this.subType = _item.subtype
      this.level = ''
      this.schzone = ''
      this.getSchoolList()
    }
  },
  mounted () {
    this.getProjectList()
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

.data-in {
h3 {
  font-weight: normal;
  font-size: 16px;
  text-align: center;
  color: $color-warning;
}
p {
  margin-top: 20px;
  font-size: 14px;
  color: $font-primary-color;
}
}
</style>
