import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                   // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true               // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect           // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'             // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * meta : {
    noCache: true                // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'               // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'             // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false            // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'   // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [{
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [{
      path: '/redirect/:path(.*)',
      component: (resolve) => require(['@/views/redirect'], resolve)
    }]
  },
  {
    path: '/login',
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },
  {
    path: '/register',
    component: (resolve) => require(['@/views/register'], resolve),
    hidden: true
  },
  {
    path: '/404',
    component: (resolve) => require(['@/views/error/404'], resolve),
    hidden: true
  },
  {
    path: '/401',
    component: (resolve) => require(['@/views/error/401'], resolve),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: 'index',
    children: [{
      path: 'index',
      component: (resolve) => require(['@/views/index'], resolve),
      // name: 'Index',
      meta: {
        title: '首页',
        icon: 'dashboard',
        affix: true
      }
    }]
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [{
      path: 'index',
      component: (resolve) => require(['@/views/index'], resolve),
      // name: 'Index',
      meta: {
        title: '首页',
        icon: 'dashboard',
        affix: true
      }
    }]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [{
      path: 'profile',
      component: (resolve) => require(['@/views/system/user/profile/index'], resolve),
      name: 'Profile',
      meta: {
        title: '个人中心',
        icon: 'user'
      }
    }]
  },
  {
    path: '/system',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [{
      path: 'about',
      component: (resolve) => require(['@/views/system/about/index'], resolve),
      name: 'About',
      meta: {
        title: '系统关于',
        icon: 'peoples'
      }
    }]
  },
  {
    path: '/prototype',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [{
      path: 'showImg',
      component: (resolve) => require(['@/views/prototype/showImg/index'], resolve),
      name: 'ShowImg',
      meta: {
        title: '效果图',
        icon: 'peoples'
      }
    }]
  },
  {
    path: '/workingHours',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [{
      path: 'workingInfo',
      component: (resolve) => require(['@/views/workingHours/myWorkingHours/workingInfo'], resolve),
      name: 'WorkingInfo',
      meta: {
        title: '查看工时详情',
        icon: '',
        activeMenu: '/workingHours/myWorkingHours'
      }
    }, {
      path: 'dailyReportToRecord',
      component: (resolve) => require(['@/views/workingHours/projectDai/dailyReportToRecord'], resolve),
      name: 'DailyReportToRecord',
      meta: {
        title: '每日上报记录',
        icon: '',
        activeMenu: '/workingHours/projectDai'
      }
    }, {
      path: 'fillInWorkingHours',
      component: (resolve) => require(['@/views/workingHours/myWorkingHours/fillInWorkingHours'], resolve),
      name: 'FillInWorkingHours',
      meta: {
        title: '填报记录',
        icon: '',
        activeMenu: '/workingHours/myWorkingHours'
      }
    }]
  },
  {
    path: '/overallStatistics',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [{
      path: "workingHoursForDetails",
      component: (resolve) => require(['@/views/workingHours/overallStatistics/workingHoursForDetails'], resolve),
      name: 'WorkingHoursForDetails',
      meta: {
        title: '上报记录',
        activeMenu: '/workingHours/overallStatistics'

      },
    }]
  },
  {
    path: '/projectManagement',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [{
        path: "newWorkingHouring",
        component: (resolve) => require(['@/views/workingHours/projectManagement/newWorkingHouring/index'], resolve),
        name: 'NewWorkingHouring',
        meta: {
          title: '工时上报记录',
          activeMenu: '/workingHours/projectManagement'

        }
      },
      {
        path: "tadayForHour",
        component: (resolve) => require(['@/views/workingHours/projectManagement/tadayForHour/index'], resolve),
        name: 'TadayForHour',
        meta: {
          title: '每日上报记录',
          activeMenu: '/workingHours/projectManagement'

        }
      },
      {
        path: "hourSubsidiary",
        component: (resolve) => require(['@/views/workingHours/projectManagement/hourSubsidiary'], resolve),
        name: 'HourSubsidiary',
        meta: {
          title: '工时明细',
          activeMenu: '/workingHours/projectManagement'

        }
      },
      {
        path: "costStatistics",
        component: (resolve) => require(['@/views/workingHours/projectManagement/costStatistics/index'], resolve),
        name: 'CostStatistics',
        meta: {
          title: '项目成本统计',
          activeMenu: '/workingHours/projectManagement'

        }
      },
      {
        path: "projectSettingsHour",
        component: (resolve) => require(['@/views/workingHours/projectSettingsHour/index'], resolve),
        name: 'ProjectSettingsHour',
        meta: {
          title: '项目详情',
          activeMenu: '/workingHours/projectManagement'
        }
      }
    ]
  }, {
    path: '/mange/project-set',
    component: Layout,
    hidden: true,
    children: [{
      path: 'projectSettings',
      component: (resolve) => require(['@/views/system/projectSettings/index'], resolve),
      name: 'ProjectSettings',
      meta: {
        title: '项目设置',
        activeMenu: '/manage/project'
      }
    }]
  }, {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    children: [{
      path: 'role/:userId(\\d+)',
      component: (resolve) => require(['@/views/system/user/authRole'], resolve),
      name: 'AuthRole',
      meta: {
        title: '分配角色',
        activeMenu: '/system/user'
      }
    }]
  }, {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    children: [{
      path: 'user/:roleId(\\d+)',
      component: (resolve) => require(['@/views/system/role/authUser'], resolve),
      name: 'AuthUser',
      meta: {
        title: '分配用户',
        activeMenu: '/system/role'
      }
    }]
  }, {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    children: [{
      path: 'index/:dictId(\\d+)',
      component: (resolve) => require(['@/views/system/dict/data'], resolve),
      name: 'Data',
      meta: {
        title: '字典数据',
        activeMenu: '/system/dict'
      }
    }]
  }, {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    children: [{
      path: 'index',
      component: (resolve) => require(['@/views/monitor/job/log'], resolve),
      name: 'JobLog',
      meta: {
        title: '调度日志',
        activeMenu: '/monitor/job'
      }
    }]
  }, {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    children: [{
      path: 'index/:tableId(\\d+)',
      component: (resolve) => require(['@/views/tool/gen/editTable'], resolve),
      name: 'GenEdit',
      meta: {
        title: '修改生成配置',
        activeMenu: '/tool/gen'
      }
    }]
  }
]

export default new Router({
  // mode: 'history', // 去掉url中的#
  mode: 'hash', // 去掉url中的#
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRoutes
})
