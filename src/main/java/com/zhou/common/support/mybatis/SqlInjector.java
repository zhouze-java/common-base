package com.zhou.common.support.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.zhou.common.support.mybatis.method.ExistRecord;
import com.zhou.common.support.mybatis.method.TruncateTable;

import java.util.List;

/**
 * @author 周泽
 * @date Create in 13:43 2021/3/16
 * @Description Mybatis sql注入器
 */
public class SqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new TruncateTable());
        methodList.add(new ExistRecord());
        return methodList;
    }

}
