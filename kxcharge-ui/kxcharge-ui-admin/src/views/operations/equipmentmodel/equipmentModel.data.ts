import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { dateFormatter } from '@/utils/formatTime'

// 表单校验
export const rules = reactive({
  orgId: [required],
  equipmentBrandId: [required],
  equipmentModel: [required],
  number: [required],
  type: [required],
  power: [required]
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
    label: '品牌id',
    field: 'equipmentBrandId',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '电站型号',
    field: 'equipmentModel',
    isSearch: true
  },
  {
    label: '充电枪数量',
    field: 'number',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '电桩类型',
    field: 'type',
    isSearch: true,
    form: {
      component: 'SelectV2'
    }
  },
  {
    label: '设备图片',
    field: 'img',
    isSearch: true
  },
  {
    label: '备注说明',
    field: 'remark',
    isSearch: true
  },
  {
    label: '电桩功率',
    field: 'power',
    isSearch: true
  },
  {
    label: '辅助电源',
    field: 'supPower',
    isSearch: true
  },
  {
    label: '枪线温度预警',
    field: 'gunTemperatureWarn',
    isSearch: true
  },
  {
    label: '枪线温度断充',
    field: 'gunTemperatureStop',
    isSearch: true
  },
  {
    label: '输入电压',
    field: 'inputv',
    isSearch: true
  },
  {
    label: '最大电压',
    field: 'maxv',
    isSearch: true
  },
  {
    label: '最小电压',
    field: 'minv',
    isSearch: true
  },
  {
    label: '最大电流',
    field: 'maxa',
    isSearch: true
  },
  {
    label: '最小电流',
    field: 'mina',
    isSearch: true
  },
  {
    label: '恒功率输出电压区间大',
    field: 'maxp',
    isSearch: true
  },
  {
    label: '恒功率输出电压区间小',
    field: 'minp',
    isSearch: true
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
