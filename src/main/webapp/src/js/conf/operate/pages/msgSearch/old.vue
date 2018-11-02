<template>
  <div class="wrapper">
    <Location>信息查询 > 已毕业学生</Location>
    <div class="con">
      <Card :padding="10">
        <div class="form-con">
          <Row :gutter="10">
            <Col span="2"><p class="form-label">项目选择：</p></Col>
            <Col span="6">
              <Cascader :data="dataItems" v-model="projectItem" trigger="hover" placeholder="请选择"></Cascader>
            </Col>
          </Row>
        </div>
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
          </Row>
        </div>
        <div class="form-con">
          <Row :gutter="10">
            <Col span="2"><p class="form-label">身份证号：</p></Col>
            <Col span="6">
              <Input v-model="formList.idcard" placeholder="请输入"></Input>
            </Col>
            <Col span="2"><p class="form-label">年龄：</p></Col>
            <Col span="3">
              <Input v-model="formList.minAge" placeholder="请输入" class="else"></Input>
            </Col>
            <Col span="3">
              <Input v-model="formList.maxAge" placeholder="请输入"></Input>
            </Col>
          </Row>
        </div>
        <div class="form-con">
          <Row :gutter="10">
            <Col span="2"><p class="form-label">学校名称：</p></Col>
            <Col span="6">
              <Input v-model="formList.school" placeholder="请输入"></Input>
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
            <Button type="primary" @click="modalFile = true">信息对比</Button>
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
            <th style="width: 150px;" :class="{left:props.idx == 0,center:props.idx > 0}"><p
              :sort-key="props.data.colCode"><span>{{props.data.colName}}</span><span class="sort"><i
              class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td :class="{left:$index == 0,center:$index > 0}" v-for="(el, $index) in headData" :key="$index">
                <span v-if="el.colCode != 'name'">{{el.colCode == 'school_name'
                  ? props.data['school'] ==null
                  ?props.data[el.colCode]:props.data['school']
                  : props.data[el.colCode]}}</span>
                <router-link v-if="el.colCode == 'name'"
                             :to="{ name: 'detail', params: { stuId: props.data.stu_id, fundId: props.data.funded_id } }">
                  {{props.data[el.colCode]}}
                </router-link>
              </td>
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
            <p><span>1.</span>下载模板，以便导入数据
              <Button type="primary" size="small" @click="downFile()">下载模板</Button>
            </p>
            <p><span>2.</span>已填好数据，导入数据 <input type="file" id="file"
                                                accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                                                name="file" @change="getFile()"></p>
          </div>
        </Modal>
      </Card>
    </div>
  </div>
</template>

<script>
  import Location from 'js/module/components/location/location'
  import io from 'js/module/api/io'
  import {api} from 'js/module/api/url'
  import vTable from 'js/module/components/table/table'
  import _ from 'lodash'
  import $ from 'jquery'
  import Nodata from 'js/module/components/nodata/nodata'

  export default {
    name: 'msgSearch',
    data() {
      return {
        modalFile: false,
        formList: {
          name: '',
          gender: '',
          minAge: '',
          maxAge: '',
          idcard: '',
          school: ''
        },
        projectItem: [],
        dataItems: [],
        headData: [],
        listData: [],
        total: 0,
        page: {
          page: 1,
          limit: 10
        }
      }
    },
    components: {vTable, Location, Nodata},
    methods: {
      // 获取列表
      getList() {
        let searchData = _.merge({}, this.page, {
          name: this.formList.name,
          gender: this.formList.gender,
          minAge: this.formList.minAge,
          maxAge: this.formList.maxAge,
          idcard: this.formList.idcard,
          school: this.formList.school,
          schFundId: this.projectItem[1],
          isGraduation: 1
        })

        io.get(api.msg.list, searchData, (res) => {
          let data = res.data
          this.headData = data.header
          this.listData = data.page.list
          this.total = data.page.totalCount
        })
      },
      // 分页操作
      pageChange(page) {
        this.page.page = page
        this.getList()
      },
      // 获取项目列表
      getProjectList() {
        io.get(api.funded.schoolFundedlist, (res) => {
          let data = res.data.page.list
          let casData = []
          let typeA = {value: '1', label: '', children: []}
          let typeB = {value: '2', label: '', children: []}
          let typeC = {value: '3', label: '', children: []}
          let typeD = {value: '4', label: '', children: []}

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
      // input 工具方法
      generatorInput(obj) {
        let result = ''
        for (var key in obj) {
          result += '<input type="hidden" name = "' + key + '" value="' + obj[key] + '">'
        }
        return result
      },

      // 下载模板
      // 下载模板
      // 数据导出
      downFile() {
        io.post(api.student.checkTemp, {}, (res) => {
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
        }, (e) => {
        }, (e) => {
        }, {responseType: 'blob'})

        // $(`<form action="http://erm.comezx.com${api.student.export}" method="post"></form>`).appendTo('body').submit().remove()
      },
      // 上传文件
      getFile() {
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

      dataIn() {
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
        }, (e) => {
        }, (e) => {
        }, {responseType: 'blob'})
      },
      // 数据导出
      dataOut() {
        io.post(api.report.export, {
          reportType: 'infoQuery',
          name: this.formList.name,
          gender: this.formList.gender,
          minAge: this.formList.minAge,
          maxAge: this.formList.maxAge,
          idcard: this.formList.idcard,
          schFundId: this.projectItem[1]
        }, (res) => {
          let blob = res
          let reader = new FileReader()
          reader.readAsDataURL(blob)
          reader.onload = (e) => {
            let a = document.createElement('a')
            a.download = '信息查询导出.xls'
            a.href = e.target.result
            $('body').append(a)
            a.click()
            $(a).remove()
          }
        }, (e) => {
        }, (e) => {
        }, {responseType: 'blob'})

        // $(`<form action="http://erm.comezx.com${api.report.export}" method="post">${this.generatorInput(params)}</form>`).appendTo('body').submit().remove()
      }
    },
    mounted() {
      this.getList()
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
