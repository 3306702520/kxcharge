import charging_site from '@/assets/svgs/index/charging_site.svg'
import occupancy_factor from '@/assets/svgs/index/occupancy_factor.svg'
import leisure from '@/assets/svgs/index/leisure.svg'
import malfunction from '@/assets/svgs/index/malfunction.svg'
import off_line from '@/assets/svgs/index/off_line.svg'

import drder_form from '@/assets/svgs/index/drder_form.svg'
import station_manage from '@/assets/svgs/index/station_manage.svg'
import charging from '@/assets/svgs/index/charging.svg'
import member from '@/assets/svgs/index/member.svg'
import operator from '@/assets/svgs/index/enterprise.svg'
import block_up from '@/assets/svgs/index/block_up.svg'

export const chargeDataOrigin = [
  {
    bg: "#15c350",
    title: "充电电量(度)",
    value: "645.86",
    compare: "3",
    nextValue: "543",
    key: 'ChargedPower'
  },
  {
    bg: "#ffb100",
    title: "充电次数(单)",
    value: "3234",
    compare: "10",
    nextValue: "5657",
    key: 'OrderCount'
  },
  {
    bg: "#02c1ff",
    title: "订单应收金额(元)",
    value: "1356",
    compare: "10",
    nextValue: "9823",
    key: 'ReceivableAmount'
  },
  {
    bg: "#7c7eff",
    title: "订单应收电费(元)",
    value: "4267.33",
    compare: "10",
    nextValue: "734",
    key: 'ReceivableElectricity'
  },
  {
    bg: "#0778f6",
    title: "订单应收服务费(元)",
    value: "154.12",
    compare: "11",
    nextValue: "423",
    key: 'ReceivableService'
  }
]

export const realTimeDataOrigin = [
  {
    title: "充电中",
    url: charging_site,
    key: 'charging',
    value: 0
  },
  {
    title: "空闲中",
    url: leisure,
    key: 'atLeisure',
    value: 0
  },
  {
    title: "故障中",
    url: malfunction,
    key: 'inTrouble',
    value: 0
  },
  {
    title: "占用中",
    url: occupancy_factor,
    key: 'inUse',
    value: 0
  },
  {
    title: "离线中",
    url: off_line,
    key: 'offLine',
    value: 0
  },
  {
    title: "停用中",
    url: block_up,
    key: 'outOfUse',
    value: 0
  }
]

export const appDataOrigin = [
  {
    name: "实时订单",
    avater: drder_form,
    url: "/order/current-order",
    bg: "rgba(79,176,81,.2)"
  },
  {
    name: "站点管理",
    avater: station_manage,
    url: "/electric/station",
    bg: "rgba(137,146,200,.2)"
  },
  {
    name: "电桩管理",
    avater: charging,
    url: "/electric/equipment",
    bg: "rgba(89,173,248,.2)"
  },
  {
    name: "会员管理",
    avater: member,
    url: "/member/user",
    bg: "rgba(255,198,0,.2)"
  },
  {
    name: "优惠券",
    avater: operator,
    url: "/company/company",
    bg: "rgba(85,215,255,.2)"
  }
]

export const stationOptionsOrigin = [
  {label: "今日", value: 1},
  {label: "昨日", value: 2},
  {label: "近30天", value: 3},
  {label: "近12个月", value: 4},
]
