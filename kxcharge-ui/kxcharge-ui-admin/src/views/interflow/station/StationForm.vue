<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="150px"
      v-loading="formLoading"
    >
      <el-form-item label="内容">
        <Editor v-model="formData.content" height="150px" />
      </el-form-item>
      <el-form-item label="充电站乡镇编码" prop="townCode">
        <el-input v-model="formData.townCode" placeholder="请输入充电站乡镇编码" />
      </el-form-item>
      <el-form-item label="运营商编号" prop="operationId">
        <el-input v-model="formData.operationId" placeholder="请输入运营商编号" />
      </el-form-item>
      <el-form-item label="站点ID" prop="stationId">
        <el-input v-model="formData.stationId" placeholder="请输入站点ID" />
      </el-form-item>
      <el-form-item label="站点名称" prop="stationName">
        <el-input v-model="formData.stationName" placeholder="请输入站点名称" />
      </el-form-item>
      <el-form-item label="设备所属放ID" prop="equipmentOwnerId">
        <el-input v-model="formData.equipmentOwnerId" placeholder="请输入设备所属放ID" />
      </el-form-item>
      <el-form-item label="充电站国家代码" prop="countryCode">
        <el-input v-model="formData.countryCode" placeholder="请输入充电站国家代码" />
      </el-form-item>
      <el-form-item label="充电站省市辖区编号" prop="areaCode">
        <el-input v-model="formData.areaCode" placeholder="请输入充电站省市辖区编号" />
      </el-form-item>
      <el-form-item label="详细地址" prop="address">
        <el-input v-model="formData.address" placeholder="请输入详细地址" />
      </el-form-item>
      <el-form-item label="服务电话" prop="serviceTel">
        <el-input v-model="formData.serviceTel" placeholder="请输入服务电话" />
      </el-form-item>
      <el-form-item label="站点类型" prop="stationType">
        <el-select v-model="formData.stationType" placeholder="请选择站点类型">
          <el-option
            v-for="item in stationTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="站点状态" prop="stationStatus">
        <el-radio-group v-model="formData.stationStatus">
          <el-radio
            v-for="(dict, index) in stationStatusOptions"
            :key="index"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="车位数量" prop="parkNums">
        <el-input v-model="formData.parkNums" placeholder="请输入车位数量:可停放进行充电的车位总数， 默认：0未知" />
      </el-form-item>
      <el-form-item label="经度" prop="stationLng">
        <el-input v-model="formData.stationLng" placeholder="请输入经度" />
      </el-form-item>
      <el-form-item label="纬度" prop="stationLat">
        <el-input v-model="formData.stationLat" placeholder="请输入纬度" />
      </el-form-item>
      <el-form-item label="建设场所" prop="construction">
        <el-input v-model="formData.construction" placeholder="请输入建设场所" />
      </el-form-item>
      <el-form-item label="电站图片" prop="pictures">
        <el-input v-model="formData.pictures" placeholder="请输入电站图片" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as StationApi from '@/api/interflow/station'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  content: undefined,
  townCode: undefined,
  operationId: undefined,
  stationId: undefined,
  stationName: undefined,
  equipmentOwnerId: undefined,
  countryCode: undefined,
  areaCode: undefined,
  address: undefined,
  serviceTel: undefined,
  stationType: undefined,
  stationStatus: undefined,
  parkNums: undefined,
  stationLng: undefined,
  stationLat: undefined,
  construction: undefined,
  pictures: undefined
})

const props = defineProps({
  stationTypeOptions: {
    type: Array,
    default: () => []
  },
  stationStatusOptions: {
    type: Array,
    default: () => []
  }
});

const formRules = reactive({
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await StationApi.getStation(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

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
    if (formType.value === 'create') {
      await StationApi.createStation(data)
      message.success(t('common.createSuccess'))
    } else {
      await StationApi.updateStation(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    content: undefined,
    townCode: undefined,
    operationId: undefined,
    stationId: undefined,
    stationName: undefined,
    equipmentOwnerId: undefined,
    countryCode: undefined,
    areaCode: undefined,
    address: undefined,
    serviceTel: undefined,
    stationType: undefined,
    stationStatus: undefined,
    parkNums: undefined,
    stationLng: undefined,
    stationLat: undefined,
    construction: undefined,
    pictures: undefined
  }
  formRef.value?.resetFields()
}
</script>
