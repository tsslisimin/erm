<template>
  <div class="main-body">
    <e-menu :user="'ROLE_EB'"></e-menu>
    <div class="main-r">
      <router-view></router-view>
    </div>

    <!-- third float -->
    <div class="menu-third">
      <div class="sub-menu">
        <p><router-link :to="'/fund/guojia/index/111'"><i class="iconfont">&#xe648;</i>学前教育资助</router-link></p>
        <p><router-link :to="'/fund/guojia/index/112'"><i class="iconfont">&#xe648;</i>义务教育资助</router-link></p>
        <p><router-link :to="'/fund/guojia/index/113'"><i class="iconfont">&#xe648;</i>普高教育资助</router-link></p>
        <p><router-link :to="'/fund/guojia/index/114'"><i class="iconfont">&#xe648;</i>中职教育资助</router-link></p>
        <!-- <p><router-link :to="'/fund/guojia/index/115'"><i class="iconfont">&#xe648;</i>高等教育资助</router-link></p> -->
      </div>
    </div>
  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import eMenu from 'js/module/components/menu/menu'
import $ from 'jquery'

export default {
  data () {
    return {
      listData: []
    }
  },
  components: { eMenu },
  methods: {
    // 添加三级菜单侦听事件
    initSubMenu () {
      $('body').on('mouseenter', '.fund', (e) => {
        $('.menu-third').show()
      })
      $('body').on('mouseleave', '.fund', (e) => {
        $('.menu-third').hide()
      })
      $('body').on('mouseenter', '.menu-third', (e) => {
        $('.menu-third').show()
      })
      $('body').on('mouseleave', '.menu-third', (e) => {
        $('.menu-third').hide()
      })
    }
  },
  mounted () {
    this.initSubMenu()
  }
}
</script>

<style lang="scss" scoped>
  .main-body {
    .main-r {
      margin-left: 200px;
    }
    .menu-third {
      display: none;
      position: fixed;
      left: 199px;
      top: 159px;
      z-index: 99;
      background: #fff;
      border-top: 1px solid #0098E1;
      border-right: 1px solid #0098E1;
      border-bottom: 1px solid #0098E1;
      -moz-box-shadow:3px 2px 3px #e6e6e6;
      -webkit-box-shadow:3px 2px 3px #e6e6e6;
      box-shadow:3px 2px 3px #e6e6e6;
      .sub-menu {
        p {
          height: 49px;
          line-height: 49px;
          font-size: 14px;
          a {
            color: #323e5d;
            display: block;
            height: 49px;
            line-height: 49px;
            padding-left: 20px;
            padding-right: 30px;
          }
          i {
            font-size: 14px;
            margin-right: 5px;
          }
          &:hover {
            background-color: #02B1FF;
            color: #fff;
            a {
              color: #fff;
            }
          }
        }
      }
    }
  }
</style>
