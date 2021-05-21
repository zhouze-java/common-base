package com.zhou.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 周泽
 * @date Create in 11:21 2021/3/22
 * @Description entity 相关工具类
 */
public class ModelUtils {

    public static final char UNDERLINE = '_';

    /**
     * 判断model中是否有某个字段
     * @param model
     * @param orderBy
     * @return
     */
    public static boolean ifParameterNull(Object model,String orderBy){
        boolean d=true;
        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = (Class)model.getClass();
        // 当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
            // 得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        //获取实体类的所有属性，返回Field数组
        //遍历所有属性
        for (Field f : fieldList) {
            //获取属性的名字
            String name = f.getName();
            if (name.equals(camelCaseName(orderBy))) {
                d=false;
                break;
            }
        }
        return d;
    }

    /**
     * 下划线转驼峰法
     * @param underscoreName
     * @return 转换后的字符串
     */
    public static String camelCaseName(String underscoreName) {
        if (underscoreName.contains("_")) {
            underscoreName = underscoreName.toLowerCase();
        }
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return null;
        }
        if (param.contains("_")) {
            return param;
        }

        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
