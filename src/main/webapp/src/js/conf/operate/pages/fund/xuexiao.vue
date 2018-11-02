<template>
  <div class="wrapper">
    <Location>资助管理 > 学校资助</Location>
    <div class="con">
      <Card :padding="10">
        <Project :proType="3"></Project>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import Project from './source/project2'

export default {
  name: 'manageZizhu',
  data () {
    return {
    }
  },
  components: { Location, Project },
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
