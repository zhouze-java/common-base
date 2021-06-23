package com.zhou.common.support.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author 周泽
 * @date Create in 14:24 2021/6/22
 * @Description
 */
@Slf4j
public class SecurityJwtTokenResolver {

    /**
     * 请求头中 token的前缀
     */
    protected static final String AUTHORIZATION_PREFIX = "bearer ";

    private final String jwtTokenSignKey;

    public static SecurityJwtTokenResolver setJwtTokenSignKey(String jwtTokenSignKey) {
        return new SecurityJwtTokenResolver(jwtTokenSignKey);
    }

    private SecurityJwtTokenResolver(String jwtTokenSignKey) {
        this.jwtTokenSignKey = jwtTokenSignKey;
    }

    /**
     * 获取登录的userID，如果ID为空获取小于0报428错误
     *
     * @return
     */
    public Long getLoginUserId(){

        String jwtToken = StringUtils.substringAfter(getHttpServletRequest().getHeader(HttpHeaders.AUTHORIZATION), AUTHORIZATION_PREFIX);
        log.info("请求头中的token:{}", jwtToken);

        // 获取配置中的 jwtTokenSignKey
        Claims claims = Jwts.parser().setSigningKey(jwtTokenSignKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(jwtToken).getBody();

        return Long.parseLong(String.valueOf(claims.get("userId")));
    }

    protected HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
