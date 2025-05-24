import request from '@/config/axios'

export interface CertificateVO {
  id: number
  type: number
  orgId: number
  operatorId: number
  operatorSecret: string
  sigSecret: string
  dataSecret: string
  dataSecretIv: string
  url: string
}

// 查询互联互通秘钥列表
export const getCertificatePage = async (params) => {
  return await request.get({ url: `/interconnected/certificate/page`, params })
}

// 查询互联互通秘钥详情
export const getCertificate = async (id: number) => {
  return await request.get({ url: `/interconnected/certificate/get?id=` + id })
}

// 新增互联互通秘钥
export const createCertificate = async (data: CertificateVO) => {
  return await request.post({ url: `/interconnected/certificate/create`, data })
}

// 修改互联互通秘钥
export const updateCertificate = async (data: CertificateVO) => {
  return await request.put({ url: `/interconnected/certificate/update`, data })
}

// 删除互联互通秘钥
export const deleteCertificate = async (id: number) => {
  return await request.delete({ url: `/interconnected/certificate/delete?id=` + id })
}

// 导出互联互通秘钥 Excel
export const exportCertificate = async (params) => {
  return await request.download({ url: `/interconnected/certificate/export-excel`, params })
}
