package com.zhou.common.util;

import java.math.BigDecimal;

/**
 * @author 周泽
 * @date Create in 16:22 2021/6/29
 * @Description
 */
public class CommonUtils {

    /**
     * 元转分
     *
     * @param unit
     * @return
     */
    public static Integer toCent(BigDecimal unit) {
        if (unit == null) {
            return 0;
        }
        return unit.multiply(new BigDecimal("100")).intValue();
    }

}
