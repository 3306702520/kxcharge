import request from '@/sheep/request';

const BASE_URL = 'system/'

export function getTenantByWebsite(params) {
  return request({
    url: BASE_URL + 'tenant/get-by-website',
    params
  })
}

export function getDictData(params) {
  return request({
    url: BASE_URL + 'dict-data/type',
    params
  })
}

export function getDictDataAlone(params) {
  return request({
    url: BASE_URL + 'dict-data/type?type='+params,
  })
}