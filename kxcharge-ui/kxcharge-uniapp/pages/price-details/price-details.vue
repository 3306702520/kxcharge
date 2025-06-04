<template>
  <s-layout title="电价详情" navbar="normal" onShareAppMessage>
    <view class="block">
      <view class="header">
        <text class="name title">时段</text>
        <view class="formula">
          <view class="item" style="margin-right: 5rpx;">
            <text class="name">价格</text>
            <text class="unit">(元/度)</text>
          </view>
          <view>=</view>
          <view class="item">
            <text class="name">电价</text>
            <text class="unit">(元/度)</text>
          </view>
          <view style="margin-right: 8rpx;">+</view>
          <view class="item">
            <text class="name">服务费</text>
            <text class="unit">(元/度)</text>
          </view>
        </view>
      </view>

      <scroll-view scroll-y="true" class="scroll">
        <view class="item" :class="{nowItem: item.isNow}" v-for="(item,index) in intervalList" :key="index">
          <view class="left">
            <text class="now" v-if="item.isNow">当前时段</text>
            <text class="time">{{item.interval}}</text>
          </view>
          <view class="info">
            <view class="price">
              <text style="width: 80rpx;">价格</text>
              <text class="total">{{fixedNum(item.eleMoney + item.serviceMoney,2)}}</text>
              <text>{{fixedNum(item.eleMoney,2)}}</text>
              <text>{{fixedNum(item.serviceMoney,2)}}</text>
            </view>
            <view class="price vip">
              <text style="width: 80rpx;">VIP</text>
              <text class="total">{{fixedNum(discountPrice(item.eleMoney, item.interval),2)}}</text>
              <text>{{fixedNum(item.eleMoney,2)}}</text>
              <text>{{ fixedNum(countDiscountPrice(item.interval),2)}}</text>
            </view>
            <view class="price vip">
              <text style="width: 80rpx;">SVIP</text>
              <text class="total">{{fixedNum(vipPrice(item.eleMoney, item.interval),2)}}</text>
              <text>{{fixedNum(item.eleMoney ,2)}}</text>
              <text>{{fixedNum(countVipPrice(item.interval),2)}}</text>
            </view>
          </view>
        </view>
        <view class="tip">
          充电费用仅供参考，实际以充电桩上费用为准
        </view>
      </scroll-view>

    </view>
  </s-layout>
</template>

<script>
  import moment from 'dayjs';
  export default {
    data() {
      return {
        electricityFee: {},
        serviceFee: {},
        intervalList: [],
        priceServiceDiscount: [],
        isShowVip: false
      };
    },
    methods: {
      fixedNum(num, fix) {
        // console.log('num: ',num);
        if (!num) {
          return "0.00"
        }
        return Number(num).toFixed(fix)
      },
      discountPrice(eleMoney, time) {
        const servicePrice = this.priceServiceDiscount.find(item => item.time == time)?.priceDiscount;
        return +eleMoney + +servicePrice;
      },
      vipPrice(eleMoney, time) {
        const servicePrice = this.priceServiceDiscount.find(item => item.time == time)?.vipDiscountServiceFee;
        return +eleMoney + +servicePrice;
      },
      countDiscountPrice(time) {
        return this.priceServiceDiscount.find(item => item.time == time)?.priceDiscount;
      },
      countVipPrice(time) {
        return this.priceServiceDiscount.find(item => item.time == time)?.vipDiscountServiceFee;
      },
      handelData() {
        console.log('this.electricityFee, this.serviceFee: ', this.electricityFee, this.serviceFee);
        const list = []
        for (const key in this.electricityFee) {
          const now = +new Date();
          const timeArr = key.split("-").map(t => moment(`${moment().format("YYYY-MM-DD")} ${t}`).valueOf());
          const obj = {
            interval: key,
            eleMoney: this.electricityFee[key] || 0,
            isNow: now > timeArr[0] && now <= timeArr[1]
          };
          if (this.serviceFee[key] || this.serviceFee[key] === 0) {
            obj['serviceMoney'] = this.serviceFee[key] || 0
          } else {
            for (const k in this.serviceFee) {
              const t = moment(`${moment().format("YYYY-MM-DD")} ${k.split("-")[1]}`).valueOf();
              if (timeArr[1] < t) {
                obj['serviceMoney'] = this.serviceFee[k] || 0;
                break;
              }
            }
          }
          list.push(obj);
        }
        const index = list.findIndex(k => k.isNow);
        if (index > -1) this.intervalList.push(list[index]);
        const itemsToKeep = list.slice(0, index).concat(list.slice(index + 1));
        itemsToKeep.sort((a, b) => {
          const atime = moment(`${moment().format("YYYY-MM-DD")} ${a.interval.split("-")[1]}`).valueOf();
          const btime = moment(`${moment().format("YYYY-MM-DD")} ${b.interval.split("-")[1]}`).valueOf();
          return atime - btime
        }).forEach(item => {
          this.intervalList.push(item)
        })
        const deletedItems = list.splice(index, 1);
        console.log('this.intervalList, list: ', this.intervalList, list);
      }
    },
    onLoad(option) {
      console.log(89, option)

      // 解析 electricityFee 和 serviceFee
      const parseFeeString = (feeString) => {
        const result = {};
        const items = feeString.split(";").filter(item => item); // 按分号分割并过滤空项
        items.forEach((item) => {
          // 找到最后一个冒号的位置
          const lastColonIndex = item.lastIndexOf(":");
          if (lastColonIndex === -1) return; // 如果没有冒号，跳过

          const key = item.slice(0, lastColonIndex); // 时间区间（如 "00:00-01:00"）
          const value = item.slice(lastColonIndex + 1); // 数值（如 "1.0"）

          if (key && value) {
            result[key] = parseFloat(value);
          }
        });
        return result;
      };

      this.electricityFee = parseFeeString(option.electricityFee.slice(1, -1));
      this.serviceFee = parseFeeString(option.serviceFee.slice(1, -1));
      this.priceServiceDiscount = JSON.parse(option.priceServiceDiscount);
      this.isShowVip = true;
      this.handelData();
    }
  }
</script>

<style lang="scss" scoped>
  .block {
    background-color: #fff;
    height: 100vh;

    .header {
      width: 680rpx;
      margin: auto;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 20rpx 0;

      .formula {
        display: flex;
        width: 60%;
        justify-content: flex-end;

        .item {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          width: 105rpx;

          .unit {
            font-size: 25rpx;
            color: #aaaaaa;
          }
        }
      }

      .name {
        font-size: 30rpx;
        font-weight: 600;
      }

      .title {
        display: inline-block;
        width: 30%;
        text-align: center;
      }
    }

    .scroll {
      padding-bottom: 25rpx;

      .item {
        width: 700rpx;
        margin: auto;
        border-radius: 12rpx;
        box-shadow: 0px 0px 5px 5px #f1f1f1;
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 30rpx;
        color: #666;
        font-size: 30rpx;
        position: relative;
        overflow: hidden;

        .left {
          width: 30%;
          text-align: center;
          padding: 50rpx 0;

          .now {
            position: absolute;
            top: 0;
            left: 0;
            background-color: #1890FF;
            padding: 5rpx 20rpx;
            color: #fff;
            font-size: 25rpx;
          }
        }

        .time,
        .total {
          font-weight: 600;
        }

        .info {
          width: 70%;
          padding: 20rpx 0;
          display: flex;
          flex-direction: column;
          justify-content: space-around;
          color: #1890FF;

          .price {
            width: 95%;
            padding: 10rpx 0;
            display: flex;
            justify-content: space-between;
            color: #1890FF;
          }

          .vip {
            color: #fe5d0f;
          }

          // padding-right: 10rpx;
        }
      }

      .nowItem {
        border: 1rpx solid #1890FF;
        background: #1890FF4D;
      }
    }

    .tip {
      font-size: 25rpx;
      color: #aaaaaa;
      width: 700rpx;
      margin: auto;

    }
  }
</style>