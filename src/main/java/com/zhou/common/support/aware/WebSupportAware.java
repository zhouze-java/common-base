package com.zhou.common.support.aware;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.common.base.BaseEntity;
import com.zhou.common.util.SpringUtil;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Function;

/**
 * @author 周泽
 * @date Create in 13:17 2021/3/16
 * @Description
 */
public interface WebSupportAware extends SupportAware {

    /**
     * 类型强转
     *
     * @param <T>
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    static <T, K> SFunction<T, K> cast() {
        return t -> ((K) t);
    }

    default <R> Function<R, R> identity() {
        return Function.identity();
    }

    default HttpServletRequest currentRequest() {
        return SpringUtil.currentRequest();
    }

    default HttpServletResponse currentResponse() {
        return SpringUtil.currentResponse();
    }

    default ApplicationContext getApplicationContext() {
        return SpringUtil.getApplicationContext();
    }

    /**
     * 生成空的分页数据
     *
     * @param page
     * @param <V>
     * @return
     */
    default <V> IPage<V> emptyPage(Page<? extends BaseEntity> page) {
        return new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
    }
}
