<template>
  <div class="profile">
    <Card :padding="10">
      <div class="count-num">
        <Row :gutter="30">
          <Col span="8">
            <div class="count-con">
              <h3>{{people.total || 0}}</h3>
              <p>在校总人数</p>
            </div>
          </Col>
          <Col span="8">
            <div class="count-con">
              <h3>{{people.poor || 0}}</h3>
              <p>困难人数</p>
            </div>
          </Col>
          <Col span="8">
            <div class="count-con">
              <h3>{{people.funded || 0}}</h3>
              <p>资助人数</p>
            </div>
          </Col>
        </Row>
      </div>
      <Row :gutter="10">
        <Col span="12">
          <Card :padding="10" :bordered="false" :dis-hover="true">
            <div class="circle">
              <div>
                <p>困难人数<br/>{{people.poor || 0}}</p>
              </div>
              <canvas :id="'circle-1-' + type"></canvas>
            </div>
          </Card>
        </Col>
        <Col span="12">
          <Card :padding="10" :bordered="false" :dis-hover="true">
            <div class="circle">
              <div>
                <p>资助人数<br/>{{people.funded || 0}}</p>
              </div>
              <canvas :id="'circle-2-' + type"></canvas>
            </div>
          </Card>
        </Col>
      </Row>
    </Card>
  </div>
</template>

<script>
import { drawCircle } from 'js/lib/util/drawCircle'

export default {
  name: 'profile',
  props: ['type', 'count'],
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
      drawCircle('circle-1-' + this.type, (this.people.poor / this.people.total).toFixed(1), '#0098E1', '#cccccc', 10, 0.8)
      drawCircle('circle-2-' + this.type, (this.people.funded / this.people.total).toFixed(1), '#0098E1', '#cccccc', 10, 0.8)
    }
  },
  watch: {
    count (o) {
      this.people.total = o['stuCount']
      this.people.funded = o['auditCt']
      this.people.poor = o['ct']

      this.draw()
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../../module/components/main";

.profile {
  .count-num {
    margin-top: 20px;
    margin-bottom: 50px;
    .count-con {
      height: 90px;
      border: 1px solid $border-default-color;
      border-radius: 3px;
      border-left: 3px solid $color-primary-blue;
      h3 {
        margin-left: 30px;
        margin-top: 10px;
        font-size: 32px;
        font-weight: normal;
      }
      p {
        margin-left: 30px;
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
