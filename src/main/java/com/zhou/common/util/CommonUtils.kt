package com.zhou.common.util

import java.math.BigDecimal
import java.net.URLDecoder

/**
 * @author  周泽
 * @date Create in 11:10 2020/11/6
 * @Description 通用工具类,一些Java比较麻烦的东西 用kotlin做
 */

/**
 * 获取bigDecimal集合的最大值
 */
fun getMax(list:List<BigDecimal>): BigDecimal? = list.max()

/**
 * 获取bigDecimal集合的最小值
 */
fun getMin(list: List<BigDecimal>): BigDecimal? = list.min()

/**
 * 获取bigDecimal集合的总和
 */
fun getSum(list: List<BigDecimal>): BigDecimal? = list.fold(BigDecimal.ZERO, BigDecimal::add)

/**
 * 转义关键字
 */
fun tranKeyword(keyword: String) =  keyword.replace("[", "\\[").replace("_", "\\_").replace("%", "\\%")


