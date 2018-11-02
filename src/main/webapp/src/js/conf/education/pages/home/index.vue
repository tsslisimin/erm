<template>
  <div class="wrapper">
    <Location>首页</Location>
    <div class="banner">
      <p><img src="./banner.jpg" alt="banner"></p>
    </div>
    <div class="con-1">
      <Row :gutter="10">
        <Col span="12">
          <Card :padding="15">
            <p slot="title" class="title"><i class="iconfont">&#xe602;</i> <span>待办事项</span></p>
            <ul class="list">
              <li v-for="(item, $index) in todoList" :key="$index"><span>•</span><a href="javascript:;" @click="goUrl(item.type)">{{item.title}}</a></li>
            </ul>
          </Card>
        </Col>
        <Col span="12">
          <Card :padding="15">
            <p slot="title" class="title"><i class="iconfont">&#xe691;</i> <span>通知公告</span></p>
            <ul class="list">
              <li v-for="(item, $index) in noticeList" :key="$index"><span>•</span><a href="javascript:;" @click="goNotice()">{{item.title}}</a></li>
            </ul>
          </Card>
        </Col>
      </Row>
    </div>
    <div class="con-2">
      <Row>
        <Col span="24">
          <Card :padding="15">
            <p slot="title" class="title"><i class="iconfont">&#xe61c;</i> <span>资助概况</span></p>
            <div class="bd">
              <Tabs>
                <TabPane v-for="(item, $index) in zoneData" :key="$index" :label="item.dictName">
                  <Profile :type="item.dictCode" :count="count[item.dictCode]"></Profile>
                </TabPane>
              </Tabs>
            </div>
          </Card>
        </Col>
      </Row>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import Profile from './source/profile'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'

export default {
  name: 'home',
  data () {
    return {
      todoList: [],
      noticeList: [],
      count: {},
      zoneData: []
    }
  },
  components: { Profile, Location },
  methods: {
    // 获取待办列表
    getTodo () {
      io.get(api.notice.myNotice, {
        todo: true
      }, (res) => {
        let data = res.data
        this.todoList = data.page.list.slice(0, 3)
      })
    },
    // 获取通知列表
    getNotice () {
      io.get(api.notice.list, (res) => {
        let data = res.data
        this.noticeList = data.page.list.slice(0, 3)
      })
    },
    // 添加监听
    goUrl (type) {
      let _url = {
        7: window.location.pathname + '#/fund/shenhe'
      }
      window.location.href = _url[type]
      window.location.reload()
    },
    // 前往通知列表
    goNotice () {
      window.location.href = window.location.pathname + '#/daily/tongzhi'
      window.location.reload()
    },
    // 获取概况数据
    getView () {
      io.get(api.report.view, (res) => {
        let data = res.data
        this.count = data.viewData
      })
    },
    // 获取学区
    getZone () {
      io.get(api.dict.schTypeDict, (res) => {
        let data = res.data
        this.zoneData = data.dict['21']
        this.getView()
      })
    }
  },
  mounted () {
    this.getTodo()
    this.getNotice()
    this.getZone()
  }
}
</script>

<style lang="scss" scoped>
  @import '../../../../module/components/main';

  .wrapper {
    margin: 0 10px 20px 10px;
    .banner {
      img {
        margin: 0;
        padding: 0;
        width: 100%;
      }
    }
    .con-1 {
      margin-top: 10px;
      .title {
        font-size: 15px;
        font-weight: bold;
        color: $color-primary-blue;
      }
      .list {
        min-height: 80px;
        font-size: 14px;
        span {
          margin-right: 10px;
          font-weight: bold;
          font-size: 18px;
        }
        a {
          b {
            color: $color-error;
            padding-left: 3px;
            padding-right: 3px;
          }
        }
      }
    }
    .con-2 {
      margin-top: 10px;
      .title {
        font-size: 15px;
        font-weight: bold;
        color: $color-primary-blue;
      }
    }
  }
</style>
