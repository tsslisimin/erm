<template>
  <div class="wrapper">
    <Location>日常办公 > 操作错误日志</Location>
    <div class="con">
      <Card :padding="10">
        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="[]">
          <template slot="t-header">
            <!--<th class="left"><p><span></span></p></th>-->
            <th class="left"><p><span>序号</span></p></th>
            <th class="center"><p sort-key="name"><span>错误简称</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="idcard"><span>错误详细</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center"><p sort-key="hasAvatar"><span>时间</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
            <th class="center" style="width: 200px;"><p><span>操作</span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <!--<td class="left"><checkbox :checked="props.data.checked" @click='checkedOne(props.idx)'></checkbox></td>-->
              <td class="left">{{props.idx + 1 + 10 * (page.page - 1)}}</td>
              <td class="center">{{props.data.title}}</td>
              <td class="center">{{props.data.msg}}</td>
              <td class="center">{{props.data.createTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</td>
              <td class="center">&nbsp;&nbsp;<Button type="error" @click="del(props.data.id)">删除</Button></td>
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
  name: 'operateLog',
  data () {
    return {
      formList: {
        name: '',
        gender: '',
        stuno: '',
        minAge: '',
        maxAge: '',
        idcard: '',
        hasAvatar: ''
      },
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      }
    }
  },
  components: { vTable, Location, Nodata },
  filters:{
    parseTime(time, cFormat) {
      if (arguments.length === 0) {
        return null
      }

      if ((time + '').length === 10) {
        time = +time * 1000
      }

      const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
      let date
      if (typeof time === 'object') {
        date = time
      } else {
        date = new Date(parseInt(time))
      }
      const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
      }
      const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
        let value = formatObj[key]
        if (key === 'a') return ['一', '二', '三', '四', '五', '六', '日'][value - 1]
        if (result.length > 0 && value < 10) {
          value = '0' + value
        }
        return value || 0
      })
      return time_str
    }
  },
  methods: {
    // 获取列表
    getList () {
      let searchData = _.merge({}, this.page, {

      })

      io.get(api.logs.list, searchData, (res) => {
        let data = res.data
        this.listData = data.page.list
        this.listData.forEach(item=>{
          item.checked=false;
        })
        this.total = data.page.totalCount
      })
    },
    // 分页操作
    pageChange (page) {
      this.page.page = page
      this.getList()
    },
    del (id) {
      let ids = [id]
      io.post(api.logs.delete, ids, (res) => {
        this.$Message.success('删除成功！')
        this.getList()
      })
    },
    checkedOne(index){
      this.listData[index].checked=!this.listData[index].checked;
    },
    deleteAll(){
      let ids=[];
      this.listData.forEach(item=>{
        if (item.checked==true){
          ids.push(item.id);
        }
      })
      io.post(api.student.delete, ids, (res) => {
        this.$Message.success('删除成功！')
        this.getList()
      })
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
