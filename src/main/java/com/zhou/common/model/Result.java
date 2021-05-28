package com.zhou.common.model;

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

}

