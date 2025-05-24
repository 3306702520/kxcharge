import request from '@/config/axios'

export const getMachineOrderPage = async (params) => {
  return await request.get({ url: '/sell/vending-order/page', params })
}