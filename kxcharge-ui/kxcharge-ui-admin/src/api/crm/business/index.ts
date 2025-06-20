import request from '@/config/axios'
import { TransferReqVO } from '@/api/crm/customer'

export interface BusinessVO {
  id: number
  name: string
  statusTypeId: number
  statusId: number
  contactNextTime: Date
  customerId: number
  dealTime: Date
  price: number
  discountPercent: number
  productPrice: number
  remark: string
  ownerUserId: number
  roUserIds: string
  rwUserIds: string
  endStatus: number
  endRemark: string
  contactLastTime: Date
  followUpStatus: number
}

// 查询 CRM 商机列表
export const getBusinessPage = async (params) => {
  return await request.get({ url: `/crm/operations/page`, params })
}

// 查询 CRM 商机列表，基于指定客户
export const getBusinessPageByCustomer = async (params) => {
  return await request.get({ url: `/crm/operations/page-by-customer`, params })
}

// 查询 CRM 商机详情
export const getBusiness = async (id: number) => {
  return await request.get({ url: `/crm/operations/get?id=` + id })
}

// 获得 CRM 商机列表（精简）
export const getSimpleBusinessList = async () => {
  return await request.get({ url: `/crm/operations/simple-all-list` })
}

// 新增 CRM 商机
export const createBusiness = async (data: BusinessVO) => {
  return await request.post({ url: `/crm/operations/create`, data })
}

// 修改 CRM 商机
export const updateBusiness = async (data: BusinessVO) => {
  return await request.put({ url: `/crm/operations/update`, data })
}

// 删除 CRM 商机
export const deleteBusiness = async (id: number) => {
  return await request.delete({ url: `/crm/operations/delete?id=` + id })
}

// 导出 CRM 商机 Excel
export const exportBusiness = async (params) => {
  return await request.download({ url: `/crm/operations/export-excel`, params })
}

// 联系人关联商机列表
export const getBusinessPageByContact = async (params) => {
  return await request.get({ url: `/crm/operations/page-by-contact`, params })
}

// 获得 CRM 商机列表
export const getBusinessListByIds = async (val: number[]) => {
  return await request.get({ url: '/crm/operations/list-by-ids', params: { ids: val.join(',') } })
}

// 商机转移
export const transferBusiness = async (data: TransferReqVO) => {
  return await request.put({ url: '/crm/operations/transfer', data })
}
