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
      <el-form-item label="定价类型" prop="secondaryPricingType">
        <el-select clearable class="!w-240px" v-model="formData.secondaryPricingType" @change="optionChange" placeholder="请选择定价类型">
          <el-option 
            v-for="(dict, index) in pricingTypeOptions"
            :key="index"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <template v-if="formData.secondaryPricingType === 1">
        <el-form-item label="电费折扣(%)" prop="chargeDiscount">
          <el-input v-model="formData.chargeDiscount" placeholder="请输入电费折扣" class="!w-240px" />
        </el-form-item>
        <el-form-item label="服务折扣(%)" prop="serviceDiscount">
          <el-input v-model="formData.serviceDiscount" placeholder="请输入服务折扣" class="!w-240px" />
        </el-form-item>
        <el-form-item label=" " v-if="formData.secondaryPricingType">
          <div class="text-12px leading-20px text-#5073f7">
            <el-icon><QuestionFilled /></el-icon>
            折扣价格设置，即在运营商推送价格基础上进行打折，仅保留4位小数，其他舍弃。 示例：电站推送价格为1.1291元/度，折扣为80%，计算后价格为0.90328元/度，该电站在平台的基础价格为0.9032元/度。
          </div>
        </el-form-item>
      </template>
      <template v-if="formData.secondaryPricingType === 2">
        <el-form-item label="电费调价(度/元)" prop="chargeFixedDifference">
          <el-input v-model="formData.chargeFixedDifference" placeholder="请输入电费调价" class="!w-240px" />
        </el-form-item>
        <el-form-item label="服务费调价(度/元)" prop="serviceFixedDifference">
          <el-input v-model="formData.serviceFixedDifference" placeholder="请输入服务费调价" class="!w-240px" />
        </el-form-item>
        <el-form-item label=" " v-if="formData.secondaryPricingType">
          <div class="text-12px leading-20px text-#5073f7">
            <el-icon><QuestionFilled /></el-icon>
            固定差额设置，即在运营商推送价格基础上，每度上调、下调固定金额，精确到2位小数。 示例：电站推送电费价格为1.1291元/度，设置调价+0.01每度，该电站在平台的基础电价为1.1391元/度。
          </div>
        </el-form-item>
      </template>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
          v-model="formData.startTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择开始时间"
          format="YYYY-MM-DD"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker
          v-model="formData.endTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择结束时间"
          format="YYYY-MM-DD"
          class="!w-240px"
        />
      </el-form-item>
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
import * as EntranceApi from '@/api/interconnected/entrance'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  orgId: undefined,
  name: undefined,
  secondaryPricingType: undefined,
  chargeDiscount: undefined,
  serviceDiscount: undefined,
  startTime: undefined,
  endTime: undefined,
  chargeFixedDifference: undefined,
  serviceFixedDifference: undefined,
  status: undefined
})
const formRules = reactive({
  orgId: [{ required: true, message: '运营商ID不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '方案名称不能为空', trigger: 'blur' }],
  secondaryPricingType: [{ required: true, message: '定价类型不能为空', trigger: 'blur' }],
  chargeDiscount: [{ required: true, message: '电费折扣不能为空', trigger: 'blur' }],
  serviceDiscount: [{ required: true, message: '服务折扣不能为空', trigger: 'blur' }],
  startTime: [{ required: true, message: '开始时间不能为空', trigger: 'blur' }],
  endTime: [{ required: true, message: '结束时间不能为空', trigger: 'blur' }],
  chargeFixedDifference: [{ required: true, message: '电费调价不能为空', trigger: 'blur' }],
  serviceFixedDifference: [{ required: true, message: '服务费调价不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
})
const props = defineProps({ 
  operatorOptions: { 
    type: Array,
    default: () => []
  },
  pricingTypeOptions: {
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
      formData.value = await EntranceApi.getEntrance(id)
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
    const data = formData.value as unknown as EntranceApi.EntranceVO
    if (formType.value === 'create') {
      data.status = 1;
      await EntranceApi.createEntrance(data)
      message.success(t('common.createSuccess'))
    } else {
      data.status = 2;
      await EntranceApi.updateEntrance(data)
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
    secondaryPricingType: undefined,
    chargeDiscount: undefined,
    serviceDiscount: undefined,
    startTime: undefined,
    endTime: undefined,
    chargeFixedDifference: undefined,
    serviceFixedDifference: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
}



function optionChange(){
  formData.value = {
    ...formData.value, 
    chargeDiscount: undefined,
    serviceDiscount: undefined,
    chargeFixedDifference: undefined,
    serviceFixedDifference: undefined
  }
}
onMounted(() => {
})
</script>
