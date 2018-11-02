<template>
  <div class="wrapper">
    <Location>设置 > 账户管理</Location>
    <div class="con">
      <Card :padding="10">
        <Form ref="formList" :model="formList" :rules="ruleList" :label-width="80" style="padding-top: 15px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
          <FormItem label="选择学校" prop="schoolName" style="margin-bottom: 20px;">
            <Select v-model="formList.schoolName" filterable placeholder="请选择" style="width: 300px;">
              <Option v-for="item in schoolList" :value="item.id" :key="item.id">{{item.name}}</Option>
            </Select>
          </FormItem>
          <FormItem label="分给校长" style="margin-bottom: 20px;">
            <div class="form-inline">
              <Row>
                <Col span="2" style="text-align: right;padding-right: 10px;">账号</Col>
                <Col span="6">
                  <FormItem>
                    <Input v-model="formList.schoolUser" placeholder="请输入"></Input>
                  </FormItem>
                </Col>
                <Col span="2" style="text-align: right;padding-right: 10px;">密码</Col>
                <Col span="6">
                  <FormItem>
                    <Input type="password" v-model="formList.schoolPsw" placeholder="请输入"></Input>
                  </FormItem>
                </Col>
                <Col span="2" style="text-align: right;padding-right: 10px;">确认密码</Col>
                <Col span="6">
                  <FormItem>
                    <Input type="password" v-model="formList.schoolPswCheck" placeholder="请输入"></Input>
                  </FormItem>
                </Col>
              </Row>
            </div>
          </FormItem>
          <FormItem label="分给操作员" style="margin-bottom: 20px;">
            <div class="form-inline">
              <Row>
                <Col span="2" style="text-align: right;padding-right: 10px;">账号</Col>
                <Col span="6">
                  <FormItem>
                    <Input v-model="formList.operateUser" placeholder="请输入"></Input>
                  </FormItem>
                </Col>
                <Col span="2" style="text-align: right;padding-right: 10px;">密码</Col>
                <Col span="6">
                  <FormItem>
                    <Input type="password" v-model="formList.operatePsw" placeholder="请输入"></Input>
                  </FormItem>
                </Col>
                <Col span="2" style="text-align: right;padding-right: 10px;">确认密码</Col>
                <Col span="6">
                  <FormItem>
                    <Input type="password" v-model="formList.operatePswCheck" placeholder="请输入"></Input>
                  </FormItem>
                </Col>
              </Row>
            </div>
          </FormItem>
          <FormItem>
            <Button type="primary" @click="submit('formList')">提 交</Button>
          </FormItem>
        </Form>

        <div class="do-some clearfix">
          <div class="f-fl">
            <Button type="primary" @click="dataOut()">导出数据</Button>&nbsp;
            <!--<Button type="primary" @click="autoAccount()">自动生成账号</Button>-->
          </div>
        </div>

        <v-table v-if="listData.length" :items="listData" :header="[]">
          <template slot="t-header">
            <th class="left"><p sort-key="schoolName"><span>学校名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="roleName"><span>账号类型</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="username"><span>账号</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 200px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.data.schoolName}}</td>
              <td class="center">{{props.data.roleName}}</td>
              <td class="center">{{props.data.username}}</td>
              <td class="center"><Button type="primary" @click="edit(props.data.id)">编辑</Button>&nbsp;&nbsp;&nbsp;<Button type="error" @click="del(props.data.id)">删除</Button></td>
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
          <Modal v-model="modalEdit" title="编辑账号" :ok-text="'保存'" @on-ok="updateInfo" @on-cancel="modalEdit = false">
            <div class="edit-con">
              <Form ref="formEdit" :model="formEdit" :rules="ruleEdit" :label-width="80" style="padding-top: 15px;">
                <FormItem label="学校名称" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.schoolName" disabled style="width: 300px;"></Input>
                </FormItem>
                <FormItem label="账号类型" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.roleName" disabled style="width: 300px;"></Input>
                </FormItem>
                <FormItem label="账号" prop="user" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.user" placeholder="请输入" style="width: 300px;"></Input>
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
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import vTable from 'js/module/components/table/table'
import _ from 'lodash'
import $ from 'jquery'
import Nodata from 'js/module/components/nodata/nodata'
import cookie from 'js/lib/util/cookie'

export default {
  name: 'manageZhanghu',
  data () {
    return {
      formList: {
        schoolName: '',
        schoolUser: '',
        schoolPsw: '',
        schoolPswCheck: '',
        operateUser: '',
        operatePsw: '',
        operatePswCheck: ''
      },
      ruleList: {
        schoolName: [{ required: true, type: 'number', message: '请选择学校', trigger: 'change' }]
      },
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      schoolList: [],

      // 编辑
      formEdit: {
        schoolName: '',
        roleName: '',
        user: ''
      },
      ruleEdit: {
        user: [{ required: true, message: '请输入账号', trigger: 'blur' }]
      },
      editId: '',
      modalEdit: false
    }
  },
  components: { vTable, Location, Nodata },
  methods: {
    // 提交
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let users = []

          if (this.formList.schoolUser && this.formList.schoolPsw) {
            users.push({
              schoolId: this.formList.schoolName,
              username: this.formList.schoolUser,
              password: this.formList.schoolPsw,
              roleId: 3
            })
          }
          if (this.formList.operateUser && this.formList.operatePsw) {
            users.push({
              schoolId: this.formList.schoolName,
              username: this.formList.operateUser,
              password: this.formList.operatePsw,
              roleId: 4
            })
          }

          if (users.length) {
            io.post(api.admin.saveAdmins, users, (res) => {
              this.$Message.success('分配帐号成功！')
              this.getCrtList(this.formList.schoolName)
            })
          }
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取账号列表
    getList () {
      io.get(api.admin.list, this.page, (res) => {
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
    // 获取当前学校账号列表
    getCrtList (id) {
      io.get(api.admin.list, {
        schoolId: id
      }, (res) => {
        let data = res.data
        this.listData = data.page.list
        this.total = data.page.totalCount
      })
    },
    // 获取学校列表
    getSchoolList () {
      io.get(api.school.list, (res) => {
        let data = res.data
        this.schoolList = data.page.list
      })
    },
    // 编辑
    edit (id) {
      let item = _.find(this.listData, (o) => o.id === id)
      this.formEdit.schoolName = item.schoolName
      this.formEdit.roleName = item.roleName
      this.formEdit.user = item.username
      this.modalEdit = true
      this.editId = id
    },
    // 保存
    updateInfo () {
      io.post(api.admin.edit, {
        id: this.editId,
        username: this.formEdit.user
      }, (res) => {
        this.$Message.success('编辑成功！')
        this.getList()
      })
    },
    // 删除
    del (id) {
      let ids = [id]
      io.post(api.admin.delete, ids, (res) => {
        this.$Message.success('删除成功！')
        this.getList()
      })
    },
    // 数据导出
    dataOut () {
      io.post(api.admin.export, (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = '账号导出.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })

      // $(`<form action="http://erm.comezx.com${api.admin.export}" method="post"></form>`).appendTo('body').submit().remove()
    },
    // 自动生成账号
    autoAccount () {
      io.post(api.admin.create, (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = '账号生成导出.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()

          this.getList()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })

      // $(`<form action="http://erm.comezx.com${api.admin.create}" method="post"></form>`).appendTo('body').submit().remove()
    }
  },
  watch: {
    'formList.schoolName' (o) {
      this.getCrtList(o)
    }
  },
  mounted () {
    this.getList()
    this.getSchoolList()
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
    .form-inline {
      padding: 5px 10px;
      background-color: #f5f5f5;
      border: 1px solid $border-default-color;
      border-radius: 3px;
    }
  }
}
</style>
