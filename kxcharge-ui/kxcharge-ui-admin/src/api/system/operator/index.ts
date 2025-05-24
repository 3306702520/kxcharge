import request from '@/config/axios'

// 运营商 VO
export interface OperatorVO {
  id: number // 主键
  orgName: string // 企业名称
  operatorName: string // 运营商名称
  address: string // 企业注册地址
  code: string // 统一社会信用代码
  cityCode: string // 城市code
  provinceCode: string // 省份code
  cityName: string // 城市名称
  signStartTime: Date // 签约开始时间
  signEndTime: Date // 签约结束时间
  businessLogo: string // 企业图标
  remark: string // 备注说明
  codeAbbr: string // license截取
  contactUser: string // 联系人
  contactNum: string // 联系方式
  contactAddress: string // 联系地址
  email: string // 联系邮箱
  businessLicense: string // 营业执照
  isFrozen: boolean // 状态
}

// 运营商 API

// 删除运营商
export const deleteOperator= async (id: number) => {
  return await request.delete({ url: `/system/operations/delete?id=` + id })
}

// 导出运营商 Excel
export const exportOperator= async (params) => {
  return await request.download({ url: `/system/operations/export-excel`, params })
}

// 查询运营商详情
export const getOperator= async (id: number) => {
  return await request.get({ url: `/system/operations/get?id=` + id })
}

// 查询运营商分页
export const getOperatorPage= async (params: any) => {
  return await request.get({ url: `/system/operations/page`, params })
}


// 查询所有运营商列表
export const getOperatorAll = async () => {
  return await request.get({ url: '/system/operations/listAll' })
}


// 新增运营商
export const createOperator= async (data: OperatorVO) => {
    return await request.post({ url: `/system/operations/create`, data })
  }

// 修改运营商
export const updateOperator= async (data: OperatorVO) => {
  return await request.put({ url: `/system/operations/update`, data })
}

// 修改运营商状态
export const changeOperatorStatus = async (data) => {
  return await request.post({ url: '/system/operations/updateStatus', data })
}
