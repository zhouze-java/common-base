package com.zhou.common.exception;

/**
 * 参数错误
 * @author Administrator
 */
public class BadRequestException extends RuntimeException {


    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
