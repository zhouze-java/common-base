package com.zhou.common.support.request;

import org.apache.tomcat.util.http.MimeHeaders;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * @author 周泽
 * @date Create in 15:09 2021/6/23
 * @Description request 增强， 主要作用是自定义参数放到请求头
 */
public class HttpServletRequestEnhancer {

    /**
     * 修改header信息，key-value键值对儿加入到header中
     * @param request
     * @param key
     * @param value
     */
    private static void reflectSetHeaderParam(HttpServletRequest request,String key,String value){
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        System.out.println("request实现类="+requestClass.getName());
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            o2.addValue(key).setString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
