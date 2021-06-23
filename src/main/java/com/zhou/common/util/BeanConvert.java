package com.zhou.common.util;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 阿哲
 */
public class BeanConvert {
    private static Map<String, BeanCopier> map = new HashMap<>();

    /**
     * 处理属性为Long到String的类
     */
    public static <T> T convert(Object source, Class<T> targetClz) {
        return convert(source, targetClz, (value, targetType, methodName) -> {
            if (value == null) {
                return null;
            }
            Object result = null;
            if (targetType == String.class) {
                result = value.toString();
            } else if (value.getClass().getName().equals(targetType.getName()) || value instanceof ArrayList && List.class.equals(targetType) || value instanceof HashMap && Map.class.equals(targetType)) {
                result = value;
            }
            return result;
        });
    }

    private static <T> T convert(Object source, Class<T> targetClz, Converter converter) {
        if (source == null || targetClz == null) {
            return null;
        }
        T target;
        try {
            target = targetClz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        String className = targetClz.getName() + source.getClass().getName();
        if (converter == null) {
            BeanCopier copier = map.computeIfAbsent(className, key -> BeanCopier.create(source.getClass(), targetClz, false));
            copier.copy(source, target, null);
        } else {
            BeanCopier copier = map.computeIfAbsent(className, key -> BeanCopier.create(source.getClass(), targetClz, true));
            copier.copy(source, target, converter);
        }
        return target;
    }

    public static <T> T copy(Object source, Class<T> targetClz) {
        return convert(source, targetClz, null);
    }

    public static <T, D> List<T> copy(List<D> sourceList, Class<T> targetClz) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<T> vlist = new ArrayList<>(sourceList.size());
        sourceList.forEach(d -> vlist.add(convert(d, targetClz)));
        return vlist;
    }


}