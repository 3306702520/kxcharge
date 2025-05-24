import request from '@/config/axios'

export interface ICargoLangs {
  name: string,
  remark: string,
  content: Array<any>,
  status: number
}

/**
 * 新增货道
 * @param data 
 * @returns 
 */
export const  createCargoLangs = async (data) => {
  return await request.post({ url: '/sell/vending-slot-type/create', data})
}

/**
 * 获取货道列表
 * @param params 
 * @returns 
 */
export const getCargoLansList = async (params) => {
  return await request.get({ url: '/sell/vending-slot-type/page', params })
}

/**
 * 获取货道详情
 * @param id 
 * @returns 
 */
export const getCargoLansInfo = async (id: number) => {
  return await request.get({ url: '/sell/vending-slot-type/get?id=' + id })
}

/**
 * 修改货道
 * @param data 
 * @returns 
 */
export const updateCargoLangs = async (data) => {
  return await request.put({ url: '/sell/vending-slot-type/update', data })
}

/**
 * 删除货道
 * @param id 
 * @returns
 */
export const delCargoLangs = async (id: number) => {
  return await request.delete({ url: '/sell/vending-slot-type/delete?id=' + id })
}

export const getCargoLangsAll = async () => {
  return await request.get({ url: '/sell/vending-slot-type/listByEnable' })
}
