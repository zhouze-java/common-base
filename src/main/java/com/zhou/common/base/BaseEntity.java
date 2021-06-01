package com.zhou.common.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author 周泽
 * @date Create in 13:22 2021/3/16
 * @Description 实体类基类
 */
@Data
public class BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    protected Long id;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    protected Long createdAt;

    /**
     * 更新时间
     */
    @JsonIgnore
    @TableField(value = "updated_at", fill = FieldFill.UPDATE)
    protected Long updatedAt;

    /**
     * 逻辑删除 0: 未删除 1: 已删除
     */
    @TableLogic
    @TableField(value = "del", select = false)
    @JsonIgnore
    protected Boolean del;

    /**
     * 创建人
     */
    @JsonIgnore
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    protected Long createUser;

    /**
     * 更新人
     */
    @JsonIgnore
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    protected Long updateUser;

}
