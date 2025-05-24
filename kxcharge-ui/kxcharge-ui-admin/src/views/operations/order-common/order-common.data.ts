import { CrudSchema } from "@/hooks/web/useCrudSchemas"
import * as CompanyApi from '@/api/member/company/index'
import * as OperatorApi from '@/api/system/operator'


interface IChart {
  bsmName: string;
  bsmKey: string;
  realName?: string;
  realKey?: string
}

const volSeries: IChart = {
  bsmName: '需求电压',
  bsmKey: '',
  realName: '实际电压',
  realKey: ''
}

const curSeries: IChart = {
  bsmName: '需求电流',
  bsmKey: '',
  realName: '实际电压',
  realKey: ''
}

const tempSeries: IChart = {
  bsmName: '最高动力蓄电池温度',
  bsmKey: ''
}

const socSeries: IChart = {
  bsmName: 'SOC',
  bsmKey: ''
}

const crudSchemas = reactive<CrudSchema[]>([
  {
    label: '上送数据',
    field: '1'
  },
  {
    label: '电压(V)',
    field: '1'
  },
  {
    label: '电流(A)',
    field: '1'
  },
  {
    label: 'soc(%)',
    field: '1'
  },
  {
    label: '电量(度)',
    field: '1'
  },
  {
    label: '计损电量(度)',
    field: '1'
  },
  {
    label: '剩余时间',
    field: '1'
  },
  {
    label: '已充时间',
    field: '1'
  },
  {
    label: '充电状态',
    field: '1'
  },
  {
    label: '枪线温度(℃)',
    field: '1'
  },
  {
    label: '枪链接状态',
    field: '1'
  }
])

const crud2Schemas = reactive<CrudSchema[]>([
  {
    label: '电池组最高温度(℃)',
    field: '1'
  },
  {
    label: '最高温度监测点编号',
    field: '1'
  },
  {
    label: '电池组最低温度(℃)',
    field: '1'
  },
  {
    label: '最低温度监测点编号',
    field: '1'
  }
])

const crud3Schemas = reactive<CrudSchema[]>([
  {
    label: '最高单体电压',
    field: '1'
  },
  {
    label: '最高单体电压检测点编号',
    field: '1'
  },
  {
    label: '需求电压(V)',
    field: '1'
  },
  {
    label: '需求电流(A)',
    field: '1'
  }
])

const getCompanyList = async () => {
  const data = await CompanyApi.getCompanyListAll()
  const companyList = []
  data.forEach((item) => {
    // @ts-ignore
    companyList.push({
      value: item.id,
      label: item.companyName
    })
  })
  return companyList
}

const getOrgList = async () => {
  const data = await OperatorApi.getOperatorAll()
  const orgList= []
  data.forEach((item) => {
    // @ts-ignore
    orgList.push({
      value: item.id,
      label: item.orgName
    })
  })
  return orgList
}

export {
  volSeries,
  curSeries,
  tempSeries,
  socSeries,
  IChart,
  getCompanyList,
  getOrgList
}
export const { allSchemas: realtimeColumns } = useCrudSchemas(crudSchemas)
export const { allSchemas: bmsOutColumns } = useCrudSchemas(crud2Schemas)
export const { allSchemas: bmsInfoColumns } = useCrudSchemas(crud3Schemas)


