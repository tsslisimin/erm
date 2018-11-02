import Vue from 'vue'
import Router from 'vue-router'

import homeIndex from '../pages/home/index'

import manageShenhe from '../pages/manage/shenhe'
import manageStudent from '../pages/manage/student'
import manageGongshi from '../pages/manage/gongshi'
import manageFafang from '../pages/manage/fafang'

import msgSearch from '../pages/msgSearch/index'
import msgSearchOld from '../pages/msgSearch/old'
import msgDetail from '../pages/msgSearch/detail'
import msgStudentDetail from '../pages/msgSearch/studentDetail'

import count from '../pages/count/index'
import countXiangmutype from '../pages/count/source/xiangmutype'
import countXiangmu from '../pages/count/source/xiangmu'
import countRenyuan from '../pages/count/source/renyuan'

import dailyYewu from '../pages/daily/yewu'
import dailyDaiban from '../pages/daily/daiban'
import dailyTongzhi from '../pages/daily/tongzhi'
import dailyGongzhang from '../pages/daily/gongzhang'
import operateLog from '../pages/daily/log'

import profile from '../pages/profile/index'

import fileKunnan from '../pages/file/kunnan'
import fileShenbao from '../pages/file/shenbao'
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
      path: '/manage/shenhe',
      name: 'manage/shenhe',
      component: manageShenhe
    },
    {
      path: '/manage/student',
      name: 'manage/student',
      component: manageStudent
    },
    {
      path: '/manage/gongshi',
      name: 'manage/gongshi',
      component: manageGongshi
    },
    {
      path: '/manage/fafang',
      name: 'manage/fafang',
      component: manageFafang
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
      path: '/detail/:stuId',
      name: 'studentDetail',
      component: msgStudentDetail
    },
    {
      path: '/detail/:stuId/:fundId',
      name: 'detail',
      component: msgDetail
    },
    {
      path: '/count',
      name: 'count',
      component: count
    },
    {
      path: '/count/xiangmutype',
      name: 'count/xiangmutype',
      component: countXiangmutype
    },
    {
      path: '/count/xiangmu/:year/:fundId',
      name: 'count/xiangmu',
      component: countXiangmu
    },
    {
      path: '/count/renyuan/:year',
      name: 'count/renyuan',
      component: countRenyuan
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
      path: '/daily/gongzhang',
      name: 'daily/gongzhang',
      component: dailyGongzhang
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
      path: '/file/kunnan',
      name: 'file/kunnan',
      component: fileKunnan
    },
    {
      path: '/file/shenbao',
      name: 'file/shenbao',
      component: fileShenbao
    },
    {
      path: '/file/zizhu',
      name: 'file/zizhu',
      component: fileZizhu
    }
    ,
    {
      path: '/file/ledger',
      name: 'file/ledger',
      component: ledger
    }
    // {
    //   path: '/product/:id',
    //   name: 'product',
    //   component: ProductPage
    // }
  ]
})
