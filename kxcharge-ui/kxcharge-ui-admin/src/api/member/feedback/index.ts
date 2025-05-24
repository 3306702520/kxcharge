import request from '@/config/axios'

export interface FeedbackVO {
  id: number
  userId: number
  phone: string
  name: string
  type: string
  img: string
  status: number
  content: string
}

// 查询客户意见反馈	列表
export const getFeedbackPage = async (params) => {
  return await request.get({ url: `/member/feedback/page`, params })
}

// 查询客户意见反馈	详情
export const getFeedback = async (id: number) => {
  return await request.get({ url: `/member/feedback/get?id=` + id })
}

// 新增客户意见反馈	
export const createFeedback = async (data: FeedbackVO) => {
  return await request.post({ url: `/member/feedback/create`, data })
}

// 修改客户意见反馈	
export const updateFeedback = async (data: FeedbackVO) => {
  return await request.put({ url: `/member/feedback/update`, data })
}

// 删除客户意见反馈	
export const deleteFeedback = async (id: number) => {
  return await request.delete({ url: `/member/feedback/delete?id=` + id })
}

// 导出客户意见反馈	 Excel
export const exportFeedback = async (params) => {
  return await request.download({ url: `/member/feedback/export-excel`, params })
}
