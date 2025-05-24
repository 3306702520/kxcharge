<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="130px"
    >
      <el-form-item label="充电站乡镇编码" prop="townCode">
        <el-input
          v-model="queryParams.townCode"
          placeholder="请输入充电站乡镇编码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="运营商编号" prop="operationId">
        <el-input
          v-model="queryParams.operationId"
          placeholder="请输入运营商编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="站点ID" prop="stationId">
        <el-input
          v-model="queryParams.stationId"
          placeholder="请输入站点ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="站点名称" prop="stationName">
        <el-input
          v-model="queryParams.stationName"
          placeholder="请输入站点名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备所属放ID" prop="equipmentOwnerId">
        <el-input
          v-model="queryParams.equipmentOwnerId"
          placeholder="请输入设备所属放ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="充电站国家代码" prop="countryCode">
        <el-input
          v-model="queryParams.countryCode"
          placeholder="请输入充电站国家代码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="充电站省市辖区编号" prop="areaCode">
        <el-input
          v-model="queryParams.areaCode"
          placeholder="请输入充电站省市辖区编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="详细地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入详细地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="服务电话" prop="serviceTel">
        <el-input
          v-model="queryParams.serviceTel"
          placeholder="请输入服务电话"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="站点类型1:公共；50:个人；100:公交(专用);101:环卫(专用);102:物流(专用);103:出租车(专用);255:其他" prop="stationType">
        <el-select
          v-model="queryParams.stationType"
          placeholder="请选择站点类型1:公共；50:个人；100:公交(专用);101:环卫(专用);102:物流(专用);103:出租车(专用);255:其他"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="站点状态:0:未知；1:建设中；5:关闭下线；6:维护中；50:正常使用" prop="stationStatus">
        <el-select
          v-model="queryParams.stationStatus"
          placeholder="请选择站点状态:0:未知；1:建设中；5:关闭下线；6:维护中；50:正常使用"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="车位数量:可停放进行充电的车位总数， 默认：0未知" prop="parkNums">
        <el-input
          v-model="queryParams.parkNums"
          placeholder="请输入车位数量:可停放进行充电的车位总数， 默认：0未知"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="经度" prop="stationLng">
        <el-input
          v-model="queryParams.stationLng"
          placeholder="请输入经度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="纬度" prop="stationLat">
        <el-input
          v-model="queryParams.stationLat"
          placeholder="请输入纬度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="建设场所1:居民区；2:公共机构；3:企事业单位；4:写字楼；5:工业园区；6:交通枢纽；7:大型文体设施；8:城市绿地；9:大型建筑配建停车场； 10:路边停车位；11:城际高速服务区；255:其他" prop="construction">
        <el-input
          v-model="queryParams.construction"
          placeholder="请输入建设场所1:居民区；2:公共机构；3:企事业单位；4:写字楼；5:工业园区；6:交通枢纽；7:大型文体设施；8:城市绿地；9:大型建筑配建停车场； 10:路边停车位；11:城际高速服务区；255:其他"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="电站图片" prop="pictures">
        <el-input
          v-model="queryParams.pictures"
          placeholder="请输入电站图片"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['interconnected:station:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['interconnected:station:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="内容" align="center" prop="content" />
      <el-table-column label="充电站乡镇编码" align="center" prop="townCode" />
      <el-table-column label="运营商编号" align="center" prop="operationId" />
      <el-table-column label="站点ID" align="center" prop="stationId" />
      <el-table-column label="站点名称" align="center" prop="stationName" />
      <el-table-column label="设备所属放ID" align="center" prop="equipmentOwnerId" />
      <el-table-column label="充电站国家代码" align="center" prop="countryCode" />
      <el-table-column label="充电站省市辖区编号" align="center" prop="areaCode" />
      <el-table-column label="详细地址" align="center" prop="address" />
      <el-table-column label="服务电话" align="center" prop="serviceTel" />
      <el-table-column label="站点类型1:公共；50:个人；100:公交(专用);101:环卫(专用);102:物流(专用);103:出租车(专用);255:其他" align="center" prop="stationType" />
      <el-table-column label="站点状态:0:未知；1:建设中；5:关闭下线；6:维护中；50:正常使用" align="center" prop="stationStatus" />
      <el-table-column label="车位数量:可停放进行充电的车位总数， 默认：0未知" align="center" prop="parkNums" />
      <el-table-column label="经度" align="center" prop="stationLng" />
      <el-table-column label="纬度" align="center" prop="stationLat" />
      <el-table-column label="建设场所1:居民区；2:公共机构；3:企事业单位；4:写字楼；5:工业园区；6:交通枢纽；7:大型文体设施；8:城市绿地；9:大型建筑配建停车场； 10:路边停车位；11:城际高速服务区；255:其他" align="center" prop="construction" />
      <el-table-column label="电站图片" align="center" prop="pictures" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['interconnected:station:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['interconnected:station:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <StationForm 
    ref="formRef" 
    @success="getList" 
    :stationTypeOptions="stationTypeOptions"
    :stationStatusOptions="stationStatusOptions"
  />
</template>

<script setup lang="ts">
import download from '@/utils/download'
import * as StationApi from '@/api/interconnected/station'
import StationForm from './StationForm.vue'
import { getIntDictOptions } from '@/utils/dict';

defineOptions({ name: 'Station' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  content: null,
  townCode: null,
  operationId: null,
  stationId: null,
  stationName: null,
  equipmentOwnerId: null,
  countryCode: null,
  areaCode: null,
  address: null,
  serviceTel: null,
  stationType: null,
  stationStatus: null,
  parkNums: null,
  stationLng: null,
  stationLat: null,
  construction: null,
  pictures: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await StationApi.getStationPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await StationApi.deleteStation(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await StationApi.exportStation(queryParams)
    download.excel(data, '互联互通电站.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const stationTypeOptions = getIntDictOptions(DICT_TYPE.STATION_TYPE);
const stationStatusOptions = getIntDictOptions(DICT_TYPE.INTERCONNECTION_STATION_STATUS);
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
