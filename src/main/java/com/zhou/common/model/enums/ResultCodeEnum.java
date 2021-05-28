package com.zhou.common.model.enums;

/**
 * @author 周泽
 * @date Create in 17:06 2021/5/25
 * @Description
 */
public enum  ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS("成功", 0),

    /**
     * 找不到资源
     */
    NOT_FOUND("找不到资源", 30404),

    /**
     * 找不到资源
     */
    FORBIDDEN("没有权限", 30403),

    /**
     * 参数错误
     */
    UNPROCESSABLE_ENTITY("参数错误", 30422),

    /**
     * 缺少先决条件
     */
    PRECONDITION_REQUIRED("缺少先决条件",30428)

    ;

    private final String displayName;

    private final Integer code;

    ResultCodeEnum(String displayName, Integer code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getCode() {
        return code;
    }
}
