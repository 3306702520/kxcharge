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
      <el-form-item label="模板名称" prop="templateName">
        <el-input
          v-model="queryParams.templateName"
          placeholder="请输入模板名称"
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
          v-hasPermi="['operations:cost-template:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['operations:cost-template:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="计费规则模板名称" align="center" prop="templateName" />
      <el-table-column label="归属运营商" align="center" prop="orgName" />
      <el-table-column label="是否已应用" align="center" prop="isUsed">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COST_TEMPLATE_USED" :value="scope.row.isUsed" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="修改人" align="center" prop="Updater" />
      <el-table-column
        label="修改时间"
        align="center"
        prop="updateTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm(scope.row.id)"
            v-hasPermi="['operations:cost-template:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['operations:cost-template:delete']"
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
  <!-- <CostTemplateForm ref="formRef" @success="getList" /> -->
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as CostTemplateApi from '@/api/operations/costTemplate'
import * as OrgApi from '@/api/system/operator'
// import CostTemplateForm from './CostTemplateForm.vue'
import { DICT_TYPE } from '@/utils/dict'
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router';
import gwEventBus from '@/utils/energy-eventbus'
import { useCache } from '@/hooks/web/useCache'

defineOptions({ name: 'CostTemplate' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const router = useRouter() // 路由

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  templateName: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const route = useRoute()

const orgList = ref([])

watch(() => route, () => {
  // 在路由变化时执行的逻辑
  getList()
});

// 查询运营商列表
const getOrg = async () => {
  const data = await OrgApi.getOperatorAll();
  orgList.value = data
}
const { wsCache } = useCache()
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CostTemplateApi.getCostTemplatePage(queryParams)
    list.value = data.list
    const userData = JSON.parse(wsCache.get('USER_DATA'))
    // @ts-ignore
    list.value = list.value.map((item: any) => {
      // @ts-ignore
      const find = orgList.value.find(k => k.id === item.orgId)
      const findUser = userData.find(k => k.id == item.updater)
      // @ts-ignore
      item['orgName'] = find?.orgName ?? ''
      item['Updater'] = findUser?.nickname ?? ''
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
    name: 'CostTemplateCreate',
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
    await CostTemplateApi.deleteCostTemplate(id)
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
    const data = await CostTemplateApi.exportCostTemplate(queryParams)
    download.excel(data, '计费模板.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}


/** 初始化 **/
onMounted(() => {

  getOrg()
  getList()
  gwEventBus.on("toCostList", () => {
    getList()
  })
})

</script>
