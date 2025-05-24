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
      <el-form-item label="自动售货机名称" prop="name" label-width="120px">
        <el-input
          v-model="queryParams.name"
          placeholder="自动售货机名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['machine:vending:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column type="index" label="序号" width="60px" />
      <el-table-column label="自动售货机名称" align="center" prop="name" />
      <el-table-column label="设备code" align="center" prop="deviceCode" />
      <el-table-column label="设备imei" align="center" prop="imei" />
      <el-table-column label="设备位置" align="center" prop="position" />
      <el-table-column label="设备图片" align="center">
        <template #default="scope">
          <el-check-tag @click="openModal(scope, 'img')" checked style="margin-right: 8px">查看图片</el-check-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">
            {{ scope.row.status === 0 ? '开启' : '关闭' }}
          </el-tag>
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
      <el-table-column label="操作" align="center" :show-overflow-tooltip="false">
        <template #default="scope">
          <div class="flex items-center justify-center">

            <el-button
              link
              type="primary"
              @click="openForm('info', scope.row.id)"
              v-hasPermi="['machine:vending:info']"
            >
              详情
            </el-button>
            <el-dropdown
              v-hasPermi="[
                'machine:vending:edit',
                'machine:vending:cargolanes',
                'machine:vending:qrcode',
                'machine:vending:isStop',
                'machine:vending:delete'
              ]"
              @command="(command) => handleCommand(command, scope.row)"
            >
              <el-button link type="primary">
                <Icon icon="ep:d-arrow-right" />
                更多
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                    v-if="checkPermi(['machine:vending:edit'])"
                    command="openForm"
                  >
                    编辑
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['machine:vending:cargolanes'])"
                    command="handleGoods"
                  >
                    商品管理
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['machine:vending:qrcode'])"
                    command="handleCargoLanes"
                  >
                    生成二维码
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['machine:vending:isStop'])"
                    command="handleStatus"
                  >
                    {{ scope.row.status === 0 ? '停用' : '开启' }}
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['machine:vending:delete'])"
                    command="deleteVendingMachine"
                  >
                    删除
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
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
    <el-image-viewer
      v-if="dialogVisible && modalType === 'img'"
      :url-list="[socpe?.row.photo]"
      @close="dialogVisible = false"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import { dateFormatter } from '@/utils/formatTime'
import { useRouter } from 'vue-router'
import * as VendingMachineApi from '@/api/machine/vendingMachine/index'
import gwEventBus from '@/utils/energy-eventbus';
import { useCache } from '@/hooks/web/useCache';
import { checkPermi } from '@/utils/permission'

const loading = ref(false) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const dialogVisible = ref(false);
const modalType = ref<"img" | "racetext">("img");
const socpe = ref();
const router = useRouter()

const statusList = reactive([
  { label: '启用', value: 0 },
  { label: '停用', value: 1 },
])

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: null,
  status: null
})

const { wsCache } = useCache()
const message = useMessage() // 消息弹窗

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const userData = JSON.parse(wsCache.get('USER_DATA'))
    console.log(`output->userData`,userData)
    const data = await VendingMachineApi.getVendingMachine(queryParams)
    console.log(`output->data`,data)
    list.value = data.list.map(item => {
      const findUser = userData.find(k => k.id == item.updater)
      item['Updater'] = findUser?.nickname ?? ''
      return item
    })
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const openForm = (type: string, id?: number) => {
  router.push({
    name: 'VendingMachineCreate',
    query: {
      id,
      type
    }
  })
}

/** 操作分发 */
const handleCommand = (command: string, row) => {
  switch (command) {
    case 'openForm':
      openForm('update', row.id, )
      break
    case 'handleStatus':
      handleStatus(row)
      break
    case 'deleteVendingMachine':
      handleDelete(row.id)
      break
    case 'handleGoods':
      router.push({
        name: 'GoodsSetting',
        query: {
          id: row.id
        }
      })
      // todo @jason：增加一个【修改余额】
      break
    default:
      break
  }
}

const handleStatus = async (row) => {
  try {
    const text = row.status ? '开启' : '关闭'
    await message.confirm(`确认要${text}"${row.name}"吗？`)
    await VendingMachineApi.updateVendingMachineStatus(row.id, Number(!row.status))
    message.success(text + '成功!')
    getList()
  } catch (error) {}
  
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
const resetQuery = () => {
}

const handleDelete = async (id) => {
  try {
    await message.confirm(`确认要删除该售货机数据吗？`)
    await VendingMachineApi.delVendingMachine(id)
    message.success('删除成功!')
    getList()
  } catch (error) {
    
  }
}

function openModal(data, type){
  console.log(`output->socpe?.row.photo`,data)
  modalType.value = type;
  dialogVisible.value = true;
  socpe.value = data;
}

onMounted(() => {
  getList()
  gwEventBus.on("toVendingMachine", () => {
    getList()
  })
})
</script>

<style lang="scss" scoped>

</style>
