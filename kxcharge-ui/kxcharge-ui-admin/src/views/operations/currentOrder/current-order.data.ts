import { CrudSchema } from "@/hooks/web/useCrudSchemas";
import { dateFormatter } from "@/utils/formatTime";
import { IInfoData } from "../order/order.data";

const crudSchemas = reactive<CrudSchema[]>([
  {
    label: '序号',
    field: '1',
    type: 'index'
  },
  {
    label: '充电单号',
    field: 'seqNumber',
    width: '150px'
  },
  {
    label: '用户账号',
    field: 'userAccount',
    width: '150px'
  },
  {
    label: '用户手机号',
    field: 'userPhone',
    width: '150px'
  },
  {
    label: '归属企业',
    field: 'companyName',
    width: '150px'
  },
  {
    label: '充电车辆',
    field: 'carNumber',
    width: '150px'
  },
  {
    label: '充电时长',
    field: 'chargeTime',
    width: '150px'
  },
  {
    label: '已充电量(度)',
    field: 'chargedPower',
    width: '150px'
  },
  {
    label: '当前SOC(%)',
    field: 'soc',
    width: '150px'
  },
  {
    label: '电站名称',
    field: 'stationName',
    width: '150px'
  },
  {
    label: '终端编码',
    field: 'equipmentConnectorCode',
    width: '150px'
  },
  {
    label: '运营商',
    field: 'orgName',
    width: '150px'
  },
  {
    label: '订单来源',
    field: 'chargingSource',
    dictType: DICT_TYPE.CHARGING_SOURCE,
    dictClass: 'number',
    width: '150px'
  },
  {
    label: '充电方式',
    field: 'chargingWay',
    dictType: DICT_TYPE.CHARGING_WAY,
    dictClass: 'number',
    width: '150px'
  },
  {
    label: '开始充电时间',
    field: 'startTime',
    formatter: dateFormatter,
    width: '200px'
  },
  
  {
    label: '操作',
    field: 'action',
    table: {
      fixed: 'right'
    }
  }
])

const crud2Schemas = reactive<CrudSchema[]>([
  {
    label: '序号',
    field: '1',
    type: 'index'
  },
  {
    label: '占用订单号',
    field: 'code',
  },
  {
    label: '用户账号',
    field: 'account',
  },
  {
    label: '归属企业',
    field: 'companyName',
  },
  {
    label: '是否实际扣费',
    field: 'isFree',
  },
  {
    label: '电站名称',
    field: 'stationName',
  },
  {
    label: '占用终端名称',
    field: 'devName',
  },
  {
    label: '占用终端编码',
    field: 'devCode',
  },
  {
    label: '占用时长',
    field: 'time',
  },
  {
    label: '订单来源',
    field: 'orderWay',
    dictType: DICT_TYPE.CHARGING_WAY,
    dictClass: 'number',
  },
  {
    label: '占位开始时间',
    field: 'startTime'
  },
  {
    label: '操作',
    field: 'action',
  }
])

const currentInfoLeftLabel: IInfoData[] = [
  {
    position: 'left',
    name: '用户账号',
    key: 'userAccount'
  },
  {
    position: 'left',
    name: '用户称昵',
    key: 'userName'
  },
  {
    position: 'left',
    name: '归属机构',
    key: 'chargingSourceDesc'
  },
  {
    position: 'left',
    name: '充电车辆',
    key: 'carNumber'
  },
  {
    position: 'left',
    name: '订单类型',
    key: 'orderType',
    type: 'dic',
    dictType: DICT_TYPE.ORDER_TYPE
  },
  {
    position: 'left',
    name: '充电模式',
    key: 'chargingWay',
    type: 'dic',
    dictType: DICT_TYPE.CHARGING_WAY
  }
]

const currentErInfoRightLabel:IInfoData[] = [
  {
    position: 'right',
    name: '终端名称',
    key: 'equipmentConnectorName'
  },
  {
    position: 'right',
    name: '电桩功率',
    key: 'gunPower',
    unit: 'KW'
  },
  {
    position: "right",
    name: "电桩类型",
    key: "communicationType",
    type: 'dic',
    dictType: DICT_TYPE.ELECTRIC_TYPE
  },
  {
    position: 'right',
    name: '电站名称',
    key: 'stationName'
  },
  {
    position: 'right',
    name: '运营商',
    key: 'orgName'
  },
  // {
  //   position: 'right',
  //   name: '运营商属性',
  //   key: 'stationCompanyType'
  // }
]

const currentCardMonitor = [
  {
    icon: '/public/charging_pile/power.png',
    name: '电量',
    unit: 'kw.h',
    key: 'chargedPower'
  },
  {
    icon: '/public/charging_pile/soc.png',
    name: 'SOC',
    unit: '%',
    key: 'soc'
  },
  {
    icon: '/public/charging_pile/time.png',
    name: '时长',
    unit: '',
    key: 'chargeTime'
  }
]

export const { allSchemas } = useCrudSchemas(crudSchemas)
export const { allSchemas:  all2Schemas} = useCrudSchemas(crud2Schemas)

export {
  currentInfoLeftLabel,
  currentErInfoRightLabel,
  currentCardMonitor
}