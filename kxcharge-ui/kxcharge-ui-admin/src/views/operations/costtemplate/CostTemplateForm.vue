<template>
  <!-- <Dialog :title="dialogTitle" v-model="dialogVisible"> -->
  <el-form
    ref="formRef"
    :model="formData"
    :rules="formRules"
    label-width="200px"
    v-loading="formLoading"
  >
    <div class="top" style="background-color: #fff">
      <div class="header">
        <span class="title">模板基础信息</span>
      </div>
      <el-form-item label="计算规则模板名称" prop="templateName">
        <el-col :span="6">
          <el-input v-model="formData.templateName" v-no-space placeholder="请输入模板名称" />
        </el-col>
      </el-form-item>
      <el-form-item label="归属运营商" prop="orgId">
        <el-col :span="6">
          <el-select v-model="formData.orgId" placeholder="请选择运营商">
            <el-option
              v-for="item in operatorOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-col>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-col :span="6">
          <el-input v-model="formData.remark" :rows="4" type="textarea" />
        </el-col>
      </el-form-item>
    </div>
    <div class="center" style="background-color: #fff">
      <div class="header">
        <span class="title">价格类别</span>
      </div>
      <el-form-item
        v-for="(item, index) in formData.costTemplatePriceTypeRespVOS"
        :key="index"
        :prop="'costTemplatePriceTypeRespVOS.' + index"
        :label-width="'0px'"
      >
        <el-col :span="6">
          <el-form-item
            label="类别名称"
            :prop="'costTemplatePriceTypeRespVOS.' + index + '.typeName'"
          >
            <el-input
              v-model="item.typeName"
              :formatter="(value) => priceCategoryTypeMap.get(value)"
              readonly
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            label="电费"
            :prop="'costTemplatePriceTypeRespVOS.' + index + '.pricePower'"
            :rules="[{ required: true, message: '电费不能为空', trigger: 'blur' }]"
          >
            <el-input v-model="item.pricePower">
              <template #append>元/度</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            label="服务费"
            :prop="'costTemplatePriceTypeRespVOS.' + index + '.priceService'"
            :rules="[{ required: true, message: '电费不能为空', trigger: 'blur' }]"
          >
            <el-input v-model="item.priceService">
              <template #append>元/度</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-form-item>
    </div>
    <div class="bottom" style="background-color: #fff">
      <div class="header">
        <span class="title">价格设置</span>
      </div>

      <el-form-item
        v-for="(data, index) in formData.costTemplatePriceRespVOS"
        :key="index"
        :prop="'costTemplatePriceRespVOS.' + index"
        :label-width="'82px'"
      >
        <el-icon
          :color="'#008fe0'"
          :size="39"
          style="margin-right: 10px"
          @click="removeTimeInterval(index)"
          v-if="index !== 0"
        >
          <RemoveFilled />
        </el-icon>
        <div v-else style="width: 30px; height: 30px;margin-right: 20px"></div>
        <el-col :span="3">
          <el-form-item
            :prop="'costTemplatePriceRespVOS.' + index + '.startTime'"
            :rules="[{ required: true, message: '时段不能为空', trigger: 'blur' }]"
          >
            <el-select v-model="data.startTime" @change="changeStart($event, index)">
              <el-option v-for="(item, i) in timeStartOptions[index]" :key="i" :label="item.value" :value="i" :disabled="item.disabled" />
            </el-select>
          </el-form-item>
        </el-col>
        <span style="margin: 0 11px">至</span>
        <el-col :span="4" style="margin-right: 25px">
          <el-form-item
            :prop="'costTemplatePriceRespVOS.' + index + '.endTime'"
            :rules="[{ required: true, message: '时段不能为空', trigger: 'blur' }]"
          >
            <el-select v-model="data.endTime" @change="changeEnd($event, index)" placeholder="请先选择开始时段">
              <el-option v-for="(item, i) in timeEndOptions[index]" :key="i" :label="item.value" :value="i" :disabled="item.disabled" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item
            :prop="'costTemplatePriceRespVOS.' + index + '.priceTypeId'"
            :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]"
          >
            <el-select v-model="data.priceTypeId" @visible-change="openOption($event)">
              <el-option
                v-for="(item, i) in intervalOptions"
                :key="i"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-form-item>
      <!-- <el-form-item :label-width="'130px'">
      </el-form-item> -->
      <div class="addBtn" style="margin-left: 80px" @click="addTimeInterval">
        <el-icon :color="'#008fe0'" :size="39"><CirclePlusFilled /></el-icon>
        <span style="margin-left: 10px"> 添加时间区间 </span>
      </div>
    </div>
    <div class="btn">
      <el-form-item :label-width="'0px'">
        <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
        <el-button @click="goBack">取 消</el-button>
      </el-form-item>
    </div>
  </el-form>

  <!-- </Dialog> -->
</template>
<script setup lang="ts">
import * as CostTemplateApi from '@/api/operations/costTemplate'
import { getOperatorAll } from '@/api/system/operator'
import { useTagsViewStore } from '@/store/modules/tagsView'
import { getTenantId } from '@/utils/auth'
import gwEventBus from '@/utils/energy-eventbus'
import { useRoute, useRouter } from 'vue-router'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const route = useRoute()
const router = useRouter()
const tagsViewStore = useTagsViewStore()

// const dialogVisible = ref(false) // 弹窗的是否展示
// const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref(route.query.type) // 表单的类型：create - 新增；update - 修改
const formId = ref(route.query.id)
const formData = ref({
  id: undefined,
  templateName: undefined,
  orgId: undefined,
  remark: undefined,
  costTemplatePriceTypeRespVOS: [
    {
      typeName: 'pointed',
      pricePower: undefined,
      priceService: undefined,
      identification: 1,
      orgId: undefined
    },
    {
      typeName: 'peak',
      pricePower: undefined,
      priceService: undefined,
      identification: 2,
      orgId: undefined
    },
    {
      typeName: 'flat',
      pricePower: undefined,
      priceService: undefined,
      identification: 3,
      orgId: undefined
    },
    {
      typeName: 'valley',
      pricePower: undefined,
      priceService: undefined,
      identification: 4,
      orgId: undefined
    }
  ],
  costTemplatePriceRespVOS: [
    { endTime: '', endTimeStr: '', priceTypeId: undefined, startTime: '', startTimeStr: '' }
  ],
  'tenant-id': getTenantId()
})
const priceCategoryTypeMap = new Map<string, string>()
  .set('pointed', '尖')
  .set('peak', '峰')
  .set('flat', '平')
  .set('valley', '谷')
const formRules = reactive({
  templateName: [{ required: true, message: '计算规则模板名称不能为空', trigger: 'blur' }],
  orgId: [{ required: true, message: '归属运营商不能为空', trigger: 'blur' }],
  'costTemplatePriceTypeRespVOS.*.pricePower': [
    { required: true, message: '电费不能为空', trigger: 'blur' }
  ],
  'costTemplatePriceTypeRespVOS.*.priceService': [
    { required: true, message: '服务费不能为空', trigger: 'blur' }
  ],
  'costTemplatePriceRespVOS.*.startTime': [
    { required: true, message: '时段不能为空', trigger: 'blur' }
  ],
  'costTemplatePriceRespVOS.*.endTime': [
    { required: true, message: '时段不能为空', trigger: 'blur' }
  ],
  'costTemplatePriceRespVOS.*.priceTypeId': [
    { required: true, message: '不能为空', trigger: 'blur' }
  ]
})

const handleTimeIntervals = (type: 'start' | 'end') => {
  let intervals = []
  const leng = formData.value.costTemplatePriceRespVOS.length
  const ranges = getTiemRanges()
  for (let hour = 0; hour <= 24; hour++) {
    for (let minutes = 0; minutes < 60; minutes += 30) {
      const time = `${hour.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`
      if (time !== '24:30') {
        // @ts-ignore
        intervals.push({
          value: time,
          disabled: false
        })
      }
    }
  }
  if (leng > 1) {
    const result = ranges.splice(0, ranges.length - 1)
    intervals = intervals.map((item, index) => {
      const isInInterval = result.some(([start, end]) => {
      if (type === 'start') {
        return index >= start && index < end;
      } else {
        return index >= start && index <= end;
      }
      });
      if (isInInterval) {
        item.disabled = true;
      }
      return item
    })
  }
  return intervals
}

const getTiemRanges = (): [] => {
  const ranges: any = []
  formData.value.costTemplatePriceRespVOS.forEach(item => {
    ranges.push([item.startTime, item.endTime])
  })
  return ranges
}

const changeStart = (e, ind) => {
  timeEndOptions[ind].forEach((item, index) => item.disabled = index <= e)
  for (let ins = 0; ins < ind; ins++) {
    timeEndOptions[ins].forEach((item, index) => item.disabled = index > e)
  }
  initStartInterval()
}

const changeEnd = (e, ind) => {
  if (formData.value.costTemplatePriceRespVOS[ind].startTime !== 0 && !formData.value.costTemplatePriceRespVOS[ind].startTime ) {
    formData.value.costTemplatePriceRespVOS[ind].endTime = ''
    message.error('请先选择开始区间')
    return
  }
  timeStartOptions[ind].forEach((item, index) => {
    if (index >= e) {
      item.disabled = true
    }
  })
  initStartInterval()
}

const initStartInterval = () => {
  const interarr = formData.value.costTemplatePriceRespVOS.map(item => {
    return [Number(item.startTime), Number(item.endTime)]
  })
  if (!interarr[interarr.length - 1][0]) {
    timeStartOptions[timeStartOptions.length -1].forEach((k, ind) => {
      k['disabled'] = false
      interarr.forEach(val => {
        if (ind >= val[0] && ind < val[1]) {
          k['disabled'] = true

        }
      })
    })
    timeEndOptions[timeEndOptions.length -1].forEach(k => k['disabled'] = false)
  }
}

const formRef = ref() // 表单 Ref

const operatorOptions = reactive([]) // 运营商选择器
const intervalOptions = reactive([]) // 价格选择器
const timeStartOptions = reactive([handleTimeIntervals('start')]) // 开始时间选择器
const timeEndOptions = reactive([handleTimeIntervals('end')]) // 结束时间选择器
// 获取归属运营商列表
const getOperator = async () => {
  const data = await getOperatorAll()
  data.forEach((item) => {
    // @ts-ignore
    operatorOptions.push({
      value: item.id,
      label: item.orgName
    })
  })
}

// 添加时间区间
const addTimeInterval = () => {
  const costData = formData.value.costTemplatePriceRespVOS
  const len = formData.value.costTemplatePriceRespVOS.length
  const { startTime, endTime } = costData[len - 1];
  if (startTime === "" || endTime ==="") {
    message.info("请填写完整时间区间后再添加!")
    return;
  }
  // @ts-ignore

  const timeObject = {
    endTime: '',
    endTimeStr: '',
    priceTypeId: undefined,
    startTime: '',
    startTimeStr: ''
  }
  formData.value.costTemplatePriceRespVOS.push(timeObject)
  timeStartOptions.push(handleTimeIntervals('start'))
  timeEndOptions.push(handleTimeIntervals('end'))
}


// 移除时间区间
const removeTimeInterval = (i: number) => {
  formData.value.costTemplatePriceRespVOS.splice(i, 1)
  timeEndOptions.splice(i, 1)
  timeStartOptions.splice(i, 1)
  initStartInterval()
}

const openOption = (e) => {
  if (e) {
    intervalOptions.length = 0
    formData.value.costTemplatePriceTypeRespVOS.forEach((item) => {
      // @ts-ignore
      intervalOptions.push({
        value: item.typeName,
        label: `${priceCategoryTypeMap.get(item.typeName)}:电费【${
          item.pricePower ?? ''
        }元】服务费【${item.priceService ?? ''}元】`
      })
    })
  }
}


const goBack = () => {
  router.back()
  tagsViewStore.delView(route)
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as CostTemplateApi.CostTemplateVO
    let isAll = false
    const intervalArr: any = []
    data.costTemplatePriceRespVOS.forEach((item, index) => {
      intervalArr.push([item.startTime, item.endTime])
      if (item.endTime === 48) {
        isAll = true
      }
      item['endTimeStr'] = timeStartOptions[index][item.endTime].value
      item['startTimeStr'] = timeEndOptions[index][item.startTime].value
    })
    if (!isAll || !coversEntireRange(intervalArr)) {
      message.error('添加的时段没有覆盖24小时')
      return
    }
    const hasOverlapResult = hasOverlap(intervalArr);
    if (hasOverlapResult) {
      message.error('添加的时段有重复区间')
      return
    }
    if (formType.value === 'create') {
      await CostTemplateApi.createCostTemplate(data)
      message.success(t('common.createSuccess'))
    } else {
      await CostTemplateApi.updateCostTemplate(data)
      message.success(t('common.updateSuccess'))
    }
    router.back()
    gwEventBus.emit('toCostList')
    tagsViewStore.delView(route)
    // dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

  const hasOverlap = (intervals) => {
    // 检查每一对区间是否有重叠
    for (let i = 0; i < intervals.length - 1; i++) {
      for (let j = i + 1; j < intervals.length; j++) {
        const intervalA = intervals[i];
        const intervalB = intervals[j];

        // 检查是否有重叠
        if (
          (intervalA[0] < intervalB[1] && intervalA[1] > intervalB[0]) ||
          (intervalB[0] < intervalA[1] && intervalB[1] > intervalA[0])
        ) {
          return true; // 有重叠
        }
      }
    }

    return false; // 没有重叠
  }

  const coversEntireRange = (intervals) => {
    // 定义目标区间
    const targetRange = [0, 48];
    // 将输入区间按照起点进行排序
    intervals.sort((a, b) => a[0] - b[0]);
    // 如果没有区间，直接返回 false
    if (intervals.length === 0) {
      return false;
    }
    let currentEnd = intervals[0][1];
    // 遍历区间
    for (let i = 1; i < intervals.length; i++) {
      // 如果下一个区间的起点在当前区间之后，说明存在间隙
      if (intervals[i][0] > currentEnd) {
        return false;
      }
      // 更新当前区间的结束点
      currentEnd = Math.max(currentEnd, intervals[i][1]);
      // 如果当前区间的结束点超过了目标区间的结束点，说明完整覆盖
      if (currentEnd >= targetRange[1]) {
        return true;
      }
    }
    // 检查最后一个区间的结束点是否等于目标区间的结束点
    if (currentEnd === targetRange[1]) {
        return true;
    }
    return false;
  }

/** 重置表单 */
// const resetForm = () => {
//   formData.value = {
//     id: undefined,
//     templateName: undefined,
//     orgId: undefined,
//     remark: undefined,
//     costTemplatePriceTypeRespVOS: [
//       { typeName: 'pointed', pricePower: undefined, priceService: undefined, identification: 1, orgId: undefined },
//       { typeName: 'peak', pricePower: undefined, priceService: undefined, identification: 2, orgId: undefined },
//       { typeName: 'flat', pricePower: undefined, priceService: undefined, identification: 3, orgId: undefined },
//       { typeName: 'valley', pricePower: undefined, priceService: undefined, identification: 4, orgId: undefined }
//     ],
//     costTemplatePriceRespVOS: [
//       { endTime: "", endTimeStr: "",  priceTypeId: undefined, startTime: "", startTimeStr: "" }
//     ],
//     'tenant-id': getTenantId()
//   }
//   formRef.value?.resetFields()
// }

const initFormData = async () => {
  if (formType.value === 'update') {
    const data = await CostTemplateApi.getCostTemplate(formId.value)
    formData.value = data
    openOption(true)
    const len = formData.value.costTemplatePriceRespVOS.length
    for (let index = 0; index < len - 1; index++) {
      timeStartOptions.push(handleTimeIntervals('start'))
      timeEndOptions.push(handleTimeIntervals('end'))
    }

  }
}

onMounted(() => {
  route.meta.title = formId.value ? '编辑计费模板' : '新增计费模板'
  tagsViewStore.visitedViews.find(k => k.path === route.path)!.meta.title = route.meta.title
  getOperator()
  initFormData()
})
</script>

<style scoped lang="scss">
// .el-form {
//   height: 100%;
//   background-color: #fff;
// }
.top,
.center,
.bottom {
  padding: 20px 0;
}
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
.addBtn {
  display: flex;
  align-items: center;
}

.bottom {
  .el-col-6 {
    max-width: 30%;
    flex: 0 0 28%;
  }
}
.btn {
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
}

::v-deep {
  .el-select {
    width: 100%;
  }
}
</style>
