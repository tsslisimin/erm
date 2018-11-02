<template>
  <div class="wrapper">
    <Location>资助管理 > 分配资助名额</Location>
    <div class="con">
      <Card :padding="10">
        <Form ref="formList" :model="formList" :rules="ruleList" :label-width="80" style="padding-top: 15px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
          <FormItem label="选择学校" prop="schoolName" style="margin-bottom: 20px;">
            <Select v-model="formList.schoolName" filterable placeholder="请选择" style="width: 300px;">
              <Option v-for="item in schoolList" :value="item.id" :key="item.id">{{item.name}}</Option>
            </Select>
          </FormItem>
          <FormItem label="选择项目" prop="schoolItem" style="margin-bottom: 20px;">
            <Cascader :data="dataItems" v-model="formList.schoolItem" trigger="hover" placeholder="请选择" style="width: 300px;"></Cascader>
          </FormItem>
          <FormItem label="分配名额" prop="schoolMinge" style="margin-bottom: 20px;">
            <Input v-model="formList.schoolMinge" placeholder="请输入" style="width: 300px;"></Input>
          </FormItem>
          <FormItem>
            <Button type="primary" @click="submit('formList')">提 交</Button>
          </FormItem>
        </Form>

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
            <th class="center"><p sort-key="fundedName"><span>项目名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="count"><span>名额</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 200px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.schoolName}}</td>
              <td class="center">{{props.data.fundedName}}</td>
              <td class="center">{{props.data.count}}</td>
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
          <Modal v-model="modalEdit" title="编辑名额" :ok-text="'保存'" @on-ok="updateInfo" @on-cancel="modalEdit = false">
            <div class="edit-con">
              <Form ref="formEdit" :model="formEdit" :rules="ruleEdit" :label-width="80" style="padding-top: 15px;">
                <FormItem label="学校名称" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.schoolName" disabled style="width: 300px;"></Input>
                </FormItem>
                <FormItem label="项目名称" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.schoolItem" disabled style="width: 300px;"></Input>
                </FormItem>
                <FormItem label="分配名额" prop="schoolMinge" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.schoolMinge" placeholder="请输入" style="width: 300px;"></Input>
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

export default {
  name: 'manageFenpei',
  data () {
    return {
      formList: {
        schoolName: '',
        schoolItem: [],
        schoolMinge: ''
      },
      ruleList: {
        schoolName: [{ required: true, type: 'number', message: '请选择学校', trigger: 'change' }],
        schoolItem: [{ required: true, type: 'array', min: 1, message: '请选择资助项目', trigger: 'change' }],
        schoolMinge: [{ required: true, message: '请输入名额', trigger: 'blur' }]
      },
      listData: [],
      dataItems: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      formData: null,
      modalFile: false,
      schoolList: [],

      // 编辑
      formEdit: {
        schoolName: '',
        schoolItem: '',
        schoolMinge: ''
      },
      ruleEdit: {
        schoolMinge: [{ required: true, message: '请输入名额', trigger: 'blur' }]
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
          io.post(api.schoolFunded.save, {
            schoolId: this.formList.schoolName,
            fundedId: this.formList.schoolItem[1],
            count: this.formList.schoolMinge
          }, (res) => {
            this.$Message.success('分配名额成功！')
            this.getList()
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取列表
    getList () {
      io.get(api.schoolFunded.list, this.page, (res) => {
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
    // 获取学校列表
    getSchoolList () {
      io.get(api.school.list, (res) => {
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
    },
    // 编辑
    edit (id) {
      let item = _.find(this.listData, (o) => o.id === id)
      this.formEdit.schoolName = item.schoolName
      this.formEdit.schoolItem = item.fundedName
      this.formEdit.schoolMinge = item.count
      this.modalEdit = true
      this.editId = id
    },
    // 保存
    updateInfo () {
      io.post(api.schoolFunded.edit, {
        id: this.editId,
        count: this.formEdit.schoolMinge
      }, (res) => {
        this.$Message.success('编辑成功！')
        this.getList()
      })
    },
    // 删除
    del (id) {
      let ids = [id]
      io.post(api.schoolFunded.delete, ids, (res) => {
        this.$Message.success('删除成功！')
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
      io.get(api.schoolFunded.template, (res) => {
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
      io.post(api.schoolFunded.import, this.formData, (res) => {
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
      })
    }
  },
  mounted () {
    this.getList()
    this.getSchoolList()
    this.getProjectList()
  }
};
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
