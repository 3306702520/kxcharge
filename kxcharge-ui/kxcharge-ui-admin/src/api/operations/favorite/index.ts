import request from '@/config/axios'

// 站点收藏 VO
export interface FavoriteVO {
  id: number // 收藏编号
  userId: number // 用户编号
  stationId: number // 站点编号
}

// 站点收藏 API
export const FavoriteApi = {
  // 查询站点收藏分页
  getFavoritePage: async (params: any) => {
    return await request.get({ url: `/operations/favorite/page`, params })
  },

  // 查询站点收藏详情
  getFavorite: async (id: number) => {
    return await request.get({ url: `/operations/favorite/get?id=` + id })
  },

  // 新增站点收藏
  createFavorite: async (data: FavoriteVO) => {
    return await request.post({ url: `/operations/favorite/create`, data })
  },

  // 修改站点收藏
  updateFavorite: async (data: FavoriteVO) => {
    return await request.put({ url: `/operations/favorite/update`, data })
  },

  // 删除站点收藏
  deleteFavorite: async (id: number) => {
    return await request.delete({ url: `/operations/favorite/delete?id=` + id })
  },

  // 导出站点收藏 Excel
  exportFavorite: async (params) => {
    return await request.download({ url: `/operations/favorite/export-excel`, params })
  }
}