<template>
  <div class="wrapper">
    <Location>学生管理 > 学生信息查看</Location>
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
            <Col span="3"><p class="form-label">身份证号：</p></Col>
            <Col span="6">
              <Input v-model="formList.idcard" placeholder="请输入"></Input>
            </Col>
            <!--<Col span="3"><p class="form-label">是否有头像：</p></Col>-->
            <!--<Col span="6">-->
            <!--<Select v-model="formList.hasAvatar" placeholder="请选择">-->
            <!--<Option value="1">有</Option>-->
            <!--<Option value="0">无</Option>-->
            <!--</Select>-->
            <!--</Col>-->

            <Col span="3"><p class="form-label">学校名称：</p></Col>
            <Col span="6">
              <Input v-model="formList.school" placeholder="请输入"></Input>
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
            <Button type="primary" @click="dataOut()">导出数据</Button>
            <Button type="primary" @click="modalFile = true">信息对比</Button>
            <Button type="primary" @click="excelFile = true">贫困户信息验证</Button>

          </div>
        </div>


        <Modal v-model="excelFile" title="导入数据" :ok-text="'确定导入'" @on-ok="excelImport" @on-cancel="excelFile = false">
          <div class="data-in">
            <Select style="width: 120px" v-model="formStu.isPoor" placeholder="请选择">
              <Option v-for="(item, $index) in formData.isPoorData" :value="item.dictCode" :key="$index">
                {{item.dictName}}
              </Option>
            </Select>
            <input type="file" id="excelFile"/>
          </div>
        </Modal>


        <div class="do-some clearfix">
          <div class="f-fr">
            <Button type="primary" @click="deleteAll()">批量删除</Button>
          </div>
        </div>
        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="[]">
          <template slot="t-header">
            <th slot="t-check" id="all-check" style="width: 30px;"><input type="checkbox" style="margin-top: 5px;"></th>
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="name"><span>姓名</span><span class="sort"><i
              class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="idcard"><span>身份证号</span><span class="sort"><i
              class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="hasAvatar"><span>学校名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i
              class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="stuno"><span>学号</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i
              class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 200px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td style="width: 30px;" class="check-box"><input type="checkbox" :data-id="props.data.id"
                                                                style="margin-top: 5px;"></td>
              <!--<td class="left"><checkbox :checked="props.data.checked" @click='checkedOne(props.idx)'></checkbox></td>-->
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">
                <router-link :to="{ name: 'studentDetail', params: { stuId: props.data.id } }">{{props.data.name}}
                </router-link>
              </td>
              <td class="center">{{props.data.idcard}}</td>
              <td class="center">{{props.data.school ==null ? props.data.schoolName : props.data.school}}</td>
              <td class="center">{{props.data.stuno}}</td>
              <td class="center">
                <Button type="primary" @click="edit(props.data.id)">编辑</Button>&nbsp;&nbsp;&nbsp;<Button type="error"
                                                                                                         @click="del(props.data.id)">
                删除
              </Button>
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


      </Card>
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
    </div>
  </div>
</template>

<script>
  import Location from 'js/module/components/location/location'
  import io from 'js/module/api/io'
  import {api} from 'js/module/api/url'
  import vTable from 'js/module/components/table/table'
  import $ from 'jquery'
  import _ from 'lodash'
  import Nodata from 'js/module/components/nodata/nodata'

  export default {
    name: 'stuManageXinxi',
    data() {
      return {
        formData: {
          schSystem: [],
          stuType: [],
          studyType: [],
          nature: [],
          accountType: [],
          addressType: [],
          politicalStatus: [],
          healthStatus: [],
          studentFrom: [],
          learnType: [],
          attendingType: [],
          nation: [],
          marriageStatus: [],
          divisionCode: [],
          studentObj: [],
          isTrue: [],
          recruitType: [],
          isPoorData: []
        },
        formStu: {
          name: '',
          idcard: '',
          photoUrl: '',
          pinyin: '',
          parentName: '',
          telphone: '',
          addressTown: '',
          addressTownship: '',
          addressGroup: '',
          addressProvince: '',
          addressCity: '',
          addressArea: '',
          grade: '',
          semester: '',
          schSystem: '',
          stuno: '',
          major: '',
          clazz: '',
          stuType: '',
          studyType: '',
          startYear: '',
          nature: '',
          schoolName: '',
          accountType: '',
          addressType: '',
          politicalStatus: '',
          healthStatus: '',
          studentFrom: '',
          learnType: '',
          attendingType: '',
          nation: '',
          marriageStatus: '',
          divisionCode: '',
          birthDivisionCode: '',
          registeredDivisionCode: '',
          birthPlace: '',
          policeStation: '',
          trainRegion: '',
          studentObj: '',
          studyPlace: '',
          isMove: '',
          transProvincial: '',
          recruitType: '',
          lodging: '',
          actualBankcard: '',
          isPoor: '',
          //'湖南省扶贫补贴明白折（建档立卡）人关系'
          archiveRelation: '',
          //'湖南省扶贫补贴明白折（建档立卡）人账号'
          archiveAcount: '',
          //'学生资助卡姓名'
          supportName: '',

          //'学生资助卡银行账号'
          supportBankCard: '',
          //'帮扶人姓名'
          helper: '',
          // '帮扶人单位'
          helperWorkPlace: '',
          //'帮扶人职位'
          helperPosition: '',
          //'帮扶人联系电话'
          helperTel: '',
          archiveName: '',
          archiveIdcard: '',
          familyNum: ''
        },
        excelFile: false,
        modalFile: false,
        formDatas: [],
        formList: {
          name: '',
          gender: '',
          stuno: '',
          minAge: '',
          maxAge: '',
          idcard: '',
          hasAvatar: '',
          school: ''
        },
        listData: [],
        total: 0,
        page: {
          page: 1,
          limit: 10
        },
        verifyData: []
      }
    },
    components: {vTable, Location, Nodata},
    methods: {
      // 获取下拉数据
      getSubList() {
        io.get(api.dict.list, (res) => {
          let data = res.data.dict

          this.formData.schSystem = data['14']
          this.formData.stuType = data['15']
          this.formData.studyType = data['20']
          this.formData.nature = data['2']
          this.formData.accountType = data['5']
          this.formData.addressType = data['6']
          this.formData.politicalStatus = data['4']
          this.formData.healthStatus = data['3']
          this.formData.studentFrom = data['7']
          this.formData.learnType = data['11']
          this.formData.attendingType = data['12']
          this.formData.nation = data['10']
          this.formData.marriageStatus = data['16']
          this.formData.divisionCode = data['9']
          this.formData.studentObj = data['17']
          this.formData.isTrue = data['19']
          this.formData.recruitType = data['13']

          this.formData.isPoorData = data['8']
        })
      },
      // 获取列表
      getList() {
        let searchData = _.merge({}, this.page, {
          name: this.formList.name,
          gender: this.formList.gender,
          stuno: this.formList.stuno,
          minAge: this.formList.minAge,
          maxAge: this.formList.maxAge,
          idcard: this.formList.idcard,
          hasAvatar: this.formList.hasAvatar,
          school: this.formList.school
        })

        io.get(api.student.list, searchData, (res) => {
          let data = res.data
          this.listData = data.page.list
          this.listData.forEach(item => {
            item.checked = false;
          })
          this.total = data.page.totalCount
        })
      },
      // 分页操作
      pageChange(page) {
        this.page.page = page
        this.getList()
      },
      // input 工具方法
      generatorInput(obj) {
        let result = ''
        for (var key in obj) {
          result += '<input type="hidden" name = "' + key + '" value="' + obj[key] + '">'
        }
        return result
      },


      addCheck() {
        $('.con').on('change', '#all-check input', (e) => {
          this.verifyData = []
          if ($(e.target).is(':checked')) {
            this.listData.forEach((item) => {
              this.verifyData.push(item.stu_id)
            })
            $('.check-box input').prop('checked', true)
          } else {
            $('.check-box input').prop('checked', false)
          }
        })
        $('.con').on('change', '.check-box input', (e) => {
          let _id = +$(e.target).attr('data-id')
          let _idx = this.verifyData.indexOf(_id)

          if ($(e.target).is(':checked')) {
            if (_idx === -1) {
              this.verifyData.push(_id)
            }
          } else {
            this.verifyData.splice(_idx, 1)
          }
        })


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
      excelImport() {
        console.log(this.formStu.isPoor)
        if (!this.formDatas) {
          return
        }
        if (this.formStu.isPoor === '') {
          this.$Message.error('请选择')
          return
        }
        let file = document.getElementById('excelFile')
        console.log(file);
        this.formDatas = new FormData()
        this.formDatas.append('file', file.files[0])
        this.formDatas.append('type', this.formStu.isPoor)


        this.$Spin.show()
        io.post(api.student.excelImport, this.formDatas, (res) => {
          this.$Spin.hide()
          console.log(res)
          // this.$Message.info('导入数据成功' + res.data.resultData.successNum + '条，失败' + res.data.resultData.failNum + '条')
          //
          // if (res.data.resultData.failList && res.data.resultData.failList.length) {
          //   res.data.resultData.failList.forEach(item => {
          //     this.$Message.error({
          //       content: item,
          //       duration: 0,
          //       closable: true
          //     })
          //   })
          // }
        }, (e) => {
          this.$Spin.hide()
          console.log(e)
          this.$Message.error(e.message)
        }, (e) => {
          this.$Spin.hide()
          this.$Message.error(e)

        })
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
      }
      ,
      // 数据导出
      dataOut() {
        io.post(api.student.export, {
          name: this.formList.name,
          gender: this.formList.gender,
          stuno: this.formList.stuno,
          minAge: this.formList.minAge,
          maxAge: this.formList.maxAge,
          idcard: this.formList.idcard,
          hasAvatar: this.formList.hasAvatar
        }, (res) => {
          let blob = res
          let reader = new FileReader()
          reader.readAsDataURL(blob)
          reader.onload = (e) => {
            let a = document.createElement('a')
            a.download = '学生信息导出.xls'
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
      edit(id) {
        this.$router.push({name: 'stuManage/mingdanedit', params: {stuId: id}})
      },
      del(id) {
        let ids = [id]
        io.post(api.student.delete, ids, (res) => {
          this.$Message.success('删除成功！')
          this.getList()
          this.verifyData = []
          $('#all-check input').prop('checked', false)
          $('.check-box input').prop('checked', false)
        })
      },

      deleteAll() {
        let ids = [];

        io.post(api.student.delete, this.verifyData, (res) => {
          this.$Message.success('删除成功！')
          this.getList()
          this.verifyData = []
          $('#all-check input').prop('checked', false)
          $('.check-box input').prop('checked', false)
        })
      }
    },
    watch: {
      verifyData(o) {
        if (o.length === this.listData.length) {
          $('#all-check input').prop('checked', true)
        } else {
          $('#all-check input').prop('checked', false)
        }
      }
    },
    mounted() {
      this.getSubList()
      this.getList()
      this.addCheck()
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
