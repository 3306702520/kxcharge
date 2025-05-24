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

// 查询异常订单管理列表
export const getPendingErrorOrder = async (params) => {
  return await request.get({ url: `/operations/pendingOrder/error/page`, params })
}

// 查询待处理订单管理列表
export const getPendingOrderList = async (params) => {
  return await request.get({ url: `/operations/pendingOrder/page`, params })
}

// 查询订单管理详情
export const getOrder = async (id: number) => {
  return await request.get({ url: `/operations/pendingOrder/get?id=` + id })
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
