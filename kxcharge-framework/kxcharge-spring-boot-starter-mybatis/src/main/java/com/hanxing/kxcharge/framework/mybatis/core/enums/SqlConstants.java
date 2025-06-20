package com.hanxing.kxcharge.framework.mybatis.core.enums;

import com.baomidou.mybatisplus.annotation.DbType;

/**
 * SQL相关常量类
 *
 * @author 航兴新能源
 */
public class SqlConstants {

    /**
     * 数据库的类型
     */
    public static DbType DB_TYPE;

    public static void init(DbType dbType) {
        DB_TYPE = dbType;
    }

}
