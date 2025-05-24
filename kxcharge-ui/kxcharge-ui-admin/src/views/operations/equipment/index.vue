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
      <el-form-item label="电桩名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入电桩名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="归属电站" prop="stationId">
        <el-select v-model="queryParams.stationId" placeholder="归属电站">
          <el-option v-for="item in stationList" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="电桩编码" prop="equipmentCode">
        <el-input
          v-model="queryParams.equipmentCode"
          placeholder="请输入电桩编码"
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
          @click="openForm()"
          v-hasPermi="['operations:equipment:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-card class="box-card" v-for="item in list" :key="(item as any).id">
      <template #header>
        <div class="card-header">
          <div class="left">
            <span style="margin-right: 10px;">电桩编码：{{ (item as any).equipmentCode }}</span>
            <span style="margin-right: 10px;">功率：{{ (item as any).equipmentModelRespVO?.power || '' }}</span>
            <span style="margin-right: 10px;">
              在线状态:
              <dict-tag :type="DICT_TYPE.IS_ONLINE" :value="(item as any).isOnline" style="margin-left: 5px;" />
            </span>
            <span style="margin-right: 10px;">电桩名称：{{ (item as any).equipmentName }}</span>
          </div>
          <div class="right">
            <el-button
              link
              type="primary"
              @click="openForm((item as any).id)"
              v-hasPermi="['operations:equipment:update']"
            >
              编辑
            </el-button>
            <el-button
              link
              type="primary"
              @click="remoteOperation((item as any).id, 'restart')"
              v-hasPermi="['operations:equipment:restart']"
            >
              远程重启
            </el-button>
            <el-button
              link
              type="primary"
              @click="remoteOperation((item as any).id, 'upgrade')"
              v-hasPermi="['operations:equipment:upgradation']"
            >
              远程升级
            </el-button>
            <el-button
              link
              type="primary"
              @click="proof((item as any).id, 'time')"
              v-hasPermi="['operations:equipment:proofTime']"
              style="color: #04d919;"
            >
              校时
            </el-button>
            <el-button
              link
              type="primary"
              @click="proof((item as any).id, 'price')"
              v-hasPermi="['operations:equipment:proofPrice']"
              style="color: #04d919;"
            >
              校价
            </el-button>
            <el-button
              link
              type="danger"
              @click="handleDelete((item as any).id)"
              v-hasPermi="['operations:equipment:delete']"
            >
              删除
            </el-button>
          </div>
        </div>
      </template>
      <div class="content">
        <div class="left">
          <span class="item">归属电站：{{ (item as any).stationName }}</span>
          <span class="item">品牌型号：{{ (item as any).equipmentBrandRespVO?.brandName || '' }}/{{ (item as any).equipmentModelRespVO?.equipmentModel || '' }}</span>
          <span class="item">固件版本：{{ (item as any).firmwareVersion }}</span>
          <span class="item">最新校价时间：{{ formatDate((item as any).setPriceTime, 'YYYY-MM-DD HH:mm:ss') }}</span>
        </div>
        <div class="right">
          <el-table v-loading="loading" :data="(item as any).equipmentConnectorRespVOS" :stripe="true" :show-overflow-tooltip="true">
            <el-table-column type="index" label="序号" width="60px" />
            <el-table-column label="充电枪名称" align="center" prop="connectorName" />
            <el-table-column label="终端编码" align="center" prop="connectorCode" />
            <el-table-column label="启停状态" align="center" prop="isEnableStart">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.ENABLE_START" :value="scope.row.isEnableStart" />
              </template>
            </el-table-column>
            <el-table-column label="工作状态" align="center" prop="workStatus">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.WORK_STATUS" :value="scope.row.workStatus" />
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template #default="scope">
                <el-button
                  link
                  type="primary"
                  @click="changeDevStatus(scope.row.id)"
                >
                  {{ scope.row.isEnableStart ? '停用' : '启用' }}
                </el-button>
                <el-button
                  link
                  type="primary"
                  @click="viewDevInfo(scope.row.id)"
                >
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </el-card>
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <!-- <EquipmentForm ref="formRef" @success="getList" /> -->
</template>

<script setup lang="ts">
import download from '@/utils/download'
import * as EquipmentApi from '@/api/operations/equipment'
import * as StationApi from '@/api/operations/station'
import * as EquipmentConnectorApi from '@/api/operations/equipment-connector'
import gwEventBus from '@/utils/energy-eventbus';
// import EquipmentForm from './EquipmentForm.vue'
import { DICT_TYPE } from '@/utils/dict'
import { formatDate } from '@/utils/formatTime';

defineOptions({ name: 'Equipment' })


const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const router = useRouter()
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equipmentName: null,
  equipmentCode: null,
  stationId: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const stationList = reactive([])
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await EquipmentApi.getEquipmentPage(queryParams)
    list.value = data.list
    list.value = list.value.map(item => {
      const find = stationList.find(k => k.value === item.stationId)
      item['stationName'] = find ? find.label : ''
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
const openForm = (id?: number) => {
  // formRef.value.open(id)
  router.push({
    name: 'EquipmentCreate',
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
    await EquipmentApi.deleteEquipment(id)
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
    const data = await EquipmentApi.exportEquipment(queryParams)
    download.excel(data, '电桩管理.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

// 远程重启或升级
const remoteOperation = async (id: string, type: 'restart' | 'upgrade') => {
  try {
    await EquipmentApi.remote(id, type)
    message.success('操作成功!')
    await getList()
  } catch (error: any) {
    message.error(error)
  }
}

// 校价时
const proof = async (id: string, type: 'time' | 'price') => {
  try {
    await EquipmentApi.proof(id, type)
    message.success('操作成功!')
    await getList()
  } catch (error: any) {
    message.error(error)
  }
}

// 查看枪详情
const viewDevInfo = (id: string) => {
  router.push({
    name: 'GunInfo',
    query: {
      id
    }
  })
}

// 启停
const changeDevStatus = async (id: string) => {
  try {
    await EquipmentConnectorApi.equipmentConnectorIsStop(id)
    message.success('操作成功!')
    await getList()
  } catch (error: any) {
    message.error(error)
  }
}

const getStationAll = async () => {
  const data = await StationApi.getStationAll()
  data.forEach(element => {
    // @ts-ignore
    stationList.push({
      value: element.id,
      label: element.stationName
    })
  });
}

/** 初始化 **/
onMounted(() => {
  getStationAll()
  getList()
  gwEventBus.on("toEquipmentList", () => {
    getList()
  })
})
</script>

<style scoped lang="scss">

.box-card {
  border-radius: 12px;
  margin-bottom: 20px;
  ::v-deep .el-card__header {
    background-color: #def4fd;
    padding: 10px;
  }
  ::v-deep .el-card__body {
    padding: 10px 20px;
  }
  .card-header {
    display: flex;
    justify-content: space-between;
    .left {
      display: flex;
      justify-content: space-between;
    }
  }
  .content {
    display: flex;
    justify-content: space-between;
    .left {
      width: 35%;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .item {
        margin-bottom: 10px;
      }
    }
    .right {
      width: 60%;
    }
  }
}
</style>
