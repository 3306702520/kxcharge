<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="用户ID" prop="userId">
        <el-input :disabled="formType === 'manage'" v-model="formData.userId" placeholder="请输入用户ID" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input :disabled="formType === 'manage'" v-model="formData.phone" placeholder="请输入电话" />
      </el-form-item>
      <el-form-item label="用户名称" prop="name">
        <el-input :disabled="formType === 'manage'" v-model="formData.name" placeholder="请输入用户名称" />
      </el-form-item>
      <el-form-item label="反馈类型" prop="type">
        <el-select class="w-100%" :disabled="formType === 'manage'" v-model="formData.type" placeholder="请选择反馈类型">
          <el-option
            v-for="item in feedbackTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="图片" prop="img">
        <UploadImgs v-model:modelValue="formData.img" :limit="9" :disabled="formType === 'manage'" />
      </el-form-item>
      <!-- <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="item in feedbackStatusOptions"
            :key="item.value"
            :label="item.value"
          >
            {{ item.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item> -->
      <el-form-item label="反馈内容">
        <div class="w-100% p-10px" style="border: 1px solid rgba(0,0,0,.1)" v-html="formData.content" v-if="formType === 'manage'"></div>
        <Editor v-else v-model="formData.content" height="150px" />

      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as FeedbackApi from '@/api/member/feedback'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  userId: undefined,
  phone: undefined,
  name: undefined,
  type: undefined,
  img: undefined,
  status: undefined,
  content: undefined
})
const props = defineProps({
  feedbackTypeOptions: {
    type: Array,
    default: () => []
  },
  feedbackStatusOptions: {
    type: Array,
    default: () => []
  }
});
const formRules = reactive({
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
      formData.value = await FeedbackApi.getFeedback(id);
      formData.value.img = formData.value.img?.map(item => ({url: window.location.origin + '/' + item}))
      // console.log("%c Line:98 🍧 formData.value", "color:#b03734", formData.value);
      // formData.value.type = +formData.value.type;
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
    let data = formData.value as unknown as FeedbackApi.FeedbackVO
    data = JSON.parse(JSON.stringify(data));
    data.img = data.img?.map(item => item.url);

    if (formType.value === 'create') {
      data.status = 2;
      await FeedbackApi.createFeedback(data)
      message.success(t('common.createSuccess'))
    } else {
      data.status = 1;
      await FeedbackApi.updateFeedback(data)
      message.success(t('common.manageSuccess'))
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
    userId: undefined,
    phone: undefined,
    name: undefined,
    type: undefined,
    img: undefined,
    status: undefined,
    content: undefined
  }
  formRef.value?.resetFields()
}
</script>
