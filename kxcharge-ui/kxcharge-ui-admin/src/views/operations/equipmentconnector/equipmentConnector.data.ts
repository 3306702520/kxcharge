import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { dateFormatter } from '@/utils/formatTime'

// 表单校验
export const rules = reactive({
  stationId: [required],
  equipmentBrandId: [required],
  equipmentModelId: [required],
  orgId: [required],
  connectorCode: [required],
  connectorName: [required],
  isEnableStart: [required],
  workStatus: [required],
  version: [required],
  isAlonePrice: [required]
})

const crudSchemas = reactive<CrudSchema[]>([
  {
    label: '主键',
    field: 'id',
    isForm: false
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
    label: '电桩id',
    field: 'equipmentId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
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
    label: '运营商id',
    field: 'orgId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '操作人id',
    field: 'operatorId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '电枪编号',
    field: 'connectorCode',
    isSearch: true
  },
  {
    label: '电枪简短编号',
    field: 'shortcutCode',
    isSearch: true
  },
  {
    label: '电枪名称',
    field: 'connectorName',
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
    label: '工作状态',
    field: 'workStatus',
    isSearch: true,
    form: {
      component: 'Radio'
    }
  },
  {
    label: '操作时间',
    field: 'operationDate',
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
    label: '版本号',
    field: 'version',
    isSearch: true
  },
  {
    label: '是否单独价格',
    field: 'isAlonePrice',
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
    label: '操作',
    field: 'action',
    isForm: false
  }
])
export const { allSchemas } = useCrudSchemas(crudSchemas)
