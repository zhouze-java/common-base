package com.zhou.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author 周泽
 * @date Create in 9:49 2020/11/9
 * @Description 自定义消息体
 */
@Data
public class Message implements Serializable {

    @ApiModelProperty(name = "消息id")
    private Long id;

    @ApiModelProperty(name = "消息内容", dataType = "String", required = true, example = "这是一个测试过期时间为3分钟的消息")
    private String content;

    @ApiModelProperty(name = "过期时间", dataType = "Long", required = true)
    private Long ttl;

    @ApiModelProperty("持久化")
    private Boolean durable;

    /**
     * 功能描述 设置消息过期时间
     * @author wilson wei
     * @date 11:38 2018/8/19
     */
    public void setTtl(long number, TimeUnit unit) {
        this.setTtl(unit.toMillis(number));
    }

}
