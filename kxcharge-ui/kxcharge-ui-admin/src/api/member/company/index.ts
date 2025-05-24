import request from '@/config/axios'
import { ArrowDown } from '@element-plus/icons-vue/dist/types'

export interface CompanyVO {
  id: number
  companyName: string
  orgId: string
  address: string
  code: string
  signStartTime: Date
  signEndTime: Date
  remark: string
  contactUser: string
  contactNum: string
  contactAddress: string
  email: string
  businessLicense: string
  isFrozen: boolean
}

// 查询企业管理列表
export const getCompanyPage = async (params) => {
  return await request.get({ url: `/member/company/page`, params })
}

// 查询企业管理详情
export const getCompany = async (id: number) => {
  return await request.get({ url: `/member/company/get?id=` + id })
}

// 新增企业管理
export const createCompany = async (data: CompanyVO) => {
  return await request.post({ url: `/member/company/create`, data })
}

// 修改企业管理
export const updateCompany = async (data: CompanyVO) => {
  return await request.put({ url: `/member/company/update`, data })
}

// 删除企业管理
export const deleteCompany = async (id: number) => {
  return await request.delete({ url: `/member/company/delete?id=` + id })
}

// 导出企业管理 Excel
export const exportCompany = async (params) => {
  return await request.download({ url: `/member/company/export-excel`, params })
}

// 修改企业状态
export const updateStatus = async (data) => {
  return await request.post({ url: '/member/company/updateStatus', data })
}

// 查询企业金额变更
export const getCompanyRecord = async (params) => {
  return await request.get({ url: `/member/company-record/page`, params })
}

// 导出企业金额变更
export const exportRecord = async (params) => {
  return await request.download({ url: `/member/company-record/export-excel`, params })
}

// 企业账户变更金额
export const companyCreateMoney = async (data) => {
  return await request.put({ url: `/member/company-record/create-money`, data })
}

// 查询企业列表全部
export const getCompanyListAll = async () => {
  return await request.get({ url: `/member/company/listAll` })
}

// 删除企业用户
export const delCompanyUser = async (id: number) => {
  return await request.delete({ url: `/member/user/delete?id=` + id })
}