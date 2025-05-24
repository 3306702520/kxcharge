<template>
  <div style="background-color: #fff; padding: 10px;">
    <div class="changeInfo">
      <div class="header" style="display: flex; justify-content: space-between; align-items: center;">
        <span class="title">电站信息</span>
        <el-button type="primary" @click="goBack">返回</el-button>

      </div>
      <div class="content">
        <div class="left">
          <div class="item">
            <span class="label">电桩编码</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentCode || '' }}</span>
          </div>
          <div class="item">
            <span class="label">电桩国际</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentStandard ? '老国际' : '新国际' }}</span>
          </div>
          <div class="item">
            <span class="label">输入电压</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.inputv || '' }}</span>
          </div>
          <div class="item">
            <span class="label">最大电流</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.maxa || '' }}</span>
          </div>
        </div>
        <div class="middle">
          <div class="item">
            <span class="label">电桩类型</span>
            <span>
              <dict-tag :type="DICT_TYPE.ELECTRIC_TYPE" :value="gunInfo.equipmentRespVO?.equipmentModelRespVO?.type" style="margin-left: 5px;" />
            </span>
          </div>
          <div class="item">
            <span class="label">电桩功率</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.power || '' }}</span>
          </div>
          <div class="item">
            <span class="label">最大电压</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.maxv || '' }}</span>
          </div>
          <div class="item">
            <span class="label">最小电流</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.mina || '' }}</span>
          </div>
        </div>
        <div class="right">
          <div class="item">
            <span class="label">品牌型号</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentBrandRespVO?.brandName || '' }}/{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.equipmentModel || '' }}</span>
          </div>
          <div class="item">
            <span class="label">辅助电源支持</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.supPower }}</span>
          </div>
          <div class="item">
            <span class="label">最小电压</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.minv }}</span>
          </div>
          <div class="item">
            <span class="label">恒功率输出电压区间</span>
            <span>{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.maxp }}~{{ gunInfo.equipmentRespVO?.equipmentModelRespVO?.minp }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="gunInfo">
      <div class="header">
        <span class="title">终端信息</span>
      </div>
      <div class="content">
        <div class="left">
          <div class="item">
            <span class="label">终端编码</span>
            <span>{{ gunInfo.connectorCode }}</span>
          </div>
          <div class="item">
            <span class="label">工作状态</span>
            <span>
              <dict-tag :type="DICT_TYPE.WORK_STATUS" :value="gunInfo.workStatus" style="margin-left: 5px;" />
            </span>
          </div>
          <div class="item">
            <span class="label">投运时间</span>
            <span>
              {{ formatDate(gunInfo.createTime, 'YYYY-MM-DD') || '--' }}
            </span>
          </div>
          <div class="item">
            <span class="label">网络链接类型</span>
            <span>
              <dict-tag :type="DICT_TYPE.NETWORK_LINK_TYPE" :value="gunInfo.equipmentRespVO?.communicationType" style="margin-left: 5px;" />
            </span>
          </div>
          <div class="item">
            <span class="label">车位号</span>
            <span>{{ gunInfo.equipmentRespVO?.parkNo || '--' }}</span>
          </div>
        </div>
        <div class="middle">
          <div class="item">
            <span class="label">终端名称</span>
            <span>{{ gunInfo.connectorName }}</span>
          </div>
          <div class="item">
            <span class="label">归属电站</span>
            <span>{{ gunInfo.stationName }}</span>
          </div>
          <div class="item">
            <span class="label">协议版本</span>
            <span>{{ gunInfo.version }}</span>
          </div>
          <div class="item">
            <span class="label">SIM卡号</span>
            <span>{{ gunInfo.equipmentRespVO?.numberSim || '--' }}</span>
          </div>
          <div class="item">
            <span class="label">有无地锁</span>
            <span>
              <dict-tag :type="DICT_TYPE.PARK_LOCK" :value="gunInfo.equipmentRespVO?.parkLock" style="margin-left: 5px;" />
            </span>
          </div>
        </div>
        <div class="right">
          <div class="item">
            <span class="label">启停状态</span>
            <span>
              <dict-tag :type="DICT_TYPE.ENABLE_START" :value="gunInfo.isEnableStart" />
            </span>
          </div>
          <div class="item">
            <span class="label">电站属性</span>
            <span>
              <dict-tag :type="DICT_TYPE.STATION_PROPERTY" :value="gunInfo.stationProperty" />
            </span>
          </div>
          <div class="item">
            <span class="label">通讯运营商</span>
            <span>
              <dict-tag :type="DICT_TYPE.COMMUNICATION_OPERATOR" :value="gunInfo.equipmentRespVO?.communicationOperator" />
            </span>
          </div>
          <div class="item">
            <span class="label">设备生产日期</span>
            <span>{{ gunInfo.equipmentRespVO?.productionDate[0] }}-{{ gunInfo.equipmentRespVO?.productionDate[1] }}-{{ gunInfo.equipmentRespVO?.productionDate[2] }}</span>
          </div>
          <div class="item">
            <span class="label">充电枪二维码</span>
            <el-image
              style="height: 150px"
              :src="'data:image/png;base64,' + gunInfo.codeImage"
              :zoom-rate="1.2"
              :max-scale="7"
              :min-scale="0.2"
              :preview-src-list="['data:image/png;base64,' + gunInfo.codeImage]"
              fit="cover"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="chargeChangeInfo">
      <div class="header">
        <span class="title">充电桩修改信息</span>
      </div>
      <div class="content">
        <div class="left">
          <div class="item">
            <span>创建人</span>
            <span>{{ gunInfo.Creator || '--' }}</span>
          </div>
          <div class="item">
            <span>修改人</span>
            <span>{{ gunInfo.Updater || '--' }}</span>
          </div>
        </div>
        <div class="right">
          <div class="item">
            <span>创建时间</span>
            <span>{{ formatDate(gunInfo.createTime, 'YYYY-MM-DD') || '--' }}</span>
          </div>
          <div class="item">
            <span>修改时间</span>
            <span>{{ formatDate(gunInfo.updateTime, 'YYYY-MM-DD') || '--' }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="price">
      <div class="header">
        <span class="title">充电单价</span>
      </div>
      <div class="table" style="padding: 10px; width: 100%;">
        <el-table :data="costInfo">
          <el-table-column type="index" label="序号" :width="400" />
          <el-table-column property="interval" label="时段" />
          <el-table-column property="pricePower" label="电费(元/度)" />
          <el-table-column property="priceService" label="服务费(元/度)" />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useTagsViewStore } from '@/store/modules/tagsView';
import { DICT_TYPE } from '@/utils/dict'
import * as EquipmentConnectorApi from '@/api/operations/equipment-connector'
import * as StationApi from '@/api/operations/station'
import * as CostApi from '@/api/operations/costTemplate'
import { formatDate } from '@/utils/formatTime';
import { useCache } from '@/hooks/web/useCache'

const router = useRouter()
const route = useRoute()
const tagsViewStore = useTagsViewStore()
const gunId = ref(route.query.id)
const gunInfo = ref({})
const stationList = ref([])
const price = reactive([])
const costInfo = reactive([])

const goBack = () => {
  router.back()
  tagsViewStore.delView(route)
}

const { wsCache } = useCache()

const initData = async () => {
  gunInfo.value = await EquipmentConnectorApi.getEquipmentConnector(gunId.value)
  const userData = JSON.parse(wsCache.get('USER_DATA'))
  const find = stationList.value.find(k => k.id === gunInfo.value.stationId)
  const creatFind = userData.find(k => k.id == gunInfo.value.creator)
  const changeind = userData.find(k => k.id == gunInfo.value.updater)
  gunInfo.value['stationName'] = find ? find.stationName : ''
  gunInfo.value['stationProperty'] = find ? find.property : ''
  gunInfo.value['Creator'] = creatFind?.nickname ?? ''
  gunInfo.value['Updater'] = changeind?.nickname ?? ''

  const data = await CostApi.getCostTemplate(gunInfo.value.equipmentRespVO.costTemplateId)
  data['costTemplatePriceRespVOS'].forEach(item => {
    const findData = data['costTemplatePriceTypeRespVOS'].find(k => k.typeName === item.priceTypeId)
    const obj = {
      interval: `${item.startTimeStr}~${item.endTimeStr}`,
      pricePower: findData.pricePower,
      priceService: findData.priceService
    }
    costInfo.push(obj)
  })
}

const getStationAll = async () => {
  stationList.value = await StationApi.getStationAll()

}

onMounted(() => {
  getStationAll()
  initData()
})
</script>

<style scoped lang="scss">
.header {
  .title {
    color: #008fe0;
    display: flex;
    &::before {
      display: inline-block;
      content: '';
      width: 5px;
      height: 25px;
      background-color: #008fe0;
      margin-right: 20px;
    }
  }
}
.changeInfo, .gunInfo, .chargeChangeInfo {
  margin-bottom: 10px;
  .content {
    display: flex;
    justify-content: space-around;
    .left, .middle, .right {
      width: 28%;
      .item {
        margin: 10px 0;
        display: flex;
        justify-content: space-between;
        font-size: 14px;
      }
    }
  }
}
.chargeChangeInfo {
  .content {
    .left, .right {
      width: 40%;

    }
  }
}
</style>
