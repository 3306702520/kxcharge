import request from '@/config/axios'

export const getOrderReport = async (params) => {
  console.log(`output->params`,params)  
  return await request.get({ url: `/report/order/statistics`, params })
}

export const exportOrderReport = async (params) => {
  return await request.download({ url: `/report/order/export-excel`, params })
}