<template>
  <div class="wrapper">
    <Location>学生信息详情</Location>
    <div class="con">
      <Card :padding="10">
        <div class="info-con">
          <Row>
            <Col span="6">
              <p class="info-photo"><img :src="'/erm/api/ermFile/img/' + studentInfo.photoUrl" onerror="this.src = 'http://iph.href.lu/150x180?text=暂无图片&fg=cccccc&bg=ffffff'"></p>
            </Col>
            <Col span="6">
              <h3>学生基本信息</h3>
              <p class="info-text"><span>姓名：</span><span>{{studentInfo.name}}</span></p>
              <p class="info-text"><span>年级：</span><span>{{studentInfo.grade}}</span></p>
              <p class="info-text"><span>民族：</span><span>{{studentInfo.nature}}</span></p>
              <p class="info-text"><span>监护人姓名：</span><span>{{studentInfo.parentName}}</span></p>
            </CoL>
            <Col span="6">
              <h4>&nbsp;</h4>
              <p class="info-text"><span>学号：</span><span>{{studentInfo.stuno}}</span></p>
              <p class="info-text"><span>班级：</span><span>{{studentInfo.clazz}}</span></p>
              <p class="info-text"><span>政治面貌：</span><span>{{studentInfo.politicalStatus}}</span></p>
              <p class="info-text"><span>监护人电话：</span><span>{{studentInfo.telphone}}</span></p>
            </CoL>
            <Col span="6">
              <h4>&nbsp;</h4>
              <p class="info-text"><span>身份证号：</span><span>{{studentInfo.idcard}}</span></p>
              <p class="info-text"><span>学期：</span><span>{{studentInfo.semester}}</span></p>
              <p class="info-text"><span>毕业学校：</span><span>{{studentInfo.schoolName}}</span></p>
              <p class="info-text"><span>家庭住址：</span><span>{{studentInfo.address}}</span></p>
            </CoL>
          </Row>
        </div>

        <div class="fund-con">
          <h3>贫困信息</h3>
          <p v-for="(item, $index) in poorList"><i>{{$index + 1}}.</i> <span>{{item.key}}：</span><span>{{item.value}}</span></p>
          <p>贫困类型：<span>{{studentInfo.isPoor}}</span></p>
        </div>

        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="headData">
          <template slot="d-header" slot-scope="props">
            <th style="width: 150px;" :class="{left:props.idx == 0,center:props.idx > 0}"><p :sort-key="props.data.colCode"><span>{{props.data.colName}}</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td :class="{left:$index == 0,center:$index > 0}" v-for="(el, $index) in headData" :key="$index"><span>{{props.data[el.colCode]}}</span></td>
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
import Nodata from 'js/module/components/nodata/nodata'
import $ from 'jquery'

export default {
  name: 'student',
  data () {
    return {
      stuId: '',
      studentInfo: {},
      headData: [],
      listData: [],
      poorList: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      }
    }
  },
  components: { Location, vTable, Nodata },
  methods: {
    // 获取基础信息数据
    getInfo () {
      io.get(api.student.info + '/' + this.stuId, (res) => {
        let data = res.data
        this.studentInfo = data.ermStudent
        this.poorList = data.fundedInfo
      })
    },
    // 获取列表信息
    getList () {
      io.get(api.fundedinfo.studentFund, {
        studentId: this.stuId
      }, (res) => {
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
    }
  },
  mounted () {
    this.stuId = this.$route.params.stuId || ''
    this.getInfo()
    this.getList()
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
    .info-con {
      margin-bottom: 10px;
      border-bottom: 1px dashed #d5d5d5;
      .info-photo {
        padding-left: 30px;
        img {
          width: 150px;
          height: 180px;
          border: 1px solid #d5d5d5;
        }
      }
      h3 {
        margin-top:5px;
        margin-bottom: 15px;
        color: $color-primary-blue-active;
      }
      h4 {
        margin-top:5px;
        margin-bottom: 15px;
      }
      .info-text {
        height: 35px;
        line-height: 35px;
      }
    }
    .info-img-con {
      border-bottom: 1px dashed #d5d5d5;
      margin-bottom: 10px;
      h3 {
        margin-top:10px;
        margin-bottom: 10px;
        color: $color-primary-blue-active;
      }
      .info-img img {
        width: 100%;
        height: 300px;
        border: 1px solid #d5d5d5;
      }
    }
    .fund-con {
      border-bottom: 1px dashed #d5d5d5;
      margin-bottom: 10px;
      h3 {
        margin-top:10px;
        margin-bottom: 10px;
        color: $color-primary-blue-active;
      }
      p {
        margin-top: 10px;
        margin-bottom: 10px;
      }
    }
  }
}
</style>
