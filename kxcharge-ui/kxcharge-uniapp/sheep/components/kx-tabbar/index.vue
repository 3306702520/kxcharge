<template>
  <kx-tabbar-wrapper>
    <view class="flex tabbar-container">
      <view class="flex align-center flex-column left" @click="contactService">
        <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16585.png"></image>
        <text>联系客服</text>
      </view>
      <view class="right" @click="onScan">
        扫码充电
      </view>
    </view>
  </kx-tabbar-wrapper>
</template>

<script setup>
  const emit = defineEmits(['contact-service'])

  const onScan = () => {
    uni.scanCode({
      success(res) {
        console.log('条码类型：' + res.scanType);
        console.log('条码内容：' + res.result);
      },
      fail(res) {
        console.log('扫码失败：' + res)
      }
    });
  }
  const contactService = () => {
    emit('contact-service')
  }
</script>

<style scoped lang="scss">
  .tabbar-container {
    width: 100%;
    justify-content: space-evenly;
    align-items: center;

    .left {
      image {
        width: 50rpx;
        height: 50rpx;
        margin-bottom: 2rpx;
      }

      text {
        font-size: 26rpx;
        color: #333;
      }
    }

    .right {
      background: linear-gradient(132deg, #4A8EFC 0%, #50C3FD 100%);
      border-radius: 45rpx;
      font-weight: bold;
      font-size: 30rpx;
      color: #FFF;
      padding: 24rpx 226rpx;
    }
  }
</style>