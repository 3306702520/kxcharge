package com.hanxing.kxcharge.module.operations.dal.redis.realtimeorder;

import com.hanxing.kxcharge.framework.common.util.json.JsonUtils;
import com.hanxing.kxcharge.module.operations.dal.dataobject.realtimeorder.RealTimeOrderDO;
import com.hanxing.kxcharge.module.operations.dal.redis.RedisKeyConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Repository
public class RealTimeOrderDao {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public RealTimeOrderDO get(String transactionSerialNo){
        String redisKey = formatKey(transactionSerialNo);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(redisKey), RealTimeOrderDO.class);
    }

    /**
     * 入库操作,超时时间300秒
     * @param realTimeOrderDO do类
     */
    public void set(RealTimeOrderDO realTimeOrderDO){
        String redisKey = formatKey(realTimeOrderDO.getTransactionSerialNo());
        stringRedisTemplate.opsForValue().set(redisKey,
                JsonUtils.toJsonString(realTimeOrderDO),
                300,
                TimeUnit.SECONDS);
    }



    private static String formatKey(String transactionSerialNo){
        return String.format(RedisKeyConstants.CHARGE_ORDER, transactionSerialNo);
    }

}
