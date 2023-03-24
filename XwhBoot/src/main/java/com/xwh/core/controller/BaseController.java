package com.xwh.core.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.xwh.core.dto.Result;
import com.xwh.core.exception.FailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author xiangwenhao
 */
public class BaseController {


    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 属性去除
     * 去除不需要显示的属性
     *
     * @param obj
     * @param strings
     * @return
     */
    public Object propertyDel(Object obj, String... strings) {
        PropertyFilter propertyFilter = (object, name, value) -> {
            return !StrUtil.equalsAny(name, strings);
        };
        String s = JSON.toJSONString(obj, SerializeConfig.globalInstance, new SerializeFilter[]{propertyFilter}, "yyyy-MM-dd HH:mm:ss", JSON.DEFAULT_GENERATE_FEATURE);
        return JSON.parse(s);
    }

    /**
     * 属性显示
     * 只显示需要显示的属性
     *
     * @param obj
     * @param strings 需要显示的字段
     * @return
     */
    public Object propertyShow(Object obj, String... strings) {
        PropertyFilter propertyFilter = (object, name, value) -> StrUtil.equalsAny(name, strings);
        String s = JSON.toJSONString(obj, SerializeConfig.globalInstance, new SerializeFilter[]{propertyFilter}, "yyyy-MM-dd HH:mm:ss", JSON.DEFAULT_GENERATE_FEATURE);
        return JSON.parse(s);
    }


    public static Result success(String message) {
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_OK);
        if (Validator.isEmpty(message)) {
            result.setMessage("success!");
        } else {

            result.setMessage(message);
        }
        result.setSuccess(true);
        return result;
    }


    public static Result fail(String message) {
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_BAD_REQUEST);
        if (Validator.isEmpty(message)) {
            result.setMessage("fail!");
        } else {
            result.setMessage(message);
        }
        result.setSuccess(false);
        return result;
    }

    public static Result fail() {
        return fail("");
    }

    public static Result success() {
        return success("");
    }

    /**
     * Exception result.
     *
     * @param e the e
     * @return the result
     * <p>
     * 捕获 未知 异常
     */
    @ExceptionHandler(Throwable.class)
    public Result exception(Throwable e) {
        log.error(getStackTrace(e));
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_BAD_REQUEST);
        result.setMessage(e.getMessage());
        result.setSuccess(false);
        return result;
    }

    /**
     * Fail exception result.
     *
     * @param e the e
     * @return the result
     * <p>
     * 获 FailException 异常
     */
    @ExceptionHandler(FailException.class)
    public Result failException(FailException e) {
        log.error(getStackTrace(e));
        Result result = new Result();
        result.setCode(HttpStatus.HTTP_BAD_REQUEST);
        if (Validator.isEmpty(e.getMessage())) {
            result.setMessage("FailException!");
        } else {
            result.setMessage(e.getMessage());
        }
        result.setSuccess(false);
        return result;
    }

    /**
     * 获取堆栈信息
     *
     * @param throwable the throwable
     * @return the string
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }


}
