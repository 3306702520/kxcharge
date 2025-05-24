package com.hanxing.kxcharge.module.operations.enums;

import com.hanxing.kxcharge.framework.common.exception.ErrorCode;

/**
 * operations 错误码枚举类
 * <p>
 * operations 系统，使用 1_010-000-000 段
 */
public interface ErrorCodeConstants {


    // ========== 计费模板==========
    ErrorCode COST_TEMPLATE_NOT_EXISTS = new ErrorCode(1_010_030_000, "计费模板不存在");
    ErrorCode COST_TEMPLATE_DELETION_FAILURE = new ErrorCode(1_010_030_001, "该计费模板存在关联的桩体，无法删除！");
    ErrorCode COST_TEMPLATE_DELETION_FAILURE_STATION = new ErrorCode(1_010_030_002, "该计费模板存在关联的站点，无法删除！");

    // ========== 计费模板价格类别 ==========
    ErrorCode COST_TEMPLATE_PRICE_TYPE_NOT_EXISTS = new ErrorCode(1_010_031_000, "计费模板价格类别不存在");


    // ========== 计费模板价格时段 ==========
    ErrorCode COST_TEMPLATE_PRICE_NOT_EXISTS = new ErrorCode(1_010_032_000, "计费模板价格时段不存在");

    // ========== 站点信息  ==========
    ErrorCode STATION_NOT_EXISTS = new ErrorCode(1_010_033_000, "站点信息不存在");
    ErrorCode STATION_UNABLE_DELETE = new ErrorCode(1_010_033_000, "站点存在有效的桩信息，无法删除！");


    // ========== 设备型号管理 ==========
    ErrorCode EQUIPMENT_MODEL_NOT_EXISTS = new ErrorCode(1_010_034_000, "设备型号管理不存在");
    // ========== 设备品牌管理 ==========
    ErrorCode EQUIPMENT_BRAND_NOT_EXISTS = new ErrorCode(1_010_035_000, "设备品牌管理不存在");
    ErrorCode EQUIPMENT_BRAND_NOT_DELETE = new ErrorCode(1_010_035_001, "设备品牌无法删除,已经有电桩关联");

    // ========== 电桩管理 ==========
    ErrorCode EQUIPMENT_NOT_EXISTS = new ErrorCode(1_010_036_000, "电桩管理不存在");
    ErrorCode EQUIPMENT_ALREADY_EXISTED = new ErrorCode(1_010_036_000, "电桩编号/电桩名称已存在");
    // ========== 电桩管理 ==========
    ErrorCode EQUIPMENT_CONNECTOR_NOT_EXISTS = new ErrorCode(1_010_037_000, "电枪管理不存在");
    ErrorCode EQUIPMENT_CONNECTOR_NOT_ENABLED = new ErrorCode(1_010_037_001, "电枪未启用");
    ErrorCode EQUIPMENT_CONNECTOR_OFF_LINE = new ErrorCode(1_010_037_002, "电枪离线/故障");
    ErrorCode EQUIPMENT_CONNECTOR_INSERT_ARMS = new ErrorCode(1_010_037_002, "电枪未归位");
    ErrorCode EQUIPMENT_CONNECTOR_WORKING_POSITION = new ErrorCode(1_010_037_002, "电枪工作状态不是空闲，无法启动充电");


    // ========== 订单管理 ==========
    ErrorCode ORDER_NOT_EXISTS = new ErrorCode(1_010_038_000, "订单管理不存在");
    ErrorCode ORDER_ADD_FAILURE = new ErrorCode(1_010_038_001, "订单信息新增失败");
    ErrorCode ORDER_NOT_END = new ErrorCode(1_010_038_002, "已经有正在充电订单,不能再次发起");
    ErrorCode MONEY_NOT_FIND = new ErrorCode(1_010_038_003, "找不到钱包");
    ErrorCode MONEY_NOT_ENOUGH = new ErrorCode(1_010_038_004, "金额不够，不能充电");
    ErrorCode ORDER_NOT_REPEAT_START = new ErrorCode(1_010_038_005, "订单不能重复发起");
    ErrorCode ORDER_NOT_HANDLER = new ErrorCode(1_010_038_006, "尚有订单未处理完成");
    ErrorCode ORDER_ERROR_ORDER = new ErrorCode(1_010_038_007, "您有异常订单,工作人员正在给您处理中,请等待");
    ErrorCode ORDER_STATUS_NOT_COMPLETED = new ErrorCode(1_010_038_008, "订单状态不是已经完成");
    ErrorCode ORDER_NOT_INTERCONNECTED = new ErrorCode(1_010_038_009, "订单类型不是互联互通");

    // ========== 订单详情管理 ==========
    ErrorCode ORDER_DETAILS_NOT_EXISTS = new ErrorCode(1_010_039_000, "订单详情管理不存在");

    // ========== Coupon 相关 1-010-003-000 ============
    ErrorCode COUPON_NO_MATCH_SPU = new ErrorCode(1_010_003_000, "优惠劵没有可使用的商品！");
    ErrorCode COUPON_NO_MATCH_MIN_PRICE = new ErrorCode(1_010_003_001, "所结算的商品中未满足使用的金额");

    // ========== 优惠劵模板 1-010-004-000 ==========
    ErrorCode COUPON_TEMPLATE_NOT_EXISTS = new ErrorCode(1_010_004_000, "优惠劵模板不存在");
    ErrorCode COUPON_TEMPLATE_TOTAL_COUNT_TOO_SMALL = new ErrorCode(1_010_004_001, "发放数量不能小于已领取数量({})");
    ErrorCode COUPON_TEMPLATE_NOT_ENOUGH = new ErrorCode(1_010_004_002, "当前剩余数量不够领取");
    ErrorCode COUPON_TEMPLATE_USER_ALREADY_TAKE = new ErrorCode(1_010_004_003, "用户已领取过此优惠券");
    ErrorCode COUPON_TEMPLATE_EXPIRED = new ErrorCode(1_010_004_004, "优惠券已过期");
    ErrorCode COUPON_TEMPLATE_CANNOT_TAKE = new ErrorCode(1_010_004_005, "领取方式不正确");

    // ========== 优惠劵 1-010-005-000 ==========
    ErrorCode COUPON_NOT_EXISTS = new ErrorCode(1_010_005_000, "优惠券不存在");
    ErrorCode COUPON_DELETE_FAIL_USED = new ErrorCode(1_010_005_001, "回收优惠劵失败，优惠劵已被使用");
    ErrorCode COUPON_STATUS_NOT_UNUSED = new ErrorCode(1_010_005_002, "优惠劵不处于待使用状态");
    ErrorCode COUPON_VALID_TIME_NOT_NOW = new ErrorCode(1_010_005_003, "优惠券不在使用时间范围内");
    ErrorCode COUPON_STATUS_NOT_USED = new ErrorCode(1_010_005_004, "优惠劵不是已使用状态");

}
