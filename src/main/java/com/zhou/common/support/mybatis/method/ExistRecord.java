package com.zhou.common.support.mybatis.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author liaozan
 * @since 2020/7/12
 */
public class ExistRecord extends AbstractMethod {

    private static final String SQL = "<script>\nSELECT IF((SELECT 1 FROM %s %s LIMIT 1) IS NULL , 0 , 1)\n</script>";

    private static final String SQL_ID = "exist";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = String.format(SQL, tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForOther(mapperClass, SQL_ID, sqlSource, Boolean.class);
    }

}
