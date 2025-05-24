<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" :close-on-click-modal="false">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="基础信息" name="baseInfo">
        <el-form
          ref="formRef"
          :model="formData"
          :rules="formRules"
          label-width="150px"
          v-loading="formLoading"
        >
          <el-form-item label="企业名称" prop="companyName">
            <el-col :span="10">
              <el-input v-model="formData.companyName" v-no-space placeholder="请输入企业名称" />
            </el-col>
          </el-form-item>
          <el-form-item label="运营商" prop="orgId">
            <el-select v-model="formData.orgId" placeholder="请选择运营商">
              <el-option
                v-for="item in operatorList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="企业注册地址" prop="address">
            <el-col :span="10">
              <el-input v-model="formData.address" v-no-space placeholder="请输入企业注册地址" />
            </el-col>
          </el-form-item>
          <el-form-item label="统一社会信用代码" prop="code">
            <el-col :span="10">
              <el-input v-model="formData.code" v-no-space placeholder="请输入统一社会信用代码" />
            </el-col>
          </el-form-item>
          <!-- <el-form-item label="归属城市" prop="cityCode">
            <el-cascader
              v-model="formData.cityCode"
              :props="defaultProps2"
              :options="cityOptions"
            />
          </el-form-item> -->
          <el-form-item label="签约时间">
            <el-form-item prop="signStartTime">
              <el-date-picker
                v-model="formData.signStartTime"
                type="date"
                value-format="x"
                placeholder="选择签约开始时间"
              />
            </el-form-item>
            <span style="margin: 0 10px;">至</span>
            <el-form-item prop="signEndTime">
              <el-date-picker
                v-model="formData.signEndTime"
                type="date"
                value-format="x"
                placeholder="选择签约结束时间"
              />
            </el-form-item>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="其他信息" name="otherInfo">
        <el-form
          ref="otherFormRef"
          :model="otherFormData"
          :rules="otherFormRules"
          label-width="100px"
          v-loading="otherFormLoading"
        >
          <el-form-item label="联系人" prop="contactUser">
            <el-col :span="10">
              <el-input v-model="otherFormData.contactUser" v-no-space  placeholder="请输入联系人" />
            </el-col>
          </el-form-item>
          <el-form-item label="联系方式" prop="contactNum">
            <el-col :span="10">
              <el-input v-model="otherFormData.contactNum" v-no-space placeholder="请输入联系方式" />
            </el-col>
          </el-form-item>
          <el-form-item label="联系地址" prop="contactAddress">
            <el-col :span="10">
              <el-input v-model="otherFormData.contactAddress" v-no-space placeholder="请输入联系地址" />
            </el-col>
          </el-form-item>
          <el-form-item label="联系邮箱" prop="email">
            <el-col :span="10">
              <el-input v-model="otherFormData.email" v-no-space placeholder="请输入联系邮箱" />
            </el-col>
          </el-form-item>
          <el-form-item label="营业执照" prop="businessLicense">
            <UploadImg v-model="otherFormData.businessLicense" />
          </el-form-item>
          <el-form-item label="备注说明" prop="remark">
            <el-col :span="15">
              <el-input :rows="4" type="textarea" v-model="otherFormData.remark" placeholder="请输入备注说明" />
            </el-col>
          </el-form-item>
          <el-form-item label="企业状态" prop="isFrozen">
            <el-radio-group v-model="otherFormData.isFrozen">
              <el-radio
                v-for="dict in getIntDictOptions(DICT_TYPE.COMPANY_STATUS)"
                :key="dict.value"
                :label="dict.value"
              >
                {{ dict.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button @click="nextStep" type="primary" v-if="activeTab === 'baseInfo'">下一步</el-button>
      <el-button @click="submitForm" type="primary" :disabled="formLoading && otherFormLoading" v-else>确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import * as CompanyApi from '@/api/member/company'
import { getOperatorAll } from '@/api/system/operator';
// import { defaultProps } from '@/utils/tree';
import * as AreaApi from '@/api/system/area'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const activeTab = ref('baseInfo')
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const otherFormLoading = ref(false)
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const operatorList = reactive([])
const formData = ref({
  id: undefined,
  companyName: undefined,
  orgId: undefined,
  // cityCode: undefined,
  address: undefined,
  code: undefined,
  signStartTime: undefined,
  signEndTime: undefined,

})
const otherFormData = ref({
  remark: undefined,
  contactUser: undefined,
  contactNum: undefined,
  contactAddress: undefined,
  email: undefined,
  businessLicense: undefined,
  isFrozen: undefined
})
const formRules = reactive({
  companyName: [{ required: true, message: '企业名称不能为空', trigger: 'blur' }],
  orgId: [{ required: true, message: '运营商不能为空', trigger: 'blur' }],
  address: [{ required: true, message: '企业注册地不能为空', trigger: 'blur' }],
  code: [{ required: true, message: '社会信用代码不能为空', trigger: 'blur' }],


})

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

const otherFormRules = reactive({
  contactUser: [{ required: true, message: '联系人不能为空', trigger: 'blur' }],
  contactNum: [{required: true, validator: phoneValidatePass, trigger: 'blur' }],
  contactAddress: [{ required: true, message: '联系地址不能为空', trigger: 'blur' }],
  email: [{ required: true, message: '联系邮箱不能为空', trigger: 'blur' }],
  businessLicense: [{ required: true, message: '营业执照不能为空', trigger: 'blur' }],
})


// const cityOptions = ref([])
// const defaultProps2 = defaultProps


const formRef = ref() // 表单 Ref
const otherFormRef = ref()
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  activeTab.value = 'baseInfo'
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    otherFormLoading.value = true
    try {
      const data = await CompanyApi.getCompany(id)
      formData.value = data
      otherFormData.value = data
    } finally {
      formLoading.value = false
      otherFormLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

// 下一步
const nextStep = async () => {
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  activeTab.value = 'otherInfo'
}

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
    const data = formData.value as unknown as CompanyApi.CompanyVO
    const otherData = otherFormData.value as unknown as CompanyApi.CompanyVO
    const parmas = {...data, ...otherData}
    if (formType.value === 'create') {
      await CompanyApi.createCompany(parmas)
      message.success(t('common.createSuccess'))
    } else {
      await CompanyApi.updateCompany(parmas)
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

// const getAreaTree = async () => {
//   cityOptions.value = await AreaApi.getTreeTwoStage()
// }

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    companyName: undefined,
    orgId: undefined,
    address: undefined,
    code: undefined,
    signStartTime: undefined,
    signEndTime: undefined,
    // cityCode: undefined
  }
  otherFormData.value = {
    remark: undefined,
    contactUser: undefined,
    contactNum: undefined,
    contactAddress: undefined,
    email: undefined,
    businessLicense: undefined,
    isFrozen: undefined
  }
  formRef.value?.resetFields()
  otherFormRef.value?.resetFields()
}

onMounted(() => {
  // getAreaTree()
  getOperator()
})
</script>
