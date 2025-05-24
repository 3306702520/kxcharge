import request from '@/config/axios'

export interface FlowVO {
  id: number
  orgId: string
  name: string
  startTime: localdate
  endTime: localdate
  chargeDiscount: number
  serviceDiscount: number
  stationIds: string
  settlePriceType: number
  stationPriceType: number
  status: number
}

// 查询流量方案列表
export const getFlowPage = async (params) => {
  return await request.get({ url: `/interconnected/flow/page`, params })
}

// 查询流量方案详情
export const getFlow = async (id: number) => {
  return await request.get({ url: `/interconnected/flow/get?id=` + id })
}

// 新增流量方案
export const createFlow = async (data: FlowVO) => {
  return await request.post({ url: `/interconnected/flow/create`, data })
}

// 修改流量方案
export const updateFlow = async (data: FlowVO) => {
  return await request.put({ url: `/interconnected/flow/update`, data })
}

// 删除流量方案
export const deleteFlow = async (id: number) => {
  return await request.delete({ url: `/interconnected/flow/delete?id=` + id })
}

// 导出流量方案 Excel
export const exportFlow = async (params) => {
  return await request.download({ url: `/interconnected/flow/export-excel`, params })
}
