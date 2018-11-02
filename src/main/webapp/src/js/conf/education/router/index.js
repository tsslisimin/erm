import Vue from 'vue'
import Router from 'vue-router'

import homeIndex from '../pages/home/index'

import manageShezhi from '../pages/manage/shezhi'
import manageZhanghu from '../pages/manage/zhanghu'
import manageFenpei from '../pages/manage/fenpei'

import fundGuojia from '../pages/fund/guojia/index'
import fundGuojiaDetail from '../pages/fund/guojia/detail'
import fundDifang from '../pages/fund/difang/index'
import fundXuexiao from '../pages/fund/xuexiao/index'
import fundShehui from '../pages/fund/shehui/index'
import fundShenhe from '../pages/fund/shenhe'
import fundCard from '../pages/fund/card'
import fundShenheitem from '../pages/fund/shenheitem'
import shenheDetail from '../pages/fund/shenheDetail'
import fundDetail from '../pages/fund/source/projectDetail'

import msgSearch from '../pages/msgSearch/index'
import msgSearchOld from '../pages/msgSearch/old'
import msgDetail from '../pages/msgSearch/detail'
import detailBasic from '../pages/msgSearch/detail-basic'
import msgBasic from '../pages/msgSearch/basic'

import count from '../pages/count/index'
import countXuexiao from '../pages/count/source/xuexiao'
import countXuequ from '../pages/count/source/xuequ'
import countXiangmu from '../pages/count/source/xiangmu'
import countRenyuan from '../pages/count/source/renyuan'

import dailyYewu from '../pages/daily/yewu'
import dailyDaiban from '../pages/daily/daiban'
import dailyTongzhi from '../pages/daily/tongzhi'
import operateLog from '../pages/daily/log'

import profile from '../pages/profile/index'

import fileZizhu from '../pages/file/zizhu'
import ledger from '../pages/file/ledger'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/home',
      name: 'home',
      component: homeIndex
    },
    {
      path: '/manage/shezhi',
      name: 'manage/shezhi',
      component: manageShezhi
    },
    {
      path: '/manage/zhanghu',
      name: 'manage/zhanghu',
      component: manageZhanghu
    },
    {
      path: '/manage/fenpei',
      name: 'manage/fenpei',
      component: manageFenpei
    },
    {
      path: '/fund/shenhe',
      name: 'fund/shenhe',
      component: fundShenhe
    },
    {
      path: '/fund/card',
      name: 'fund/card',
      component: fundCard
    },
    {
      path: '/fund/shenheitem',
      name: 'fund/shenheitem',
      component: fundShenheitem
    },
    {
      path: '/fund/guojia/index/:guoFundId',
      name: 'fund/guojia/index',
      component: fundGuojia
    },
    {
      path: '/fund/guojia/detail/:fundId',
      name: 'fund/guojia/detail',
      component: fundGuojiaDetail
    },
    {
      path: '/fund/difang',
      name: 'fund/difang',
      component: fundDifang
    },
    {
      path: '/fund/xuexiao',
      name: 'fund/xuexiao',
      component: fundXuexiao
    },
    {
      path: '/fund/shehui',
      name: 'fund/shehui',
      component: fundShehui
    },
    {
      path: '/fund/detail/:proId',
      name: 'fund/detail',
      component: fundDetail
    },
    {
      path: '/msgSearch/index',
      name: 'msgSearch/index',
      component: msgSearch
    },
    {
      path: '/msgSearch/old',
      name: 'msgSearch/old',
      component: msgSearchOld
    },
    {
      path: '/msgSearch/basic',
      name: 'basic',
      component: msgBasic
    },
    {
      path: '/detail/:stuId/:fundId',
      name: 'detail',
      component: msgDetail
    }, {
      path: '/detailBasic/:stuId',
      name: 'detailBasic',
      component: detailBasic
    },

    {
      path: '/count',
      name: 'count',
      component: count
    },
    {
      path: '/daily/yewu',
      name: 'daily/yewu',
      component: dailyYewu
    },
    {
      path: '/daily/daiban',
      name: 'daily/daiban',
      component: dailyDaiban
    },
    {
      path: '/daily/tongzhi',
      name: 'daily/tongzhi',
      component: dailyTongzhi
    },
    {
      path: '/daily/log',
      name: 'daily/log',
      component: operateLog
    },
    {
      path: '/profile',
      name: 'profile',
      component: profile
    },
    {
      path: '/shenheDetail/:fundId/:schId',
      name: 'shenheDetail',
      component: shenheDetail
    },
    {
      path: '/count/xuexiao/:year/:schId',
      name: 'count/xuexiao',
      component: countXuexiao
    },
    {
      path: '/count/xuequ',
      name: 'count/xuequ',
      component: countXuequ
    },
    {
      path: '/count/xiangmu',
      name: 'count/xiangmu',
      component: countXiangmu
    },
    {
      path: '/count/renyuan/:year',
      name: 'count/renyuan',
      component: countRenyuan
    },
    {
      path: '/file/zizhu',
      name: 'file/zizhu',
      component: fileZizhu
    },
    {
      path: '/file/ledger',
      name: 'file/ledger',
      component: ledger
    }
  ]
})
