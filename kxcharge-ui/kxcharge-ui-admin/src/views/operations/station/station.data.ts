import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { dateFormatter } from '@/utils/formatTime'

// 表单校验
export const rules = reactive({
  orgId: [required],
  stationName: [required],
  areaCodeName: [required],
  address: [required],
  stationType: [required],
  stationStatus: [required],
  stationLng: [required],
  stationLat: [required],
  construction: [required],
  stationAddress: [required],
  isOccupationCost: [required],
  isBrake: [required],
  isEnableStart: [required],
  property: [required]
})

const crudSchemas = reactive<CrudSchema[]>([
  {
    label: '主键',
    field: 'id',
    isForm: false
  },
  {
    label: '运营商id',
    field: 'orgId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '电站名称',
    field: 'stationName',
    isSearch: true
  },
  {
    label: '国家code',
    field: 'countryCode',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '省code',
    field: 'provinceCode',
    isSearch: true
  },
  {
    label: '市code',
    field: 'cityCode',
    isSearch: true
  },
  {
    label: '区code',
    field: 'areaCode',
    isSearch: true
  },
  {
    label: '镇级code',
    field: 'townCode',
    isSearch: true
  },
  {
    label: '省市区中文名',
    field: 'areaCodeName',
    isSearch: true
  },
  {
    label: '地址',
    field: 'address',
    isSearch: true
  },
  {
    label: '服务电话',
    field: 'serviceTel',
    isSearch: true
  },
  {
    label: '电站类型',
    field: 'stationType',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '电站状态',
    field: 'stationStatus',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '经度',
    field: 'stationLng',
    isSearch: true
  },
  {
    label: '纬度',
    field: 'stationLat',
    isSearch: true
  },
  {
    label: '建设场所',
    field: 'construction',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '电站位置（地上、地下）',
    field: 'stationAddress',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '可用电容',
    field: 'capacitance',
    isSearch: true
  },
  {
    label: '分润设置',
    field: 'shareType',
    isSearch: true,
    form: {
      component: 'SelectV2'
    }
  },
  {
    label: '是否收取占用费',
    field: 'isOccupationCost',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '停车免费时间',
    field: 'parkBufferTime',
    formatter: dateFormatter,
    isSearch: true,
    search: {
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        type: 'daterange',
        defaultTime: [new Date('1 00:00:00'), new Date('1 23:59:59')]
      }
    },
    form: {
      component: 'DatePicker',
      componentProps: {
        type: 'datetime',
        valueFormat: 'x'
      }
    }
  },
  {
    label: '超额费用',
    field: 'overtimeMoney',
    isSearch: true
  },
  {
    label: '超额min',
    field: 'overtimeMin',
    isSearch: true
  },
  {
    label: '单次扣款限额',
    field: 'singleDeductionLimit',
    isSearch: true
  },
  {
    label: '道闸',
    field: 'isBrake',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '停车说明',
    field: 'parkExplain',
    isSearch: true
  },
  {
    label: '站点描述',
    field: 'stationDescribe',
    isSearch: true
  },
  {
    label: '站点服务设施',
    field: 'serviceSetup',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '周边基础设施',
    field: 'baseSetup',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '开放说明时间',
    field: 'openExplain',
    isSearch: true
  },
  {
    label: '主入口图',
    field: 'imgMainEntrance',
    isSearch: true
  },
  {
    label: '标志路劲',
    field: 'imgFlagPath',
    isSearch: true
  },
  {
    label: '电站全景',
    field: 'imgFullView',
    isSearch: true
  },
  {
    label: '电站特写',
    field: 'imgPilesFeature',
    isSearch: true
  },
  {
    label: '其他图片',
    field: 'imgOther',
    isSearch: true
  },
  {
    label: '资质',
    field: 'qualification',
    isSearch: true
  },
  {
    label: '备注说明',
    field: 'remarks',
    isSearch: true
  },
  {
    label: '是否启用电站',
    field: 'isEnableStart',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '客服电话',
    field: 'hotLine',
    isSearch: true
  },
  {
    label: '站点code',
    field: 'stationCode',
    isSearch: true
  },
  {
    label: '总电量',
    field: 'sumPower',
    isSearch: true
  },
  {
    label: '站点服务设施',
    field: 'stationServiceSets',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '单价关联id',
    field: 'costTemplateId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '场地属性',
    field: 'property',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '创建时间',
    field: 'createTime',
    formatter: dateFormatter,
    isSearch: true,
    search: {
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        type: 'daterange',
        defaultTime: [new Date('1 00:00:00'), new Date('1 23:59:59')]
      }
    },
    isForm: false
  },
  {
    label: '设备所属id',
    field: 'equipmentOwnerId',
    isSearch: true
  },
  {
    label: '操作',
    field: 'action',
    isForm: false
  }
])
export const { allSchemas } = useCrudSchemas(crudSchemas)
