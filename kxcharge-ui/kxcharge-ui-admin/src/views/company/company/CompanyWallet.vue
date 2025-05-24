<template>
  <div>
    <div class="header">
      <div class="left">
        <div class="item">
          <img src="/public/company.png" class="icon" />
          <span style="font-size: 14px; font-weight: 600;">企业名称</span>
          <span>{{ (companyInfo as any).companyName }}</span>
        </div>
        <div class="item">
          <img src="/public/money-wallet.png" class="icon" />
          <span style="font-size: 14px; font-weight: 600;">钱包余额</span>
          <span>{{ handleMoney((companyInfo as any).balance) }}</span>
        </div>
      </div>
      <div class="right">
        <el-button style="color: #4064ea;" @click="createMoney(1)">
          充值
        </el-button>
        <el-button style="color: #fa3939;" @click="createMoney(2)">
          退款
        </el-button>
        <el-button style="color: #4064ea;" @click="goBack">
          返回
        </el-button>
      </div>
    </div>
    <div class="searchForm">
      <div class="title">
        <span class="title">钱包流水</span>
      </div>
      <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="68px"
        style="display: flex; justify-content: space-between;padding: 0 20px;"
      >
        <div class="item">
          <el-form-item label="流水类型" prop="bizType">
            <el-select
              v-model="queryParams.bizType"
              placeholder="请选择流水类型"
              clearable
              class="!w-240px"
            >
              <el-option
                v-for="dict in getIntDictOptions(DICT_TYPE.COMPANY_RECORD_BIZ_TYPE)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="流水时间" prop="createTime">
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
        </div>
        <div class="btn">
          <el-form-item>
            <el-button @click="handleQuery" type="primary"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
            <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
    <ContentWrap>
      <el-button
        :loading="exportLoading"
        @click="handleExport"
      >
        <Icon icon="ep:download" class="mr-5px" /> 导出
      </el-button>
      <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
        <el-table-column type="index" label="序号" width="60px" />
        <el-table-column label="流水号" align="center" prop="bizId" />
        <el-table-column label="流水类型" align="center" prop="bizType">
          <template #default="scope">
            <dict-tag :type="DICT_TYPE.COMPANY_RECORD_BIZ_TYPE" :value="scope.row.bizType" />
          </template>
        </el-table-column>
        <el-table-column label="流水金额(元)" align="center" prop="balance">
          <template #default="scope">
            <span>{{ handleMoney(scope.row.balance) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="description" />
        <el-table-column label="执行人" align="center" prop="creator" />
        <el-table-column
          label="流水时间时间"
          align="center"
          prop="createTime"
          :formatter="dateFormatter"
          width="180px"
        />
      </el-table>
      <!-- 分页 -->
      <Pagination
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </ContentWrap>

    <Dialog :title="dialogTitle" v-model="dialogVisible" :close-on-click-modal="false" :width="600">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="150px"
        v-loading="formLoading"
      >
        <el-form-item :label="dialogTitle + '金额'" prop="balance">
          <el-col :span="18">
            <el-input v-model="formData.balance" type="number" />
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="description">
          <el-col :span="18">
            <el-input
              v-model="formData.description"
              :rows="5"
              type="textarea"
            />
          </el-col>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </template>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import * as CompanyApi from '@/api/member/company'
import download from '@/utils/download';
import { useTagsViewStore } from '@/store/modules/tagsView';

const message = useMessage() // 消息弹窗
const route = useRoute()
const tagsViewStore = useTagsViewStore()
const router = useRouter()
const queryFormRef = ref()
const dialogTitle = ref('')
const dialogVisible = ref(false)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  companyId: route.query.id,
  bizType: undefined,
  createTime: undefined
})
const formData = ref({
  description: '',
  balance: ''
})
const formRules= reactive({
  description: [{ required: true, message: '备注不能为空', trigger: 'blur' }],
  balance: [{ required: true, message: '充值金额不能为空', trigger: 'blur' }],
})
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const exportLoading = ref(false) // 导出的加载中
const companyInfo = ref({})
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

const handleMoney = (money) => {
  return money / 100
}

const getList = async () => {
  loading.value = true
  try {
    const data = await CompanyApi.getCompanyRecord(queryParams)
    list.value = data.list
    // list.value = list.value.map(item => {
    //   // @ts-ignore
    //   const find = orgList.value.find(k => k.id === item.orgId)
    //   // @ts-ignore
    //   item['orgName'] = find?.orgName ?? ''
    //   return item
    // })
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const createMoney = async (type: 2 | 1) => {
  dialogTitle.value = type === 1 ? '充值' : '退款'
  dialogVisible.value = true
}

const formRef = ref()
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  const parmas = {
    companyId: queryParams.companyId,
    bizId: queryParams.companyId,
    bizType: dialogTitle.value === '充值' ? 1 : 2,
    title: dialogTitle.value,
    ...formData.value
  }
  // @ts-ignore
  parmas['balance'] = parmas.bizType === 2 ? -Number(parmas.balance * 100) : Number(parmas.balance) * 100
  try {
    await CompanyApi.companyCreateMoney(parmas)
    message.success(`${dialogTitle.value}成功!`)
    getList()
    companyWallet()
    dialogVisible.value = false
    formLoading.value = false
    resetForm()
  } catch (error: any) {}
}

const goBack = () => {
  router.back()
  tagsViewStore.delView(route)
}


const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await CompanyApi.exportRecord(queryParams)
    download.excel(data, '企业流水记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const companyWallet = async () => {
  // @ts-ignore
  const data = await CompanyApi.getCompany(queryParams.companyId);
  companyInfo.value = data
}

const resetForm = async () => {
  formData.value = {
    description: '',
    balance: ''
  }
  formRef.value?.resetFields()

}

onMounted(() => {
  companyWallet()
  getList()
})

</script>

<style scoped lang="scss">
.header {
  background: linear-gradient(180deg,#5073f7 0%,#2e52db 100%);
  padding: 20px 40px;
  border-radius: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .left {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 70%;
    .item {
      display: flex;
      flex-direction: column;
      align-items: center;
      color: #fff;
      .icon {
        width: 50px;
        height: 50px;
      }
    }
  }
  .el-button {
    padding: 8px 35px;
    border-radius: 8px;
  }
}
.searchForm {
  background-color: #fff;
  padding: 20px 0;
  margin: 20px 0;
  .title {
  .title {
    color: #008fe0;
    display: flex;
    &::before {
      display: inline-block;
      content: '';
      width: 5px;
      height: 20px;
      background-color: #008fe0;
      margin-right: 20px;
    }
  }
}
}
</style>