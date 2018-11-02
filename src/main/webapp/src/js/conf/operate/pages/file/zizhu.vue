<template>
  <div class="wrapper">
    <Location>电子档案 > 资助学生名单</Location>
    <div class="con">
      <Card :padding="10">
        <div class="form-con">
          <Row :gutter="10">
            <Col span="3"><p class="form-label">姓名：</p></Col>
            <Col span="6">
            <Input v-model="formList.name" placeholder="请输入"></Input>
            </Col>
            <Col span="3"><p class="form-label">性别：</p></Col>
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
            <Col span="3"><p class="form-label">学号：</p></Col>
            <Col span="6">
            <Input v-model="formList.stuno" placeholder="请输入"></Input>
            </Col>
            <Col span="3"><p class="form-label">年龄：</p></Col>
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
            <Col span="3"><p class="form-label">项目选择：</p></Col>
            <Col span="6">
            <Cascader :data="dataItems" v-model="formList.project" trigger="hover" placeholder="请选择"></Cascader>
            </Col>
            <Col span="3"><p class="form-label">身份证号：</p></Col>
            <Col span="6">
            <Input v-model="formList.idcard" placeholder="请输入"></Input>
            </Col>
          </Row>
        </div>
        <div class="form-con">
          <Row :gutter="10">
            <Col span="3"><p class="form-label">申请表是否上传：</p></Col>
            <Col span="6">
            <Select v-model="formList.hasApplyImg" placeholder="请选择">
              <Option value="1">是</Option>
              <Option value="0">否</Option>
            </Select>
            </Col>
            <Col span="3"><p class="form-label">贫困类型：</p></Col>
            <Col span="6">
            <Select v-model="formList.isPoor" placeholder="请选择">
              <Option v-for="(item, $index) in formData.diffTypes" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
            </Select>
            </Col>


          </Row>
        </div>
        <div class="form-con">
          <Row :gutter="10">
            <Col span="3"><p class="form-label">贫困等级：</p></Col>
            <Col span="6">
            <Select v-model="formList.diffLevel" placeholder="请选择">
              <Option v-for="(item, $index) in formData.diffLevels" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
            </Select>
            </Col>

            <Col span="3"><p class="form-label">家庭地址：</p></Col>
            <Col span="6">
            <Input v-model="formList.address" placeholder="请输入" ></Input>
            </Col>
          </Row>
        </div>
        <div class="form-con" style="padding-bottom: 20px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
          <Row :gutter="10">
            <Col span="3">&nbsp;</Col>
            <Col span="6">
            <Button type="primary" @click="getList()">查 询</Button>
            </Col>
          </Row>
        </div>


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
  name: 'zizhumingdan',
  data () {
    return {
      modalFile: false,
      formList: {
        name: '',
        gender: '',
        stuno: '',
        minAge: '',
        maxAge: '',
        project: [],
        idcard: '',
        hasApplyImg: '',
        isPoor:'',
        diffLevel:'',
        address:''
      },
      formData:{
        diffTypes:[],
        diffLevels:[{
          dictCode:0,
          dictName:"不困难"
        },
          {
            dictCode:1,
            dictName:"困难"
          },
          {
            dictCode:2,
            dictName:"一般困难"
          },{
            dictCode:3,
            dictName:"特别困难"
          }]

      },
      gradeClazz: {},
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
  components: { vTable, Location, Nodata },
  methods: {
    // 获取列表
    getList () {
      if (!this.formList.project.length) {
        return
      }

      let _data = _.merge({}, this.page, {
//        schFundId: this.projectItem[1],
//        stuName: this.formList.name,
//        idCard: this.formList.idcard,
//        grade: this.formList.grade,
//        clazz: this.formList.clazz,

        stuName: this.formList.name,
        gender: this.formList.gender,
        stuno: this.formList.stuno,
        minAge: this.formList.minAge,
        maxAge: this.formList.maxAge,
        schFundId: this.formList.project[1],
        idCard: this.formList.idcard,
        hasApplyImg: this.formList.hasApplyImg,
        isPoor: this.formList.isPoor,
        diffLevel: this.formList.diffLevel,
        address:this.formList.address,
        status: 31
      })

      io.post(api.fundProcess.queryApply, _data, (res) => {
        let data = res.data
        this.headData = data.header
        this.listData = data.page.list
        this.total = data.page.totalCount
        console.log(data)

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
    // 获取班级
    getClazz () {
      io.get(api.school.querySchGradeClazzList, (res) => {
        let data = res.data
        this.gradeClazz = data.page
      })
    },
    // 获取下拉数据
    getSubList () {
      io.get(api.dict.list, (res) => {
        let data = res.data.dict
        console.log(data)
        this.formData.diffTypes = data['8']
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
    },
    // 数据导出
    dataOut () {
      if (!this.formList.project.length) {
        return
      }
      io.post(api.fundProcess.exportStudentApply, {
        stuName: this.formList.name,
        gender: this.formList.gender,
        stuno: this.formList.stuno,
        minAge: this.formList.minAge,
        maxAge: this.formList.maxAge,
        schFundId: this.formList.project[1],
        idCard: this.formList.idcard,
        hasApplyImg: this.formList.hasApplyImg,
        isPoor: this.formList.isPoor,
        diffLevel: this.formList.diffLevel,

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
  mounted () {
    this.getProjectList()
    this.getSubList()
    this.getClazz()
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
    .money {
      border: none;
      background-color: #f7f7f7;
      width: 80px;
      padding-left: 5px;
      padding-right: 5px;
      text-align: center;
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
