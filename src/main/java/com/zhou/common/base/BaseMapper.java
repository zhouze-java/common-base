package com.zhou.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zhou.common.support.aware.WebSupportAware;
import org.apache.ibatis.annotations.Param;

/**
 * @author 周泽
 * @date Create in 13:32 2021/3/16
 * @Description Mybatis Mapper 基类
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T>, WebSupportAware {

    /**
     * 删除全部
     *
     */
    void truncateTable();

    /**
     * 判断是否存在
     *
     * @param wrapper
     * @return
     */
    boolean exist(@Param(Constants.WRAPPER) Wrapper<T> wrapper);
}
