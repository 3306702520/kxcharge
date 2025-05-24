<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" :close-on-click-modal="false" @close="dialogClose">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="渠道企业信息" name="operatorInfo">
        <el-form
          ref="formRef"
          :model="formData"
          :rules="formRules"
          label-width="135px"
          v-loading="formLoading"
        >
          <!-- <el-form-item label="企业名称" prop="orgName">
            <el-input v-model="formData.orgName" placeholder="请输入企业名称" />
          </el-form-item> -->
          <el-form-item label="运营商名称" prop="orgName">
            <el-input v-model="formData.orgName" v-no-space placeholder="请输入运营商名称" />
          </el-form-item>
          <el-form-item label="企业注册地址" prop="address">
            <el-input v-model="formData.address" v-no-space placeholder="请输入企业注册地址" />
          </el-form-item>
          <el-form-item label="统一社会信用代码" prop="code">
            <el-input v-model="formData.code" v-no-space placeholder="请输入统一社会信用代码" />
          </el-form-item>
          <!-- <el-form-item label="城市code" prop="cityCode">
            <el-input v-model="formData.cityCode" placeholder="请输入城市code" />
          </el-form-item>
          <el-form-item label="省份code" prop="provinceCode">
            <el-input v-model="formData.provinceCode" placeholder="请输入省份code" />
          </el-form-item> -->
          <el-form-item label="城市名称" prop="cityCode">
            <!-- <el-input v-model="formData.cityName" placeholder="请输入城市名称" /> -->
            <el-cascader
              v-model="formData.cityCode"
              :props="defaultProps2"
              :options="cityOptions"
            />
          </el-form-item>
          <el-form-item label="签约开始时间" prop="signStartTime">
            <el-date-picker
              v-model="formData.signStartTime"
              type="date"
              value-format="x"
              placeholder="选择签约开始时间"
            />
          </el-form-item>
          <el-form-item label="签约结束时间" prop="signEndTime">
            <el-date-picker
              v-model="formData.signEndTime"
              type="date"
              value-format="x"
              placeholder="选择签约结束时间"
            />
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="其他信息" name="otherInfo" :disabled="isDisabledOther">
        <el-form
          ref="otherFormRef"
          :model="otherFormData"
          :rules="otherFormRules"
          label-width="100px"
          v-loading="otherFormLoading"
        >
          <el-form-item label="企业图标" prop="businessLogo">
            <UploadImg v-model="otherFormData.businessLogo" />
          </el-form-item>
          <el-form-item label="备注说明" prop="remark">
            <el-input v-model="otherFormData.remark" v-no-space placeholder="请输入备注说明" />
          </el-form-item>
          <!-- <el-form-item label="license截取" prop="codeAbbr">
            <el-input v-model="otherFormData.codeAbbr" placeholder="请输入license截取" />
          </el-form-item> -->
          <el-form-item label="联系人" prop="contactUser">
            <el-input v-model="otherFormData.contactUser" v-no-space placeholder="请输入联系人" />
          </el-form-item>
          <el-form-item label="联系方式" prop="contactNum">
            <el-input v-model="otherFormData.contactNum" v-no-space placeholder="请输入联系方式" />
          </el-form-item>
          <el-form-item label="联系地址" prop="contactAddress">
            <el-input v-model="otherFormData.contactAddress" v-no-space placeholder="请输入联系地址" />
          </el-form-item>
          <el-form-item label="联系邮箱" prop="email">
            <el-input v-model="otherFormData.email" v-no-space placeholder="请输入联系邮箱" />
          </el-form-item>
          <el-form-item label="营业执照" prop="businessLicense">
            <UploadImg v-model="otherFormData.businessLicense" />
          </el-form-item>
          <el-form-item label="状态" prop="isFrozen">
            <el-radio-group v-model="otherFormData.isFrozen">
              <el-radio v-for="(dict, index) in status" :key="index" :label="dict.value">
                {{ dict.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>

    <template #footer>
      <el-button @click="nextStep" type="primary" v-if="activeTab === 'operatorInfo'">下一步</el-button>
      <el-button @click="submitForm" type="primary" :disabled="formLoading || otherFormLoading" v-else>确 定</el-button>
      <el-button @click="dialogVisible = false;">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import * as OperatorApi from '@/api/system/operator'
import * as AreaApi from '@/api/system/area'
import { defaultProps } from '@/utils/tree'
import { getTenantId } from '@/utils/auth'

const status = reactive(getIntDictOptions(DICT_TYPE.OPERATOR_STATUS))
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const activeTab = ref('operatorInfo')
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const otherFormLoading = ref(false)
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const isDisabledOther = ref(true)
const formData = ref({
  id: undefined,
  orgName: undefined,
  // operatorName: undefined,
  address: undefined,
  code: undefined,
  // cityCode: undefined,
  // provinceCode: undefined,
  cityCode: undefined,
  signStartTime: undefined,
  signEndTime: undefined
})
const otherFormData = ref({
  businessLogo: undefined,
  remark: undefined,
  // codeAbbr: undefined,
  contactUser: undefined,
  contactNum: undefined,
  contactAddress: undefined,
  email: undefined,
  businessLicense: undefined,
  isFrozen: status[0].value
})
// @ts-ignore
// otherFormData.value['isFrozen'] = status[0].value
const formRules = reactive({
  orgName: [{ required: true, message: '运营商名称不能为空', trigger: 'blur' }],
  address: [{ required: true, message: '企业注册地址不能为空', trigger: 'blur' }],
  code: [{ required: true, message: '信用代码不能为空', trigger: 'blur' }],
  cityCode: [{ required: true, message: '城市名称不能为空', trigger: 'blur' }],
  signStartTime: [{ required: true, message: '签约开始时间不能为空', trigger: 'blur' }],
  signEndTime: [{ required: true, message: '签约结束时间不能为空', trigger: 'blur' }]
})

const dialogClose = () => {
  isDisabledOther.value = true;
  activeTab.value = 'operatorInfo';
}

const phoneValidatePass = (rule: any, value: any, callback: any) => {
  const phoneNumberRegex = /^1[3456789]\d{9}$/;
  if (value !== 0 && !value) {
    callback(new Error('联系方式不能为空'))
  } else if(!phoneNumberRegex.test(value)) {
    callback(new Error('联系方式不符合手机号格式'))
  } else {
    callback()
  }
}

const emailValidatePass = (rule: any, value: any, callback: any) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (value !== 0 && !value) {
    callback(new Error('联系方式不能为空'))
  } else if(!emailRegex.test(value)) {
    callback(new Error('邮箱格式不符'))
  } else {
    callback()
  }
}

const otherFormRules = reactive({
  contactUser: [{ required: true, message: '联系人不能为空', trigger: 'blur' }],
  contactNum: [{ required: true, validator: phoneValidatePass, trigger: 'blur' }],
  contactAddress: [{ required: true, message: '联系地址不能为空', trigger: 'blur' }],
  email: [{ required: true, validator: emailValidatePass, trigger: 'blur' }],
  businessLogo: [{ required: true, message: '企业图标不能为空', trigger: 'blur' }],
  remark: [{ required: true, message: '备注说明不能为空', trigger: 'blur' }],
  businessLicense: [{ required: true, message: '营业执照不能为空', trigger: 'blur' }],
  isFrozen: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const otherFormRef = ref()
const cityOptions = ref([])
const defaultProps2 = defaultProps
const getAreaTree = async () => {
  cityOptions.value = await AreaApi.getTreeTwoStage()
}



const nextStep = async () => {
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  activeTab.value = 'otherInfo'
  isDisabledOther.value = false;
}
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    otherFormLoading.value = true
    try {
      const data = await OperatorApi.getOperator(id)
      formData.value = data
      // @ts-ignore
      // formData.value.cityCode = Number(data.cityCode)
      otherFormData.value = data
      console.log(`output->formData, otherFormData`,formData, otherFormData)
    } finally {
      formLoading.value = false
      otherFormLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

watch(
  () => otherFormData.value.businessLogo,
  // @ts-ignore
  (newValue) => {
    console.log(`output->newValue`,newValue)
    if (newValue) {
      setTimeout(() => {
        otherFormRef.value?.validateField('businessLogo', () => {})
      }, 100);
    }
  }
)
watch(
  () => otherFormData.value.businessLicense,
  // @ts-ignore
  (newValue) => {
    console.log(`output->newValue`,newValue)
    if (newValue) {
      setTimeout(() => {
        otherFormRef.value?.validateField('businessLicense', () => {})
      }, 100)
    }
  }
)

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef && !otherFormRef) return
  const valid = await formRef.value.validate()
  const otherValid = await otherFormRef.value.validate()
  if (!valid && !otherValid) return
  // 提交请求
  formLoading.value = true
  otherFormLoading.value = true
  try {
    const data = formData.value as unknown as OperatorApi.OperatorVO
    console.log(`output->`,data)
    const otherData = otherFormData.value as unknown as OperatorApi.OperatorVO
    const parmas = { ...data, ...otherData, ...handleCityCode(Number(data.cityCode)) }
    console.log(`output->parmas`, parmas)
    parmas['tenantId'] = getTenantId()
    if (formType.value === 'create') {
      await OperatorApi.createOperator(data)
      message.success(t('common.createSuccess'))
    } else {
      await OperatorApi.updateOperator(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false

    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
    otherFormLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    orgName: undefined,
    // operatorName: undefined,
    address: undefined,
    code: undefined,
    // cityCode: undefined,
    // provinceCode: undefined,
    cityCode: undefined,
    signStartTime: undefined,
    signEndTime: undefined
  }
  otherFormData.value = {
    businessLogo: undefined,
    remark: undefined,
    // codeAbbr: undefined,
    contactUser: undefined,
    contactNum: undefined,
    contactAddress: undefined,
    email: undefined,
    businessLicense: undefined,
    isFrozen: status[0].value
  }

  formRef.value?.resetFields()
  otherFormRef.value?.resetFields()
}

// 处理省份城市code
const handleCityCode = (code: number) => {
  const twoNum = getNum(code, 0, 2)
  const fourNum = getNum(code, 0, 4)
  // @ts-ignore
  const province: any = cityOptions.value.find((k) => getNum(k.id, 0, 2) === twoNum)
  const provinceCode = province.id
  const city = province.children.find((k) => getNum(k.id, 0, 4) === fourNum)
  const cityCode = city.id
  const cityName = city.name
  return {
    provinceCode,
    cityCode,
    cityName
  }
}

const getNum = (num: number, start: number, end: number) => {
  return Number(num.toString().substring(start, end))
}

/** 初始化 **/
onMounted(() => {
  getAreaTree()
})
</script>

<style scoped>
.com-dialog .el-dialog__header {
  border-bottom: none;
}
</style>
