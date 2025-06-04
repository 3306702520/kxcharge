<template>
  <s-layout title="月度账单" navbar="normal" onShareAppMessage>
    <view class="block">
      <scroll-view scroll-y="true" style="height: 84vh;">
        <view class="bottom"></view>
        <view class="top">
          <view class="timeSelect">
            <view class="monthBtn" @click="changeMonth('sub')">上个月</view>
            <picker mode="date" :value="date" @change="bindDateChange($event)" fields='month' class="datePicker">
              <view class="uni-input">{{ formatDate(date)}}<u-icon name="arrow-down"></u-icon></view>
            </picker>
            <view class="monthBtn" @click="changeMonth('add')">下个月</view>
          </view>
          <view class="totalData">
            <text class="label">本月为您节省(元)</text>
            <text class="money">{{toFix(totalMonitorData.economizeMoney ,2)}}</text>
            <text class="power">约等于电量:{{ toFix((totalMonitorData.economizeMoney / 0.90961475),2) }}度</text>
            <view class="dataInfo">
              <view class="info">
                <text class="number">{{ toFix(totalMonitorData.totalSpent,2)}}</text>
                <text class="label">本月支出(元)</text>
              </view>
              <view class="info">
                <text class="number">{{ toFix(totalMonitorData.totalPower,2)}}</text>
                <text class="label">本月电量(度)</text>
              </view>
              <view class="info">
                <text class="number">{{totalMonitorData.totalOrders}}</text>
                <text class="label">总笔数(笔)</text>
              </view>
            </view>
          </view>
        </view>
        <view class="class">
          <text class="title">支出分类</text>
          <view class="charts-box">
            <qiun-data-charts type="ring" :opts="expendDataOpts" :chartData="expendData" />
          </view>
          <view class="expendLegen">
            <view class="item" v-for="(item,index) in expendLegend" :key="index">
              <view class="left">
                <text class="icon" :style="{background: item.color}"></text>
                {{item.label}}
              </view>
              <text class="value">￥{{item.value}}</text>
            </view>
          </view>
        </view>

        <view class="class">
          <text class="title">充电优惠分类</text>
          <view class="charts-box">
            <qiun-data-charts type="ring" :opts="powerDiscountOpts" :chartData="powerDiscountData" />
          </view>
          <view class="powerDiscountLegen">
            <view class="item" v-for="(item,index) in powerDiscountLegend" :key="index">
              <view class="left">
                <text class="icon" :style="{background: item.color}"></text>
                {{item.label}}({{item.percent}})
              </view>
              <text class="line"></text>
              <text class="value">￥{{item.value}}</text>
            </view>
          </view>
        </view>
      </scroll-view>

      <view class="btn">
        <u-button type="primary" text="去充电" @click="goToStation"></u-button>
      </view>
    </view>
  </s-layout>
</template>

<script>
  import moment from 'dayjs';
  import {
    getOrderReport
  } from "@/sheep/api/charge/order.js";
  export default {
    data() {
      return {
        date: new Date,
        expendLegend: [{
            color: "#01b3c3",
            label: '充电支出',
            value: 0
          },
          {
            color: "#2bcbdb",
            label: '超时占位费',
            value: 0
          },
          {
            color: "#73e4ee",
            label: '省钱会员',
            value: 0
          },
          {
            color: "#a3f1f8",
            label: '券包套餐',
            value: 0
          },
          {
            color: "#bcfaf8",
            label: '零售支出',
            value: 0
          }
        ],
        expendData: {
          series: [{
            data: [{
                name: '充电支出',
                value: 0
              },
              {
                name: '超时占位费',
                value: 0
              },
              {
                name: '省钱会员',
                value: 0
              },
              {
                name: '券包套餐',
                value: 0
              },
              {
                name: '零售支出',
                value: 0
              },
            ]
          }]
        },
        expendDataOpts: {
          rotate: false,
          rotateLock: false,
          color: ["#01b3c3", "#2bcbdb", "#73e4ee", "#a3f1f8", "#bcfaf8"],
          padding: [5, 5, 5, 5],
          dataLabel: false,
          enableScroll: false,
          legend: {
            show: false,
            position: "bottom",
            lineHeight: 25
          },
          title: {
            name: "0.00",
            fontSize: 25,
            color: "#000"
          },
          subtitle: {
            name: "本月支出(元)",
            fontSize: 15,
            color: "#737169"
          },
          extra: {
            ring: {
              ringWidth: 30,
              activeOpacity: 0.5,
              activeRadius: 10,
              offsetAngle: 0,
              labelWidth: 15,
              border: false,
              borderWidth: 3,
              borderColor: "#FFFFFF"
            }
          }
        },
        powerDiscountLegend: [{
            color: "#fd4c03",
            label: '会员优惠',
            value: 0,
            percent: '0%'
          },
          {
            color: "#f97729",
            label: '卡券优惠',
            value: 0,
            percent: '0%'
          },
          {
            color: "#fc9a56",
            label: '商家优惠',
            value: 0,
            percent: '0%'
          },
          {
            color: "#fcba8f",
            label: '红包优惠',
            value: 0,
            percent: '0%'
          },
          {
            color: "#ffdebf",
            label: '碳积分优惠',
            value: 0,
            percent: '0%'
          }
        ],
        powerDiscountOpts: {
          rotate: false,
          rotateLock: false,
          color: ["#fd4c03", "#f97729", "#fc9a56", "#fcba8f", "#ffdebf"],
          // padding: [5,5,5,5],
          dataLabel: false,
          enableScroll: false,
          legend: {
            show: false,
            position: "bottom",
            lineHeight: 25
          },
          title: {
            name: "0.00",
            fontSize: 25,
            color: "#000"
          },
          subtitle: {
            name: "充电节省(元)",
            fontSize: 15,
            color: "#737169"
          },
          extra: {
            ring: {
              ringWidth: 30,
              activeOpacity: 0.5,
              activeRadius: 10,
              offsetAngle: 0,
              labelWidth: 15,
              border: false,
              borderWidth: 3,
              borderColor: "#FFFFFF"
            }
          }
        },
        powerDiscountData: {
          series: [{
            data: [{
                name: '会员优惠',
                value: 0
              },
              {
                name: '卡券优惠',
                value: 0
              },
              {
                name: '商家优惠',
                value: 0
              },
              {
                name: '红包优惠',
                value: 0
              },
              {
                name: '碳积分优惠',
                value: 0
              },
            ]
          }]
        },
        expendParmasMap: new Map([
          ['chargingExpense', '充电支出'],
          ['timeoutExpense', '超时占位费'],
          ['membershipExpense', '省钱会员'],
          ['voucherPackageExpense', '券包套餐'],
          ['retailExpense', '零售支出']
        ]),
        powerDiscountParmasMap: new Map([
          ['membershipCouponDiscount', '会员优惠'],
          ['cardCouponDiscount', '卡券优惠'],
          ['merchantCouponDiscount', '商家优惠'],
          ['redPacketCouponDiscount', '红包优惠'],
          ['carbonCreditCouponDiscount', '碳积分优惠']
        ]),
        totalMonitorData: {
          economizeMoney: 0,
          totalSpent: 0,
          totalPower: 0,
          totalOrders: 0,
        }
      };
    },
    filters: {

    },
    methods: {
      formatDate(date) {
        return moment(date).format('YYYY年MM月')
      },
      toFix(data, num) {
        if (!data) {
          return '0.00'
        }
        return Number(data).toFixed(num);
      },
      goToStation() {
        uni.reLaunch({
          url: '/pages/home-page/home-page'
        })
      },
      changeMonth(type) {
        if (type == 'sub') {
          this.date = moment(this.date).subtract(1, 'month');
        } else {
          this.date = moment(this.date).add(1, 'month');
        }
        this.getReportData();
      },
      bindDateChange(e) {
        this.date = e.detail.value
        this.getReportData();
      },

      async getReportData() {
        try {
          const startTs = moment(this.date).startOf('month').valueOf();
          const endTs = moment(this.date).endOf('month').valueOf();
          const {
            data
          } = await getOrderReport(startTs, endTs);
          if (data) {
            this.totalMonitorData = data;
            for (const key in data) {
              if (this.expendParmasMap.get(key)) {
                const findLegend = this.expendLegend.find(k => k.label == this.expendParmasMap.get(key));
                this.$set(findLegend, 'value', Number(data[key]).toFixed(2));
                const findData = this.expendData.series[0].data.find(k => k.name == this.expendParmasMap.get(key));
                this.$set(findData, 'value', Number(Number(data[key]).toFixed(2)));
              }
              if (this.powerDiscountParmasMap.get(key)) {
                const findLegend = this.powerDiscountLegend.find(k => k.label == this.powerDiscountParmasMap.get(
                  key));
                this.$set(findLegend, 'value', Number(data[key]).toFixed(2));
                const findData = this.powerDiscountData.series[0].data.find(k => k.name == this.powerDiscountParmasMap
                  .get(key));
                this.$set(findData, 'value', Number(Number(data[key]).toFixed(2)));
              }
            }
            this.expendDataOpts.title.name = this.expendLegend.reduce((pre, item) => {
              pre = Number(pre) + Number(item.value);
              return (pre).toFixed(2);
            }, 0)
            this.powerDiscountOpts.title.name = this.powerDiscountLegend.reduce((pre, item) => {
              pre = Number(pre) + Number(item.value);
              return (pre).toFixed(2);
            }, 0)
            this.powerDiscountLegend = this.powerDiscountLegend.map((ele, index, arr) => {
              const total = arr.reduce((sum, i) => {
                sum += Number(i.value || 0);
                return sum;
              }, 0)
              ele.percent = (ele.value / total * 100).toFixed(2) + '%';
              return ele;
            })
          } else {
            this.expendLegend.forEach(item => item.value = 0);
            this.expendDataOpts.title.name = '0.00';
            this.powerDiscountOpts.title.name = '0.00';
            this.powerDiscountLegend = this.powerDiscountLegend.map(item => {
              item.percent = 0 + '%';
              item.value = 0;
              return item;
            });
            this.powerDiscountData.series[0].data.forEach(item => item.value = 0);
            this.expendData.series[0].data.forEach(item => item.value = 0);
            this.totalMonitorData = {
              economizeMoney: 0,
              totalSpent: 0,
              totalPower: 0,
              totalOrders: 0,
            }
          }

          // consoel.log(this.expendDataOpts, this.e)
        } catch (e) {
          //TODO handle the exception
        }
      }
    },
    onShow() {
      this.getReportData();
    }
  }
</script>

<style lang="scss" scoped>
  page {
    background-color: #f5f5f5;
  }

  .block {
    position: relative;

    .bottom {
      position: absolute;
      z-index: -1;
      top: -10px;
      height: 23vh;
      width: 750rpx;
      background: linear-gradient(45deg, #00c3ffa6, #0b91ff);
    }

    .top,
    .class {
      width: 660rpx;
      margin: 20rpx auto;
      padding: 25rpx;
      border-radius: 20rpx;
      background-color: #fff;
    }

    .top {
      .timeSelect {
        display: flex;
        background: #f5f5f5;
        justify-content: space-between;
        align-items: center;
        width: 85%;
        margin: auto;
        border-radius: 40rpx;
        padding: 8rpx;

        .monthBtn {
          background-color: #fff;
          // border-radius: 25%;
          padding: 10rpx 0;
          display: flex;
          justify-content: center;
          align-items: center;
          width: 25%;
          border-radius: 25rpx;
        }

        .datePicker {
          width: 50%;

          .uni-input {
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }
      }

      .totalData {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-top: 25rpx;

        .label {
          color: #bcb6b3;
        }

        .money {
          font-size: 55rpx;
          font-weight: 600;
          color: #42a5f5;
          display: inline-block;
          margin: 20rpx 0rpx;
        }

        .power {
          display: inline-block;
          padding: 10rpx 15rpx;
          font-size: 30rpx;
          margin: 0rpx 0rpx 20rpx;
          color: #42a5f5;
          text-align: center;
          width: 50%;
          border-radius: 25rpx;
          background-color: rgba(66, 165, 245, 0.3);
        }

        .dataInfo {
          display: flex;
          width: 100%;
          justify-content: space-around;
          border-radius: 15rpx;
          padding: 20rpx 0;
          background-color: rgba(66, 165, 245, 0.1);

          .info {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;

            .number {
              font-size: 35rpx;
              font-weight: 600;
              display: inline-block;
              margin-bottom: 15rpx;
            }
          }
        }
      }
    }

    .class {
      .title {
        font-size: 30rpx;
        font-weight: 600;
      }

      .charts-box {
        width: 100%;
        height: 30vh;
      }

      .expendLegen {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;

        .item {
          width: 48%;
          display: flex;
          margin-bottom: 15rpx;
          justify-content: space-between;
          align-items: center;

        }
      }

      .powerDiscountLegen {
        .item {
          display: flex;
          align-items: center;
          margin-bottom: 15rpx;

          .line {
            display: inline-block;
            border-bottom: 2rpx dashed #bab9b5;
            height: 2rpx;
            width: 43%;
            margin: 0 20rpx;
          }
        }
      }

      .left {
        font-size: 30rpx;
      }

      .icon {
        display: inline-block;
        width: 25rpx;
        height: 25rpx;
        margin-right: 10rpx;
      }

      .value {
        font-weight: 600;
      }
    }

    .btn {
      position: fixed;
      bottom: 0;
      padding: 30rpx 0;
      width: 750rpx;
      background-color: #fff;

      .u-button {
        margin: auto !important;
        width: 700rpx !important;
        border-radius: 50rpx !important;
        height: 103rpx !important;

        .u-button__text {
          font-size: 37.5rpx !important;
        }
      }
    }
  }
</style>