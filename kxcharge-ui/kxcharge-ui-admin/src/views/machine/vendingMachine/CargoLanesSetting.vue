<template>
  <el-form
    ref="formRef"
    :model="formData"
    :rules="formRules"
    label-width="150px"
    v-loading="formLoading"
    :disabled="formType == 'info'"
    style="background-color: #fff;"
  >
    <div class="header">
      <span class="title">货道配置</span>
    </div>
    <el-form-item label="选择货道模板" prop="template">
      <el-col :span="6">
        <el-select v-model="formData.typeId" placeholder="请选择模板" @change="templateChange">
          <el-option
            v-for="item in templateOptions"
            :key="(item as any).id"
            :label="(item as any).name"
            :value="(item as any).id"
          />
        </el-select>
      </el-col>
    </el-form-item>
    <el-form-item v-for="(item, index) in formData.vendingSlotCreateReqVOS" :key="index" :prop="'vendingSlotCreateReqVOS.' + index" :label="(item as any).floor + '层：'" label-width="85">
      <el-form-item v-for="(data, ind) in (item as any).vendingSlotBaseVOS" :key="ind" :prop="'vendingSlotCreateReqVOS.' + index + '.vendingSlotBaseVOS.' + ind" style="margin: 0 10px 10px 0;">
        <el-form-item class="goodName" :label="'货道'+ data.columnNumber" :prop="'vendingSlotCreateReqVOS.' + index + '.vendingSlotBaseVOS.' + ind + '.productId'" label-width="80">
          <el-col :span="24">
            <el-select v-model="data.productId" placeholder="商品" @change="selectGood(data.productId, index, ind)" :disabled="defaultObj.typeId == formData.typeId">
              <el-option
                v-for="ele in goodsOptions"
                :key="(ele as any).id"
                :label="(ele as any).name"
                :value="(ele as any).id"
              />
            </el-select>
            <el-image
              v-if="data.productImg"
              style="width: 80px; height: 80px; margin-top: 8px;"
              :src="data.productImg"
              :zoom-rate="1.2"
              :max-scale="7"
              :min-scale="0.2"
              :preview-src-list="[data.productImg]"
              fit="cover"
            />
          </el-col>
        </el-form-item>
        <el-form-item label="最大库存" class="goodNum" :prop="'vendingSlotCreateReqVOS.' + index + '.vendingSlotBaseVOS.' + ind + '.productMaxNumber'" label-width="80">
          <el-input-number v-model="data.productMaxNumber" :min="0" :disabled="defaultObj.typeId == formData.typeId" />
        </el-form-item>
      </el-form-item>
    </el-form-item>
    <div class="btn" v-if="isShowBtn">
      <el-form-item :label-width="'0px'">
        <el-button @click="submitForm" type="primary" :disabled="formLoading || defaultObj.typeId == formData.typeId">确 定</el-button>
        <el-button @click="goBack">取 消</el-button>
      </el-form-item>
    </div>
  </el-form>
</template>

<script setup lang="ts">
import * as CargoLangsApi from '@/api/machine/cargoLanes/index'
import * as SpuApi from '@/api/mall/product/spu'
import * as VendingMachineApi from '@/api/machine/vendingMachine/index'
import { cloneDeep } from 'lodash-es';
import { useTagsViewStore } from '@/store/modules/tagsView';
import gwEventBus from '@/utils/energy-eventbus';
defineProps({
  isShowBtn: { type: Boolean, default: true }
})

const route = useRoute()
const router = useRouter()
const tagsViewStore = useTagsViewStore()
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref({
  typeId: '',
  vendingSlotCreateReqVOS: []
})
const defaultObj: any = ref({})

const formRules = ref({
  // vendingSlotCreateReqVOS: [{ required: true,  trigger: 'blur' }]
})
const formType = ref(route.query.type)
const formId = ref(route.query.id)
const templateOptions = ref([])
const goodsOptions = ref([])
const templateChange = () => {
  try {
    formData.value.vendingSlotCreateReqVOS.length = 0
    if (defaultObj.value.typeId === formData.value.typeId) {
      formData.value.vendingSlotCreateReqVOS = defaultObj.value.vendingSlotCreateReqVOS
      return
    }
    // @ts-ignore
    const findData = templateOptions.value.find(item => item.id === formData.value.typeId)
    if (findData) {
      // @ts-ignore
      findData.content.forEach(item => {
        const obj = {
          floor: Number(item.floor),
          vendingSlotBaseVOS: []
        }
        for (let index = 1; index <= Number(item.num); index++) {
          // @ts-ignore
          obj.vendingSlotBaseVOS.push({
            columnNumber: index,
            productMaxNumber: 0,
            productId: undefined,
            productImg: undefined
          })
        }
        // @ts-ignore
        formData.value.vendingSlotCreateReqVOS.push(obj)
      })
    }
  } catch (error) {
    console.log(`output->error`,error)
  }
}

const selectGood = (id, index, ind) => {
  // @ts-ignore
  const good = goodsOptions.value.find(item => item.id === id)
  // @ts-ignore
  formData.value.vendingSlotCreateReqVOS[index].vendingSlotBaseVOS[ind].productImg = good.picUrl ?? ''
}

const formRef = ref() // 表单 Ref

const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    const parmas = {...{
      machineId: defaultObj.value.id,
      slotTypeId: data.typeId
    }, ...data}
    await VendingMachineApi.updateSlotType(parmas)
    message.success(t('common.updateSuccess'))
    router.push('/machine/vending-machine')
    gwEventBus.emit('toVendingMachine')
    tagsViewStore.delView(route)
  }finally {
    formLoading.value = false
  }
}

const goBack = () => {
  router.back()
  tagsViewStore.delView(route)
}



const getCargoLangs = async () => {
  try {
    const data = await CargoLangsApi.getCargoLangsAll()
    templateOptions.value = data
  } catch (error) {
    console.log(`output->error`,error)
  }
}

const getCommodity = async () => {
  try {
    const data = await SpuApi.getSimpleAll()
    goodsOptions.value = data
  } catch (error) {
    console.log(`output->error`,error)
  }
}

const initFormData = async () => {
  if (formId.value) {
    try {
    const data = await VendingMachineApi.getVendingMachineInfo(formId.value)
    defaultObj.value = cloneDeep(data)
      formData.value.typeId = data.typeId
      formData.value.vendingSlotCreateReqVOS = data.vendingSlotCreateReqVOS
    } catch (error) {
      
    }
  }
}



defineExpose({
  formData,
  initFormData
})

onMounted(() => {
  getCargoLangs()
  getCommodity()
  initFormData()
})
</script>

<style scoped lang="scss">
.header {
  .title {
    color: #008fe0;
    display: flex;
    &::before {
      display: inline-block;
      content: '';
      width: 5px;
      height: 25px;
      background-color: #008fe0;
      margin-right: 20px;
    }
  }
}

::v-deep {
  .goodName, .goodNum {
    .el-form-item__content {
      width: 115px;
      margin-right: 10px;
    }
  }
  .el-form-item__content {
    align-items: flex-start;
  }
}
.btn {
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
