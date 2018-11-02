<template>
  <div class="wrapper">
    <Location>统计报表</Location>
    <div class="con">
      <Card :padding="10">
        <Tabs :animated="false">
          <TabPane label="全部学生">
            <Student type="base"></Student>
          </TabPane>
          <TabPane label="困难学生">
            <Student type="diff"></Student>
          </TabPane>
          <TabPane label="受助学生">
            <Student type="funding"></Student>
          </TabPane>
        </Tabs>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import Student from './source/student'

export default {
  name: 'xuexiao',
  data () {
    return { }
  },
  components: { Location, Student },
  methods: {
  },
  mounted () {
  }
}
</script>

<style lang="scss" scoped>

.wrapper {
  margin: 0 10px 20px 10px;
}
</style>
