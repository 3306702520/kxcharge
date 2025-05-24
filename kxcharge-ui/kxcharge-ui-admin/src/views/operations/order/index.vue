<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-tabs v-model="activeName" class="demo-tabs" @tab-change="tabChange">
      <el-tab-pane label="充电订单" name="order">
        <el-form
          class="-mb-15px"
          :model="queryParams"
          ref="queryFormRef"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="手机号" prop="mobile">
            <el-input v-model="queryParams.mobile" placeholder="请输入手机号" class="!w-300px" />
          </el-form-item>
          <el-form-item label="充电单号" prop="seqNumber">
            <el-input v-model="queryParams.seqNumber" placeholder="请输入充电单号" class="!w-300px" />
          </el-form-item>
          <el-form-item label="终端编码" prop="gunId">
            <el-input v-model="queryParams.gunId" placeholder="请输入终端编码" class="!w-300px" />
          </el-form-item>
          <el-form-item label="开始充电" prop="startTime" v-if="isShow" label-width="90px">
            <el-date-picker
              v-model="queryParams.startTime"
              value-format="YYYY-MM-DD HH:mm:ss"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
              class="!w-280px"
            />
          </el-form-item>
          <el-form-item label="结束充电" v-if="isShow" prop="endTime">
            <el-date-picker
              v-model="queryParams.endTime"
              value-format="YYYY-MM-DD HH:mm:ss"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
              class="!w-280px"
            />
          </el-form-item>
          <el-form-item label="用户账户" prop="userAccount" v-if="isShow">
            <el-input v-model="queryParams.userAccount" placeholder="请输入用户账户" class="!w-300px" />
          </el-form-item>

          <el-form-item label="归属企业" v-if="isShow" prop="companyId">
            <el-select v-model="queryParams.companyId" placeholder="归属企业" class="!w-300px">
              <el-option
                v-for="item in companyList"
                :key="(item as any).value"
                :label="(item as any).label"
                :value="(item as any).value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="订单来源" label-width="90px" v-if="isShow" prop="chargingSource">
            <el-select
              v-model="queryParams.chargingSource"
              placeholder="请选择订单来源"
              clearable
              class="!w-300px"
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.CHARGING_WAY)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="电站名称" v-if="isShow" prop="stationName">
            <el-input
              v-model="queryParams.stationName"
              placeholder="请输入电站名称"
              class="!w-300px"
            />
          </el-form-item>
          <el-form-item label="订单结算" v-if="isShow" prop="payTime">
            <el-date-picker
              v-model="queryParams.payTime"
              value-format="YYYY-MM-DD HH:mm:ss"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
              class="!w-280px"
            />
          </el-form-item>
          <el-form-item label="充电方式" v-if="isShow" prop="chargingWay">
            <el-select
              v-model="queryParams.chargingWay"
              placeholder="请选择充电方式"
              clearable
              class="!w-300px"
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.CHARGING_SOURCE)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="归属运营商" v-if="isShow" label-width="90px" prop="orgId">
            <el-select v-model="queryParams.orgId" placeholder="归属运营商" class="!w-300px">
              <el-option
                v-for="item in companyList"
                :key="(item as any).value"
                :label="(item as any).label"
                :value="(item as any).value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="充电车辆" v-if="isShow" prop="plateNumber">
            <el-input
              v-model="queryParams.plateNumber"
              placeholder="请输入充电车辆"
              class="!w-300px"
            />
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <!-- <el-tab-pane label="占用订单" name="occupyOrder">
        <el-form
          class="-mb-15px"
          :model="queryOtherParams"
          ref="queryOtherFormRef"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="手机号" prop="phone" label-width="100px">
            <el-input
              v-model="queryOtherParams.phone"
              placeholder="请输入手机号"
              class="!w-250px"
            />
          </el-form-item>
          <el-form-item label="占用订单号" prop="seqNumber" label-width="90px">
            <el-input
              v-model="queryOtherParams.seqNumber"
              placeholder="请输入占用终端编码"
              class="!w-250px"
            />
          </el-form-item>
          <el-form-item label="占用终端编码" label-width="100px" prop="gunId">
            <el-input
              v-model="queryOtherParams.gunId"
              placeholder="请输入占用终端编码"
              class="!w-250px"
            />
          </el-form-item>
          <el-form-item label="订单开始时间" prop="startTime" v-if="isShow" label-width="100px">
            <el-date-picker
              v-model="queryOtherParams.startTime"
              value-format="YYYY-MM-DD HH:mm:ss"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
              class="!w-280px"
            />
          </el-form-item>
          <el-form-item label="用户账户" v-if="isShow" prop="userAccount" label-width="100px">
            <el-input
              v-model="queryOtherParams.userAccount"
              placeholder="请输入用户账户"
              class="!w-250px"
            />
          </el-form-item>
          <el-form-item label="归属企业" v-if="isShow" prop="companyId" label-width="90px">
            <el-select v-model="queryOtherParams.companyId" placeholder="归属企业" class="!w-250px">
              <el-option
                v-for="item in companyList"
                :key="(item as any).value"
                :label="(item as any).label"
                :value="(item as any).value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="订单来源" v-if="isShow" prop="chargingSource" label-width="100px">
            <el-select
              v-model="queryOtherParams.chargingSource"
              placeholder="请选择订单来源"
              clearable
              class="!w-250px"
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.CHARGING_WAY)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="电站名称" v-if="isShow" label-width="100px" prop="stationName">
            <el-input
              v-model="queryOtherParams.stationName"
              placeholder="请输入电站名称"
              class="!w-250px"
            />
          </el-form-item>
          <el-form-item label="订单结束原因" v-if="isShow" label-width="100px" prop="endReason">
            <el-input
              v-model="queryOtherParams.endReason"
              placeholder="请输入电站名称"
              class="!w-250px"
            />
          </el-form-item>
        </el-form>
      </el-tab-pane> -->
    </el-tabs>
    <div class="btn">
      <el-button @click="isShow = !isShow">{{ isShow ? '隐藏' : '展开' }} </el-button>
      <el-button @click="handleQuery" type="primary"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
      <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      <el-button
        type="primary"
        plain
        @click="openForm('create')"
        v-hasPermi="['operations:order:create']"
      >
        <Icon icon="ep:plus" class="mr-5px" /> 新增
      </el-button>
      <el-button
        type="success"
        plain
        @click="handleExport"
        :loading="exportLoading"
        v-hasPermi="['operations:order:export']"
      >
        <Icon icon="ep:download" class="mr-5px" /> 导出
      </el-button>
    </div>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-if="activeName === 'order'" :data="tableObject.tableList" style="width: 100%" v-loading="tableObject.loading" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column prop="seqNumber" label="充电单号" width="150" />
      <el-table-column prop="userAccount" label="用户账号" width="150" />
      <el-table-column prop="mobile" label="用户手机号" width="150" />
      <el-table-column prop="companyName" label="归属企业" width="150" />
      <el-table-column prop="plateNumber" label="充电车辆" width="150" />
      <el-table-column prop="plateVINCode" label="充电VIN码" width="150" />
      <el-table-column label="充电方式" align="center" prop="chargingWay" width="150">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.CHARGING_WAY" :value="scope.row.chargingWay" />
        </template>
      </el-table-column>
      <el-table-column prop="allAmount" label="订单金额" width="150" />
      <el-table-column prop="inPay" label="实收金额" width="150" />
      <el-table-column
        label="开始充电时间"
        align="center"
        prop="startTime"
        :formatter="dateFormatter"
        width="150"
      />
      <el-table-column
        label="结束充电时间"
        align="center"
        prop="endTime"
        :formatter="dateFormatter"
        width="150"
      />
      <el-table-column
        label="订单支付时间"
        align="center"
        prop="payTime"
        :formatter="dateFormatter"
        width="150"
      />
      <el-table-column prop="chargedTime" label="充电时长" width="150" />
      <el-table-column prop="chargedPower" label="已充电量(度)" width="150" />
      <el-table-column prop="startSoc" label="起始SOC(%)" width="150" />
      <el-table-column prop="endSoc" label="结束SOC(%)" width="150" />
      <el-table-column prop="stationName" label="电站名称" width="150" />
      <el-table-column prop="gunId" label="终端编码" width="150" />
      <el-table-column prop="orgName" label="运营商" width="150" />
      <el-table-column label="订单来源" align="center" prop="chargingSource" width="150">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.CHARGING_SOURCE" :value="scope.row.chargingSource" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="120">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="viewOrderInfo(scope.row.seqNumber)"
            v-hasPermi="['operations:order:info']"
          >
            详情
          </el-button>
          <el-button
            link
            type="primary"
            @click="rePush(scope.row.id)"
            v-hasPermi="['operations:order:repush']"
            v-if="scope.row.chargingWay === 5"
          >
            重推
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- <el-table v-else :data="tableObject.tableList" style="width: 100%" v-loading="tableObject.loading" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column prop="code" label="占用订单号" width="150" />
      <el-table-column prop="userAccount" label="用户账号" width="150" />
      <el-table-column prop="isFree" label="是否实际扣费" width="150" />
      <el-table-column prop="companyName" label="归属企业" width="150" />
      <el-table-column prop="stationName" label="电站名称" width="150" />
      <el-table-column prop="devName" label="占用终端名称" width="150" />
      <el-table-column prop="devName" label="占用终端编码" width="150" />
      <el-table-column prop="devName" label="占用时长" width="150" />
      <el-table-column prop="devName" label="订单计费" width="150" />
      <el-table-column prop="time" label="占用收费" width="150" />
      <el-table-column label="订单来源" align="center" prop="chargingSource" width="150">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.CHARGING_WAY" :value="scope.row.chargingSource" />
        </template>
      </el-table-column>
      <el-table-column prop="time" label="订单结束原因" width="150" />
      <el-table-column prop="time" label="订单支付时间" width="150" />
      <el-table-column label="操作" align="center" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="viewOrderInfo(scope.row.seqNumber)"
            v-hasPermi="['operations:order:info']"
          >
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table> -->
    <Pagination
      :total="tableObject.total"
      v-model:page="pagetion.pageNo"
      v-model:limit="pagetion.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <!-- <OrderForm ref="formRef" @success="getList" /> -->
</template>

<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import * as OrderApi from '@/api/operations/order'
import * as CompanyApi from '@/api/member/company/index'
import * as OperatorApi from '@/api/system/operator/index'
import download from '@/utils/download'
defineOptions({ name: 'Order' })
const message = useMessage() // 消息弹窗
// const { t } = useI18n() // 国际化
const activeName = ref('order')
const companyList = reactive([])
const orgList = reactive([])
const isShow = ref(false)
const queryParams = reactive({
  mobile: null,
  seqNumber: null,
  gunId: null,
  startTime: [],
  endTime: [],
  chargingWay: null,
  companyId: null,
  stationName: null,
  orgId: null,
  chargingSource: null,
  plateNumber: null,
  userAccount: null,
  payTime: null
})
const pagetion = reactive({
  pageNo: 1,
  pageSize: 10,
})

const queryOtherParams = reactive({

  phone: null,
  startTime: [],
  gunId: null,
  companyId: null,
  stationName: null,
  userAccount: null,
  seqNumber: null,
  chargingSource: null,
  endReason: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const tableObject = reactive({
  tableList: [],
  loading: false,
  total: 0,
})
/** 查询列表 */
const getList = async () => {
  tableObject.loading = true
  const params = activeName.value === 'order' ? queryParams : queryOtherParams
  try {
    if (activeName.value === 'order') {
      const data = await OrderApi.getHisOrderPage({...params, ...pagetion})
      data.list = data.list.map(item => {
        // @ts-ignore
        item['orgName'] = orgList.find(k => k.value === item.orgId)?.label ?? ''
        return item
      })
      tableObject.tableList = data.list
      tableObject.total = data.total
    }else {
      tableObject.tableList = []
      tableObject.total = 0
    }
  } finally {
    tableObject.loading = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  pagetion.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

// /** 添加/修改操作 */
// const formRef = ref()
const openForm = (id?: number) => {
  // formRef.value.open(type, id)

}

const tabChange = () => {
  isShow.value = false
  pagetion.pageNo = 1;
  pagetion.pageSize = 10;
  getList()
}

// /** 删除按钮操作 */
// const handleDelete = async (id: number) => {
//   try {
//     // 删除的二次确认
//     await message.delConfirm()
//     // 发起删除
//     await OrderApi.deleteOrder(id)
//     message.success(t('common.delSuccess'))
//     // 刷新列表
//     await getList()
//   } catch {}
// }

// /** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await OrderApi.exportOrder(queryParams)
    download.excel(data, '订单管理.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const router = useRouter()
const viewOrderInfo = (seqNumber: string) => {
  console.log(`output->tableList`,tableObject.tableList)
  console.log('seqNumber', seqNumber);

  router.push({
    name: 'OrderDetail',
    query: {
      seqNumber
    }
  })
}


const getOrgList = async () => {
  const data = await OperatorApi.getOperatorAll()
  data.forEach((item) => {
    // @ts-ignore
    orgList.push({
      value: item.id,
      label: item.orgName
    })
  })
}

const rePush = async (id: string) => {
  console.log(`rePush->tableList`,tableObject.tableList)
  console.log('rePush', id);
  await OrderApi.orderRethrust(id);
  getList();
}

/** 初始化 **/
onMounted(() => {
  getOrgList()
  getList()
})
</script>

<style lang="scss" setup>
.btn {
  margin: 10px 15px 0;
}
</style>
