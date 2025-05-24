import request from '@/config/axios'

export interface EntranceVO {
  id: number
  orgId: string
  name: string
  secondaryPricingType: number
  chargeDiscount: number
  serviceDiscount: number
  startTime: localdate
  endTime: localdate
  chargeFixedDifference: string
  serviceFixedDifference: string
  status: number
}

// 查询入口方案列表
export const getEntrancePage = async (params) => {
  return await request.get({ url: `/interconnected/entrance/page`, params })
}

// 查询入口方案详情
export const getEntrance = async (id: number) => {
  return await request.get({ url: `/interconnected/entrance/get?id=` + id })
}

// 新增入口方案
export const createEntrance = async (data: EntranceVO) => {
  return await request.post({ url: `/interconnected/entrance/create`, data })
}

// 修改入口方案
export const updateEntrance = async (data: EntranceVO) => {
  return await request.put({ url: `/interconnected/entrance/update`, data })
}

// 删除入口方案
export const deleteEntrance = async (id: number) => {
  return await request.delete({ url: `/interconnected/entrance/delete?id=` + id })
}

// 导出入口方案 Excel
export const exportEntrance = async (params) => {
  return await request.download({ url: `/interconnected/entrance/export-excel`, params })
}
