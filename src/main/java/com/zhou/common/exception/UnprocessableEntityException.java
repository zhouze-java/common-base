package com.zhou.common.exception;

/**
 * 422,请求验证不通过
 */
public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}
