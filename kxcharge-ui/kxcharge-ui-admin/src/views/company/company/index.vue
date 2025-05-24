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
      <el-form-item label="企业名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入企业名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="统一社会信用代码" prop="code" :label-width="'130px'">
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
      <el-form-item label="联系方式" prop="contactNum">
        <el-input
          v-model="queryParams.contactNum"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item><el-form-item label="运营商id" prop="orgId">
        <el-input
          v-model="queryParams.orgId"
          placeholder="请输入运营商id"
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
          v-hasPermi="['member:company:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['member:company:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="企业名称" align="center" prop="companyName" />
      <el-table-column label="运营商id" align="center" prop="orgId" />
      <el-table-column label="统一社会信用代码" align="center" prop="code" />
      <el-table-column label="备注说明" align="center" prop="remark" />
      <el-table-column label="联系人" align="center" prop="contactUser" />
      <el-table-column label="联系方式" align="center" prop="contactNum" />
      <el-table-column label="联系地址" align="center" prop="contactAddress" />
      <el-table-column label="联系邮箱" align="center" prop="email" />
      <el-table-column label="营业执照" align="center" prop="businessLicense" />
      <el-table-column label="状态" align="center" prop="isFrozen">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COMPANY_STATUS" :value="scope.row.isFrozen" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="250px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['member:company:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="primary"
            @click="changeCompanyStatus(scope.row.id, 0)"
            v-hasPermi="['member:company:freeze']"
            v-if="scope.row.isFrozen === 1 "
          >
            冻结
          </el-button>
          <el-button
            link
            type="primary"
            @click="changeCompanyStatus(scope.row.id, 1)"
            v-hasPermi="['member:company:thaw']"
            v-else
          >
            解冻
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['member:company:delete']"
          >
            删除
          </el-button>
          <el-button
            link
            type="primary"
            @click="toCompanyWallet(scope.row.id)"
            v-hasPermi="['member:company:wallet']"
            style="color:#67c23a"
          >
            企业钱包
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
  <CompanyForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import download from '@/utils/download'
import * as CompanyApi from '@/api/member/company'
import CompanyForm from './CompanyForm.vue'
import { DICT_TYPE } from '@/utils/dict'

defineOptions({ name: 'Company' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const router = useRouter()
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  companyName: null,
  code: null,
  contactUser: null,
  contactNum: null,
  orgId: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CompanyApi.getCompanyPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

// 冻结解冻企业
const changeCompanyStatus = async (id: string, isFrozen: number) => {
  const parms = {
    id,
    isFrozen
  }
  try {
    await CompanyApi.updateStatus(parms)
    message.success('操作成功!')
    getList()
  } catch (error: any) {
    message.error(error)
  }
}

// 企业钱包
const toCompanyWallet = (id: string) => {
  router.push({
    name: 'CompanyWallet',
    query: {
      id
    }
  })
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
    await CompanyApi.deleteCompany(id)
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
    const data = await CompanyApi.exportCompany(queryParams)
    download.excel(data, '企业管理.xls')
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
