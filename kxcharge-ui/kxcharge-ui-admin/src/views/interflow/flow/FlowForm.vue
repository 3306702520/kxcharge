<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="150px"
      v-loading="formLoading"
      :inline="true"
    >
      <el-form-item label="运营商" prop="orgId">
        <el-select clearable v-model="formData.orgId" placeholder="请选择运营商" class="!w-240px">
          <el-option
            v-for="item in operatorOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="方案名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入方案名称" class="!w-240px" />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
          v-model="formData.startTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择开始时间"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker
          v-model="formData.endTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择结束时间"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="站点IDs" prop="stationIds">
        <el-select clearable multiple v-model="formData.stationIds" placeholder="请输入站点IDs" class="!w-240px">
          <el-option
            v-for="item in idsOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="结算价格类型" prop="settlePriceType">
        <el-select clearable v-model="formData.settlePriceType" class="!w-240px" @change="optionsChange" placeholder="请选择结算价格类型">
          <el-option
            v-for="(dict, index) in priceTypeOptions"
            :key="index"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="站点推送价格类型" prop="stationPriceType">
        <el-select clearable v-model="formData.stationPriceType" class="!w-240px" @change="optionsChange" placeholder="请选择站点推送价格类型">
          <el-option
            v-for="(dict, index) in priceTypeOptions"
            :key="index"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label=" ">
        <div class="text-12px leading-20px text-#5073f7">
          <el-icon><QuestionFilled /></el-icon>
          原价：电站所设置的原始价格；商务价：在电站原始价格基础上进行了电费、服务费折扣后的价格
        </div>
      </el-form-item>
      <template v-if="isBusiness">
        <el-form-item label="电费折扣(%)" prop="chargeDiscount">
          <el-input type="number" v-model.number="formData.chargeDiscount" placeholder="请输入电费折扣" class="!w-240px" />
        </el-form-item>
        <el-form-item label="服务折扣(%)" prop="serviceDiscount">
          <el-input type="number" v-model.number="formData.serviceDiscount" placeholder="请输入服务折扣" class="!w-240px" />
        </el-form-item>
        <el-form-item label=" ">
          <div class="text-12px leading-20px text-#5073f7">
            <el-icon><QuestionFilled /></el-icon>
            折扣价格设置，即在电站基础价格上打折后推送给流量方，打折后的价格仅保留4位小数，其他舍弃。示例：电站基础价格为1.1291元/度，折扣为80%，计算后价格为0.90328元/度，推送给流量方的价格为0.9032元/度。 注：互联互通电站的基础价为入口方案折扣后的价格。
          </div>
        </el-form-item>
      </template>

      <!-- <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="(dict, index) in statusOptions"
            :key="index"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item> -->
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getOperatorStation } from '@/api/operations/station';
import * as FlowApi from '@/api/interflow/flow'
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  orgId: undefined,
  name: undefined,
  startTime: undefined,
  endTime: undefined,
  chargeDiscount: undefined,
  serviceDiscount: undefined,
  stationIds: undefined,
  settlePriceType: undefined,
  stationPriceType: undefined,
  status: undefined
})
const formRules = reactive({
  orgId: [{ required: true, message: '运营商ID不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '方案名称不能为空', trigger: 'blur' }],
  startTime: [{ required: true, message: '开始时间不能为空', trigger: 'blur' }],
  endTime: [{ required: true, message: '结束时间不能为空', trigger: 'blur' }],
  chargeDiscount: [{ required: true, message: '电费折扣不能为空', trigger: 'blur' }],
  serviceDiscount: [{ required: true, message: '服务折扣不能为空', trigger: 'blur' }],
  // stationIds: [{ required: true, message: '站点IDs不能为空', trigger: 'blur' }],
  settlePriceType: [{ required: true, message: '结算价格类型不能为空', trigger: 'blur' }],
  stationPriceType: [{ required: true, message: '站点推送价格类型', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
})
defineProps({
  operatorOptions: {
    type: Array,
    default: () => []
  },
  priceTypeOptions: {
    type: Array,
    default: () => []
  },
  statusOptions: {
    type: Array,
    default: () => []
  }
});
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
      formData.value = await FlowApi.getFlow(id)
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
    const data = formData.value as unknown as FlowApi.FlowVO
    if (formType.value === 'create') {
      data.status = 1;
      await FlowApi.createFlow(data)
      message.success(t('common.createSuccess'))
    } else {
      data.status = 2;
      await FlowApi.updateFlow(data)
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
    orgId: undefined,
    name: undefined,
    startTime: undefined,
    endTime: undefined,
    chargeDiscount: undefined,
    serviceDiscount: undefined,
    stationIds: undefined,
    settlePriceType: undefined,
    stationPriceType: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
  idsOptions.length = 0;
}

const  isBusiness = computed(() => {
  const {settlePriceType, stationPriceType} = formData.value;
  return settlePriceType === 2 || stationPriceType === 2;
});


function optionsChange(){
  if(isBusiness){
    formData.value.chargeDiscount = undefined;
    formData.value.serviceDiscount = undefined;
  }
}

const idsOptions = reactive([]) // 运营商选择器
watchEffect(async () => {
  if(!formData.value.orgId) return;
  idsOptions.length = 0;
  const data = await getOperatorStation(formData.value.orgId);
  data.forEach((item) => {
    // @ts-ignore
    idsOptions.push({
      value: item.id,
      label: item.stationName
    })
  })
})



onMounted(() => {
})
</script>
