<template>
  <div class="wrapper">
    <Location>资助管理 > 地方政府资助</Location>
    <div class="con">
      <Card :padding="10">
        <Project :proType="2"></Project>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import Project from '../source/project'

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
.wrapper {
  margin: 0 10px 20px 10px;
}
</style>
