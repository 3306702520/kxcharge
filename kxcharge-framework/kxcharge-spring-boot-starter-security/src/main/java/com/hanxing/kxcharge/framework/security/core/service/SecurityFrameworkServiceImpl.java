package com.hanxing.kxcharge.framework.security.core.service;

import cn.hutool.core.collection.CollUtil;
import com.hanxing.kxcharge.framework.common.core.KeyValue;
import com.hanxing.kxcharge.framework.security.core.LoginUser;
import com.hanxing.kxcharge.framework.security.core.util.SecurityFrameworkUtils;
import com.hanxing.kxcharge.module.system.api.permission.PermissionApi;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.util.cache.CacheUtils.buildCache;
import static com.hanxing.kxcharge.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 默认的 {@link SecurityFrameworkService} 实现类
 *
 * @author 航兴新能源
 */
@AllArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService {

    private final PermissionApi permissionApi;

    /**
     * 针对 {@link #hasAnyRoles(String...)} 的缓存
     */
    private final LoadingCache<KeyValue<Long, List<String>>, Boolean> hasAnyRolesCache = buildCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<Long, List<String>>, Boolean>() {

                @Override
                public Boolean load(KeyValue<Long, List<String>> key) {
                    return permissionApi.hasAnyRoles(key.getKey(), key.getValue().toArray(new String[0])).getCheckedData();
                }

            });

    /**
     * 针对 {@link #hasAnyPermissions(String...)} 的缓存
     */
    private final LoadingCache<KeyValue<Long, List<String>>, Boolean> hasAnyPermissionsCache = buildCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<Long, List<String>>, Boolean>() {

                @Override
                public Boolean load(KeyValue<Long, List<String>> key) {
                    return permissionApi.hasAnyPermissions(key.getKey(), key.getValue().toArray(new String[0])).getCheckedData();
                }

            });

    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    @SneakyThrows
    public boolean hasAnyPermissions(String... permissions) {
        Long userId = getLoginUserId();
        if (userId == null) {
            return false;
        }
        return hasAnyPermissionsCache.get(new KeyValue<>(userId, Arrays.asList(permissions)));
    }

    @Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    @SneakyThrows
    public boolean hasAnyRoles(String... roles) {
        Long userId = getLoginUserId();
        if (userId == null) {
            return false;
        }
        return hasAnyRolesCache.get(new KeyValue<>(userId, Arrays.asList(roles)));
    }

    @Override
    public boolean hasScope(String scope) {
        return hasAnyScopes(scope);
    }

    @Override
    public boolean hasAnyScopes(String... scope) {
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user == null) {
            return false;
        }
        return CollUtil.containsAny(user.getScopes(), Arrays.asList(scope));
    }

}
