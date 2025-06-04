<!-- 首页，支持店铺装修 -->
<template>
  <s-layout title="首页" navbar="normal" tabbar="pages/index/index" onShareAppMessage>
    <view class="container">
      <kx-search-bar class="search-bar" scroll id="kxSearchBar"></kx-search-bar>
      <view class="banner">
        <uni-swiper-dot :info="carouselList"
          :dots-styles="{backgroundColor: 'rgba(255,255,255, .4)',selectedBackgroundColor: 'rgba(255,255,255)'}"
          :current="currentCarousel">
          <swiper autoplay circular @change="onCarouselChange">
            <swiper-item v-for="item in carouselList" :key="item.id">
              <image :src="item.url" mode="aspectFit" style="width:100%;height:100%;"></image>
            </swiper-item>
          </swiper>
        </uni-swiper-dot>
      </view>
      <view>
        <view class="charging" v-for="item in chargingList" :key="item.id">
          <view class="left">
            <view class="icon">
              <image :src="userStore?.userInfo?.avatar || 'https://kxcharge.oss-cn-beijing.aliyuncs.com/default-avatar.png'" mode="aspectFill">
              </image>
            </view>
            <view class="info">
              <view class="car-no">
                {{item.plateNumber}}
              </view>
              <view class="progress">
                <view class="icon">

                </view>
                <view>
                  正在充电中<span class="ellipsis">......</span>{{ item.endSoc }}%
                </view>
              </view>
            </view>
          </view>
          <view class="right" @click="toPage('/pages/charging/index?seqNumber=' + item.seqNumber)">
            查看详情
            <uni-icons type="arrow-right" size="16" color="#3072F6" style="vertical-align: middle;"></uni-icons>
          </view>
        </view>
      </view>
      <view class="station-header">
        附近充电站
      </view>
      <view class="station-tabs"
        :style="{top:stationTabsTop+'px',backgroundColor:(scrollTop>stationTabsTop)?'#f5f9ff':'transparent'}">
        <view class="item" v-for="(item,index) in tabs" :key="item.index" :class="{active:tabIndex===index}"
          @tap="onTabTap(index)">
          {{item.text}}
        </view>
      </view>
      <view class="station-list">
        <kx-station-info :station="item" v-for="item in stationList" :key="item.id"></kx-station-info>
        <kx-tabbar-placeholder></kx-tabbar-placeholder>
      </view>
    </view>
  </s-layout>
</template>

<script setup>
  import {
    getStationList,
    getChargingPage
  } from '@/sheep/api/charge/operations'
  import {
    getCarouselList
  } from '@/sheep/api/charge/member'

  // onPageScroll
  const {
    scrollTop
  } = usePageScroll()

  let longitude = null,
    latitude = null

  const userStore = computed(() => {
    return sheep.$store('user').$state
  });

  const stationTabsTop = ref(0)

  onMounted(() => {
    uni.createSelectorQuery().in(getCurrentInstance().proxy).select("#kxSearchBar").boundingClientRect((data) => {
      stationTabsTop.value = data.height + sheep.$platform.navbar
    }).exec();
  })


  const stationList = ref([])
  const chargingList = ref([])

  const carouselList = ref([])
  const currentCarousel = ref(0)

  onShow(() => {
    const actualLocation = sheep.$store('user').actualLocation
    longitude = actualLocation.longitude
    latitude = actualLocation.latitude
    getStationList({
      longitude,
      latitude
    }).then(res => {
      stationList.value = res.data
    })
    getChargingPage().then(({
      data
    }) => {
      chargingList.value = data.list
    })
    getCarouselList().then(res => {
      carouselList.value = res.data
    })
  })



  const onCarouselChange = (e) => {
    currentCarousel.value = e.detail.current
  }

  // 隐藏原生tabBar
  uni.hideTabBar();

  const tabIndex = ref(0)

  const tabs = [{
    text: '距离最近'
  }, {
    text: '价格最低'
  }, {
    text: '快充'
  }, {
    text: '慢充'
  }]

  const onTabTap = (index) => {
    tabIndex.value = index;

    // 确定 rank 的值
    let rank = '';
    switch (index) {
      case 0:
        rank = 'distance'; // 距离最近
        break;
      case 1:
        rank = 'price'; // 价格最低
        break;
      case 2:
        rank = 'fast'; // 快充（如果需要）
        break;
      case 3:
        rank = 'slow'; // 慢充（如果需要）
        break;
      default:
        rank = '';
    }

    // 重新获取站点列表
    getStationList({
      longitude,
      latitude,
      rank
    }).then(res => {
      stationList.value = res.data;
    });
  }

  const toPage = (url) => {
    if (!url) return; // 如果没有设置路径，则不跳转

    // 判断是否是 tabBar 页面
    const tabBarPages = [
      '/pages/index/index',
      '/pages/map/index',
      '/pages/order/index',
      '/pages/my/index'
    ];

    if (tabBarPages.includes(url)) {
      // 使用 switchTab 跳转 tabBar 页面
      uni.switchTab({
        url
      });
    } else {
      // 其他页面使用 navigateTo
      uni.navigateTo({
        url
      });
    }
  }
</script>
<style lang="scss" scoped>
  .ellipsis {
    display: inline-block;
    vertical-align: bottom;
    overflow: hidden;
    animation: ellipsis 3s infinite steps(4, jump-none);
  }

  @keyframes ellipsis {
    from {
      width: 18.1rpx;
    }

    to {
      width: 36.5rpx;
    }
  }

  .container {
    background: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/1.png') 0 0/100% 424rpx no-repeat, #f5f9ff;
  }

  .banner {
    width: 690rpx;
    height: 300rpx;
    margin: 20rpx auto 30rpx;
  }

  .grid-item-box {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    flex: 1;
    padding: 15px 0;

    .text {
      font-size: 26rpx;
      color: #333333;
    }

    .img {
      width: 90rpx;
      height: 90rpx;
    }
  }

  .charging {
    width: 690rpx;
    height: 140rpx;
    background: linear-gradient(90deg, #FFFFFF 0%, #C8E7FF 100%);
    box-shadow: inset 0rpx 3rpx 6rpx 1rpx #FFFFFF;
    border-radius: 87rpx;
    margin: 30rpx auto 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20rpx 30rpx;

    .left {
      display: flex;
    }

    .icon {
      width: 94rpx;
      height: 94rpx;
      // background: #B7CFFB;
      border-radius: 34rpx;
    }

    .info {
      margin-left: 20rpx;
      margin-top: 5rpx;

      .icon {
        width: 20rpx;
        height: 20rpx;
        background: #03E698;
        margin-right: 10rpx;
      }
    }

    .car-no {
      font-weight: bold;
      font-size: 30rpx;
      color: #333;
      margin-bottom: 15rpx;
    }

    .progress {
      font-size: 26rpx;
      color: #666;
      display: flex;
      align-items: center;
    }

    .right {
      font-size: 24rpx;
      color: #3072F6;
    }
  }

  .station-header {
    display: inline-block;
    margin: 30rpx 0 25rpx 30rpx;
    font-weight: 800;
    font-size: 36rpx;
    color: #333;
    background: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/5946@2x.png') 0 bottom/100% 12rpx no-repeat
  }

  .station-tabs {
    display: flex;
    padding: 0 0 20rpx 30rpx;
    position: -webkit-sticky;
    position: sticky;
    z-index: 100;

    .item {
      font-weight: bold;
      font-size: 28rpx;
      color: #333;
      padding: 5rpx 12rpx;
    }

    .item+.item {
      margin-left: 60rpx;
    }

    .item.active {
      background: rgba(48, 114, 246, 0.1);
      border-radius: 25rpx 25rpx 25rpx 25rpx;
      color: #3072F6;
    }
  }
</style>