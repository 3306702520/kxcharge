<template>
  <div>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-change="changeTab">
      <el-tab-pane label="上传实时监测数据" name="realData" /> 
      <el-tab-pane label="BMS需求和充电机输出" name="bmsOut" />
      <el-tab-pane label="充电过程BMS信息" name="bmsInfo" />
    </el-tabs>
    <Table
      :columns="tableColumns"
      :data="tableObject.tableList"
      :loading="tableObject.loading"
      :pagination="{
        total: tableObject.total
      }"
      v-model:pageSize="tableObject.pageSize"
      v-model:currentPage="tableObject.currentPage"
    />
  </div>
</template>

<script setup>
import { realtimeColumns, bmsInfoColumns, bmsOutColumns } from "./order-common.data";
const activeName = ref('realData')
const tableColumns = ref(realtimeColumns.tableColumns)
const tableObject = reactive({
  tableList: [],
  loading: false,
  total: 0,
  pageSize: 10, 
  currentPage: 1
}) 

const changeTab = () => {
  switch (activeName.value) {
    case 'realData':
      tableColumns.value = realtimeColumns.tableColumns
      break
    case 'bmsOut':
      tableColumns.value = bmsOutColumns.tableColumns
      break
    default:
      tableColumns.value = bmsInfoColumns.tableColumns
      break
  }
  tableObject.currentPage = 1
}

onMounted(() => {
  console.log(`output->111111111111`)
})



</script>

<style lang="scss" scoped>
</style>