<template>
  <div class="head-container">
    <el-input v-model="deptName" class="mb-20px" clearable placeholder="请输入运营商名称">
      <template #prefix>
        <Icon icon="ep:search" />
      </template>
    </el-input>
  </div>
  <div class="head-container">
    <el-tree
      ref="treeRef"
      :data="deptList"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      :props="defaultProps"
      default-expand-all
      highlight-current
      node-key="id"
      @node-click="handleNodeClick"
    />
  </div>
</template>

<script lang="ts" setup>
import { ElTree } from 'element-plus'
import * as DeptApi from '@/api/system/operator'
import { handleTree } from '@/utils/tree'

defineOptions({ name: 'SystemUserDeptTree' })

const deptName = ref('')
const deptList = ref<Tree[]>([]) // 树形结构
const treeRef = ref<InstanceType<typeof ElTree>>()
const defaultProps = {
  label: 'orgName',
  value: 'id',
  children: 'children',
}
/** 获得部门树 */
const getTree = async () => {
  const res = await DeptApi.getOperatorAll()
  deptList.value = []
  const root = {
    orgName: '所有运营商',
    id: '',
    children: []
  }
  // @ts-ignore
  root.children.push(...handleTree(res))
  // @ts-ignore
  deptList.value.push(root)
}

/** 基于名字过滤 */
const filterNode = (name: string, data: Tree) => {
  if (!name) return true
  // @ts-ignore
  return data.orgName.includes(name)
}

/** 处理部门被点击 */
const handleNodeClick = async (row: { [key: string]: any }) => {
  emits('node-click', row)
}
const emits = defineEmits(['node-click'])

/** 监听deptName */
watch(deptName, (val) => {
  treeRef.value!.filter(val)
})

/** 初始化 */
onMounted(async () => {
  await getTree()
})
</script>
