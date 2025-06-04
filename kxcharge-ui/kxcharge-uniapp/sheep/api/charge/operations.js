import request from '@/sheep/request';

const BASE_URL = 'operations/app/'

export function getStationList(params) {
  return request({
    url: BASE_URL + 'station/info',
    params
  })
}

export function getStationDetail({
  id,
  ...params
}) {
  return request({
    url: BASE_URL + 'station/' + id,
    params
  })
}

export function createFavorite(data) {
  return request({
    url: BASE_URL + 'favorite/create',
    data,
    method: 'post'
  })
}

export function deleteFavorite(data) {
  return request({
    url: BASE_URL + 'favorite/delete',
    data,
    method: 'delete'
  })
}

export function getConnectorInfo(id) {
  return request({
    url: BASE_URL + 'station/connectorInfo/' + id,
    custom: {
      showLoading: false
    }
  })
}

export function getOrder(seqNumber) {
  return request({
    url: BASE_URL + 'order/' + seqNumber,
    custom: {
      showLoading: false
    }
  })
}

export function getFavoritePage(params) {
  return request({
    url: BASE_URL + 'favorite/page',
    params
  })
}


export function getChargingPage() {
  return request({
    url: BASE_URL + 'order/page?pageNo=1&pageSize=10&orderStatus=2'
  })
}