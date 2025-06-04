import request from '@/sheep/request';

const BASE_URL = 'member/'

export function getCarouselList() {
  return request({
    url: BASE_URL + 'carousel/list'
  })
}

export function getUserInfo() {
  return request({
    url: BASE_URL + 'user/get'
  })
}

export function getMyCar() {
  return request({
    url: BASE_URL + 'plate/listAll'
  })
}

export function setDefaultCar(id, isDefault) {
  return request({
    url: BASE_URL + 'plate/updateDefault',
    method: 'put',
    data: {
      id,
      isDefault
    }
  })
}

// 创建车辆
export function createCar(data) {
  return request({
    url: BASE_URL + 'plate/create',
    method: 'post',
    data
  })
}

// 编辑车辆
export function editCar(data) {
  return request({
    url: BASE_URL + 'plate/update',
    method: 'post',
    data
  })
}

// 删除车辆
export function delCar(id) {
  return request({
    url: BASE_URL + 'plate/delete?id=' + id,
    method: 'delete'
  })
}

export function createFeedback(data) {
  return request({
    url: BASE_URL + 'feedback/create',
    method: 'post',
    data
  })
}

export function getFeedbackList() {
  return request({
    url: BASE_URL + 'feedback/listAll',
  })
}