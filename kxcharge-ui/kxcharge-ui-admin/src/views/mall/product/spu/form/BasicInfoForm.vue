<template>
  <!-- 情况一：添加/修改 -->
  <el-form
    v-if="!isDetail"
    ref="productSpuBasicInfoRef"
    :model="formData"
    :rules="rules"
    label-width="120px"
  >
    <el-row>
      <el-col :span="12">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入商品名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="商品分类" prop="categoryId">
          <el-cascader
            v-model="formData.categoryId"
            :options="categoryList"
            :props="defaultProps"
            class="w-1/1"
            clearable
            placeholder="请选择商品分类"
            filterable
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="商品关键字" prop="keyword">
          <el-input v-model="formData.keyword" placeholder="请输入商品关键字" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="单位" prop="unit">
          <el-select v-model="formData.unit" class="w-1/1" placeholder="请选择单位">
            <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.PRODUCT_UNIT)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="商品简介" prop="introduction">
          <el-input
            v-model="formData.introduction"
            :rows="3"
            placeholder="请输入商品简介"
            type="textarea"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="商品封面图" prop="picUrl">
          <UploadImg v-model="formData.picUrl" height="80px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="商品轮播图" prop="sliderPicUrls">
          <UploadImgs v-model:modelValue="formData.sliderPicUrls" />
        </el-form-item>
      </el-col>
      <!-- <el-col :span="12">
        <el-form-item label="运费模板" prop="deliveryTemplateId">
          <el-select v-model="formData.deliveryTemplateId" placeholder="请选择">
            <el-option
              v-for="item in deliveryTemplateList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-col> -->
      <el-col :span="12">
        <el-form-item label="品牌" prop="brandId">
          <el-select v-model="formData.brandId" placeholder="请选择">
            <el-option
              v-for="item in brandList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <!-- <el-col :span="12">
        <el-form-item label="商品库存" prop="num">
          <el-input placeholder="请输入商品库存" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="所需积分" prop="point">
          <el-input placeholder="请输入所需积分" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="商品价格" prop="money">
          <el-input placeholder="请输入商品价格" />
        </el-form-item>
      </el-col> -->
     <el-col :span="12">
        <el-form-item label="商品规格" props="specType">
          <el-radio-group v-model="formData.specType" @change="onChangeSpec">
            <el-radio :label="false" class="radio">单规格</el-radio>
            <!-- <el-radio :label="true">多规格</el-radio> -->
          </el-radio-group>
        </el-form-item>
      </el-col>
       <el-col :span="12">
        <el-form-item label="分销类型" props="subCommissionType">
          <el-radio-group v-model="formData.subCommissionType" @change="changeSubCommissionType">
            <el-radio :label="false">默认设置</el-radio>
            <!-- <el-radio :label="true" class="radio">单独设置</el-radio> -->
          </el-radio-group>
        </el-form-item>
      </el-col>
      <!-- 多规格添加-->
      <el-col :span="24">
        <el-form-item v-if="!formData.specType">
          <SkuList
            ref="skuListRef"
            :prop-form-data="formData"
            :propertyList="propertyList"
            :rule-config="ruleConfig"
          />
        </el-form-item>
        <el-form-item v-if="formData.specType" label="商品属性">
          <el-button class="mb-10px mr-15px" @click="attributesAddFormRef.open()">添加属性</el-button>
          <ProductAttributes :propertyList="propertyList" @success="generateSkus" />
        </el-form-item>
        <template v-if="formData.specType && propertyList.length > 0">
          <el-form-item label="批量设置">
            <SkuList :is-batch="true" :prop-form-data="formData" :propertyList="propertyList" />
          </el-form-item>
          <el-form-item label="属性列表">
            <SkuList
              ref="skuListRef"
              :prop-form-data="formData"
              :propertyList="propertyList"
              :rule-config="ruleConfig"
            />
          </el-form-item>
        </template>
      </el-col>
    </el-row>
  </el-form>

  <!-- 情况二：详情 -->
  <Descriptions v-if="isDetail" :data="formData" :schema="allSchemas.detailSchema">
    <template #categoryId="{ row }"> {{ formatCategoryName(row.categoryId) }}</template>
    <template #brandId="{ row }">
      {{ brandList.find((item) => item.id === row.brandId)?.name }}
    </template>
    <!-- <template #deliveryTemplateId="{ row }">
      {{ deliveryTemplateList.find((item) => item.id === row.deliveryTemplateId)?.name }}
    </template> -->
    <template #specType="{ row }">
      {{ row.specType ? '多规格' : '单规格' }}
    </template>
    <template #subCommissionType="{ row }">
      {{ row.subCommissionType ? '单独设置' : '默认设置' }}
    </template>
    <template #picUrl="{ row }">
      <el-image :src="row.picUrl" class="h-60px w-60px" @click="imagePreview(row.picUrl)" />
    </template>
    <template #sliderPicUrls="{ row }">
      <el-image
        v-for="(item, index) in row.sliderPicUrls"
        :key="index"
        :src="item.url"
        class="mr-10px h-60px w-60px"
        @click="imagePreview(row.sliderPicUrls)"
      />
    </template>
    <template #skus>
      <SkuList
        ref="skuDetailListRef"
        :is-detail="isDetail"
        :prop-form-data="formData"
        :propertyList="propertyList"
      />
    </template>
  </Descriptions>

  <!-- 商品属性添加 Form 表单 -->
  <ProductPropertyAddForm ref="attributesAddFormRef" :propertyList="propertyList" />
</template>
<script lang="ts" setup>
import { PropType } from 'vue'
import { isArray } from '@/utils/is'
import { copyValueToTarget } from '@/utils'
import { propTypes } from '@/utils/propTypes'
import { checkSelectedNode, defaultProps, handleTree, treeToString } from '@/utils/tree'
import { createImageViewer } from '@/components/ImageViewer'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { getPropertyList, RuleConfig, SkuList } from '@/views/mall/product/spu/components/index.ts'
import ProductAttributes from './ProductAttributes.vue'
import ProductPropertyAddForm from './ProductPropertyAddForm.vue'
import { basicInfoSchema } from './spu.data'
import type { Spu } from '@/api/mall/product/spu'
import * as ProductCategoryApi from '@/api/mall/product/category'
import * as ProductBrandApi from '@/api/mall/product/brand'
import * as ExpressTemplateApi from '@/api/mall/trade/delivery/expressTemplate'

defineOptions({ name: 'ProductSpuBasicInfoForm' })

// sku 相关属性校验规则
const ruleConfig: RuleConfig[] = [
  {
    name: 'stock',
    rule: (arg) => arg >= 1,
    message: '商品库存必须大于等于 1 ！！！'
  },
  {
    name: 'price',
    rule: (arg) => arg >= 0.01,
    message: '商品销售价格必须大于等于 0.01 元！！！'
  },
  {
    name: 'marketPrice',
    rule: (arg) => arg >= 0.01,
    message: '商品市场价格必须大于等于 0.01 元！！！'
  },
  {
    name: 'costPrice',
    rule: (arg) => arg >= 0.01,
    message: '商品成本价格必须大于等于 0.01 元！！！'
  }
]

// ====== 商品详情相关操作 ======
const { allSchemas } = useCrudSchemas(basicInfoSchema)
/** 商品图预览 */
const imagePreview = (args) => {
  const urlList = []
  if (isArray(args)) {
    args.forEach((item) => {
      urlList.push(item.url)
    })
  } else {
    urlList.push(args)
  }
  createImageViewer({
    urlList
  })
}

// ====== end ======

const message = useMessage() // 消息弹窗

const props = defineProps({
  propFormData: {
    type: Object as PropType<Spu>,
    default: () => {}
  },
  activeName: propTypes.string.def(''),
  isDetail: propTypes.bool.def(false) // 是否作为详情组件
})
const attributesAddFormRef = ref() // 添加商品属性表单
const productSpuBasicInfoRef = ref() // 表单 Ref
const propertyList = ref([]) // 商品属性列表
const skuListRef = ref() // 商品属性列表Ref
/** 调用 SkuList generateTableData 方法*/
const generateSkus = (propertyList) => {
  skuListRef.value.generateTableData(propertyList)
}
const formData = reactive<Spu>({
  name: '', // 商品名称
  categoryId: null, // 商品分类
  keyword: '', // 关键字
  unit: null, // 单位
  picUrl: '', // 商品封面图
  sliderPicUrls: [], // 商品轮播图
  introduction: '', // 商品简介
  // deliveryTemplateId: null, // 运费模版
  brandId: null, // 商品品牌
  specType: false, // 商品规格
  subCommissionType: false, // 分销类型
  skus: []
})
const rules = reactive({
  name: [required],
  categoryId: [required],
  keyword: [required],
  unit: [required],
  introduction: [required],
  picUrl: [required],
  sliderPicUrls: [required],
  // deliveryTemplateId: [required],
  brandId: [required],
  specType: [required],
  subCommissionType: [required]
})

/**
 * 将传进来的值赋值给 formData
 */
watch(
  () => props.propFormData,
  (data) => {
    if (!data) {
      return
    }
    copyValueToTarget(formData, data)
    formData.sliderPicUrls = data['sliderPicUrls']?.map((item) => ({
      url: item
    }))
    propertyList.value = getPropertyList(data)
  },
  {
    immediate: true
  }
)

/**
 * 表单校验
 */
const emit = defineEmits(['update:activeName'])
const validate = async () => {
  // 校验 sku
  skuListRef.value.validateSku()
  // 校验表单
  if (!productSpuBasicInfoRef) return
  return await unref(productSpuBasicInfoRef).validate((valid) => {
    if (!valid) {
      message.warning('商品信息未完善！！')
      emit('update:activeName', 'basicInfo')
      // 目的截断之后的校验
      throw new Error('商品信息未完善！！')
    } else {
      // 校验通过更新数据
      Object.assign(props.propFormData, formData)
    }
  })
}
defineExpose({ validate })

/** 分销类型 */
const changeSubCommissionType = () => {
  // 默认为零，类型切换后也要重置为零
  for (const item of formData.skus) {
    item.firstBrokeragePrice = 0
    item.secondBrokeragePrice = 0
    item.integralPrice = 0
  }
}

/** 选择规格 */
const onChangeSpec = () => {
  // 重置商品属性列表
  propertyList.value = []
  // 重置sku列表
  formData.skus = [
    {
      price: 0,
      marketPrice: 0,
      integralPrice: 0,
      costPrice: 0,
      barCode: '',
      picUrl: '',
      stock: 0,
      weight: 0,
      volume: 0,
      firstBrokeragePrice: 0,
      secondBrokeragePrice: 0
    }
  ]
}

const categoryList = ref([]) // 分类树
/** 获取分类的节点的完整结构 */
const formatCategoryName = (categoryId) => {
  return treeToString(categoryList.value, categoryId)
}

const brandList = ref([]) // 精简商品品牌列表
// const deliveryTemplateList = ref([]) // 运费模版
onMounted(async () => {
  // 获得分类树
  const data = await ProductCategoryApi.getCategoryList({})
  categoryList.value = handleTree(data, 'id', 'parentId')
  // 获取商品品牌列表
  brandList.value = await ProductBrandApi.getSimpleBrandList()
  // 获取运费模版
  // deliveryTemplateList.value = await ExpressTemplateApi.getSimpleTemplateList()
})
</script>

<style>
.el-select {
  width: 100%;
}
</style>
