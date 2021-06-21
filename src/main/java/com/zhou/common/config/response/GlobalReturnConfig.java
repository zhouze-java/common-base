package com.zhou.common.config.response;

import com.zhou.common.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author 周泽
 * @date Create in 9:52 2021/6/1
 * @Description
 */
@RequiredArgsConstructor
public class GlobalReturnConfig {

    @RestControllerAdvice(basePackages = {
            "com.xinzhouqi.session.record.server.controller",
            "com.xinzhouqi.finance.web.controller",
            "com.xinzhouqi.pay.web.controller",
            "com.xinzhouqi.collect.server.controller"
    })
    static class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return !methodParameter.hasMethodAnnotation(IgnoreResponseBody.class);
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            if (body instanceof Result) {
                return body;
            }
            return new Result<>(body);
        }
    }

}
