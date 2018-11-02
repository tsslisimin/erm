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
      <FormItem label="项目金额" prop="totalMoney" style="margin-bottom: 20px;">
        <Input v-model="formList.totalMoney" placeholder="请输入" style="width: 300px;"></Input>
      </FormItem>
      <FormItem label="项目名额" prop="count" style="margin-bottom: 20px;">
        <Input v-model="formList.count" placeholder="请输入" style="width: 300px;"></Input>
      </FormItem>

      <FormItem label="项目描述" prop="itemContent" style="margin-bottom: 20px;">
        <Input v-model="formList.itemContent" type="textarea" :autosize="{minRows: 5,maxRows: 10}" placeholder="请输入..." style="width: 300px;"></Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="submit('formList')">新 增</Button>
      </FormItem>
    </Form>
    <div class="form-con">
      <Row :gutter="10">
        <Col span="2"><p class="form-label">年份：</p></Col>
        <Col span="6">
        <DatePicker v-model="formList.year" type="year" placeholder="请选择" style="width: 100%;"></DatePicker>
        </Col>
        <Col span="2"><p class="form-label">状态：</p></Col>
        <Col span="6">
        <Select v-model="formList.status" filterable placeholder="请选择">
          <Option v-for="(item, $index) in statusData" :value="item.statusCode" :key="$index">{{item.statusName}}</Option>
        </Select>
        </Col>
      </Row>
    </div>

    <div class="form-con">
      <Row :gutter="10">
        <Col span="2"><p class="form-label">项目名称：</p></Col>
        <Col span="6">
        <Select v-model="formList.fundName" filterable placeholder="请选择">
          <Option v-for="(item, $index) in fundData" :value="item.id" :key="$index">{{item.fundedName}}</Option>
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

    <!-- <div class="fund-tips">
      <h4>操作说明：</h4>
      <p>1.首先进行自定义指标的配置， 完成配置后，可启动项目，项目启动后，进入财政厅放款流程环节；</p>
      <p>2.财政厅放款后，可填写或备案项目资助总金额(注意：金额一旦填写将不可修改)；</p>
      <p>3.财政厅放款完成后，即进入资助申报期，此时可下载[学生资助申请表]并通知班主任进行受助学生推选；</p>
      <p>4.项目完成后，可通过[上传]功能上传公示照片以备案存档。</p>
    </div> -->

    <div class="do-some clearfix">
      <div class="f-fl">
        <Button type="primary" @click="doConfig()">考核指标配置</Button>
      </div>
    </div>

    <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="[]">
      <template slot="t-header">
        <th class="left" style="width: 50px;"><p><span>序号</span></p></th>
        <th class="left" style="width: 150px;"><p sort-key="fundedName"><span>项目名称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="left" style="width: 250px;"><p><span>贫困考核指标</span></p></th>
        <th class="center"><p sort-key="year"><span>年份</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="count"><span>资助名额</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="left"><p sort-key="totalMoney"><span>项目总金额</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <th class="center"><p sort-key="status"><span>状态</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
        <!-- <th class="center" style="width: 200px;"><p><span>操作</span></p></th> -->
      </template>
      <template slot="t-body" slot-scope="props">
        <tr>
          <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
          <td class="left">{{props.data.fundedName}}</td>
          <td class="left">
            <div style="max-height: 100px;overflow: auto;">
              <p class="field" v-for="(item, $index) in props.data.schFundFields" :key="$index">
                <Poptip trigger="hover" placement="right">
                  <Button type="text">{{item.ermFieldEntity.fieldName}}({{item.ermFieldEntity.weight}})</Button>
                  <div slot="content">
              <p v-if="item.ermFieldEntity.fieldVal.length" v-for="(el, $index) in item.ermFieldEntity.fieldVal" :key="$index"><span>{{el.valVal}}：</span><span>{{el.valWeight}}</span></p>
              <p v-if="!item.ermFieldEntity.fieldVal.length"><span>{{item.ermFieldEntity.fieldName}}：</span><span>{{item.ermFieldEntity.weight}}</span></p>
            </div>
            </Poptip>
            </p>
  </div>
  </td>
  <td class="center">{{props.data.year}}</td>
  <td class="center">{{props.data.count}}</td>
  <td class="left"><input type="text" placeholder="请输入" v-if="props.data.status == 5" class="money" :class="'money-' + props.data.id" :value="props.data.totalMoney"><Poptip confirm placement="top-end" @on-ok="changeMoney(props.data.id)">
    <Button v-if="props.data.status == 5" type="info">确认</Button>
    <div slot="title">确认后就不能修改金额</div>
  </Poptip><span v-if="props.data.status != 5">{{props.data.totalMoney}}</span></td>
  <td class="center"><span class="status">{{props.data.statusName}}</span></td>
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
    props: {
      'proType': {
        type: Number
      },
      'hasNew': {
        type: Boolean,
        default: true
      }
    },
    data () {
      return {
        formList: {
          itemName: '',
          itemYear: '',
          itemContent: '',
          totalMoney:'',
          count:''
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
        },
        statusData: [],
        fundData: []
      }
    },
    components: { vTable, Nodata },
    methods: {
      // 提交
      submit (name) {
        this.$refs[name].validate(valid => {
            console.log(valid)
          if (!valid) {
            this.$Message.error('表单验证失败！')
            return

          }
        })

        io.post(api.funded.saveShcFund, {
              name: this.formList.itemName,
              semester: this.formList.itemYear,
              describ: this.formList.itemContent,
              count:this.formList.count,
              totalMoney:this.formList.totalMoney,
              type: this.proType
        }, (res) => {
          this.$Message.success('新增项目成功！')
          this.getList()
        })
      },
      // 获取当前资助列表
      getList () {
        let searchData = _.merge({}, this.page, {
          type: this.proType,
          year: this.formList.year ? new Date(this.formList.year).getFullYear() : '',
          id: this.formList.fundName,
          status: this.formList.status
        })

        io.get(api.schoolFunded.fundList, searchData, (res) => {
          let data = res.data
          this.listData = data.page.list
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

      // 获取状态
      getStatus () {
        io.get(api.schoolFunded.fundStatus, (res) => {
          let data = res.data
          this.statusData = data.fundStatus
        })
      },
      // 获取项目名称
      getFund () {
        io.get(api.schoolFunded.listSimple, {
          type: this.proType
        }, (res) => {
          let data = res.data
          this.fundData = data.page.list
        })
      },
      // 获取年份列表
      getSemester () {
        io.get(api.dict.semester, (res) => {
          let data = res.data
          this.semesterData = data.dict['22']
        })
      },
      // 更改金额
      changeMoney (id) {
        io.post(api.fundProcess.money, {
          id: id,
          totalMoney: $('.money-' + id).val()
        }, (res) => {
          this.$Message.success(res.data.msg)
          this.getList()
        })
      },
      // 考核指标配置
      doConfig () {
        this.$router.push({ name: 'fund/zhibiao' })
        window.location.reload()
      }
    },
    mounted () {
      this.getSemester()
      this.getList()
      this.getStatus()
      this.getFund()
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
</style>
