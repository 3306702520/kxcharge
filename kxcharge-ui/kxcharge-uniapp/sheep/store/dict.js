import {
  defineStore
} from 'pinia';
import {
  getDictData
} from '../api/charge/system'


const useDictStore = defineStore('dict', {
  state: () => {
    return {
      'station_type': [],
      'station_eigenvalue': []
    }
  },
  actions: {
    getDictData() {
      Object.keys(this.$state).forEach(type => {
        getDictData({
          type
        }).then(res => {
          this[type] = res.data
        })
      })
    }
  }
})

export default useDictStore