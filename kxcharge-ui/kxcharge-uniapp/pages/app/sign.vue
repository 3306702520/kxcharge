<!-- 签到界面  -->
<template>
  <s-layout title="签到有礼">
    <s-empty v-if="state.loading" icon="/static/data-empty.png" text="签到活动还未开始" />
    <view v-if="state.loading" />
    <view class="sign-wrap" v-else-if="!state.loading">
      <!-- 签到日历 -->
      <view class="content-box calendar">
        <view class="sign-everyday ss-flex ss-col-center ss-row-between ss-p-x-30">
          <text class="sign-everyday-title">签到日历</text>
          <view class="sign-num-box">
            已连续签到 <text class="sign-num">{{ state.signInfo.continuousDay }}</text> 天
          </view>
        </view>
        <view class="list acea-row row-between-wrapper" style="
            padding: 0 30rpx;
            height: 240rpx;
            display: flex;
            justify-content: space-between;
            align-items: center;
          ">
          <view class="item" v-for="(item, index) in state.signConfigList" :key="index">
            <view :class="
                (index  === state.signConfigList.length ? 'reward' : '') +
                ' ' +
                (state.signInfo.continuousDay >= item.day ? 'rewardTxt' : '')
              ">
              第{{ item.day }}天
            </view>
            <view class="venus" :class="
                (index + 1 === state.signConfigList.length ? 'reward' : '') +
                ' ' +
                (state.signInfo.continuousDay >= item.day ? 'venusSelect' : '')
              ">
            </view>
            <view class="num" :class="state.signInfo.continuousDay >= item.day ? 'on' : ''">
              + {{ item.point }}
            </view>
          </view>
        </view>

        <!-- 签到按钮 -->
        <view class="myDateTable">
          <view class="ss-flex ss-col-center ss-row-center sign-box ss-m-y-40">
            <button class="ss-reset-button sign-btn" v-if="!state.signInfo.todaySignIn" @tap="onSign">
              签到
            </button>
            <button class="ss-reset-button already-btn" v-else disabled> 已签到 </button>
          </view>
        </view>
      </view>

      <!-- 签到说明 TODO @【签到】这里改成【已累计签到】；改版，接入 sheepjs  -->
      <view class="bg-white ss-m-t-16 ss-p-t-30 ss-p-b-60 ss-p-x-40">
        <view class="activity-title ss-m-b-30">签到说明</view>
        <view class="activity-des">1、已累计签到{{state.signInfo.totalDay}}天</view>
        <view class="activity-des">
          2、据说连续签到第 {{ state.maxDay }} 天可获得超额积分，一定要坚持签到哦~~~
        </view>
      </view>
    </view>

    <!-- 签到结果弹窗 -->
    <su-popup :show="state.showModel" type="center" round="10" :isMaskClick="false">
      <view class="model-box ss-flex-col">
        <view class="ss-m-t-56 ss-flex-col ss-col-center">
          <text class="cicon-check-round"></text>
          <view class="score-title">
            <text v-if="state.signResult.point">{{ state.signResult.point }} 积分 </text>
            <text v-if="state.signResult.experience"> {{ state.signResult.experience }} 经验</text>
          </view>
          <view class="model-title ss-flex ss-col-center ss-m-t-22 ss-m-b-30">
            已连续打卡 {{ state.signResult.day }} 天
          </view>
        </view>
        <view class="model-bg ss-flex-col ss-col-center ss-row-right">
          <view class="title ss-m-b-64">签到成功</view>
          <view class="ss-m-b-40">
            <button class="ss-reset-button confirm-btn" @tap="onConfirm">确认</button>
          </view>
        </view>
      </view>
    </su-popup>
  </s-layout>
</template>

<script setup>
  import sheep from '@/sheep';
  import {
    onReady
  } from '@dcloudio/uni-app';
  import {
    reactive
  } from 'vue';
  import SignInApi from '@/sheep/api/member/signin';

  const headerBg = sheep.$url.css('/static/img/shop/app/sign.png');

  const state = reactive({
    loading: true,

    signInfo: {}, // 签到信息

    signConfigList: [], // 签到配置列表
    maxDay: 0, // 最大的签到天数

    showModel: false, // 签到弹框
    signResult: {}, // 签到结果
  });

  // 发起签到
  async function onSign() {
    const {
      code,
      data
    } = await SignInApi.createSignInRecord();
    if (code !== 0) {
      return;
    }
    state.showModel = true;
    state.signResult = data;
    // 重新获得签到信息
    await getSignInfo();
  }

  // 签到确认刷新页面
  function onConfirm() {
    state.showModel = false;
  }

  // 获得个人签到统计
  async function getSignInfo() {
    const {
      code,
      data
    } = await SignInApi.getSignInRecordSummary();
    if (code !== 0) {
      return;
    }
    state.signInfo = data;
    state.loading = false;
  }

  // 获取签到配置
  async function getSignConfigList() {
    const {
      code,
      data
    } = await SignInApi.getSignInConfigList();
    if (code !== 0) {
      return;
    }
    state.signConfigList = data;
    if (data.length > 0) {
      state.maxDay = data[data.length - 1].day;
    }
  }

  onReady(() => {
    getSignInfo();
    getSignConfigList();
  });
  // TODO 1）css 需要优化，例如说引入的图片；2）删除多余的样式
</script>

<style lang="scss" scoped>
  .header-box {
    border-top: 2rpx solid rgba(#dfdfdf, 0.5);
  }

  // 日历
  .calendar {
    background: #fff;

    .sign-everyday {
      height: 100rpx;
      background: rgba(255, 255, 255, 1);
      border: 2rpx solid rgba(223, 223, 223, 0.4);

      .sign-everyday-title {
        font-size: 32rpx;
        color: rgba(51, 51, 51, 1);
        font-weight: 500;
      }

      .sign-num-box {
        font-size: 26rpx;
        font-weight: 500;
        color: rgba(153, 153, 153, 1);

        .sign-num {
          font-size: 30rpx;
          font-weight: 600;
          color: #4A8EFC;
          padding: 0 10rpx;
          font-family: OPPOSANS;
        }
      }
    }

    // 年月日
    .bar {
      height: 100rpx;

      .date {
        font-size: 30rpx;
        font-family: OPPOSANS;
        font-weight: 500;
        color: #333333;
        line-height: normal;
      }
    }

    .cicon-back {
      margin-top: 6rpx;
      font-size: 30rpx;
      color: #c4c4c4;
      line-height: normal;
    }

    .cicon-forward {
      margin-top: 6rpx;
      font-size: 30rpx;
      color: #c4c4c4;
      line-height: normal;
    }

    // 星期
    .week {
      .week-item {
        font-size: 24rpx;
        font-weight: 500;
        color: rgba(153, 153, 153, 1);
        flex: 1;
      }
    }

    // 日历表
    .myDateTable {
      display: flex;
      flex-wrap: wrap;

      .dateCell {
        width: calc(750rpx / 7);
        height: 80rpx;
        font-size: 26rpx;
        font-weight: 400;
        color: rgba(51, 51, 51, 1);
      }
    }
  }

  .is-sign {
    width: 48rpx;
    height: 48rpx;
    position: relative;

    .is-sign-num {
      font-size: 24rpx;
      font-family: OPPOSANS;
      font-weight: 500;
      line-height: normal;
    }

    .is-sign-image {
      position: absolute;
      left: 0;
      top: 0;
      width: 48rpx;
      height: 48rpx;
    }
  }

  .cell-num {
    font-size: 24rpx;
    font-family: OPPOSANS;
    font-weight: 500;
    color: #333333;
    line-height: normal;
  }

  .cicon-title {
    position: absolute;
    right: -10rpx;
    top: -6rpx;
    font-size: 20rpx;
    color: red;
  }

  // 签到按钮
  .sign-box {
    height: 140rpx;
    width: 100%;

    .sign-btn {
      width: 710rpx;
      height: 80rpx;
      border-radius: 35rpx;
      font-size: 30rpx;
      font-weight: 500;
      box-shadow: 0 0.2em 0.5em rgba(#4A8EFC, 0.4);
      background: linear-gradient(132deg, #4A8EFC 0%, #50C3FD 100%);
      color: #fff;
    }

    .already-btn {
      width: 710rpx;
      height: 80rpx;
      border-radius: 35rpx;
      font-size: 30rpx;
      font-weight: 500;
    }
  }

  .model-box {
    width: 520rpx;
    // height: 590rpx;
    background: linear-gradient(132deg, #4A8EFC 0%, #50C3FD 100%);
    // background: linear-gradient(177deg, var(--ui-BG-Main), var(--ui-BG-Main-gradient));
    border-radius: 10rpx;

    .cicon-check-round {
      font-size: 70rpx;
      color: #fff;
    }

    .score-title {
      font-size: 34rpx;
      font-family: OPPOSANS;
      font-weight: 500;
      color: #fcff00;
    }

    .model-title {
      font-size: 28rpx;
      font-weight: 500;
      color: #ffffff;
    }

    .model-bg {
      width: 520rpx;
      height: 344rpx;
      background-size: 100% 100%;
      background-image: v-bind(headerBg);
      background-repeat: no-repeat;
      border-radius: 0 0 10rpx 10rpx;

      .title {
        font-size: 34rpx;
        font-weight: bold;
        // color: var(--ui-BG-Main);
        color: #4A8EFC;
      }

      .subtitle {
        font-size: 26rpx;
        font-weight: 500;
        color: #999999;
      }

      .cancel-btn {
        width: 220rpx;
        height: 70rpx;
        border: 2rpx solid #4A8EFC;
        border-radius: 35rpx;
        font-size: 28rpx;
        font-weight: 500;
        color: #4A8EFC;
        line-height: normal;
        margin-right: 10rpx;
      }

      .confirm-btn {
        width: 220rpx;
        height: 70rpx;
        background: linear-gradient(132deg, #4A8EFC 0%, #50C3FD 100%);
        box-shadow: 0 0.2em 0.5em rgba(#4A8EFC, 0.4);
        border-radius: 35rpx;
        font-size: 28rpx;
        font-weight: 500;
        color: #ffffff;
        line-height: normal;
      }
    }
  }

  //签到说明
  .activity-title {
    font-size: 32rpx;
    font-weight: 500;
    color: #333333;
    line-height: normal;
  }

  .activity-des {
    font-size: 26rpx;
    font-weight: 500;
    color: #666666;
    line-height: 40rpx;
  }

  .reward {
    background-image: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/mini/reward.png');
    width: 75rpx;
    height: 56rpx;
  }

  .rewardTxt {
    width: 74rpx;
    height: 32rpx;
    background-color: #f4b409;
    border-radius: 16rpx;
    font-size: 20rpx;
    color: #a57d3f;
    line-height: 32rpx;
    text-align: center;
  }

  .venus {
    background-image: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/mini/venus.png');
    background-repeat: no-repeat;
    background-size: 100% 100%;
    width: 56rpx;
    height: 56rpx;
    margin: 10rpx 0;
  }

  .venusSelect {
    background-image: url('https://kxcharge.oss-cn-beijing.aliyuncs.com/mini/venusSelect.png');
  }

  .num {
    font-size: 36rpx;
    font-family: 'Guildford Pro';
  }

  .item {
    align-items: center;
    justify-content: space-between;
    border-bottom: 1px solid #eee;
    height: 130rpx;
  }

  .on {
    background-color: #999 !important;
  }
</style>