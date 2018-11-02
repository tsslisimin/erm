<template>
  <div class="wrapper">
    <Location>资助概况</Location>
    <div class="con">
      <Card :padding="10">
        <Tabs>
          <TabPane v-for="(item, $index) in zoneData" :key="$index" :label="item.dictName">
            <Profile :type="item.dictCode" :count="count[item.dictCode]"></Profile>
          </TabPane>
        </Tabs>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import Profile from './source/profile'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'

export default {
  name: 'profile',
  data () {
    return {
      count: {},
      zoneData: []
    }
  },
  components: { Location, Profile },
  methods: {
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
    this.getZone()
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
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
  }
}
</style>
