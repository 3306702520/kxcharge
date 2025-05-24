<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="时间" prop="month">
        <el-date-picker
          v-model="queryParams.month"
          type="month"
          placeholder="请选择月份"
        />
      </el-form-item>
      <el-form-item label="电站" prop="stationId">
        <el-select v-model="queryParams.stationId" placeholder="请选择" clearable>
          <el-option v-for="item in stationList" :key="(item as any).id" :label="(item as any).stationName" :value="(item as any).id" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="handleQuery" type="primary"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['report:station:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table :data="tableData" v-loading="loading" stripe show-summary style="width: 100%">
      <el-table-column type="index" label="序号" width="60px" />
      <el-table-column label="时间">
        <template #default="scope">
          <div style="display: flex; align-items: center; color: #4d8cfd;">
            {{ scope.row.date }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="electricQuantity" label="充电量(度)" />
      <el-table-column prop="chargeNum" label="充电次数(次)" />
      <el-table-column prop="chargingTime" label="充电时长(小时)" />
      <el-table-column prop="electricCharge" label="电费(元)" />
      <el-table-column prop="serviceCharge" label="服务费(元)" />
      <el-table-column prop="totalOrderAmount" label="订单总金额(元)" />
      <el-table-column prop="totalDiscountsElectricCharge" label="优惠电费(元)" />
      <el-table-column prop="totalDiscountsServiceCharge" label="优惠服务费(元)" />
      <el-table-column prop="totalDiscountsOrderAmount" label="总优惠费用(元)" />
      <el-table-column prop="actuallyPaid" label="实收金额(元)" />
    </el-table>
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import * as StationApi from '@/api/operations/station'
import * as StationReportApi from '@/api/report/stationReport/index'
import download from '@/utils/download';
import moment from 'moment';

const message = useMessage() // 消息弹窗

const loading = ref(false)
const tableData = ref([])
const queryParams = reactive({
  month: null,
  stationId: null
})
const queryFormRef = ref() // 搜索的表单

const stationList = ref([])
const exportLoading = ref(false) // 导出的加载中
const getList = async () => {
  console.log(`output->queryParams`,queryParams)
  loading.value = true
  try {
    if (queryParams.month) {
      // @ts-ignore
      queryParams.month = moment(queryParams.month).format('YYYY-MM')
    }
    const data = await StationReportApi.getStationReport(queryParams)
    tableData.value = data.map(item => {
      return {
        ...item,
        date:  `${item.day[0]}-${item.day[1]}-${item.day[2]}`
      }
    })
    console.log(`output->data`,data)
  } finally {
    loading.value = false
  }
}

/**
 * 查询
 */
const handleQuery = () => {
  getList()
}

/**
 * 重置
 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}


/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await StationReportApi.exportStationReport(queryParams)
    download.excel(data, '站点分析报表.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const getStationList = async () => {
  try {
    const station = await StationApi.getStationAll()
    stationList.value = station
  } catch (error) {}
}

onMounted(() => {
  getStationList()
  getList()
})

</script>

<style lang="scss" scoped>

</style>
