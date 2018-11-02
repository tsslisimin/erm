<template>
  <div class="wrapper">
    <Location>困难信息详情</Location>
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
        </div>
        <div class="info-img-con">
          <h3>相关图片</h3>
          <Row :gutter="10">
            <Col span="12">
              <p class="info-img-text"><span>申请表照片</span></p>
              <p class="info-img"><a :href="'/erm/api/ermFile/img/' + imgInfo[0].value" data-lightbox="images"><img :src="'/erm/api/ermFile/img/' + imgInfo[0].value" onerror="this.src = 'http://iph.href.lu/550x300?text=暂无图片&fg=cccccc&bg=ffffff'"></a></p>
            </CoL>
            <Col span="12">
              <p class="info-img-text"><span>公示照片</span></p>
              <p class="info-img"><a :href="'/erm/api/ermFile/img/' + imgInfo[1].value" data-lightbox="images"><img :src="'/erm/api/ermFile/img/' + imgInfo[1].value" onerror="this.src = 'http://iph.href.lu/550x300?text=暂无图片&fg=cccccc&bg=ffffff'"></a></p>
            </CoL>
          </Row>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import $ from 'jquery'
import lightbox from 'lightbox'

export default {
  name: 'student',
  data () {
    return {
      stuId: '',
      fundId: '',
      studentInfo: {},
      poorList: [],
      imgInfo: []
    }
  },
  components: { Location },
  methods: {
    // 获取基础信息数据
    getInfo () {
      io.get(api.student.info + '/' + this.stuId, (res) => {
        let data = res.data
        this.studentInfo = data.ermStudent
      })
    },
    // 获取照片
    getImg () {
      io.get(api.report.stuImg, {
        studentId: this.stuId,
        schFundedId: this.fundId
      }, (res) => {
        let data = res.data
        this.imgInfo = data.info
      })
    },
    // 获取贫困信息
    getPoorInfo () {
      io.get(api.report.studentIndex, {
        studentId: this.stuId,
        schFundedId: this.fundId
      }, (res) => {
        let data = res.data
        this.poorList = data.info
      })
    }
  },
  mounted () {
    this.stuId = this.$route.params.stuId || ''
    this.fundId = this.$route.params.fundId || ''
    this.getInfo()
    this.getPoorInfo()
    this.getImg()
    lightbox.option({
      'resizeDuration': 200,
      'wrapAround': true,
      'showImageNumberLabel': false,
      'fadeDuration': 300,
      'imageFadeDuration': 300
    })
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
    .info-con {
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
