<template>
  <div class="wrapper">
    <Location>资助管理 > 项目详情</Location>
    <div class="con">
      <Card :padding="10">
        <div class="fund-con">
          <h3>资助项目启动</h3>
          <div class="form-con">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">项目名称：</p></Col>
              <Col span="10">
                <p>{{fundInfo.name}}</p>
              </Col>
              <Col span="2"><p class="form-label">年份：</p></Col>
              <Col span="4">
                <p>{{fundInfo.semester}}</p>
              </Col>
              <Col span="2"><p class="form-label">状态：</p></Col>
              <Col span="4">
                <Button type="primary" v-if="fundInfo.status < 2" @click="startFund(fundInfo.id)">启 动</Button>
                <Button type="primary" disabled v-if="fundInfo.status >= 2">启动中</Button>
              </Col>
            </Row>
          </div>
        </div>
        <div class="fund-con">
          <h3 style="margin-top: 20px;">资助项目配置</h3>
          <div class="form-con">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">选择学校：</p></Col>
              <Col span="6">
                <Select v-model="formList.schools" v-if="fundInfo.status <= 3" filterable multiple placeholder="请选择">
                  <Option v-for="(item, $index) in schoolList" :key="$index" :value="item.id">{{item.name}}</Option>
                </Select>
                <Select v-model="formList.schools" v-if="fundInfo.status > 3" disabled filterable multiple placeholder="请选择">
                  <Option v-for="(item, $index) in schoolList" :key="$index" :value="item.id">{{item.name}}</Option>
                </Select>
              </Col>
              <Col span="3" style="margin-top:5px;">
                <Checkbox v-model="formList.isAll" v-if="fundInfo.status <= 3">全部学校</Checkbox>
              </Col>
            </Row>
          </div>
          <div class="form-con">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">资助总名额：</p></Col>
              <Col span="6">
                <Input v-model="formList.minge" v-if="fundInfo.status <= 3" placeholder="请输入"></Input>
                <Input v-model="formList.minge" disabled v-if="fundInfo.status > 3" placeholder="请输入"></Input>
              </Col>
            </Row>
          </div>
          <div class="form-con" v-if="fundInfo.subtype != 112 && fundInfo.subtype != 113">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">资助总金额：</p></Col>
              <Col span="6">
                <Input v-model="formList.jine" v-if="fundInfo.status <= 3" placeholder="请输入"></Input>
                <Input v-model="formList.jine" disabled v-if="fundInfo.status > 3" placeholder="请输入"></Input>
              </Col>
            </Row>
          </div>
          <div class="form-con" v-if="fundInfo.subtype == 112">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">选择学段：</p></Col>
              <Col span="6">
                <Select v-model="formList.schzone1" v-if="fundInfo.status <= 3" placeholder="请选择">
                  <Option v-for="(item, $index) in schzoneList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
                <Select v-model="formList.schzone1" v-if="fundInfo.status > 3" disabled placeholder="请选择">
                  <Option v-for="(item, $index) in schzoneList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
              </Col>
              <Col span="2"><p class="form-label">名额：</p></Col>
              <Col span="6">
                <Input v-model="formList.schzone1ren" placeholder="请输入"></Input>
              </Col>
              <Col span="2"><p class="form-label">资助总金额：</p></Col>
              <Col span="6">
                <Input v-model="formList.schzone1money" placeholder="请输入"></Input>
              </Col>
            </Row>
          </div>
          <div class="form-con" v-if="fundInfo.subtype == 112">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">选择学段：</p></Col>
              <Col span="6">
                <Select v-model="formList.schzone2" v-if="fundInfo.status <= 3" placeholder="请选择">
                  <Option v-for="(item, $index) in schzoneList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
                <Select v-model="formList.schzone2" v-if="fundInfo.status > 3" disabled placeholder="请选择">
                  <Option v-for="(item, $index) in schzoneList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
              </Col>
              <Col span="2"><p class="form-label">名额：</p></Col>
              <Col span="6">
                <Input v-model="formList.schzone2ren" placeholder="请输入"></Input>
              </Col>
              <Col span="2"><p class="form-label">资助总金额：</p></Col>
              <Col span="6">
                <Input v-model="formList.schzone2money" placeholder="请输入"></Input>
              </Col>
            </Row>
          </div>
          <div class="form-con" v-if="fundInfo.subtype == 112">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">选择学段：</p></Col>
              <Col span="6">
                <Select v-model="formList.schzone3" v-if="fundInfo.status <= 3" placeholder="请选择">
                  <Option v-for="(item, $index) in schzoneList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
                <Select v-model="formList.schzone3" v-if="fundInfo.status > 3" disabled placeholder="请选择">
                  <Option v-for="(item, $index) in schzoneList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
              </Col>
              <Col span="2"><p class="form-label">名额：</p></Col>
              <Col span="6">
                <Input v-model="formList.schzone3ren" placeholder="请输入"></Input>
              </Col>
              <Col span="2"><p class="form-label">资助总金额：</p></Col>
              <Col span="6">
                <Input v-model="formList.schzone3money" placeholder="请输入"></Input>
              </Col>
            </Row>
          </div>
          <div class="form-con" v-if="fundInfo.subtype == 113">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">选择档次：</p></Col>
              <Col span="6">
                <Select v-model="formList.level1" v-if="fundInfo.status <= 3" placeholder="请选择">
                  <Option v-for="(item, $index) in levelList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
                <Select v-model="formList.level1" v-if="fundInfo.status > 3" disabled placeholder="请选择">
                  <Option v-for="(item, $index) in levelList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
              </Col>
              <Col span="2"><p class="form-label">名额：</p></Col>
              <Col span="6">
                <Input v-model="formList.level1ren" placeholder="请输入"></Input>
              </Col>
              <Col span="2"><p class="form-label">资助总金额：</p></Col>
              <Col span="6">
                <Input v-model="formList.level1money" placeholder="请输入"></Input>
              </Col>
            </Row>
          </div>
          <div class="form-con" v-if="fundInfo.subtype == 113">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">选择档次：</p></Col>
              <Col span="6">
                <Select v-model="formList.level2" v-if="fundInfo.status <= 3" placeholder="请选择">
                  <Option v-for="(item, $index) in levelList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
                <Select v-model="formList.level2" v-if="fundInfo.status > 3" disabled placeholder="请选择">
                  <Option v-for="(item, $index) in levelList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
              </Col>
              <Col span="2"><p class="form-label">名额：</p></Col>
              <Col span="6">
                <Input v-model="formList.level2ren" placeholder="请输入"></Input>
              </Col>
              <Col span="2"><p class="form-label">资助总金额：</p></Col>
              <Col span="6">
                <Input v-model="formList.level2money" placeholder="请输入"></Input>
              </Col>
            </Row>
          </div>
          <div class="form-con" v-if="fundInfo.subtype == 113">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">选择档次：</p></Col>
              <Col span="6">
                <Select v-model="formList.level3" v-if="fundInfo.status <= 3" placeholder="请选择">
                  <Option v-for="(item, $index) in levelList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
                <Select v-model="formList.level3" v-if="fundInfo.status > 3" disabled placeholder="请选择">
                  <Option v-for="(item, $index) in levelList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                </Select>
              </Col>
              <Col span="2"><p class="form-label">名额：</p></Col>
              <Col span="6">
                <Input v-model="formList.level3ren" placeholder="请输入"></Input>
              </Col>
              <Col span="2"><p class="form-label">资助总金额：</p></Col>
              <Col span="6">
                <Input v-model="formList.level3money" placeholder="请输入"></Input>
              </Col>
            </Row>
          </div>
          <div class="form-con">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">备注：</p></Col>
              <Col span="6">
                <Input v-model="formList.desc" type="textarea" :autosize="{minRows: 5,maxRows: 10}" placeholder="请输入..."></Input>
              </Col>
            </Row>
          </div>
          <div class="form-con">
            <Row :gutter="10">
              <Col span="2"><p class="form-label">&nbsp;</p></Col>
              <Col span="22">
                <Button type="primary" @click="save()">保存配置</Button>
                <span style="margin-left: 10px;color: #f68f70;">注：1. 只能处于[项目配置]状态的项目才允许编辑名额；2. 批量编辑名额时，非[项目配置]状态下的条目将不会被更新！</span>
              </Col>
            </Row>
          </div>
        </div>

        <div class="do-some clearfix">
          <div class="f-fl">
            <Button type="primary" @click="modalFile = true">导入数据</Button>
            <Modal v-model="modalFile" title="导入数据" :ok-text="'确定导入'" @on-ok="dataIn" @on-cancel="modalFile = false">
              <div class="data-in">
                <h3>请按以下步骤进行数据导入</h3>
                <p><span>1.</span>下载模板，以便导入数据 <Button type="primary" size="small" @click="downFile()">下载模板</Button></p>
                <p><span>2.</span>已填好数据，导入数据 <input type="file" id="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" @change="getFile()"></p>
              </div>
            </Modal>
          </div>
          <div class="f-fr">
            <Input placeholder="搜索" style="width: 300px">
              <Button slot="append"><i class="iconfont" style="font-size: 12px;">&#xe60c;</i></Button>
            </Input>
          </div>
        </div>

        <v-table v-if="listData.length" :items="listData" :header="[]">
          <template slot="t-header">
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="schoolName"><span>学校名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" v-if="fundInfo.subtype == '112'"><p sort-key="schzone"><span>学段</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" v-if="fundInfo.subtype == '113'"><p sort-key="level"><span>资助级别</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="fundedName"><span>项目名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="statusName"><span>状态</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="count"><span>资助名额</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 120px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.schoolName}}</td>
              <td class="center" v-if="fundInfo.subtype == '112'">{{props.data.schzoneName}}</td>
              <td class="center" v-if="fundInfo.subtype == '113'">{{props.data.levelName}}</td>
              <td class="center">{{props.data.fundedName}}</td>
              <td class="center">{{props.data.statusName}}</td>
              <td class="center">{{props.data.count}}</td>
              <td class="center">
                <Button type="primary"  @click="edit(props.data.id)">编辑</Button>
                <!--<Button type="primary" v-if="props.data.status > 3" disabled @click="edit(props.data.id)">编辑名额</Button>-->
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

        <div class="edit">
          <Modal v-model="modalEdit" title="编辑" :ok-text="'保存'" @on-ok="updateInfo" @on-cancel="modalEdit = false">
            <div class="edit-con">
              <Form ref="formEdit" :model="formEdit" :rules="ruleEdit" :label-width="80" style="padding-top: 15px;">
                <FormItem label="学校名称" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.schoolName" disabled style="width: 300px;"></Input>
                </FormItem>
                <FormItem label="项目名称" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.schoolItem" disabled style="width: 300px;"></Input>
                </FormItem>
                <FormItem label="总金额" prop="totalMoney" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.totalMoney" placeholder="请输入" style="width: 300px;"></Input>
                </FormItem>
                <FormItem label="名额" prop="count" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.count" placeholder="请输入" style="width: 300px;"></Input>
                </FormItem>
              </Form>
            </div>
          </Modal>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import Location from 'js/module/components/location/location'
import vTable from 'js/module/components/table/table'
import Nodata from 'js/module/components/nodata/nodata'
import $ from 'jquery'
import _ from 'lodash'

export default {
  name: 'fundGuojia',
  data () {
    return {
      fundId: '',
      fundInfo: {
        name: '',
        semester: '',
        status: '',
        subtype: ''
      },
      formList: {
        jine: '',
        minge: '',
        schools: [],
        desc: '',
        isAll: false,
        schzone1: '',
        schzone2: '',
        schzone3: '',
        schzone1ren: '',
        schzone2ren: '',
        schzone3ren: '',
        schzone1money: '',
        schzone2money: '',
        schzone3money: '',
        level1: '',
        level2: '',
        level3: '',
        level1ren: '',
        level2ren: '',
        level3ren: '',
        level1money: '',
        level2money: '',
        level3money: ''
      },
      schoolList: [],
      schzoneList: [],
      levelList: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      formData: null,
      modalFile: false,

      // 编辑
      formEdit: {
        schoolName: '',
        schoolItem: '',
        schoolMinge: '',
        totalMoney:'',
       count:''
      },
      ruleEdit: {
        schoolMinge: [{ required: true, message: '请输入名额', trigger: 'blur' }]
      },
      editId: '',
      modalEdit: false
    }
  },
  components: { Location, vTable, Nodata },
  methods: {
    // 获取项目信息
    getFundInfo (id) {
      io.get(api.funded.info + '/' + id, (res) => {
        let data = res.data
        this.fundInfo = data.ermFunded

        this.getList()
        this.getSchoolList(this.fundInfo.id)

        if (this.fundInfo.subtype === '112') {
          this.getZone()
        }
        if (this.fundInfo.subtype === '113') {
          this.getLevel()
        }
      })
    },
    // 获取学校列表
    getSchoolList (id) {
      io.get(api.school.listByFundedId, {
        fundedId: id
      }, (res) => {
        let data = res.data
        this.schoolList = data.page.list
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
    // 获取列表
    getList () {
      let _data = _.merge({}, this.page, {
        type: this.fundInfo.type,
        subtype: this.fundInfo.subtype,
        fundedId: this.fundInfo.id
      })

      io.get(api.schoolFunded.list, _data, (res) => {
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
    // 启动项目
    startFund (id) {
      io.post(api.fundProcess.start + '/' + id, (res) => {
        this.$Message.success('项目启动成功！')
        this.getFundInfo(this.fundId)
        this.getList()
      })
    },
    // 保存配置
    save () {
      let levelList = []

      if (this.fundInfo.subtype === '113') {
        if ((+this.formList.level1ren) + (+this.formList.level2ren) + (+this.formList.level3ren) > this.formList.minge) {
          this.$Message.error('名额相加大于总名额！')
          return
        } else {
          if (this.formList.level1) {
            levelList.push({
              'levelCode': this.formList.level1,
              'levelCount': this.formList.level1ren,
              'levelMoney': this.formList.level1money
            })
          }
          if (this.formList.level2) {
            levelList.push({
              'levelCode': this.formList.level2,
              'levelCount': this.formList.level2ren,
              'levelMoney': this.formList.level2money
            })
          }
          if (this.formList.level3) {
            levelList.push({
              'levelCode': this.formList.level3,
              'levelCount': this.formList.level3ren,
              'levelMoney': this.formList.level3money
            })
          }
        }
      }

      if (this.fundInfo.subtype === '112') {
        if ((+this.formList.schzone1ren) + (+this.formList.schzone2ren) + (+this.formList.schzone3ren) > this.formList.minge) {
          this.$Message.error('名额相加大于总名额！')
          return
        } else {
          if (this.formList.schzone1) {
            levelList.push({
              'schzone': this.formList.schzone1,
              'levelCount': this.formList.schzone1ren,
              'levelMoney': this.formList.schzone1money
            })
          }
          if (this.formList.schzone2) {
            levelList.push({
              'schzone': this.formList.schzone2,
              'levelCount': this.formList.schzone2ren,
              'levelMoney': this.formList.schzone2money
            })
          }
          if (this.formList.schzone3) {
            levelList.push({
              'schzone': this.formList.schzone3,
              'levelCount': this.formList.schzone3ren,
              'levelMoney': this.formList.schzone3money
            })
          }
        }
      }

      if (this.fundInfo.subtype === '112' || this.fundInfo.subtype === '113') {
        io.post(api.schoolFunded.saveNew, {
          count: this.formList.minge,
          fundedId: this.fundInfo.id,
          schIdList: this.formList.schools,
          isAll: this.formList.isAll,
          note: this.formList.desc,
          levelList: levelList || []
        }, (res) => {
          this.$Message.success('项目配置成功！')
          this.getList()
        }, (e) => {
          this.$Message.info(e.data.msg)
        })
      } else {
        io.post(api.schoolFunded.batchsave, {
          count: this.formList.minge,
          fundedId: this.fundInfo.id,
          schIdList: this.formList.schools,
          isAll: this.formList.isAll,
          note: this.formList.desc,
          totalMoney: this.formList.jine
        }, (res) => {
          this.$Message.success('项目配置成功！')
          this.getList()
        }, (e) => {
          this.$Message.info(e.data.msg)
        })
      }
    },
    // 编辑
    edit (id) {
      let item = _.find(this.listData, (o) => o.id === id)
      this.formEdit.schoolName = item.schoolName
      this.formEdit.schoolItem = item.fundedName
      this.formEdit.schoolMinge = item.count
      this.formEdit. totalMoney=item.totalMoney
      this.formEdit. count=item.count
      this.modalEdit = true
      this.editId = id
    },
    // 保存
    updateInfo () {
      io.post(api.schoolFunded.edit, {
        id: this.editId,
        count: this.formEdit.count,
        totalMoney: this.formEdit.totalMoney
      }, (res) => {
        this.$Message.success('编辑成功！')
        this.getList()
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
    // 下载模板
    downFile () {
      io.get(api.schoolFunded.template, {
        type: this.fundId
      }, (res) => {
        let url = res.data.fileName

        io.post(api.file.download, {
          fileName: url
        }, (res) => {
          let blob = res
          let reader = new FileReader()
          reader.readAsDataURL(blob)
          reader.onload = (e) => {
            let a = document.createElement('a')
            a.download = url
            a.href = e.target.result
            $('body').append(a)
            a.click()
            $(a).remove()
          }
        }, (e) => {}, (e) => {}, { responseType: 'blob' })

        // $(`<form action="http://erm.comezx.com${api.file.download}" method="get">${this.generatorInput({ fileName: res.data.fileName })}</form>`).appendTo('body').submit().remove()
      })
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
    // 数据导入
    dataIn () {
      this.$Spin.show()
      io.post(api.schoolFunded.import + '?fundedId=' + this.fundId, this.formData, (res) => {
        this.$Spin.hide()
        this.$Message.info('导入数据成功' + res.data.resultData.successNum + '条，失败' + res.data.resultData.failNum + '条')
        this.getList()

        if (res.data.resultData.failList && res.data.resultData.failList.length) {
          res.data.resultData.failList.forEach(item => {
            this.$Message.error({
              content: item,
              duration: 0,
              closable: true
            })
          })
        }
      }, (e) => {
        this.$Spin.hide()
      }, (e) => {
        this.$Spin.hide()
      })
    }
  },
  watch: {
    '$route' (to) {
      this.fundId = to.params.fundId
      this.fundList = []
      this.getFundInfo(this.fundId)
      this.getList()
    },
    curFund: function (val) {
      this.fundInfo = _.find(this.fundList, (o) => o.id == val)
    }
  },
  mounted () {
    this.fundId = this.$route.params.fundId
    this.getFundInfo(this.fundId)
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
    .page {
      margin-top: 10px;
    }
    .do-some {
      margin-bottom: 10px;
    }
    .fund-con {
      border-bottom: 1px dashed #d5d5d5;
      margin-bottom: 10px;
      h3 {
        padding-left: 5px;
        margin-top: 10px;
        margin-bottom: 10px;
        height: 20px;
        line-height: 20px;
        font-weight: normal;
        border-left: 3px solid $color-primary-blue;
      }
      .form-con {
        margin-top: 15px;
        margin-bottom: 15px;
        p {
          height: 32px;
          line-height: 32px;
        }
        .form-label {
          color: $form-label-color;
          height: 32px;
          line-height: 32px;
          text-align: right;
          font-size: 12px;
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
