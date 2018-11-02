<template>
  <div class="wrapper">
    <Location>日常办公 > 待办事项</Location>
    <div class="con">
      <Card :padding="10">
        <div class="notice-list" v-for="(item, $index) in listData" :key="$index">
          <div class="title clearfix" @click="goUrl(item.type)">
            <h3 class="f-fl"><b>●</b><span>{{item.title}}</span></h3>
            <p class="f-fr">{{item.createTime}}</p>
          </div>
        </div>
        <div class="page clearfix" v-if="total > 10">
          <div style="text-align: center">
            <Page :total="total" show-total></Page>
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
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'dailyDaiban',
  data () {
    return {
      listData: [],
      total: 0
    }
  },
  components: { Location, Nodata },
  methods: {
    // 获取列表
    getList () {
      io.get(api.notice.myNotice, {
        todo: true
      }, (res) => {
        let data = res.data
        this.listData = data.page.list
        this.total = data.page.totalCount
      })
    },
    // 添加监听
    goUrl (type) {
      let _url = {
        7: window.location.pathname + '#/manage/shenhe'
      }
      window.location.href = _url[type]
      window.location.reload()
    }
  },
  mounted () {
    this.getList()
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
    .notice-list {
      border-bottom: 1px dashed $border-default-color;
      .title{
        height: 40px;
        line-height: 40px;
        cursor: pointer;
        h3 {
          font-size: 14px;
          font-weight: normal;
          margin-left: 15px;
          b {
            color: $color-primary-blue-hover;
            margin-right: 5px;
          }
        }
        p {
          margin-right: 15px;
        }
        &:hover h3{
          color: $color-primary-blue-hover;
        }
      }
    }
    .page {
      margin-top: 10px;
    }
  }
}
</style>
