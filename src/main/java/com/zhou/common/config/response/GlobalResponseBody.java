package com.zhou.common.config.response;

import java.lang.annotation.*;

/**
 * @author 周泽
 * @date Create in 16:21 2021/7/5
 * @Description
 */
@Documented
@Inherited
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalResponseBody {
}
