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
      <el-form-item label="活动名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入活动名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="活动状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择活动状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="dict.value"
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
          v-hasPermi="['promotion:combination-activity:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="活动编号" prop="id" min-width="80" />
      <el-table-column label="活动名称" prop="name" min-width="140" />
      <el-table-column label="活动时间" min-width="210">
        <template #default="scope">
          {{ formatDate(scope.row.startTime, 'YYYY-MM-DD') }}
          ~ {{ formatDate(scope.row.endTime, 'YYYY-MM-DD') }}
        </template>
      </el-table-column>
      <el-table-column label="商品图片" prop="spuName" min-width="80">
        <template #default="scope">
          <el-image
            :src="scope.row.picUrl"
            class="h-40px w-40px"
            :preview-src-list="[scope.row.picUrl]"
            preview-teleported
          />
        </template>
      </el-table-column>
      <el-table-column label="商品标题" prop="spuName" min-width="300" />
      <el-table-column
        label="原价"
        prop="marketPrice"
        min-width="100"
        :formatter="fenToYuanFormat"
      />
      <el-table-column label="拼团价" prop="seckillPrice" min-width="100">
        <template #default="scope">
          {{ formatCombinationPrice(scope.row.products) }}
        </template>
      </el-table-column>
      <el-table-column label="开团组数" prop="groupCount" min-width="100" />
      <el-table-column label="成团组数" prop="groupSuccessCount" min-width="100" />
      <el-table-column label="购买次数" prop="recordCount" min-width="100" />
      <el-table-column label="活动状态" align="center" prop="status" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" width="150px" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['promotion:combination-activity:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleClose(scope.row.id)"
            v-if="scope.row.status === 0"
            v-hasPermi="['promotion:combination-activity:close']"
          >
            关闭
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-else
            v-hasPermi="['promotion:combination-activity:delete']"
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
  <CombinationActivityForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import * as CombinationActivityApi from '@/api/mall/promotion/combination/combinationActivity'
import CombinationActivityForm from './CombinationActivityForm.vue'
import { formatDate } from '@/utils/formatTime'
import { fenToYuanFormat } from '@/utils/formatter'
import { fenToYuan } from '@/utils'

defineOptions({ name: 'PromotionBargainActivity' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: null,
  status: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CombinationActivityApi.getCombinationActivityPage(queryParams)
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

// TODO 这里要改下
/** 关闭按钮操作 */
const handleClose = async (id: number) => {
  try {
    // 关闭的二次确认
    await message.confirm('确认关闭该秒杀活动吗？')
    // 发起关闭
    await CombinationActivityApi.closeCombinationActivity(id)
    message.success('关闭成功')
    // 刷新列表
    await getList()
  } catch {}
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await CombinationActivityApi.deleteCombinationActivity(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

const formatCombinationPrice = (products) => {
  const combinationPrice = Math.min(...products.map((item) => item.combinationPrice))
  return `￥${fenToYuan(combinationPrice)}`
}

/** 初始化 **/
onMounted(async () => {
  await getList()
})
</script>
