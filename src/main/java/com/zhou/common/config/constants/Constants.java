package com.zhou.common.config.constants;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

/**
 * @author 周泽
 * @date Create in 14:40 2021/3/16
 * @Description
 */
public class Constants {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * BigDecimal - 100
     */
    public static final BigDecimal TIMES_100 = new BigDecimal("100");

}
