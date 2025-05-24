import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { dateFormatter } from '@/utils/formatTime'

// 表单校验
export const rules = reactive({
  protocolVersion: [required],
  isUsed: [required]
})

const crudSchemas = reactive<CrudSchema[]>([
  {
    label: '主键',
    field: 'id',
    isForm: false
  },
  {
    label: '模板名称',
    field: 'templateName',
    isSearch: true
  },
  {
    label: '更新版本',
    field: 'protocolVersion',
    isSearch: true,
    form: {
      component: 'InputNumber',
      value: 0
    }
  },
  {
    label: '是否使用',
    field: 'isUsed',
    isSearch: true,
    form: {
      component: 'Radio'
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
    label: '备注',
    field: 'remark',
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
