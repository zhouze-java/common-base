/**
 *
 * Copyright 2010 boosj.com, Inc. All rights reserved.
 */
package com.zhou.common.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期帮助类
 */
public class DateUtils {
	
	private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");  
	private final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
	private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * @param stringDate
	 * @return 线程安全
	 */
	public static final Date string2Date(String format, String stringDate) {
		if (stringDate == null) {
			return null;
		}
		try {
			// SimpleDateFormat 不是线程安全，所以使用每次都创建变量。
			return new SimpleDateFormat(format).parse(stringDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 时间转字符串, 线程安全的
	 * @param format
	 * @param date
	 * @return
	 */
	public static final String dateFormat(String format, Date date) {
		if (date == null) {
			return "";
		}
		if (format == null || format.length() <= 0){
			format = "yyyy-MM-dd";
		}
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 格式为：yyyy-MM/dd/HHmmssSSS 
	 * @param date
	 * @return yyyy-MM/dd/HHmmssSSS
	 */
	public static final String dateFormatPathName(Date date) {
		return new SimpleDateFormat("yyyy-MM/dd").format(date);
	}

	/**
	 * 时间戳转string 线程安全
	 * @param timeMillis
	 * @return
	 */
	public static final String dateTimeFormat(Long timeMillis) {
		if(timeMillis == null){
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillis));
	}
	 
	public static final String simpleFormat(String date) {
		String ret = null;
		if (date != null) {
			String[] d1 = StringUtils.tokenizeToStringArray(date, "-");
			if (d1.length > 0) {
				ret = d1[0] + "年";
			}
			if (d1.length > 1) {
				ret += d1[1] + "月";
			}
			if (d1.length > 2) {
				ret += d1[2] + "日";
			}
		}
		return ret;
	}
	
	public static final String getTwoDay(Date date1, Date date2) {
		// 定义日期的输入格式
		int day = 0;
		try {
			// 间隔的毫秒数转为天
			day = (int)((date1.getTime() - date2.getTime())/86400000);
		} catch (Exception e) {
			// 转换失败不抛出异常而是返回空
			return "";
		}
		return day+"";
	}

		public static Date getCurrentQuarterStartTime() {  
	        Calendar c = Calendar.getInstance();  
	        int currentMonth = c.get(Calendar.MONTH) + 1;  
	        Date now = null;  
	        try {  
	            if (currentMonth >= 1 && currentMonth <= 3)  
	                c.set(Calendar.MONTH, 0);  
	            else if (currentMonth >= 4 && currentMonth <= 6)  
	                c.set(Calendar.MONTH, 3);  
	            else if (currentMonth >= 7 && currentMonth <= 9)  
	                c.set(Calendar.MONTH, 4);  
	            else if (currentMonth >= 10 && currentMonth <= 12)  
	                c.set(Calendar.MONTH, 9);  
	            c.set(Calendar.DATE, 1);  
	            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return now;  
	    }  
	  
	    /** 
	     * 当前季度的结束时间 
	     * 
	     * @return 
	     */  
	    public static Date getCurrentQuarterEndTime() {  
	        Calendar c = Calendar.getInstance();  
	        int currentMonth = c.get(Calendar.MONTH) + 1;  
	        Date now = null;  
	        try {  
	            if (currentMonth >= 1 && currentMonth <= 3) {  
	                c.set(Calendar.MONTH, 2);  
	                c.set(Calendar.DATE, 31);  
	            } else if (currentMonth >= 4 && currentMonth <= 6) {  
	                c.set(Calendar.MONTH, 5);  
	                c.set(Calendar.DATE, 30);  
	            } else if (currentMonth >= 7 && currentMonth <= 9) {  
	                c.set(Calendar.MONTH, 8);  
	                c.set(Calendar.DATE, 30);  
	            } else if (currentMonth >= 10 && currentMonth <= 12) {  
	                c.set(Calendar.MONTH, 11);  
	                c.set(Calendar.DATE, 31);  
	            }  
	            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return now;  
	    }

	/**
	 * 计算时间差
	 * @param date1 当前时间
	 * @param date2 计算的时间
	 * @return
	 */
	public static int differentDays(Date date1, Date date2) {
		// 付款时间减去当前时间
		int dayCount = (int)(( date2.getTime() - date1.getTime()) / (1000*3600*24));
		if (( date2.getTime() - date1.getTime()) % (1000*3600*24) > 0 ){
			dayCount++;
		}
		return dayCount;
	}

	/**
	 * 字符串日期转为时间戳
	 * @param date 时间
	 * @return 时间戳
	 */
	public static long StringToLong(String date){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime parse = LocalDateTime.parse(date, dtf);
		return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * 字符串日期转为时间戳
	 * @param date 时间
	 * @return 时间戳
	 */
	public static long stringToLongMin(String date) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date).getTime();
	}
	    
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	//得到一个月的开始时间与结束时间
	public static Date getBeginTime(int year, int month) {
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate localDate = yearMonth.atDay(1);
		LocalDateTime startOfDay = localDate.atStartOfDay();
		ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));

		return Date.from(zonedDateTime.toInstant());
	}

	public static Date getEndTime(int year, int month) {
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate endOfMonth = yearMonth.atEndOfMonth();
		LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * date转localDate
	 * @param date
	 * @return
	 */
	public static LocalDate dateToLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();

		// atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
		return instant.atZone(zoneId).toLocalDate();
	}

	/**
	 * date转localDate
	 * @return
	 */
	public static Date localDateToDate(LocalDate localDate) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDate.atStartOfDay(zoneId);

		return Date.from(zdt.toInstant());
	}

	/**
	 * localDateTime to Date
	 *
	 * @param localDateTime
	 * @return
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * localDate 转时间戳
	 * @param localDate
	 * @return
	 */
	public static Long localDateToTimestamp(LocalDate localDate) {
		return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
	}

	/**
	 * localDateTime 转时间戳
	 * @param localDateTime
	 * @return
	 */
	public static Long localDateTimeToTimestamp(LocalDateTime localDateTime){
		return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	/**
	 * 时间戳转localdatetime
	 * @param timestamp
	 * @return
	 */
	public static LocalDateTime timestampToLocalDateTime(Long timestamp) {
		Instant instant = Instant.ofEpochMilli(timestamp);
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}


	public static long diffDays(LocalDate startLocalDate, LocalDate endLocalDate) {
		return startLocalDate.until(endLocalDate, ChronoUnit.DAYS);
	}

	public static long diffMonth(LocalDate startLocalDate, LocalDate endLocalDate) {
		return startLocalDate.until(endLocalDate, ChronoUnit.MONTHS);
	}

	public static long diffDays(Date start, Date end) {
		return diffDays(dateToLocalDate(start), dateToLocalDate(end));
	}

	public static long diffMonth(Date start, Date end) {
		return diffMonth(dateToLocalDate(start), dateToLocalDate(end));
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(diffDays(new Date(1606752000000L),new  Date(1606838400000L)));
	}

	/**
	 * 获取两个时间中间隔了几个月几天
	 * @param start
	 * @param end
	 * @return [月数,天数]
	 */
	public static int[] getDiff(LocalDate start, LocalDate end) {
		if (!start.isBefore(end)) {
			throw new IllegalArgumentException("Start must not be before end.");
		}

		Period period = Period.between(start, end);

		int years = period.getYears();
		int months = period.getMonths();
		int days = period.getDays();

		return new int[] {years * 12 + months, days};
	}

	/**
	 * 时间戳转localDate
	 * @param timestamp
	 * @return
	 */
	public static LocalDate timestampToLocalDate(Long timestamp){
		return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
	}

	/**
	 * 判断时间是否在时间段内
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, String beginTime, String endTime) {
		// 设置日期格式
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date now =null;
		Date beginTimeDate = null;
		Date endTimeDate = null;
		try {
			now = df.parse(df.format(nowTime));
			beginTimeDate = df.parse(beginTime);
			endTimeDate = df.parse(endTime);
		} catch (Exception e) {
			return false;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(now);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTimeDate);

		Calendar end = Calendar.getInstance();
		end.setTime(endTimeDate);

		return date.after(begin) && date.before(end);
	}
	/**
	 * 获取两个时间节点之间的月份列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public static List<String> getMonthBetween(Date startDate, Date endDate) {
		ArrayList<String> resultList = new ArrayList<>();
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		min.setTime(startDate);
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(endDate);
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			resultList.add(shortSdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}
		return resultList;
	}
	/**
	 * 获取两个时间节点之间的日期列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public static List<String> getDayBySpace(Date startDate, Date endDate) {
		List<String> days = new ArrayList<>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(startDate);
		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(endDate);
		// 日期加1(包含结束)
		tempEnd.add(Calendar.DATE, 0);
		while (tempStart.before(tempEnd)) {
			days.add(DateUtils.dateFormat("yyyy-MM-dd", tempStart.getTime()));
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return days;
	}
}
