import request from '@/config/axios'

export const getStationReport = async (params) => {
  console.log(`output->params`,params)  
  return await request.get({ url: `/report/station/powerStationStatistics`, params })
}

export const exportStationReport = async (params) => {
  return await request.download({ url: `/report/station/export-excel`, params })
}