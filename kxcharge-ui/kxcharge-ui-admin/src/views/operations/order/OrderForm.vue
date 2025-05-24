<template>
  <div class="info">
    <el-card class="detail">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <div class="title">
            <span class="codeId">充电单号：{{ infoData.seqNumber ?? '--' }}</span>
            <span style="margin: 0 10px;">
              <dict-tag :type="DICT_TYPE.STOP_CHARGE_TYPE" :value="infoData.stopReason" />
            </span>
            <span class="settleTypeDesc">正常单自动结算</span>
          </div>
          <el-button type="primary" @click="goBack">返回</el-button>
        </div>
      </template>
      <div class="content">
        <div class="right">
          <div v-for="item in infoLeftLabel" style="margin-bottom: 8px" :key="item.key">
            <span class="name"> {{ item.name }}： </span>
            <span v-if="item.type === 'dic'"><dict-tag :type="item.dictType" :value="infoData[item.key]" /></span>
            <span v-else> {{ infoData[item.key] ?? '--' }} </span>
          </div>
        </div>
        <div class="steps">
          <div class="step" v-for="data in periodOfTime" :key="data.key">
            <span class="cricle"></span>
            <span>{{ data.title ?? '--' }}</span>
            <span>{{ infoData[data.key] ?? '--' }}</span>
            <span>{{ infoData[data.keyTwo] ?? '--' }}</span>
          </div>
        </div>
        <div class="left">
          <div v-for="item in infoRightLabel" style="margin-bottom: 8px" :key="item.key">
            <span class="name"> {{ item.name ?? '--' }}： </span>
            <span v-if="item.type === 'dic'"><dict-tag :type="item.dictType" :value="infoData[item.key]" /></span>
            <span v-else> {{ infoData[item.key] ?? '--' }} </span>
          </div>
        </div>
      </div>
    </el-card>

    <el-card class="chargingDetail">
      <template #header>
        <div class="card-header">
          <span>充电明细</span>
        </div>
      </template>
      <!-- 使用summary-method自定义总计方法 -->
      <el-table :data="infoData.orderDetailsRespVOList" show-summary :summary-method="handleSummary">
        <el-table-column prop="ChargeTime" label="充电时间" show-overflow-tooltip>
          <template #default="scope">
            {{ formatDate(scope.row.startTime) }}~{{ formatDate(scope.row.endTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="price_type" label="时段类型">
          <template #default="scope">
            <dict-tag :type="DICT_TYPE.CHARGING_PERIOD" :value="scope.row.priceType" />
          </template>
        </el-table-column>
        <el-table-column prop="chargedPrice" label="电费" />
        <el-table-column prop="servicePrice" label="服务费" />
        <el-table-column prop="chargedPower" label="充电度数(度)" />
        <el-table-column prop="total" label="费用(元)">
          <template #default="scope">
            {{ toFix(scope.row.chargedPrice + scope.row.servicePrice) }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-card>
      <template #header>
        <div class="card-header">
          <span>充电费用明细</span>
        </div>
      </template>
      <div class="expenseDetail" v-for="item in expenseDetail" :key="item.key">
        <div class="title">
          <span>{{ item.title }}</span>
          <span class="tip">{{ item.desc ?? '--' }}</span>
        </div>
        <div class="num">
          {{ infoData[item.key] ?? '--' }}
        </div>
      </div>
    </el-card>
    <!-- <el-card>
      <template #header>
        <div class="card-header">
          <span>运营商应收信息</span>
        </div>
      </template>
      <div class="stationPreferential">
        <div class="title">
          <div>
            电站活动抵扣<span
              style="
                display: inline-block;
                border-radius: 20px;
                padding: 3px 10px;
                background-color: rgb(77, 140, 253);
                margin-left: 8px;
                color: #fff;
              ">运营商优惠</span
            >
          </div>
          <div class="tip"> 当前订单享受的运营商发起电站折扣活动抵扣金额 </div>
        </div>
        <div class="value">
          <span>电费: {{ infoData.operatorPreferentialElecMoney || '--' }}</span>
          <span>服务费: {{ infoData.operatorPreferentialServiceMoney || '--' }}</span>
        </div>
      </div>
      <div class="stationDiscount">
        <div class="title">
          <div> 卡卷抵扣 </div>
          <div class="tip"> 用户充电结算时使用的运营商卡券抵扣金额 </div>
        </div>
        <div class="value">
          <span>电费: {{ infoData.operatorDiscountElecMoney || '--' }}</span>
          <span>服务费: {{ infoData.operatorDiscountServiceMoney || '--' }}</span>
        </div>
      </div>
      <div class="totalPreferential">
        <div class="title">
          <div> 运营商应收 </div>
          <div class="tip">
            <span style="margin-right: 10px">
              电费:<span style="color: rgb(77, 140, 253)">{{ infoData.operatorChargeElecMoney || '--' }}</span>
            </span>
            <span>
              服务费: <span style="color: rgb(77, 140, 253)">{{ infoData.operatorChargeServiceMoney || '--' }}</span>
            </span>
          </div>
        </div>
        <div class="value">
          <span style="color: rgb(77, 140, 253)">{{ infoData.operatorChargeMoney || '--' }}元</span>
        </div>
      </div>
    </el-card> -->
    <!-- <tb-order-info [tradeNo]="infoData.startChargeSeq"></tb-order-info> -->
  </div>
</template>

<script setup>
import { hisInfoLeftLabel, hisperiodOfTime, hisInfoRightLabel, hisexpenseDetail } from "./order.data"
import * as OrderApi from "@/api/operations/order/index"
import { getOrgList } from "../order-common/order-common.data";
import { useTagsViewStore } from '@/store/modules/tagsView'
import { formatDate } from "@/utils/formatTime";
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'

const route = useRoute()
const infoLeftLabel = hisInfoLeftLabel
const periodOfTime = hisperiodOfTime
const infoRightLabel = hisInfoRightLabel
const expenseDetail = hisexpenseDetail
const infoData = ref({})
const orgList = ref([])
const orderId = route.query.seqNumber

const getOrderInfo = async () => {
  infoData.value = await OrderApi.getHisOrderInfo(orderId)
  infoData.value['orgName'] = orgList.value.find(k => k.value === infoData.value.orgId).label ?? ''
  infoData.value['upTime'] = infoData.value.createTime ? formatDate(infoData.value.createTime, 'YYYY-MM-DD') : '--'
  infoData.value['UpTime'] = infoData.value.createTime ? formatDate(infoData.value.createTime, 'HH:mm:ss') : '--'
  infoData.value['stTime'] = infoData.value.startTime ? formatDate(infoData.value.startTime, 'YYYY-MM-DD') : '--'
  infoData.value['StTime'] = infoData.value.startTime ? formatDate(infoData.value.startTime, 'HH:mm:ss') : '--'
  infoData.value['edTime'] = infoData.value.endTime ? formatDate(infoData.value.endTime, 'YYYY-MM-DD') : '--'
  infoData.value['EdTime'] = infoData.value.endTime ? formatDate(infoData.value.endTime, 'HH:mm:ss') : '--'
  infoData.value['settTime'] = infoData.value.payTime ? formatDate(infoData.value.payTime, 'YYYY-MM-DD') : '--'
  infoData.value['SettTime'] = infoData.value.payTime ? formatDate(infoData.value.payTime, 'HH:mm:ss') : '--'
}

const handleSummary = ({columns, data}) => {
  const sums = [];
  columns.forEach((column, index) => {
    if (index === 0) {
      const totalTime = data.reduce((pre, cur) => {
        return pre = pre + (cur.endTime - cur.startTime)
      }, 0)
      const time = handeTotalTime(totalTime)
      sums[index] =  `总计(总时长:${time})`
    }
    if (column.property.includes('Power')) {
      sums[index] = data.reduce((sum, item) => {
        return sum += item.chargedPower
      }, 0)
    }
    if(column.property === 'total') {
      sums[index] = data.reduce((sum, item) => {
        return sum += item.servicePrice + item.chargedPrice
      }, 0)
      sums[index] = Number(sums[index]).toFixed(2)
    }
  })
  return sums;
}

const toFix = (num) => {
 return Number(num).toFixed(2)
}
const handeTotalTime = (ms) => {
  if (isNaN(ms) || ms < 0) {
    return 0;
  }

  // 计算小时、分钟和秒
  const hours = Math.floor(ms / (1000 * 60 * 60));
  const minutes = Math.floor((ms % (1000 * 60 * 60)) / (1000 * 60));

  // 构建格式化字符串
  let formattedTime = '';
  if (hours > 0) {
    formattedTime += `${hours}小时`;
  }
  if (minutes > 0 || (hours === 0 && minutes === 0)) {
    formattedTime += `${minutes}分钟`;
  }

  return formattedTime;
}


const initData = async () => {
  orgList.value = await getOrgList()
}

const router = useRouter()
const tagsViewStore = useTagsViewStore()
const goBack = () => {
  router.push('/order/order')
  tagsViewStore.delView(route)
}

onMounted(() => {
  initData()
  getOrderInfo()
})
</script>

<style lang="scss" scoped>
@import url(../order-common/order-common-style.scss);
.expenseDetail {
    display: flex;
    justify-content: space-around;
    height: 60px;
    .title {
      display: flex;
      flex-direction: column;
      width: 500px;
      align-items: flex-start;
    }
    .num {
      width: 200px;
      display: flex;
      justify-content: flex-end;
      align-items: center;
    }
  }
  .stationDiscount, .totalPreferential, .stationPreferential {
    display: flex;
    justify-content: space-around;
    align-items: center;
    height: 60px;
    .title {
      width: 500px;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      align-items: flex-start;
    }
    .value {
      width: 200px;
      text-align: end;
    }
  }
  .tip {
    color: #ccc;
    font-size: 12px;
  }
  ::v-deep .el-tag {
    border-radius: 25px;
  }
</style>
