<template>
  <div class="index-home">
    <el-card class="m-b-10px !rounded-12px">
      <div class="flex p-b-15px">
        <span class="m-r-5px h-22px w-5px bg-blue"></span>
        充电数据
      </div>
      <el-row :gutter="12">
        <el-col class="!flex-1" v-for="item, key of chargeData" :key="key">
          <el-card shadow="always" class="!rounded-12px !text-#fff" :body-style="{padding: '20x 15px', backgroundColor: item.bg}">
            <div class="flex flex-justify-between flex-items-center text-14px">
              {{ item.title }}
              <el-tag
                class="mx-1"
                effect="plain"
                size="small"
              >
                今日
              </el-tag></div>
            <div class="m-5px flex flex-justify-between flex-items-end">
              <div class="text-28px font-700">{{ item.value }}</div>
              <div class="text-14px">
                {{ item.compare }}%
                <el-icon>
                  <CaretTop class="text-16px text-red" v-if="item.value > item.nextValue" />
                  <CaretBottom v-else />
                </el-icon>
              </div>
            </div>
            <el-divider style="margin: 10px 0" />
            <div class="flex flex-justify-between flex-items-center text-14px">
              <span>昨日数据</span>
              <span>{{ item.nextValue }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

    <el-row :gutter="12" class="m-b-10px">
      <el-col :span="14">
        <el-card class="!rounded-12px">
          <div class="flex p-b-10px">
            <span class="m-r-5px h-22px w-5px bg-blue"></span>
            实时数据
          </div>
          <el-divider border-style="dashed" style="margin: 8px 0" />
          <el-row>

            <el-col class="flex items-center justify-center p-b-20px p-t-20px !flex-1" v-for="item, key of realTimeData" :key="key">
              <div class="m-r-8px h-50px w-50px">
                <img :src="item.url" style="width: 100%; height: 100%;" />
              </div>
              <div>
                <div class="text-18px line-height-30px">{{ item.title }}</div>
                <div class="text-30px font-700">{{ item.value }}</div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card class="!rounded-12px">
          <div class="flex p-b-10px">
            <span class="m-r-5px h-22px w-5px bg-blue"></span>
            快捷入口
          </div>
          <el-divider border-style="dashed" style="margin: 8px 0" />
          <el-row>
            <el-col class="flex justify-center p-b-15px p-t-15px !flex-1" v-for="item, key of appData" :key="key">
              <router-link :to="item.url" custom v-slot="{ navigate }">
                <div class="flex flex-col cursor-pointer items-center justify-center" @click="navigate">
                  <div class="box-border h-50px w-50px rounded-8px p-8px text-20px" :style="{background: item.bg}">
                    <img class="rounded-8px" style="width: 100%; height: 100%;" :src="item.avater" />
                  </div>
                  <div class="text-15px line-height-31px">{{ item.name }}</div>
                </div>
              </router-link>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="!rounded-12px">
      <div class="flex items-center justify-between p-b-10px">
        <div class="flex">
          <span class="m-r-5px h-22px w-5px bg-blue"></span>
          场站经营数据
        </div>
        <div class="flex">
          <el-select v-model="chartA.station" class="m-r-2" placeholder="全部电站" @change="getStationData()">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-radio-group class="m-0" v-model="chartA.date" @change="getStationData()">
            <el-radio-button v-for="item, key of stationOptions" :label="item.label" :key="key" />
          </el-radio-group>
        </div>
      </div>
      <el-divider border-style="dashed" style="margin: 8px 0" />

      <el-row :gutter="12">
        <el-col :span="14">
          <div class="chart-wrapper">
            <div class="chart-title">充电趋势</div>
            <Echart :options="chatOptionsA" height="300px" />
          </div>
        </el-col>
        <el-col :span="10">
          <div class="chart-wrapper">
            <div class="chart-title">时段分布</div>
            <Echart :options="chatOptionsB" height="300px" />
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { chatOptionsOriginA, chatOptionsOriginB } from "./index3-common/echarts-origin"
import { appDataOrigin, chargeDataOrigin, realTimeDataOrigin, stationOptionsOrigin } from "./index3-common/data-origin"
import { cloneDeep } from 'lodash-es'
import * as HomeApi from '@/api/home/home/index'
import * as StationApi from '@/api/operations/station/index'
import * as UserApi from '@/api/system/user/index'
import { useCache } from "@/hooks/web/useCache"
const options = reactive([
  {label: "全部", value: 0}
])

const chargeData = reactive(chargeDataOrigin);

const realTimeData = reactive(realTimeDataOrigin);

const appData = reactive(appDataOrigin);

const stationOptions = reactive(stationOptionsOrigin);

const chartA = reactive({
  station: 0,
  date: "今日"
})

const trendKeyMap = new Map()
  .set("充电次数", "orderCount")
  .set("充电量", "chargedPower")
  .set("订单金额", "orderAmount")


// const chartB = reactive({
//   station: 0,
//   date: "今日"
// })

const chatOptionsA = reactive(cloneDeep(chatOptionsOriginA));
const chatOptionsB = reactive(cloneDeep(chatOptionsOriginB));

// setInterval(function () {
//   for (var i = 0; i < 5; i++) {
//     chatOptionsA.series[0].data.shift();
//     chatOptionsB.series[0].data.shift();
//     chatOptionsA.series[0].data.push(randomData());
//     chatOptionsB.series[0].data.push(randomData());
//   }
// }, 3000);
const getHomeData = async () => {
  try {
    const data = await Promise.all([
      HomeApi.getGunData(),
      HomeApi.getChargeData()
    ]);
    const gunData = data[0]
    const chargeNowData = data[1]
    handleGun(gunData)
    handleChargeData(chargeNowData)
  } catch (error) {
    console.log('error', error);
  }
}

const handleData = (data, callbackFun) => {
  for (const key in data) {
    callbackFun(data, key)
  }
}

const handleGun = (gunData) => {
  handleData(gunData, (data, k) => {
    const find = realTimeData.find(val => val.key === k)
    if (find) {
      find.value = data[k]
    }
  })
}

const handleChargeData = (chargeNowData) => {
  handleData(chargeNowData, (data, k) => {
    const find = chargeData.find(val => k.includes(val.key))
    if (find) {
      const key = k.includes('yesterday') ? 'nextValue' : 'value'
      find[key] = data[k]
    }
  })
  chargeData.forEach(item => {
    item.compare = Number(item.nextValue) > 0 ? (Math.abs(Number(item.value) - Number(item.nextValue)) / Number(item.nextValue)).toFixed(2) : '100'
  })
}

const getStationAll = async () => {
  const data = await StationApi.getStationAll()
  data.forEach(item => {
    options.push({
      label: item.stationName,
      value: item.id
    })
  })
}

const getStationData = async () => {
  const date = stationOptions.find(k => k.label == chartA.date)?.value
  const querys = {
    type: date
  }
  if (chartA.station) {
    querys['stationId'] = chartA.station
  }
  try {
    const result = await Promise.all([
      HomeApi.getTimeDistribution(querys),
      HomeApi.getTerminalOperation(querys)
    ])
    const timeData = result[0]
    const chargeTrendData = result[1]
    const chargePowerData = Array(4).fill(null)
    const chargeNumData = Array(4).fill(null)
    timeData.forEach(item => {
      const index = chatOptionsB.baseOption['yAxis'][0]['data'].findIndex(y => y == item.name)
      chargePowerData[index] = item.chargedPower
      chargeNumData[index] = item.orderCount
    })
    chatOptionsB.options[0]['series'][0]['data'] = chargePowerData
    chatOptionsB.options[0]['series'][1]['data'] = chargeNumData

    switch (date) {
      case 3:
        chatOptionsA.xAxis['data'] = setChargeTrendDayMonthX(30)
        break;
      case 4:
        chatOptionsA.xAxis['data'] = setChargeTrendDayMonthX(12)
        break;
      default:
        chatOptionsA.xAxis['data'] = setChargeTrendHoursX()
        break;
    }
    const trendSeriesData = {
      chargedPower: Array(chatOptionsA.xAxis['data'].length).fill(null),
      orderAmount: Array(chatOptionsA.xAxis['data'].length).fill(null),
      orderCount: Array(chatOptionsA.xAxis['data'].length).fill(null),
    }
    chargeTrendData.forEach(ele => {
      const index = chatOptionsA.xAxis['data'].findIndex(k => k == ele.dateTime)
      for (const key in ele) {
        if (!key.includes('Time')) {
          trendSeriesData[key][index] = ele[key]
        }
      }
    })
    chatOptionsA.series.forEach(element => {
      element['data'] = trendSeriesData[trendKeyMap.get(element.name)]
    })
  } catch (error) {
    console.log(`output->error`,error)
  }

}

const setChargeTrendHoursX = () => {
  const xData = []
  for (let index = 0; index < 24; index++) {
    const x = index < 10 ? '0' + index : index
    // @ts-ignore
    xData.push(`${x}:00~${x}:59`)
  }
  return xData
}


/**
 * 生成近30天或近12个月的时间数组
 * @param numDaysOrMonths: 30表示近30天，12表示近12个月
 */
const setChargeTrendDayMonthX = (numDaysOrMonths: 30 | 12) => {
  const today = new Date();
  const dateArray = [];

  for (let i = 0; i < numDaysOrMonths; i++) {
    const date = new Date(today);
    if (numDaysOrMonths === 30) {
      date.setDate(today.getDate() - i);
      // @ts-ignore
      dateArray.push(date.toISOString().split('T')[0]); // 获取日期部分
    } else if (numDaysOrMonths === 12) {
      date.setMonth(today.getMonth() - i);
      const formattedMonth = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}`;
      // @ts-ignore
      dateArray.push(formattedMonth);
    }
  }

  return dateArray.reverse(); // 反转数组，使日期按照从过去到现在的顺序排列
}

const { wsCache } = useCache()
const userData = 'USER_DATA'
const getAllUser = async () => {
  const data = await UserApi.getSimpleUserList()
  console.log(`output->data`,data)
  wsCache.set(userData, JSON.stringify(data))
}

onMounted(() => {
  getStationAll()
  getAllUser()
  getHomeData()
  getStationData()
})
</script>

<style lang="scss" scoped>
.chart-wrapper{
  position: relative;
  box-sizing: border-box;
  border: 1px solid rgba(0, 0, 0, .1);
  border-radius: 15px;
  padding: 10px;
  .chart-title{
    position: absolute;
    left: -1px;
    top: -1px;
    border-radius: 15px 0 15px 0;
    background-color: #008FE0;
    padding: 3px 20px;
    font-size: 14px;
    color: #fff;
  }
}
</style>
