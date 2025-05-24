import type { App } from 'vue'
import { hasRole } from './permission/hasRole'
import { hasPermi } from './permission/hasPermi'
import { noSpace } from './permission/noSpace'

/**
 * 导出指令：v-xxx
 * @methods hasRole 用户权限，用法: v-hasRole
 * @methods hasPermi 按钮权限，用法: v-hasPermi
 * @methods noSpace 禁输空格，用法: v-no-space
 */
export const setupAuth = (app: App<Element>) => {
  hasRole(app)
  hasPermi(app)
  noSpace(app)
}
