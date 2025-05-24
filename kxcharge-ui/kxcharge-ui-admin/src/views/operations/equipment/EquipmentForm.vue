<template>
  <!-- <Dialog :title="dialogTitle" v-model="dialogVisible"> -->
  <div>
    <el-form ref="formRef" :model="formData" :rules="formRules" label-width="150px" v-loading="formLoading">
      <div class="header">
        <span class="title">电站信息</span>
      </div>
      <el-form-item label="归属电站" prop="stationId">
        <el-select v-model="formData.stationId" placeholder="请选择归属电站" :disabled="formId">
          <el-option v-for="item in stationList" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="归属运营商" prop="orgId">
        <el-select v-model="formData.orgId" placeholder="请选择归属运营商" :disabled="formId">
          <el-option v-for="item in orgList" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="品牌型号" prop="equipmentModelId">
        <el-cascader
          v-model="formData.equipmentModelId"
          :props="defaultProps2"
          :options="branceOptions"
          @change="selectEquipment"
          :disabled="formId"
        />
      </el-form-item>

      <el-form-item label="终端数量" prop="equipmentGunNumber">
        <span style="margin-right: 10px;" v-if="formData.equipmentGunNumber">{{ formData.equipmentGunNumber }}</span>
        <span style="color: #008fe0; font-size: 12px;">多终端电桩会自动生成多个终端，在桩编码、桩名称后添加后缀01、02、03，从而生成相应终端的编号、名称</span>
      </el-form-item>
      <el-form-item label="电桩名称" prop="equipmentName">
        <el-col :span="6">
          <el-input v-model="formData.equipmentName" v-no-space placeholder="请输入电桩名称" />
        </el-col>
      </el-form-item>
      <el-form-item label="电桩编码" prop="equipmentCode">
        <el-col :span="6">
          <el-input v-model="formData.equipmentCode" v-no-space placeholder="请输入电桩编码" :disabled="formId" />
          <el-icon color="#aaa"><Warning /></el-icon><span style="color: #aaa;">电桩编码为14位长的16 进制字符串组成</span>
        </el-col>
      </el-form-item>
      <el-form-item label="通信运营商" prop="communicationOperator">
        <el-radio-group v-model="formData.communicationOperator">
          <el-radio v-for="dict in getIntDictOptions(DICT_TYPE.COMMUNICATION_OPERATOR)" :key="dict.value" :label="dict.value">
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="网络链接类型" prop="communicationType">
        <el-select v-model="formData.communicationType" placeholder="请选择网络链接类型">
          <el-option v-for="dict in getIntDictOptions(DICT_TYPE.NETWORK_LINK_TYPE)" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="SIM卡号" prop="numberSim" v-if="formData.communicationType === 1">
        <el-col :span="6">
          <el-input v-model="formData.numberSim" v-no-space placeholder="请输入SIM卡号" />
        </el-col>
      </el-form-item>
      <el-form-item label="有无车锁" prop="parkLock">
        <el-radio-group v-model="formData.parkLock">
          <el-radio v-for="dict in getIntDictOptions(DICT_TYPE.PARK_LOCK)" :key="dict.value" :label="dict.value">
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <div class="header">
        <span class="title">运营信息</span>
      </div>
      <el-form-item label="价格设置" prop="priceSet">
        <el-radio-group v-model="formData.priceSet">
          <el-radio v-for="dict in getIntDictOptions(DICT_TYPE.PRICE_SET)" :key="dict.value" :label="dict.value">
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="选择计费模板" prop="costTemplateId" v-if="formData.priceSet === 2" :rules="formData.priceSet === 2 ? { required: true, message: '计费模板不能为空', trigger: 'blur' } : {required: false}">
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
      <div class="header">
        <span class="title">硬件信息</span>
      </div>
      <el-form-item label="设备标准" prop="equipmentStandard">
        <el-radio-group v-model="formData.equipmentStandard">
          <el-radio v-for="dict in getIntDictOptions(DICT_TYPE.EQUIPMENT_STANDARD)" :key="dict.value" :label="dict.value">
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="充电模块品牌型号" prop="chargingModuleBrandModel">
        <el-col :span="6">
          <el-input v-model="formData.chargingModuleBrandModel" v-no-space placeholder="请输入充电模块品牌型号" />
        </el-col>
      </el-form-item>
      <el-form-item label="充电枪线品牌型号" prop="chargingGunCableBrandModel">
        <el-col :span="6">
          <el-input v-model="formData.chargingGunCableBrandModel" v-no-space placeholder="请输入充电枪线品牌型号" />
        </el-col>
      </el-form-item>
      <el-form-item label="电桩屏幕品牌" prop="equipmentScreenBrand">
        <el-col :span="6">
          <el-input v-model="formData.equipmentScreenBrand" v-no-space placeholder="请输入电桩屏幕品牌" />
        </el-col>
      </el-form-item>
      <el-form-item label="电桩程序" prop="equipmentProcedure">
        <el-col :span="6">
          <el-input v-model="formData.equipmentProcedure" v-no-space placeholder="请输入电桩程序" />
        </el-col>
      </el-form-item>
      <el-form-item label="设备生产日期" prop="productionDate">
        <el-date-picker value-format="YYYY-MM-DD" v-model="formData.productionDate" type="date" placeholder="选择设备生产日期" />
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

  <!-- </Dialog> -->
</template>
<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions, getStrDictOptions } from '@/utils/dict'
import * as EquipmentApi from '@/api/operations/equipment'
import * as StationApi from '@/api/operations/station'
import * as EquipmentModelApi from '@/api/operations/equipmentModel'
import * as CostApi from "@/api/operations/costTemplate"

import { defaultProps } from '@/utils/tree';
import { getOperatorAll } from '@/api/system/operator';
import { isUndefined } from 'lodash-es';
import gwEventBus from '@/utils/energy-eventbus';
import { useTagsViewStore } from '@/store/modules/tagsView';
import moment from 'moment'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const defaultProps2 = defaultProps
const route = useRoute()
const router = useRouter()
const dialogVisible = ref(false) // 弹窗的是否展示
// const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref(route.query.type) // 表单的类型：create - 新增；update - 修改
const formId = ref(route.query.id)
const formData = ref({
  id: undefined,
  orgId: undefined,
  costTemplateId: undefined,
  equipmentModelId: undefined,
  equipmentGunNumber: undefined,
  equipmentName: undefined,
  equipmentCode: undefined,
  communicationType: undefined,
  communicationOperator: undefined,
  parkLock: undefined,
  priceSet: undefined,
  equipmentStandard: undefined,
  chargingModuleBrandModel: undefined,
  chargingGunCableBrandModel: undefined,
  equipmentScreenBrand: undefined,
  equipmentProcedure: undefined,
  productionDate: '',
  stationId: undefined,
  numberSim: undefined
})
const formRules = reactive({
  orgId: [{ required: true, message: '归属运营商不能为空', trigger: 'blur' }],
  equipmentModelId: [{ required: true, message: '品牌型号不能为空', trigger: 'blur' }],
  equipmentName: [{ required: true, message: '电桩名称不能为空', trigger: 'blur' }],
  equipmentCode: [{ required: true, message: '设备编码不能为空', trigger: 'blur' }],
  communicationType: [{ required: true, message: '网络链接类型不能为空', trigger: 'change' }],
  priceSet: [{ required: true, message: '价格设置不能为空', trigger: 'blur' }],
  equipmentStandard: [{ required: true, message: '设备标准不能为空', trigger: 'change' }],
  productionDate: [{ required: true, message: '设备生产日期不能为空', trigger: 'blur' }],
  stationId: [{ required: true, message: '归属电站不能为空', trigger: 'blur' }],

})
const formRef = ref() // 表单 Ref
const stationList = reactive([])
const orgList = reactive([])
const billingTemplate = reactive([])
const costInfo = reactive([])
const branceOptions =ref([])
const tagsViewStore = useTagsViewStore()


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
    const data = formData.value as unknown as EquipmentApi.EquipmentVO
    const parmas = {...data, ...handleBrandCode(data.equipmentModelId)}
    if (formType.value === 'create') {
      await EquipmentApi.createEquipment(parmas)
      message.success(t('common.createSuccess'))
    } else {
      await EquipmentApi.updateEquipment(parmas)
      message.success(t('common.updateSuccess'))
    }
    // 发送操作成功的事件
    emit('success')
    router.back()
    gwEventBus.emit('toEquipmentList')
    tagsViewStore.delView(route)
  } finally {
    formLoading.value = false
  }
}

// 处理品牌型号code
const handleBrandCode = (code: number) => {
  // @ts-ignore
  const brand = branceOptions.value.find((k: any) => {
    const index = k.children.findIndex(m => m.id === code)
    return index > -1
  })
  const equipmentBrandId = brand?.id ?? ''
  return {
    equipmentBrandId
  }
}

// const getNum = (num: number, start: number, end: number) => {
//   return Number(num.toString().substring(start, end))
// }

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

const getStationAll = async () => {
  const data = await StationApi.getStationAll()
  data.forEach(element => {
    // @ts-ignore
    stationList.push({
      value: element.id,
      label: element.stationName
    })
  });
}

const selectEquipment = async () => {
  if (!isUndefined(formData.value.equipmentModelId)) {
    const data = await EquipmentModelApi.getEquipmentModel(formData.value.equipmentModelId)
    formData.value.equipmentGunNumber = data.number ?? 0
  }
}

const goBack = () => {
  router.back()
  tagsViewStore.delView(route)
}

// 获取归属运营商列表
const getOperator = async () => {

  const data = await getOperatorAll()
  data.forEach(item => {
    // @ts-ignore
    orgList.push({
      value: item.id,
      label: item.orgName
    })
  })
}

const getBrandModel = async () => {
  try {
    const data = await EquipmentModelApi.getBrandModelTree()
    branceOptions.value = data.map(item => {
      item['disabled'] = !item.children.length
      return item;
    })
  } catch (error) {
  }
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

const initFormData = async () => {
  if (formType.value === 'update') {
    const data = await EquipmentApi.getEquipment(formId.value)
    formData.value = data
    const date = `${data.productionDate[0]}-${data.productionDate[1]}-${data.productionDate[2]}`
    formData.value.productionDate = moment(date).format('YYYY-MM-DD')
  }
}

onMounted(() => {
  route.meta.title = formId.value ? '编辑电桩' : '新增电桩'
  tagsViewStore.visitedViews.find(k => k.path === route.path)!.meta.title = route.meta.title
  getStationAll()
  getOperator()
  getBrandModel()
  costTemplateAll()
  initFormData()
})

</script>

<style scoped lang="scss">
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
