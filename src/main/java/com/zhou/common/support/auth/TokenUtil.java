package com.zhou.common.support.auth;

import com.zhou.common.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 周泽
 * @date Create in 15:42 2021/3/25
 * @Description
 */
public class TokenUtil {

    private final static String USER_ID_KEY = "userId";

    /**
     * 获取request上下文中的TokenInfo
     */
    public static TokenInfo getContextTokenInfo() {
        HttpServletRequest request = SpringUtil.currentRequest();
        if (StringUtils.isEmpty(request.getHeader(USER_ID_KEY))) {
            return null;
        }
        return new TokenInfo(Long.valueOf(request.getHeader(USER_ID_KEY)));
    }

}
