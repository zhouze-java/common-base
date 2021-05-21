package com.zhou.common.exception;

/**
 * @author 周泽
 * @date Create in 22:22 2019/9/27
 * @Description 403 没权限
 */
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = -5447692349297847968L;

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
