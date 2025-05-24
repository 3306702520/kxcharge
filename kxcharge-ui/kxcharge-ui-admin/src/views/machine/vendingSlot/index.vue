<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="售货机名称" prop="machineName">
        <el-input
          v-model="queryParams.machineName"
          placeholder="请输入自动售货机名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="货道号" prop="cargoLangeNo">
        <el-input
          v-model="queryParams.cargoLangeNo"
          placeholder="请输入货道号"
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
          v-hasPermi="['sell:vending-slot:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['sell:vending-slot:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="商品名称" align="center" prop="productName" />
      <el-table-column label="自动售货机名称" align="center" prop="machineName" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="层数(从上向下)" align="center" prop="floorNumber" />
      <el-table-column label="列数(从左向右)" align="center" prop="columnNumber" />
      <el-table-column label="货道号" align="center" prop="cargoLangeNo" />
      <el-table-column label="商品最大数量" align="center" prop="productMaxNumber" />
      <el-table-column label="商品数量" align="center" prop="productNumber" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="warehousing(scope.row)"
            v-hasPermi="['sell:vending-slot:warehousing']"
          >
            入库
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['sell:vending-slot:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['sell:vending-slot:delete']"
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
  <VendingSlotForm ref="formRef" @success="getList" />

  <Dialog title="入库" v-model="dialogVisible" width="500">
    <el-form
      ref="warehousingFormRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
      <el-form-item label="数量" prop="number">
        <el-input-number v-model="formData.number" :min="0" />
      </el-form-item>
      <div class="btn" style="display: flex; justify-content: center; align-items: center;">
        <el-form-item :label-width="'0px'">
          <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </el-form-item>

      </div>
    </el-form>
  </Dialog>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as VendingSlotApi from '@/api/machine/vendingSlot/index'
import VendingSlotForm from './VendingSlotForm.vue'

defineOptions({ name: 'VendingSlot' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const dialogVisible = ref(false)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  productName: null,
  machineName: null,
  createTime: [],
  cargoLangeNo: null,
  columnNumber: null,
  productMaxNumber: null,
  productNumber: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref({
  vendingSlotId: '',
  number: '',
})
const formRules = ref({
  number: [{ required: true, message: '请输入数量', trigger: 'blur' }],
})

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await VendingSlotApi.getVendingSlotPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const warehousing = async (row) => {
  console.log(`output->row`,row)
  dialogVisible.value = true
  formData.value.vendingSlotId = row.id
  formData.value.number = row.productNumber
}

const warehousingFormRef = ref() // 表单 Ref
const submitForm = async () => {
  // 校验表单
  if (!warehousingFormRef) return
  const valid = await warehousingFormRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    await VendingSlotApi.putVendingSlot(data)
    message.success('入库成功!')
    dialogVisible.value = false
    formData.value.number = ''
    // 发送操作成功的事件
    getList()
  } finally{
    formLoading.value = false
    
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
    await VendingSlotApi.deleteVendingSlot(id)
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
    const data = await VendingSlotApi.exportVendingSlot(queryParams)
    download.excel(data, '自动售货机货道.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
