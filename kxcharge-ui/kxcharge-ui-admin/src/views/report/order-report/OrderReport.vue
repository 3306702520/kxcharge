<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="formData"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="时间" prop="time">
        <el-date-picker
          v-model="formData.time"
          type="daterange"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          value-format="x"
        />
      </el-form-item>

      <el-form-item label="电站" prop="stationId">
        <el-select v-model="formData.stationId" placeholder="请选择" clearable>
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
      <el-table-column label="时间段">
        <template #default="scope">
          <div style="display: flex; align-items: center; color: #4d8cfd;">
            {{ scope.row.time }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="chargedCount" label="充电次数" />
      <el-table-column prop="chargedPower" label="充电电量" />
      <el-table-column prop="chargedPrice" label="充电金额" />
      <el-table-column prop="servicePrice" label="服务费用" />
    </el-table>
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive, ref,computed,onMounted } from 'vue';
import * as StationApi from '@/api/operations/station';
import download from '@/utils/download';
import * as OrderReportApi from '@/api/report/orderReport/index'
import moment from 'moment';
const startDate = moment().month(moment().month()).startOf('month').valueOf()
const endDate = moment().month(moment().month()).endOf('month').valueOf();

const message = useMessage() // 消息弹窗
const loading = ref(false)
const tableData = ref([])

const formData=reactive({
  stationId: null,
  time:[startDate,endDate],
})
const queryParams=computed(()=>{
  if (formData.time.length>1) {
    return {
     stationId: formData.stationId,
     startTime:formData.time[0],
     endTime:formData.time[1]
    }
  }else{
    return {
     stationId: formData.stationId,
     startTime:0,
     endTime:0
    }
  }
})
const queryFormRef = ref() // 搜索的表单
const stationList = ref([])
const exportLoading = ref(false) // 导出的加载中
const getList = async () => {
 // console.log(`output->queryParams`,queryParams,queryParams.value,moment(startDate).format('yyyy/m/d h:mm:ss'),moment(endDate).format('yyyy/m/d h:mm:ss'))
 // console.log(`output->queryParams`,moment(queryParams.value.startTime).format('YYYY-MM-DD h:mm:ss'),moment(queryParams.value.endTime).format('YYYY-MM-DD h:mm:ss'))
  loading.value = true
  try {
    tableData.value = await OrderReportApi.getOrderReport(queryParams.value)
    console.log(`output->data`,tableData.value)
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
    const data = await OrderReportApi.exportOrderReport(queryParams.value)
    console.log("导出",data)
    download.excel(data, '账单统计报表.xls')
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
