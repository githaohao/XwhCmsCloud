package com.xwh.core.annotation;

import java.lang.annotation.*;

/**
 * @author xwh
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
	String value();
}

