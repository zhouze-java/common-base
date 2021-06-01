package com.zhou.common.base;

import com.zhou.common.support.aware.WebSupportAware;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 周泽
 * @date Create in 11:59 2021/3/16
 * @Description
 */
public class BaseController<T extends BaseEntity, S extends BaseService<? extends BaseMapper<T>, T>> implements WebSupportAware {

    /**
     * 对应类的service
     */
    @Autowired
    protected S service;

}
