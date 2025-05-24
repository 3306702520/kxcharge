import request from '@/config/axios'

// 首页轮播图 VO
export interface CarouselVO {
  id: number // 自增主键
  name: string // 轮播标题
  url: string // 轮播图片
  jumpType: number // 跳转类型
  page: string // 跳转路径
  status: number // 状态
}

// 首页轮播图 API
export const CarouselApi = {
  // 查询首页轮播图分页
  getCarouselPage: async (params: any) => {
    return await request.get({ url: `/member/carousel/page`, params })
  },

  // 查询首页轮播图详情
  getCarousel: async (id: number) => {
    return await request.get({ url: `/member/carousel/get?id=` + id })
  },

  // 新增首页轮播图
  createCarousel: async (data: CarouselVO) => {
    return await request.post({ url: `/member/carousel/create`, data })
  },

  // 修改首页轮播图
  updateCarousel: async (data: CarouselVO) => {
    return await request.put({ url: `/member/carousel/update`, data })
  },

  // 删除首页轮播图
  deleteCarousel: async (id: number) => {
    return await request.delete({ url: `/member/carousel/delete?id=` + id })
  },

  // 导出首页轮播图 Excel
  exportCarousel: async (params) => {
    return await request.download({ url: `/member/carousel/export-excel`, params })
  },
}