import { Layout } from '@/utils/routerHelper'

const { t } = useI18n()
/**
 * redirect: noredirect        当设置 noredirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'          设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * meta : {
 hidden: true              当设置 true 的时候该路由不会再侧边栏出现 如404，login等页面(默认 false)

 alwaysShow: true          当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式，
 只有一个时，会将那个子路由当做根路由显示在侧边栏，
 若你想不管路由下面的 children 声明的个数都显示你的根路由，
 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，
 一直显示根路由(默认 false)

 title: 'title'            设置该路由在侧边栏和面包屑中展示的名字

 icon: 'svg-name'          设置该路由的图标

 noCache: true             如果设置为true，则不会被 <keep-alive> 缓存(默认 false)

 breadcrumb: false         如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)

 affix: true               如果设置为true，则会一直固定在tag项中(默认 false)

 noTagsView: true          如果设置为true，则不会出现在tag中(默认 false)

 activeMenu: '/dashboard'  显示高亮的路由路径

 followAuth: '/dashboard'  跟随哪个路由进行权限过滤

 canTo: true               设置为true即使hidden为true，也依然可以进行路由跳转(默认 false)
 }
 **/
const remainingRouter: AppRouteRecordRaw[] = [
  {
    path: '/redirect',
    component: Layout,
    name: 'Redirect',
    children: [
      {
        path: '/redirect/:path(.*)',
        name: 'Redirect1',
        component: () => import('@/views/Redirect/Redirect.vue'),
        meta: {}
      }
    ],
    meta: {
      hidden: true,
      noTagsView: true
    }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/index',
    name: 'Home',
    meta: {},
    children: [
      {
        path: 'index',
        component: () => import('@/views/Home/Index3.vue'),
        name: 'Index',
        meta: {
          title: t('router.home'),
          icon: 'ep:home-filled',
          noCache: false,
          affix: true
        }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    name: 'UserInfo',
    meta: {
      hidden: true
    },
    children: [
      {
        path: 'profile',
        component: () => import('@/views/Profile/Index.vue'),
        name: 'Profile',
        meta: {
          canTo: true,
          hidden: true,
          noTagsView: false,
          icon: 'ep:user',
          title: t('common.profile')
        }
      },
      {
        path: 'notify-message',
        component: () => import('@/views/system/notify/my/index.vue'),
        name: 'MyNotifyMessage',
        meta: {
          canTo: true,
          hidden: true,
          noTagsView: false,
          icon: 'ep:message',
          title: '我的站内信'
        }
      }
    ]
  },

  {
    path: '/dict',
    component: Layout,
    name: 'dict',
    meta: {
      hidden: true
    },
    children: [
      {
        path: 'type/data/:dictType',
        component: () => import('@/views/system/dict/data/index.vue'),
        name: 'SystemDictData',
        meta: {
          title: '字典数据',
          noCache: true,
          hidden: true,
          canTo: true,
          icon: '',
          activeMenu: '/system/dict'
        }
      }
    ]
  },

  {
    path: '/codegen',
    component: Layout,
    name: 'CodegenEdit',
    meta: {
      hidden: true
    },
    children: [
      {
        path: 'edit',
        component: () => import('@/views/infra/codegen/EditTable.vue'),
        name: 'InfraCodegenEditTable',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          icon: 'ep:edit',
          title: '修改生成配置',
          activeMenu: 'infra/codegen/index'
        }
      }
    ]
  },
  {
    path: '/job',
    component: Layout,
    name: 'JobL',
    meta: {
      hidden: true
    },
    children: [
      {
        path: 'job-log',
        component: () => import('@/views/infra/job/logger/index.vue'),
        name: 'InfraJobLog',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          icon: 'ep:edit',
          title: '调度日志',
          activeMenu: 'infra/job/index'
        }
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/Login/Login.vue'),
    name: 'Login',
    meta: {
      hidden: true,
      title: t('router.login'),
      noTagsView: true
    }
  },
  {
    path: '/sso',
    component: () => import('@/views/Login/components/SSOLogin.vue'),
    name: 'SSOLogin',
    meta: {
      hidden: true,
      title: t('router.login'),
      noTagsView: true
    }
  },
  {
    path: '/social-login',
    component: () => import('@/views/Login/SocialLogin.vue'),
    name: 'SocialLogin',
    meta: {
      hidden: true,
      title: t('router.socialLogin'),
      noTagsView: true
    }
  },
  {
    path: '/403',
    component: () => import('@/views/Error/403.vue'),
    name: 'NoAccess',
    meta: {
      hidden: true,
      title: '403',
      noTagsView: true
    }
  },
  {
    path: '/404',
    component: () => import('@/views/Error/404.vue'),
    name: 'NoFound',
    meta: {
      hidden: true,
      title: '404',
      noTagsView: true
    }
  },
  {
    path: '/500',
    component: () => import('@/views/Error/500.vue'),
    name: 'Error',
    meta: {
      hidden: true,
      title: '500',
      noTagsView: true
    }
  },
  {
    path: '/station',
    component: Layout,
    name: 'station',
    meta: {
      hidden: true
    },
    children: [
      {
        path: '/station/cost-template/create',
        component: () => import('@/views/operations/costtemplate/CostTemplateForm.vue'),
        name: 'CostTemplateCreate',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '新建计费模板',
          activeMenu: '/station/cost-template'
        }
      },
      {
        path: '/station/station/create',
        component: () => import('@/views/operations/station/StationForm.vue'),
        name: 'StationCreate',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '新建站点',
          activeMenu: '/station/station'
        }
      },
      {
        path: '/station/equipment/create',
        component: () => import('@/views/operations/equipment/EquipmentForm.vue'),
        name: 'EquipmentCreate',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '新建电桩',
          activeMenu: '/station/equipment'
        }
      },
      {
        path: '/station/equipment/gun-info',
        component: () => import('@/views/operations/equipment/GunInfo.vue'),
        name: 'GunInfo',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '站枪详情',
          activeMenu: '/station/equipment'
        }
      }
    ]
  },
  {
    path: '/company',
    component: Layout,
    name: 'company',
    meta: {
      hidden: true
    },
    children: [
      {
        path: '/company/company/wallet',
        component: () => import('@/views/company/company/CompanyWallet.vue'),
        name: 'CompanyWallet',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '企业钱包',
          activeMenu: '/company/company'
        }
      },
    ]
  },
  {
    path: '/machine',
    component: Layout,
    name: 'machine',
    meta: {
      hidden: true
    },
    children: [
      {
        path: '/machine/vending-machine/create',
        component: () => import('@/views/machine/vendingMachine/VendingMachine.vue'),
        name: 'VendingMachineCreate',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '新建自动售货机',
          activeMenu: '/machine/vending-machine'
        }
      },
      {
        path: '/machine/vending-machine/cargo-lanes',
        component: () => import('@/views/machine/vendingMachine/CargoLanesSetting.vue'),
        name: 'CargoLanesSetting',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '售货机货道配置',
          activeMenu: '/machine/vending-machine'
        }
      },
      {
        path: '/machine/vending-machine/goods-setting',
        component: () => import('@/views/machine/vendingMachine/GoodsSetting.vue'),
        name: 'GoodsSetting',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '售货机商品管理',
          activeMenu: '/machine/vending-machine'
        }
      },
    ]
  },
  {
    path: '/member',
    component: Layout,
    name: 'member',
    meta: {
      hidden: true
    },
    children: [
      {
        path: '/member/company/user-info',
        component: () => import('@/views/member/company/UserInfo.vue'),
        name: 'CompanyUserInfo',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '企业用户详情',
          activeMenu: '/member/company'
        }
      },
    ]
  },
  {
    path: '/mall/product', // 商品中心
    component: Layout,
    name: 'ProductCenter',
    meta: {
      hidden: true
    },
    children: [
      {
        path: 'spu/add',
        component: () => import('@/views/mall/product/spu/form/index.vue'),
        name: 'ProductSpuAdd',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          icon: 'ep:edit',
          title: '商品添加',
          activeMenu: '/mall/product/spu'
        }
      },
      {
        path: 'spu/edit/:id(\\d+)',
        component: () => import('@/views/mall/product/spu/form/index.vue'),
        name: 'ProductSpuEdit',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          icon: 'ep:edit',
          title: '商品编辑',
          activeMenu: '/mall/product/spu'
        }
      },
      {
        path: 'spu/detail/:id(\\d+)',
        component: () => import('@/views/mall/product/spu/form/index.vue'),
        name: 'ProductSpuDetail',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          icon: 'ep:view',
          title: '商品详情',
          activeMenu: '/mall/product/spu'
        }
      },
      {
        path: 'property/value/:propertyId(\\d+)',
        component: () => import('@/views/mall/product/property/value/index.vue'),
        name: 'ProductPropertyValue',
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          icon: 'ep:view',
          title: '商品属性值',
          activeMenu: '/product/property'
        }
      }
    ]
  },
  {
    path: '/mall/trade', // 交易中心
    component: Layout,
    name: 'TradeCenter',
    meta: {
      hidden: true
    },
    children: [
      {
        path: 'order/detail/:id(\\d+)',
        component: () => import('@/views/mall/trade/order/detail/index.vue'),
        name: 'TradeOrderDetail',
        meta: { title: '订单详情', icon: 'ep:view', activeMenu: '/mall/trade/order' }
      },
      {
        path: 'after-sale/detail/:id(\\d+)',
        component: () => import('@/views/mall/trade/afterSale/detail/index.vue'),
        name: 'TradeAfterSaleDetail',
        meta: { title: '退款详情', icon: 'ep:view', activeMenu: '/mall/trade/after-sale' }
      }
    ]
  },
  {
    path: '/member',
    component: Layout,
    name: 'MemberCenter',
    meta: { hidden: true },
    children: [
      {
        path: 'user/detail/:id',
        name: 'MemberUserDetail',
        meta: {
          title: '会员详情',
          noCache: true,
          hidden: true
        },
        component: () => import('@/views/member/user/detail/index.vue')
      }
    ]
  },
  {
    path: '/order',
    component: Layout,
    name: 'OrderManagement',
    meta: { hidden: true },
    children: [
      {
        path: '/order/order/info',
        name: 'OrderDetail',
        component: () => import('@/views/operations/order/OrderForm.vue'),
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '历史订单详情',
          activeMenu: '/order/order'
        }
      },
      {
        path: '/order/pending-order/info',
        name: 'PendingOrderDetail',
        component: () => import('@/views/operations/pendingOrder/OrderForm.vue'),
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '待处理订单详情',
          activeMenu: '/order/pending-order'
        }
      },
      {
        path: '/order/current-order/info',
        name: 'CurrentOrderDetail',
        component: () => import('@/views/operations/currentOrder/OrderForm.vue'),
        meta: {
          noCache: true,
          hidden: true,
          canTo: true,
          title: '实时订单详情',
          activeMenu: '/order/current-order'
        }
      }
    ]
  },
  {
    path: '/pay',
    component: Layout,
    name: 'pay',
    meta: { hidden: true },
    children: [
      {
        path: 'cashier',
        name: 'PayCashier',
        meta: {
          title: '收银台',
          noCache: true,
          hidden: true
        },
        component: () => import('@/views/pay/cashier/index.vue')
      }
    ]
  },
  {
    path: '/diy',
    name: 'DiyCenter',
    meta: { hidden: true },
    component: Layout,
    children: [
      {
        path: 'template/decorate/:id',
        name: 'DiyTemplateDecorate',
        meta: {
          title: '模板装修',
          noCache: true,
          hidden: true
        },
        component: () => import('@/views/mall/promotion/diy/template/decorate.vue')
      },
      {
        path: 'page/decorate/:id',
        name: 'DiyPageDecorate',
        meta: {
          title: '页面装修',
          noCache: true,
          hidden: true
        },
        component: () => import('@/views/mall/promotion/diy/page/decorate.vue')
      }
    ]
  }
]

export default remainingRouter
