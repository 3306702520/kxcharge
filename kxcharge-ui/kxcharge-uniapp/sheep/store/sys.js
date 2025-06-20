import {
  defineStore
} from 'pinia';
import app from './app';

const sys = defineStore({
  id: 'sys',
  state: () => ({
    theme: '', // 主题,
    mode: 'light', // 明亮模式、暗黑模式（暂未支持）
    modeAuto: false, // 跟随系统
    fontSize: 1, // 设置默认字号等级(0-4)
    tabbarPlaceholderHeight: 0
  }),
  getters: {},
  actions: {
    setTheme(theme = '') {
      if (theme === '') {
        this.theme = app().template?.basic.theme || 'blue';
      } else {
        this.theme = theme;
      }
    },
    setTabbarPlaceholderHeight(height) {
      this.tabbarPlaceholderHeight = height
    }
  },
  persist: {
    enabled: true,
    strategies: [{
      key: 'sys-store',
    }, ],
  },
});

export default sys;