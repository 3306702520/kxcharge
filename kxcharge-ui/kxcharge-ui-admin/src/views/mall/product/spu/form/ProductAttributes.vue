<template>
  <el-col v-for="(item, index) in attributeList" :key="index">
    <div>
      <el-text class="mx-1">属性名：</el-text>
      <el-tag class="mx-1" closable type="success" @close="handleCloseProperty(index)">
        {{ item.name }}
      </el-tag>
    </div>
    <div>
      <el-text class="mx-1">属性值：</el-text>
      <el-tag
        v-for="(value, valueIndex) in item.values"
        :key="value.id"
        class="mx-1"
        closable
        @close="handleCloseValue(index, valueIndex)"
      >
        {{ value.name }}
      </el-tag>
      <el-input
        v-show="inputVisible(index)"
        :id="`input${index}`"
        :ref="setInputRef"
        v-model="inputValue"
        class="!w-20"
        size="small"
        @blur="handleInputConfirm(index, item.id)"
        @keyup.enter="handleInputConfirm(index, item.id)"
      />
      <el-button
        v-show="!inputVisible(index)"
        class="button-new-tag ml-1"
        size="small"
        @click="showInput(index)"
      >
        + 添加
      </el-button>
    </div>
    <el-divider class="my-10px" />
  </el-col>
</template>

<script lang="ts" setup>
import { ElInput } from 'element-plus'
import * as PropertyApi from '@/api/mall/product/property'

defineOptions({ name: 'ProductAttributes' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const inputValue = ref('') // 输入框值
const attributeIndex = ref<number | null>(null) // 获取焦点时记录当前属性项的index
// 输入框显隐控制
const inputVisible = computed(() => (index) => {
  if (attributeIndex.value === null) return false
  if (attributeIndex.value === index) return true
})
const inputRef = ref([]) //标签输入框Ref
/** 解决 ref 在 v-for 中的获取问题*/
const setInputRef = (el) => {
  if (el === null || typeof el === 'undefined') return
  // 如果不存在id相同的元素才添加
  if (!inputRef.value.some((item) => item.input?.attributes.id === el.input?.attributes.id)) {
    inputRef.value.push(el)
  }
}
const attributeList = ref([]) // 商品属性列表
const props = defineProps({
  propertyList: {
    type: Array,
    default: () => {}
  }
})

watch(
  () => props.propertyList,
  (data) => {
    if (!data) return
    attributeList.value = data
  },
  {
    deep: true,
    immediate: true
  }
)

/** 删除属性值*/
const handleCloseValue = (index, valueIndex) => {
  attributeList.value[index].values?.splice(valueIndex, 1)
}
/** 删除属性*/
const handleCloseProperty = (index) => {
  attributeList.value?.splice(index, 1)
}
/** 显示输入框并获取焦点 */
const showInput = async (index) => {
  attributeIndex.value = index
  inputRef.value[index].focus()
}

const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

/** 输入框失去焦点或点击回车时触发 */
const handleInputConfirm = async (index, propertyId) => {
  if (inputValue.value) {
    // 保存属性值
    try {
      const id = await PropertyApi.createPropertyValue({ propertyId, name: inputValue.value })
      attributeList.value[index].values.push({ id, name: inputValue.value })
      message.success(t('common.createSuccess'))
      emit('success', attributeList.value)
    } catch {
      message.error('添加失败，请重试') // TODO 缺少国际化
    }
  }
  attributeIndex.value = null
  inputValue.value = ''
}
</script>
