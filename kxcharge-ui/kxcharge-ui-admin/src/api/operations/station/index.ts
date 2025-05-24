import request from '@/config/axios'

export interface StationVO {
  id: number
  orgId: number
  stationName: string
  countryCode: string
  provinceCode: string
  cityCode: string
  townCode: string
  areaCode: string
  areaCodeName: string
  address: string
  serviceTel: string
  stationType: string
  stationStatus: boolean
  stationLng: number
  stationLat: number
  construction: string
  stationAddress: boolean
  capacitance: string
  shareType: string
  isOccupationCost: boolean
  parkBufferTime: string
  overtimeMoney: number
  overtimeMin: string
  singleDeductionLimit: number
  isBrake: boolean
  parkExplain: string
  stationDescribe: string
  serviceSetup: string
  baseSetup: string
  openExplain: string
  imgMainEntrance: string
  imgFlagPath: string
  imgFullView: string
  imgPilesFeature: string
  imgOther: string
  qualification: string
  remarks: string
  isEnableStart: boolean
  createBy: string
  updateBy: string
  hotLine: string
  stationCode: string
  sumPower: number
  stationServiceSets: string
  costTemplateId: number
  property: boolean
}

// 查询站点信息列表
export const getStationPage = async (params) => {
  return await request.get({ url: `/operations/station/page`, params })
}

// 查询全部站点信息列表
export const getStationAll = async () => {
  return await request.get({ url: `/operations/station/listAll` })
}

// 查询单个运营商的站点信息
export const getOperatorStation = async (orgId: number) => {
  return await request.get({ url: `/operations/station/listAllByOrgId?orgId=${orgId}` })
}

// 查询站点信息详情
export const getStation = async (id: number) => {
  return await request.get({ url: `/operations/station/get?id=` + id })
}

// 新增站点信息
export const createStation = async (data: StationVO) => {
  return await request.post({ url: `/operations/station/create`, data })
}

// 修改站点信息
export const updateStation = async (data: StationVO) => {
  return await request.put({ url: `/operations/station/update`, data })
}

// 删除站点信息
export const deleteStation = async (id: number) => {
  return await request.delete({ url: `/operations/station/delete?id=` + id })
}

// 导出站点信息 Excel
export const exportStation = async (params) => {
  return await request.download({ url: `/operations/station/export-excel`, params })
}
