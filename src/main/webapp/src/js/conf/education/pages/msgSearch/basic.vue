<template>
  <div class="wrapper">
    <Location>信息查询 > 在校学生</Location>
    <div class="con">
      <Card :padding="10">
        <div class="form-con">
          <Row :gutter="10">
            <!--<Col span="2"><p class="form-label">项目选择：</p></Col>-->
            <!--<Col span="6">-->
            <!--<Cascader :data="dataItems" v-model="projectItem" trigger="hover" placeholder="请选择"></Cascader>-->
            <!--</Col>-->
            <Col span="2"><p class="form-label">学校：</p></Col>
            <Col span="6">
              <Select v-model="formList.school" filterable placeholder="请选择">
                <Option v-for="(item, $index) in schoolList" :key="$index" :value="item.id">{{item.name}}</Option>
              </Select>
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
            <Col span="2"><p class="form-label">年级：</p></Col>
            <Col span="6">
              <Select v-model="formList.grade" placeholder="请选择">
                <Option v-for="item in gradeClazz.grade" :key="item" :value="item">{{item}}</Option>
              </Select>
            </Col>
            <Col span="2"><p class="form-label">班级：</p></Col>
            <Col span="6">
              <Select v-model="formList.clazz" placeholder="请选择">
                <Option v-for="item in gradeClazz.clazz" :key="item" :value="item">{{item}}</Option>
              </Select>
            </Col>
          </Row>
        </div>
        <div class="form-con">
          <Row :gutter="10">
            <Col span="2"><p class="form-label">档次：</p></Col>
            <Col span="6">
              <Select v-model="level" placeholder="请选择">
                <Option v-for="(item, $index) in levelList" :key="$index" :value="item.dictCode">{{item.dictName}}
                </Option>
              </Select>
            </Col>
            <Col span="2"><p class="form-label">学段：</p></Col>
            <Col span="6">
              <Select v-model="schzone" placeholder="请选择">
                <Option v-for="(item, $index) in schzoneList" :key="$index" :value="item.dictCode">{{item.dictName}}
                </Option>
              </Select>
            </Col>
          </Row>
        </div>
        <div class="form-con">
          <Row :gutter="10">
            <!--<Col span="2"><p class="form-label">资助类型：</p></Col>-->
            <!--<Col span="6">-->
            <!--<Select v-model="formList.subtype" placeholder="请选择">-->
            <!--<Option value="111">学前教育资助</Option>-->
            <!--<Option value="112">义务教育资助</Option>-->
            <!--<Option value="113">普高教育资助</Option>-->
            <!--<Option value="114">中职教育资助</Option>-->
            <!--<Option value="115">高等教育资助</Option>-->
            <!--</Select>-->
            <!--</Col>-->
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
            <Button type="primary" @click="exportList">导出数据</Button>
            <Button type="primary" @click="modalFile = true">信息对比</Button>
            <Button type="primary" @click="downloadItems()">录入情况下载</Button>
            <label style=" margin-left: 25px">总人数: </label>
            <label class="sp"> {{listData.length}}</label>
            <Modal v-model="modalOut" title="导出数据" :ok-text="'确定'" @on-ok="dataOut" @on-cancel="modalOut = false">
              <div class="data-out">
                <h3>请选择需要导出的信息</h3>
                <div class="bd">
                  <CheckboxGroup v-model="outData">
                    <Checkbox v-for="(item, $index) in outList" :key="$index" :label="item.key" :disabled="item.check">
                      <span>{{item.value}}</span></Checkbox>
                  </CheckboxGroup>
                </div>
              </div>
            </Modal>
          </div>
        </div>

        <!--<v-table v-if="listData.length" :nowrap="true" :items="listData" :header="headData">-->
        <!--<template slot="d-header" slot-scope="props">-->
        <!--<th style="width: 150px;" :class="{left:props.idx == 0,center:props.idx > 0}"><p-->
        <!--:sort-key="props.data.colCode"><span>{{props.data.colName}}</span><span class="sort"><i-->
        <!--class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>-->
        <!--</template>-->
        <!--<template slot="t-body" slot-scope="props">-->
        <!--<tr>-->
        <!--<td :class="{left:$index == 0,center:$index > 0}" v-for="(el, $index) in headData" :key="$index"><span-->
        <!--v-if="el.colCode != 'name'">{{props.data[el.colCode]}}</span>-->
        <!--<router-link v-if="el.colCode == 'name'"-->
        <!--:to="{ name: 'detailBasic', params: { stuId: props.data.stu_id, fundId: props.data.funded_id } }">-->
        <!--{{props.data[el.colCode]}}-->
        <!--</router-link>-->
        <!--</td>-->
        <!--</tr>-->
        <!--</template>-->
        <!--</v-table>-->


        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="[]">
          <template slot="t-header">
            <!--<th slot="t-check" id="all-check" style="width: 30px;"><input type="checkbox" style="margin-top: 5px;"></th>-->
            <!--<th class="left"><p><span>序号</span></p></th>-->
            <th class="center"><p sort-key="name"><span>姓名</span><span class="sort"><i
              class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="idcard"><span>身份证号</span><span class="sort"><i
              class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="hasAvatar"><span>学生头像</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i
              class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="stuno"><span>学号</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i
              class="iconfont down">&#xe608;</i></span></p></th>
            <!--<th class="center" style="width: 200px;"><p><span>操作</span></p></th>-->
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <!--<td style="width: 30px;" class="check-box"><input type="checkbox" :data-id="props.data.id"-->
              <!--style="margin-top: 5px;"></td>-->
              <!--<td class="left"><checkbox :checked="props.data.checked" @click='checkedOne(props.idx)'></checkbox></td>-->
              <!--<td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>-->
              <td class="center">
                <router-link :to="{ name:'detailBasic', params: { stuId: props.data.id } }">{{props.data.name}}
                </router-link>
              </td>
              <td class="center">{{props.data.idcard}}</td>
              <td class="center">{{props.data.hasAvatar ? '有' : '无'}}</td>
              <td class="center">{{props.data.stuno}}</td>
              <!--<td class="center">-->
              <!--<Button type="primary" @click="edit(props.data.id)">编辑</Button>&nbsp;&nbsp;&nbsp;<Button type="error"-->
              <!--@click="del(props.data.id)">-->
              <!--删除-->
              <!--</Button>-->
              <!--</td>-->
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
          school: '',
          name: '',
          gender: '',
          minAge: '',
          maxAge: '',
          idcard: '',
          subtype: '',
          grade: '',
          clazz: ''
        },
        level: '',
        schzone: '',
        gradeClazz: {},
        headData: [],
        listData: [],
        total: 0,
        page: {
          page: 1,
          limit: 10
        },
        schoolList: [],
        projectItem: [],
        dataItems: [],
        schZoneList: [],
        poorList: [],

// 导出信息
        modalOut: false,
        outList: [],
        outData: []
      }
    },
    components: {vTable, Location, Nodata},
    methods: {
      downloadItems() {
        // window.open(api.student.downloadItems)


        let searchData = _.merge({}, this.page, {
          name: this.formList.name,
          gender: this.formList.gender,
          stuno: this.formList.stuno,
          minAge: this.formList.minAge,
          maxAge: this.formList.maxAge,
          idcard: this.formList.idcard,
          schId: this.formList.school,
          hasAvatar: this.formList.hasAvatar
        })

        io.get(api.student.downloadItems, searchData, (res) => {
          let blob = res
          console.log(res)

          let reader = new FileReader()
          reader.readAsDataURL(blob)
          reader.onload = (e) => {
            let a = document.createElement('a')
            a.download = '录入情况.xls'
            a.href = e.target.result
            $('body').append(a)
            a.click()
            $(a).remove()
          }


          // let data = res.data
          // this.listData = data.page.list
          // console.log(this.listData)
          // this.listData.forEach(item => {
          //   item.checked = false;
          // })
          // this.total = data.page.totalCount
        }, (e) => {
          this.$Spin.hide()
          console.log(e)
          this.$Message.error(e.message)
        }, (e) => {
          this.$Spin.hide()
          this.$Message.error(e.message)

        }, {responseType: 'blob'})
      },
// 获取列表
      getList() {
        // let searchData = _.merge({}, this.page, {
        //   fundId: this.projectItem[1],
        //   schoolId: this.formList.school,
        //   name: this.formList.name,
        //   gender: this.formList.gender,
        //   minAge: this.formList.minAge,
        //   maxAge: this.formList.maxAge,
        //   idcard: this.formList.idcard,
        //   grade: this.formList.grade,
        //   clazz: this.formList.clazz,
        //   level: this.level,
        //   schZone: this.schzone,
        //   subtype: this.formList.subtype
        // })
        //
        // io.get(api.msg.list, searchData, (res) => {
        //   let data = res.data
        //   this.headData = data.header
        //   this.listData = data.page.list
        //   console.log(this.listData)
        //   this.total = data.page.totalCount
        // })

        let searchData = _.merge({}, this.page, {
          name: this.formList.name,
          gender: this.formList.gender,
          stuno: this.formList.stuno,
          minAge: this.formList.minAge,
          maxAge: this.formList.maxAge,
          idcard: this.formList.idcard,
          schId: this.formList.school,
          hasAvatar: this.formList.hasAvatar
        })

        io.get(api.student.list, searchData, (res) => {

          let data = res.data
          this.listData = data.page.list
          console.log(this.listData)
          this.listData.forEach(item => {
            item.checked = false;
          })
          this.total = data.page.totalCount
        })


      },
      exportList() {
        let searchData = _.merge({}, this.page, {
          name: this.formList.name,
          gender: this.formList.gender,
          stuno: this.formList.stuno,
          minAge: this.formList.minAge,
          maxAge: this.formList.maxAge,
          idcard: this.formList.idcard,
          schId: this.formList.school,
          hasAvatar: this.formList.hasAvatar
        })

        io.get(api.student.exportStudentList, searchData, (res) => {
          let blob = res
          console.log(res)

          let reader = new FileReader()
          reader.readAsDataURL(blob)
          reader.onload = (e) => {
            let a = document.createElement('a')
            a.download = '学生信息.xls'
            a.href = e.target.result
            $('body').append(a)
            a.click()
            $(a).remove()
          }
        }, (e) => {
          this.$Spin.hide()
          this.$Message.error(e.message)
        }, (e) => {
          this.$Spin.hide()
          this.$Message.error(e.message)

        }, {responseType: 'blob'})


      },
// 分页操作
      pageChange(page) {
        this.page.page = page
        this.getList()
      },
// 获取学区
      getZone() {
        io.get(api.dict.fundDict, {
          type: 25
        }, (res) => {
          let data = res.data
          this.schzoneList = data.dict['25']
        })
      },
// 获取资助水平
      getLevel() {
        io.get(api.dict.fundDict, {
          type: 24
        }, (res) => {
          let data = res.data
          this.levelList = data.dict['24']
        })
      },
// 获取学校列表
      getSchoolList() {
        io.get(api.school.list, (res) => {
          let data = res.data
          this.schoolList = data.page.list
        })
      },
// 获取项目列表
      getProjectList() {
        io.get(api.funded.list, (res) => {
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
        if (!this.outData.length) {
          return
        }

        io.post(api.report.export, {
          reportType: 'infoQuery',
          cloumnFields: this.outData.join(),
          fundId: this.projectItem[1],
          schoolId: this.formList.school,
          name: this.formList.name,
          gender: this.formList.gender,
          minAge: this.formList.minAge,
          maxAge: this.formList.maxAge,
          idcard: this.formList.idcard,
          grade: this.formList.grade,
          clazz: this.formList.clazz,
          level: this.level,
          schZone: this.schzone,
          subtype: this.formList.subtype
        }, (res) => {
          console.log(res)
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
      },
      getColumn() {
        io.get(api.report.column, (res) => {
          let data = res.data
          this.outList = data.columns
          this.outData = _.map(this.outList, (item) => item.key)
        })
      },
// 获取班级
      getClazz() {
        io.get(api.school.querySchGradeClazzList, (res) => {
          let data = res.data
          this.gradeClazz = data.page
        })
      }
    }
    ,
    mounted() {
      this.getList()
      this.getSchoolList()
// this.getProjectList()
      this.getColumn()
      this.getZone()
      this.getLevel()
// this.getClazz()
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
      .sp {
        font-weight: bold;
        color: $color-primary-blue-active;
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

  .data-out {
    h3 {
      font-weight: normal;
      font-size: 16px;
      text-align: center;
      color: $color-warning;
    }
    .bd {
      margin-top: 20px;
      margin-bottom: 20px;
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


