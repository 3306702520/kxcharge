<template>
  <s-layout title="开始充电" navbar="normal" onShareAppMessage @clickLeft="clearTimer">

    <kx-equipment-info :detail="detail"></kx-equipment-info>

    <uni-card is-shadow spacing="0" padding="30rpx">
      <view class="header">
        <view class="line"></view>
        <view>
          价格信息
        </view>
      </view>
      <view class="time">
        价格时段:{{currentCostTemplatePrice.startTimeStr}}-{{currentCostTemplatePrice.endTimeStr}}
      </view>
      <view class="charge">
        <view class="price">
          <text>{{(currentCostTemplatePriceType.pricePower+currentCostTemplatePriceType.priceService).toFixed(2)}}</text>
          元/度
        </view>
        <view class="vip">
          <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16568@2x.png" mode="aspectFit"></image>
          <text>
            ￥{{(currentCostTemplatePriceType.pricePower+currentCostTemplatePriceType.priceService).toFixed(2)}}元/度
          </text>
        </view>
      </view>
      <view class="time" style="margin-bottom: 0;">
        电价{{currentCostTemplatePriceType.pricePower}}元/度，服务费{{currentCostTemplatePriceType.priceService}}元/度
      </view>
    </uni-card>

    <uni-card is-shadow spacing="0" padding="30rpx">
      <view class="header">
        <view class="line"></view>
        <view>
          停车场收费
        </view>
      </view>
      <view class="remark">
        {{detail.stationVo.openExplain}}
      </view>

    </uni-card>

    <uni-card is-shadow spacing="0" padding="30rpx">
      <view class="header">
        <view class="line"></view>
        <view>
          占用收费
        </view>
      </view>
      <view class="remark">
        充电完成后超过30分钟不拔枪，系统将按照0.17元/分钟计算收取设备占用费，费用上线为30元
      </view>

    </uni-card>

    <uni-card is-shadow spacing="0" padding="30rpx">
      <view class="header">
        <view class="line"></view>
        <view>
          充电爱车
        </view>
        <view style="flex:1;">
          <view class="right" @click="toPage('/pages/my-car/add-my-car/add-my-car')">
            <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/27672.png" style="width:28rpx;height:28rpx;margin-right: 10rpx;"></image>
            <text>添加爱车</text>
          </view>
        </view>
      </view>
      <view class="car-info" @click="toPage(`/pages/my-car/my-car?connectorCode=${connectorCode}`)">
        <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/27673.png"></image>
        <text>{{ plateNumber }}</text>
      </view>
    </uni-card>

    <uni-card is-shadow spacing="0" padding="30rpx">
      <view class="header">
        <view class="line"></view>
        <view>
          付款方式
        </view>
      </view>
      <view class="pay-info">
        <view class="account">
          <text>账户余额</text>
          <text class="value">{{ balance }}</text>
        </view>
        <view class="recharge" @click="toPage(`/pages/pay/recharge?connectorCode=${connectorCode}`)">去充值 ></view>
      </view>
    </uni-card>

    <ljs-loading v-if="progress>0">{{progress}}%</ljs-loading>

    <template v-slot:kxTabBar>
      <kx-tabbar-wrapper>
        <view class="tab-bar" @click="startCharge">
          开始充电
        </view>
      </kx-tabbar-wrapper>
    </template>
  </s-layout>
</template>

<script setup>
  import PayWalletApi from '@/sheep/api/pay/wallet';
  import * as MemberApi from "@/sheep/api/charge/member";
  import * as OrderApi from "@/sheep/api/charge/order";
  import dayjs from 'dayjs'
  import customParseFormat from 'dayjs/plugin/customParseFormat'
  import {
    getConnectorInfo as getConnectorInfoApi
  } from '@/sheep/api/charge/operations'

  dayjs.extend(customParseFormat);

  const balance = ref(0);
  const plateNumber = ref('');
  const orderSeqNumber = ref('');
  const progress = ref(0);
  const connectorCode = ref('');
  const detail = ref({
    equipmentModelRespVO: {},
    stationVo: {},
    workState: 3,
    connectorCode: ''
  })
  let timer = null
  let getDataTimer = null
  const currentCostTemplatePrice = ref({})
  const currentCostTemplatePriceType = ref({})

  const toPage = (url) => {
    uni.navigateTo({
      url
    });
  };

  const getConnectorInfo = async id => {
    const res = await getConnectorInfoApi(id)
    detail.value = res.data
    getDataTimer = setTimeout(() => {
      getConnectorInfo(id)
    }, 5000)
  }

  const getPrice = () => {
    const now = dayjs()
    const {
      costTemplatePriceRespVOS: costTemplatePrice,
      costTemplatePriceTypeRespVOS: costTemplatePriceType
    } = detail.value.stationVo.costTemplateRespVO
    currentCostTemplatePrice.value = costTemplatePrice.filter(({
      endTimeStr,
      startTimeStr
    }) => {
      const start = dayjs(startTimeStr, 'HH:mm'),
        end = dayjs(endTimeStr, 'HH:mm')
      return start.isBefore(now) && end.isAfter(now)
    })?.[0]
    currentCostTemplatePriceType.value = costTemplatePriceType.filter(({
      typeName
    }) => currentCostTemplatePrice.value.priceTypeId === typeName)?.[0]
  }

  onLoad(async (options) => {
    connectorCode.value = options.connectorCode;
    if (options.carInfo) {
      plateNumber.value = JSON.parse(options.carInfo).plateNumber
    }
    await getConnectorInfo(options.connectorCode)
    getPrice()
  });

  // 将初始化逻辑提取到单独的函数
  const initData = async () => {
    const response = await PayWalletApi.getPayWallet();
    if (response.code === 0) {
      balance.value = (response.data.balance / 100).toFixed(2);
    }
  };

  onMounted(async () => {
    const responseCar = await MemberApi.getMyCar();
    if (responseCar.code === 0) {
      const cars = responseCar.data;
      // 查找 isDefault = 1 的车辆
      const defaultCar = cars.find(car => car.isDefault === 1);
      if (plateNumber.value === '') {
        if (defaultCar) {
          plateNumber.value = defaultCar.plateNumber; // 有默认车辆，赋值
        } else if (cars.length > 0) {
          plateNumber.value = cars[0].plateNumber; // 没有默认车辆，取第一辆
        } else {
          plateNumber.value = ""; // 没有车辆，设为空
        }
      }
    }
    initData(); // 页面初次加载时调用
  });

  onShow(() => {
    initData(); // 页面再次显示时调用
  });

  const startCharge = async () => {
    const response = await OrderApi.startCharge(connectorCode.value, plateNumber.value, 1);

    if (response.code === 0) {
      orderSeqNumber.value = response.data;
      startProgress();
    }
  };

  onHide(() => {
    progress.value = 0;
    clearTimer()
  })
  onBackPress(() => {
    progress.value = 0;
    clearTimer()
  })
  onUnmounted(() => {
    progress.value = 0;
    clearTimer()
  })

  const clearTimer = () => {
    if (timer) {
      clearTimeout(timer)
      timer = null
    }
    if (getDataTimer) {
      clearTimeout(getDataTimer)
      getDataTimer = null
    }
  }

  const startProgress = () => {
    progress.value = 0;
    let count = 0;
    const query = async () => {
      progress.value += 3
      count++
      const orderResponse = await OrderApi.getOrderInfo(orderSeqNumber.value);
      // TODO
      if (count >= 1 || orderResponse.data.orderStatus === 2) {
        progress.value = 100
        clearTimer()
        uni.navigateTo({
          url: '/pages/charging/index?seqNumber=' + orderResponse.data.seqNumber
        });
        return
      }
      if (count >= 1 || orderResponse.data.orderStatus === 6) {
        progress.value = 100
        clearTimer()
        uni.navigateTo({
          url: `/pages/order-info/order-info?id=` + orderResponse.data.seqNumber
        })
        return
      }
      timer = setTimeout(() => {
        query();
      }, 1000)
    }
    query()
  }
  // const startProgress = () => {
  //   progress.value = 0;
  //   intervalId.value = setInterval(async () => {
  //     progress.value += 10;
  //     if (progress.value >= 3000) {
  //       clearInterval(intervalId.value);
  //       setTimeout(() => {
  //         uni.navigateTo({
  //           url: '/pages/charging/index'
  //         });
  //       }, 1000);
  //       return;
  //     }

  //     const orderResponse = await OrderApi.getOrderInfo(orderSeqNumber.value);

  //     if (orderResponse.data.orderStatus === 4) {
  //       clearInterval(intervalId.value);
  //       progress.value = 100;
  //       setTimeout(() => {
  //         uni.navigateTo({
  //           url: '/pages/charging/index'
  //         });
  //       }, 1000);
  //     }
  //   }, 1000);
  // };
</script>

<style lang="scss" scoped>
  .header {
    font-weight: 800;
    font-size: 32rpx;
    color: #333333;
    display: flex;
    align-items: center;
    margin-bottom: 20rpx;

    .line {
      width: 7rpx;
      height: 32rpx;
      margin-right: 16rpx;
      background: #3072F6;
    }

    .right {
      float: right;
      font-size: 30rpx;
      color: #337BFB;
    }
  }

  .time {
    font-size: 24rpx;
    color: #333333;
    margin: 20rpx 0;
  }

  .price {
    font-size: 24rpx;
    color: #333;
    display: inline-block;
    margin-right: 18rpx;
    vertical-align: middle;

    text {
      font-size: 40rpx;
      color: #EB5336;
    }
  }

  .vip {
    width: 230rpx;
    height: 45rpx;
    background: linear-gradient(180deg, #FADBB7 0%, #F6BA85 100%);
    border-radius: 10rpx 10rpx 10rpx 10rpx;
    display: inline-block;

    image {
      width: 82rpx;
      height: 45rpx;
    }

    text {
      font-size: 24rpx;
      color: #3A3E5E;
    }
  }

  .remark {
    font-size: 28rpx;
    color: #333333;
  }

  .car-info {
    background: #F5F6FA;
    border-radius: 20rpx;
    font-size: 30rpx;
    color: #323551;
    text-align: center;
    padding: 30rpx 0;

    image {
      width: 50rpx;
      height: 40rpx;
      margin-right: 10rpx;
    }
  }

  .pay-info {
    display: flex;
    justify-content: space-between;

    .account {
      font-size: 24rpx;
      color: #000000;
    }

    .value {
      font-weight: bold;
      font-size: 32rpx;
      margin-left: 10rpx;
    }

    .recharge {
      font-size: 26rpx;
      color: #666666;
    }
  }

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

  .progress-bar {
    width: 100%;
    height: 20rpx;
    background-color: #e0e0e0;
    border-radius: 10rpx;
    overflow: hidden;
    margin-top: 20rpx;

    .progress {
      height: 100%;
      background-color: #4A8EFC;
      width: v-bind(progress + '%');
      transition: width 1s linear;
    }
  }
</style>