<template>
  <el-form
    ref="formRef"
    :model="formData"
    label-width="150px"
    v-loading="formLoading"
    style="background-color: #fff;"
  >
    <div class="header">
      <span class="title">商品管理</span>
    </div>
    <el-form-item v-for="(item, index) in formData.data" :key="index" :prop="'data.' + index" :label="(item as any).floor + '层：'" label-width="85">
      <el-form-item v-for="(data, ind) in (item as any).vendingSlotBaseVOS" :key="ind" :prop="'data.' + index + '.vendingSlotBaseVOS.' + ind" style="margin: 0 10px 10px 0;">
        <el-form-item class="goodName" :label="'货道'+ data.columnNumber" :prop="'data.' + index + '.vendingSlotBaseVOS.' + ind + '.productId'" label-width="80">
          <el-col :span="24">
            <el-select v-model="data.productId" placeholder="商品" :disabled="true">
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
        <el-form-item label="剩余库存" class="goodNum" :prop="'data.' + index + '.vendingSlotBaseVOS.' + ind + '.productNumber'" label-width="80">
          <el-input-number v-model="data.productNumber" :min="0" :disabled="true" />
        </el-form-item>
        <div class="btn" style="margin-top: 5px;">
          <el-form-item :label-width="'0px'">
            <el-button link type="primary" @click="putSlot(data.slotId, 'warehousing')">入库</el-button>
            <el-button link type="primary" @click="putSlot(data.slotId, 'goodsChange')">商品修改</el-button>
          </el-form-item>
        </div>
      </el-form-item>
    </el-form-item>
    <div style="display: flex; justify-content: center; align-items: center; padding-bottom: 10px;">
      <el-button @click="goBack()">返回</el-button>
    </div>
  </el-form>
  <Dialog :title="dialogTitle" v-model="warehousingDialogVisible" width="600">
    <el-form
      ref="warehousingFormRef"
      :model="warehousingFormData"
      :rules="warehousingFormRules"
      label-width="150px"
      v-loading="warehousingFoading"
      style="background-color: #fff;"
    >
      <el-form-item label="商品名称" prop="productId">
        <el-col :span="16">
          <el-select v-model="warehousingFormData.productId" @change="changeGood($event)" placeholder="商品" :disabled="dialogType === 'warehousing'">
            <el-option
              v-for="ele in goodsOptions"
              :key="(ele as any).id"
              :label="(ele as any).name"
              :value="(ele as any).id"
            />
          </el-select>
        </el-col>
      </el-form-item>
      <el-form-item label="商品图片" prop="picUrl">
        <el-image style="width: 100px; height: 100px" :src="warehousingFormData.picUrl" :disabled="true" />
      </el-form-item>
      <el-form-item label="剩余库存" prop="productNumber" v-if="dialogType === 'warehousing'">
        <el-input-number v-model="warehousingFormData.productNumber" :min="0" :disabled="true" />
      </el-form-item>
      <el-form-item label="最大库存" prop="productMaxNumber">
        <el-input-number v-model="warehousingFormData.productMaxNumber" :min="0" :disabled="dialogType === 'warehousing'" />
      </el-form-item>
      <el-form-item label="入库数量" prop="number">
        <el-input-number v-model="warehousingFormData.number" :min="0" :max="dialogType === 'warehousing' ? warehousingFormData.productMaxNumber - warehousingFormData.productNumber : warehousingFormData.productMaxNumber" />
      </el-form-item>
      <el-form-item v-if="dialogType === 'warehousing'">
        <div>注: 剩余库存+入库数量不得大于最大库存</div>
      </el-form-item>
      <el-form-item :label-width="220">
        <el-button type="primary" @click="warehousingSubmit" :disabled="warehousingFoading">确定</el-button>
        <el-button @click="warehousingDialogVisible = false">取消</el-button>
      </el-form-item>
    </el-form>
  </Dialog>
</template>

<script setup lang="ts">
import * as SpuApi from '@/api/mall/product/spu'
import * as MachineApi from '@/api/machine/vendingMachine/index'
import * as SlotApi from '@/api/machine/vendingSlot/index'
import { useTagsViewStore } from '@/store/modules/tagsView';

const message = useMessage() // 消息弹窗

const tagsViewStore = useTagsViewStore()
const route = useRoute()
const router = useRouter()
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref({
  data: []
})
const warehousingDialogVisible = ref(false)
const goodsOptions = ref([])
const formId = ref(route.query.id)

const dialogType = ref('')
const dialogTitleMap = new Map([
  ['warehousing', '入库'],
  ['goodsChange', '商品修改']
])
const dialogTitle = ref('')
const warehousingFormData = ref({
  // data: [{ required: true,  trigger: 'blur' }]
  vendingSlotId: undefined,
  productId: undefined,
  picUrl: undefined,
  productNumber: undefined,
  productMaxNumber: undefined,
  number: undefined,
})
const warehousingFormRules = ref({
  number: [{ required: true, message: '请输入入库数量', trigger: 'blur' }]
})
const warehousingFoading = ref(false)

const putSlot = async (slotId, type) => {
  dialogType.value = type
  dialogTitle.value = dialogTitleMap.get(type)
  try {
    const data = await SlotApi.getVendingSlot(slotId)
    warehousingFormData.value = data
    warehousingFormData.value.vendingSlotId = data.id
    warehousingDialogVisible.value = true
  } catch (error) {
    
  }
}

const warehousingFormRef = ref()
const warehousingSubmit = async () => {
  // 校验表单
  if (!warehousingFormRef) return
  const valid = await warehousingFormRef.value.validate()
  if (!valid) return
  // 提交请求
  warehousingFoading.value = true
  try {
    if (dialogType.value === 'warehousing') {
      const data = {
        vendingSlotId: warehousingFormData.value.vendingSlotId,
        number: warehousingFormData.value.number
      }
      await SlotApi.putVendingSlot(data)
      message.success('入库成功!')
    }else {
      const data = {
        id: warehousingFormData.value.vendingSlotId,
        productNumber: warehousingFormData.value.number,
        productId: warehousingFormData.value.productId,
        productMaxNumber: warehousingFormData.value.productMaxNumber
      }
      await SlotApi.updateVendingSlot(data)
      message.success('修改成功!')
    }
    warehousingDialogVisible.value = false
    initData()
  } catch (error) {}finally{
    warehousingFoading.value = false
  }
}

const goBack = () => {
  router.back()
  tagsViewStore.delView(route)
}

const changeGood = (e) => {
  const find = goodsOptions.value.find(item => item.id === e)
  warehousingFormData.value.picUrl = find.picUrl ?? ''
}

const getCommodity = async () => {
  try {
    const data = await SpuApi.getSimpleAll()
    goodsOptions.value = data
  } catch (error) {
    console.log(`output->error`,error)
  }
}

const initData = async () => {
  formLoading.value = true
  try {
    const data = await MachineApi.getSlots(formId.value)
    formData.value.data = data
  } finally {
    formLoading.value = false
  }
}

onMounted(() => {
  getCommodity()
  initData()
})
</script>

<style scoped lang="scss">
.header {
  padding: 20px;
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
</style>