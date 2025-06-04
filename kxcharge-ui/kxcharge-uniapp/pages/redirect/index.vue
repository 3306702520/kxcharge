<template>
  <s-layout navbar="normal" onShareAppMessage>
  </s-layout>
</template>
<script setup>
  import tenantId from '@/sheep/config/tenantId';

  onLoad(async () => {
    uni.showLoading({
      title: '加载中...',
      mask: true
    })
    Promise.all([tenantId.getTenantByWebsite(), sheep.$store('user').getLocation()])
      .then(() => {
        // 加载Shopro底层依赖
        ShoproInit();
        sheep.$store('dict').getDictData()
        uni.hideLoading()
        uni.switchTab({
          url: '/pages/index/index'
        })
      }, err => {
        uni.hideLoading()
        console.log(err)
      })
  })
</script>