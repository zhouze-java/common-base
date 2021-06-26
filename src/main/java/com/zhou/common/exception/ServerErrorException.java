package com.zhou.common.exception;

/**
 * @author 周泽
 * @date Create in 10:25 2021/6/24
 * @Description 服务端异常
 */
public class ServerErrorException extends RuntimeException {

    public ServerErrorException() {
        super();
    }

    public ServerErrorException(String message) {
        super(message);
    }
}
