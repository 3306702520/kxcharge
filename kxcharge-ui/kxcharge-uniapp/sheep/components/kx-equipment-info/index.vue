<template>
  <uni-card is-shadow spacing="0" padding="30rpx">
    <template v-slot:title>
      <view class="title">
        <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/27698.png" style="width:20rpx;height:32rpx;margin-right: 10rpx;"></image>
        <text>
          {{detail.equipmentModelRespVO.type===1?'快充':'慢充'}}
        </text>
      </view>
    </template>
    <view class="container">
      <view class="left">
        <view class="icon" :class="[WORK_STATE.get(detail.workState).state,{plug:detail.insertArmsState==='1'}]">
          {{detail.insertArmsState===1?'已插枪':WORK_STATE.get(detail.workState).text}}
        </view>
        <view class="text">
          {{detail.connectorCode.slice(-5)}}
        </view>
      </view>
      <view class="right">
        <view class="line">
          <view class="label">
            编号
          </view>
          <view class="value">
            {{detail.connectorCode}}
          </view>
        </view>
        <view class="line">
          <view class="label">
            电流
          </view>
          <view class="value">
            {{ detail.equipmentModelRespVO.maxa }}
          </view>
        </view>
        <view class="line">
          <view class="label">
            电压
          </view>
          <view class="value">
            {{ detail.equipmentModelRespVO.minv }}V-{{ detail.equipmentModelRespVO.maxv }}V
          </view>
        </view>
        <view class="line">
          <view class="label">
            {{detail.equipmentModelRespVO.type===1?'直流快充':'交流慢充'}}
          </view>
          <view class="value">
            {{detail.equipmentModelRespVO.maxp}}
          </view>
        </view>
      </view>
    </view>
  </uni-card>
</template>

<script setup>
  defineProps({
    detail: {
      type: Object
    }
  })

  const WORK_STATE = new Map([
    [0, { state: 'hitch', text: '故障' }],
    [1, { state: 'hitch', text: '故障'}],
    [2, { state: 'idle', text: '空闲'}],
    [3, { state: 'charging', text: '充电中' }],
    [4, { state: 'idle', text: '空闲' }]
  ])
</script>

<style scoped lang="scss">
  .title {
    background: rgba(252, 130, 41, .1);
    font-size: 28rpx;
    color: #FC8229;
    padding: 10rpx 30rpx;

    &.slow {
      background: rgba(64, 197, 168, .1);
      color: #40C5A8;
    }
  }

  .container {
    display: flex;

    .left {
      width: 98rpx;
      text-align: center;

      .icon {
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

      .text {
        font-size: 28rpx;
        color: #999999;
      }
    }

    .right {
      font-size: 28rpx;
      color: #333333;
      margin-left: 30rpx;

      .line {
        display: flex;
      }

      .line:not(:last-child) {
        margin-bottom: 20rpx;
      }

      .label {
        color: #999999;
        margin-right: 20rpx;
      }
    }

  }
</style>