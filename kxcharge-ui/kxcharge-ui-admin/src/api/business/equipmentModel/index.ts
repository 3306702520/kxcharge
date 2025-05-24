import request from '@/config/axios'

export interface EquipmentModelVO {
  id: number
  orgId: number
  equipmentBrandId: number
  equipmentModel: string
  number: number
  type: number
  img: string
  remark: string
  power: number
  supPower: number
  gunTemperatureWarn: number
  gunTemperatureStop: number
  inputv: number
  maxv: number
  minv: number
  maxa: number
  mina: number
  maxp: number
  minp: number
}

// 查询设备型号管理列表
export const getEquipmentModelPage = async (params) => {
  return await request.get({ url: `/operations/equipment-model/page`, params })
}

// 查询设备型号管理详情
export const getEquipmentModel = async (id: number) => {
  return await request.get({ url: `/operations/equipment-model/get?id=` + id })
}

// 新增设备型号管理
export const createEquipmentModel = async (data: EquipmentModelVO) => {
  return await request.post({ url: `/operations/equipment-model/create`, data })
}

// 修改设备型号管理
export const updateEquipmentModel = async (data: EquipmentModelVO) => {
  return await request.put({ url: `/operations/equipment-model/update`, data })
}

// 删除设备型号管理
export const deleteEquipmentModel = async (id: number) => {
  return await request.delete({ url: `/operations/equipment-model/delete?id=` + id })
}

// 导出设备型号管理 Excel
export const exportEquipmentModel = async (params) => {
  return await request.download({ url: `/operations/equipment-model/export-excel`, params })
}

// 获取品牌型号树
export const getBrandModelTree =async () => {
  return await request.get({ url: '/operations/equipment-brand/equipmentTwoStage' })
}
