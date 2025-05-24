import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { dateFormatter } from '@/utils/formatTime'

// 表单校验
export const rules = reactive({
  orgId: [required],
  stationId: [required],
  equipmentBrandId: [required],
  equipmentModelId: [required],
  version: [required],
  equipmentGunNumber: [required],
  equipmentName: [required],
  equipmentCode: [required],
  communicationType: [required],
  priceSet: [required],
  isEnableStart: [required],
  isOnline: [required],
  equipmentStandard: [required]
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
    label: '电站id',
    field: 'stationId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '校价id',
    field: 'operatorId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '校价时间',
    field: 'operatorTime',
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
    label: '电桩',
    field: 'equipmentOwnerId',
    isSearch: true
  },
  {
    label: '设备品牌id',
    field: 'equipmentBrandId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '设备型号id',
    field: 'equipmentModelId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '版本号',
    field: 'version',
    isSearch: true
  },
  {
    label: '枪数量',
    field: 'equipmentGunNumber',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '电桩名称',
    field: 'equipmentName',
    isSearch: true
  },
  {
    label: '设备编码',
    field: 'equipmentCode',
    isSearch: true
  },
  {
    label: '设备类型',
    field: 'communicationType',
    isSearch: true,
    form: {
      component: 'SelectV2'
    }
  },
  {
    label: '通信运营商',
    field: 'communicationOperator',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: 'sim卡',
    field: 'isSim',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: 'sim号码',
    field: 'numberSim',
    isSearch: true
  },
  {
    label: '车位号',
    field: 'parkNo',
    isSearch: true
  },
  {
    label: '有无车锁',
    field: 'parkLock',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '价格设置',
    field: 'priceSet',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '固件版本',
    field: 'firmwareVersion',
    isSearch: true
  },
  {
    label: '是否启用',
    field: 'isEnableStart',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '是否在线',
    field: 'isOnline',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '设备标准',
    field: 'equipmentStandard',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '充电模块品牌型号',
    field: 'chargingModuleBrandModel',
    isSearch: true
  },
  {
    label: '充电枪线品牌型号',
    field: 'chargingGunCableBrandModel',
    isSearch: true
  },
  {
    label: '电桩屏幕品牌',
    field: 'equipmentScreenBrand',
    isSearch: true
  },
  {
    label: '电桩程序',
    field: 'equipmentProcedure',
    isSearch: true
  },
  {
    label: '设备生产日期',
    field: 'productionDate',
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
    label: '设备型号类型',
    field: 'equipmentModelType',
    isSearch: true,
    form: {
      component: 'SelectV2'
    }
  },
  {
    label: '设置价格时间',
    field: 'setPriceTime',
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
    label: '通信协议版本号',
    field: 'protocolVersion',
    isSearch: true
  },
  {
    label: '网络链接类型',
    field: 'networkLinkType',
    isSearch: true,
    form: {
      component: 'SelectV2'
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
    label: '操作',
    field: 'action',
    isForm: false
  }
])
export const { allSchemas } = useCrudSchemas(crudSchemas)
