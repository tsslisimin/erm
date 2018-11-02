<template>
  <div class="wrapper">
    <Location>设置 > 学校管理</Location>
    <div class="con">
      <Card :padding="10">
        <Form ref="formList" :model="formList" :rules="ruleList" :label-width="80" style="padding-top: 15px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
          <FormItem label="选择学段" prop="schoolZone" style="margin-bottom: 20px;">
            <Select v-model="formList.schoolZone" placeholder="请选择" style="width: 300px;">
              <Option v-for="(item, $index) in zoneData" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="输入校名" prop="schoolName" style="margin-bottom: 20px;">
            <Input v-model="formList.schoolName" placeholder="请输入" style="width: 300px;"></Input>
          </FormItem>
          <FormItem label="学校代码" prop="schoolCode" style="margin-bottom: 20px;">
            <Input v-model="formList.schoolCode" placeholder="请输入" style="width: 300px;"></Input>
          </FormItem>
          <FormItem>
            <Button type="primary" @click="submit('formList')">新 增</Button>
          </FormItem>
        </Form>

        <div class="do-some clearfix">
          <div class="f-fl">
            <Button type="primary" @click="modalFile = true">导入数据</Button>&nbsp;
            <Button type="primary" @click="dataOut()">导出数据</Button><span style="margin-left: 20px"> 学校数量：{{total}}个</span>
            <Modal v-model="modalFile" title="导入数据" :ok-text="'确定导入'" @on-ok="dataIn" @on-cancel="modalFile = false">
              <div class="data-in">
                <h3>请按以下步骤进行数据导入</h3>
                <p><span>1.</span>下载模板，以便导入数据 <Button type="primary" size="small" @click="downFile()">下载模板</Button></p>
                <p><span>2.</span>已填好数据，导入数据 <input type="file" id="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" @change="getFile()"></p>
              </div>
            </Modal>
          </div>
        </div>

        <v-table v-if="listData.length" :items="listData" :header="[]">
          <template slot="t-header">
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="type"><span>学段</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="name"><span>学校名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="code"><span>学校代码</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 200px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.typeStr}}</td>
              <td class="center">{{props.data.name}}</td>
              <td class="center">{{props.data.code}}</td>
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
          <Modal v-model="modalEdit" title="编辑数据" :ok-text="'保存'" @on-ok="updateInfo" @on-cancel="modalEdit = false">
            <div class="edit-con">
              <Form ref="formEdit" :model="formEdit" :rules="ruleEdit" :label-width="80" style="padding-top: 15px;">
                <FormItem label="选择学段" prop="schoolZone" style="margin-bottom: 20px;">
                  <Select v-model="formEdit.schoolZone" placeholder="请选择" style="width: 300px;">
                    <Option v-for="(item, $index) in zoneData" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
                  </Select>
                </FormItem>
                <FormItem label="输入校名" prop="schoolName" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.schoolName" placeholder="请输入" style="width: 300px;"></Input>
                </FormItem>
                <FormItem label="学校代码" prop="schoolCode" style="margin-bottom: 20px;">
                  <Input v-model="formEdit.schoolCode" placeholder="请输入" style="width: 300px;"></Input>
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
import $ from 'jquery'
import _ from 'lodash'
import Nodata from 'js/module/components/nodata/nodata'
import { domain } from 'js/module/api/domain'

export default {
  name: 'manageShezhi',
  data () {
    return {
      formList: {
        schoolName: '',
        schoolCode: '',
        schoolZone: ''
      },
      ruleList: {
        schoolName: [{ required: true, message: '请输入学校名称', trigger: 'blur' }],
        schoolCode: [{ required: true, message: '请输入学校代码', trigger: 'blur' }],
        schoolZone: [{ required: true, message: '请选择学段', trigger: 'change' }]
      },
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      formData: null,
      modalFile: false,
      zoneData: [],

      // 编辑
      formEdit: {
        schoolName: '',
        schoolCode: '',
        schoolZone: ''
      },
      ruleEdit: {
        schoolName: [{ required: true, message: '请输入学校名称', trigger: 'blur' }],
        schoolCode: [{ required: true, message: '请输入学校代码', trigger: 'blur' }],
        schoolZone: [{ required: true, message: '请选择学段', trigger: 'change' }]
      },
      editId: '',
      modalEdit: false
    }
  },
  components: { vTable, Location, Nodata },
  methods: {
    // 新增
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          io.post(api.school.save, {
            name: this.formList.schoolName,
            code: this.formList.schoolCode,
            type: this.formList.schoolZone
          }, (res) => {
            this.$Message.success('新增成功！')
            this.getList()
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取列表
    getList () {
      io.get(api.school.list, this.page, (res) => {
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
    // 编辑
    edit (id) {
      let item = _.find(this.listData, (o) => o.id === id)
      this.formEdit.schoolName = item.name
      this.formEdit.schoolCode = item.code
      this.formEdit.schoolZone = item.type
      this.modalEdit = true
      this.editId = id
    },
    // 编辑保存
    updateInfo () {
      io.post(api.school.edit, {
        id: this.editId,
        name: this.formEdit.schoolName,
        code: this.formEdit.schoolCode,
        type: this.formEdit.schoolZone
      }, (res) => {
        this.$Message.success('编辑成功！')
        this.getList()
      })
    },
    // 删除
    del (id) {
      let ids = [id]
      io.post(api.school.delete, ids, (res) => {
        this.$Message.success('删除成功！')
        this.getList()
      })
    },
    // 下载模板
    downFile () {
      io.get(domain.host + '/static/schoolmodel.xls', (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = 'schoolmodel.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })

      // $(`<form action="http://erm.comezx.com/static/schoolmodel.xls" method="get"></form>`).appendTo('body').submit().remove()
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
      io.post(api.school.import, this.formData, (res) => {
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
    },
    // 数据导出
    dataOut () {
      io.post(api.school.export, (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = '学校和学区.xls'
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })

      // $(`<form action="http://erm.comezx.com${api.school.export}" method="post"></form>`).appendTo('body').submit().remove()
    },
    // 获取学区
    getZone () {
      io.get(api.dict.schTypeDict, (res) => {
        let data = res.data
        this.zoneData = data.dict['21']
        this.getView()
      })
    }
  },
  mounted () {
    this.getList()
    this.getZone()
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
