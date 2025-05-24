<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" :close-on-click-modal="false" :width="500">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="品牌名称" prop="brandName">
        <el-col :span="14">
          <el-input v-model="formData.brandName" v-no-space placeholder="请输入品牌名称" />
        </el-col>
      </el-form-item>
      <el-form-item label="运营商" prop="orgId">
        <el-select v-model="formData.orgId" placeholder="请选择运营商">
          <el-option
            v-for="item in operatorOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as EquipmentBrandApi from '@/api/operations/equipmentBrand'
import { getOperatorAll } from '@/api/system/operator';

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const operatorOptions = reactive([]) // 运营商选择器

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  brandName: undefined,
  orgId: undefined
})
const formRules = reactive({
  brandName: [{ required: true, message: '品牌名称不能为空', trigger: 'blur' }],
  orgId: [{ required: true, message: '运营商id不能为空', trigger: 'blur' }]
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
      formData.value = await EquipmentBrandApi.getEquipmentBrand(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

// 获取归属运营商列表
const getOperator = async () => {
  const data = await getOperatorAll()
  // console.log(`output->data`,data)
  data.forEach((item) => {
    // @ts-ignore
    operatorOptions.push({
      value: item.id,
      label: item.orgName
    })
  })
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
    const data = formData.value as unknown as EquipmentBrandApi.EquipmentBrandVO
    if (formType.value === 'create') {
      await EquipmentBrandApi.createEquipmentBrand(data)
      message.success(t('common.createSuccess'))
    } else {
      await EquipmentBrandApi.updateEquipmentBrand(data)
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
    brandName: undefined,
    orgId: undefined
  }
  formRef.value?.resetFields()
}

onMounted(() => {
  getOperator()
})
</script>
