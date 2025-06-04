<template>
  <s-layout title="订单详情" navbar="normal" onShareAppMessage>
    <view class="block">
      <uv-loading-page :loading="isShowLoading"></uv-loading-page>
      <scroll-view scroll-y="true" class="chargeInfo" style="height: 90vh;">
        <view class="bg">
          <!-- <image src="/static/image/personal/bg-user.png" mode="widthFix"></image> -->
          <text class="tip">感谢您的光临!</text>
        </view>
        <view class="stationInfo">
          <view class="stationName">
            <text class="name">{{ orderInfo.stationName }}</text>
            <view class="map" @click="toMap()">
              <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/nav.png" mode="aspectFit"></image>
              导航
            </view>
          </view>
          <view class="address">
            {{orderInfo.address || ''}}
          </view>
        </view>

        <view class="orderInfo">
          <view class="orderCode" style="margin-bottom: 20rpx;display: flex;align-items: center;">
            <text class="label">订单编号：</text>
            <text class="seq" style="color: #000;">{{orderInfo.StartChargeSeq}}</text>
            <text v-if="orderInfo.discountType == 2"
              style="width: 60rpx;border:1px solid #ddd; padding: 10rpx; margin-left: 10px;border-radius: 5px; color: #f4ea2a;font-style: italic;text-align: center;">SVIP</text>
            <text v-if="orderInfo.discountType == 1"
              style="width: 60rpx;border:1px solid #ddd; padding: 10rpx; margin-left: 10px;border-radius: 5px; color: #f4ea2a;font-style: italic;text-align: center;">VIP</text>
          </view>
          <view class="chargeTime" style="margin-bottom: 20rpx;">
            <text>订单状态：</text>
            <text>{{ orderInfo.status || '未知' }}</text>
          </view>
          <view class="chargeTime" style="margin-bottom: 20rpx;">
            <text>车牌号：</text>
            <text>{{ orderInfo.carNumber || '未绑定车牌号' }}</text>
          </view>
          <view class="chargeTime" style="margin-bottom: 20rpx;">
            <text>充电桩度数：</text>
            <text>{{ fixedNum(orderInfo.chargedPower,2)}}度</text>
          </view>
          <view class="chargeTime" style="margin-bottom: 20rpx;">
            <text>充电时间：</text>
            <text>{{ timePip(orderInfo.startTime,'YYYY-MM-DD HH:mm')}}至{{ timePip(orderInfo.endTime,'HH:mm')}}</text>
          </view>
          <view class="orderMoney" style="margin-bottom: 20rpx;">
            <view class="total">
              <view class="money">
                <text>订单费用</text>
                <text style="color: #000;">￥{{ fixedNum(orderInfo.allAmount,2)}}</text>
              </view>
              <view class="isExpand" @click="isExpand = !isExpand" style="display: flex; align-items: center;">
                {{ isExpand ? '收起' : '展开'}}
                <uv-icon :name="isExpand ? 'arrow-up' : 'arrow-down'"></uv-icon>
              </view>
            </view>
            <view class="table" style="margin-top: 20rpx; border-bottom: 1rpx solid #e2e2e2;">
              <view v-if="isExpand">
                <view style="display: flex;color: #000; width: 100%; height: 80rpx;">
                  <view style="width: 20%; text-align: center;">开始时段</view>
                  <view style="width: 20%; text-align: center;">结束时段</view>
                  <view style="width: 20%; text-align: center;">电费</view>
                  <view style="width: 20%; text-align: center;">服务费</view>
                  <view style="width: 20%; text-align: center;">总费用</view>
                </view>
                <view v-for="(item, index) in orderInfo.orderDetailsRespVOList" :key="index"
                  style="display: flex;color: #000; width: 100%; height: 80rpx;">
                  <view style="width: 20%; text-align: center;">{{ timePip(Number(item.startTime),'HH:mm') }}</view>
                  <view style="width: 20%; text-align: center;">
                    {{ timePip(Number(item.endTime),'HH:mm') }}
                  </view>
                  <view style="width: 20%; text-align: center;">{{ fixedNum(item.chargedPrice,2) }}</view>
                  <view style="width: 20%; text-align: center;">{{ fixedNum(item.servicePrice,2) }}</view>
                  <view style="width: 20%; text-align: center;">
                    {{ fixedNum((item.chargedPrice + item.servicePrice),2) }}
                  </view>
                </view>
              </view>
            </view>

            <view class="preferential">

              <view class="item">
                <text class="label">折扣优惠</text>
                <text class="val">-￥{{ fixedNum(orderInfo.serviceDiscountAmount,2) }}</text>
              </view>
              <view class="item">
                <text class="label">优惠券</text>
                <text class="val">-￥{{ fixedNum(orderInfo.couponPrice,2) }}</text>
              </view>
              <view class="item">
                <text class="label" style="font-weight: 600;">优惠减免合计</text>
                <text class="val"
                  style="color: #f00;">-￥{{ fixedNum((orderInfo.serviceDiscountAmount + orderInfo.couponPrice),2) }}</text>
              </view>
            </view>

            <view class="actualMoney">
              实付<text style="color: #000; font-weight: 600;">-￥{{ fixedNum(orderInfo.inPay,2) }}</text>
            </view>
          </view>

        </view>
        <view class="payment">
          <view class="item" style="margin-bottom: 20rpx;">
            <text class="label">支付时间：{{ timePip(orderInfo.payTime,'YYYY-MM-DD HH:mm') }}</text>
          </view>
          <view class="item" style="margin-bottom: 20rpx;">
            <text class="label">支付方式：<text style="color: #000;">{{paymentType(orderInfo.amountMethod)}}</text></text>
          </view>
          <view class="item" v-if="orderInfo.failReasonMsg">
            <text class="label">充电结束原因：{{orderInfo.failReasonMsg || ''}}</text>
          </view>
        </view>
        <view class="code" v-if="stationInfo.codeImage">
          <uv-image :showLoading="true" :src="stationInfo.codeImage" width="100px" height="100px"
            :showMenuByLongpress="true"></uv-image>
          <text class="tips"> 长按识别图片，请添加会员群。</text>
        </view>
      </scroll-view>


      <kx-tabbar-wrapper v-slot:kxTabBar>
        <view class="tab-bar" @click="toPay" v-if="orderInfo.pay === 0 && orderInfo.startChargeSeqStat === 4">
          主动结算
        </view>
        <view class="tab-bar" @click="goToStation" v-else>
          继续充电
        </view>
      </kx-tabbar-wrapper>

      <uv-modal :show="showModal" content='是否结算该订单?' title='提示' :showCancelButton="true" class="modal"
        @cancel="showModal = false" @confirm="submitPay"></uv-modal>

    </view>
  </s-layout>
</template>

<script>
  import moment from "dayjs";
  import {
    convertSecondsToTime,
    Tips
  } from "@/sheep/util/utils";
  import {
    getOrderInfo,
    proactiveBilling
  } from "@/sheep/api/charge/order"
  import {
    getDictData,
    getDictDataAlone
  } from "@/sheep/api/charge/system";
  import {
    getStationDetail
  } from "@/sheep/api/charge/operations";

  const PayTypeMap = new Map([
    [1, '先充后付支付'],
    [2, '预充值支付'],
    [3, '余额支付']
  ])

  export default {
    data() {
      return {
        id: '',
        orderInfo: {},
        statusMap: new Map([
          [1, '启动中'],
          [2, '进行中'],
          [3, '停止中'],
          [4, '已完成'],
          [5, '未知'],
        ]),
        orderStopReason: new Map(),
        isShowLoading: true,
        showOverlay: false,
        isExpand: false,
        showModal: false,
        isPop: false,
        seconds: 15,
        time: 0,
        stationInfo: {
          codeImage: ''
        }
      };
    },
    methods: {
      toPay() {
        this.showModal = true;
      },
      paymentType(type) {
        return PayTypeMap.get(type) || '余额支付';
      },

      submitPay() {
        proactiveBilling(this.orderInfo.id).then(res => {
          this.showModal = false;
          if (res.statusCode === 200) {
            Tips({
              title: '结算成功!',
              icon: 'success'
            })
            uni.navigateBack();
          } else {
            Tips({
              title: res.data.detail,
              icon: 'none'
            })
          }
        }).catch(err => {
          Tips({
            title: err.error.detail,
            icon: 'none'
          })
          this.showModal = false;
        })
      },

      // 首页
      goToStation() {
        uni.reLaunch({
          url: '/pages/index/index'
        })

      },

      closePop() {
        clearInterval(this.time);
        this.isPop = false;
      },

      // goToShare() {
      // 	uni.reLaunch({
      // 		url: '/pages/invite-code/q-code/q-code'
      // 	})
      // },

      // 获取订单详情
      async getOrderInfo() {
        this.isShowLoading = true;
        try {
          const {
            data
          } = await getOrderInfo(this.id);
          this.orderInfo = data;
          this.$set(this.orderInfo, 'status', this.statusMap.get(this.orderInfo.orderStatus));
          this.$set(this.orderInfo, 'StartChargeSeq', this.orderInfo.seqNumber?.substring(9));
          this.$set(this.orderInfo, 'failReasonMsg', this.orderStopReason.get(String(this.orderInfo.stopReason)));
          this.isShowLoading = false;
          const {
            data: res
          } = await getStationDetail({
            id: data.stationId,
            longitude: 0,
            latitude: 0
          });
          this.stationInfo = res;
          this.$set(this.stationInfo, 'codeImage', JSON.parse(res.codeUrl || '[]')?.[0]);
          console.log('res: ', res);
        } catch (e) {
          console.log('e: ', e);
          //TODO handle the exception
        }

      },
      // 导航
      toMap() {
        uni.openLocation({
          latitude: this.orderInfo.stationLat,
          longitude: this.orderInfo.stationLng,
          name: this.orderInfo.stationName,
          address: this.orderInfo.address
        });
      },

      async getChargeStopMsg() {
        try {
          const {
            data
          } = await getDictDataAlone('stop_charge_type');
          data.forEach(item => {
            this.orderStopReason.set(item.value, item.label)
          })
        } catch (e) {
          console.log('e: ', e);
          //TODO handle the exception
        }
      },
      timePip(val, format) {
        if (!val) {
          return '';
        }
        return moment(val).format(format);
      },
      fixedNum(num, fix) {
        if (!num) {
          return '0.00'
        }
        return Number(num).toFixed(fix)
      },
      formatData(item) {
        item['status'] = this.statusMap.get(item.orderStatus);
        item['StartTime'] = moment(item.startTime).format("YYYY-MM-DD HH:mm:ss");
        return item;
      }
    },
    async onLoad(option) {
      this.id = option.id;
      await this.getChargeStopMsg();
      this.getOrderInfo();
      this.isPop = option.isPop || false;
      if (this.isPop) {
        this.time = setInterval(() => {
          if (this.seconds < 1) {
            this.isPop = false;
            clearInterval(this.time);
          } else {
            this.seconds -= 1
          }
        }, 1000)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .tab-bar {
    background: linear-gradient(132deg, #4A8EFC 0%, #50C3FD 100%);
    border-radius: 45rpx;
    font-weight: bold;
    font-size: 30rpx;
    color: #FFF;
    text-align: center;
    padding: 24rpx 0;
    width: 90%;
  }

  page {
    // background-color: rgb(191, 248, 250);
    // background-image: url(/static/images/bg-user.jpg);
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }

  .block {
    height: 100vh;
    background: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/mini/box.png') 0 0/100% 812rpx no-repeat;

    // background-color: #f2f2f2;
    // padding-bottom: 130rpx;
    .chargeInfo {
      .bg {
        // image {
        // 	width: 100%;
        // 	height: 100%;
        // 	position: absolute;
        // 	top: 0;
        // 	left: 0;
        // }
        width: 100%;
        position: relative;
        height: 17vh;
        width: 750rpx;
        // background: linear-gradient(
        // 180deg,#3c9cff 5%,#ebebeb 40%);

        .tip {
          position: absolute;
          bottom: 8vh;
          left: 50rpx;
          font-size: 38rpx;
          font-weight: 600;
          color: #fff;
        }
      }

      .stationInfo {
        width: 650rpx;
        padding: 25rpx;
        margin: auto;
        background-color: #fff;
        border-radius: 12rpx;
        position: relative;
        box-shadow: 0rpx 0rpx 20rpx 5rpx #ccc;
        top: -50rpx;

        .stationName {
          display: flex;
          font-size: 30rpx;
          justify-content: space-between;

          .name {
            font-weight: 600;
          }

          .map {
            color: #229bff;

            image {
              width: 25rpx;
              margin-right: 15rpx;
              height: 25rpx;
            }
          }
        }

        .address {
          font-size: 25rpx;
          color: #aaa;
          margin-top: 20rpx;
        }
      }

      .orderInfo {
        width: 650rpx;
        padding: 25rpx;
        margin: -20rpx auto 0;

        background-color: #fff;
        border-radius: 12rpx;
        box-shadow: 0rpx 0rpx 20rpx 5rpx #ccc;
        color: #aaa;
        font-size: 30rpx;

        .total {
          display: flex;
          justify-content: space-between;
        }

        .preferential {
          margin-top: 20rpx;
          border-bottom: 1rpx solid #e2e2e2;
          color: #000;

          .item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20rpx;
          }
        }

        .actualMoney {
          display: flex;
          justify-content: flex-end;
          margin-top: 25rpx;
        }
      }

      .payment {

        .item {
          color: #aaa;
        }

      }
    }

    .btn {
      position: fixed;
      bottom: 0;
      padding: 30rpx 0;
      width: 750rpx;

      .uv-button {
        margin: auto !important;
        width: 700rpx !important;
        border-radius: 50rpx !important;
        height: 103rpx !important;

        .uv-button__text {
          font-size: 37.5rpx !important;
        }
      }
    }

    .uv-fade-enter-active {
      display: flex;
      justify-content: center;
      align-items: center;

      .content {
        position: relative;
        display: flex;
        flex-direction: column;
        align-items: center;
        color: #229bff;

        .header {
          position: absolute;
          z-index: 9999;
          right: 0;
          color: #fff !important;
          display: flex;
          justify-content: space-between;
          align-items: center;
          width: 99%;
          padding: 10rpx 0;

          .uv-icon__icon {
            color: #fff !important;
          }
        }

        .uv-button {
          margin-top: 30rpx;
          border-radius: 50rpx;
          width: 270rpx;
        }
      }
    }

    .code,
    .payment {
      width: 650rpx;
      padding: 25rpx;
      margin: auto;
      background-color: #fff;
      border-radius: 12rpx;
      box-shadow: 0rpx 0rpx 20rpx 5rpx #ccc;
      font-size: 30rpx;
      margin-top: 30rpx;
    }

    .code {
      display: flex;
      justify-content: flex-start;
      align-items: center;

      .tips {
        display: inline-block;
        margin-left: 20rpx;
        font-size: 30rpx;
        font-weight: 600;
      }
    }
  }
</style>