package com.zhou.common.support.aware;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author 周泽
 * @date Create in 12:00 2021/3/16
 * @Description 验证支持类
 */
public interface SupportAware {

    /**
     * 集合验证
     *
     * @param collection
     * @return
     */
    default boolean isEmpty(Collection<?> collection) {
        return org.springframework.util.CollectionUtils.isEmpty(collection);
    }

    /**
     * 集合验证非空
     *
     * @param collection
     * @return
     */
    default boolean notEmpty(Collection<?> collection) {
        return !org.springframework.util.CollectionUtils.isEmpty(collection);
    }

    /**
     * map验证为空
     *
     * @param map
     * @return
     */
    default boolean isEmpty(Map<?, ?> map) {
        return org.springframework.util.CollectionUtils.isEmpty(map);
    }

    /**
     * map 非空验证
     *
     * @param map
     * @return
     */
    default boolean notEmpty(Map<?, ?> map) {
        return !CollectionUtils.isEmpty(map);
    }

    /**
     * 字符串非空验证
     *
     * @param value
     * @return
     */
    default boolean isNotBlank(String value) {
        return StringUtils.isNotBlank(value);
    }

    /**
     * 字符串空验证
     *
     * @param value
     * @return
     */
    default boolean isBlank(String value) {
        return StringUtils.isBlank(value);
    }


}
