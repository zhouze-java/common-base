package com.zhou.common.exception;

/**
 * @author 周泽
 * @date Create in 16:52 2021/7/5
 * @Description 第三方平台接口异常
 */
public class ExternalApiException extends RuntimeException {

    public ExternalApiException() {
        super();
    }

    public ExternalApiException(String message) {
        super(message);
    }

}
