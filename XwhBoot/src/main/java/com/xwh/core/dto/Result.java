package com.xwh.core.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.xwh.localguide.common
 * @ClassName: Result
 * @Author: xiangwenhao
 * @CreateTime: 11/20/20 5:26 下午
 * @Description:
 */
public class Result {
    //返回码
    private Integer code;
    //是否成功
    private boolean success;
    //返回消息
    private String message;
    private Object data = new HashMap<>();

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

    // 链式操作返回信息
    public Result add(String key, Object value) {
        Map<String, Object> map = BeanUtil.beanToMap(this.data);
        map.put(key,value);
        this.setData(map);
        return this;
    }

    // 链式操作返回信息
    public Result add(Object value) {
        this.data = value;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
