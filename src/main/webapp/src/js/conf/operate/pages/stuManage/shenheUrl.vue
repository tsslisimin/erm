<template>
  <div class="wrapper">
    <Location>班级评议小组认定链接</Location>
    <div class="con">
      <Card :padding="10">
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

        <div class="form-con" style="padding-bottom: 20px;border-bottom: 1px dashed #d5d5d5;margin-bottom: 10px;">
          <Row :gutter="10">
            <Col span="3">&nbsp;</Col>
            <Col span="6">
              <Button type="primary" @click="getList()">查 询</Button>
            </Col>
          </Row>
        </div>

        <div v-if="listData.length">
          <div class="url-line clearfix" v-for="item in listData" :key="item">
            <div class="f-fl" style="width: 250px;">年级： {{item.grade}} ，班级：{{item.clazz}} ：</div>
            <div class="f-fl"> <a :href="baseUrl+'/confirm/index.html?grade=' + item.grade + '&clazz=' + item.clazz" target="_blank">{{baseUrl+'/confirm/index.html?grade=' + item.grade + '&clazz=' + item.clazz}}</a></div>
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
import { api,baseUrl } from 'js/module/api/url'
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'shenheurl',
  data () {
    return {
      formList: {
        grade: '',
        clazz: ''
      },
      listData: [],
      gradeClazz: {},
      baseUrl:baseUrl
    }

  },
  components: { Location, Nodata },
  methods: {
    // 获取列表
    getList () {
      io.get(api.school.listClazz, {
        grade: this.formList.grade,
        clazz: this.formList.clazz
      }, (res) => {
        let data = res.data
        this.listData = data.page
      })
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
    }
    .url-line {
      border-bottom: 1px dashed #eee;
      height: 30px;
      line-height: 30px;
    }
  }
}
</style>
