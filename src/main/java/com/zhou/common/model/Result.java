package com.zhou.common.model;

import com.zhou.common.model.enums.ResultCodeEnum;
import lombok.Data;

/**
 * @author 周泽
 * @date Create in 16:53 2021/5/25
 * @Description 定义统一返回体
 */
@Data
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 小细胞
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public Result(T obj) {
        this.data = obj;
        this.code = ResultCodeEnum.SUCCESS.getCode();
    }

    public Result(T obj, Integer code) {
        this.data = obj;
        this.code = code;
    }

    public Result(T obj, Integer code, String msg) {
        this.data = obj;
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg) {
        this.data = null;
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code) {
        this.code = code;
    }

}

