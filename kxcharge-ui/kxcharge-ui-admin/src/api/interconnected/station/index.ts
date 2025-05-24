import request from '@/config/axios'

export interface StationVO {
  id: string
  content: string
  townCode: string
  operationId: string
  stationId: string
  stationName: string
  equipmentOwnerId: string
  countryCode: string
  areaCode: string
  address: string
  serviceTel: string
  stationType: number
  stationStatus: number
  parkNums: number
  stationLng: number
  stationLat: number
  construction: number
  pictures: string
}

// 查询互联互通电站列表
export const getStationPage = async (params) => {
  return await request.get({ url: `/interconnected/station/page`, params })
}

// 查询互联互通电站详情
export const getStation = async (id: number) => {
  return await request.get({ url: `/interconnected/station/get?id=` + id })
}

// 新增互联互通电站
export const createStation = async (data: StationVO) => {
  return await request.post({ url: `/interconnected/station/create`, data })
}

// 修改互联互通电站
export const updateStation = async (data: StationVO) => {
  return await request.put({ url: `/interconnected/station/update`, data })
}

// 删除互联互通电站
export const deleteStation = async (id: number) => {
  return await request.delete({ url: `/interconnected/station/delete?id=` + id })
}

// 导出互联互通电站 Excel
export const exportStation = async (params) => {
  return await request.download({ url: `/interconnected/station/export-excel`, params })
}
