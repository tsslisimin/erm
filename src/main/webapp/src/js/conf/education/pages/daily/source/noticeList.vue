<template>
  <div class="notice">
    <div class="notice-list" v-for="(item, $index) in listData" :key="$index">
      <div class="title clearfix" @click="showContent($index)">
        <h3 class="f-fl"><b>●</b><span>{{item.title}}<i v-if="item.url != null" class="iconfont" @click="downFile(item.url)">&#xe689;</i></span></h3>
        <p class="f-fr">{{item.createTime}}</p>
      </div>
      <div class="content">{{item.describ}}</div>
    </div>
    <div class="page clearfix" v-if="total > 10">
      <div style="text-align: center">
        <Page :total="total" show-total></Page>
      </div>
    </div>

    <Nodata v-if="!listData.length"></Nodata>
  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import $ from 'jquery'
import Nodata from 'js/module/components/nodata/nodata'

export default {
  name: 'noticeList',
  data () {
    return {
      listData: [],
      total: 0
    }
  },
  components: { Nodata },
  methods: {
    // 获取列表
    getList () {
      io.get(api.notice.list, (res) => {
        let data = res.data
        this.listData = data.page.list
        this.total = data.page.totalCount
      })
    },
    // 添加监听
    showContent (idx) {
      if ($('.notice-list .content').eq(idx).is(':hidden')) {
        $('.notice-list .content').slideUp()
        $('.notice-list .content').eq(idx).slideDown()
      }
    },
    // input 工具方法
    generatorInput (obj) {
      let result = ''
      for (var key in obj) {
        result += '<input type="hidden" name = "' + key + '" value="' + obj[key] + '">'
      }
      return result
    },
    // 下载附件
    downFile (url) {
      io.post(api.file.download, {
        fileName: url
      }, (res) => {
        let blob = res
        let reader = new FileReader()
        reader.readAsDataURL(blob)
        reader.onload = (e) => {
          let a = document.createElement('a')
          a.download = url
          a.href = e.target.result
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }, (e) => {}, (e) => {}, { responseType: 'blob' })

      // $(`<form action="http://erm.comezx.com${api.file.download}" method="get">${this.generatorInput({ fileName: url })}</form>`).appendTo('body').submit().remove()
    }
  },
  mounted () {
    this.getList()
  }
}
</script>

<style lang="scss" scoped>
  @import '../../../../../module/components/main';

  .notice {
    margin-bottom: 10px;
    .notice-list {
      border-bottom: 1px dashed $border-default-color;
      .title{
        height: 40px;
        line-height: 40px;
        h3 {
          font-size: 14px;
          font-weight: normal;
          margin-left: 15px;
          b {
            color: $color-primary-blue-hover;
            margin-right: 5px;
          }
          i {
            font-size: 14px;
            cursor: pointer;
            margin-left: 5px;
          }
        }
        p {
          margin-right: 15px;
        }
        &:hover h3{
          color: $color-primary-blue-hover;
        }
      }
      .content {
        padding: 15px;
        background-color: #F5F5F5;
        display: none;
      }
    }
    .page {
      margin-top: 10px;
    }
  }
</style>
