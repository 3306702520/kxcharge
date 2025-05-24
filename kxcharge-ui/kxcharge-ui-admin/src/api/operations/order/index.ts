import request from '@/config/axios'

export interface OrderVO {
  id: number
  stationId: string
  stationName: string
  orderStatus: string
  orderType: string
  orgId: number
  companyName: string
  companyConsume: number
  personalConsume: number
  companyId: number
  chargedAmount: number
  chargedTime: string
  cardNo: string
  payer: string
  orgChargedAmount: number
  orgParkedAmount: number
  orgAllAmount: string
  orgElecActualIncome: number
  orgServActualIncome: number
  gunName: string
  pilePower: string
  payType: string
  gunId: string
  tradeSeq: string
  tradeStatus: string
  tradeTime: Date
  chargedPower: number
  nickName: string
  userAccount: string
  parkedDiscountAmount: string
  inPay: number
  startSoc: string
  endSoc: string
  income: number
  redPacketConsume: number
  parkedAmount: number
  carType: string
  orgIncome: number
  startTime: Date
  stationOrgType: string
  plateNumber: string
  chargingSource: string
  allAmount: number
  chargedDiscountAmount: string
  pileType: string
  seqNumber: string
  endTime: Date
  settleType: string
  carModel: string
}

// 查询订单管理列表
export const getOrderPage = async (params) => {
  return await request.get({ url: `/operations/order/page`, params })
}

// 查询订单管理详情
export const getOrder = async (id: number) => {
  return await request.get({ url: `/operations/order/get?id=` + id })
}

// 新增订单管理
export const createOrder = async (data: OrderVO) => {
  return await request.post({ url: `/operations/order/create`, data })
}

// 修改订单管理
export const updateOrder = async (data: OrderVO) => {
  return await request.put({ url: `/operations/order/update`, data })
}

// 删除订单管理
export const deleteOrder = async (id: number) => {
  return await request.delete({ url: `/operations/order/delete?id=` + id })
}

// 导出订单管理 Excel
export const exportOrder = async (params) => {
  return await request.download({ url: `/operations/order/export-excel`, params })
}

// 获取实时订单列表
export const getRealtimeOrderList = async (params) => {
  return await request.get({ url: `/operations/currentOrder/list`, params })
}

// 获取实时订单详情
export const getRealtimeOrderInfo = async (id) => {
  return await request.get({ url: `/operations/currentOrder/selectById?seqNumber=` + id })
}

// 获取历史订单分页
export const getHisOrderPage = async (params) => {
  return await request.get({ url: `/operations/historyOrder/page`, params })
}

// 订单详情
export const getHisOrderInfo = async (seqNumber) => {
  return await request.get({ url: `/operations/historyOrder/get?seqNumber=` + seqNumber })
}

// 重推
export const orderRethrust = async (id: string) => {
  return await request.post({ url: `/operations/backGroundProcess/orderRethrust?id=` + id })
}
