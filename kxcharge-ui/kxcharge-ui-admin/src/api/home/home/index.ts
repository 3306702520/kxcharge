import request from '@/config/axios'

// 获取实时枪数据
export const getGunData = async () => {
  return await request.get({ url: '/operations/home/gunData' })
}

// 获取昨日今日数据
export const getChargeData = async () => {
  return await request.get({ url: '/operations/home/chargeData' })
}

// 获取时段分布
export const getTimeDistribution = async (params) => {
  return await request.get({url: '/operations/home/timeDistribution', params})
}

// 获取时段分布
export const getTerminalOperation = async (params) => {
  return await request.get({url: '/operations/home/terminalOperationData', params})
}
