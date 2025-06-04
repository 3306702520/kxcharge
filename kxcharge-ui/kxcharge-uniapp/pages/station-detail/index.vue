<template>
  <s-layout :title="station?.stationName" navbar="normal" onShareAppMessage>
    <uni-card is-shadow spacing="0" padding="30rpx">
      <view class="info-container">
        <view class="content">
          <view class="header">
            <view class="name">
              <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16511.png"></image>
              <text>{{station.stationName}}</text>
            </view>
            <view class="favorite" @click="toggleFavorite">
              <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/27697(1).png" v-if="station.favorite"></image>
              <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/27697.png" v-else></image>
              <text :class="{active:station.favorite}">收藏</text>
            </view>
          </view>
          <view class="abstract">
            <text>{{station.stationType}}</text>
            <view class="line"></view>
            <text>{{station.openExplain}}</text>
            <view class="line"></view>
            <text>{{station.remarks}}</text>
          </view>
          <view class="abstract">
            <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16512.png"></image>
            <text>营业时间：{{station.openExplain}}</text>
          </view>
          <scroll-view scroll-x="true" class="detail">
            <image :src="station.imgFlagPath"></image>
            <image :src="station.imgFullView"></image>
            <image :src="station.imgMainEntrance"></image>
            <image :src="station.imgOther"></image>
            <image :src="station.imgPilesFeature"></image>
          </scroll-view>
        </view>
        <view class="tip">
          <text>{{station.address}}</text>
          <view class="distance">
            <uni-icons type="paperplane-filled" color="#3072F6" :size="13"></uni-icons>
            {{station?.distance?.toFixed(2)}}米
          </view>
        </view>
      </view>
    </uni-card>

    <view class="tabs">
      <view class="tab-item" :class="{active:currentType===CONNECT_TYPE.FAST}" @click="onToggleType(CONNECT_TYPE.FAST)">
        <view class="icon">快</view>
        <view class="text">{{station.leisureConnectorNumber}}空/共{{station.connectorNumber}}</view>
        <view class="arrow">
          <!-- <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/jiantou1.png"></image> -->
        </view>
      </view>
      <view class="tab-item slow" :class="{active:currentType===CONNECT_TYPE.SLOW}"
        @click="onToggleType(CONNECT_TYPE.SLOW)">
        <view class="icon">慢</view>
        <view class="text">{{station.slowLeisureConnectorNumber}}空/共{{station.slowConnectorNumber}}</view>
        <view class="arrow">
          <!-- <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/jiantou2.png"></image> -->
        </view>
      </view>
    </view>

    <uni-card is-shadow spacing="0" padding="30rpx">
      <view class="price-container">
        <view class="header">
          <view class="title">
            <view class="line"></view>
            价格信息
          </view>
          <view class="detail" @click="goToPriceDetails()">价格详情<uni-icons type="right" size="14"></uni-icons></view>
        </view>
        <view class="time">价格时段：08:30-24:00</view>
        <view class="charge">
          <view class="price">
            <text>{{station.currentPrice}}</text>
            元/度
          </view>
          <view class="vip">
            <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16568@2x.png" mode="aspectFit"></image>
            <text>
              ￥{{station.currentPrice}}/度
            </text>
          </view>
        </view>
        <view class="tip">
          <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16415@2x.png" mode="aspectFit"></image>
          <text>停车说明：{{station.parkExplain}}</text>
        </view>
      </view>
    </uni-card>

    <uni-card is-shadow spacing="0" padding="30rpx">
      <view class="list-container">
        <view class="header">
          <view class="title">
            <view class="line"></view>
            终端列表
          </view>
          <view class="detail" @click="viewList">查看全部<uni-icons type="right" size="14"></uni-icons></view>
        </view>
        <view class="list">
          <view class="list-item" v-for="item in list" :key="item.id"
                @click="WORK_STATE.get(item.workState).clickable ? onChargeStart(item) : null">
            <view class="status" :class="[WORK_STATE.get(item.workState).state,{plug:item.insertArmsState==='1'}]">
              {{item.insertArmsState==='1'?'已插枪':WORK_STATE.get(item.workState).text}}
            </view>
            <view class="info">
              <view class="name">{{item.connectorName}}</view>
              <view class="remark">{{item.connectorCode}}</view>
            </view>
          </view>
        </view>
      </view>
    </uni-card>

    <template v-slot:kxTabBar>
      <kx-tabbar @contact-service="contactService"></kx-tabbar>
    </template>
  </s-layout>
</template>

<script setup>
import {
  createFavorite,
  deleteFavorite,
  getStationDetail
} from '@/sheep/api/charge/operations'

const CONNECT_TYPE = {
  FAST: 1,
  SLOW: 2
}

const WORK_STATE = new Map([
  [0, { state: 'hitch', text: '故障', clickable: false }],
  [1, { state: 'hitch', text: '故障', clickable: false }],
  [2, { state: 'idle', text: '空闲', clickable: true }],
  [3, { state: 'charging', text: '充电中', clickable: false }],
  [4, { state: 'idle', text: '空闲', clickable: true }]
])

const station = ref({})
let refreshInterval = null // 存储定时器ID
let isRefreshing = false // 标记是否正在刷新中

const contactService = () => {
  uni.makePhoneCall({
    phoneNumber: station.value.serviceTel
  });
}

// 获取站点详情
const fetchStationDetail = async () => {
  try {
    const { data } = await getStationDetail({
      id,
      longitude,
      latitude
    })
    station.value = data
  } catch (error) {
    console.error('获取站点详情失败:', error)
  } finally {
    isRefreshing = false
  }
}

// 启动定时刷新
const startRefresh = () => {
  // 先清除已有的定时器
  stopRefresh()

  // 立即执行第一次刷新
  fetchStationDetail()

  // 设置定时器，每5秒刷新一次
  // refreshInterval = setInterval(() => {
  //   if (!isRefreshing) {
  //     isRefreshing = true
  //     fetchStationDetail()
  //   }
  // }, 5000)
}

// 停止定时刷新
const stopRefresh = () => {
  if (refreshInterval) {
    clearInterval(refreshInterval)
    refreshInterval = null
  }
  isRefreshing = false
}

let id, longitude, latitude
onLoad((params) => {
  id = params.id
  longitude = params.longitude
  latitude = params.latitude

  // 开始定时刷新
  startRefresh()
})

// 页面卸载时停止定时刷新
onUnload(() => {
  stopRefresh()
})

const toggleFavorite = () => {
  const {
    stationLat: latitude,
    stationLng: longitude,
    id,
    favorite
  } = station.value
  let api = createFavorite
  if (favorite) {
    api = deleteFavorite
  }
  api({
    stationId: id
  }).then(() => {
    fetchStationDetail()
  })
}

const currentType = ref(CONNECT_TYPE.FAST)
const onToggleType = (val) => {
  currentType.value = val
}

const goToPriceDetails = () => {
  const priceServiceDiscount = station.value.costTemplateRespVO.costTemplatePriceRespVOS.map(item => {
    return {
      time: `${item.startTimeStr}-${item.endTimeStr}`
    }
  })
  uni.navigateTo({
    url: `/pages/price-details/price-details?priceServiceDiscount=${JSON.stringify(priceServiceDiscount)}&electricityFee=${JSON.stringify(station.value.electricityFee)}&serviceFee=${JSON.stringify(station.value.serviceFee)}`
  })
}

const list = computed(() => {
  return station.value?.equipmentConnectorInfoVOS?.filter(el => el.equipmentModelRespVO.type === currentType
      .value)
})

const viewList = () => {
  uni.navigateTo({
    url: '/pages/equipment-list/index'
  })
}

const onChargeStart = (item) => {
  uni.navigateTo({
    url: `/pages/equipment-detail/index?connectorCode=${item.connectorCode}`
  })
}
</script>

<style scoped lang="scss">
  .info-container {
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .name {
      font-weight: 800;
      font-size: 34rpx;
      color: #333;

      image {
        width: 36rpx;
        height: 38rpx;
        display: inline-block;
        margin-right: 10rpx;
      }
    }

    .favorite {
      float: right;

      image {
        width: 30rpx;
        height: 24rpx;
        display: inline-block;
        margin-right: 4rpx;
      }

      text {
        font-weight: 500;
        font-size: 24rpx;
        color: #333333;
      }

      text.active {
        color: #FC8229;
      }
    }

    .abstract {
      margin-top: 10rpx;
      display: flex;
      align-items: center;

      image {
        width: 24rpx;
        height: 24rpx;
        display: inline-block;
        margin-right: 10rpx;
      }

      text {
        font-weight: 500;
        font-size: 24rpx;
        color: #666;
      }

      .line {
        width: 1rpx;
        height: 21rpx;
        border: 1rpx solid #ccc;
        margin: 0 10rpx;
      }
    }

    .detail {
      margin: 10rpx 0;
      white-space: nowrap;

      image {
        width: 200rpx;
        height: 112rpx;
      }
    }

    .tip {
      padding-top: 20rpx;
      border-top: 1rpx solid #E3E3E3;

      text {
        font-weight: 500;
        font-size: 24rpx;
        color: #666;
        margin-left: 10rpx;
      }

      .distance {
        float: right;
        background: rgba(48, 114, 246, .1);
        border-radius: 25rpx;
        font-weight: bold;
        font-size: 24rpx;
        color: #3072F6;
        padding: 0 10rpx;
      }
    }
  }

  .tabs {
    display: flex;
    justify-content: space-evenly;
    position: relative;

    .tab-item {
      color: #40C5A8;
      background: #FFFFFF;
      box-shadow: 0rpx 0rpx 20rpx 1rpx rgba(53, 127, 251, 0.1);
      border-radius: 20rpx 20rpx 20rpx 20rpx;
      width: 334rpx;
      height: 90rpx;
      display: flex;
      align-items: center;
      justify-content: center;

      .icon {
        font-weight: bold;
        font-size: 26rpx;
        color: #FFFFFF;
        padding: 8rpx 10rpx;
        margin-right: 10rpx;
        background: #40C5A8;
        border-radius: 50%;
      }

      .text {
        font-size: 32rpx;
        font-weight: bold;
      }

      .arrow {
        position: absolute;
        width: 30rpx;
        height: 30rpx;
        bottom: -2rpx;
        display: none;
      }
    }

    .tab-item.slow {
      color: #FC8229;

      .icon {
        background: #FC8229;
      }
    }

    .tab-item.active {
      background: #40C5A8;

      .icon {
        color: #40C5A8;
        background: #fff;
      }

      .text {
        color: #fff;
      }

      .arrow {
        display: block;
      }
    }

    .tab-item.active.slow {
      background: #FC8229;

      .icon {
        color: #FC8229;
      }
    }
  }

  .price-container {
    .header {
      display: flex;
      justify-content: space-between;
    }

    .title {
      font-weight: 800;
      font-size: 32rpx;
      color: #333333;
      display: flex;
      align-items: center;

      .line {
        width: 8rpx;
        height: 32rpx;
        background: #3072F6;
        border-radius: 0rpx 0rpx 0rpx 0rpx;
        margin-right: 16rpx;
      }
    }

    .detail {
      display: flex;
      align-items: center;
      font-size: 26rpx;
      color: #666666;
    }

    .time {
      font-size: 24rpx;
      color: #333333;
      margin: 26rpx 0;
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
      width: 194rpx;
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

    .tip {
      padding-top: 20rpx;
      border-top: 1rpx solid #E3E3E3;
      margin-top: 14rpx;

      image {
        width: 28rpx;
        height: 28rpx;
      }

      text {
        font-weight: 500;
        font-size: 24rpx;
        color: #666;
        margin-left: 10rpx;
      }
    }
  }

  .list-container {
    .header {
      display: flex;
      justify-content: space-between;
    }

    .title {
      font-weight: 800;
      font-size: 32rpx;
      color: #333333;
      display: flex;
      align-items: center;

      .line {
        width: 8rpx;
        height: 32rpx;
        background: #3072F6;
        border-radius: 0rpx 0rpx 0rpx 0rpx;
        margin-right: 16rpx;
      }
    }

    .detail {
      display: flex;
      align-items: center;
      font-size: 26rpx;
      color: #666666;
    }

    .time {
      font-size: 24rpx;
      color: #333333;
      margin: 26rpx 0;
    }
  }

  .list-item {
    display: flex;
    padding: 30rpx 0;
    color: #333333;
    align-items: center;

    &:not(:last-child) {
      border-bottom: 1rpx solid #E3E3E3;
    }

    .status {
      width: 98rpx;
      height: 98rpx;
      border-radius: 50%;
      border-width: 6rpx;
      border-style: solid;
      border-color: rgba(48, 114, 246, 0.4);
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 20rpx;
      font-size: 28rpx;
      color: #3072F6;

      &.idle {
        border-color: rgba(64, 197, 168, 0.4);
        color: #40C5A8;
      }

      &.hitch {
        border-color: rgba(184, 184, 184, 0.4);
        color: red;
      }

      &.plug {
        border-color: rgba(13, 192, 226, 0.4);
        color: #0DC0E2;
      }

      &.charging {
        border-color: rgba(13, 192, 226, 0.4);
        color: #0d22e2;
      }

    }

    .name {
      font-weight: bold;
      font-size: 28rpx;
      margin-bottom: 16rpx;
    }

    .remark {
      font-size: 24rpx;
    }
  }
</style>