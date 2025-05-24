<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="600px">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="200px"
      v-loading="formLoading"
    >
      <el-form-item label="商品" prop="productId">
        <el-select v-model="formData.productId" placeholder="请选择商品">
          <el-option
            v-for="ele in goodsOptions"
            :key="(ele as any).id"
            :label="(ele as any).name"
            :value="(ele as any).id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="自动售货机" prop="machineId">
        <!-- <el-input v-model="formData.machineId" placeholder="请输入自动售货机id" /> -->
        <el-select v-model="formData.machineId" placeholder="请选择自动售货机">
          <el-option
            v-for="ele in machineOptions"
            :key="(ele as any).id"
            :label="(ele as any).name"
            :value="(ele as any).id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="层数(从上向下)" prop="floorNumber">
        <el-col :span="14">
          <el-input v-model="formData.floorNumber" placeholder="请输入层数(从下向上)" />
        </el-col>
      </el-form-item>
      <el-form-item label="列数(从左向右)" prop="columnNumber">
        <el-col :span="14">
          <el-input v-model="formData.columnNumber" placeholder="请输入列数(从左向右)" />
        </el-col>
      </el-form-item>
      <el-form-item label="商品最大数量" prop="productMaxNumber">
        <el-input-number v-model="formData.productMaxNumber" :min="0" />
      </el-form-item>
      <el-form-item label="商品数量" prop="productNumber" v-if="formType !== 'update'">
        <el-input-number v-model="formData.productNumber" :min="0" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as VendingSlotApi from '@/api/machine/vendingSlot'
import * as SpuApi from '@/api/mall/product/spu'
import * as VendingMachineApi from '@/api/machine/vendingMachine/index'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const goodsOptions = ref([])
const machineOptions = ref([])
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  productId: undefined,
  machineId: undefined,
  floorNumber: undefined,
  columnNumber: undefined,
  productMaxNumber: undefined,
  productNumber: undefined
})
const formRules = reactive({
  productId: [{ required: true, message: '商品id不能为空', trigger: 'blur' }],
  machineId: [{ required: true, message: '自动售货机id不能为空', trigger: 'blur' }],
  floorNumber: [{ required: true, message: '层数(从下向上)不能为空', trigger: 'blur' }],
  columnNumber: [{ required: true, message: '列数(从左向右)不能为空', trigger: 'blur' }],
  productMaxNumber: [{ required: true, message: '商品最大数量不能为空', trigger: 'blur' }],
  productNumber: [{ required: true, message: '商品数量不能为空', trigger: 'blur' }]
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
      formData.value = await VendingSlotApi.getVendingSlot(id)
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
    const data = formData.value as unknown as VendingSlotApi.VendingSlotVO
    if (formType.value === 'create') {
      await VendingSlotApi.createVendingSlot(data)
      message.success(t('common.createSuccess'))
    } else {
      await VendingSlotApi.updateVendingSlot(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const getCommodity = async () => {
  try {
    const data = await SpuApi.getSimpleAll()
    goodsOptions.value = data
    const vendingMachine = await VendingMachineApi.getVendingMachineList()
    machineOptions.value = vendingMachine
    console.log(`output->machineOptions`,machineOptions)
  } catch (error) {
    console.log(`output->error`,error)
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    productId: undefined,
    machineId: undefined,
    floorNumber: undefined,
    columnNumber: undefined,
    productMaxNumber: undefined,
    productNumber: undefined
  }
  formRef.value?.resetFields()
}

onMounted(() => {
  getCommodity()
})
</script>
