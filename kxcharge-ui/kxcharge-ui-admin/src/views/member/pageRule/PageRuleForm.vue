<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="规则名称" prop="name">
        <el-input v-model="formData.name" v-no-space placeholder="请输入规则名称" />
      </el-form-item>
      <!-- <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item> -->
      <el-form-item label="类型" prop="type">
        <el-select v-model="formData.type" placeholder="请选择类型">
          <el-option
            v-for="item in pageRuleOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="规则内容" prop="content">
        <Editor v-model="formData.content" height="150px" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as PageRuleApi from '@/api/member/pageRule'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  // status: undefined,
  type: undefined,
  content: undefined

})

const contentValidator = (rule: any, _value: any, callback: any) => {
  const str = _value.replace(/<[^<>]+>/g,"").replace(/&nbsp;/gi,"")
  if (_value !== 0 && (!_value || !_value.trim() || !str || !str.trim())) {
    callback(new Error('内容不能为空'))
  }else {
    callback()
  }
}
const formRules = reactive({
  name: [{ required: true, validator: contentValidator, trigger: 'blur' }],
  type: [{ required: true, message: '类型不能为空', trigger: 'blur' }],
  content: [{ required: true, validator: contentValidator, trigger: 'blur' }],
})
defineProps({
  pageRuleOptions: {
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
      formData.value = await PageRuleApi.getPageRule(id)
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
    const data = formData.value as unknown as PageRuleApi.PageRuleVO
    if (formType.value === 'create') {
      await PageRuleApi.createPageRule(data)
      message.success(t('common.createSuccess'))
    } else {
      await PageRuleApi.updatePageRule(data)
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
    name: undefined,
    status: undefined,
    content: undefined,
    type: undefined
  }
  formRef.value?.resetFields()
}
</script>
