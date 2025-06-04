<template>
  <s-layout title="我的" navbar="normal" tabbar="pages/my/index" onShareAppMessage>
    <view class="container">
      <view class="info flex justify-around align-center">
        <view class="avatar flex">
          <view class="image">
            <image :src="userStore?.userInfo?.avatar || 'https://kxcharge.oss-cn-beijing.aliyuncs.com/default-avatar.png'"
              style="width:100%;height:100%;" mode="aspectFill">
            </image>
          </view>
          <view class="name flex flex-column justify-center">
            <view>
              <text>
                {{ userStore? userStore.userInfo.nickname : '未登录' }}
              </text>
              <image v-if="userStore?.userInfo?.level" src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16491.png"
                style="width:100%;height:100%;"></image>
            </view>
            <view class="level">
              {{ userStore ? 'KC充电用户' : '请先登录' }}
            </view>
          </view>
        </view>
        <view class="check">
        </view>
      </view>
      <view class="account flex justify-around">
        <view class="item flex  align-center" @click="toPage('/pages/pay/recharge')">
          <view class="title">
            账户余额
          </view>
          <view class="value">
            {{ userStore ? (userStore.userWallet.balance / 100).toFixed(2) : 0 }}
          </view>
        </view>
        <view class="item flex align-center" @click="toPage('/pages/user/wallet/score')">
          <view class="title">
            积分
          </view>
          <view class="value">
            {{ userStore ? userStore.userInfo.point : 0 }}
          </view>
        </view>
      </view>

      <view class="banner">
        <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16490.png" mode="aspectFill" style="width:100%;height:100%;"></image>
      </view>

      <uni-card padding="40rpx 20rpx" margin="8rpx" spacing="20rpx">
        <template v-slot:title>
          <uni-row>
            <uni-col :span="5">
              <view class="text-bold">我的爱车</view>
            </uni-col>
            <uni-col :span="5" :push="14">
              <view @click="toPage('/pages/my-car/my-car')">全部车辆></view>
            </uni-col>
          </uni-row>
        </template>
        <view class="flex align-center" @click="toPage('/pages/my-car/add-my-car/add-my-car')">
          <image v-if="userStore?.userInfo?.plateNumber" src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16580.png"
            style="width:60rpx;height:60rpx;margin-right: 10rpx;"></image>
          <image v-else src="https://kxcharge.oss-cn-beijing.aliyuncs.com/27672.png" style="width:28rpx;height:28rpx;margin-right: 10rpx;">
          </image>
          <text v-if="userStore?.userInfo?.plateNumber">{{ userStore ? userStore.userInfo.plateNumber : 0 }}</text>
          <text v-else>添加我的爱车</text>
        </view>
        <view slot="actions" class="flex justify-around card-actions">
          <view class="flex align-center">
            <uni-icons type="heart" size="18" color="#999"></uni-icons>
            <text>停车减免</text>
          </view>
          <view class="flex align-center">
            <uni-icons type="heart" size="18" color="#999"></uni-icons>
            <text>充电安全</text>
          </view>
          <view class="flex align-center">
            <uni-icons type="heart" size="18" color="#999"></uni-icons>
            <text>精准推荐</text>
          </view>
        </view>
      </uni-card>

      <view class="service">
        <view class="more-header">
          更多服务
        </view>

        <uni-grid :column="4" :highlight="true" :show-border="false" :square="false">
          <uni-grid-item v-for="(item, index) in menus" :index="index" :key="index">
            <view class="grid-item-box" @click="toPage(item.path)">
              <image :src="item.icon" mode="aspectFit" class="img"></image>
              <text class="text">{{item.text}}</text>
            </view>
          </uni-grid-item>
        </uni-grid>
      </view>
    </view>
  </s-layout>
</template>

<script setup>
  import {
    showAuthModal
  } from '@/sheep/hooks/useModal';
  import * as UserApi from '@/sheep/api/charge/member';

  const userStore = computed(() => {
    return sheep.$store('user').$state;
  });
  // // 获取最新的用户数据
  // const getUserData = async () => {
  //   try {
  //     const {
  //       data
  //     } = await UserApi.getUserInfo();
  //     if (data) {
  //       userStore.userInfo = data;
  //       console.log('获取到新的用户数据:', data);
  //     }
  //   } catch (error) {
  //     console.error('获取用户信息失败:', error);
  //   }
  // };

  // 检查登录状态
  const checkLoginStatus = () => {
    const token = uni.getStorageSync('token');
    if (!token) {
      showAuthModal();
      return;
    }

    const cachedUserStore = uni.getStorageSync('user-store');
    if (cachedUserStore) {
      try {
        const parsedUserStore = JSON.parse(cachedUserStore);
        if (parsedUserStore.isLogin) {
          userStore.value = parsedUserStore;
          // getUserData();
        }
      } catch (e) {
        console.error('解析用户信息失败:', e);
      }
    }
  };

  // // 组件挂载时检查登录状态
  // onMounted(() => {
  //   checkLoginStatus();
  // });

  // 添加页面显示时的处理
  onShow(() => {
    checkLoginStatus();
    sheep.$store('user').getInfo()
    sheep.$store('user').getWallet()
  });

  const menus = [{
    icon: 'https://kxcharge.oss-cn-beijing.aliyuncs.com/16582.png',
    text: '账单',
    path: '/pages/user/wallet/money'
  }, {
    icon: 'https://kxcharge.oss-cn-beijing.aliyuncs.com/16588.png',
    text: '订单',
    path: '/pages/order/index'
  }, {
    icon: 'https://kxcharge.oss-cn-beijing.aliyuncs.com/16585.png',
    text: '联系客服'
  }, {
    icon: 'https://kxcharge.oss-cn-beijing.aliyuncs.com/16587.png',
    text: '设置',
    path: '/pages/public/setting'
  }]

  onLaunch(() => {
    // 隐藏原生导航栏 使用自定义底部导航
    uni.hideTabBar();
  });

  const toLogin = () => {
    const token = uni.getStorageSync('token');
    if (!token) {
      showAuthModal();
      return;
    }
  }

  const toPage = (url) => {
    uni.navigateTo({
      url
    })
  }
</script>

<style lang="scss" scoped>
  .card-actions {
    border-top: 2rpx solid #eee;
    padding-top: 10rpx;
  }

  text {
    vertical-align: middle;
  }

  .container {
    background: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/1.png') 0 0/100% 424rpx no-repeat, #f5f9ff;
    padding-top: 28rpx;
    height: 100%;
    flex: 1;
  }

  .avatar {
    .image {
      width: 114rpx;
      height: 114rpx;
      border-radius: 50%;
      border: 8rpx solid #FFFFFF;
      margin-right: 28rpx;
      overflow: hidden;

      image {
        width: 100%;
        height: 100%;
        border-radius: 50%;
      }
    }

    .name {
      font-weight: 800;
      font-size: 30rpx;
      color: #323551;

      image {
        width: 60rpx;
        height: 38rpx;
      }
    }

    .level {
      margin-top: 26rpx;
      font-size: 24rpx;
      color: #666666;
      font-weight: normal;
    }
  }

  .check {
    width: 130rpx;
    height: 52rpx;
  }

  .account {
    padding: 36rpx 50rpx;

    .title {
      font-size: 24rpx;
      color: #000000;
      margin-right: 10rpx;
    }

    .value {
      font-weight: bold;
      font-size: 32rpx;
      color: #000000;
    }
  }

  .banner {
    margin: 0 auto;
    width: 690rpx;
    height: 138rpx;
  }

  .service {
    // background: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/6043.png') 0 0/100% 100% no-repeat;
    background: #fff;
    border-radius: 40rpx;
    padding: 50rpx 40rpx;
  }

  .item-container {
    .item {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
    }

    .icon {
      width: 82rpx;
      height: 82rpx;
    }

    .title {
      font-size: 26rpx;
      color: #333333;
      margin-top: 10rpx;
    }
  }

  .car-info {
    background: #F5F6FA;
    border-radius: 20rpx;
    font-size: 30rpx;
    padding: 30rpx;
    margin: 30rpx 0;

    .left {
      color: #323551;
    }

    .right {
      color: #337BFB;
    }
  }

  .more-header {
    display: inline-block;
    font-weight: 800;
    font-size: 36rpx;
    color: #333;
    background: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/5946@2x.png') 0 bottom/100% 12rpx no-repeat
  }

  .grid-item-box {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    .text {
      font-size: 26rpx;
      color: #333333;
      margin-top: 12rpx;
    }

    .img {
      width: 50rpx;
      height: 50rpx;
      margin-top: 30rpx;
    }
  }
</style>