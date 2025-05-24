<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" :close-on-click-modal="false" :width="600">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
      :disabled="formType === 'detail'"
    >
      <el-form-item label="货道模板名称" prop="name">
        <el-col :span="14">
          <el-input v-model="formData.name" v-no-space placeholder="请输入货道模板名称" />
        </el-col>
      </el-form-item>
      <el-form-item label="货道内容" prop="content" style="margin-bottom: 0;">
        <el-form-item v-for="(item, index) in formData.content" :key="index" :label="item.floor + '层：'" :prop="'content.' + index" label-width="50" class="cargoNum">
          <el-form-item label="货道数" :prop="'content.' + index + '.num'" label-width="60" :rules="[{ required: true, message: '货道数不能为空', trigger: 'blur' }]">
            <el-col :span="24">
              <el-input-number v-model="item.num" :min="0" />
            </el-col>
            
          </el-form-item>
          <el-icon
            :color="'#008fe0'"
            :size="39"
            style="margin-left: 15px"
            @click="removeTimeInterval(index)"
            v-if="index !== 0 && formType !== 'detail'"
          >
            <RemoveFilled />
          </el-icon>
        </el-form-item>
      </el-form-item>
      <div class="addCargo" style="display: flex; align-items: center; margin-left: 150px; margin-bottom: 10px;" v-if="formType !== 'detail'">
        <el-icon :color="'#008fe0'" :size="39" @click="addCargo"><CirclePlusFilled /></el-icon>
        <span style="margin-left: 10px"> 点击添加货道 </span>
      </div>
      <el-form-item label="备注" prop="remark">
        <el-col :span="18">
          <el-input
            v-model="formData.remark"
            :rows="5"
            type="textarea"
            placeholder="请输入备注说明"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-col :span="14">
          <el-radio-group v-model="formData.status" class="ml-4">
            <el-radio :label="0">开启</el-radio>
            <el-radio :label="1">关闭</el-radio>
          </el-radio-group>
        </el-col>
      </el-form-item>
      <div class="btn" style="display: flex; justify-content: center; align-items: center;" v-if="formType !== 'detail'">
        <el-form-item :label-width="'0px'">
          <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </el-form-item>
      </div>
    </el-form>
  </Dialog>
</template>

<script setup lang="ts">
import * as CargoLanesApi from '@/api/machine/cargoLanes/index'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  content: [
    {num: undefined, floor: 1}
  ],
  status: 1,
  remark: undefined
})
const formRules = reactive({
  name: [{ required: true, message: '货道模板名称不能为空', trigger: 'blur' }],
  content: [{ required: true,  trigger: 'blur' }],
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
      const data = await CargoLanesApi.getCargoLansInfo(id)
      formData.value = data
      console.log(`output->data`,data)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const addCargo = () => {
  formData.value.content.push({num: undefined, floor: formData.value.content.length + 1})
}

const removeTimeInterval = (i) => {
  formData.value.content.splice(i, 1)
  formData.value.content.forEach((item, index) => {
    item.floor = index + 1
  })
}

const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    if (formData.value.id) {
      await CargoLanesApi.updateCargoLangs(data)
    }else {
      await CargoLanesApi.createCargoLangs(data)
    }
    message.success(t('common.createSuccess'))
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
  console.log(`output->formData.value`,formData.value)
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: undefined,
    content: [
      {num: undefined, floor: 1}
    ],
    status: 1,
    remark: undefined
  }
  formRef.value?.resetFields()
}
</script>

<style scoped lang="scss">
::v-deep {
  .cargoNum {
    margin-bottom: 10px;
    .el-form-item__label:before {
      display: none;
    }
  }
}
</style>