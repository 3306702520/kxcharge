import request from '@/config/axios'

export interface CostTemplateVO {
  id: number
  templateName: string,
  remark: string,
  orgId: string,
  costTemplatePriceTypeRespVOS: Array<any>,
  costTemplatePriceRespVOS: Array<any>
}

// 查询计费模板列表
export const getCostTemplatePage = async (params) => {
  return await request.get({ url: `/operations/cost-template/page`, params })
}

// 查询所有计费模板
export const getCostTemplateAll = async () => {
  return await request.get({ url: `/operations/cost-template/listAll` })
}

// 查询计费模板详情
export const getCostTemplate = async (id: number) => {
  return await request.get({ url: `/operations/cost-template/get?id=` + id })
}

// 新增计费模板
export const createCostTemplate = async (data: CostTemplateVO) => {
  return await request.post({ url: `/operations/cost-template/create`, data })
}

// 修改计费模板
export const updateCostTemplate = async (data: CostTemplateVO) => {
  return await request.put({ url: `/operations/cost-template/update`, data })
}

// 删除计费模板
export const deleteCostTemplate = async (id: number) => {
  return await request.delete({ url: `/operations/cost-template/delete?id=` + id })
}

// 导出计费模板 Excel
export const exportCostTemplate = async (params) => {
  return await request.download({ url: `/operations/cost-template/export-excel`, params })
}
