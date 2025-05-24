<template>
  <div>
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="150px"
      v-loading="formLoading"
    >
      <div class="header">
        <span class="title">电站信息</span>
      </div>
      <el-form-item label="电站名称" prop="stationName">
        <el-col :span="6">
          <el-input v-model="formData.stationName" v-no-space placeholder="请输入电站名称" />
        </el-col>
      </el-form-item>
      <el-form-item label="电站编码" prop="stationCode">
        <el-col :span="6">
          <el-input v-model="formData.stationCode" v-no-space placeholder="请输入电站编码" />
        </el-col>
      </el-form-item>
      <el-form-item label="归属运营商" prop="orgId">
        <el-select v-model="formData.orgId" placeholder="请选择归属运营商">
          <el-option
            v-for="item in operatorList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="省code" prop="provinceCode">
        <el-input v-model="formData.provinceCode" placeholder="请输入省code" />
      </el-form-item>
      <el-form-item label="市code" prop="cityCode">
        <el-input v-model="formData.cityCode" placeholder="请输入市code" />
      </el-form-item>
      <el-form-item label="区code" prop="areaCode">
        <el-input v-model="formData.areaCode" placeholder="请输入区code" />
      </el-form-item>
      <el-form-item label="省市区中文名" prop="areaCodeName">
        <el-input v-model="formData.areaCodeName" placeholder="请输入省市区中文名" />
      </el-form-item> -->
      <el-form-item label="区域" prop="townCode">
        <!-- <el-input v-model="formData.cityName" placeholder="请输入城市名称" /> -->
        <el-cascader
          v-model="formData.townCode"
          :props="defaultProps2"
          :options="cityOptions"
        />
      </el-form-item>

      <el-form-item label="地址" prop="address">
        <el-col :span="6">
          <el-input v-model="formData.address" v-no-space placeholder="请输入地址" />
        </el-col>
        <el-button @click="searchAdress" type="primary" style="margin-left: 10px">地图定位</el-button>
      </el-form-item>

      <el-row>
        <el-form-item label="经度" prop="stationLng" :label-width="'150px'">
          <el-col :span="18">
            <el-input v-model="formData.stationLng" />
          </el-col>
        </el-form-item>
        <el-form-item label="纬度" prop="stationLat" :label-width="'80px'">
          <el-col :span="18">
            <el-input v-model="formData.stationLat" />
          </el-col>
        </el-form-item>
      </el-row>
      <el-form-item>
        <el-col :span="10">
          <MapContainer :addressLocation="{lng: formData.stationLng, lat: formData.stationLat}" ref="mapRef" @address="getAddress($event)" @geolocation="getLocation($event)" />
        </el-col>
      </el-form-item>
      <el-form-item label="当前定位">
        <span>{{ pointAddress }}</span>
      </el-form-item>
      <el-form-item label="电站位置" prop="stationAddress">
        <el-radio-group v-model="formData.stationAddress">
          <!-- <el-radio label="1">请选择字典生成</el-radio> -->
          <el-radio
            v-for="(dict, index) in getIntDictOptions(DICT_TYPE.STATION_LOCATION)"
            :key="index"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="可用电容" prop="capacitance">
        <el-col :span="6">
          <el-input v-model="formData.capacitance" placeholder="请输入可用电容">
            <template #append>KV-A</template>
          </el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="场地属性" prop="property">
        <el-radio-group v-model="formData.property">
          <el-radio
            v-for="(dict, index) in getIntDictOptions(DICT_TYPE.STATION_PROPERTY)"
            :key="index"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 新增的多选框 -->
      <el-form-item label="电站特性" prop="stationEigenvalue">
        <el-checkbox-group v-model="formData.stationEigenvalue">
          <el-checkbox
            v-for="(dict, index) in getIntDictOptions(DICT_TYPE.STATION_EIGENVALUE)"
            :key="index"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="电站类型" prop="stationType">
        <el-select v-model="formData.stationType" placeholder="请选择电站类型">
          <el-option
            v-for="(dict, index) in getIntDictOptions(DICT_TYPE.STATION_TYPE)"
            :key="index"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="建设场所" prop="construction">
        <el-select v-model="formData.construction" placeholder="请选择建设场所">
          <el-option
            v-for="(dict, index) in getIntDictOptions(DICT_TYPE.CONSTRUCTION_SITE)"
            :key="index"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <div class="header">
        <span class="title">充电单价</span>
      </div>
      <el-form-item label="选择计费模板" prop="costTemplateId">
        <el-select v-model="formData.costTemplateId" placeholder="请选择站点服务设施">
          <el-option
            v-for="dict in billingTemplate"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
        <el-button @click="viewCostInfo" type="primary" style="margin-left: 10px">查看详情</el-button>
      </el-form-item>

      <!-- <div class="header">
        <span class="title">占用费收费设置（充电完成规定时间未拔枪）</span>
      </div>
      <el-form-item label="是否收取占用费" prop="isOccupationCost" label-width="190px">
        <el-radio-group v-model="formData.isOccupationCost">
          <el-radio
            v-for="(dict, index) in getIntDictOptions(DICT_TYPE.OCCUPATION_COST)"
            :key="index"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item> -->

      <div class="header">
        <span class="title">电站配置</span>
      </div>
      <el-form-item label="场站道闸" prop="isBrake">
        <el-radio-group v-model="formData.isBrake">
          <el-radio
            v-for="(dict, index) in getIntDictOptions(DICT_TYPE.BARRIER_GATE)"
            :key="index"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="停车说明" prop="parkExplain">
        <el-col :span="6">
          <el-input v-model="formData.parkExplain" placeholder="请输入停车说明" />
        </el-col>
      </el-form-item>
      <el-form-item label="服务电话" prop="serviceTel">
        <el-col :span="6">
          <el-input v-model="formData.serviceTel" placeholder="请输入服务电话" />
        </el-col>
      </el-form-item>
      <el-form-item label="站点描述" prop="stationDescribe">
        <el-col :span="6">
          <el-input v-model="formData.stationDescribe" placeholder="请输入站点描述" />
        </el-col>
      </el-form-item>
      <el-form-item label="站点服务设施" prop="serviceSetup">
        <el-select v-model="formData.serviceSetup" placeholder="请选择站点服务设施">
          <el-option
            v-for="(dict, index) in getIntDictOptions(DICT_TYPE.SERVICE_SETUP)"
            :key="index"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="周边基础设施" prop="baseSetup">
        <el-select v-model="formData.baseSetup" placeholder="请选择周边基础设施">
          <el-option
            v-for="(dict, index) in getIntDictOptions(DICT_TYPE.BASE_SETUP)"
            :key="index"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开放说明时间" prop="openExplain">
        <el-col :span="6">
          <el-input v-model="formData.openExplain" placeholder="请输入开放说明时间" />
        </el-col>
      </el-form-item>
      <el-form-item label="电站图片">
        <el-form-item prop="imgMainEntrance" style="margin-right: 10px;">
          <el-col>
            <UploadImg v-model="formData.imgMainEntrance" />
            <div style="text-align: center;">主入口图</div>
          </el-col>
        </el-form-item>
        <el-form-item prop="imgFlagPath" style="margin-right: 10px;">
          <el-col>
            <UploadImg v-model="formData.imgFlagPath" />
            <div style="text-align: center;">标志路劲</div>
          </el-col>
        </el-form-item>
        <el-form-item prop="imgPilesFeature" style="margin-right: 10px;">
          <el-col>
            <UploadImg v-model="formData.imgPilesFeature" />
            <div style="text-align: center;">电站特写</div>
          </el-col>
        </el-form-item>
        <el-form-item prop="imgOther" style="margin-right: 10px;">
          <el-col>
            <UploadImg v-model="formData.imgOther" />
            <div style="text-align: center;">其他图片</div>
          </el-col>
        </el-form-item>
      </el-form-item>
      <el-form-item label="所属资质" prop="qualification">
        <UploadImg v-model="formData.qualification" />
      </el-form-item>
      <el-form-item label="备注说明" prop="remarks">
        <el-col :span="6">
          <el-input
            v-model="formData.remarks"
            :rows="5"
            type="textarea"
            placeholder="请输入备注说明"
          />
        </el-col>
      </el-form-item>
      <div class="btn" style="display: flex; justify-content: center; align-items: center;">
        <el-form-item>
          <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
          <el-button @click="goBack">取 消</el-button>
        </el-form-item>
      </div>

    </el-form>
    <Dialog title="计费详情" v-model="dialogVisible" :close-on-click-modal="false">
      <el-table :data="costInfo">
        <el-table-column property="interval" label="时段" />
        <el-table-column property="pricePower" label="电费" />
        <el-table-column property="priceService" label="服务费" />
      </el-table>
    </Dialog>
  </div>
</template>
<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import * as StationApi from '@/api/operations/station'
import * as CostApi from '@/api/operations/costTemplate'
import { defaultProps } from '@/utils/tree'
import * as AreaApi from '@/api/system/area'
import MapContainer from '@/components/Map/src/MapContainer.vue'
import { getOperatorAll } from '@/api/system/operator'
import { isUndefined } from 'lodash-es'
import { useRoute, useRouter } from 'vue-router';
import gwEventBus from '@/utils/energy-eventbus'
import { useTagsViewStore } from '@/store/modules/tagsView'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const route = useRoute()
const router = useRouter()
const dialogVisible = ref(false) // 弹窗的是否展示
// const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref(route.query.type) // 表单的类型：create - 新增；update - 修改
const formId = ref(route.query.id)
const formData = ref({
  id: undefined,
  stationName: undefined,
  stationCode: undefined,
  orgId: undefined,
  townCode: undefined,
  address: undefined,
  stationLng: undefined,
  stationLat: undefined,
  stationAddress: undefined,
  capacitance: undefined,
  property: undefined,
  stationType: undefined,
  construction: undefined,
  isBrake: undefined,
  parkExplain: undefined,
  stationDescribe: undefined,
  serviceSetup: undefined,
  baseSetup: undefined,
  openExplain: undefined,
  imgMainEntrance: undefined,
  imgFlagPath: undefined,
  imgPilesFeature: undefined,
  imgOther: undefined,
  qualification: undefined,
  remarks: undefined,
  isOccupationCost: 0,
  costTemplateId: undefined,
  stationEigenvalue: [] as number[], // 新增字段：电站特性
})
const formRules = reactive({
  stationName: [{ required: true, message: '电站名称不能为空', trigger: 'blur' }],
  stationCode: [{ required: true, message: '电站编码不能为空', trigger: 'blur' }],
  orgId: [{ required: true, message: '归属运营商不能为空', trigger: 'blur' }],
  townCode: [{ required: true, message: '区域不能为空', trigger: 'blur' }],
  address: [{ required: true, message: '地址不能为空', trigger: 'blur' }],
  stationAddress: [{ required: true, message: '电站位置不能为空', trigger: 'blur' }],
  capacitance: [{ required: true, message: '可用电容不能为空', trigger: 'blur' }],
  property: [{ required: true, message: '场地属性不能为空', trigger: 'blur' }],
  stationType: [{ required: true, message: '电站类型不能为空', trigger: 'change' }],
  construction: [{ required: true, message: '建设场所不能为空', trigger: 'change' }],
  // isOccupationCost: [{ required: true, message: '是否收取占用费不能为空', trigger: 'blur' }],
  isBrake: [{ required: true, message: '道闸不能为空', trigger: 'blur' }],
  parkExplain: [{ required: true, message: '停车说明不能为空', trigger: 'blur' }],
  serviceTel: [{ required: true, message: '服务电话不能为空', trigger: 'blur' }],
  qualification: [{ required: true, message: '资质不能为空', trigger: 'blur' }],
  stationDescribe: [{ required: true, message: '站点描述不能为空', trigger: 'blur' }],
  serviceSetup: [{ required: true, message: '站点服务设施不能为空', trigger: 'blur' }],
  costTemplateId: [{ required: true, message: '请选择计费模板', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const operatorList = reactive([])
const cityOptions = ref([])
const defaultProps2 = defaultProps
const billingTemplate = reactive([])
const costInfo = reactive([])
const pointAddress = ref('')
const tagsViewStore = useTagsViewStore()

const getAreaTree = async () => {
  cityOptions.value = await AreaApi.getTreeTowns()
}

const getAddress = (e) => {
  pointAddress.value = e;
}

const getLocation = (e) => {
  formData.value.stationLat = e.latitude
  formData.value.stationLng = e.longitude
}

// 获取归属运营商列表
const getOperator = async () => {
  const data = await getOperatorAll()
  // console.log(`output->data`,data)
  data.forEach(item => {
    // @ts-ignore
    operatorList.push({
      value: item.id,
      label: item.orgName
    })
  })
}

// 地图定位
const mapRef = ref()
const searchAdress = () => {
  // @ts-ignore
  mapRef.value.searchAdress(formData.value.address)
}

// 查看计费详情
const viewCostInfo = async () => {
  if (isUndefined(formData.value.costTemplateId)) {
    message.info('请选择计费模板')
    return
  }
  const data = await CostApi.getCostTemplate(formData.value.costTemplateId)
  data['costTemplatePriceRespVOS'].forEach(item => {
    const find = data['costTemplatePriceTypeRespVOS'].find(k => k.typeName === item.priceTypeId)
    const obj = {
      interval: `${item.startTimeStr}~${item.endTimeStr}`,
      pricePower: find.pricePower,
      priceService: find.priceService
    }
    costInfo.push(obj)
  })
  dialogVisible.value = true
}

// 获取所有计费模板
const costTemplateAll = async () => {
  const data = await CostApi.getCostTemplateAll()
  data.forEach(item => {
    // @ts-ignore
    billingTemplate.push({
      value: item.id,
      label: item.templateName
    })
  })
}

const goBack = () => {
  router.back()
  tagsViewStore.delView(route)
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as StationApi.StationVO
    const parmas = {...data, ...handleCityCode(Number(data.townCode)) }
    // console.log(`output->parmas`,parmas)
    if (formType.value === 'create') {
      await StationApi.createStation(parmas)
      message.success(t('common.createSuccess'))
    } else {
      await StationApi.updateStation(parmas)
      message.success(t('common.updateSuccess'))
    }
    // 发送操作成功的事件
    emit('success')
    router.back()
    gwEventBus.emit('toStationList')
    tagsViewStore.delView(route)
  } finally {
    formLoading.value = false
  }
}

// 处理省份城市code
const handleCityCode = (code: number) => {
  const twoNum = getNum(code, 0, 2)
  const fourNum = getNum(code, 0, 4)
  const sixNum = getNum(code, 0, 6)
  // @ts-ignore
  const province = cityOptions.value.find((k) => getNum(k.id, 0, 2) === twoNum)
  const provinceCode = province.id
  const city = province.children.find((k) => getNum(k.id, 0, 4) === fourNum)
  const areaCodeName = city.name
  const cityCode = city.id
  const area = city.children.find((k) => getNum(k.id, 0, 6) === sixNum)
  const areaCode = area.id
  return {
    provinceCode,
    areaCodeName,
    cityCode,
    areaCode
  }
}

const getNum = (num: number, start: number, end: number) => {
  return Number(num.toString().substring(start, end))
}

/** 重置表单 */
// const resetForm = () => {
//   formData.value = {
// id: undefined,
// stationName: undefined,
// stationCode: undefined,
// orgId: undefined,
// areaCodeName: undefined,
// address: undefined,
// stationLng: undefined,
// stationLat: undefined,
// stationAddress: undefined,
// capacitance: undefined,
// property: undefined,
// stationType: undefined,
// construction: undefined,
// isBrake: undefined,
// parkExplain: undefined,
// stationDescribe: undefined,
// serviceSetup: undefined,
// baseSetup: undefined,
// openExplain: undefined,
// imgMainEntrance: undefined,
  // imgFlagPath: undefined,
  // imgPilesFeature: undefined,
  // imgOther: undefined,
// qualification: undefined,
// remarks: undefined,
// isOccupationCost: undefined
//   }
//   formRef.value?.resetFields()
// }

const initFormData = async () => {
  if (formType.value === 'update') {
    const data = await StationApi.getStation(formId.value)
    formData.value = data

    // 确保回显的是一个数组
    if (data.stationEigenvalue && !Array.isArray(data.stationEigenvalue)) {
      formData.value.stationEigenvalue = [data.stationEigenvalue]
    }

    // 构造 townCode 数组：省、市、区
    formData.value.townCode = [data.provinceCode, data.cityCode, data.areaCode];
  }
}

/** 初始化 **/
onMounted(() => {
  route.meta.title = formId.value ? '编辑站点' : '新增站点'
  tagsViewStore.visitedViews.find(k => k.path === route.path)!.meta.title = route.meta.title
  getAreaTree()
  getOperator()
  costTemplateAll()
  initFormData()

})
</script>

<style setup>
.el-form {
  background-color: #fff;
  padding: 20px 0;
}
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
</style>
