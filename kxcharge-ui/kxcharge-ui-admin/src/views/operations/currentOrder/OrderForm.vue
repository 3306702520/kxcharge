<template>
  <div class="info">
    <el-card class="detail" style="margin-bottom: 0;">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <div class="title" >
            <span class="codeId" style="margin-right: 10px">充电单号：{{ infoData.seqNumber || '--' }}</span>
            <span class="settleTypeDesc">充电中</span>
          </div>
          <el-button type="primary" @click="goBack">返回</el-button>
        </div>
      </template>
      <div class="content">
        <div class="right">
          <div v-for="item in reInfoLeftLabel" style="margin-bottom: 8px" :key="item.key">
            <span class="name"> {{ item.name || '--' }}： </span>
            <span style="font-size: 14px;" v-if="item.type === 'dic'">
              <dict-tag :type="item.dictType" :value="infoData[item.key]" />
            </span>
            <span style="font-size: 14px;" v-else> {{ infoData[item.key] || '--' }} </span>
            <span v-if="item.unit"> {{ item.unit }} </span>
          </div>
        </div>
        <div class="left">
          <div v-for="item in erInfoRightLabel" style="margin-bottom: 8px" :key="item.key">
            <span class="name"> {{ item.name }}： </span>
            <span style="font-size: 14px;" v-if="item.type === 'dic'">
              <dict-tag :type="item.dictType" :value="infoData[item.key]" />
            </span>
            <span style="font-size: 14px;" v-else> {{ infoData[item.key] || '--' }} </span>
            <span v-if="item.unit"> {{ item.unit }} </span>
          </div>
        </div>
      </div>
    </el-card>
    <div class="card">
      <div class="iconValue">
        <div class="item" v-for="item in cardMonitor" :key="item.key">
          <img :src="item.icon" />
          <span class="label">{{ item.name }}：</span>
          <span class="value">{{ infoData[item.key] || '--' }}</span>
          <span class="unit">{{ item.unit }}</span>
        </div>
      </div>
      <div class="start">
        <span style="font-size: 18px">{{ formatDate(infoData.startTime, 'YYYY-MM-DD HH:mm:ss') || '--' }} 开始充电</span>
      </div>
    </div>
<!--    <OrderCommon :carTitle="'实时状态监测'" />-->

  </div>
</template>

<script setup>
import {
  currentInfoLeftLabel,
  currentErInfoRightLabel,
  currentCardMonitor
} from './current-order.data'
import OrderCommon from '../order-common/OrderCommon.vue'
import * as OrderApi from '@/api/operations/order'
import { formatDate } from '@/utils/formatTime';
import { getCompanyList, getOrgList } from '../order-common/order-common.data'
import { useTagsViewStore } from '@/store/modules/tagsView'

const route = useRoute()
const router = useRouter()
const reInfoLeftLabel = currentInfoLeftLabel
const erInfoRightLabel = currentErInfoRightLabel
const cardMonitor = currentCardMonitor
const infoData = ref({})
const seqNumber = route.query.id
const orgList = ref([])
const tagsViewStore = useTagsViewStore()

const getOrderInfo = async () => {
  const data = await OrderApi.getRealtimeOrderInfo(seqNumber)
  data['orgName'] = orgList.value.find(k => k.value === data.orgId)?.label ?? ''
  infoData.value = data
}

const initData = async () => {
  orgList.value = await getOrgList()
  getOrderInfo()
}

const goBack = () => {
  router.push('/order/current-order')
  tagsViewStore.delView(route)
}


onMounted(() => {
  initData()
})
</script>

<style lang="scss" scoped>
@import url(../order-common/order-common-style.scss);
.content {
  justify-content: flex-start !important;
  margin-left: 80px;
  .right {
    width: 30%;
  }
}
.card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  padding: 20px;
  margin: 10px 0;
  .iconValue {
    display: flex;
    justify-content: flex-start;
    .item {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      margin-right: 50px;
      .label {
        margin-right: 15px;
        margin-left: 30px;
      }
    }
  }
}
</style>
