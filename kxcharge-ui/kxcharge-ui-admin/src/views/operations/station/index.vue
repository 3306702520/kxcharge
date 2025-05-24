<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="运营商" prop="orgId">

        <el-select
          v-model="queryParams.orgId"
          placeholder="请选择运营商"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in orgList"
            :key="dict.id"
            :label="dict.orgName"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="电站名称" prop="stationName">
        <el-input
          v-model="queryParams.stationName"
          placeholder="请输入电站名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="电站状态" prop="stationStatus">
        <el-select
          v-model="queryParams.stationStatus"
          placeholder="请选择电站状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.STASTION_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="场地属性" prop="property">
        <el-select
          v-model="queryParams.property"
          placeholder="请选择场地属性"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.STATION_PROPERTY)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm()"
          v-hasPermi="['operations:station:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['operations:station:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="运营商" align="center" prop="orgName" />
      <el-table-column label="电站名称" align="center" prop="stationName" />
      <el-table-column label="省市区" align="center" prop="areaCodeName" />
      <el-table-column label="地址" align="center" prop="address" />
      <el-table-column label="电站类型" align="center" prop="stationType" width="150">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.STATION_TYPE" :value="scope.row.stationType" />
        </template>
      </el-table-column>
      <el-table-column label="电站状态" align="center" prop="stationStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.STASTION_STATUS" :value="scope.row.stationStatus" />
        </template>
      </el-table-column>
      <el-table-column label="停车说明" align="center" prop="parkExplain" />
      <el-table-column label="开放时间" align="center" prop="openExplain" />
      <el-table-column label="电站全景" align="center" prop="imgMainEntrance" />
      <el-table-column label="状态" align="center" prop="isEnableStart">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.ENABLE_START" :value="scope.row.isEnableStart" />
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="Updater" />
      <el-table-column
        label="更新时间"
        align="center"
        prop="updateTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="场地属性" align="center" prop="property">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.STATION_PROPERTY" :value="scope.row.property" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm(scope.row.id)"
            v-hasPermi="['operations:station:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['operations:station:delete']"
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
  <!-- <StationForm ref="formRef" @success="getList" /> -->
</template>

<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as StationApi from '@/api/operations/station'
import gwEventBus from '@/utils/energy-eventbus';
import { useCache } from '@/hooks/web/useCache';
import * as OrgApi from '@/api/system/operator';
// import StationForm from './StationForm.vue'

defineOptions({ name: 'StationList' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const router = useRouter() // 路由
const route = useRoute()
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: null,
  stationName: null,
  stationStatus: null,
  property: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const { wsCache } = useCache()

const orgList = ref([])

// 查询运营商列表
const getOrg = async () => {
  const data = await OrgApi.getOperatorAll();
  orgList.value = data
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await StationApi.getStationPage(queryParams)
    const userData = JSON.parse(wsCache.get('USER_DATA'))
    list.value = data.list.map((item: any) => {
      const findUser = userData?.find(k => k.id == item.updater)
      const orgItme = orgList.value?.find(k => k.id == item.orgId)
      // @ts-ignore
      item['Updater'] = findUser?.nickname ?? ''
      // @ts-ignore
      item['orgName'] = orgItme?.orgName ?? ''
      return item
    })
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
// const formRef = ref()
const openForm = (id?: number) => {
  router.push({
    name: 'StationCreate',
    query: {
      id,
      type: id ? 'update' : 'create'
    }
  })
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
    download.excel(data, '站点信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const initData = async () => {
  await getOrg()
  getList()
  gwEventBus.on("toStationList", () => {
    getList()
  })
}

/** 初始化 **/
onMounted(() => {
  initData()
})
</script>
