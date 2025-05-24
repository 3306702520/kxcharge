// import { CrudSchema } from "@/hooks/web/useCrudSchemas";
// import { dateFormatter } from "@/utils/formatTime";
import { DICT_TYPE } from '@/utils/dict'

interface IInfoData {
  position?: 'right' | 'left';
  key: string;
  key2?: string;
  name: string;
  needPipe?: boolean;
  unit?: string,
  type?: 'dic' | 'span',
  dictType?: DICT_TYPE
}

interface IDetail {
  title: string;
  desc: string;
  key: string;
  keyTwo?: string;
}

const hisInfoLeftLabel: IInfoData[] = [
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
  // {
  //   position: 'left',
  //   name: '采购公司',
  //   key: 'purchaseCompany'
  // },
  {
    position: 'left',
    name: '充电VIN',
    key: 'chargeVin'
  },
  {
    position: 'left',
    name: '充电模式',
    key: 'chargingWay',
    type: 'dic',
    dictType: DICT_TYPE.CHARGING_WAY
  },
  // {
  //   position: 'left',
  //   name: '车辆备注',
  //   key: 'carRemark'
  // },

]

const hisperiodOfTime: IDetail[] = [
  {
    title: "准备充电",
    desc: "",
    key: "upTime",
    keyTwo: "UpTime"
  },
  {
    title: "开始充电",
    desc: "",
    key: "stTime",
    keyTwo: "StTime"
  },
  {
    title: "结束充电",
    desc: "",
    key: "edTime",
    keyTwo: "EdTime"

  },
  {
    title: "结算时间",
    desc: "",
    key: "settTime",
    keyTwo: "SettTime"

  }
]

const hisInfoRightLabel:IInfoData[] = [
  {
    position: 'right',
    name: '终端名称',
    key: 'equipmentConnectorName'
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
  // {
  //   position: 'right',
  //   name: '运营商属性',
  //   key: 'operatorProperty'
  // }
]

const hisexpenseDetail: IDetail[] = [
  {
    title: "电费",
    desc: "所有折扣前的电站标价计算出的电费费用=电度数*电站电费标价",
    key: "chargedAmount"
  },
  {
    title: "服务费",
    desc: "所有折扣前的电站标价计算出的服务费费用=电度数*电站服务费标价",
    key: "serviceAmount"
  },
  {
    title: "订单总金额",
    desc: "所有折扣前的电站标价计算出的费用=电费+服务费",
    key: "allAmount"
  }
]

// export const { allSchemas } = useCrudSchemas(crudSchemas)
// export const { allSchemas:  all2Schemas} = useCrudSchemas(crud2Schemas)

export {
  hisInfoLeftLabel,
  hisperiodOfTime,
  hisInfoRightLabel,
  hisexpenseDetail,
  IDetail,
  IInfoData
}
