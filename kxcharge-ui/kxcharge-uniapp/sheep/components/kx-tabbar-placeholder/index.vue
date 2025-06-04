<template>
  <view :style="{height:height+'px',width:'100%'}">

  </view>
</template>

<script setup>
  const height = computed(() => {
    let height = sheep.$store('sys').tabbarPlaceholderHeight
    //#ifdef H5
    if (height === 0) {
      height = 50
    }
    //#endif
    //#ifndef H5
    height = 0
    //#endif
    return height + 30
  });
</script>