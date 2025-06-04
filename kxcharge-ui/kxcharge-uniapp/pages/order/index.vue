<template>
  <s-layout title="订单" navbar="normal" tabbar="pages/order/index" onShareAppMessage>
    <view class="block">
      <view class="tabs">
        <view class="item" v-for="(item,index) in tabList" :key="index" :class="{'activeItem': activeTab === item[0]}"
          @click="getTrottle(item)">
          <text class="label">{{item[1]}}</text>
        </view>
      </view>
      <uv-loading-icon text="加载中" v-if="isShowPageLoading"></uv-loading-icon>
      <scroll-view scroll-y="true" v-else @scrolltolower="nextPage" style="height:60vh;" :refresher-enabled="true"
        :refresher-triggered="nowRefsh" @refresherrefresh="refresh">
        <template v-if="orderList.length > 0">
          <view class="orderItem" v-for="(item,index) in orderList" :key="item.id">
            <view class="top">
              <view class="code">
                <!-- <text class="subCode" >订单编号：{{item.StartChargeSeq}}</text> -->
                <uni-tooltip :content="'订单编号：' +item.StartChargeSeq" placement="top" style="width: 80%;">
                  <uv-text class="subCode" :lines="1" :text="'订单编号：' +item.StartChargeSeq"
                    style="font-size: 30rpx; font-weight: 600;"></uv-text>
                </uni-tooltip>
                <text style="display: inline-block; width: 15%;">{{item.status}}</text>
              </view>
              <view class="time">{{item.StartTime}}</view>
            </view>
            <view class="bottom">
              <view class="item">
                <text class="label">实付金额</text>
                <text class="val">{{fixedNum(item.inPay,2)}}</text>
              </view>
              <view class="item">
                <text class="label">站点名称</text>
                <text class="val">{{item.stationName || ''}}</text>
              </view>
              <view class="item">
                <text class="label">车牌号</text>
                <text class="val">{{item.plateNumber || '未绑定车牌号'}}</text>
              </view>
            </view>
            <view class="opBtn">
              <view class="btn errorBtn" @click="toPay(item)" v-if="item.pay === 0 && item.startChargeSeqStat === 4"
                style="margin-right: 10rpx;">主动结算
              </view>
              <!-- <view class="btn" style="margin-right: 20rpx;" @click="goToTicket(item)">去开票</view> -->
              <view class="btn" style="margin-right: 20rpx;" @click="goToInfo(item)">查看详情</view>
              <view v-if="item.plateNumber == null" class="btn" style="background-color: #00b26a;" @click="open(item)">
                绑定车牌</view>
            </view>
          </view>
          <uv-loading-icon text="加载中" v-if="isShowLoading"></uv-loading-icon>
          <view class="loadTip" v-else>
            到底了...
          </view>
          <kx-tabbar-placeholder></kx-tabbar-placeholder>
        </template>

        <view class="noData" v-else>
          <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/noData.png" mode="aspectFit"></image>
          <text class="label">暂无数据</text>
        </view>
      </scroll-view>
      <uv-toast ref="uToast"></uv-toast>
      <uv-modal :show="showModal" content='是否结算该订单?' title='提示' :showCancelButton="true" class="modal"
        @cancel="showModal = false" @confirm="submitPay"></uv-modal>

      <uv-popup :show="showPop" mode="bottom">
        <view class="btn">
          <text @click="showPop = false">取消</text>
          <picker @change="selectPlateNum($event)" :range="columns" range-key="label">
            <text @click="showPop = false;isSelectCar = true" style="color: #1890ff;">选择车辆</text>
          </picker>
          <text @click="submitPlate">确定</text>
        </view>
        <view>
          <text class="label">车牌号</text>
          <view class="num">
            <text class="block" v-for="(item,index) in popBlockArr" :key="index">{{item || ''}}</text>
          </view>
        </view>
        <uv-keyboard :autoChange="true" ref="uKeyboard" mode="car" :show="true" :overlay="false"
          @change="clickKeyboard($event)" :tooltip="false" @backspace="backspace"></uv-keyboard>
      </uv-popup>

      <uv-modal :show="showbindCarModal" :content='modalContent' title='提示' :showCancelButton="true" class="modal"
        @cancel="showbindCarModal = false" @confirm="saveCarPlate"></uv-modal>
    </view>
  </s-layout>
</template>

<script>
  import {
    proactiveBilling
  } from "@/sheep/api/charge/order";
  import {
    convertSecondsToTime,
    throttle,
    Tips
  } from "@/sheep/util/utils";
  import moment from "dayjs";
  import * as OrderApi from "@/sheep/api/charge/order";
  import * as UserApi from "@/sheep/api/charge/member";
  export default {
    data() {
      return {
        tabMap: new Map([
          ['all', '全部'],
          [2, '进行中'],
          [4, '已完成'],
          [5, '待处理'],
        ]),
        statusMap: new Map([
          [1, '启动中'],
          [2, '进行中'],
          [3, '停止中'],
          [4, '已完成'],
          [5, '未知'],
        ]),
        tabList: [],
        activeTab: 'all',
        time: 0,
        pagetion: {
          page: 1,
          pageSize: 10
        },
        orderList: [],
        time2: 0,
        isShowLoading: false,
        isShowPageLoading: true,
        time3: 0,
        nowRefsh: false,
        getTrottle: undefined,
        time4: 0,
        showModal: true,
        payOrderId: '',

        orderId: null,
        showPop: false,
        popBlockArr: new Array(8),
        plateValue: '',
        isSelectCar: false,
        columns: [],
        plateNum: '未设置',
        showbindCarModal: false,
        modalContent: '',

        totalData: {
          economizeMoney: 0,
          totalMoney: 0,
          totalPower: 0
        },
      };
    },
    created() {
      this.getTrottle = throttle((data) => {
        this.active(data)
      }, 800)
    },
    methods: {
      fixedNum(num, fix) {
        if (!num) {
          return '0.00'
        }
        return Number(num).toFixed(fix)
      },
      clickKeyboard(event) {
        if (this.plateValue.length >= 8) {
          return;
        }
        this.plateValue += event;
        this.plateValue.split("").forEach((k, index) => {
          this.popBlockArr[index] = k;
        });
      },
      backspace() {
        this.popBlockArr = this.popBlockArr.map((item, index, arr) => {
          if (!arr[index + 1]) item = '';
          return item;
        });
      },
      selectPlateNum(e) {
        const {
          detail
        } = e;
        this.plateNum = this.columns[detail.value].label;

        this.bindCarconfirm();
      },
      open(orderInfo) {
        this.showPop = true;
        this.orderId = orderInfo.id;
        console.log(this.orderId)
      },
      getCarList() {
        this.columns = []
        if (!sheep.$store('user').userInfo.token) return
        UserApi.getMyCar().then(res => {
          const {
            data
          } = res
          // if (statusCode === 200) {
          const find = data.find(item => item.isDefault);
          console.log('find', find)
          this.plateNum = this.initPlate ? this.initPlate : (find?.plateNumber || '未设置')
          data.forEach(item => {
            this.columns.push({
              label: item.plateNumber,
              id: item.id
            })
          })
          // }
        }).catch(err => {
          console.log('err: ', err);
        })
      },
      submitPlate() {
        if (!this.plateValue) {
          Tips({
            icon: 'none',
            title: '输入的车牌号是否为空'
          })
          return;
        }
        // const plateArr = this.plateValue.split("")
        if (this.plateValue.length !== 8 && this.plateValue.length !== 7) {
          Tips({
            icon: 'none',
            title: '输入的车牌号有误，请重新输入'
          })
          return;
        }
        this.showPop = false;
        this.bindCarconfirm();
      },
      bindCarconfirm() {
        let plate = this.isSelectCar ? this.plateNum : this.plateValue;
        this.modalContent = `是否为此订单绑定车牌【${plate}】？`;
        this.showbindCarModal = true;
      },
      saveCarPlate() {
        let plate = this.isSelectCar ? this.plateNum : this.plateValue;
        OrderApi.licensePlateBinding(this.orderId, plate).then(res => {
          this.orderId = null;
          this.popBlockArr = new Array(8);
          this.plateValue = '',
            this.isSelectCar = false;

          this.showbindCarModal = false;
          Tips({
            icon: 'none',
            title: '绑定成功！'
          })
          this.refresh();
        }).catch(err => {
          console.log(err)
          this.showbindCarModal = false;
        })
      },

      active(data) {
        this.isShowPageLoading = true;
        this.activeTab = data[0];
        this.orderList = [];
        this.pagetion.page = 1;
        clearInterval(this.time4);

        this.getData();
        if (this.activeTab === '2') {
          this.time4 = setInterval(() => {
            this.getData();
          }, 5000)
        }
      },
      toPay(data) {
        this.payOrderId = data.id;
        this.showModal = true;
      },

      submitPay() {
        proactiveBilling(this.payOrderId).then(res => {
          if (res.statusCode === 200) {
            Tips({
              title: res.data.msg,
              icon: 'success'
            })
            this.orderList = [];
            this.getData();
          } else {
            Tips({
              title: res.data.detail,
              icon: 'none'
            })
          }
          this.showModal = false;
        }).catch(err => {
          Tips({
            title: err.error.detail,
            icon: 'none'
          })
          this.showModal = false;
        })
      },
      nextPage() {
        if (this.isShowLoading) {
          clearTimeout(this.time3);
          this.time3 = setTimeout(() => {
            this.pagetion.page += 1;
            this.getData();
          }, 2000)

        }
      },

      refresh() {
        this.nowRefsh = true;
        this.pagetion.page = 1;
        this.getData();
      },

      goToInfo(item) {
        if (item.status !== '进行中') {
          uni.navigateTo({
            url: `/pages/order-info/order-info?id=${item.seqNumber}`
          })
        } else {
          uni.navigateTo({
            url: `/pages/charging/index?seqNumber=${item.seqNumber}`
          })
        }
      },

      async getData() {
        try {
          const {
            data
          } = await OrderApi.getOrderList(this.pagetion, this.activeTab);
          if (data) {
            const {
              list,
              total
            } = data;
            if (this.nowRefsh || this.isShowPageLoading) {
              this.orderList = [];
            }
            this.isShowLoading = this.hasNext(total);
            if (this.activeTab === '2') {
              this.orderList = list?.map(item => this.formatData(item))
            } else {
              list?.forEach(item => {
                this.orderList.push(this.formatData(item));
              })
            }
            this.nowRefsh = false;
            this.isShowPageLoading = false;
          }
          const {
            data: result
          } = await OrderApi.getTotalOrder();
          result && (this.totalData = result);

        } catch (e) {
          this.isShowPageLoading = false;
          this.$nextTick(() => {
            this.$refs.uToast.show({
              message: '数据异常，稍后重试',
              type: 'error',
              duration: '3000'
            })
          })
        }

      },

      hasNext(total) {
        return this.pagetion.page * this.pagetion.pageSize < total
      },

      formatData(item) {
        item['status'] = this.statusMap.get(item.orderStatus);
        item['StartTime'] = moment(item.startTime).format("YYYY-MM-DD HH:mm:ss");
        item['EndTime'] = item['endTime'] ? moment(item.endTime).format("YYYY-MM-DD HH:mm:ss") : '';
        item['StartChargeSeq'] = item['seqNumber'].substring(9);
        item['interval'] = item['endTime'] ? convertSecondsToTime(item.startTime, item.endTime) :
          convertSecondsToTime(item.startTime, +new Date());
        return item;
      }
    },
    onLoad(options) {
      this.tabList = Array.from(this.tabMap);
      console.log(this.tabList)
      if (options.type) {
        this.activeTab = options.type
      }
      this.isShowPageLoading = true;

    },
    onShow() {
      this.orderList = [];
      this.getData();
      this.getCarList();
      if (this.activeTab === '2') {
        this.time4 = setInterval(() => {
          this.getData();
        }, 5000)
      }
    },
    onHide() {
      clearTimeout(this.time);
      clearTimeout(this.time2);
      clearTimeout(this.time3);
      clearTimeout(this.time4);
    },
    beforeDestroy() {
      clearTimeout(this.time);
      clearTimeout(this.time2);
      clearTimeout(this.time3);
      clearTimeout(this.time4);
    }
  }
</script>

<style lang="scss" scoped>
  .block {
    background-color: #f5f9ff;

    .header {
      width: 640rpx;
      margin: auto;
      padding: 30rpx;
      border-top-right-radius: 25rpx;
      border-top-left-radius: 25rpx;
      background: linear-gradient(45deg, #00c3ffa6, #0b91ff);
      font-size: 30rpx;
      color: #fffc;
      position: relative;

      .saveMoney {
        color: #fff;
        margin: 20rpx 0;

        .money {
          font-size: 45rpx;
          font-weight: 600;
        }
      }

      .saveInfo {
        display: flex;

        .vlaueData {
          color: #fff;
        }
      }

      .billInfo {
        position: absolute;
        right: 20rpx;
        top: 30rpx;
        font-size: 25rpx;
        border-radius: 25rpx;
        color: #fff;
        background-color: #a5d2f78c;
      }
    }

    .tabs {
      display: flex;
      justify-content: space-between;
      // background-color: #fff;
      padding: 0 15rpx;

      .item {
        flex: 1;
        display: flex;
        padding-top: 15rpx;
        display: flex;
        flex-direction: column;
        align-items: center;

        &::after {
          width: 25rpx;
          height: 5rpx;
          content: "";
          display: inline-block;
          margin-top: 15rpx;
        }
      }

      .activeItem {

        .label {
          color: #229bff;
        }

        &::after {
          background-color: #229bff;
        }

      }
    }

    .orderItem {
      padding: 25rpx;
      width: 650rpx;
      background-color: #fff;
      box-shadow: 0rpx 0rpx 8rpx 5rpx rgba(172, 172, 172, 0.3);
      margin: 30rpx auto;
      color: #aaaaaa;
      font-size: 30rpx;
      border-radius: 15rpx;

      .top {
        border-bottom: 1rpx solid #e2e2e2;
        padding-bottom: 20rpx;
        color: #aaaaaa;

        .code {
          display: flex;
          justify-content: space-between;
          margin-bottom: 20rpx;

          .uv-text__value {
            font-weight: 600 !important;
            font-size: 30rpx !important;
            color: #000 !important;
          }
        }
      }

      .bottom {
        .item {
          color: #aaaaaa;
          margin: 15rpx 0;

          .label {
            margin-right: 20rpx;
          }
        }
      }

      .opBtn {
        display: flex;
        justify-content: flex-end;

        .btn {
          color: #fff;
          padding: 15rpx 25rpx;
          border-radius: 50rpx;
          background-color: #229bff;
        }

        .errorBtn {
          background-color: #f56c6c;
        }
      }
    }

    .uv-loading-icon {
      margin-top: 10rpx;
    }

    .loadTip {
      display: flex;
      justify-content: center;
      margin-top: 10rpx;
      color: #aaaaaa;
    }

    .noData {
      position: relative;

      image {
        width: 750rpx;
        height: 50vh;
      }

      .label {
        color: #aaaaaa;
        display: flex;
        justify-content: center;
        position: absolute;
        width: 750rpx;
        top: 35vh;
      }
    }
  }

  .uv-slide-up-enter-active {


    .uv-popup__content {
      padding: 35rpx 35rpx 450rpx;
      position: relative;

      .label {
        font-size: 22rpx;
        color: #797979;
      }

      .btn {
        color: #1890ff;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 50rpx;
      }

      .num {
        display: flex;
        justify-content: space-around;
        align-items: center;
      }

      .block {
        display: inline-block;
        margin-right: 10rpx;
        width: 46rpx;
        background-color: #f2f2f2;
        height: 50rpx;
        line-height: 50rpx;
        text-align: center;
      }

      .uv-popup {
        position: absolute;
        bottom: 0;

        .uv-popup__content {
          padding: 0;
        }
      }
    }
  }
</style>