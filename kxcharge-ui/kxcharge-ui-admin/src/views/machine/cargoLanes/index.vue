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
      <el-form-item label="货道模板名称" prop="name" label-width="120px">
        <el-input
          v-model="queryParams.name"
          placeholder="货道模板名称"
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
          v-hasPermi="['machine:cargolanes:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column type="index" label="序号" width="60px" />
      <el-table-column label="货道模板名称" align="center" prop="name" />
      <el-table-column label="货道内容" align="center"> 
        <template #default="scope">
          <span v-for="(item, index) in scope.row.content" :key="index" style="margin: 0 0 5px">
            {{ item.floor }}层：{{ item.num }}个货道
          </span>  
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">
            {{ scope.row.status === 0 ? '开启' : '关闭' }}
          </el-tag>
        </template>
      </el-table-column>
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
            @click="openForm('detail', scope.row.id)"
            v-hasPermi="['machine:cargolanes:info']"
          >
            详情
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['machine:cargolanes:edit']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['machine:cargolanes:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <CargoLanesForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import CargoLanesForm from './CargoLanesForm.vue'
import * as CargoLangsApi from '@/api/machine/cargoLanes/index'
import { useCache } from '@/hooks/web/useCache';

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: null,
})
const { wsCache } = useCache()

/** 查询列表 */
const getList = async () => {
  try {
    const userData = JSON.parse(wsCache.get('USER_DATA'))
    const data = await CargoLangsApi.getCargoLansList(queryParams)
    list.value = data.list.map(item => {
      const findUser = userData.find(k => k.id == item.updater)
      item['Updater'] = findUser?.nickname ?? ''
      return item
    })
    total.value = data.total
    console.log(`output->data`,data)
  } finally {
    loading.value = false
  
  }
}

const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/**
 * 搜索
 */
 const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/**
 * 重置
 */
 const queryFormRef = ref() // 搜索的表单
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.confirm('是否确认删除该货道模板?')
    // 发起删除
    await CargoLangsApi.delCargoLangs(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

onMounted(() => {
  getList()
})
</script>

<style scoped lang="scss">

</style>