package com.zhou.common.base;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.common.exception.BusinessException;
import com.zhou.common.support.aware.WebSupportAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * @author 周泽
 * @date Create in 13:29 2021/3/16
 * @Description Service
 */
@Slf4j
public class BaseService<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements WebSupportAware {

    private final Method columnToStringMethod;

    private final Map<SFunction<T, Number>, String> columnNameCache = new ConcurrentHashMap<>(16);

    {
        try {
            columnToStringMethod = AbstractLambdaWrapper.class.getDeclaredMethod("columnToString", SFunction.class);
            columnToStringMethod.setAccessible(true);
        } catch (NoSuchMethodException ignored) {
            throw new BusinessException("could not found incr method on AbstractLambdaWrapper");
        }
    }

    public static <T> BinaryOperator<T> throwing() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

    @Override
    public T getById(Serializable id) {
        T entity = super.getById(id);
        if (entity == null) {
            String tableName = TableInfoHelper.getTableInfo(entityClass).getTableName();
            throw new BusinessException(String.format("找不到 id = [ %s ] 对应的资源 %s", id, tableName));
        }
        return entity;
    }

    public void checkExist(Serializable id) {
        getById(id);
    }

    public Map<Long, Long> counting(Set<?> query, SFunction<T, Long> condition) {
        return counting(query, condition, T::getId);
    }

    public Map<Long, Long> counting(Set<?> query, SFunction<T, Long> condition, SFunction<T, Long> classifier) {
        return findDict(query, condition, singletonList(classifier), groupingBy(classifier, Collectors.counting()));
    }

    public Map<Long, T> findDict(Set<?> query) {
        return findDict(query, T::getId, WebSupportAware.cast(), false);
    }

    public <V> Map<Long, V> findDict(Set<?> query, SFunction<T, V> valueMapper) {
        return findDict(query, T::getId, valueMapper);
    }

    public <K, V> Map<K, V> findDict(Set<?> query, SFunction<T, K> condition, SFunction<T, V> valueMapper) {
        return findDict(query, condition, valueMapper, true);
    }

    public <K, V> Map<K, V> findDict(Set<?> query, SFunction<T, K> condition, SFunction<T, V> valueMapper, boolean onlyQuery) {
        if (onlyQuery) {
            return findDict(query, condition, asList(condition, valueMapper), toMap(condition, valueMapper));
        }
        return findDict(query, condition, toMap(condition, valueMapper));
    }

    public <K, V> Map<K, V> findDict(Set<?> query, SFunction<T, K> condition, Collector<T, ?, Map<K, V>> collector) {
        return findDict(query, condition, emptyList(), collector);
    }

    @SuppressWarnings("all")
    public <K, V> Map<K, V> findDict(Set<?> query, SFunction<T, ?> condition, List<SFunction<T, ?>> selections, Collector<T, ?, Map<K, V>> collector) {
        if (isEmpty(query)) {
            return Collections.emptyMap();
        }
        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntityClass(entityClass);
        if (notEmpty(selections)) {
            queryWrapper.select(selections.toArray(new SFunction[0]));
        }
        List<T> results;
        if (query.size() == 1) {
            results = list(queryWrapper.eq(condition, query.iterator().next()));
        } else {
            results = list(queryWrapper.in(condition, query));
        }
        return results.stream().collect(collector);
    }

    public void incr(SFunction<T, ?> condition, Object conditionValue, SFunction<T, Number> mapper) {
        String columnName = getColumnName(mapper);
        boolean success;
        int retryCount = 0;
        do {
            success = lambdaUpdate()
                    .eq(condition, conditionValue)
                    .setSql(formatIncrSqlFragment(columnName))
                    .update();
            if (!success) {
                retryCount++;
                log.warn("incr {} failed, retrying... retryCount: {}", columnName, retryCount);
            }
        } while (!success);
    }

    public boolean exist(Wrapper<T> wrapper) {
        return getBaseMapper().exist(wrapper);
    }

    public void truncateTable() {
        getBaseMapper().truncateTable();
    }

    public void throwException(String message) {
        throw new BusinessException(message);
    }

    public <ITEM> List<ITEM> emptyList() {
        return Collections.emptyList();
    }

    public <ITEM> Set<ITEM> emptySet() {
        return Collections.emptySet();
    }

    public <K, V> Map<K, V> emptyMap() {
        return Collections.emptyMap();
    }

    public String formatIncrSqlFragment(String columnName) {
        return String.format("%s = %s + 1", columnName, columnName);
    }

    private String getColumnName(SFunction<T, Number> mapper) {
        return columnNameCache.computeIfAbsent(mapper, mapping -> {
            LambdaUpdateWrapper<T> updateWrapper = new LambdaUpdateWrapper<>();
            return (String) ReflectionUtils.invokeMethod(columnToStringMethod, updateWrapper, mapping);
        });
    }
}
