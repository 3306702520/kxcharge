<template>
  <div class="info">
    <el-alert :title="'异常原因：' + infoData.errorMessage" type="error" :closable="false" />
    <el-card class="detail">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <div class="title">
            <span class="codeId">充电单号：{{ infoData.seqNumber || '--' }}</span>
            <span class="chargingStatus">充电结束</span>
            <span class="settleTypeDesc">手动处理</span>
          </div>
          <el-button type="primary" @click="goBack">返回</el-button>
        </div>
      </template>
      <div class="content">
        <div class="right">
          <div v-for="item in abInfoLeftLabel" style="margin-bottom: 8px" :key="item.key">
            <span class="name"> {{ item.name || '--' }}： </span>
            <span> {{ infoData[item.key] || '--' }} </span>
          </div>
        </div>
        <div class="steps">
          <div class="step" v-for="data in periodOfTime" :key="data.key">
            <span class="cricle"></span>
            <span>{{ data.title || '--' }}</span>
            <span>{{ formatDate(infoData[data.key], 'YYYY-MM-DD') || '--' }}</span>
            <span>{{ formatDate(infoData[data.key], 'HH:mm') || '--' }}</span>
          </div>
        </div>
        <div class="left">
          <div v-for="item in abInfoRightLabel" style="margin-bottom: 8px" :key="item.key">
            <span class="name"> {{ item.name || '--' }}： </span>
            <span> {{ infoData[item.key] || '--' }} </span>
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
      <div class="content">
        <div class="item">
          <span class="title">充电总时长</span>
          <span class="value">{{ infoData.chargedTime || '--' }}</span>
        </div>
        <div class="item">
          <span class="title">充电总电量(度)</span>
          <span class="value">{{ infoData.chargedPower || '--' }}</span>
        </div>
        <div class="item">
          <span class="title">订单总金额(元)</span>
          <span class="value">{{ infoData.inPay || '--' }}</span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { formatDate } from '@/utils/formatTime'
import { abnormalInfoLeftLabel, abnormalInfoRightLabel } from './pending-order.data'
import { hisperiodOfTime } from '../order/order.data'
import * as OrderApi from '@/api/operations/order/index'
import { useTagsViewStore } from '@/store/modules/tagsView'
import { getOrgList } from "../order-common/order-common.data";

const route = useRoute()
const orderId = route.query.seqNumber
const abInfoLeftLabel = abnormalInfoLeftLabel
const abInfoRightLabel = abnormalInfoRightLabel
const periodOfTime = hisperiodOfTime
const infoData = ref({})
const orgList = ref([])

const getOrderInfo = async () => {
  orgList.value = await getOrgList()
  infoData.value = await OrderApi.getHisOrderInfo(orderId)
  console.log(`output->infoData.value`,infoData.value)
  infoData.value['orgName'] = orgList.value.find(k => k.value === infoData.value.orgId).label ?? ''
  infoData.value['upTime'] = infoData.value.createTime ? formatDate(infoData.value.createTime, 'YYYY-MM-DD') : '--'
  infoData.value['UpTime'] = infoData.value.createTime ? formatDate(infoData.value.createTime, 'HH:mm:ss') : '--'
  infoData.value['stTime'] = infoData.value.startTime ? formatDate(infoData.value.startTime, 'YYYY-MM-DD') : '--'
  infoData.value['StTime'] = infoData.value.startTime ? formatDate(infoData.value.startTime, 'HH:mm:ss') : '--'
  infoData.value['edTime'] = infoData.value.endTime ? formatDate(infoData.value.endTime, 'YYYY-MM-DD') : '--'
  infoData.value['EdTime'] = infoData.value.endTime ? formatDate(infoData.value.endTime, 'HH:mm:ss') : '--'
  infoData.value['settTime'] = infoData.value.updateTime ? formatDate(infoData.value.updateTime, 'YYYY-MM-DD') : '--'
  infoData.value['SettTime'] = infoData.value.updateTime ? formatDate(infoData.value.updateTime, 'HH:mm:ss') : '--'
}


const router = useRouter()
const tagsViewStore = useTagsViewStore()
const goBack = () => {
  router.push('/order/pending-order')
  tagsViewStore.delView(route)
}

onMounted(() => {
  getOrderInfo()
})
</script>

<style lang="scss" scoped>
@import url(../order-common/order-common-style.scss);
.chargingDetail {
  .content {
    width: 100%;
    .item {
      // width: 33%;
      .title {
        // display: inline-block;
        height: 60px;
      }
      .value {
        display: flex;
        width: 100%;
        height: 60px;
        // background-color: #ccc;
      }
    }
  }
}
.el-alert {
  margin-bottom: 10px;
}
</style>
