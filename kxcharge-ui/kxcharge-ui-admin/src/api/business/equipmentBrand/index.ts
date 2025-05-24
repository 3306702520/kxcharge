import request from '@/config/axios'

export interface EquipmentBrandVO {
  id: number
  brandName: string
  orgId: number
}

// 查询设备品牌管理列表
export const getEquipmentBrandPage = async (params) => {
  return await request.get({ url: `/operations/equipment-brand/page`, params })
}

// 查询所有设备品牌列表
export const getEquipmentBrandAll = async (params = null) => {
  return await request.get({ url: `/operations/equipment-brand/listAll`, params })
}

// 查询设备品牌管理详情
export const getEquipmentBrand = async (id: number) => {
  return await request.get({ url: `/operations/equipment-brand/get?id=` + id })
}

// 新增设备品牌管理
export const createEquipmentBrand = async (data: EquipmentBrandVO) => {
  return await request.post({ url: `/operations/equipment-brand/create`, data })
}

// 修改设备品牌管理
export const updateEquipmentBrand = async (data: EquipmentBrandVO) => {
  return await request.put({ url: `/operations/equipment-brand/update`, data })
}

// 删除设备品牌管理
export const deleteEquipmentBrand = async (id: number) => {
  return await request.delete({ url: `/operations/equipment-brand/delete?id=` + id })
}

// 导出设备品牌管理 Excel
export const exportEquipmentBrand = async (params) => {
  return await request.download({ url: `/operations/equipment-brand/export-excel`, params })
}
