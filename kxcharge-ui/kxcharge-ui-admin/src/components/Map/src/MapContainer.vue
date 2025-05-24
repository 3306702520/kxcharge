<template>
  <div class="main">
    <!--查询列表,高德地图api绑定id-->
    <div class="list" id="list"></div>
    <!--地图,需要设置宽高-->
    <div class="map" id="gd_map_d"></div>
  </div>
</template>

<script setup>
import AMapLoader from '@amap/amap-jsapi-loader'

const props = defineProps({
  center: {
    type: Array,
    default: null
  },
  zoom: {
    type: Number,
    default: 14
  },
  addressLocation: {
    type: Object,
    default: null
  }
});

const emit = defineEmits(['address', 'geolocation']);

watch(() => props.addressLocation, (newValue) => {
  if (newValue && newValue?.lng && newValue?.lat && MyMap) { // 确保新值存在
    addMarks(newValue.lng, newValue.lat)
      // map.setFitView();
  }
});

// 地图的key和秘钥
const mapKey = '64dba58e982370d968123002b9abbbda'
const mapSecretKey = 'f31a4ae101fa9f26713e80f07d5496ce'
window._AMapSecurityConfig = {  // 配置地图加载时所需要的安全配置信息
  securityJsCode: mapSecretKey
}

let geocoder = null;
let MyMap = null;
let map = null;
const marks = reactive([])

function initMap() {
  try {
    AMapLoader.load({
      key: mapKey, // 申请好的Web端开发者Key，首次调用 load 时必填
      version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
      plugins: ['AMap.PlaceSearch', 'AMap.Geocoder'], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
    })
    .then((AMap) => {
      MyMap = AMap
      map = new AMap.Map("gd_map_d", {
        // 设置地图容器id
        viewMode: "3D", // 是否为3D地图模式
        zoom: props.zoom, // 初始化地图级别
        center: props.center, // 初始化地图中心点位置
      });
      geocoder = new AMap.Geocoder();  // 创建地址解析器
      const { lng, lat } = props.addressLocation;
      if (lat && lng) {
        map.remove(marks)
        addMarks(lng, lat);
      }
      // 点击事件
      map.on('click', (result) => {
        map.remove(marks);
        const { lnglat } = result
        emit('geolocation', {
          longitude: lnglat.lng,
          latitude: lnglat.lat
        }) 
        addMarks(lnglat.lng, lnglat.lat)
      })
    })
    .catch((e) => {
      console.log( 'e', e);
    });
  } catch (error) {
    console.log('error', error);
    
  }
  
}

const addMarks = (lng, lat) => {
  const marker = new MyMap.Marker({
    position: [lng, lat],   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
  });
  marks.push(marker);
  map.add(marker)
  getAddress(lng, lat) 
  map.setFitView();
  return marks
}

const searchAdress = (address) => {
  const placeSearch = new MyMap.PlaceSearch({
    pageSize: 3, // 单页显示结果条数
    pageIndex: 1, // 页码
    city: "010", // 兴趣点城市
    citylimit: false,  //是否强制限制在设置的城市内搜索
    map: map, // 展现结果的地图实例
    panel: 'list', // 结果列表将在此容器中进行展示。
    autoFitView: false // 是否自动调整地图视野使绘制的 Marker点都处于视口的可见范围
  })
  placeSearch.search(address, (status, result) => {
    // 将视角定位到搜索的位置
    map.setFitView();
  });
  placeSearch.on('selectChanged', (result) => {
    // 处理选中的搜索结果
    const { data } = result.selected
    const address = data.pname + data.cityname + data.adname + data.address + '-' + data.name
    emit('address', address)
    emit('geolocation',{
      longitude: data.entr_location.lng,
      latitude: data.entr_location.lat
    })
    map.setFitView();

  })
}

// 通过经纬获取地址信息
const getAddress = (lng, lat) => {
  geocoder.getAddress([lng, lat], (status, result) => {
    emit('address', result.regeocode?.formattedAddress ?? '')
  })
}

//mapinit
onMounted(() => {
  initMap();
});

onUnmounted(() => {
  map?.destroy();
});

defineExpose({
  searchAdress  //将事件暴露出去
})
</script>
<style scoped lang="scss">
.main {
  width: 100%;
  height: 350px;
  position: relative;

  .map {
    width: 100%;
    height: 100%;
  }
  // .amap_lib_placeSearch_poi {
  //   display: none;
  // }
  .list {
    position: absolute;
    top: 10px;
    right: 10px;
    height: 300px;
    width: 240px;
    z-index: 999;
 
    .li {
      line-height: 25px;
 
    }
  }

}
</style>
