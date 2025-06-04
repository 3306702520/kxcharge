<template>
  <view class="flex align-center" :style="style">
    <uni-search-bar @confirm="onConfirm" @input="onInput" bgColor="#fff" textColor="#555" style="flex:1"
      placeholder="输入电站名称/地点" v-bind="$attrs" />
    <view class="scan-code" @click="onScan">
      <image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/16424@2x.png" mode="aspectFit"></image>
    </view>
  </view>
</template>

<script setup>
  const emit = defineEmits(['input', 'confirm', 'scan'])

  const props = defineProps({
    scroll: {
      type: Boolean,
      default: false
    }
  })

  // onPageScroll
  const {
    scrollTop
  } = usePageScroll()

  const style = computed(() => {
    return props.scroll ? {
      top: sheep.$platform.navbar + 'px',
      backgroundColor: scrollTop.value > 0 ? '#f5f9ff' : 'transparent',
      position: 'sticky',
      zIndex: 100
    } : null
  })

  const onConfirm = val => {
    emit('confirm', val)
  }

  const onInput = val => {
    emit('input', val)
  }

  const onScan = () => {
    emit('scan')
  }
</script>

<style lang="scss" scoped>
  image {
    width: 100%;
    height: 100%;
    vertical-align: middle;
  }

  .scan-code {
    width: 68rpx;
    height: 68rpx;
    background: #FFFFFF;
    border-radius: 20rpx 20rpx 20rpx 20rpx;
    margin-right: 20rpx;
    padding: 18rpx;
  }
</style>