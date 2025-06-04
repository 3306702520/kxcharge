<!-- 
  全页面的Loading样式
-->
<template>
	<view 
		class="ljs-loading"
		id="ljs-loading"
		ref="ljsLoading"
		:style="{
			'--speed': speed,
			'--gaussian': gaussian,
			'--bgopacity': bgOpacity,
			'--textcolor': textColor
		}">
		<view class="text">
			<slot></slot>
		</view>
		<view class="g-container">
			<view class="g-circle"></view>
			<view class="g-bubbles">
				<view class="g-bubble" v-for="index in 200" :key="index"></view>
			</view>
		</view>
	</view>
</template>

<script>
import index from './index.js';
export default index;
</script>

<style lang="scss" scoped>
@import "./index.scss";
</style>
