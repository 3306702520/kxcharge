<template>
  <!--  -->
  <view class="u-page__item" v-if="tabbar?.list?.length > 0">
    <su-tabbar :value="path" :fixed="true" :placeholder="true" :safeAreaInsetBottom="true" :inactiveColor="tabbar.color"
      :activeColor="tabbar.selectedColor" midTabBar :customStyle="tabbarStyle">
      <su-tabbar-item v-for="item in tabbar.list" :key="item.text" :text="item.text" :name="item.pagePath"
        :isCenter="item.center" :centerImage="'/'+item.iconPath" @tap="onTap(item)">
        <template v-slot:active-icon>
          <image class="u-page__item__slot-icon" :src="'/'+item.selectedIconPath"></image>
        </template>
        <template v-slot:inactive-icon>
          <image class="u-page__item__slot-icon" :src="'/'+item.iconPath"></image>
        </template>
      </su-tabbar-item>
    </su-tabbar>
  </view>
</template>

<script setup>
  import {
    tabBar
  } from '../../../pages.json'

  // const tabbar = computed(() => {
  //   return sheep.$store('app').template.basic?.tabbar;
  // });

  const getTabbarCenter = (index) => {
    // if (unref(tabbar).mode !== 2) return false;
    return tabBar.list.length % 2 > 0 ?
      Math.ceil(tabBar.list.length / 2) === index + 1 :
      false;
  };

  const tabbar = reactive({
    ...tabBar,
    list: tabBar.list.map((el, i) => {
      return {
        ...el,
        center: getTabbarCenter(i)
      }
    })
  })

  const tabbarStyle = computed(() => {
    // const backgroundStyle = tabbar.value.style;
    // if (backgroundStyle.bgType === 'color') {
    //   return {
    //     background: backgroundStyle.bgColor
    //   };
    // }
    // if (backgroundStyle.bgType === 'img')
    //   return {
    //     background: `url(${sheep.$url.cdn(
    //       backgroundStyle.bgImg,
    //     )}) no-repeat top center / 100% auto`,
    //   };
  });

  const onTap = (item) => {
    if (item.center) {
      uni.scanCode({
        success(res) {
          console.log(res)
        },
        fail(err) {
          console.log('扫码失败', err)
        }
      })
      return
    }
    sheep.$router.go('/' + item.pagePath)
  }

  const props = defineProps({
    path: String,
    default: '',
  });
</script>

<style lang="scss">
  .u-page {
    padding: 0;

    &__item {
      &__title {
        color: var(--textSize);
        background-color: #fff;
        padding: 15px;
        font-size: 15px;

        &__slot-title {
          color: var(--textSize);
          font-size: 14px;
        }
      }

      &__slot-icon {
        width: 40rpx;
        height: 40rpx;
      }
    }
  }
</style>