package com.zhou.common.config.response;

import java.lang.annotation.*;

/**
 * @author 周泽
 * @date Create in 11:06 2021/6/1
 * @Description 忽略统一返回值
 */
@Documented
@Inherited
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseBody {
}
