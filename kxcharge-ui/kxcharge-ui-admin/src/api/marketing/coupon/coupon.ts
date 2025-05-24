import request from '@/config/axios'

// TODO @dhb52：vo 缺少

// 删除优惠券
export const deleteCoupon = async (id: number) => {
  return request.delete({
    url: `/operations/coupon/delete?id=${id}`
  })
}

// 获得优惠券分页
export const getCouponPage = async (params: PageParam) => {
  return request.get({
    url: '/operations/coupon/page',
    params: params
  })
}

// 发送优惠券
export const sendCoupon = async (data: any) => {
  return request.post({
    url: '/operations/coupon/send',
    data: data
  })
}
