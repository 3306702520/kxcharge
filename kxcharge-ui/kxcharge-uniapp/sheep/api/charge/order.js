import request from '@/sheep/request';

export function proactiveBilling(params) {
  return request({
    url: '/order-pile-service/chargeOrder/activeSettlement',
    params
  })
}

// 获取订单详情
export function getOrderInfo(id) {
  return request({
    url: `/operations/app/order/${id}`,
    custom: {
      showLoading: false
    }
  })
}

// 开启充电
export function startCharge(connectorCode, plateNumber, amountMethod) {
  const params = {
    connectorCode: connectorCode,
    amountMethod: amountMethod
  }
  if (plateNumber !== '') {
    params['plateNumber'] = plateNumber
  }
  return request({
    url: `/operations/app/order/startCharge`,
    params
  })
}

// 获取订单列表
export function getOrderList(pageLink, orderStatus) {
  const params = {
    pageNo: pageLink.page,
    pageSize: pageLink.pageSize,
  }
  if (orderStatus !== 'all') {
    params['orderStatus'] = orderStatus
  }
  return request({
    url: '/operations/app/order/page',
    params
  })
}

// 结束充电
export function endCharge(seqNumber) {
  return request({
    url: '/operations/app/order/endCharge',
    params: {
      seqNumber
    }
  })
}

// 订单绑定车牌
export function licensePlateBinding(id, plateNumber) {
  const parmas = {
    id
  }
  if (plateNumber != '未设置') {
    parmas['plateNumber'] = plateNumber
  }
  return request({
    url: `/operations/app/order/licensePlateBinding`,
    params
  })
}

// 查询信用分
export function orderaccredit(option) {
  const data = {
    channelCode: option.channelCode,
    openid: option.openid
  }
  return request({
    url: '/pay/order/accredit',
    method: 'post',
    data
  })
}

// 查询总订单数据
export function getTotalOrder() {
  return request({
    url: '/operations/app/order/totalOrder'
  })
}

// 查订单图表数据
export function getOrderReport(startTs, endTs) {
  return request({
    url: `/operations/app/order/report?startTime=${startTs}&endTime=${endTs}`
  })
}