import request from '@/config/axios'

export interface EquipmentConnectorVO {
                    id: number
                    stationId: number
                    equipmentId: number
                    equipmentBrandId: number
                    equipmentModelId: number
                    orgId: number
                    operatorId: number
                    connectorCode: string
                    shortcutCode: string
                    connectorName: string
                    isEnableStart: boolean
                    workStatus: number
                    operationDate: Date
                    version: string
                    isAlonePrice: boolean
}

// 查询电枪管理列表
export const getEquipmentConnectorPage = async (params) => {
  return await request.get({ url: '/operations/equipment-connector/page', params })
}

// 查询电枪管理详情
export const getEquipmentConnector = async (id: number) => {
  return await request.get({ url: '/operations/equipment-connector/get?id=' + id })
}

// 新增电枪管理
export const createEquipmentConnector = async (data: EquipmentConnectorVO) => {
  return await request.post({ url: '/operations/equipment-connector/create', data })
}

// 修改电枪管理
export const updateEquipmentConnector = async (data: EquipmentConnectorVO) => {
  return await request.put({ url: '/operations/equipment-connector/update', data })
}

// 删除电枪管理
export const deleteEquipmentConnector = async (id: number) => {
  return await request.delete({ url: '/operations/equipment-connector/delete?id=' + id })
}

// 导出电枪管理 Excel
export const exportEquipmentConnectorApi = async (params) => {
  return await request.download({ url: '/operations/equipment-connector/export-excel', params })
}