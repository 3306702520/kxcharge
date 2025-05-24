<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" :close-on-click-modal="false" @close="dialogClose">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="型号信息" name="modelInfo">
        <el-form
          ref="formRef"
          :model="formData"
          :rules="formRules"
          label-width="120px"
          v-loading="formLoading"
        >
          <el-form-item label="运营商" prop="orgId">
            <el-select v-model="formData.orgId" placeholder="请选择运营商">
              <el-option
                v-for="item in operatorOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="电桩品牌" prop="equipmentBrandId">
            <el-col :span="13">
              <el-select v-model="formData.equipmentBrandId" @visible-change="openBrand($event)" placeholder="请先选择运营商, 再选择品牌" :disabled="!formData.orgId">
                <el-option
                  v-for="item in equipmentBrandOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
          </el-form-item>
          <el-form-item label="电桩型号" prop="equipmentModel">
            <el-col :span="13">
              <el-input v-model="formData.equipmentModel" v-no-space placeholder="请输入电站型号" />
            </el-col>
          </el-form-item>
          <el-form-item label="终端数量" prop="number">
            <el-col :span="13">
              <el-input-number v-model="formData.number" :min="0" :max="2" :step="1" placeholder="充电枪数量" />
            </el-col>
          </el-form-item>
          <el-form-item label="电桩类型" prop="type">
            <el-radio-group v-model="formData.type">
              <el-radio
                v-for="dict in getIntDictOptions(DICT_TYPE.ELECTRIC_TYPE)"
                :key="dict.value"
                :label="dict.value"
              >
                {{ dict.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="设备图片" prop="img">
            <UploadImg v-model="formData.img" />
          </el-form-item>
          <el-form-item label="备注说明" prop="remark">
            <el-col :span="15">
              <el-input :rows="5" type="textarea" v-model="formData.remark" placeholder="请输入备注说明" />
            </el-col>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="电桩工作信息" name="chargepileInfo" :disabled="isDisabledChargePile">
        <el-form
          ref="otherFormRef"
          :model="otherFormData"
          :rules="otherFormRules"
          label-width="150px"
          v-loading="otherFormLoading"
        >
          <el-form-item label="电桩功率" prop="power">
            <el-col :span="13">
              <el-input type="number" v-model="otherFormData.power" placeholder="请输入电桩功率">
                <template #append>kW</template>
              </el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="辅助电源" prop="supPower">
            <el-col :span="13">
              <el-input type="number" v-model="otherFormData.supPower" placeholder="请输入辅助电源">
                <template #append>V</template>
              </el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="枪线温度预警" prop="gunTemperatureWarn">
            <el-col :span="13">
              <el-input type="number" v-model="otherFormData.gunTemperatureWarn" placeholder="请输入枪线温度预警">
                <template #append>℃</template>
              </el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="枪线温度断充" prop="gunTemperatureStop">
            <el-col :span="13">
              <el-input type="number" v-model="otherFormData.gunTemperatureStop" placeholder="请输入枪线温度断充">
                <template #append>℃</template>
              </el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="输入电压" prop="inputv">
            <el-col :span="13">
              <el-input type="number" v-model="otherFormData.inputv" placeholder="请输入输入电压">
                <template #append>V</template>
              </el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="最大电压" prop="maxv">
            <el-col :span="13">
              <el-input type="number" v-model="otherFormData.maxv" placeholder="请输入最大电压">
                <template #append>V</template>
              </el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="最小电压" prop="minv">
            <el-col :span="13">
              <el-input type="number" v-model="otherFormData.minv" placeholder="请输入最小电压">
                <template #append>V</template>
              </el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="最大电流" prop="maxa">
            <el-col :span="13">
              <el-input type="number" v-model="otherFormData.maxa" placeholder="请输入最大电流">
                <template #append>A</template>
              </el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="最小电流" prop="mina">
            <el-col :span="13">
              <el-input type="number" v-model="otherFormData.mina" placeholder="请输入最小电流">
                <template #append>A</template>
              </el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="恒功率输出电压区间">
            <el-form-item prop="maxp">
              <el-input v-model="otherFormData.maxp" type="number" placeholder="电压下限">
                <template #append>V</template>
              </el-input>
            </el-form-item>
            <span style="margin: 0 10px;">至</span>
            <el-form-item prop="minp">
              <el-input v-model="otherFormData.minp" type="number" placeholder="电压上限">
                <template #append>V</template>
              </el-input>
            </el-form-item>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button @click="nextStep" type="primary" v-if="activeTab === 'modelInfo'">下一步</el-button>
      <el-button @click="submitForm" type="primary" :disabled="formLoading && otherFormLoading" v-else>确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import * as EquipmentModelApi from '@/api/operations/equipmentModel'
import * as EquipmentBrandApi from '@/api/operations/equipmentBrand'
import { getOperatorAll } from '@/api/system/operator'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const operatorOptions = reactive([]) // 运营商选择器
const equipmentBrandOptions = reactive([])
const activeTab = ref('modelInfo')
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const otherFormLoading = ref(false)
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const isDisabledChargePile = ref(true)
const formData = ref({
  id: undefined,
  orgId: undefined,
  equipmentBrandId: undefined,
  equipmentModel: undefined,
  number: undefined,
  type: 1,
  img: undefined,
  remark: undefined
})
const otherFormData = ref({
  power: undefined,
  supPower: undefined,
  gunTemperatureWarn: undefined,
  gunTemperatureStop: undefined,
  inputv: undefined,
  maxv: undefined,
  minv: undefined,
  maxa: undefined,
  mina: undefined,
  maxp: undefined,
  minp: undefined
})
const formRules = reactive({
  orgId: [{ required: true, message: '运营商id不能为空', trigger: 'blur' }],
  equipmentBrandId: [{ required: true, message: '品牌id不能为空', trigger: 'blur' }],
  equipmentModel: [{ required: true, message: '电站型号不能为空', trigger: 'blur' }],
  number: [{ required: true, message: '充电枪数量不能为空', trigger: 'blur' }],
  type: [{ required: true, message: '电桩类型不能为空', trigger: 'blur' }]
  // img: [{ required: true, message: '设备图片不能为空', trigger: 'blur' }],
  // remark: [{ required: true, message: '备注说明不能为空', trigger: 'blur' }],
  // gunTemperatureWarn: [{ required: true, message: '枪线温度预警不能为空', trigger: 'blur' }],
  // gunTemperatureStop: [{ required: true, message: '枪线温度断充不能为空', trigger: 'blur' }],
  // inputv: [{ required: true, message: '输入电压不能为空', trigger: 'blur' }],
  // maxv: [{ required: true, message: '最大电压不能为空', trigger: 'blur' }],
  // minv: [{ required: true, message: '最小电压不能为空', trigger: 'blur' }],
  // maxa: [{ required: true, message: '最大电流不能为空', trigger: 'blur' }],
  // mina: [{ required: true, message: '最小电流不能为空', trigger: 'blur' }]
})
const otherFormRules = reactive({
  power: [{ required: true, message: '电桩功率不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const otherFormRef = ref()
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
      const data = await EquipmentModelApi.getEquipmentModel(id)
      formData.value = data
      otherFormData.value = data
    } finally {
      formLoading.value = false
      otherFormLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

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
    const data = formData.value as unknown as EquipmentModelApi.EquipmentModelVO
    const otherData = otherFormData.value as unknown as EquipmentModelApi.EquipmentModelVO
    const parmas = {...data, ...otherData}
    if (formType.value === 'create') {
      await EquipmentModelApi.createEquipmentModel(parmas)
      message.success(t('common.createSuccess'))
    } else {
      await EquipmentModelApi.updateEquipmentModel(parmas)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    otherFormLoading.value = false

    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const dialogClose = async () => {
  isDisabledChargePile.value = true
  activeTab.value ='modelInfo'

}

const openBrand = async (e) => {
  if (e) {
    equipmentBrandOptions.length = 0
    const parmas ={
      orgId: formData.value.orgId
    }
    const val = await EquipmentBrandApi.getEquipmentBrandAll(parmas);
    val.forEach((item) => {
      // @ts-ignore
      equipmentBrandOptions.push({
        value: item.id,
        label: item.brandName
      })
    })
  }
}

const nextStep = async () => {
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  activeTab.value = 'chargepileInfo'
  isDisabledChargePile.value = false
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    orgId: undefined,
    equipmentBrandId: undefined,
    equipmentModel: undefined,
    number: undefined,
    type: 1,
    img: undefined,
    remark: undefined,

  }
  otherFormData.value = {
    power: undefined,
    supPower: undefined,
    gunTemperatureWarn: undefined,
    gunTemperatureStop: undefined,
    inputv: undefined,
    maxv: undefined,
    minv: undefined,
    maxa: undefined,
    mina: undefined,
    maxp: undefined,
    minp: undefined
  }
  formRef.value?.resetFields()
  // otherFormData.value?.resetFields()

}

// 获取归属运营商列表
const getOperator = async () => {
  const data = await getOperatorAll()
  // console.log(`output->data`,data)
  data.forEach((item) => {
    // @ts-ignore
    operatorOptions.push({
      value: item.id,
      label: item.orgName
    })
  })

}

onMounted(() => {
  getOperator()
})
</script>
