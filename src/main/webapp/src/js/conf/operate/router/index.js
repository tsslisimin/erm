import Vue from 'vue'
import Router from 'vue-router'

import homeIndex from '../pages/home/index'

import stuManageXinxi from '../pages/stuManage/xinxi'
import stuManageMingdan from '../pages/stuManage/mingdan'
import stuManageStudent from '../pages/stuManage/student'
import stuManageShenhe from '../pages/stuManage/shenhe'
import stuManageMingdanEdit from '../pages/stuManage/mingdanedit'
import stuManageZizhu from '../pages/stuManage/zizhu'
import stuManageApply from '../pages/stuManage/apply'
import stuManageShenheUrl from '../pages/stuManage/shenheUrl'
import stuManageZizhumingdan from '../pages/stuManage/zizhumingdan'

import fundGuojia from '../pages/fund/guojia'
import fundDifang from '../pages/fund/difang'
import fundXuexiao from '../pages/fund/xuexiao'
import fundShehui from '../pages/fund/shehui'
import fundZhibiao from '../pages/fund/zhibiao'
import fundFafang from '../pages/fund/fafang'

import msgSearch from '../pages/msgSearch/index'
import msgSearchOld from '../pages/msgSearch/old'
import msgDetail from '../pages/msgSearch/detail'

import msgKunnanDetail from '../pages/msgSearch/kunnanDetail'
import msgStudentDetail from '../pages/msgSearch/studentDetail'

import count from '../pages/count/index'
import countXiangmutype from '../pages/count/source/xiangmutype'
import countXiangmu from '../pages/count/source/xiangmu'
import countRenyuan from '../pages/count/source/renyuan'

import dailyYewu from '../pages/daily/yewu'
import dailyDaiban from '../pages/daily/daiban'
import dailyTongzhi from '../pages/daily/tongzhi'
import operateLog from '../pages/daily/log'
import profile from '../pages/profile/index'

import fileKunnan from '../pages/file/kunnan'
import fileShenbao from '../pages/file/shenbao'
import fileZizhu from '../pages/file/zizhu'
import shenqinEdit from '../pages/file/shenqinEdit'
import fileKunnanEdit from '../pages/file/kunnanedit'
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
      path: '/stuManage/xinxi',
      name: 'stuManage/xinxi',
      component: stuManageXinxi
    },
    {
      path: '/stuManage/mingdan',
      name: 'stuManage/mingdan',
      component: stuManageMingdan
    },
    {
      path: '/stuManage/student',
      name: 'stuManage/student',
      component: stuManageStudent
    },
    {
      path: '/stuManage/shenhe',
      name: 'stuManage/shenhe',
      component: stuManageShenhe
    },
    {
      path: '/stuManage/zizhu',
      name: 'stuManage/zizhu',
      component: stuManageZizhu
    },
    {
      path: '/stuManage/zizhumingdan',
      name: 'stuManage/zizhumingdan',
      component: stuManageZizhumingdan
    },
    {
      path: '/stuManage/apply',
      name: 'stuManage/apply',
      component: stuManageApply
    },
    {
      path: '/stuManage/shenheUrl',
      name: 'stuManage/shenheUrl',
      component: stuManageShenheUrl
    },
    {
      path: '/stuManage/mingdanedit/:stuId',
      name: 'stuManage/mingdanedit',
      component: stuManageMingdanEdit
    },
    {
      path: '/fund/guojia',
      name: 'fund/guojia',
      component: fundGuojia
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
      path: '/fund/zhibiao',
      name: 'fund/zhibiao',
      component: fundZhibiao
    },
    {
      path: '/fund/fafang',
      name: 'fund/fafang',
      component: fundFafang
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
      path: '/detail/:stuId/:fundId',
      name: 'detail',
      component: msgDetail
    },
    {
      path: '/detail/:stuId',
      name: 'studentDetail',
      component: msgStudentDetail
    },
    {
      path: '/kunnandetail/:stuId/:fundId',
      name: 'kunnandetail',
      component: msgKunnanDetail
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
      path: '/file/kunnanedit/:stuId',
      name: 'file/kunnanedit',
      component: fileKunnanEdit
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
    },
    {
      path: '/file/shenqinEdit/:stuId/:schId',
      name: 'file/shenqinEdit',
      component: shenqinEdit
    },

    {
      path: '/file/ledger',
      name: 'file/ledger',
      component: ledger
    }
  ]
})
