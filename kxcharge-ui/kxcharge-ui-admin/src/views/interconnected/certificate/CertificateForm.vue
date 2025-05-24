<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="200px"
      v-loading="formLoading"
      :inline="true"
    >
      <el-form-item label="类型" prop="type">
        <el-select clearable v-model="formData.type" placeholder="请选择类型" class="!w-240px">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.INTERCONNECTED_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
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
      <el-form-item label="组织机构代码" prop="operatorId">
        <el-input type="number" v-model.number="formData.operatorId" placeholder="请输入组织机构代码" class="!w-240px" />
      </el-form-item>
      <el-form-item label="秘钥" prop="operatorSecret">
        <el-input v-model="formData.operatorSecret" placeholder="请输入秘钥" class="!w-240px" />
      </el-form-item>
      <el-form-item label="签名秘钥" prop="sigSecret">
        <el-input v-model="formData.sigSecret" placeholder="请输入签名秘钥" class="!w-240px" />
      </el-form-item>
      <el-form-item label="数据加密秘钥" prop="dataSecret">
        <el-input v-model="formData.dataSecret" placeholder="请输入数据加密秘钥" class="!w-240px" />
      </el-form-item>
      <el-form-item label="初始化向量" prop="dataSecretIv">
        <el-input v-model="formData.dataSecretIv" placeholder="请输入初始化向量" class="!w-240px" />
      </el-form-item>
      <el-form-item label="UAT互联互通测试url地址" prop="url">
        <el-input v-model="formData.url" placeholder="请输入UAT互联互通测试url地址" class="!w-240px" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as CertificateApi from '@/api/interconnected/certificate'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  type: undefined,
  orgId: undefined,
  operatorId: undefined,
  operatorSecret: undefined,
  sigSecret: undefined,
  dataSecret: undefined,
  dataSecretIv: undefined,
  url: undefined
})
const formRules = reactive({
  type: [{ required: true, message: '类型不能为空', trigger: 'change' }],
  orgId: [{ required: true, message: '运营商ID不能为空', trigger: 'blur' }],
  operatorId: [{ required: true, message: '组织机构代码不能为空', trigger: 'blur' }],
  operatorSecret: [{ required: true, message: '秘钥不能为空', trigger: 'blur' }],
  sigSecret: [{ required: true, message: '签名秘钥不能为空', trigger: 'blur' }],
  dataSecret: [{ required: true, message: '数据加密秘钥不能为空', trigger: 'blur' }],
  dataSecretIv: [{ required: true, message: '初始化向量不能为空', trigger: 'blur' }],
  url: [{ required: true, message: 'UAT互联互通测试url地址不能为空', trigger: 'blur' }]
})
defineProps({
  operatorOptions: {
    type: Array,
    default: () => []
  },
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
      formData.value = await CertificateApi.getCertificate(id)
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
    const data = formData.value as unknown as CertificateApi.CertificateVO
    if (formType.value === 'create') {
      await CertificateApi.createCertificate(data)
      message.success(t('common.createSuccess'))
    } else {
      await CertificateApi.updateCertificate(data)
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
    type: undefined,
    orgId: undefined,
    operatorId: undefined,
    operatorSecret: undefined,
    sigSecret: undefined,
    dataSecret: undefined,
    dataSecretIv: undefined,
    url: undefined
  }
  formRef.value?.resetFields()
}
</script>
