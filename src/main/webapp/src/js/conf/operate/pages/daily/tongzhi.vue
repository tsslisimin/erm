<template>
  <div class="wrapper">
    <Location>日常办公 > 通知通告</Location>
    <div class="con">
      <Card :padding="10">
        <Tabs :animated="false">
          <TabPane label="我的通知">
            <NoticeList></NoticeList>
          </TabPane>
          <TabPane label="发布通知">
            <Notice></Notice>
          </TabPane>
        </Tabs>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import Notice from './source/notice'
import NoticeList from './source/noticeList'

export default {
  name: 'dailyTongzhi',
  data () {
    return {
    }
  },
  components: { Location, Notice, NoticeList },
  methods: {
  },
  mounted () {
  }
};
</script>

<style lang="scss" scoped>
@import "../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
}
</style>
