<template>
  <div>
    <Dialog v-model="dialogVisible" :title="dialogTitle" width="830px">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
      >
        <el-form-item label-width="180px" label="渠道费率" prop="feeRate">
          <el-input v-model="formData.feeRate" v-no-space placeholder="请输入渠道费率" clearable>
            <template #append>%</template>
          </el-input>
        </el-form-item>
        <el-form-item label-width="180px" label="渠道状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio
              v-for="dict in getDictOptions(DICT_TYPE.COMMON_STATUS)"
              :key="parseInt(dict.value)"
              :label="parseInt(dict.value)"
            >
              {{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label-width="180px" label="商户号" prop="config.cusId">
          <el-input v-model="formData.config.cusId" v-no-space placeholder="请输入商户号" clearable />
        </el-form-item>
        <el-form-item label-width="180px" label="md5私钥" prop="config.md5AppKey">
          <el-input v-model="formData.config.md5AppKey" v-no-space placeholder="请输入md5私钥" clearable />
        </el-form-item>
        <el-form-item label-width="180px" label="算法类型" prop="config.signType">
          <el-radio-group v-model="formData.config.signType">
            <el-radio key="RSA" label="RSA">RSA</el-radio>
            <el-radio key="SM2" label="SM2">SM2</el-radio>
          </el-radio-group>
        </el-form-item>
        <div v-if="formData.config.signType === 'RSA'">
          <el-form-item label-width="180px" label="RSA私钥" prop="config.rsaPrivateKey">
            <el-input
              type="textarea"
              :autosize="{ minRows: 8, maxRows: 8 }"
              v-model="formData.config.rsaPrivateKey"
              placeholder="请输入RSA私钥"
              clearable
              :style="{ width: '100%' }"
            />
          </el-form-item>
          <el-form-item label-width="180px" label="RSA公钥" prop="config.rsaPublicKey">
            <el-input
              type="textarea"
              :autosize="{ minRows: 8, maxRows: 8 }"
              v-model="formData.config.rsaPublicKey"
              placeholder="请输入RSA公钥"
              clearable
              :style="{ width: '100%' }"
            />
          </el-form-item>
        </div>
        <div v-if="formData.config.signType === 'SM2'">
          <el-form-item label-width="180px" label="SM2私钥" prop="config.sm2PrivateKey">
            <el-input
              type="textarea"
              :autosize="{ minRows: 8, maxRows: 8 }"
              v-model="formData.config.sm2PrivateKey"
              placeholder="请输入SM2私钥"
              clearable
              :style="{ width: '100%' }"
            />
          </el-form-item>
          <el-form-item label-width="180px" label="SM2公钥" prop="config.sm2PublicKey">
            <el-input
              type="textarea"
              :autosize="{ minRows: 8, maxRows: 8 }"
              v-model="formData.config.sm2PublicKey"
              placeholder="请输入SM2公钥"
              clearable
              :style="{ width: '100%' }"
            />
          </el-form-item>
        </div>
        <el-form-item label-width="180px" label="是否支持信用卡支付" prop="config.isCredit">
          <el-radio-group v-model="formData.config.isCredit">
            <el-radio key="true" :label="true">是</el-radio>
            <el-radio key="false" :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label-width="180px" label="微信小程序id" prop="config.wxAppId">
          <el-input v-model="formData.config.wxAppId" v-no-space placeholder="请输入微信小程序id" clearable />
        </el-form-item>
        <el-form-item label-width="180px" label="通联AppId" prop="config.appId">
          <el-input v-model="formData.config.wxAppId" v-no-space placeholder="请输入通联AppId" clearable />
        </el-form-item>
        <el-form-item label-width="180px" label="备注" prop="remark">
          <el-input v-model="formData.remark" :style="{ width: '100%' }" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </template>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import * as ChannelApi from '@/api/pay/channel'
import { DICT_TYPE, getDictOptions } from '@/utils/dict'

const { t } = useI18n() // 国际化
defineOptions({ name: 'TonglianChannelForm' })

const message = useMessage() // 消息弹窗
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({
  appId: '',
  feeRate: '',
  status: 1,
  code: '',
  remark: '',
  config: {
    cusId: '',
    appId: '',
    md5AppKey: '',
    signType: '',
    rsaPrivateKey: '',
    rsaPublicKey: '',
    sm2PrivateKey: '',
    sm2PublicKey: '',
    isCredit: '',
    wxAppId: ''
  }
})

const contentValidator = (rule: any, _value: any, callback: any) => {
  if (_value !== 0 && (!_value || !_value.toString().trim())) {
    callback(new Error('内容不能为空'))
  }else {
    callback()
  }
}

const formRules = {
  feeRate: [{ required: true, message: '内容不能为空', validator: contentValidator, trigger: 'blur' }],
  status: [{ required: true, message: '渠道状态不能为空', trigger: 'blur' }],
  'config.cusId': [{ required: true, message: '内容不能为空', validator: contentValidator, trigger: 'blur' }],
  'config.md5AppKey': [{ required: true, message: '内容不能为空', validator: contentValidator, trigger: 'blur' }],
  'config.signType': [{ required: true, message: '请选择算法类型', trigger: 'blur' }],
  'config.rsaPrivateKey': [{ required: true, message: '内容不能为空', validator: contentValidator, trigger: 'blur' }],
  'config.rsaPublicKey': [{ required: true, message: '内容不能为空', validator: contentValidator, trigger: 'blur' }],
  'config.sm2PrivateKey': [{ required: true, message: '内容不能为空', validator: contentValidator, trigger: 'blur' }],
  'config.sm2PublicKey': [{ required: true, message: '内容不能为空', validator: contentValidator, trigger: 'blur' }],
  'config.isCredit': [{ required: true, message: '请选择是否支持信用卡支付', trigger: 'blur' }],
  'config.wxAppId': [{ required: true, message: '内容不能为空', validator: contentValidator, trigger: 'blur' }],
  'config.appId': [{ required: true, message: '内容不能为空', validator: contentValidator, trigger: 'blur' }],

}
const formRef = ref() // 表单 Ref
const open = async (appId, code) => {
  dialogVisible.value = true
  formLoading.value = true
  resetForm(appId, code)
  // 加载数据
  try {
    const data = await ChannelApi.getChannel(appId, code)
    if (data && data.id) {
      formData.value = data
      formData.value.config = JSON.parse(data.config)
    }
    dialogTitle.value = !formData.value.id ? '创建支付渠道' : '编辑支付渠道'
  } finally {
    formLoading.value = false
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
    const data = { ...formData.value } as unknown as ChannelApi.ChannelVO
    data.config = JSON.stringify(formData.value.config)
    if (!data.id) {
      await ChannelApi.createChannel(data)
      message.success(t('common.createSuccess'))
    } else {
      await ChannelApi.updateChannel(data)
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
const resetForm = (appId, code) => {
  formData.value = {
    appId,
    feeRate: '',
    code,
    status: 1,
    remark: '',
    config: {
      cusId: '',
      appId: appId,
      md5AppKey: '',
      signType: '',
      rsaPrivateKey: '',
      rsaPublicKey: '',
      sm2PrivateKey: '',
      sm2PublicKey: '',
      isCredit: '',
      wxAppId: ''
    }
  }
  formRef.value?.resetFields()
}
</script>

<style scoped lang="scss">

</style>