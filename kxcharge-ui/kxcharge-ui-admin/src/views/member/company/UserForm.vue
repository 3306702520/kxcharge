<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" :width="600" :close-on-click-modal="false">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="150px"
      v-loading="formLoading"
    >
      <el-form-item label="归属企业" prop="companyId">
        <el-select
          v-model="formData.companyId"
          placeholder="归属企业"
          :disabled="formType === 'update'"
        >
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户姓名" prop="name">
        <el-col :span="15">
          <el-input v-model="formData.name" placeholder="请输入用户姓名" />
        </el-col>
      </el-form-item>
      <el-form-item label="用户账号" prop="account">
        <el-col :span="15">
          <el-input
            v-model="formData.account"
            placeholder="请输入用户账号"
            :disabled="formType === 'update'"
          />
        </el-col>
        <span>
          <el-icon><WarningFilled /></el-icon>
          账号可由数字、字母组合，必须含字母，实例：zhangsan、zs1
        </span>
      </el-form-item>
      <el-form-item label="密码" prop="password" v-if="formType === 'create'">
        <el-col :span="15">
          <el-input v-model="formData.password" placeholder="请输入密码" />
        </el-col>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-col :span="15">
          <el-input v-model="formData.mobile" placeholder="请输入手机号" />
        </el-col>
      </el-form-item>
      <el-form-item label="归属城市" prop="areaId">
        <el-cascader v-model="formData.areaId" :props="defaultProps2" :options="cityOptions" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as UserApi from '@/api/member/user'
import * as AreaApi from '@/api/system/area'
import { defaultProps } from '@/utils/tree'
import * as CompanyApi from '@/api/member/company'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const defaultProps2 = defaultProps
const cityOptions = ref([])
const companyList = reactive([])

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  mobile: undefined,
  password: undefined,
  name: undefined,
  areaId: undefined,
  account: undefined,
  companyId: undefined
})
const formRules = reactive({
  mobile: [{ required: true, message: '手机号不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
  areaId: [{ required: true, message: '归属城市不能为空', trigger: 'blur' }],
  account: [{ required: true, message: '账号不能为空', trigger: 'blur' }],
  companyId: [{ required: true, message: '归属企业不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const getAreaTree = async () => {
  cityOptions.value = await AreaApi.getTreeTwoStage()
}
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 获得地区列表
  getAreaTree()
  getCompanyList()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await UserApi.getUser(id)
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
    const data = formData.value as unknown as UserApi.UserVO
    const find = companyList.find((k) => k.value === data.companyId)
    console.log(find, companyList)

    data['nickname'] = data.name
    data['orgId'] = find ? find.orgId : null
    if (formType.value === 'create') {
      // 说明：目前暂时没有新增操作。如果自己业务需要，可以进行扩展
      await UserApi.addCompanyUser(data)
      message.success(t('common.createSuccess'))
    } else {
      await UserApi.updateUser(data)
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
    mobile: undefined,
    password: undefined,
    name: undefined,
    areaId: undefined,
    account: undefined,
    companyId: undefined
  }
  formRef.value?.resetFields()
}

const getCompanyList = async () => {
  const data = await CompanyApi.getCompanyListAll()
  data.forEach((item) => {
    // @ts-ignore
    companyList.push({
      value: item.id,
      label: item.companyName,
      orgId: item.orgId
    })
  })
}
</script>
