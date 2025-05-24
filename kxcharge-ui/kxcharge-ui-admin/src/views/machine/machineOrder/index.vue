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
      <el-form-item label="用户昵称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="用户昵称"
          clearable
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="userAccount">
        <el-input
          v-model="queryParams.userAccount"
          placeholder="手机号"
          clearable
          class="!w-260px"
        />
      </el-form-item>
      <el-form-item label="自动售货机名称" prop="machineName" label-width="120px">
        <el-input
          v-model="queryParams.vendingMachineName"
          placeholder="自动售货机名称"
          clearable
          class="!w-260px"
        />
      </el-form-item> 
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="商品名称"
          clearable
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="支付方式" prop="payType">
        <el-select v-model="queryParams.payType" placeholder="支付方式" clearable class="!w-240px">
          <el-option v-for="dict in getIntDictOptions(DICT_TYPE.VENDING_ORDER_PAY_TYPE)" :key="(dict as any).value" :label="(dict as any).label" :value="(dict as any).value" />
        </el-select>
      </el-form-item>
      <el-form-item label="支付时间" prop="payTime">
        <el-date-picker
          v-model="queryParams.payTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="tableData" style="width: 100%">
      <el-table-column label="序号" type="index" width="80" />
      <el-table-column label="用户称昵" align="center" prop="userName" />
      <el-table-column label="用户手机号" align="center" prop="userAccount" />
      <el-table-column label="自动售货机名称" align="center" prop="vendingMachineName" />
      <el-table-column label="商品名称" align="center" prop="productName" />
      <el-table-column label="数量" align="center" prop="productCount" />
      <el-table-column label="支付方式" align="center">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.VENDING_ORDER_PAY_TYPE" :value="scope.row.payType" />
        </template>
      </el-table-column>
      <el-table-column label="积分/价格" align="center">
        <template #default="scope">
          {{ scope.row.payType === 1 ? scope.row.payIntegral : (scope.row.payPrice / 100) }}
        </template>
      </el-table-column>
      <el-table-column label="支付时间" align="center" prop="payTime" :formatter="dateFormatter" />
      <el-table-column label="订单状态" align="center">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.VENDING_ORDER_STATUS" :value="scope.row.orderStatus" />
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import * as MachineOrderApi from '@/api/machine/machineOrder/index';
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime';


const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  userName: null,
  userAccount: null,
  vendingMachineName: null,
  productName: null,
  payType: null,
  payTime: null
})
const loading = ref(false)
const tableData = ref([])
const total = ref(0)

/*
 * 获取列表
 */
const getList = async () => {
  loading.value = true
  try {
   const data = await MachineOrderApi.getMachineOrderPage(queryParams)
   tableData.value = data.list
   total.value = data.total
   console.log(`output->data`,data)
  } finally {
    loading.value = false
  }
}

/**
 * 搜索
 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

const queryFormRef = ref() // 搜索的表单
/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

onMounted(() => {
  getList()
})
</script>

<style scoped>

</style>