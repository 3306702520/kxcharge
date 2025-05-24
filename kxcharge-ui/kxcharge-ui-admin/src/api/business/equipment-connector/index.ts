import request from '@/config/axios'

export interface EquipmentBrandVO {
  id: number
  stationId: number
  orgId: number
}

// 获取电枪详情
export const getEquipmentConnector = async (id: number) => {
  return await request.get({ url: `/operations/equipment-connector/get?id=` + id })
}

// 启停充电枪
export const equipmentConnectorIsStop =async (id: string) => {
  return await request.get({ url: `/operations/equipment/is-stop?id=` + id })
}
