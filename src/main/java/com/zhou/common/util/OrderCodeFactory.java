package com.zhou.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 周泽
 * @date Create in 17:55 2021/6/22
 * @Description
 */
public class OrderCodeFactory {


    /**
     * 生成订单单号编码
     * @param userId
     */
    public static String getOrderCode(Long userId) {
        // 时间+ 雪花id
        String dateFormat = DateUtils.dateFormat("yyyyMMddHHmm", new Date());
        return StringUtils.join(dateFormat, IdGenerator.nextIdStr());
    }

}
