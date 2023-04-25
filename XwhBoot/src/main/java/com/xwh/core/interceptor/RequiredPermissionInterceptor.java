package com.xwh.core.interceptor;

import com.xwh.core.annotation.RequiredPermission;
import com.xwh.core.utils.BlankUtils;
import com.xwh.core.utils.RedisUtils;
import com.xwh.core.utils.TokenUtil;
import com.xwh.core.utils.WriterUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: SecurityInterceptor
 * @Description: 按钮权限拦截
 */
public class RequiredPermissionInterceptor implements AsyncHandlerInterceptor {

	@Resource
	RedisUtils redis;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 验证权限
        if (this.hasPermission(handler)) {
            return true;
        }
		// 返回未授权信息
		WriterUtil.renderString(response, "接口无访问权限！请联系管理员授权使用！");
        return false;

	}

	/**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    private boolean hasPermission(Object handler) {
		boolean rtFlag = false;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            // 如果标记了注解，则判断权限
			if (BlankUtils.isNotBlank(requiredPermission) && BlankUtils.isNotBlank(requiredPermission.value())) {
				String permissionValue = requiredPermission.value();
				String key = "sys:permissions:" + TokenUtil.getUserId();
				if (BlankUtils.isNotBlank(redis.get(key))) {
					Set<String> permsData = (HashSet<String>) redis.get(key);
					if (permissionValue.contains(",")) {
						String[] permissionValueArr = permissionValue.split(",");
						for (String pa : permissionValueArr) {
							if (permsData.contains(pa)) {
								rtFlag = true;
								break;
							}
						}
					} else {
						if (permsData.contains(permissionValue)) {
							rtFlag = true;
						}
					}
				}
			} else {
				rtFlag = true;
            }
		} else {
			rtFlag = true;
        }
		return rtFlag;
    }

}

