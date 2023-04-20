package com.xwh.core.dto;

import cn.hutool.core.lang.Validator;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @Package: com.xwh.localguide.common
 * @ClassName: Result
 * @Author: xiangwenhao
 * @CreateTime: 11/20/20 5:26 下午
 * @Description:
 */
@Getter
@Setter
public class Result {
    private Integer code;
    private boolean success;
    private String message;
    private Object data = new HashMap<>();

    public static Result success(String message) {
        return getResult(true, HTTP_OK, message);
    }

    public static Result fail(String message) {
        return getResult(false, HTTP_BAD_REQUEST, message);
    }

    public static Result success() {
        return success("");
    }

    public static Result fail() {
        return fail("");
    }

    private static Result getResult(boolean success, int code, String message) {
        Result result = new Result();
        result.setSuccess(success);
        result.setCode(code);
        result.setMessage(Validator.isEmpty(message) ? (success ? "success!" : "fail!") : message);
        return result;
    }

    public Result add(String key, Object value) {
        Map<String, Object> map = (Map<String, Object>) this.data;
        map.put(key, value);
        return this;
    }

    public Result add(Object value) {
        this.data = value;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

}
