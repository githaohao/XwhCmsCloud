package com.xwh.core.exception;

import lombok.Getter;
import org.springframework.util.StringUtils;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/*
    自定义注册异常，继承RuntimeException
 */
@Getter
public class FailException extends RuntimeException {


    private Integer status = BAD_REQUEST.value();

    //定义无参构造方法
    public FailException() {
        super();
    }

    //定义有参构造方法
    public FailException(String message) {
        super(message);
    }


    public FailException(Integer status, String message) {
        super(message);
        this.status = status;
    }

    public FailException(Class clazz, String field, String val) {
        super(FailException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " "+ val + " does not exist";
    }
}
