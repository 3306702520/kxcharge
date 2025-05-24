import request from '@/config/axios'

export interface VendingSlotVO {
  id: number
  productId: number
  machineId: number
  floorNumber: number
  columnNumber: number
  productMaxNumber: number
  productNumber: number
}

// 查询自动售货机货道列表
export const getVendingSlotPage = async (params) => {
  return await request.get({ url: `/sell/vending-slot/page`, params })
}

// 查询自动售货机货道详情
export const getVendingSlot = async (id: number) => {
  return await request.get({ url: `/sell/vending-slot/get?id=` + id })
}

// 新增自动售货机货道
export const createVendingSlot = async (data: VendingSlotVO) => {
  return await request.post({ url: `/sell/vending-slot/create`, data })
}

// 修改自动售货机货道
export const updateVendingSlot = async (data) => {
  return await request.put({ url: `/sell/vending-slot/update`, data })
}

// 删除自动售货机货道
export const deleteVendingSlot = async (id: number) => {
  return await request.delete({ url: `/sell/vending-slot/delete?id=` + id })
}

// 导出自动售货机货道 Excel
export const exportVendingSlot = async (params) => {
  return await request.download({ url: `/sell/vending-slot/export-excel`, params })
}

export const putVendingSlot = async (data) => {
  return await request.put({ url: `/sell/vending-slot/put`, data })
}
