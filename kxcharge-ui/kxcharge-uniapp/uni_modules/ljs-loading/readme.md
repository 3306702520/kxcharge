# 方便您的同时，请五星、收藏，让好的东西照亮更多深渊中负重前行的代码人。
# 创作不易，在您方便之际，赞赏作者，我们会更有动力继续下去。

# 简介
ljs-loading，美观的loading组件，让你的产品提高N++个Level。

# 快速开始
### 1.使用 uni_modules 安装（推荐）
使用 uni_modules 方式安装组件库，可以直接通过插件市场导入，通过右键菜单快速更新组件，不需要引用、注册，直接在页面中使用 ljs-loading 组件。

### 主参数

参数  | 类型| 必填项 | 默认值 | 说明
---- | ----- | ------ | ------  | :------ 
speed | Number | × | 3 | 转速，单位秒
gaussian | Number | × | 1 | 高斯模糊
bgOpacity | Number | × | 0.5 | 背景透明度，0~1的数字。
textColor | String | × | #FFF | solt字体颜色。
插槽 | slot | × | | 插槽，可插入文字显示内容。
 
### 方法

 参数  | 类型| 解释
 ---- | ----- | :------ 

### 快速应用
###### 官方示例
```
<template>
	<view class="content">		
		<ljs-loading v-if="loading">加载中....</ljs-loading>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				loading: false
			}
		},
		onLoad() {
			this.loading = true
			setTimeout(() => {
				this.loading = false
			}, 3000)
		},
		methods: {
			
		}
	}
</script>
```

# 贡献代码
龙九山。有任何问题，请在平台留言，在手头宽裕得情况下，我会尽快修复问题。

