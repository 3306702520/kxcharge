<template>
  <s-layout title="我的车辆" navbar="normal" onShareAppMessage>
    <view class="block">
      <uv-loading-icon text="加载中" v-if="isShowPageLoading"></uv-loading-icon>
      <scroll-view scroll-y="true" v-else class="scroll">
        <view class="noCar" v-if="isShowNoCar">
          <text class="title">您还没有添加车辆</text>
          <text class="tip">添加车辆可以获得更贴心的服务</text>
          <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/mini/car-default.png" mode="aspectFit"></image>
          <uv-button type="primary" text="添加车辆" shape="circle" @click="goToAddCar"></uv-button>
        </view>
        <view v-else>
          <view class="scrollItem" v-for="(item,index) in carList" :key="item.id" @click="goToInfo(item)">
            <text class="plateNum">{{item.plateNumber}}</text>
            <view class="info">
              <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/car-icon.png" mode="aspectFit"></image>
              <view class="detail">
                <view class="item"><text class="title">汽车类型:</text>{{item.CarType}}</view>
                <view class="item"><text class="title">上次充电地点:</text>{{item.latelyPlace || ''}}</view>
                <view class="item"><text class="title">上次充电时间:</text>{{item.LatelyTime || ''}}</view>
                <view class="item"><text class="title">充电次数:</text>{{item.chargeQuantity}}</view>
                <view class="item"><text class="title">创建时间:</text>{{formatTime(item.createTime)}}</view>
              </view>
            </view>
            <view class="default" v-if="item.isDefault">
              默认车辆
            </view>
            <view class="default" v-else style="background-color: #1890ff;" @click.stop="setDefault(item.id)">
              设为默认
            </view>
          </view>

          <uv-button type="primary" text="添加车辆" shape="circle" @click="goToAddCar"></uv-button>
          <uv-modal ref="modal" content='是否设置该车辆为默认车辆' title='提示' :showCancelButton="true" class="modal"
            @cancel="showModal = false" @confirm="submitDefault"></uv-modal>
        </view>

      </scroll-view>
    </view>
  </s-layout>
</template>

<script>
  import moment from "dayjs";
  import {
    getMyCar,
    setDefaultCar
  } from "@/sheep/api/charge/member";
  let connectorCode = null
  export default {
    data() {
      return {
        carList: [],
        isShowNoCar: true,
        showModal: false,
        carTypeMap: new Map([
          [1, '新能源汽车']
        ]),
        activeId: '',
        isShowPageLoading: true
      };
    },
    methods: {
      formatTime(ts) {
        return moment(ts).format("YYYY-MM-DD HH:mm");
      },
      getCarList() {
        this.isShowPageLoading = true;
        getMyCar().then(res => {
          const {
            data
          } = res;
          this.isShowNoCar = data.length === 0;
          if (!this.isShowNoCar) {
            data.forEach(item => {
              item['CarType'] = this.carTypeMap.get(item.plateType);
              item['LatelyTime'] = item.latelyTime ? moment(item.latelyTime).format("YYYY-MM-DD HH:mm:ss") :
                '';
              this.carList.push(item);
            })

          }
        }).finally(() => {
          this.isShowPageLoading = false
        })
      },

      goToInfo(data) {
        if (connectorCode) {
          uni.navigateTo({
            url: `/pages/equipment-detail/index?carInfo=${JSON.stringify(data)}&connectorCode=${connectorCode}`
          })
        } else {
          uni.navigateTo({
            url: `/pages/my-car/add-my-car/add-my-car?carInfo=${JSON.stringify(data)}`
          })
        }
      },

      goToAddCar() {
        uni.navigateTo({
          url: '/pages/my-car/add-my-car/add-my-car'
        })
      },

      setDefault(id) {
        this.activeId = id;
        this.$refs.modal.open();
      },
      submitDefault() {
        try {
          setDefaultCar(this.activeId, 0).then(res => {
            this.carList = [];
            this.getCarList();
            this.$refs.modal.close();;
            uni.showToast({
              icon: 'success',
              title: '设置成功!'
            })
          }).catch(err => {
            uni.showToast({
              icon: 'fail',
              title: '设置失败!'
            })
            console.log('err: ', err);
          })
        } catch (e) {
          //TODO handle the exception
          uni.showToast({
            icon: 'fail',
            title: '设置失败!'
          })
        }
      }
    },
    onLoad(options) {
      if (options.connectorCode) {
        connectorCode = options.connectorCode
      }
    },
    onShow() {
      this.carList = [];
      this.getCarList();
    }
  }
</script>

<style lang="scss" scoped>
  page {
    background-color: #f0f3f8;
  }

  .block {

    // height: 100vh;
    // background-color: #f2f2f2;
    .noCar {
      display: flex;
      flex-direction: column;

      image {
        width: 650rpx;
        height: 650rpx;
        margin: auto;
      }

      .title {
        font-size: 58rpx;
        margin: 180rpx 40rpx 15rpx;
      }

      .tip {
        color: #a7a7a7;
        margin-left: 40rpx;
      }
    }

    .scroll {
      background-color: #fff;
      padding: 15rpx;
    }

    .uv-button {
      margin-top: 50rpx;
      width: 375rpx !important;
      height: 95rpx !important;
      margin: 20rpx auto;

      .uv-button__text {
        font-size: 35rpx !important;
      }
    }

    .scrollItem {
      background-color: #fff;
      margin: 35rpx 15rpx 0;
      position: relative;
      padding: 30rpx 20rpx 0;
      border-radius: 20rpx;

      .plateNum {
        font-size: 35rpx;
        display: inline-block;
        margin-left: 10rpx;
      }

      .info {
        display: flex;
        font-size: 25rpx;

        image {
          width: 220rpx;
          height: 220rpx;
          margin-right: 30rpx;
        }

        .item {
          margin-bottom: 25rpx;
        }

        .title {
          width: 160rpx;
          display: inline-block;
          text-align: right;
          margin-right: 20rpx;
        }
      }

      .default {
        position: absolute;
        top: 20rpx;
        right: 0rpx;
        color: #fff;
        background-color: #61ec0b;
        border-top-left-radius: 25rpx;
        border-bottom-left-radius: 25rpx;
        padding: 8rpx 15rpx;
        font-size: 25rpx;
      }
    }
  }
</style>