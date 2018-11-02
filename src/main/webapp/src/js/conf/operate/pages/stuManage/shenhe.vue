<template>
  <div class="wrapper">
    <Location>学生管理 > 困难学生认定</Location>
    <div class="con">
      <Card :padding="10">
        <div class="form-con">
          <Row :gutter="10">
            <Col span="3"><p class="form-label">班级评议小组认定：</p></Col>
            <Col span="6">
              <Button type="ghost" @click="getUrl()">查看链接</Button>
            </Col>
          </Row>
        </div>

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
            <Col span="3"><p class="form-label">年级：</p></Col>
            <Col span="6">
              <Select v-model="formList.grade" placeholder="请选择">
                <Option v-for="item in gradeClazz.grade" :key="item" :value="item">{{item}}</Option>
              </Select>
            </Col>
            <Col span="3"><p class="form-label">班级：</p></Col>
            <Col span="6">
              <Select v-model="formList.clazz" placeholder="请选择">
                <Option v-for="item in gradeClazz.clazz" :key="item" :value="item">{{item}}</Option>
              </Select>
            </Col>
          </Row>
        </div>

        <div class="form-con">
          <Row :gutter="10">
            <Col span="3"><p class="form-label">是否已认定：</p></Col>
            <Col span="6">
              <Select v-model="formList.headTeacheCheck" placeholder="请选择">
                <Option value="">全部</Option>
                <Option value="1">是</Option>
                <Option value="0">否</Option>
              </Select>
            </Col>
            <Col span="3"><p class="form-label">身份证号：</p></Col>
            <Col span="6">
              <Input v-model="formList.idcard" placeholder="请输入"></Input>
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

        <v-table v-if="listData.length" :items="listData" :header="[]">
          <template slot="t-header">
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="name"><span>姓名</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="gender"><span>性别</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="age"><span>年龄</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="stuno"><span>学号</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="grade"><span>年级</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="clazz"><span>班级</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="diffLevel"><span>困难等级</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="score"><span>智能得分</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="headTeacheCheck"><span>是否已认定</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.name}}</td>
              <td class="center">{{props.data.gender == 1 ? '男' : '女'}}</td>
              <td class="center">{{props.data.age}}</td>
              <td class="center">{{props.data.stuno}}</td>
              <td class="center">{{props.data.grade}}</td>
              <td class="center">{{props.data.clazz}}</td>
              <td class="center">{{props.data.diffLevelStr}}</td>
              <td class="center">{{props.data.score}}</td>
              <td class="center">{{props.data.headTeacheCheck == 1 ? '是' : '否'}}</td>
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
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import vTable from 'js/module/components/table/table'
import $ from 'jquery'
import _ from 'lodash'
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'zizhu',
  data () {
    return {
      formList: {
        name: '',
        idcard: '',
        grade: '',
        clazz: '',
        headTeacheCheck: '',
        gender: ''
      },
      dataItems: [],
      headData: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      gradeClazz: {}
    }
  },
  components: { vTable, Location, Nodata },
  methods: {
    // 获取列表
    getList () {
      let searchData = _.merge({}, this.page, {
        name: this.formList.name,
        gender: this.formList.gender,
        grade: this.formList.grade,
        clazz: this.formList.clazz,
        headTeacheCheck: this.formList.headTeacheCheck,
        idcard: this.formList.idcard
      })

      io.get(api.student.list, searchData, (res) => {
        let data = res.data
        this.headData = data.header
        this.listData = data.page.list
        this.total = data.page.totalCount
      })
    },
    // 分页操作
    pageChange (page) {
      this.page.page = page
      this.getList()
    },
    // 查看链接
    getUrl () {
      this.$router.push({ name: 'stuManage/shenheUrl' })
    },
    // 获取班级
    getClazz () {
      io.get(api.school.querySchGradeClazzList, (res) => {
        let data = res.data
        this.gradeClazz = data.page
      })
    }
  },
  mounted () {
    this.getClazz()
    this.getList()
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
</style>
