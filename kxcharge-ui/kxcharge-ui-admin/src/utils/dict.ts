/**
 * 数据字典工具类
 */
import { useDictStoreWithOut } from '@/store/modules/dict'
import { ElementPlusInfoType } from '@/types/elementPlus'

const dictStore = useDictStoreWithOut()

/**
 * 获取 dictType 对应的数据字典数组
 *
 * @param dictType 数据类型
 * @returns {*|Array} 数据字典数组
 */
export interface DictDataType {
  dictType: string
  label: string
  value: string | number | boolean
  colorType: ElementPlusInfoType | ''
  cssClass: string
}

export const getDictOptions = (dictType: string) => {
  return dictStore.getDictByType(dictType) || []
}

export const getIntDictOptions = (dictType: string): DictDataType[] => {
  const dictOption: DictDataType[] = []
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: parseInt(dict.value + '')
    })
  })
  return dictOption
}

export const getStrDictOptions = (dictType: string) => {
  const dictOption: DictDataType[] = []
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + ''
    })
  })
  return dictOption
}

export const getBoolDictOptions = (dictType: string) => {
  const dictOption: DictDataType[] = []
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + '' === 'true'
    })
  })
  return dictOption
}

/**
 * 获取指定字典类型的指定值对应的字典对象
 * @param dictType 字典类型
 * @param value 字典值
 * @return DictDataType 字典对象
 */
export const getDictObj = (dictType: string, value: any): DictDataType | undefined => {
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  for (const dict of dictOptions) {
    if (dict.value === value + '') {
      return dict
    }
  }
}

/**
 * 获得字典数据的文本展示
 *
 * @param dictType 字典类型
 * @param value 字典数据的值
 * @return 字典名称
 */
export const getDictLabel = (dictType: string, value: any): string => {
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  const dictLabel = ref('')
  dictOptions.forEach((dict: DictDataType) => {
    if (dict.value === value + '') {
      dictLabel.value = dict.label
    }
  })
  return dictLabel.value
}

/**
 * 获得对象和对应value值获取label展示
 *
 * @param objOptions 对比对象
 * @param value 表格数值
 * @return label
 */
export const getObjLabel = (objOptions: object, value: any): string => {
  // @ts-ignore
  const result: string =  objOptions.find((item: any) => item.value == value + "")?.label;
  return result;
}


export enum DICT_TYPE {
  JUMP_TYPE = 'jump_type',
  USER_TYPE = 'user_type',
  COMMON_STATUS = 'common_status',
  SYSTEM_TENANT_PACKAGE_ID = 'system_tenant_package_id',
  TERMINAL = 'terminal', // 终端
  OPERATOR_STATUS = 'operator_status', // 运营商状态
  COST_TEMPLATE_USED = 'cost_template_used', // 计费模板状态
  ELECTRIC_TYPE = 'electric_type',
  STASTION_STATUS = 'station_status',
  ENABLE_START = 'enable_start',
  NETWORK_LINK_TYPE = 'network_link_type', // 网络连接类型
  COMMUNICATION_OPERATOR = 'communication_operator', // 通讯运营商
  PARK_LOCK = 'park_lock', // 有无地锁
  PRICE_SET = 'price_set',
  EQUIPMENT_STANDARD = 'equipment_standard',
  IS_ONLINE = 'is_online',
  WORK_STATUS = 'work_status',
  COMPANY_STATUS = 'company_status',
  COMPANY_RECORD_BIZ_TYPE = 'company_record_biz_type',
  PLATE_TYPE = 'plate_type',
  CHARGING_SOURCE = 'charging_source',
  CHARGING_WAY = 'charging_way',
  ORDER_TYPE = 'order_type',
  STOP_CHARGE_TYPE = 'stop_charge_type',
  CHARGING_PERIOD = 'charging_period',
  // ========== SYSTEM 模块 ==========
  SYSTEM_USER_SEX = 'system_user_sex',
  SYSTEM_MENU_TYPE = 'system_menu_type',
  SYSTEM_ROLE_TYPE = 'system_role_type',
  SYSTEM_DATA_SCOPE = 'system_data_scope',
  SYSTEM_NOTICE_TYPE = 'system_notice_type',
  SYSTEM_OPERATE_TYPE = 'system_operate_type',
  SYSTEM_LOGIN_TYPE = 'system_login_type',
  SYSTEM_LOGIN_RESULT = 'system_login_result',
  SYSTEM_SMS_CHANNEL_CODE = 'system_sms_channel_code',
  SYSTEM_SMS_TEMPLATE_TYPE = 'system_sms_template_type',
  SYSTEM_SMS_SEND_STATUS = 'system_sms_send_status',
  SYSTEM_SMS_RECEIVE_STATUS = 'system_sms_receive_status',
  SYSTEM_ERROR_CODE_TYPE = 'system_error_code_type',
  SYSTEM_OAUTH2_GRANT_TYPE = 'system_oauth2_grant_type',
  SYSTEM_MAIL_SEND_STATUS = 'system_mail_send_status',
  SYSTEM_NOTIFY_TEMPLATE_TYPE = 'system_notify_template_type',
  SYSTEM_SOCIAL_TYPE = 'system_social_type',

  // ========== INFRA 模块 ==========
  INFRA_BOOLEAN_STRING = 'infra_boolean_string',
  INFRA_JOB_STATUS = 'infra_job_status',
  INFRA_JOB_LOG_STATUS = 'infra_job_log_status',
  INFRA_API_ERROR_LOG_PROCESS_STATUS = 'infra_api_error_log_process_status',
  INFRA_CONFIG_TYPE = 'infra_config_type',
  INFRA_CODEGEN_TEMPLATE_TYPE = 'infra_codegen_template_type',
  INFRA_CODEGEN_FRONT_TYPE = 'infra_codegen_front_type',
  INFRA_CODEGEN_SCENE = 'infra_codegen_scene',
  INFRA_FILE_STORAGE = 'infra_file_storage',

  // ========== BPM 模块 ==========
  BPM_MODEL_CATEGORY = 'bpm_model_category',
  BPM_MODEL_FORM_TYPE = 'bpm_model_form_type',
  BPM_TASK_ASSIGN_RULE_TYPE = 'bpm_task_assign_rule_type',
  BPM_PROCESS_INSTANCE_STATUS = 'bpm_process_instance_status',
  BPM_PROCESS_INSTANCE_RESULT = 'bpm_process_instance_result',
  BPM_TASK_ASSIGN_SCRIPT = 'bpm_task_assign_script',
  BPM_OA_LEAVE_TYPE = 'bpm_oa_leave_type',

  // ========== PAY 模块 ==========
  PAY_CHANNEL_CODE = 'pay_channel_code', // 支付渠道编码类型
  PAY_ORDER_STATUS = 'pay_order_status', // 商户支付订单状态
  PAY_REFUND_STATUS = 'pay_refund_status', // 退款订单状态
  PAY_NOTIFY_STATUS = 'pay_notify_status', // 商户支付回调状态
  PAY_NOTIFY_TYPE = 'pay_notify_type', // 商户支付回调状态
  PAY_TRANSFER_STATUS = 'pay_transfer_status', // 转账订单状态
  PAY_TRANSFER_TYPE = 'pay_transfer_type', // 转账订单状态

  // ========== MP 模块 ==========
  MP_AUTO_REPLY_REQUEST_MATCH = 'mp_auto_reply_request_match', // 自动回复请求匹配类型
  MP_MESSAGE_TYPE = 'mp_message_type', // 消息类型

  // ========== MALL - 会员模块 ==========
  MEMBER_POINT_BIZ_TYPE = 'member_point_biz_type', // 积分的业务类型
  MEMBER_EXPERIENCE_BIZ_TYPE = 'member_experience_biz_type', // 会员经验业务类型

  // ========== MALL - 商品模块 ==========
  PRODUCT_UNIT = 'product_unit', // 商品单位
  PRODUCT_SPU_STATUS = 'product_spu_status', //商品状态
  PROMOTION_TYPE_ENUM = 'promotion_type_enum', // 营销类型枚举

  // ========== MALL - 交易模块 ==========
  EXPRESS_CHARGE_MODE = 'trade_delivery_express_charge_mode', //快递的计费方式
  TRADE_AFTER_SALE_STATUS = 'trade_after_sale_status', // 售后 - 状态
  TRADE_AFTER_SALE_WAY = 'trade_after_sale_way', // 售后 - 方式
  TRADE_AFTER_SALE_TYPE = 'trade_after_sale_type', // 售后 - 类型
  TRADE_ORDER_TYPE = 'trade_order_type', // 订单 - 类型
  TRADE_ORDER_STATUS = 'trade_order_status', // 订单 - 状态
  TRADE_ORDER_ITEM_AFTER_SALE_STATUS = 'trade_order_item_after_sale_status', // 订单项 - 售后状态
  TRADE_DELIVERY_TYPE = 'trade_delivery_type', // 配送方式
  BROKERAGE_ENABLED_CONDITION = 'brokerage_enabled_condition', // 分佣模式
  BROKERAGE_BIND_MODE = 'brokerage_bind_mode', // 分销关系绑定模式
  BROKERAGE_BANK_NAME = 'brokerage_bank_name', // 佣金提现银行
  BROKERAGE_WITHDRAW_TYPE = 'brokerage_withdraw_type', // 佣金提现类型
  BROKERAGE_RECORD_BIZ_TYPE = 'brokerage_record_biz_type', // 佣金业务类型
  BROKERAGE_RECORD_STATUS = 'brokerage_record_status', // 佣金状态
  BROKERAGE_WITHDRAW_STATUS = 'brokerage_withdraw_status', // 佣金提现状态

  // ========== MALL - 营销模块 ==========
  PROMOTION_DISCOUNT_TYPE = 'promotion_discount_type', // 优惠类型
  PROMOTION_PRODUCT_SCOPE = 'promotion_product_scope', // 营销的商品范围
  PROMOTION_COUPON_TEMPLATE_VALIDITY_TYPE = 'promotion_coupon_template_validity_type', // 优惠券模板的有限期类型
  PROMOTION_COUPON_STATUS = 'promotion_coupon_status', // 优惠券的状态
  PROMOTION_COUPON_TAKE_TYPE = 'promotion_coupon_take_type', // 优惠券的领取方式
  PROMOTION_ACTIVITY_STATUS = 'promotion_activity_status', // 优惠活动的状态
  PROMOTION_CONDITION_TYPE = 'promotion_condition_type', // 营销的条件类型枚举
  PROMOTION_BARGAIN_RECORD_STATUS = 'promotion_bargain_record_status', // 砍价记录的状态
  PROMOTION_COMBINATION_RECORD_STATUS = 'promotion_combination_record_status', // 拼团记录的状态
  PROMOTION_BANNER_POSITION = 'promotion_banner_position', // banner 定位

  // ========== CRM - 客户管理模块 ==========
  CRM_RECEIVABLE_CHECK_STATUS = 'crm_receivable_check_status',
  CRM_RETURN_TYPE = 'crm_return_type',
  CRM_CUSTOMER_INDUSTRY = 'crm_customer_industry',
  CRM_CUSTOMER_LEVEL = 'crm_customer_level',
  CRM_CUSTOMER_SOURCE = 'crm_customer_source',
  CRM_PRODUCT_STATUS = 'crm_product_status',

  // ============ 电站模块 ====================
  STATION_PROPERTY = 'station_property',
  STATION_EIGENVALUE = 'station_eigenvalue',
  STATION_LOCATION = 'station_location',
  STATION_TYPE = 'station_type',
  CONSTRUCTION_SITE = 'construction_site',
  OCCUPATION_COST = 'occupation_cost',
  BARRIER_GATE = 'barrier_gate',
  SERVICE_SETUP = 'service_setup',
  BASE_SETUP = 'base_setup',

  // ============ 互联互通秘钥模块 ====================
  INTERCONNECTED_TYPE = 'interflow_type',
  INTERCONNECTED_STATUS = 'interflow_status',
  SECONDARY_PRICING_TYPE = 'secondary_pricing_type',
  PUSH_PRICE = 'push_price',
  INTERCONNECTION_STATION_STATUS = 'interconnection_station_status',

  // ============ 会员中心-意见反馈 ====================
  FEEDBACK_TYPE = 'feedback_type',
  FEEDBACK_STATUS = 'feedback_status',
  PAGE_RULE = 'page_rule',

  // ============ 自动售货机模块 =======================
  VENDING_ORDER_PAY_TYPE = 'vending_order_pay_type',
  VENDING_ORDER_STATUS = 'vending_order_status',

}
