package com.zhou.common.exception;

/**
 * @author 周泽
 * @date Create in 14:44 2021/6/23
 * @Description 未登录异常
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}
