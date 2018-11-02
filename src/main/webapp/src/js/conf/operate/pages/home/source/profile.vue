<template>
  <div class="profile">
    <Row :gutter="10">
      <Col span="6">
        <Card :padding="0">
          <div class="desc">
            <h3>总体情况</h3>
            <div class="bd clearfix">
              <div class="fl f-fl">
                <p>在校总人数</p>
                <p>困难人数</p>
                <p>资助人数</p>
              </div>
              <div class="fr f-fr">
                <p><span>{{people.total}}</span> 人</p>
                <p><span>{{people.poor}}</span> 人</p>
                <p><span>{{people.funded}}</span> 人</p>
              </div>
            </div>
          </div>
        </Card>
      </Col>
      <Col span="9">
        <Card :padding="10" :bordered="false" :dis-hover="true">
          <div class="circle">
            <div>
              <p>困难人数<br/>{{people.poor}}</p>
            </div>
            <canvas :id="'circle-1-' + type"></canvas>
          </div>
        </Card>
      </Col>
      <Col span="9">
        <Card :padding="10" :bordered="false" :dis-hover="true">
          <div class="circle">
            <div>
              <p>资助人数<br/>{{people.funded}}</p>
            </div>
            <canvas :id="'circle-2-' + type"></canvas>
          </div>
        </Card>
      </Col>
    </Row>
  </div>
</template>

<script>
import { drawCircle } from 'js/lib/util/drawCircle'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'

export default {
  name: 'profile',
  props: ['type'],
  data () {
    return {
      people: {
        total: 0,
        poor: 0,
        funded: 0
      }
    }
  },
  components: {},
  methods: {
    draw () {
      drawCircle('circle-1-' + this.type, (this.people.poor / this.people.total).toFixed(1), '#0098E1', '#cccccc', 10, 0.8);
      drawCircle('circle-2-' + this.type, (this.people.funded / this.people.total).toFixed(1), '#0098E1', '#cccccc', 10, 0.8);
    },
    // 获取概况数据
    getView () {
      io.get(api.report.view, (res) => {
        let data = res.data
        this.people.total = data.viewData['sch-total-student']
        this.people.funded = data.viewData['sch-funded-student']
        this.people.poor = data.viewData['sch-poor-student']

        this.draw()
      })
    }
  },
  mounted () {
    this.getView()
  }
}
</script>

<style lang="scss" scoped>
@import '../../../../../module/components/main';

.profile {
  .desc {
    h3 {
      background-color: $color-primary-blue-hover;
      color: #fff;
      text-align: center;
      font-weight: normal;
      height: 30px;
      line-height: 30px;
    }
    .bd {
      .fl p {
        height: 50px;
        line-height: 50px;
        padding-left: 10px;
        padding-right: 10px;
      }
      .fr p {
        text-align: right;
        height: 50px;
        line-height: 50px;
        padding-left: 10px;
        padding-right: 10px;
        span {
          color: $color-error;
        }
      }
    }
  }
  .circle {
    canvas {
      width: 300px;
      margin: 0 auto;
      display: block;
    }
    div {
      position: relative;
      width: 300px;
      margin: 0 auto;
      p {
        position: absolute;
        text-align: center;
        width: 100%;
        top: 60px;
      }
    }
  }
}
</style>
