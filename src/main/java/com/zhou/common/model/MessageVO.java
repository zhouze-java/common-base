package com.zhou.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 错误状态返回的消息，可直接用于提示用户
 * @author 周泽
 *
 */
@Data
@ApiModel(description = "错误信息对象")
public class MessageVO {

    @ApiModelProperty(value="错误详细提示信息,直接提示用户")
    private String message;

    @ApiModelProperty(value="错误帮助url")
    private String documentationUrl;

    public MessageVO() {
        super();
    }

    public MessageVO(String message) {
        super();
        this.message = message;
    }

    public MessageVO(String message, String documentationUrl) {
        super();
        this.message = message;
        this.documentationUrl = documentationUrl;
    }

}
