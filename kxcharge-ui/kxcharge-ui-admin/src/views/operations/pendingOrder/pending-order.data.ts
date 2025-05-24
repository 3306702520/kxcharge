import { IInfoData } from "../order/order.data";
import { DICT_TYPE } from '@/utils/dict'

const abnormalInfoLeftLabel: IInfoData[] = [
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
    name: '充电卡号',
    key: 'cardNo'
  },
  {
    position: 'left',
    name: '归属机构',
    key: 'companyName'
  },
  {
    position: 'left',
    name: '充电车辆',
    key: 'carNumber'
  },
  {
    position: 'left',
    name: '充电VIN码',
    key: 'vinCode'
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

const abnormalInfoRightLabel:IInfoData[] = [
  {
    position: 'right',
    name: '终端名称',
    key: 'equipmentConnectorName'
  },
  {
    position: 'right',
    name: '电桩品牌',
    key: 'pileBrand'
  },
  {
    position: 'right',
    name: '电桩功率',
    key: 'gunPower'
  },
  {
    position: 'right',
    name: '电站名称',
    key: 'stationName'
  },
  {
    position: 'right',
    name: '运营商',
    key: 'operatorName'
  },
  {
    position: 'right',
    name: '运营商属性',
    key: 'stationCompanyType'
  }
]


export {
  abnormalInfoLeftLabel,
  abnormalInfoRightLabel
}

