<template>
  <div>
    <Dialog title="车辆管理" v-model="dialogVisible" :close-on-click-modal="false">
      <el-form ref="formRef" :model="formData" label-width="150px">
        <el-form-item
          v-for="(data, index) in formData.plateRespVOS"
          :key="index"
          :prop="'plateRespVOS.' + index"
          :label-width="2"
        >
          <el-form-item
            label="车牌号"
            :prop="'plateRespVOS.' + index + '.plateNumber'"
            :label-width="80"
            style="margin-right: 10px"
          >
            <el-input v-model="(data as any).plateNumber" placeholder="添加车辆后自动显示" disabled />
          </el-form-item>
          <el-button type="primary" :disabled="(data as any).isDefault" @click="setDefault((data as any).plateId)">{{
            (data as any).isDefault ? '已设为默认' : '设为默认'
          }}</el-button>
          <el-button type="primary" @click="addCar((data as any).plateId, index)">添加车辆</el-button>
          <el-button icon="Plus" @click="addItem(index)" />
          <el-button icon="Minus" :disabled="formData.plateRespVOS.length <= 1" @click="removeCar((data as any).plateId, index)" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </template>
    </Dialog>
    <Dialog title="添加车辆" v-model="dialogVisible2" :width="600" :close-on-click-modal="false">
      <el-form
        ref="otherFormRef"
        :model="otherFormDate"
        label-width="150px"
        v-loading="otherFormLoading"
        :rules="otherFormRules"
      >
        <el-form-item label="车牌号码" prop="plateNumber">
          <el-col :span="15">
            <el-input v-model="otherFormDate.plateNumber" />
          </el-col>
        </el-form-item>
        <el-form-item label="车辆类型" prop="plateType">
          <el-select v-model="otherFormDate.plateType" placeholder="请选择网络链接类型">
            <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.PLATE_TYPE)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="submitForm" type="primary" :disabled="otherFormLoading">确 定</el-button>
        <el-button @click="dialogVisible2 = false">取 消</el-button>
      </template>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import * as UserApi from "@/api/member/user/index";
const otherFormLoading = ref(false)
const dialogVisible = ref(false)
const dialogVisible2 = ref(false)
const message = useMessage() // 消息弹窗
const userId = ref(0)
const formData = ref({
  plateRespVOS: []
})
const otherFormIndex = ref(0)
const otherFormDate = ref({
  plateNumber: '',
  plateType: '',
  plateId: '',
  id: ''
})
const otherFormRules = reactive({
  plateNumber: [{ required: true, message: '车牌号不能为空', trigger: 'blur' }],
  plateType: [{ required: true, message: '类型不能为空', trigger: 'blur' }]
})
/** 打开弹窗 */
const open = (id?: number) => {
  dialogVisible.value = true
  userId.value = id
  if (id) {
    getCarList(id)
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const getCarList = async (id) => {
  try {
    const data = await UserApi.getUserCar(id)
    if (data.length > 0) {
      formData.value.plateRespVOS.length = 0
      data.forEach(item => {
        // @ts-ignore
        formData.value.plateRespVOS.push({
          plateNumber: item.plateNumber,
          isDefault: !!item.isDefault,
          plateId: item.id
        })
      })
    }else {
        // @ts-ignore
      formData.value.plateRespVOS.push({
        plateNumber: '',
        isDefault: false,
        plateId: ''
      })
    }
    console.log(`output->data`,data)    
  } catch {}
}

// 设置默认车辆
const setDefault = async (id) => {
  if (!id) return
  try {
    const parmas = {
      id,
      isDefault: 1,
      userId: userId.value
    }
    await UserApi.updateDefault(parmas)
    message.success('设置成功!')
    getCarList(userId.value)
  } catch {}
}

const otherFormRef = ref()
const submitForm = async () => {
  if (!otherFormRef) return
  const valid = await otherFormRef.value.validate()
  if (!valid) return
  // 提交请求
  otherFormLoading.value = true
  try {
    const data = otherFormDate.value
    data['userId'] = userId.value
    data['isDefault'] = formData.value.plateRespVOS.length === 1 ? 1 : 0
    if (data.id) {
      await UserApi.updateCar(data)
    } else {
      await UserApi.addCar(data)
    }
    // console.log(`output->addData`,addData)
    // const parmas = {
    //   ...data,
    //   ...{plateId: addData, isDefault: }
    // }
    message.success('操作成功!')
    getCarList(userId.value)
    dialogVisible2.value = false
  } catch {
    dialogVisible2.value = false
  }
}
const addCar = async (id: number, index) => {
  dialogVisible2.value = true
  otherFormIndex.value = index
  resetOtherForm()
  if (id) {
    otherFormLoading.value = true
    try {
      otherFormDate.value = await UserApi.getCar(id)
    } finally {
      otherFormLoading.value = false
    }
  }
}

const addItem = (index: number) => {
  if (!formData.value.plateRespVOS[index].plateNumber) {
    message.info('请先添加车辆!')
    return
  }
  // @ts-ignore
  formData.value.plateRespVOS.push({
    plateNumber: '',
    isDefault: false,
    plateId: ''
  })
}

const removeCar = async (id: number, index: number) => {
  try {
    await UserApi.delCar(id)
    message.success('移除成功!')
    formData.value.plateRespVOS.splice(index, 1)
  } catch(error: any) {
    message.error(error)
  }
}

const resetOtherForm = () => {
  otherFormDate.value = {
    plateNumber: '',
    plateType: '',
    plateId: '',
    id: ''
  }
  otherFormRef.value?.resetFields()
}
</script>

<style scoped lang="scss">
::v-deep .el-form-item__content {
  width: 300px;
}
.el-button {
  padding: 8px;
}
</style>
