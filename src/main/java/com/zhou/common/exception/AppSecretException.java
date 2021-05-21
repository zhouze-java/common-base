package com.zhou.common.exception;

/**
 * @author 周泽
 * @date Create in 22:11 2019/8/28
 * @Description app参数异常
 */
public class AppSecretException extends RuntimeException {

    public AppSecretException(String message){
        super(message);
    }
}
