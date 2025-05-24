<template>
  <ContentWrap>
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="150px"
      v-loading="formLoading"
      :disabled="formType == 'info'"
    >
      <div class="header">
        <span class="title">自动售货机基本信息</span>
      </div>
      <el-form-item label="自动售货机名称" prop="name">
        <el-col :span="6">
          <el-input v-model="formData.name" placeholder="请输入自动售货机名称" />
        </el-col>
      </el-form-item>
      <el-form-item label="设备code" prop="deviceCode">
        <el-col :span="6">
          <el-input v-model="formData.deviceCode" placeholder="请输入设备code" />
        </el-col>
      </el-form-item>
      <el-form-item label="设备imei" prop="imei">
        <el-col :span="6">
          <el-input v-model="formData.imei" placeholder="请输入设备imei" />
        </el-col>
      </el-form-item>
      <el-form-item label="设备照片" prop="photo">
        <UploadImg v-model="formData.photo" />
      </el-form-item>
      <el-form-item label="详细地址" prop="position">
        <el-col :span="6">
          <el-input v-model="formData.position" v-no-space placeholder="请输入详细地址" />
        </el-col>
        <el-button @click="searchAdress" type="primary" style="margin-left: 10px">地图定位</el-button>
      </el-form-item>

      <el-row>
        <el-form-item label="经度" prop="longitude" :label-width="'150px'">
          <el-col :span="18">
            <el-input v-model="formData.longitude" />
          </el-col>
        </el-form-item>
        <el-form-item label="纬度" prop="latitude" :label-width="'80px'">
          <el-col :span="18">
            <el-input v-model="formData.latitude" />
          </el-col>
        </el-form-item>
      </el-row>
      <el-form-item>
        <el-col :span="10">
          <MapContainer
            :addressLocation="{ lng: formData.longitude, lat: formData.latitude }"
            ref="mapRef"
            @address="getAddress($event)"
            @geolocation="getLocation($event)"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="当前定位">
        <span>{{ pointAddress }}</span>
      </el-form-item>
      <el-form-item label="状态" prop="status" v-if="formType != 'update'">
        <el-col :span="14">
          <el-radio-group v-model="formData.status" class="ml-4">
            <el-radio :label="0">开启</el-radio>
            <el-radio :label="1">关闭</el-radio>
          </el-radio-group>
        </el-col>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-col :span="6">
          <el-input
            v-model="formData.remark"
            :rows="5"
            type="textarea"
            placeholder="请输入备注说明"
          />
        </el-col>
      </el-form-item>
      <CargoLanesSetting ref="cargoLanesSetting" :isShowBtn="false" />
    </el-form>
    <div class="btn">
      <el-form-item :label-width="'0px'">
        <el-button @click="submitForm" type="primary" :disabled="formLoading" v-if="formType != 'info'">确 定</el-button>
        <el-button @click="goBack">取 消</el-button>
      </el-form-item>
    </div>
  </ContentWrap>
</template>

<script setup lang="ts">
import { useTagsViewStore } from '@/store/modules/tagsView';
import CargoLanesSetting from './CargoLanesSetting.vue'
import * as VendingMachineApi from '@/api/machine/vendingMachine/index'
import gwEventBus from '@/utils/energy-eventbus';
const route = useRoute()
const router = useRouter()
const tagsViewStore = useTagsViewStore()
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref(route.query.type) // 表单的类型：create - 新增；update - 修改
const formId = ref(route.query.id)
const formTitle = new Map()
  .set('create', '自动售货机新增')
  .set('update', '自动售货机修改')
  .set('info', '自动售货机详情')
const formData = ref({
  id: '',
  name: '',
  deviceCode: '',
  imei: '',
  photo: '',
  position: '',
  longitude: '',
  latitude: '',
  remark: '',
  status: 1
})
const formRules = ref({
  name: [{ required: true, message: '请输入自动售货机名称', trigger: 'blur' }],
  deviceCode: [{ required: true, message: '请输入设备code', trigger: 'blur' }],
  imei: [{ required: true, message: '请输入设备imei', trigger: 'blur' }],
  position: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const pointAddress = ref('')

const cargoLanesSetting = ref()
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    const cargoLangesData = cargoLanesSetting.value.formData
    if (formType.value === 'create') {
      await VendingMachineApi.createVendingMachine({...data, ...cargoLangesData})
      message.success(t('common.createSuccess'))
    }else {
      await VendingMachineApi.updateVendingMachine({...data, ...cargoLangesData})
      message.success(t('common.updateSuccess'))
    }
    // 发送操作成功的事件
    // emit('success')
    router.back()
    gwEventBus.emit('toVendingMachine')
    tagsViewStore.delView(route)
    
  } finally {
    formLoading.value = false
  }
}

const goBack = () => {
  router.back()
  tagsViewStore.delView(route)
}

// 地图定位
const mapRef = ref()
const searchAdress = () => {
  // @ts-ignore
  mapRef.value.searchAdress(formData.value.position)
}
const getAddress = (e) => {
  pointAddress.value = e
}

const getLocation = (e) => {
  formData.value.latitude = e.latitude
  formData.value.longitude = e.longitude
}

const initData = async () => {
  console.log(`output->formId.value`,formId.value)
  if (formId.value) {
    const data = await VendingMachineApi.getVendingMachineInfo(formId.value)
    formData.value = data
  }
}


onMounted(() => {
  route.meta.title = formTitle.get(formType.value)
  tagsViewStore.visitedViews.find(k => k.path === route.path)!.meta.title = route.meta.title
  tagsViewStore.visitedViews.find(k => k.path === route.path)!.title = route.meta.title
  initData()
})

</script>

<style lang="scss" scoped>
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
.goodName {
  ::v-deep .el-form-item__content {
    width: 120px;
  }
}
.btn {
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
