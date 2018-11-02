<template>
  <div class="wrapper">
    <Location>资助管理 > 国家资助</Location>
    <div class="con">
      <Card :padding="10">
        <Form ref="formList" :model="formList" :rules="ruleList" :label-width="80" style="padding-top: 15px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
          <FormItem label="项目名称" style="margin-bottom: 20px;">
            <p v-if="fundList.length <= 1">{{fundInfo.dictName}}</p>
            <Select v-if="fundList.length > 1" v-model="curFund" placeholder="请选择" style="width: 300px;">
              <Option v-for="(item, $index) in fundList" :key="$index" :value="item.dictCode">{{item.dictName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="选择年份" prop="itemYear" style="margin-bottom: 20px;">
            <p >{{formList.term}}</p>
            <!--<Select v-model="formList.itemYear" placeholder="请选择" style="width: 300px;">-->
              <!--<Option v-for="(item, $index) in semesterData" :key="$index" :value="item.dictCode + '-' + item.dictName">{{item.dictName}}</Option>-->
            <!--</Select>-->
          </FormItem>
          <!---->
          <FormItem>
            <Button type="primary" :disabled="fundInfo.status>3" @click="submit('formList')">启 动</Button>
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
            <th class="center"><p sort-key="semester"><span>年份</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="status"><span>状态</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 200px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="left">{{props.data.name}}</td>
              <td class="center">{{props.data.semester}}</td>
              <td class="center">{{props.data.statusName}}</td>
              <td class="center"><Button type="primary" @click="edit(props.data.id)">详情</Button></td>
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
    </div>
  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import Location from 'js/module/components/location/location'
import vTable from 'js/module/components/table/table'
import Nodata from 'js/module/components/nodata/nodata'
import _ from 'lodash'

export default {
  name: 'fundGuojia',
  data () {
    return {
      guoFundId: '',
      fundList: [],
      curFund: '',
      fundInfo: {
        dictCode: '',
        dictName: '',
        id: '',
        type: ''
      },
      semesterData: [],
      formList: {
        itemYear: '',
        term:''
      },
      ruleList: {
        // itemYear: [{ required: true, message: '请选择年份', trigger: 'change' }]
      },
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      }
    }
  },
  components: { Location, vTable, Nodata },
  methods: {
    // 启动
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.$Spin.show()
          io.post(api.fundProcess.countryStart, {
            semester: this.formList.itemYear.split('-')[0],
            semesterName: this.formList.term,
            fundDictCode: this.fundInfo.dictCode,
            name: this.fundInfo.dictName,
            type: 1,
            subType: this.guoFundId
          }, (res) => {
            this.$Spin.hide()
            this.$Message.success('项目启动成功！')
            this.getList()
          }, (e) => {
            this.$Spin.hide()
          }, (e) => {
            this.$Spin.hide()
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取项目信息
    getFundInfo (id) {
      io.get(api.dict.getFundDictDataByType, {
        type: id ,
        term:this.formList.term
      }, (res) => {
        let data = res.data
        this.fundList = data.dict

        this.curFund = this.fundList[0].dictCode

        this.fundInfo = _.find(this.fundList, (o) => o.dictCode === this.curFund)

        this.getList()
        this.getSemester()
      })
    },
    // 获取年份列表
    getSemester () {
      io.get(api.dict.semester, (res) => {
        let data = res.data
        this.semesterData = data.dict['22']
      })
    },
    // 获取当前资助列表
    getList () {
      let _data = _.merge({}, this.page, {
        type: 1,
        subtype: this.guoFundId
      })

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
        path: '/fund/guojia/detail/' + id
      })
    }
  },
  watch: {
    '$route' (to) {
      this.guoFundId = to.params.guoFundId
      this.fundList = []
      this.getFundInfo(this.guoFundId)
    },
    curFund: function (val) {
      this.fundInfo = _.find(this.fundList, (o) => o.dictCode == val)
    }
  },
  mounted () {
    this.guoFundId = this.$route.params.guoFundId

    let date=new Date();
    let max=new Date(date.getFullYear(),8,1);
    let min=new Date(date.getFullYear(),2,1)
    if (date<max&&date>min) {
     this.formList.term=date.getFullYear()+"年春季"
    }
    else if (date<min) {
      this.formList.term=(date.getFullYear()-1)+"年秋季"
    }
    else {
      this.formList.term=(date.getFullYear())+"年秋季"
    }
    this.getFundInfo(this.guoFundId)
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
  }
}
</style>
