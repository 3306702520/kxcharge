<template>
  <div>
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>{{ carTitle }}</span>
          <el-radio-group v-model="type">
            <el-radio-button label="图表" />
            <el-radio-button label="列表" />
          </el-radio-group>
        </div>
      </template>
      <div class="chart" v-if="type === '图表'">
        <OrderChart />
      </div>
      <div class="chart" v-if="type === '列表'">
        <OrderTable />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import OrderChart from './OrderChart.vue'
import OrderTable from './OrderTable.vue'
const type = ref('图表')

const props = defineProps({
  tradeNo: {
    type: String
  },
  carTitle: {
    type: String,
    default: '历史监测数据列表'
  }
})
</script>

<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
