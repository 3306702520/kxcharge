package com.hanxing.kxcharge.module.operations.dal.redis.onlinequipmentconnector;

import com.alibaba.fastjson.JSONObject;
import com.hanxing.kxcharge.module.operations.dal.dataobject.onlinequipmentconnector.OnlineEquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.redis.RedisKeyConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class OnlineEquipmentConnectorDao {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public <T> T get(String equipmentConnectorCode, String hkey) {
        String redisKey = formatKey(equipmentConnectorCode);
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(redisKey, hkey);
    }

    public OnlineEquipmentConnectorDO getObject(String equipmentConnectorCode) {
        String redisKey = formatKey(equipmentConnectorCode);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);
        if (ObjectUtils.isEmpty(entries)) {
            return null;
        }
        return JSONObject.parseObject(JSONObject.toJSONString(entries), OnlineEquipmentConnectorDO.class);
    }

    /**
     * 入库操作,超时时间300秒
     *
     * @param onlineEquipmentConnectorDO do类
     */
    public void set(OnlineEquipmentConnectorDO onlineEquipmentConnectorDO) {
        String redisKey = formatKey(onlineEquipmentConnectorDO.getCode());
        Map<String, Object> objectMap = objectToMap(onlineEquipmentConnectorDO);
        redisTemplate.opsForHash().putAll(redisKey, objectMap);
        redisTemplate.expire(redisKey,
                300,
                TimeUnit.SECONDS);
    }

    public static Map<String, Object> objectToMap(Object object) {
        Map<String, Object> dataMap = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(object) != null) {
                    dataMap.put(field.getName(), field.get(object));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return dataMap;
    }


    private static String formatKey(String equipmentConnectorCode) {
        return String.format(RedisKeyConstants.ONLINE_CHARGE_GUN_STATE, equipmentConnectorCode);
    }


}
