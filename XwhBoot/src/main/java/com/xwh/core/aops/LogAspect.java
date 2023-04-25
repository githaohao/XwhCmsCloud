package com.xwh.core.aops;

import com.xwh.core.annotation.Log;
import com.xwh.core.entity.SysLog;
import com.xwh.core.threads.SaveLogThread;
import com.xwh.core.utils.StringUtils;
import com.xwh.core.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.net.SocketException;

/**
 * @ClassName: LogAspect
 * @Description: 日志Aop
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
	/**
     * 切入点
     */
	@Pointcut("@annotation(com.xwh.core.annotation.Log) ")
    public void entryPoint() {
        // 无需内容
    }


	/**
	 * 环绕通知处理处理
	 *
	 * @param point
	 * @throws Throwable
	 */
	@Around("entryPoint()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		// 先执行业务,注意:业务这样写业务发生异常不会拦截日志。
		Object result = point.proceed();
		try {
			handleAround(point);// 处理日志
		} catch (Exception e) {
			log.error("日志记录异常", e);
		}
		return result;
	}

	/**
	 * around日志记录
	 *
	 * @param point
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public void handleAround(ProceedingJoinPoint point) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SysLog lv = new SysLog();
		lv.setCreateBy(String.valueOf(TokenUtil.getUserId()));
		// 正常日志
		lv.setType(1);
		// 获取IP地址
		String ip = StringUtils.getRemoteAddr(request);
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			try {
				ip = StringUtils.getRealIp();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		lv.setIp(ip);
		lv.setMethod(request.getMethod());

		Signature sig = point.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		msig = (MethodSignature) sig;
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		// 方法名称
		String methodName = currentMethod.getName();
		// 获取注解对象
		Log aLog = currentMethod.getAnnotation(Log.class);
		// 类名
		String className = point.getTarget().getClass().getName();
		// 方法的参数
		Object[] params = point.getArgs();

		StringBuilder paramsBuf = new StringBuilder();
		for (Object arg : params) {
			paramsBuf.append(arg);
			paramsBuf.append("&");
		}
		String content = "类:" + className + ",方法名：" + methodName + ",参数:" + paramsBuf;
		// 日志保存
		lv.setContent(content);
		lv.setOperation(aLog.operation());
		new SaveLogThread(lv, request).start();
	}

	@AfterThrowing(pointcut = "entryPoint()", throwing = "ex")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SysLog lv = new SysLog();
		lv.setCreateBy(String.valueOf(TokenUtil.getUserId()));
		// 异常日志
		lv.setType(2);
		// 获取IP地址
		String ip = StringUtils.getRemoteAddr(request);
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			try {
				ip = StringUtils.getRealIp();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		lv.setIp(ip);
		lv.setMethod(request.getMethod());

		try {
			String targetName = joinPoint.getTarget().getClass().getName();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class<?> targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			String operation = "";
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class<?>[] clazzs = method.getParameterTypes();
					if (clazzs.length == arguments.length) {
						operation = method.getAnnotation(Log.class).operation();
						break;
					}
				}
			}

			StringBuilder paramsBuf = new StringBuilder();
			for (Object arg : arguments) {
				paramsBuf.append(arg);
				paramsBuf.append("&");
			}
			String content = "异常方法:" + className + "." + methodName + "();参数:" + paramsBuf + ",异常信息:" + ex;

			log.error("异常信息:{}", ex.getMessage());
			//日志保存
			lv.setContent(content);
			lv.setOperation(operation);
			new SaveLogThread(lv, request).start();

		} catch (Exception e) {
			log.error("异常信息:{}", e.getMessage());
		}
	}



}
