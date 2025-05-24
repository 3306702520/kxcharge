import request from '@/config/axios'

export interface PageRuleVO {
  id: number
  name: string
  status: number
  content: string
  type: string
}

// 查询页面规则配置列表
export const getPageRulePage = async (params) => {
  return await request.get({ url: `/member/page-rule/page`, params })
}

// 查询页面规则配置详情
export const getPageRule = async (id: number) => {
  return await request.get({ url: `/member/page-rule/get?id=` + id })
}

// 新增页面规则配置
export const createPageRule = async (data: PageRuleVO) => {
  return await request.post({ url: `/member/page-rule/create`, data })
}

// 修改页面规则配置
export const updatePageRule = async (data: PageRuleVO) => {
  return await request.put({ url: `/member/page-rule/update`, data })
}

// 删除页面规则配置
export const deletePageRule = async (id: number) => {
  return await request.delete({ url: `/member/page-rule/delete?id=` + id })
}

// 导出页面规则配置 Excel
export const exportPageRule = async (params) => {
  return await request.download({ url: `/member/page-rule/export-excel`, params })
}
