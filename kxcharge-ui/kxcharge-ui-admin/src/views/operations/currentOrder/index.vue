<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-tabs v-model="activeName" class="demo-tabs" @tab-change="isShow = false">
      <el-tab-pane label="充电订单" name="order">
        <el-form
          class="-mb-15px"
          :model="queryParams"
          ref="queryFormRef"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="手机号" prop="userPhone">
            <el-input v-model="queryParams.userPhone" placeholder="请输入手机号" class="!w-300px" />
          </el-form-item>
          <el-form-item label="充电单号" prop="orderNo">
            <el-input v-model="queryParams.orderNo" placeholder="请输入充电单号" class="!w-300px" />
          </el-form-item>
          <el-form-item label="终端编码" prop="equipmentConnectorCode">
            <el-input v-model="queryParams.equipmentConnectorCode" placeholder="请输入终端编码" class="!w-300px" />
          </el-form-item>
          <el-form-item label="用户账户" prop="userId">
            <el-input v-model="queryParams.userId" placeholder="请输入用户账户" class="!w-300px" />
          </el-form-item>
          <el-form-item label="开始充电" v-if="isShow" prop="startChargeTime">
            <el-date-picker
              v-model="queryParams.startChargeTime"
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
          <el-form-item label="订单来源" v-if="isShow" prop="chargingSource">
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
            <el-input v-model="queryParams.stationName" placeholder="请输入电站名称" class="!w-300px" />
          </el-form-item>
          <el-form-item label="归属运营商" v-if="isShow" prop="orgId">
            <el-select v-model="queryParams.orgId" placeholder="归属运营商" class="!w-300px">
              <el-option
                v-for="item in companyList"
                :key="(item as any).value"
                :label="(item as any).label"
                :value="(item as any).value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="充电车辆" v-if="isShow" prop="carNumber">
            <el-input v-model="queryParams.carNumber" placeholder="请输入充电车辆" class="!w-300px" />
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <!-- <el-tab-pane label="实时占用记录" name="orderRecords">
        <el-form
          class="-mb-15px"
          :model="queryOtherParams"
          ref="queryOtherFormRef"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="手机号" prop="userPhone">
            <el-input v-model="queryOtherParams.userPhone" placeholder="请输入手机号" class="!w-300px" />
          </el-form-item>
          <el-form-item label="占用终端编码" label-width="100px" prop="equipmentConnectorCode">
            <el-input v-model="queryOtherParams.equipmentConnectorCode" placeholder="请输入占用终端编码" class="!w-300px" />
          </el-form-item>
          <el-form-item label="占用时间" prop="startChargeTime">
            <el-date-picker
              v-model="queryOtherParams.startChargeTime"
              value-format="YYYY-MM-DD HH:mm:ss"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
              class="!w-280px"
            />
          </el-form-item>
          <el-form-item label="用户账户" v-if="isShow" prop="userId">
            <el-input v-model="queryOtherParams.userId" placeholder="请输入用户账户" class="!w-300px" />
          </el-form-item>
          <el-form-item label="归属企业" v-if="isShow" prop="companyId">
            <el-select v-model="queryOtherParams.companyId" placeholder="归属企业" class="!w-300px">
              <el-option
                v-for="item in companyList"
                :key="(item as any).value"
                :label="(item as any).label"
                :value="(item as any).value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="电站名称" v-if="isShow" label-width="100px" prop="stationName">
            <el-input v-model="queryOtherParams.stationName" placeholder="请输入电站名称" class="!w-300px" />
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
    <Table
      :columns="allSchemas.tableColumns"
      :data="tableObject.tableList"
      :loading="tableObject.loading"
      v-model:pageSize="tableObject.pageSize"
      v-model:currentPage="tableObject.currentPage"
      v-if="activeName === 'order'"
    >
      <template #action="{ row }">
        <el-button
          link
          type="primary"
          @click="viewOrderInfo(row.seqNumber)"
          v-hasPermi="['operations:currentOrder:info']"
        >
          详情
        </el-button>
      </template>
    </Table>
    <!-- <Table
      :columns="all2Schemas.tableColumns"
      :data="tableObject.tableList"
      :loading="tableObject.loading"
      v-model:pageSize="tableObject.pageSize"
      v-model:currentPage="tableObject.currentPage"
      v-else
    >
      <template #action="{ row }">
        <el-button
          link
          type="primary"
          @click="viewOrderInfo(row.orderNo)"
          v-hasPermi="['operations:currentOrder:info']"
        >
          详情
        </el-button>
      </template>
    </Table> -->
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <!-- <OrderForm ref="formRef" @success="getList" /> -->
</template>

<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { allSchemas, all2Schemas } from './current-order.data'
import * as OrderApi from '@/api/operations/order'
// import OrderForm from './OrderForm.vue'
import * as CompanyApi from "@/api/member/company/index";
import * as OperatorApi from '@/api/system/operator/index'
defineOptions({ name: 'Order' })
// const message = useMessage() // 消息弹窗
// const { t } = useI18n() // 国际化
const activeName = ref('order')

const companyList = reactive([])
const orgList = reactive([])
const isShow = ref(false)
const queryParams = reactive({
  userPhone: null,
  orderNo: null,
  equipmentConnectorCode: null,
  startChargeTime: [],
  chargingWay: null,
  companyId: null,
  stationName: null,
  orgId: null,
  chargingSource: null,
  carNumber: null,
  userId: null
})

const queryOtherParams = reactive({
  userPhone: null,
  startChargeTime: [],
  equipmentConnectorCode: null,
  companyId: null,
  stationName: null,
  userId: null

})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const tableObject = reactive({
  tableList: [],
  loading: false,
  total: 0,
  pageSize: 10,
  currentPage: 1
})
/** 查询列表 */
const getList = async () => {
  tableObject.loading = true
  const params = activeName.value === 'order' ? queryParams : queryOtherParams
  try {
    if (activeName.value === 'order') {
      const data = await OrderApi.getRealtimeOrderList(params)
      // @ts-ignore
      tableObject.tableList = data.map(item => {
        item['orgName'] = orgList.find(k => k.value === item.orgId)?.label ?? ''
        return item;
      })
      tableObject.total = data.length
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
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

const router = useRouter()
const viewOrderInfo = (id: string) => {
  router.push({
    name: 'CurrentOrderDetail',
    query: {
      id
    }
  })
}

/** 导出 */
const handleExport = () => {

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
  getList()
}

/** 初始化 **/
onMounted(() => {
  getOrgList()
})
</script>

<style lang="scss" setup>
  .btn {
    margin: 10px 15px 0;
  }
</style>
