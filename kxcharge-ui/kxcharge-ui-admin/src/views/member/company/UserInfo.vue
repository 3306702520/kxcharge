<template>
  <div style="background-color: #fff">
    <div class="baseInfo">
      <div
        class="header"
        style="display: flex; justify-content: space-between; align-items: center"
      >
        <span class="title">基础信息</span>
        <el-button type="primary" @click="goBack">返回</el-button>
      </div>
      <div class="content">
        <div class="left">
          <div class="item">
            <span>平台运营商</span>
            <span>{{ userInfo.orgName }}</span>
          </div>
          <div class="item">
            <span>用户账号</span>
            <span>{{ userInfo.account }}</span>
          </div>

          <div class="item">
            <span>归属城市</span>
            <span>{{ userInfo.areaName }}</span>
          </div>
        </div>
        <div class="right">
          <div class="item">
            <span>归属企业</span>
            <span>{{ userInfo.companyName }}</span>
          </div>
          <div class="item">
            <span>用户姓名</span>
            <span>{{ userInfo.name }}</span>
          </div>
          <div class="item">
            <span>手机号</span>
            <span>{{ userInfo.mobile }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="carList" style="margin-top: 20px;">
      <div class="header">
        <span class="title">车辆信息</span>
      </div>
      <div class="table" style="padding: 10px;">
        <el-table :data="userInfo.plateRespVOS">
          <el-table-column type="index" label="序号" :width="100" />
          <el-table-column property="plateNumber" label="车牌号" :width="200" />
          <el-table-column property="plateType" label="车辆类型" :width="200">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.PLATE_TYPE" :value="scope.row.plateType" />
            </template>
          </el-table-column>
          <el-table-column property="chargeQuantity" label="充电次数" :width="200" />
          <el-table-column property="plateType" label="是否为默认车辆" :width="200">
            <template #default="scope">
              <span>{{ scope.row.isDefault ? '是' : '否' }}</span>
            </template>
          </el-table-column>
          <el-table-column property="latelyPlace" label="最近充电地点" :width="250" />
          <el-table-column
            label="最近充电时间"
            align="center"
            prop="latelyTime"
            :formatter="dateFormatter"
            :width="250"
          />
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            :formatter="dateFormatter"
            :width="250"
          />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useTagsViewStore } from '@/store/modules/tagsView'
import * as UserApi from '@/api/member/user/index'
import * as OperatorApi from '@/api/system/operator/index'
import * as CompanyApi from '@/api/member/company'
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'

import { constrainedMemory } from 'process';
import { use } from 'echarts';
const tagsViewStore = useTagsViewStore()

const router = useRouter()
const route = useRoute()
const userId = route.query.id
const userInfo = ref({})
const companyList = reactive([])
const orgList = reactive([])
const goBack = () => {
  router.back()
  tagsViewStore.delView(route)
}

const getInfo = async () => {
  userInfo.value = await UserApi.getUser(userId)
  userInfo.value['orgName'] = orgList.find(k => k.value === userInfo.value.orgId).label
  userInfo.value['companyName'] = companyList.find(k => k.value === userInfo.value.companyId).label

  console.log(`output->data`, userInfo.value)
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


onMounted(() => {
  getCompanyList()
  getOrgList()
  getInfo()
})
</script>

<style lang="scss" scoped>
.header {
  .title {
    color: #008fe0;
    display: flex;
    &::before {
      display: inline-block;
      content: '';
      width: 5px;
      height: 25px;
      background-color: #008fe0;
      margin-right: 20px;
    }
  }
}
.content {
  display: flex;
  justify-content: space-around;
  .left,
  .right {
    width: 45%;
    .item {
      margin: 10px 0;
      display: flex;
      justify-content: space-between;
      font-size: 14px;
    }
  }
}
</style>
