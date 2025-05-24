<template>
  <div class="chart">
    <div class="volChart" style="width: 47%; height: 500px;">
      <Echart :options="volOptions" />
    </div>
    <div class="curChart" style="width: 47%; height: 500px;">
      <Echart :options="curOptions" />
    </div>

    <div class="tempChart" style="width: 47%; height: 500px;">
      <Echart :options="tempOptions" />
    </div>

    <div class="socChart" style="width: 47%; height: 500px;">
      <Echart :options="socOptions" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { EChartsOption } from 'echarts'
import { cloneDeep, set } from 'lodash-es';
import { IChart, curSeries, socSeries, tempSeries, volSeries } from './order-common.data';

const initOption = reactive<EChartsOption>({
  tooltip: {
      trigger: 'axis'
    },
    legend: {
      show: true
    },
    grid: {
      right: 80,
      left: 80
    },
    xAxis: {
      type: 'time'
    },
    yAxis: {
      type: 'value',
    },
    dataZoom: [
      {
        type: 'inside',
        start: 80,
        end: 100,
        textStyle: {
          width: 70,
          overflow: "break"
        },
      },
      {
        start: 80,
        end: 100,
        textStyle: {
          width: 70,
          overflow: "break"
        },
      }
    ],
    series: []
}) as EChartsOption

const volOptions = ref({})
const curOptions = ref({})
const tempOptions = ref({})
const socOptions = ref({})


const initOptionsSeries = (options: EChartsOption, seriesData: IChart) => {
  const keyArr = Object.keys(seriesData).filter(k => k.includes('Name'))
  console.log('keyArr', keyArr);
  
  const series = []
  keyArr.forEach(item => {
    // @ts-ignore
    series.push({
      name: seriesData[item],
      type: 'line',
      data: []
    })
  })
  set(options, 'series', series)
}

onMounted(() => {
  volOptions.value = cloneDeep(initOption)
  curOptions.value = cloneDeep(initOption)
  tempOptions.value = cloneDeep(initOption)
  socOptions.value = cloneDeep(initOption)
  initOptionsSeries(volOptions.value, volSeries)
  initOptionsSeries(curOptions.value, curSeries)
  initOptionsSeries(tempOptions.value, tempSeries)
  initOptionsSeries(socOptions.value, socSeries)

})


</script>

<style lang="scss" scoped>
.chart {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
</style>