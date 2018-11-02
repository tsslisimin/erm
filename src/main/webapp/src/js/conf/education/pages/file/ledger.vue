<template>
  <div class="wrapper">
    <Location>日常办公 > 台账查看</Location>
    <div class="con">
      <Card :padding="10">

        <div class="form-con">
          <Row :gutter="10">
            <Col span="2"><p class="form-label">项目：</p></Col>
            <Col span="6">
            <Cascader :data="dataItems" v-model="projectItem" trigger="hover" placeholder="请选择"></Cascader>
            </Col>
            <Col span="2"><p class="form-label">学校：</p></Col>
            <Col span="6">
            <Select v-model="formList.school" filterable placeholder="请选择">
              <Option v-for="item in schoolList" :value="item.id" :key="item.id">{{item.name}}</Option>
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
        <div class="do-some clearfix" v-if="listData.length">
          <div class="f-fl">
            <Button type="primary" @click="dataOut()">导出数据</Button>  <Button type="primary" @click="modalFile = true">信息对比</Button>
            <!-- <Modal v-model="modalFile" title="导入数据" :ok-text="'确定导入'" @on-ok="dataIn" @on-cancel="modalFile = false">
              <div class="data-in">
                <h3>请按以下步骤进行数据导入</h3>
                <p><span>1.</span>下载模板，以便导入数据 <Button type="primary" size="small" @click="downFile()">下载模板</Button></p>
                <p><span>2.</span>已填好数据，导入数据 <input type="file" id="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" @change="getFile()"></p>
              </div>
            </Modal> -->
          </div>
        </div>


        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="[]">
          <template slot="t-header">
            <tr class="v-thead">
            <th class="center" colspan="8"><p><span>学生及家长基本信息</span></p></th>
            <th class="center" colspan="7"><p><span>学生就读信息</span></p></th>
            <th class="center" colspan="6"><p><span>家庭住址</span></p></th>
            <th class="center" colspan="1"><p><span>贫困类型</span></p></th>
            <th class="center" colspan="4"><p><span>“湖南省扶贫补贴明白折”信息</span></p></th>
            <th class="center" colspan="2"><p><span>“学生资助卡”信息</span></p></th>
            <th class="center" colspan="4"><p><span>学生帮扶责任人</span></p></th>
            </tr>
            <tr class="v-thead">
              <th class="left"><p><span>序号</span></p></th>
              <th class="left"><p><span>学生姓名</span></p></th>
              <th class="left"><p><span>性别</span></p></th>
              <!--<th class="left"><p><span>学生姓名</span></p></th>-->
              <th class="left"><p><span>年龄</span></p></th>
              <th class="left"><p><span>身份证号</span></p></th>
              <th class="left"><p><span>家长姓名</span></p></th>
              <th class="left"><p><span>电话号码</span></p></th>
              <th class="left"><p><span>家庭人口数（人）</span></p></th>
              <th class="left"><p><span>乡镇</span></p></th>
              <th class="left"><p><span>学校名称</span></p></th>
              <th class="left"><p><span>学籍号</span></p></th>
              <th class="left"><p><span>就读阶段</span></p></th>
              <th class="left"><p><span>年级</span></p></th>
              <th class="left"><p><span>班次</span></p></th>
              <th class="left"><p><span>是否寄宿</span></p></th>
              <th class="left"><p><span>省</span></p></th>
              <th class="left"><p><span>市</span></p></th>
              <th class="left"><p><span>县</span></p></th>
              <th class="left"><p><span>乡镇</span></p></th>
              <th class="left"><p><span>村（居委会）</span></p></th>
              <th class="left"><p><span>组（号）</span></p></th>
              <th class="left"><p><span>贫困类型</span></p></th>
              <th class="left"><p><span>姓 名（账户名）</span></p></th>
              <th class="left"><p><span>账号</span></p></th>
              <th class="left"><p><span>身份证号</span></p></th>
              <th class="left"><p><span>与学生关系</span></p></th>
              <th class="left"><p><span>姓 名（账户名）</span></p></th>
              <th class="left"><p><span>银行账号</span></p></th>
              <th class="left"><p><span>姓名</span></p></th>
              <th class="left"><p><span>单位</span></p></th>
              <th class="left"><p><span>职务</span></p></th>
              <th class="left"><p><span>电话号码</span></p></th>
            </tr>
            <!--<th class="center" style="width: 200px;"><p><span>操作</span></p></th>-->
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.data.No}}</td>
              <td class="center">{{props.data.name}}</td>
              <td class="center">{{props.data.gender}}</td>
              <td class="center">{{props.data.age}}</td>
              <td class="center">{{props.data.idcard}}</td>
              <td class="center">{{props.data.parent_name}}</td>
              <td class="center">{{props.data.telphone}}</td>
              <td class="center">{{props.data.students_count}}</td>

              <td class="center">{{props.data.study_place}}</td>
              <td class="center">{{props.data.schoolName}}</td>
              <td class="center">{{props.data.stuno}}</td>
              <td class="center">{{props.data.zone}}</td>
              <td class="center">{{props.data.grade}}</td>
              <td class="center">{{props.data.clazz}}</td>
              <td class="center">{{props.data.lodging}}</td>
              <td class="center">{{props.data.addressProvince}}</td>
              <td class="center">{{props.data.addressCity}}</td>
              <td class="center">{{props.data.addressArea}}</td>
              <td class="center">{{props.data.addressTown}}</td>
              <td class="center">{{props.data.addressTownship}}</td>
              <td class="center">{{props.data.addressGroup}}</td>
              <td class="center">{{props.data.is_poor}}</td>
              <td class="center">{{props.data.archive_name}}</td>
              <td class="center">{{props.data.archiveAcount}}</td>
              <td class="center">{{props.data.archive_idcard}}</td>
              <td class="center">{{props.data.archiveRelation}}</td>
              <td class="center">{{props.data.supportName}}</td>
              <td class="center">{{props.data.supportBankCard}}</td>
              <td class="center">{{props.data.helperName}}</td>
              <td class="center">{{props.data.helperWorkPlace}}</td>
              <td class="center">{{props.data.helperPosition}}</td>
              <td class="center">{{props.data.helperTel}}</td>
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
  import {api} from 'js/module/api/url'
  import vTable from 'js/module/components/table/tableplus'
  import $ from 'jquery'
  import _ from 'lodash'
  import Nodata from 'js/module/components/nodata/nodata'

  export default {
    name: 'ledger',
    data() {
      return {
        modalFile: false,
        formList: {
          school: '',
          fund: ''
        },
        dataItems:[],
        listData: [],
        schoolList:[],
        total: 0,
        page: {
          page: 1,
          limit: 10
        },
        projectItem:''
      }
    },
    components: {vTable, Location, Nodata},
    filters: {

    },
    methods: {
      // 获取列表
      getList() {
        this.formList.fund=this.projectItem
        if (this.formList.fund==''){
          this.$Message.error('请选择项目！')
          return
        }
        let searchData = _.merge({}, this.page, {
          fundId:this.formList.fund[1],
          schoolId:this.formList.school,
        })

        io.get(api.ledger.list, searchData, (res) => {
          let data = res.data
          this.listData = data.page.list
          this.total = data.page.totalCount
        })
      },

      // 获取学校列表
      getSchoolList () {
        io.get(api.school.listByFundedId,{ fundedId: this.projectItem[1]}, (res) => {
          let data = res.data
          this.schoolList = data.page.list
        })
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
          console.log(this.dataItems)
        })
      },
      // 分页操作
      pageChange(page) {
        this.page.page = page
        this.getList()
      },
      del(id) {
        let ids = [id]
        io.post(api.logs.delete, ids, (res) => {
          this.$Message.success('删除成功！')
          this.getList()
        })
      },
      checkedOne(index) {
        this.listData[index].checked = !this.listData[index].checked;
      },
      deleteAll() {
        let ids = [];
        this.listData.forEach(item => {
          if (item.checked == true) {
            ids.push(item.id);
          }
        })
        io.post(api.student.delete, ids, (res) => {
          this.$Message.success('删除成功！')
          this.getList()
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
      dataOut(){
        if (this.formList.fund==''){
          this.$Message.error('请选择项目！')
          return
        }
        let searchData = _.merge({}, this.page, {
          fundId:this.formList.fund[1],
          schoolId:this.formList.school,
        })
        io.post(api.total.export+"?fundId="+this.formList.fund[1]+"&schoolId="+this.formList.school, {
        }, (res) => {
          let blob = res
          let reader = new FileReader()
          reader.readAsDataURL(blob)
          reader.onload = (e) => {
            let a = document.createElement('a')
            a.download = '台账导出.xls'
            a.href = e.target.result
            $('body').append(a)
            a.click()
            $(a).remove()
          }
        }, (e) => {}, (e) => {}, { responseType: 'blob' })
      }

    },

    watch: {
      projectItem (o) {
       //let _item = _.find(this.schFundList, (n) => o[1] == n.id)
        // this.subType = _item.subtype
        // this.level = ''
        // this.schzone = ''
        this.getSchoolList()
      }
    },
    mounted() {
      this.getProjectList();
     // this.getSchoolList();
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

  .main-r{
    max-width: 996px;
    overflow: auto;
  }
  .v-thead {

    height: 40px;
    font-size: 15px;

    th {
      border-right:1px solid #fff;
      border-bottom:1px solid #fff;
      font-weight: normal;
      padding: 8px;
      user-select: none;
      text-align: center;
      color: #fff;
      &.left {
        text-align: left;
      }
      &.center {
        text-align: center;
      }
      &.right {
        text-align: right;
      }

      p {
        &[sort-key] {
          cursor: pointer;
        }
        .sort {
          margin-left: 3px;
          position: relative;
          i {
            font-size: 12px;
            position: absolute;
            left: 0;
            color: #fff;
            &.up {
              top: -5px;
            }
            &.down {
              top: 3px;
            }
          }

          &.up {
            i {
              &.up {
                color: #1875D1;
              }
              &.down {
                color: #fff;
              }
            }
          }

          &.down {
            i {
              &.up {
                color: #fff;
              }
              &.down {
                color: #1875D1;
              }
            }
          }
        }
      }
    }
  }
  .v-thead{

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
