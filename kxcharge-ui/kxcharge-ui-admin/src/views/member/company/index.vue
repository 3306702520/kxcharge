<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      ref="queryFormRef"
      :inline="true"
      :model="queryParams"
      class="-mb-15px"
      label-width="68px"
    >
      <el-form-item label="用户账户" prop="account">
        <el-input
          v-model="queryParams.account"
          class="!w-240px"
          clearable
          placeholder="请输入用户账户"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          class="!w-240px"
          clearable
          placeholder="请输入手机号"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归属企业">
        <el-select v-model="queryParams.companyId" placeholder="归属企业" clearable>
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="平台运营商" label-width="85px">
        <el-select v-model="queryParams.orgId" placeholder="平台运营商" clearable>
          <el-option
            v-for="item in orgList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="用户状态" style="width: 242px" clearable>
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          class="!w-240px"
          clearable
          placeholder="请输入用户姓名"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['member:user:createCompany']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['member:user:exportCompany']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <!-- <el-button v-hasPermi="['promotion:coupon:send']" @click="openCoupon">发送优惠券</el-button> -->
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table
      v-loading="loading"
      :data="list"
      :show-overflow-tooltip="true"
      :stripe="true"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="index" label="序号" width="60px" />
      <el-table-column align="center" label="平台运营商" prop="orgName" />
      <el-table-column align="center" label="所属企业" prop="companyName" />
      <el-table-column align="center" label="用户账户" prop="account" />
      <el-table-column align="center" label="用户姓名" prop="name" />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="注册时间"
        prop="createTime"
        width="180px"
      />
      <el-table-column align="center" label="车牌号" prop="plateNumber" />
      <el-table-column align="center" label="手机号" prop="mobile" />
      <el-table-column
        :show-overflow-tooltip="false"
        align="center"
        label="绑定标签"
        prop="tagNames"
      >
        <template #default="scope">
          <el-tag v-for="(tagName, index) in scope.row.tagNames" :key="index" class="mr-5px">
            {{ tagName }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="经验" prop="experience" />
      <el-table-column align="center" label="状态" prop="status">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="false" align="center" fixed="right" label="操作">
        <template #default="scope">
          <div class="flex items-center justify-center">
            <el-button link type="primary" @click="openDetail(scope.row.id)">详情</el-button>
            <el-dropdown
              v-hasPermi="[
                'member:user:updateCompany',
                'member:user:resetPasswordCompany',
                'member:user:freezeCompany',
                'member:user:thawCompany',
                'member:user:deleteCompany'
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
                    v-if="checkPermi(['member:user:updateCompany'])"
                    command="handleUpdate"
                  >
                    修改
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['member:user:carCompany'])"
                    command="carManagement"
                  >
                    车辆管理
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['member:user:resetPasswordCompany'])"
                    command="resetPassword"
                  >
                    重置密码
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['member:user:freezeCompany']) && scope.row.status === 0"
                    command="freezeUser"
                  >
                    冻结
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['member:user:thawCompany']) && scope.row.status === 1"
                    command="thawUser"
                  >
                    解冻
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['member:user:deleteCompany'])"
                    command="deleteUser"
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
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <UserForm ref="formRef" @success="getList" />
  <!-- 车辆管理 -->
  <UserCar ref="carFormRef" />
  <!-- 修改用户等级弹窗 -->
  <UserLevelUpdateForm ref="updateLevelFormRef" @success="getList" />
  <!-- 修改用户积分弹窗 -->
  <UserPointUpdateForm ref="updatePointFormRef" @success="getList" />
  <!-- 发送优惠券弹窗 -->
  <CouponSendForm ref="couponSendFormRef" />
</template>
<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import * as UserApi from '@/api/member/user'
import * as CompanyApi from '@/api/member/company'
import * as OperatorApi from '@/api/system/operator'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'

import UserForm from './UserForm.vue'
import UserCar from './UserCar.vue'
import { CouponSendForm } from '@/views/mall/promotion/coupon/components'
import { checkPermi } from '@/utils/permission'
import download from '@/utils/download'
import { User } from '@element-plus/icons-vue/dist/types'
import { changeAppStatus } from '@/api/pay/app'
defineOptions({ name: 'MemberUser' })

const message = useMessage() // 消息弹窗

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  account: null,
  mobile: null,
  companyId: null,
  orgId: null,
  status: null,
  name: null
})
const queryFormRef = ref() // 搜索的表单
const updateLevelFormRef = ref() // 修改会员等级表单
const updatePointFormRef = ref() // 修改会员积分表单
const selectedIds = ref<number[]>([]) // 表格的选中 ID 数组
const exportLoading = ref(false) // 导出的加载中
const companyList = reactive([])
const orgList = reactive([])

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await UserApi.getCompanyPage(queryParams)
    list.value = data.list.map(item => {
      item['orgName'] = orgList.find(k => k.value === item.orgId)?.label ?? ''
      item['companyName'] = companyList.find(k => k.value === item.companyId)?.label ?? ''
      return item
    })
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 导出 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await UserApi.exportCompanyUser(queryParams)
    download.excel(data, '企业用户.xls')
  } catch {
  } finally {
    exportLoading.value = false
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

/** 打开会员详情 */
const { push } = useRouter()
const openDetail = (id: number) => {
  push({ name: 'CompanyUserInfo', query: { id } })
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 表格选中事件 */
const handleSelectionChange = (rows: UserApi.UserVO[]) => {
  selectedIds.value = rows.map((row) => row.id)
}

/** 发送优惠券 */
// const couponSendFormRef = ref()
// const openCoupon = () => {
//   if (selectedIds.value.length === 0) {
//     message.warning('请选择要发送优惠券的用户')
//     return
//   }
//   couponSendFormRef.value.open(selectedIds.value)
// }

const carFormRef = ref()
/** 操作分发 */
const handleCommand = async (command: string, row: UserApi.UserVO) => {
  switch (command) {
    case 'handleUpdate':
      openForm('update', row.id)
      break
    case 'resetPassword':
      await message.confirm('是否确定将密码重置为123456？')
      console.log(`output->1111`,1111)
      try {
        const parmas = {
          id: row.id,
          password: '123456'
        }
        await UserApi.updatePassword(parmas)
        message.success('重置成功!')
      } catch{}
      break
    case 'freezeUser':
      changeUserStatus(1, row.id)
      break
    case 'thawUser':
      changeUserStatus(0, row.id)
      break
    case 'deleteUser':
      try {
        await message.delConfirm()
        await UserApi.delCompanyUser(row.id)
        message.success('删除成功!')
        getList()
      } catch{}
      break
    case 'carManagement':
      carFormRef.value.open(row.id)
      break
    default:
      break
  }
}

const changeUserStatus = async (status, id) => {
  try {
    await message.confirm(`是否确认${status ? '冻结' : '解冻'}`)
    const params = {
      id,
      status
    }
    await UserApi.updateCompanyStatus(params)
    message.success(`${status ? '冻结' : '解冻'}成功!`)
    getList()
  } catch {}
}

const getCompanyList = async () => {
  const data = await CompanyApi.getCompanyListAll()
  data.forEach((item) => {
    // @ts-ignore
    companyList.push({
      value: item.id,
      label: item.companyName
    })
  })
}

const getOrgList = async () => {
  const data = await OperatorApi.getOperatorAll()
  data.forEach((item) => {
    // @ts-ignore
    orgList.push({
      value: item.id,
      label: item.orgName
    })
  })
}

/** 初始化 **/
onMounted(() => {
  getCompanyList()
  getOrgList()
  getList()
})
</script>
