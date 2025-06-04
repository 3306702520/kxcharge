<template>
  <uni-card is-shadow spacing="0" padding="30rpx" @click="toDetail">
    <view class="container">
      <view class="content">
        <view class="header">
          <view class="name">
            {{station.stationName}}
          </view>
          <view class="distance">
            <uni-icons type="paperplane-filled" color="#3072F6" :size="13"></uni-icons>
            {{ station.distance < 1 ? (station.distance * 1000).toFixed(2) + 'm' : station.distance.toFixed(2) + 'km' }}
          </view>
        </view>
        <view class="abstract">
          <!--              //todo  公共；50:个人；100:公交(专用);101:环卫(专用);102:物流(专用);103:出租车(专用);255:其他-->
          <text>{{(stationType)}}</text>
          <view class="line"></view>
          <text>{{station.openExplain}}</text>
          <view class="line"></view>
          <text>{{station.remarks}}</text>
        </view>
        <view class="detail">
          <view class="img">
            <image :src="station.imgMainEntrance" mode="aspectFit" style="width:100%;height:100%"></image>
          </view>
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
          <view class="info">
            <!-- 慢充部分，条件渲染 -->
            <view class="slow" v-if="station.slowConnectorNumber && station.slowConnectorNumber > 0">
              <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16566@2x.png" mode="aspectFit"></image>
              <text>
                {{station.slowLeisureConnectorNumber}}空/{{station.slowConnectorNumber}}
              </text>
            </view>
            <!-- 快充部分，条件渲染 -->
            <view class="fast" v-if="station.connectorNumber && station.connectorNumber > 0">
              <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16567@2x.png" mode="aspectFit"></image>
              <text>
                {{station.leisureConnectorNumber}}空/{{station.connectorNumber}}
              </text>
            </view>
          </view>
        </view>
      </view>
      <view class="tip">
        <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16415@2x.png" mode="aspectFit"></image>
        <text>停车说明：{{station.parkExplain}}</text>
      </view>
    </view>
  </uni-card>
</template>

<script setup>
  const props = defineProps({
    station: {
      type: Object
    }
  })

  const stationTypes = sheep.$store('dict')['station_type']

  const stationType = computed(() => {
    return stationTypes.filter(el => (el.value + '') === (props.station.stationType + ''))[0]?.label
  })

  function toDetail() {
    const {
      id,
      stationLng,
      stationLat
    } = props.station
    uni.navigateTo({
      url: '/pages/station-detail/index?id=' + id + '&longitude=' + stationLng + '&latitude=' + stationLat
    })
  }
</script>

<style lang="scss" scoped>
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .name {
    font-weight: 800;
    font-size: 30rpx;
    color: #333;
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

  .abstract {
    margin-top: 10rpx;
    display: flex;
    align-items: center;

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
    display: flex;
    justify-content: space-between;
    align-items: center;

    .img {
      width: 200rpx;
      height: 112rpx;
    }
  }

  .charge {
    .price {
      font-size: 24rpx;
      color: #333;

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
      margin-top: 20rpx;

      image {
        width: 82rpx;
        height: 45rpx;
      }

      text {
        font-weight: bold;
        font-size: 24rpx;
        color: #3A3E5E;
      }
    }
  }

  .info {
    font-size: 26rpx;

    image {
      width: 44rpx;
      height: 44rpx;
    }

    .slow {
      background: rgba(64, 197, 168, .1);
      color: #40C5A8;
    }

    .fast {
      background: rgba(252, 130, 41, .1);
      color: #FC8229;
      margin-top: 25rpx;
    }

    text {
      padding: 0 10rpx;
    }
  }

  .tip {
    padding-top: 20rpx;
    border-top: 1rpx solid #E3E3E3;

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
</style>