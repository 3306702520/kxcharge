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
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入电话"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="用户名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="反馈类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择反馈类型" clearable class="!w-240px">
          <el-option
            v-for="item in feedbackTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="图片" prop="img">
        <el-input
          v-model="queryParams.img"
          placeholder="请输入图片"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="item in feedbackStatusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <!-- <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['member:feedback:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button> -->

        <!-- <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['member:feedback:export']"
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
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="电话" align="center" prop="phone" />
      <el-table-column label="用户名称" align="center" prop="name" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="反馈类型" align="center" prop="type">
        <template #default="scope">
          <el-tag>{{ getDictLabel(DICT_TYPE.FEEDBACK_TYPE, scope.row.type) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" prop="img">
        <template #default="scope">
          <el-check-tag @click="openModal(scope, 'img')" checked style="margin-right: 8px">查看图片</el-check-tag>
        </template>
      </el-table-column>
      <el-table-column label="反馈内容" align="center" prop="content">
        <template #default="scope">
          <el-check-tag @click="openModal(scope, 'racetext')" checked style="margin-right: 8px">查看反馈</el-check-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success': 'warning'">{{ getDictLabel(DICT_TYPE.FEEDBACK_STATUS, scope.row.status) }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('manage', scope.row.id)"
            v-hasPermi="['member:feedback:update']"
          >
            处理
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['member:feedback:delete']"
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
  <FeedbackForm 
    ref="formRef" 
    @success="getList" 
    :feedbackTypeOptions="feedbackTypeOptions" 
    :feedbackStatusOptions="feedbackStatusOptions" 
  />
  <el-image-viewer
    v-if="dialogVisible && modalType === 'img'"
    :url-list="socpe?.row.img"
    @close="dialogVisible = false"
  />
  <el-dialog v-model="dialogVisible" v-if="modalType === 'racetext'" title="反馈" width="600px" draggable>
    <div class="w-100%" v-html="socpe.row.content"></div>
  </el-dialog>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as FeedbackApi from '@/api/member/feedback'
import FeedbackForm from './FeedbackForm.vue'
import { getStrDictOptions, getDictLabel, DICT_TYPE } from '@/utils/dict';

defineOptions({ name: 'Feedback' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const dialogVisible = ref(false);
const modalType = ref<"img" | "racetext">("img");
const socpe = ref();
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  userId: null,
  phone: null,
  name: null,
  type: null,
  img: null,
  status: null,
  content: null,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await FeedbackApi.getFeedbackPage(queryParams)
    list.value = data.list.map(item => {
      item['img'] = item['img'].map(ele => window.location.origin + '/' + ele)
      return item;
    });
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
    await FeedbackApi.deleteFeedback(id)
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
    const data = await FeedbackApi.exportFeedback(queryParams)
    download.excel(data, '客户意见反馈	.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const feedbackTypeOptions = getStrDictOptions(DICT_TYPE.FEEDBACK_TYPE);
const feedbackStatusOptions = getStrDictOptions(DICT_TYPE.FEEDBACK_STATUS);

function openModal(scope, type){
  console.log(`output->window.location, scope`,window.location, scope)
  modalType.value = type;
  dialogVisible.value = true;
  socpe.value = scope;
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
