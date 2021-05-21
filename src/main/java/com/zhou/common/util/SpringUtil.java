package com.zhou.common.util;

import com.zhou.common.exception.BusinessException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author 周泽
 * @date Create in 13:18 2021/3/16
 * @Description Spring相关工具类
 */
public class SpringUtil {

    private static AnnotationConfigServletWebServerApplicationContext applicationContext;

    /**
     * 通过class获取Bean
     *
     * @param <T>   Bean类型
     * @param clazz Bean类
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取当前request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest currentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(requestAttributes)
                .map(attributes -> ((ServletRequestAttributes) attributes).getRequest())
                .orElseThrow(() -> new BusinessException("请确认当前操作在 request 生命周期内！"));
    }

    /**
     * 获取当前response
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse currentResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(requestAttributes)
                .map(attributes -> ((ServletRequestAttributes) attributes).getResponse())
                .orElseThrow(() -> new BusinessException("请确认当前操作在 response 生命周期内！"));
    }

    /**
     * 获取applicationContext
     *
     * @return ApplicationContext
     */
    public static AnnotationConfigServletWebServerApplicationContext getApplicationContext() {
        assertApplicationInitialized();
        return applicationContext;
    }

    /**
     * 获取BeanFactory
     *
     * @return BeanFactory
     */
    public static DefaultListableBeanFactory getBeanFactory() {
        return getApplicationContext().getDefaultListableBeanFactory();
    }

    public static void setApplicationContextIfNone(AnnotationConfigServletWebServerApplicationContext context) {
        if (applicationContext == null) {
            synchronized (SpringUtil.class) {
                if (applicationContext == null) {
                    SpringUtil.applicationContext = context;
                }
            }
        }
    }

    private static void assertApplicationInitialized() {
        Assert.notNull(applicationContext, "current applicationContext is null");
    }

}
