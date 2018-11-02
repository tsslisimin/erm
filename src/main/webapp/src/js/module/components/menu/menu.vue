<template>
  <div class="nav clearfix">
    <!-- 教育局 -->
    <Menu ref="sideMenu" v-if="user === 'ROLE_EB'" :theme="'dark'" :active-name="activeMenu"
          :open-names="openedSubmenuArr" :width="200" @on-select="changeMenu" :accordion="true">
      <MenuItem name="home">
        <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe600;</i>
        首页
      </MenuItem>
      <Submenu name="fund">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe69f;</i>
          资助管理
        </template>
        <MenuItem name="fund/guojia/index/111" class="fund" data-idx="0">国家资助</MenuItem>
        <MenuItem name="fund/difang">地方政府资助</MenuItem>
        <!--<MenuItem name="fund/xuexiao">学校资助</MenuItem>-->
        <!--<MenuItem name="fund/shehui">社会资助</MenuItem>-->
        <MenuItem name="fund/shenhe">资助名单审核</MenuItem>
        <MenuItem name="fund/shenheitem">资助项目审核</MenuItem>
        <!--<MenuItem name="fund/card">银行卡比对</MenuItem>-->
      </Submenu>
      <Submenu name="msgSearch">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe603;</i>
          信息查询
        </template>
        <MenuItem name="msgSearch/basic">全县学生基础信息</MenuItem>
        <MenuItem name="msgSearch/index">受助在校生</MenuItem>
        <MenuItem name="msgSearch/old">受助毕业生</MenuItem>
      </Submenu>
      <MenuItem name="count">
        <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe632;</i>
        统计报表
      </MenuItem>
      <Submenu name="daily">
        <template slot="title">
          <i class="iconfont" style="font-size: 13px;margin-right: -2px;margin-left: -2px;">&#xe647;</i>
          日常办公
        </template>
        <MenuItem name="daily/yewu">业务流程</MenuItem>
        <MenuItem name="daily/daiban">待办事项</MenuItem>
        <MenuItem name="daily/tongzhi">通知通告</MenuItem>
        <MenuItem name="daily/log">操作日志</MenuItem>
      </Submenu>
      <Submenu name="file">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe64d;</i>
          电子档案
        </template>
        <MenuItem name="file/zizhu">资助学生名单</MenuItem>
        <MenuItem name="file/ledger">台账查看</MenuItem>
      </Submenu>
      <MenuItem name="profile">
        <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe61c;</i>
        资助概况
      </MenuItem>
      <Submenu name="manage">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe76a;</i>
          设置
        </template>
        <MenuItem name="manage/shezhi">学校管理</MenuItem>
        <MenuItem name="manage/zhanghu">账户管理</MenuItem>
      </Submenu>
    </Menu>

    <!-- 校长 -->
    <Menu v-if="user === 'ROLE_SCH'" :theme="'dark'" :active-name="activeMenu" :open-names="openedSubmenuArr"
          :width="200" @on-select="changeMenu" :accordion="true">
      <MenuItem name="home">
        <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe600;</i>
        首页
      </MenuItem>
      <Submenu name="manage">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe76a;</i>
          资助管理
        </template>
        <MenuItem name="manage/shenhe">资助名单推荐</MenuItem>
        <MenuItem name="manage/gongshi">公示</MenuItem>
        <MenuItem name="manage/fafang">资金发放结果查看</MenuItem>
      </Submenu>
      <Submenu name="msgSearch">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe603;</i>
          信息查询
        </template>
        <MenuItem name="msgSearch/index">受助在校生</MenuItem>
        <MenuItem name="msgSearch/old">受助毕业生</MenuItem>
      </Submenu>
      <MenuItem name="count">
        <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe632;</i>
        统计报表
      </MenuItem>
      <Submenu name="daily">
        <template slot="title">
          <i class="iconfont" style="font-size: 13px;margin-right: -2px;margin-left: -2px;">&#xe647;</i>
          日常办公
        </template>
        <MenuItem name="daily/yewu">业务流程</MenuItem>
        <MenuItem name="daily/daiban">待办事项</MenuItem>
        <MenuItem name="daily/tongzhi">通知通告</MenuItem>
        <MenuItem name="daily/gongzhang">公章上传</MenuItem>
        <MenuItem name="daily/log">操作日志</MenuItem>
      </Submenu>
      <Submenu name="file">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe64d;</i>
          电子档案
        </template>
        <MenuItem name="file/kunnan">困难学生花名册</MenuItem>
        <MenuItem name="file/shenbao">申报对象名单</MenuItem>
        <MenuItem name="file/zizhu">资助学生名单</MenuItem>
        <MenuItem name="file/ledger">台账查看</MenuItem>
      </Submenu>
      <MenuItem name="profile">
        <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe61c;</i>
        资助概况
      </MenuItem>
    </Menu>

    <!-- 操作员 -->
    <Menu v-if="user === 'ROLE_OPER'" :theme="'dark'" :active-name="activeMenu" :open-names="openedSubmenuArr"
          :width="200" @on-select="changeMenu" :accordion="true">
      <MenuItem name="home">
        <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe600;</i>
        首页
      </MenuItem>

      <Submenu name="stuManage">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe64d;</i>
          学生管理
        </template>
        <MenuItem name="stuManage/mingdan">学生信息录入</MenuItem>
        <MenuItem name="stuManage/xinxi">学生信息查看</MenuItem>
        <MenuItem name="stuManage/student">贫困信息录入</MenuItem>
        <MenuItem name="stuManage/shenhe">困难学生认定</MenuItem>
        <MenuItem name="stuManage/apply">申请表填写</MenuItem>
        <MenuItem name="stuManage/zizhu">资助名单拟定</MenuItem>
        <MenuItem name="stuManage/zizhumingdan">拟定名单查看</MenuItem>
      </Submenu>
      <Submenu name="fund">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe69f;</i>
          资助管理
        </template>
        <MenuItem name="fund/guojia">国家资助</MenuItem>
        <MenuItem name="fund/difang">地方政府资助</MenuItem>
        <MenuItem name="fund/xuexiao">学校资助</MenuItem>
        <MenuItem name="fund/shehui">社会资助</MenuItem>
        <MenuItem name="fund/zhibiao">指标管理</MenuItem>
        <MenuItem name="fund/fafang">资金发放结果查看</MenuItem>
      </Submenu>

      <Submenu name="msgSearch">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe603;</i>
          信息查询
        </template>
        <MenuItem name="msgSearch/index">受助在校生</MenuItem>
        <MenuItem name="msgSearch/old">受助毕业生</MenuItem>
      </Submenu>
      <MenuItem name="count">
        <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe632;</i>
        统计报表
      </MenuItem>
      <Submenu name="daily">
        <template slot="title">
          <i class="iconfont" style="font-size: 13px;margin-right: -2px;margin-left: -2px;">&#xe647;</i>
          日常办公
        </template>
        <MenuItem name="daily/yewu">业务流程</MenuItem>
        <MenuItem name="daily/daiban">待办事项</MenuItem>
        <MenuItem name="daily/tongzhi">通知通告</MenuItem>
        <MenuItem name="daily/log">操作日志</MenuItem>
      </Submenu>
      <Submenu name="file">
        <template slot="title">
          <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe64d;</i>
          电子档案
        </template>
        <MenuItem name="file/kunnan">困难学生花名册</MenuItem>
        <MenuItem name="file/shenbao">申报对象名单</MenuItem>
        <MenuItem name="file/zizhu">资助学生名单</MenuItem>
        <MenuItem name="file/ledger">台账查看</MenuItem>
      </Submenu>
      <MenuItem name="profile">
        <i class="iconfont" style="font-size: 15px;margin-right: 3px;">&#xe61c;</i>
        资助概况
      </MenuItem>
    </Menu>
  </div>
</template>

<script>
  import $ from 'jquery'

  export default {
    name: 'menu',
    props: ['user'],
    data() {
      return {
        activeMenu: this.$route.path.slice(1),
        openedSubmenuArr: []
      }
    },
    methods: {
      changeMenu(active) {
        this.$router.push({
          path: '/' + active
        })
      }
    },
    watch: {
      '$route'(to) {
        let _path = to.path

        if (_path.indexOf('fund/guojia') !== -1) {
          this.$nextTick(() => {
            if (this.$refs.sideMenu) {
              this.$refs.sideMenu.currentActiveName = 'fund/guojia/index/111'
            }
          })
        }
      }
    },
    created() {
      let curName = this.$route.path
      let subName = curName.split('/')[1]
      this.openedSubmenuArr.push(subName)
    }
  }
</script>

<style lang="scss">
  @import '../main';

  .nav {
    background-color: $color-primary-blue;
    width: 200px;
    height: 100%;
    position: fixed;
    /* overflow: auto; */

    .ivu-menu {
      color: $color-white;
    }
    .ivu-menu-dark {
      background-color: $color-primary-blue;
    }
    .ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu-title {
      font-size: 15px;
      font-weight: bold;
      color: $color-white;
      background-color: $color-primary-blue;
    }
    .ivu-menu-dark.ivu-menu-vertical > .ivu-menu-item {
      font-size: 15px;
      font-weight: bold;
      color: $color-white;
      background-color: $color-primary-blue;
    }
    .ivu-menu-dark.ivu-menu-vertical > .ivu-menu-item:hover {
      background-color: $color-primary-blue-hover !important;
    }
    .ivu-menu-dark.ivu-menu-vertical > .ivu-menu-item-active {
      color: $color-primary-blue;
      background-color: $color-white !important;
      border-right: none;
    }
    .ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu .ivu-menu-item {
      color: $color-white;
      background-color: $color-primary-blue-active !important;
    }
    .ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu .ivu-menu-item:hover {
      background-color: $color-primary-blue-hover !important;
    }
    .ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu .ivu-menu-item-active, .ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu .ivu-menu-item-active:hover {
      color: $color-primary-blue;
      background-color: $color-white !important;
    }
    .ivu-menu-vertical .ivu-menu-submenu .ivu-menu-item {
      padding-left: 48px;
    }
  }
</style>
