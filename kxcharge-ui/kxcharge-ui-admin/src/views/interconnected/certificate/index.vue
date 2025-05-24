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
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable class="!w-240px">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.INTERCONNECTED_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <!-- <el-form-item label="组织机构代码" prop="operatorId">
        <el-input
          v-model="queryParams.operatorId"
          placeholder="请输入组织机构代码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <!-- <el-form-item label="秘钥" prop="operatorSecret">
        <el-input
          v-model="queryParams.operatorSecret"
          placeholder="请输入秘钥"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="签名秘钥" prop="sigSecret">
        <el-input
          v-model="queryParams.sigSecret"
          placeholder="请输入签名秘钥"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="数据加密秘钥" prop="dataSecret">
        <el-input
          v-model="queryParams.dataSecret"
          placeholder="请输入数据加密秘钥"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <!-- <el-form-item label="初始化向量" prop="dataSecretIv">
        <el-input
          v-model="queryParams.dataSecretIv"
          placeholder="请输入初始化向量"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="UAT互联互通测试url地址" class="text-ellipsis" prop="url">
        <el-input
          v-model="queryParams.url"
          placeholder="请输入UAT互联互通测试url地址"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['interconnected:certificate:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['interconnected:certificate:export']"
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
      <el-table-column label="类型" align="center" prop="type">
        <template #default="scope">
          {{ getDictLabel(DICT_TYPE.INTERCONNECTED_TYPE, scope.row.type) }}
        </template>
      </el-table-column>
      <el-table-column label="运营商" align="center" prop="orgId">
        <template #default="scope">
          {{ getObjLabel(operatorOptions, scope.row.orgId) }}
        </template>
      </el-table-column>
      <el-table-column label="组织机构代码" align="center" prop="operatorId" />
      <el-table-column label="秘钥" align="center" prop="operatorSecret" />
      <el-table-column label="签名秘钥" align="center" prop="sigSecret" />
      <el-table-column label="数据加密秘钥" align="center" prop="dataSecret" />
      <el-table-column label="初始化向量" align="center" prop="dataSecretIv" />
      <el-table-column label="UAT互联互通测试url地址" align="center" prop="url" />
      <el-table-column label="操作" align="center" width="180">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['interconnected:certificate:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['interconnected:certificate:delete']"
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
  <CertificateForm ref="formRef" @success="getList" :operatorOptions="operatorOptions" />
</template>

<script setup lang="ts">
import download from '@/utils/download'
import * as CertificateApi from '@/api/interconnected/certificate'
import CertificateForm from './CertificateForm.vue'
import { DICT_TYPE, getStrDictOptions, getDictLabel, getObjLabel } from '@/utils/dict'
import { getOperatorAll } from '@/api/system/operator';

defineOptions({ name: 'Certificate' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  type: null,
  orgId: null,
  operatorId: null,
  operatorSecret: null,
  sigSecret: null,
  dataSecret: null,
  dataSecretIv: null,
  url: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CertificateApi.getCertificatePage(queryParams)
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
    await CertificateApi.deleteCertificate(id)
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
    const data = await CertificateApi.exportCertificate(queryParams)
    download.excel(data, '互联互通秘钥.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const operatorOptions = reactive([]) // 运营商选择器
// 获取归属运营商列表
async function getOperator(){
  const data = await getOperatorAll()
  // console.log(`output->data`,data)
  data.forEach((item) => {
    // @ts-ignore
    operatorOptions.push({
      value: item.id,
      label: item.orgName
    })
  })
}

/** 初始化 **/
onMounted(() => {
  getList()
  getOperator()
})
</script>
