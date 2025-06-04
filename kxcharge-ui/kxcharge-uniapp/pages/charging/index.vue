<template>
  <s-layout title="正在充电" navbar="normal" onShareAppMessage @clickLeft="clearTimer">
    <view class="container">
      <view class="charging-percentage">
        <view class="percentage-container">
          <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/147.png" class="circle"></image>
          <view class="percentage">
            <text class="value">{{ data.soc?data.soc:0 }}</text>
            <text>%</text>
          </view>
          <view class="status">
            充电中...
          </view>
        </view>
        <view class="name">
          <text>{{data.stationName}}</text>
          <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/27574.png"></image>
        </view>
      </view>

      <view class="detail-info">
        <view class="rest-time">
          <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/27698 (2).png"></image>
          <text>预计充满时间：{{ estimatedFullTime }}</text>
        </view>

        <view class="cost-info">
          <view class="item">
            <view class="name">
              已充电量
            </view>
            <view class="value">
              {{data.chargedPower}}度
            </view>
          </view>
          <view class="line"></view>
          <view class="item">
            <view class="name">
              充电时长
            </view>
            <view class="value">
              {{chargingDuration}}
            </view>
          </view>
          <view class="line"></view>
          <view class="item">
            <view class="name">
              费用总计
            </view>
            <view class="value">
              {{data.chargedAmount}}元
            </view>
          </view>
        </view>

        <view class="cable-info">
          <view class="item">
            <text>实时电流</text>
            <text class="value">{{data.outputCurrent}}</text>
          </view>
          <view class="item">
            <text>实时电压</text>
            <text class="value">{{data.outputVoltage}}</text>
          </view>
          <view class="item">
            <text>实时功率</text>
            <text class="value">{{ (data.outputCurrent * data.outputVoltage).toFixed(2) }}KW</text>
          </view>
        </view>

        <view class="split-line"></view>

        <view style="width:100%;">
          <view class="header">
            <view class="line"></view>
            <view>
              订单信息
            </view>
          </view>
        </view>

        <view style="width: 100%;">
          <view class="list-item">
            <text>订单编号</text>
            <text class="value">{{data.seqNumber}}</text>
          </view>
          <view class="list-item">
            <text>绑定车牌</text>
            <text class="value">{{data.carNumber}}</text>
          </view>
          <view class="list-item">
            <text>充电时间</text>
            <text class="value">{{ formatTime  }}</text>
          </view>
          <view class="list-item">
            <text>充电桩编号</text>
            <text class="value">{{data.equipmentConnectorCode}}</text>
          </view>
        </view>

      </view>
      <ljs-loading v-if="progress>0">{{progress}}%</ljs-loading>

    </view>

    <template v-slot:kxTabBar>
      <kx-tabbar-wrapper>
        <view class="tab-bar">
          <button class="left item" @click="toPage('/pages/pay/recharge')">
            续缴
          </button>
          <button class="right item" :disabled="interval>0" @click="endCharge()">
            {{interval>0?`(${interval}秒)`:''}}结束
          </button>
        </view>
      </kx-tabbar-wrapper>
    </template>
  </s-layout>
</template>

<script setup>
  import {
    ref,
    onMounted,
    onUnmounted,
    computed
  } from 'vue'
  import {
    getOrder as getOrderApi
  } from '@/sheep/api/charge/operations';
  import * as OrderApi from "@/sheep/api/charge/order";

  const interval = ref(0) // 剩余秒数
  const data = ref({})
  let timer = null
  let countdownTimer = null // 新增倒计时定时器
  let durationTimer = null // 新增充电时长定时器
  let hasInitializedTimers = false // 新增标志变量
  const chargeStatus = ref([5, 6, 7, 8, 9])
  const progress = ref(0)
  let progressTimer = null

  const toPage = (url) => {
    uni.navigateTo({
      url
    });
  };

  // 清除所有定时器
  const clearTimers = () => {
    if (timer) {
      clearTimeout(timer)
      timer = null
    }
    if (countdownTimer) {
      clearInterval(countdownTimer)
      countdownTimer = null
    }
    if (durationTimer) {
      clearInterval(durationTimer)
      durationTimer = null
    }
    if (progressTimer) {
      clearTimeout(progressTimer)
      progressTimer = null
    }
    hasInitializedTimers = false // 重置标志
  }
  // 格式化时间戳
  const formatTime = computed(() => {
    if (!data.value.startTime) return '--'

    const date = new Date(parseInt(data.value.startTime))

    // 格式化为 YYYY-MM-DD HH:mm:ss
    return `${date.getFullYear()}-${padZero(date.getMonth()+1)}-${padZero(date.getDate())} ${padZero(date.getHours())}:${padZero(date.getMinutes())}:${padZero(date.getSeconds())}`
  })

  // 计算预计充满时间（响应式）
  const estimatedFullTime = computed(() => {
    if (!data.value.soc) return '计算中...'

    const remainingPercent = 100 - data.value.soc
    const totalMinutes = Math.ceil(remainingPercent * 1.5) // 假设每1%需要1.5分钟

    const hours = Math.floor(totalMinutes / 60)
    const minutes = totalMinutes % 60

    if (hours > 0) {
      return `${hours}小时${minutes}分钟`
    } else {
      return `${minutes}分钟`
    }
  })

  const endCharge = async () => {
    const response = await OrderApi.endCharge(data.value.seqNumber);
    if (response.code === 0) {
      clearTimers()
      progress.value = 90
      afterEndCharge()
    }
  };

  const afterEndCharge = async () => {
    progress.value--
    const orderResponse = await OrderApi.getOrderInfo(data.value.seqNumber);
    if (chargeStatus.value.includes(orderResponse.data.chargeStatus) || progress.value <= 0) {
      uni.navigateTo({
        url: `/pages/order-info/order-info?id=` + data.value.seqNumber
      })
    } else {
      progressTimer = setTimeout(() => {
        afterEndCharge()
      }, 5000)
    }
  }

  // 补零函数
  const padZero = (num) => {
    return num < 10 ? `0${num}` : num
  }
  // 格式化时间为 HH:mm:ss
  const formatDuration = (seconds) => {
    const hours = Math.floor(seconds / 3600)
    const minutes = Math.floor((seconds % 3600) / 60)
    const secs = seconds % 60

    return [
      hours.toString().padStart(2, '0'),
      minutes.toString().padStart(2, '0'),
      secs.toString().padStart(2, '0')
    ].join(':')
  }

  // 计算充电时长（响应式）
  const chargingDuration = computed(() => {
    if (!data.value.startTime) return '00:00:00'

    const now = Date.now()
    const startTimestamp = parseInt(data.value.startTime)
    const diffInSeconds = Math.floor((now - startTimestamp) / 1000)

    return formatDuration(diffInSeconds)
  })

  // 计算剩余秒数并开始倒计时
  const startCountdown = (startTime) => {
    const now = Date.now()
    const startTimestamp = parseInt(startTime)
    const diffInSeconds = Math.floor((now - startTimestamp) / 1000)

    // 如果超过60秒，不显示倒计时
    if (diffInSeconds >= 60) {
      interval.value = 0
      return
    }

    // 计算剩余秒数
    interval.value = 60 - diffInSeconds

    // 开始倒计时
    countdownTimer = setInterval(() => {
      interval.value -= 1

      // 倒计时结束
      if (interval.value <= 0) {
        clearInterval(countdownTimer)
        countdownTimer = null
      }
    }, 1000)
  }

  // 开始充电时长计时
  const startDurationTimer = () => {
    // 先清除已有的定时器
    if (durationTimer) clearInterval(durationTimer)

    // 每秒更新一次
    durationTimer = setInterval(() => {
      // computed属性会自动更新
    }, 1000)
  }

  onLoad(({
    seqNumber
  }) => {
    getOrder(seqNumber)
  })

  onHide(() => {
    clearTimers()
  })

  onUnmounted(() => {
    clearTimers()
  })

  onBackPress(() => {
    clearTimers()
  })

  const getOrder = async seqNumber => {
    const res = await getOrderApi(seqNumber)
    data.value = res.data

    // 修改后的计时器初始化逻辑
    if (res.data.startTime && !hasInitializedTimers) {
      startCountdown(res.data.startTime)
      startDurationTimer()
      hasInitializedTimers = true // 设置标志为true
    }

    if (res.data.orderStatus === 4) {
      clearTimers()
      uni.navigateTo({
        url: `/pages/order-info/order-info?id=` + res.data.seqNumber
      })
      return
    }

    timer = setTimeout(() => {
      getOrder(seqNumber)
    }, 10000)
  }
</script>

<style scoped lang="scss">
  .container {
    padding-top: 54rpx;
    background: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/mini/box.png') 0 0/100% 812rpx no-repeat;
  }

  @keyframes spin {
    from {
      transform: rotate(0deg);
    }

    to {
      transform: rotate(360deg);
    }
  }

  .charging-percentage {
    display: flex;
    flex-direction: column;
    align-items: center;

    .percentage-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      position: relative;
      background: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/mini/percentage.png') 0 0/100% 100% no-repeat;
      width: 416rpx;
      height: 416rpx;
      justify-content: center;
    }

    .circle {
      width: 347rpx;
      height: 347rpx;
      position: absolute;
      transform-origin: center center;
      animation: spin 1s linear infinite;
    }



    .percentage {
      font-weight: bold;
      font-size: 42rpx;
      color: #000000;
    }

    .value {
      font-size: 99rpx;
    }

    .status {
      font-size: 33rpx;
      color: #000000;
    }

    .name {
      background: rgba(255, 255, 255, 0.19);
      border-radius: 45rpx;
      border: 1rpx solid #FFFFFF;
      padding: 8rpx 38rpx;
      margin: 38rpx 0 64rpx;
      font-weight: bold;
      font-size: 30rpx;
      color: #FFFFFF;

      image {
        width: 26rpx;
        height: 26rpx;
        margin-left: 10rpx;
      }
    }
  }

  .detail-info {
    display: flex;
    flex-direction: column;
    align-items: center;
    background: #FFFFFF;
    box-shadow: 0rpx 0rpx 20rpx 1rpx rgba(53, 127, 251, 0.1);
    border-radius: 36rpx 36rpx 0rpx 0rpx;
    padding: 30rpx;
  }

  .rest-time {
    font-weight: 800;
    font-size: 32rpx;
    color: #333333;
    margin-bottom: 24rpx;

    image {
      width: 20rpx;
      height: 32rpx;
      margin-right: 10rpx;
    }
  }

  .cost-info {
    background: rgba(48, 114, 246, .1);
    border-radius: 25rpx;
    display: flex;
    align-items: center;
    justify-content: space-around;
    width: 690rpx;
    padding: 30rpx 0;
    font-size: 28rpx;
    color: #000000;

    .line {
      width: 1rpx;
      height: 61rpx;
      background: #DCDCDC;
    }

    .item {
      text-align: center;
    }

    .value {
      font-weight: bold;
      font-size: 32rpx;
      margin-top: 10rpx;
    }
  }

  .cable-info {
    display: flex;
    justify-content: space-evenly;
    margin: 30rpx 0;
    font-size: 24rpx;
    color: #000000;
    width: 100%;

    .value {
      font-weight: bold;
      margin-left: 10rpx;
    }
  }

  .split-line {
    width: 693rpx;
    height: 0rpx;
    border: 1rpx solid #E3E3E3;
    margin-bottom: 24rpx;
  }

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
  }

  .list-item {
    font-size: 28rpx;
    color: #999999;

    .value {
      color: #333333;
      margin-left: 20rpx;
    }
  }

  .list-item+.list-item {
    margin-top: 20rpx;
  }

  .tab-bar {
    display: flex;
    justify-content: space-evenly;
    width: 100%;

    .item {
      width: 334rpx;
      // padding: 24rpx 0;
      font-weight: bold;
      font-size: 30rpx;
      color: #FFFFFF;
      background: linear-gradient(132deg, #4A8EFC 0%, #50C3FD 100%);
      border-radius: 45rpx;
      text-align: center;
    }

    .right {
      background: linear-gradient(131deg, #FC754A 0%, #FD5050 100%);
    }
  }
</style>