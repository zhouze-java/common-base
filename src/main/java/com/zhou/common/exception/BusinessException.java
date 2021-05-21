package com.zhou.common.exception;

/**
 * @author 周泽
 * @date Create in 13:21 2021/3/16
 * @Description 业务异常基类
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
