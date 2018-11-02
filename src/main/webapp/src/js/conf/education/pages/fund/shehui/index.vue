<template>
  <div class="wrapper">
    <Location>资助管理 > 社会资助</Location>
    <div class="con">
      <Card :padding="10">
        <projectList :proType="4"></projectList>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import projectList from '../source/projectList'

export default {
  name: 'manageZizhu',
  data () {
    return {
    }
  },
  components: { Location, projectList },
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
