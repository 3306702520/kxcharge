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
      <el-form-item label="运营商" prop="orgName">
        <el-input
          v-model="queryParams.orgName"
          placeholder="请输入运营商名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="统一社会信用代码" prop="code" label-width="135px">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入统一社会信用代码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="联系人" prop="contactUser">
        <el-input
          v-model="queryParams.contactUser"
          placeholder="请输入联系人"
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
          v-hasPermi="['system:operator:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['system:operator:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- <el-table-column label="企业名称" align="center" prop="orgName" /> -->
      <el-table-column label="运营商名称" align="center" prop="orgName" />
      <el-table-column label="统一社会信用代码" align="center" prop="code" />
      <el-table-column label="联系人" align="center" prop="contactUser" />
      <el-table-column label="联系方式" align="center" prop="contactNum" />
      <el-table-column label="联系邮箱" align="center" prop="email" />
      <el-table-column label="状态" align="center" prop="isFrozen">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.OPERATOR_STATUS" :value="scope.row.isFrozen" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="changeStatus(scope.row.id, 0)"
            v-hasPermi="['system:operator:freeze']"
            v-if="scope.row.isFrozen"
          >
            冻结
          </el-button>
          <el-button
            link
            type="primary"
            @click="changeStatus(scope.row.id, 1)"
            v-hasPermi="['system:operator:enable']"
            v-else
          >
            启用
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['system:operator:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['system:operator:delete']"
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
  <OperatorForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import download from '@/utils/download'
import * as OperatorApi from '@/api/system/operator'
import OperatorForm from './OperatorForm.vue'
import { DICT_TYPE } from '@/utils/dict'

/** 运营商 列表 */
defineOptions({ name: 'Operator' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orgName: null,
  code: null,
  contactUser: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await OperatorApi.getOperatorPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

// 启用与冻结
const changeStatus = async (id, isFrozen) => {

  try {
    // 二次确认
    await message.confirm(`是否确认${isFrozen ? '启用' : '冻结'}?`)
    const parmas = {
      id,
      isFrozen
    }
    await OperatorApi.changeOperatorStatus(parmas)
    message.success('操作成功!')
    // 刷新列表
    await getList()
  } catch (error) {}


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
    await OperatorApi.deleteOperator(id)
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
    const data = await OperatorApi.exportOperator(queryParams)
    download.excel(data, '运营商.xls')
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
