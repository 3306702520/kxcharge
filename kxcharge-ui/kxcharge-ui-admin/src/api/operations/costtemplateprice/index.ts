import request from '@/config/axios'

export interface CostTemplatePriceVO {
                    id: number
                    costTemplateId: number
                    protocolVersion: string
                    orgId: number
                    startTime: number
                    endTime: number
                    startTimeStr: string
                    endTimeStr: string
                    priceTypeId: string
}

// 查询计费模板价格时段列表
export const getCostTemplatePricePage = async (params) => {
  return await request.get({ url: '/operations/cost-template-price/page', params })
}

// 查询计费模板价格时段详情
export const getCostTemplatePrice = async (id: number) => {
  return await request.get({ url: '/operations/cost-template-price/get?id=' + id })
}

// 新增计费模板价格时段
export const createCostTemplatePrice = async (data: CostTemplatePriceVO) => {
  return await request.post({ url: '/operations/cost-template-price/create', data })
}

// 修改计费模板价格时段
export const updateCostTemplatePrice = async (data: CostTemplatePriceVO) => {
  return await request.put({ url: '/operations/cost-template-price/update', data })
}

// 删除计费模板价格时段
export const deleteCostTemplatePrice = async (id: number) => {
  return await request.delete({ url: '/operations/cost-template-price/delete?id=' + id })
}

// 导出计费模板价格时段 Excel
export const exportCostTemplatePriceApi = async (params) => {
  return await request.download({ url: '/operations/cost-template-price/export-excel', params })
}