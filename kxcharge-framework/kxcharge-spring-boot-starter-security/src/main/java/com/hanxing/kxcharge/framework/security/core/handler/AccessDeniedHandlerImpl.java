package com.hanxing.kxcharge.framework.security.core.handler;

import com.hanxing.kxcharge.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.security.core.util.SecurityFrameworkUtils;
import com.hanxing.kxcharge.framework.common.util.servlet.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hanxing.kxcharge.framework.common.exception.enums.GlobalErrorCodeConstants.FORBIDDEN;
import static com.hanxing.kxcharge.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;

/**
 * 访问一个需要认证的 URL 资源，已经认证（登录）但是没有权限的情况下，返回 {@link GlobalErrorCodeConstants#FORBIDDEN} 错误码。
 *
 * 补充：Spring Security 通过 {@link ExceptionTranslationFilter#handleAccessDeniedException(HttpServletRequest, HttpServletResponse, FilterChain, AccessDeniedException)} 方法，调用当前类
 *
 * @author 航兴新能源
 */
@Slf4j
@SuppressWarnings("JavadocReference")
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        // 打印 warn 的原因是，不定期合并 warn，看看有没恶意破坏
        log.warn("[commence][访问 URL({}) 时，用户({}) 权限不够]", request.getRequestURI(),
                SecurityFrameworkUtils.getLoginUserId(), e);
        // 返回 403
        ServletUtils.writeJSON(response, CommonResult.error(FORBIDDEN));
    }

}
