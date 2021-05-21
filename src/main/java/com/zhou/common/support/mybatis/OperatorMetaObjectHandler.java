package com.zhou.common.support.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zhou.common.support.auth.TokenInfo;
import com.zhou.common.support.auth.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Objects;
import java.util.Optional;

/**
 * 自动填充类
 * @author zhoze
 */
@RequiredArgsConstructor
public class OperatorMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Long userId = Optional.ofNullable(TokenUtil.getContextTokenInfo()).map(TokenInfo::getUserId).orElse(0L);
            setFieldValue("createUser", userId, metaObject);
            setFieldValue("createdAt", System.currentTimeMillis(), metaObject);
            setFieldValue("updateUser", userId, metaObject);
            setFieldValue("updatedAt", System.currentTimeMillis(), metaObject);
        } catch (Exception e) {
            setFieldValue("createUser", 0L, metaObject);
            setFieldValue("createdAt", System.currentTimeMillis(), metaObject);
            setFieldValue("updateUser", 0L, metaObject);
            setFieldValue("updatedAt", System.currentTimeMillis(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            Long userId = Optional.ofNullable(TokenUtil.getContextTokenInfo()).map(TokenInfo::getUserId).orElse(0L);
            setFieldValue("updateUser", userId, metaObject);
            setFieldValue("updatedAt", System.currentTimeMillis(), metaObject);
        } catch (Exception e) {
            setFieldValue("updateUser", 0L, metaObject);
            setFieldValue("updatedAt", System.currentTimeMillis(), metaObject);
        }
    }

    private void setFieldValue(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (Objects.nonNull(fieldVal) && metaObject.hasSetter(fieldName)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }

}
