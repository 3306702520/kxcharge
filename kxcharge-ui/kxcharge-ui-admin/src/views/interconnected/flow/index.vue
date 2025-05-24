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
      <el-form-item label="方案名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入方案名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="运营商" prop="orgId">
        <el-select clearable v-model="queryParams.orgId" placeholder="请选择运营商" class="!w-240px">
          <el-option
            v-for="item in operatorOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>

      <!-- <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
          v-model="queryParams.startTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker
          v-model="queryParams.endTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item> -->
      <!-- <el-form-item label="电费折扣" prop="chargeDiscount">
        <el-input
          v-model="queryParams.chargeDiscount"
          placeholder="请输入电费折扣"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="服务折扣" prop="serviceDiscount">
        <el-input
          v-model="queryParams.serviceDiscount"
          placeholder="请输入服务折扣"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="站点IDs" prop="stationIds">
        <el-input
          v-model="queryParams.stationIds"
          placeholder="请输入站点IDs"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item label="结算价格类型" :label-width="'130px'" prop="settlePriceType">
        <el-select
          v-model="queryParams.settlePriceType"
          placeholder="请选择结算价格类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="(dict, index) in priceTypeOptions"
            :key="index"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="站点推送价格类型" :label-width="'130px'" prop="stationPriceType">
        <el-select
          v-model="queryParams.stationPriceType"
          placeholder="请选择站点推送价格类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="(dict, index) in priceTypeOptions"
            :key="index"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item> -->
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="(dict, index) in statusOptions"
            :key="index"
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
          @click="openForm('create')"
          v-hasPermi="['interconnected:flow:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['interconnected:flow:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="运营商" align="center" prop="orgId">
        <template #default="scope">
          {{ getObjLabel(operatorOptions, scope.row.orgId) }}
        </template>
      </el-table-column>
      <el-table-column label="方案名称" align="center" prop="name" />
      <el-table-column label="开始时间" :formatter="dateFormatter2" align="center" prop="startTime" />
      <el-table-column label="结束时间" :formatter="dateFormatter2" align="center" prop="endTime" />
      <el-table-column label="电费折扣(%)" align="center" prop="chargeDiscount" />
      <el-table-column label="服务折扣(%)" align="center" prop="serviceDiscount" />
      <el-table-column label="站点IDs" align="center" prop="stationIds">
        <template #default="scope">
          <div class="m-b-3px" v-for="item, key of scope.row.stationIds" :key="key">
            <el-tag>
              {{ getObjLabel(idsOptions, item) }}
            </el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="结算价格类型" a clign="center" prop="settlePriceType">
        <template #default="scope">
          {{ getDictLabel(DICT_TYPE.PUSH_PRICE, scope.row.settlePriceType) }}
        </template>
      </el-table-column>
      <el-table-column label="站点推送价格类型" align="center" prop="stationPriceType">
        <template #default="scope">
          {{ getDictLabel(DICT_TYPE.PUSH_PRICE, scope.row.stationPriceType) }}
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success': 'warning'">{{ getDictLabel(DICT_TYPE.INTERCONNECTED_STATUS, scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['interconnected:flow:update']"
          >
            编辑
          </el-button>
          <el-popconfirm
            title="是否确认操作?"
            @confirm="handleStatus(scope.row)"
            confirm-button-text="是"
            cancel-button-text="否"
          >
            <template #reference>
              <el-button
                link
                :type="scope.row.status === 1 ? 'danger' : 'success'"
              >
                {{ scope.row.status === 1 ? '停止' : '启用' }}
              </el-button>
            </template>
          </el-popconfirm>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['interconnected:flow:delete']"
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
  <FlowForm
    ref="formRef"
    @success="getList"
    :operatorOptions="operatorOptions"
    :priceTypeOptions="priceTypeOptions"
    :statusOptions="statusOptions"
  />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as FlowApi from '@/api/interconnected/flow'
import FlowForm from './FlowForm.vue'
import { DICT_TYPE, getIntDictOptions, getDictLabel, getObjLabel } from '@/utils/dict'
import { getOperatorAll } from '@/api/system/operator'
import { getOperatorStation, getStationAll } from '@/api/operations/station'

defineOptions({ name: 'Flow' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgId: null,
  name: null,
  startTime: [],
  endTime: [],
  chargeDiscount: null,
  serviceDiscount: null,
  stationIds: null,
  settlePriceType: null,
  stationPriceType: null,
  createTime: [],
  status: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await FlowApi.getFlowPage(queryParams)
    console.log("%c Line:275 🍑 data", "color:#ffdd4d", data);
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
    await FlowApi.deleteFlow(id)
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
    const data = await FlowApi.exportFlow(queryParams)
    download.excel(data, '流量方案.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

// 获取站点ids
const idsOptions = reactive([]);
async function getStationIds(){
  try {
    const data = await getStationAll()
    data.forEach((item) => {
      // @ts-ignore
      idsOptions.push({
        value: item.id,
        label: item.stationName
      })
    })

    console.log("%c Line:360 🍫", "color:#4fff4B", idsOptions);
  } catch (error) {

  }

}

const operatorOptions = reactive([]) // 运营商选择器
// 获取归属运营商列表
async function getOperator(){
  try {
    const data = await getOperatorAll()
    // console.log(`output->data`,data)
    data.forEach((item) => {
      // @ts-ignore
      operatorOptions.push({
        value: item.id + "",
        label: item.orgName
      })
    })
  }catch(error){

  }
}

const priceTypeOptions = getIntDictOptions(DICT_TYPE.PUSH_PRICE);
const statusOptions = getIntDictOptions(DICT_TYPE.INTERCONNECTED_STATUS);

// 启动停用数据
async function handleStatus(scope){
  const is = scope.status === 1

  scope.status = is ? 2 : 1
  try {
    await FlowApi.createFlow(scope);
    message.success(t(`common.${is ? 'freezeSuccess' : 'enableSuccess'}`))
  } catch (error) {

  }
}
/** 初始化 **/
onMounted(() => {
  getList();
  getOperator();
  getStationIds();
})
</script>
