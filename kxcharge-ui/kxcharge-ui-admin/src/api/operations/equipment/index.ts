import request from '@/config/axios'

export interface EquipmentVO {
  id: number
  orgId: number
  equipmentOwnerId: string
  equipmentBrandId: number
  equipmentModelId: number
  version: string
  equipmentGunNumber: number
  equipmentName: string
  equipmentCode: string
  communicationType: number
  communicationOperator: string
  isSim: boolean
  numberSim: string
  parkNo: string
  parkLock: boolean
  priceSet: number
  firmwareVersion: string
  isEnableStart: boolean
  isOnline: boolean
  equipmentStandard: number
  chargingModuleBrandModel: string
  chargingGunCableBrandModel: string
  equipmentScreenBrand: string
  equipmentProcedure: string
  productionDate: localdate
  equipmentModelType: string
  setPriceTime: Date
  stationId: number
  operatorId: number
}

// 查询电桩管理列表
export const getEquipmentPage = async (params) => {
  return await request.get({ url: `/operations/equipment/page`, params })
}

// 查询电桩管理详情
export const getEquipment = async (id: number) => {
  return await request.get({ url: `/operations/equipment/get?id=` + id })
}

// 新增电桩管理
export const createEquipment = async (data: EquipmentVO) => {
  return await request.post({ url: `/operations/equipment/create`, data })
}

// 修改电桩管理
export const updateEquipment = async (data: EquipmentVO) => {
  return await request.put({ url: `/operations/equipment/update`, data })
}

// 删除电桩管理
export const deleteEquipment = async (id: number) => {
  return await request.delete({ url: `/operations/equipment/delete?id=` + id })
}

// 导出电桩管理 Excel
export const exportEquipment = async (params) => {
  return await request.download({ url: `/operations/equipment/export-excel`, params })
}

// 校时或校价
export const proof = async (id: string, type: 'time' | 'price') => {
  return await request.get({ url: `/operations/equipment/proof-${type}?id=` + id })
}

// 远程升级或重启
export const remote = async (id: string, type: 'restart' | 'upgrade') => {
  return await request.get({ url: `/operations/equipment/remote-${type}?id=` + id })
}
