package com.zhou.common.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zhou.common.support.mybatis.OperatorMetaObjectHandler;
import com.zhou.common.support.mybatis.SqlInjector;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 周泽
 * @date Create in 13:43 2021/3/16
 * @Description Mybatis 配置类
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class MybatisConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();

        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        paginationInterceptor.setMaxLimit(500L);
        paginationInterceptor.setDbType(DbType.MYSQL);

        BlockAttackInnerInterceptor blockAttackInterceptor = new BlockAttackInnerInterceptor();

        mybatisPlusInterceptor.addInnerInterceptor(paginationInterceptor);
        mybatisPlusInterceptor.addInnerInterceptor(blockAttackInterceptor);
        return mybatisPlusInterceptor;
    }

    @Bean
    public ISqlInjector sqlInjector() {
        return new SqlInjector();
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new OperatorMetaObjectHandler();
    }
}
