package com.zhou.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;

/**
 * @author zhouze
 * @since 2020/6/23
 */
@RequiredArgsConstructor
public class IdGenerator {


    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(0L, 0L);

    public static String nextIdStr() {
        return SNOWFLAKE.nextIdStr();
    }

    public static Long nextId() {
        return SNOWFLAKE.nextId();
    }

}
