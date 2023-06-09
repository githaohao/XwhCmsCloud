package com.xwh.core.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.google.gson.*;
import com.xwh.core.dto.Result;
import com.xwh.core.exception.FailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * @author xiangwenhao
 */

@Slf4j
@RestControllerAdvice
public class BaseController {

    /**
     * 属性去除
     * 去除不需要显示的属性
     *
     * @param obj
     * @param strings
     * @return
     */
    public Object propertyDel(Object obj, String... strings) {
        PropertyFilter propertyFilter = (object, name, value) -> !StrUtil.equalsAny(name, strings);
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


    public static <T> String filterFields(List<T> objects, Set<String> includedFields) {
        Gson gson = new GsonBuilder().registerTypeAdapter(objects.get(0).getClass(),
                new FieldFilterSerializer(includedFields)).create();
        return gson.toJson(objects);
    }

    static class FieldFilterSerializer implements JsonSerializer<Object> {
        private final Set<String> includedFields;

        public FieldFilterSerializer(Set<String> includedFields) {
            this.includedFields = includedFields;
        }
        @Override
        public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            for (java.lang.reflect.Field f : src.getClass().getDeclaredFields()) {
                if (!includedFields.contains(f.getName())) continue;
                f.setAccessible(true);
                try {
                    Object value = f.get(src);
                    obj.add(f.getName(), context.serialize(value));
                } catch (IllegalAccessException e) {
                    // ignore
                }
            }
            return obj;
        }
    }


    public static Result success(String message) {
        return getResult(true, HttpStatus.HTTP_OK, message);
    }

    public static Result fail(String message) {
        return getResult(false, HttpStatus.HTTP_BAD_REQUEST, message);
    }

    public static Result fail() {
        return fail("");
    }

    public static Result success() {
        return success("");
    }

    private static Result getResult(boolean success, int code, String message) {
        Result result = new Result();
        result.setSuccess(success);
        result.setCode(code);
        result.setMessage(Validator.isEmpty(message) ? (success ? "success!" : "fail!") : message);
        return result;
    }

    @ExceptionHandler(Throwable.class)
    public Result exception(Throwable e) {
        log.error(getStackTrace(e));
        return getResult(false, HttpStatus.HTTP_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(FailException.class)
    public Result failException(FailException e) {
        log.error(getStackTrace(e));
        return getResult(false, HttpStatus.HTTP_BAD_REQUEST, Validator.isEmpty(e.getMessage()) ? "FailException!" : e.getMessage());
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
