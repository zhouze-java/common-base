package com.zhou.common.support.auth;

import com.alibaba.fastjson.JSONObject;
import com.zhou.common.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
        return Long.parseLong(String.valueOf(resolveToken().get("userId")));
    }

    /**
     * 获取权限列表
     * @return
     */
    public List<String> getAuthorities(){
        return resolveToken().get("authorities", List.class);
    }

    private Claims resolveToken(){
        try {
            String jwtToken = StringUtils.substringAfter(getHttpServletRequest().getHeader(HttpHeaders.AUTHORIZATION), AUTHORIZATION_PREFIX);
            return Jwts.parserBuilder().setSigningKey(jwtTokenSignKey.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(jwtToken).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException();
        }
    }

    protected HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static void main(String[] args) {

        String token = "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsieHpxLWFkbWluIl0sInVzZXJfbmFtZSI6IjE4ODg4ODg4ODg4Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwiYWxsIl0sImV4cCI6MTYyNTUzNDU2NiwidXNlcklkIjo3NSwiYXV0aG9yaXRpZXMiOlsiTUVSQ0hBTlRfUkVORVciLCJGSU5BTkNJQUxfTElTVCIsIlZFUlNJT05fQUREIiwiTUVSQ0hBTlRfQVVESVRfREVUQUlMUyIsIkFSVElDTEVfU09SVCIsIlVTRVJTX1VQREFURV9ERVBUIiwiQVBQTElDQVRJT05fVVBEQVRFIiwiVVNFUlNfTUFOQUdFTUVOVCIsIlVTRVJTX0FERF9ERVBUIiwiRklOQU5DSUFMX0RPV05MT0FEIiwiREVMRVRFX0FSVElDTEUiLCJUUklBTF9BUFBMWV9MSVNUIiwiTUVSQ0hBTlRfVVBEQVRFX1ZFUlNJT04iLCJNRVJDSEFOVF9SRV9BVURJVCIsIlVTRVJTX0RFTF9ST0xFIiwiTk9USUNFX01BTkFHRU1FTlQiLCJSRURfUEFDS0VUX1JFQ0hBUkdFIiwiQ09OVFJBQ1RfQUNDT1VOVCIsIlVQREFURV9BUlRJQ0xFX0NMQVNTIiwiREVMRVRFX0FSVElDTEVfQ0xBU1MiLCJBRERfQVJUSUNMRV9DTEFTUyIsIlJFQ0hBUkdFX1JFQ09SRCIsIlZFUlNJT05fVVBEQVRFIiwiTUVSQ0hBTlRfREVUQUlMUyIsIkFSVElDTEVfT05fT0ZGIiwiUEFZTUVOVF9ERVRBSUxTIiwiUkVEX1BBQ0tFVF9SRUNPUkQiLCJNRVJDSEFOVF9DTE9TRSIsIkFQUExZX01FUkNIQU5UX0lORk8iLCJNRVJDSEFOVF9BVURJVCIsIkZJTkFOQ0lBTF9ERVRBSUxTIiwiVVNFUlNfREVMX0RFUFQiLCJNVl9BUlRJQ0xFX0NMQVNTIiwiTUVSQ0hBTlRfVVBEQVRFIiwiUEFZTUVOVF9NQU5BR0VNRU5UIiwiVVNFUlNfQUREIiwiVkVSU0lPTl9NQU5BR0VNRU5UIiwiVVNFUlNfVVBEQVRFIiwiVVNFUlNfQUREX0NISUxEX0RFUFQiLCJVU0VSU19BRERfUk9MRSIsIkFQUExJQ0FUSU9OX09QRVJBVEUiLCJTRVNTSU9OX0FVRElUIiwiVkVSU0lPTl9ERUxFVEUiLCJVU0VSU19ERUwiLCJNRVJDSEFOVF9BVURJVF9NQU5BR0VNRU5UIiwiQUREX0FSVElDTEUiLCJNRVJDSEFOVF9PUEVOIiwiQVBQTElDQVRJT05fQ1JFQVRFIiwiVVBEQVRFX0FSVElDTEUiLCJTVVBQT1JUX0NFTlRFUiIsIk1FUkNIQU5UX01BTkFHRU1FTlQiLCJBUFBMSUNBVElPTl9JTkZPIiwiVVNFUlNfVVBEQVRFX1JPTEUiLCJTRVNTSU9OX01BTkFHRU1FTlQiLCJVU0VSU19OT19MT0dHSU4iLCJWRVJTSU9OX1VQREFURV9ERUZBVUxUIiwiQVBQTElDQVRJT05fTUFOQUdFTUVOVCJdLCJqdGkiOiI0NTY5Yjk5OC1hNzY1LTQxZTAtOTZmMC03YzVhYTA4NGU2ZDciLCJjbGllbnRfaWQiOiJhZG1pbi13ZWIifQ.dFDhd92pUi7_RUoQ0Db8-SY1BsvyRkoeuxwXKqDD0OY";
        Claims claims = Jwts.parserBuilder().setSigningKey("291110722c1e418f9e5258d3382d41d3".getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody();
        System.out.println();
    }
}
