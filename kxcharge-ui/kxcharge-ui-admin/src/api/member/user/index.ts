import request from '@/config/axios'

export interface UserVO {
  id: number
  avatar: string | undefined
  birthday: number | undefined
  createTime: number | undefined
  loginDate: number | undefined
  loginIp: string
  mark: string
  mobile: string
  name: string | undefined
  nickname: string | undefined
  registerIp: string
  sex: number
  status: number
  areaId: number | undefined
  areaName: string | undefined
  levelName: string | null
  point: number | undefined | null
  totalPoint: number | undefined | null
  experience: number | null | undefined
  companyId: number
}

// 查询会员用户列表
export const getUserPage = async (params) => {
  return await request.get({ url: `/member/user/page`, params })
}

// 查询会员用户详情
export const getUser = async (id: number) => {
  return await request.get({ url: `/member/user/get?id=` + id })
}

// 修改会员用户
export const updateUser = async (data: UserVO) => {
  return await request.put({ url: `/member/user/update`, data })
}

// 修改会员用户等级
export const updateUserLevel = async (data: any) => {
  return await request.put({ url: `/member/user/update-level`, data })
}

// 修改会员用户积分
export const updateUserPoint = async (data: any) => {
  return await request.put({ url: `/member/user/update-point`, data })
}

// 修改会员用户余额
export const updateUserBalance = async (data: any) => {
  return await request.put({ url: `/member/user/update-balance`, data })
}

// 查询企业用户
export const getCompanyPage = async (params) => {
  return await request.get({ url: `/member/user/pageCompany`, params })
}

// 导出企业用户
export const exportCompanyUser = async (params) => {
  return await request.download({ url: `/member/user/export-excel`, params })
}

// 新增企业用户
export const addCompanyUser =  async (data) => {
  return await request.post({ url: '/member/user/create', data })
}

// 创建车辆信息
export const addCar =  async (data) => {
  return await request.post({ url: '/member/plate/create', data })
}

// 删除车辆信息
export const delCar = async (id: number) => {
  return await request.delete({ url: `/member/plate/delete?id=` + id })
}

// 查询用户名下所有车辆信息
export const getUserCar = async (userId: number) => {
  return await request.get({ url: `/member/plate/listAll?userId=` + userId })
}

// 设置默认车辆
export const updateDefault = async (data: any) => {
  return await request.put({ url: `/member/plate/updateDefault`, data })
}

// 获取车辆详情
export const getCar = async (id: number) => {
  return await request.get({ url: `/member/plate/get?id=` + id })
}

// 更新车辆信息
export const updateCar = async (data: any) => {
  return await request.put({ url: `/member/plate/update`, data })
}

// 修改企业用户密码
export const updatePassword = async (data: any) => {
  return await request.put({ url: `/member/user/update-password`, data })
}

// 更新企业用户状态
export const updateCompanyStatus = async (data: any) => {
  return await request.put({ url: `/member/user/updateStatus`, data })
}

// 删除企业用户信息
export const delCompanyUser = async (id: number) => {
  return await request.delete({ url: `/member/user/delete?id=` + id })
}