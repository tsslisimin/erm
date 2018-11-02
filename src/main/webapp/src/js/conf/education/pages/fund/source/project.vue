<template>
  <div class="project">
    <Form ref="formList" :model="formList" :rules="ruleList" :label-width="80" style="padding-top: 15px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
      <FormItem label="项目名称" prop="itemName" style="margin-bottom: 20px;">
        <Input v-model="formList.itemName" placeholder="请输入" style="width: 300px;"></Input>
      </FormItem>
      <FormItem label="选择年份" prop="itemYear" style="margin-bottom: 20px;">
        <Select v-model="formList.itemYear" placeholder="请选择" style="width: 300px;">
          <Option v-for="(item, $index) in semesterData" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
        </Select>
      </FormItem>
      <FormItem label="项目描述" prop="itemContent" style="margin-bottom: 20px;">
        <Input v-model="formList.itemContent" type="textarea" :autosize="{minRows: 5,maxRows: 10}" placeholder="请输入..." style="width: 300px;"></Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="submit('formList')">新 增</Button>
      </FormItem>
    </Form>

    <div class="do-some clearfix">
      <div class="f-fr">
        <Input placeholder="搜索" style="width: 300px">
          <Button slot="append"><i class="iconfont" style="font-size: 12px;">&#xe60c;</i></Button>
        </Input>
      </div>
    </div>

    <v-table v-if="listData.length" :items="listData" :header="[]">
      <template slot="t-header">
        <th class="left"><p><span>序号</span></p></th>
        <th class="left"><p sort-key="name"><span>项目名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="describ"><span>项目描述</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="semester"><span>年份</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="status"><span>状态</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center" style="width: 200px;"><p><span>操作</span></p></th>
      </template>
      <template slot="t-body" slot-scope="props">
        <tr>
          <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
          <td class="left">{{props.data.name}}</td>
          <td class="center">{{props.data.describ}}</td>
          <td class="center">{{props.data.semester}}</td>
          <td class="center">{{props.data.statusName}}</td>
          <td class="center"><Button type="primary" @click="edit(props.data.id)">详情</Button>&nbsp;&nbsp;&nbsp;<Button type="error" @click="del(props.data.id)">删除</Button></td>
        </tr>
      </template>
    </v-table>

    <div class="page clearfix" v-if="total > 10">
      <div class="f-fr">
        <Page :total="total" show-total @on-change="pageChange"></Page>
      </div>
    </div>

    <Nodata v-if="!listData.length"></Nodata>
  </div>
</template>

<script>
  import io from 'js/module/api/io'
  import { api } from 'js/module/api/url'
  import vTable from 'js/module/components/table/table'
  import _ from 'lodash'
  import Nodata from 'js/module/components/nodata/nodata'

  export default {
    name: 'project',
    props: ['proType'],
    data () {
      return {
        formList: {
          itemName: '',
          itemYear: '',
          itemContent: ''
        },
        ruleList: {
          itemName: [{ required: true, message: '请输入名称', trigger: 'blur' }],
          itemYear: [{ required: true, message: '请选择年份', trigger: 'change' }],
          itemContent: [{ required: true, message: '请输入内容', trigger: 'blur' }]
        },
        semesterData: [],
        listData: [],
        total: 0,
        page: {
          page: 1,
          limit: 10
        }
      }
    },
    components: { vTable, Nodata },
    methods: {
      // 提交
      submit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            io.post(api.funded.save, {
              name: this.formList.itemName,
              semester: this.formList.itemYear,
              describ: this.formList.itemContent,
              type: this.proType
            }, (res) => {
              this.$Message.success('新增项目成功！')
              this.getList()
            })
          } else {
            this.$Message.error('表单验证失败！')
          }
        })
      },
      // 获取当前资助列表
      getList () {
        let _data = _.merge({}, this.page, { type: this.proType })

        io.get(api.funded.list, _data, (res) => {
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
        this.$router.push({
          path: '/fund/detail/' + id
        })
      },
      // 删除
      del (id) {
        let ids = [id]
        io.post(api.funded.delete, ids, (res) => {
          this.$Message.success('删除成功！')
          this.getList()
        })
      },
      // 获取年份列表
      getSemester () {
        io.get(api.dict.semester, (res) => {
          let data = res.data
          this.semesterData = data.dict['22']
        })
      }
    },
    mounted () {
      this.getSemester()
      this.getList()
    }
  }
</script>

<style lang="scss" scoped>
  @import '../../../../../module/components/main';
  .project {
    .page {
      margin-top: 10px;
    }
    .do-some {
      margin-bottom: 10px;
    }
  }
</style>
