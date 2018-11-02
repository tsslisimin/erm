<template>
  <div class="wrapper">
    <Location>学生管理 > 申请表管理</Location>
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
            <Col span="3"><p class="form-label">有无申请表：</p></Col>
            <Col span="6">
              <Select v-model="formList.hasApplyInfo" placeholder="请选择">
                <Option value="1">有</Option>
                <Option value="0">无</Option>
              </Select>
            </Col>
          </Row>
        </div>
        <div class="form-con" style="padding-bottom: 20px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
          <Row :gutter="10">
            <Col span="3">&nbsp;</Col>
            <Col span="15">
              <Button type="primary" @click="getList()">查 询</Button>
              <span style="margin-left: 15px;" v-if="formUrl">学生申请表页面：<a :href="formUrl" target="_blank">{{formUrl}}</a></span>
            </Col>
          </Row>
        </div>

        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="[]">
          <template slot="t-header">
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="name"><span>姓名</span><span class="sort"><i
              class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="gender"><span>性别</span><span class="sort"><i
              class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="stuno"><span>学号</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i
              class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="age"><span>年龄</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i
              class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="idcard"><span>身份证号</span><span class="sort"><i
              class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="hasApplyInfo"><span>有无申请表</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i
              class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.name}}</td>
              <td class="center">{{props.data.gender == 1 ? '男' : '女'}}</td>
              <td class="center">{{props.data.stuno}}</td>
              <td class="center">{{props.data.age}}</td>
              <td class="center">{{props.data.idcard}}</td>
              <td class="center">{{props.data.hasApplyInfo}}</td>
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
  import {api} from 'js/module/api/url'
  import vTable from 'js/module/components/table/table'
  import $ from 'jquery'
  import _ from 'lodash'
  import Nodata from 'js/module/components/nodata/nodata'

  export default {
    name: 'stuManageXinxi',
    data() {
      return {
        formList: {
          name: '',
          gender: '',
          stuno: '',
          minAge: '',
          maxAge: '',
          idcard: '',
          hasApplyInfo: ''
        },
        listData: [],
        total: 0,
        page: {
          page: 1,
          limit: 10
        },
        formUrl: 'http://'+window.location.hostname+':'+window.location.port+ '/apply/index.html'
      }
    },
    components: {vTable, Location, Nodata},
    methods: {
      // 获取列表
      getList() {
        let searchData = _.merge({}, this.page, {
          name: this.formList.name,
          gender: this.formList.gender,
          stuno: this.formList.stuno,
          minAge: this.formList.minAge,
          maxAge: this.formList.maxAge,
          idcard: this.formList.idcard,
          hasApplyInfo: this.formList.hasApplyInfo
        })

        io.get(api.student.stuApplyList, searchData, (res) => {
          let data = res.data
          this.listData = data.page.list
          this.total = data.page.totalCount
        })
      },
      // 分页操作
      pageChange(page) {
        this.page.page = page
        this.getList()
      },
      // 获取学校信息
      getSchool() {
        io.get(api.school.info, (res) => {
          let data = res.data.ermSchool

          this.formUrl = 'http://'+window.location.hostname+':'+window.location.port+'/apply/index.html?schId=' + data.id
        })
      }
    },
    mounted() {
      this.getSchool()
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
