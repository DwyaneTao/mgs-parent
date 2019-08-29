package com.mgs.util;

import com.mgs.exception.ParameterException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtil {

	/**
	 * 默认格式 yyyy-MM-dd
	 */
	public static String defaultFormat = "yyyy-MM-dd";


	/**
	 * 默认格式 yyyy-MM-dd hh:mm:dd
	 */
	public static String hour_format = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 小程序支持苹果手机日期小时格式
	 */
	public static String appleFormat = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 小程序支持苹果手机日期格式
	 */
	public static String appleDefaultFormat = "yyyy/MM/dd";

	/**
	 * 将字符串转换为日期 (使用默认格式yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String date) {
		return stringToDate(date, defaultFormat);

	}

	/**
	 * 自定义格式将字符串转换为日期
	 * 
	 * @param date
	 *            字符串的日期
	 * @param format
	 *            格式(形如:yyyy-MM-dd)
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String date, String format) {
		try {
			if (!StringUtil.isValidString(date))
				return null;
			if (!StringUtil.isValidString(format))
				format = defaultFormat;

			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new ParameterException(e);
		}
	}

	/**
	 * 将日期转换为字符串 (使用默认格式yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, defaultFormat);
	}

	/**
	 * 自定义格式将日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (null == date)
			return null;
		if (!StringUtil.isValidString(format))
			format = defaultFormat;

		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		return sdf.format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date dateFormat(Date date, String format) {

		if (null == date)
			return null;

		if (!StringUtil.isValidString(format))
			format = defaultFormat;

		String formatDate = dateToString(date, format);

		return stringToDate(formatDate, format);
	}

	/**
	 * 对传入的时间进行加减天数/加减小时数
	 * 
	 * @param date
	 *            原始时间
	 * @param day
	 *            需要增加减少的天数
	 * @param hour
	 *            需要增加减少的小时数
	 * @return
	 */
	public static Date getDate(Date date, int day, int hour) {
		if (null != date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, day);
			calendar.add(Calendar.HOUR, hour);
			date = calendar.getTime();
		}
		return date;
	}
	/**
	 * 原始时间减少天  小时   分钟数
	 * @Title  getDate
	 * @author fengxiaozhen 2015-11-16 下午2:16:51
	 * @param date
	 * @param day
	 * @param hour
	 * @return Date
	 * 
	 */
	public static Date getDate(Date date, int day, int hour,int minute) {
		if (null != date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, day);
			calendar.add(Calendar.HOUR, hour);
			calendar.add(Calendar.MINUTE, minute);
			date = calendar.getTime();
		}
		return date;
	}
	
	public static Date getDate(Date date, int month) {
		if (null != date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, month);
			date = calendar.getTime();
		}
		return date;
	}

	/**
	 * 测试日期是否在某一段日期之间
	 * 
	 * @param date
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean between(Date date, Date start, Date end) {
		return 0 <= getDay(start, date) && 0 >= getDay(end, date);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取当前时间
	 * 1精确到小时分钟
	 * 2精确到日
	 * @return
	 */
	public static String getCurrentDateStr(int formatType) {
		String format = null;
		if (formatType == 1){
			format = hour_format;
		}else if(formatType == 2){
			format = defaultFormat;
		}

		return DateUtil.dateToString(getCurrentDate(),format);
	}

	/**
	 * 得到两个时间点相差的天数(date2-date1)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDay(Date date1, Date date2) {
		if (null == date1 || null == date2)
			return 0;
		return Long.valueOf((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24)).longValue();
	}


	/**
	 * 得到两个时间点相差的小时数(date2-date1)
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getHour(Date date1, Date date2) {
		if (null == date1 || null == date2)
			return 0;
		return Long.valueOf((date2.getTime() - date1.getTime()) / (1000 * 60 * 60)).longValue();
	}
	
	/**
	 * 得到两个时间点相差的分钟数(date2-date1)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getMinute(Date date1, Date date2) {
		if (null == date1 || null == date2)
			return 0;
		return Long.valueOf((date2.getTime() - date1.getTime()) / (1000 * 60)).longValue();
	}
	
	/**
	 * 获取两个时间段的秒数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getSecond(Date beginDate, Date endDate) {
		long time = endDate.getTime() - beginDate.getTime();
		long second = (time/1000);
		return second;
	}

	/**
	 * 比较两个时间点大小(精确到日期) </br> date1 > date2 返回1 </br> date1 = date2 返回0 </br> date1 <
	 * date2 返回-1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compare(Date date1, Date date2) {
		int resultCode = 0;
		long days = getDay(date1, date2);
		if (days < 0) {
			resultCode = 1;
		} else if (days > 0) {
			resultCode = -1;
		}
		return resultCode;
	}
	
	/**
	 * 比较两个时间点大小(精确到毫秒) </br> date1 > date2 返回1 </br> date1 = date2 返回0 </br> date1 <
	 * date2 返回-1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareInMS(Date date1, Date date2) {
		int resultCode = 0;	
		if (null == date1 || null == date2) {
			return resultCode;
		}
		long days = Long.valueOf((date2.getTime() - date1.getTime())).longValue();
		if (days < 0) {
			resultCode = 1;
		} else if (days > 0) {
			resultCode = -1;
		}
		return resultCode;
	}

	/**
	 * 日期的星期
	 * 
	 * @param date
	 *            Date
	 * @return int 1-7
	 */
	public static int getWeekOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return 0 == (calendar.get(Calendar.DAY_OF_WEEK) - 1) ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 返回日期星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekStrOfDate(Date date) {
		int week = getWeekOfDate(date);
		String weekStr = null;
		switch (week) {
		case 1:
			weekStr = "周一";
			break;
		case 2:
			weekStr = "周二";
			break;
		case 3:
			weekStr = "周三";
			break;
		case 4:
			weekStr = "周四";
			break;
		case 5:
			weekStr = "周五";
			break;
		case 6:
			weekStr = "周六";
			break;
		case 7:
			weekStr = "周日";
			break;
		}
		return weekStr;
	}

	/**
	 * 返回两个时间段的List
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static List<Date> getDateList(Date date1, Date date2) {
		List<Date> dateList = null;
		if (null == date1 || null == date2)
			dateList = Collections.emptyList();
		int days = ((Long) getDay(date1, date2)).intValue();
		if (days >= 0) {
			dateList = new ArrayList<Date>(days);
			for (int i = 0; i <= days; i++) {
				dateList.add(getDate(date1, i, 0));
			}
		} else {
			dateList = Collections.emptyList();
		}

		return dateList;
	}

	/**
	 * 返回两个时间段的List
	 * 日期满足传入星期
	 * 传入的星期0-6表示周日-周六
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static List<Date> getDateInWeekList(Date date1, Date date2, String weekStr) {
		List<Date> dateList = null;
		if (null == date1 || null == date2)
			dateList = Collections.emptyList();
		int days = ((Long) getDay(date1, date2)).intValue();
		if (days >= 0) {
			dateList = new ArrayList<Date>();
			for (int i = 0; i <= days; i++) {
				if (StringUtil.isValidString(weekStr)) {
					//传入的星期0-6表示周日-周六，满足星期的日期星期要减去1
					if (weekStr.contains(String.valueOf(getWeekOfDate(getDate(date1, i, 0)) == 7 ? 0 : getWeekOfDate(getDate(date1, i, 0))))) {
						dateList.add(getDate(date1, i, 0));
					}
				}else {
					dateList.add(getDate(date1, i, 0));
				}
			}
		} else {
			dateList = Collections.emptyList();
		}

		return dateList;
	}
	
	/**
	 * 返回两个时间段的Map
	 * 升序返回
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Map<Date,Date> getDateMap(Date date1, Date date2) {
		Map<Date, Date> map = new TreeMap<Date, Date>(
				new Comparator<Date>() {
					public int compare(Date obj1, Date obj2) {
						// 升序排序
						return obj1.compareTo(obj2);
					}
				});
		if (null == date1 || null == date2)
			map = Collections.emptyMap();
		int days = ((Long) getDay(date1, date2)).intValue();
		if (days >= 0) {
			for (int i = 0; i <= days; i++) {
				map.put(dateFormat(getDate(date1, i, 0),"yyyy-MM-dd"),dateFormat(getDate(date1, i, 0),"yyyy-MM-dd"));
			}
		} else {
			map = Collections.emptyMap();
		}

		return map;
	}
	
	/**
	 * 获取两个时间段的分钟数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getMinsBetweenDate(Date beginDate,Date endDate){
		long time=endDate.getTime()-beginDate.getTime();
		long min=(time/(60*1000));
		return min;
	}
	
	/**
	  * 根据某个日期得到前一天日期
	  * @param d
	  * @return
	  */
	 public static Date getBeforeDate(Date d){
	  Date date = null;
	  Calendar calendar = Calendar.getInstance();  
	  calendar.setTime(d);  
	  calendar.add(Calendar.DAY_OF_MONTH,-1);  
	  date = calendar.getTime();  
	  return date;
	 }
	 
	 /**
	  * 得到某个日期的后一天日期
	  * @param d
	  * @return
	  */
	 public static Date getAfterDate(Date d){
	  Date date = null;
	  Calendar calendar = Calendar.getInstance();  
	  calendar.setTime(d);  
	  calendar.add(Calendar.DAY_OF_MONTH,1);  
	  date = calendar.getTime();  
	  return date;
	 }
	 
	    /**
	     * 日期是否符合某一星期
	     * 
	     * @param date
	     * @param week
	     * @return
	     */
	    public static boolean isMatchWeek(Date date, int week) {
	        return getWeekOfDate(date) == week;
	    }

	/**
	 * 获取日期的时间点
	 * 转换前日期格式为：yyyy-MM-dd HH:mm
	 * @param date
	 * @return
     */
	public static String getDateTime(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(stringToDate(date,"yyyy-MM-dd HH:mm"));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		return (hour<10?("0"+hour):hour)+":"+(minute<10?("0"+minute):minute);
	}


	/**
	 * 获取2个时间的日期差，小时差，分钟差
	 * 结果：1天2时10分
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String getDayHourMinuteFormat(Date date1,Date date2){

		long allMinutes = DateUtil.getMinute(date1,date2);
		long days = allMinutes/60/24;
		long hours = allMinutes/60 - days*24;
		long minutes = allMinutes - hours*60 - days*60*24;
		String dateStr = String.valueOf(days)+"天"+String.valueOf(hours)+"时"+String.valueOf(minutes)+"分";
		return dateStr;
	}

	/**
	 * 得到某个日期的后一天日期
	 * @param d
	 * @return
	 */
	public static Date addDate(Date d, int days){
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获取当前月的第一天
	 * 日期格式：yyyy-MM-dd
	 * @return
     */
	public static String getFirstDayOfThisMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//获取前月的第一天
		Calendar   cal_1=Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		return format.format(cal_1.getTime());
	}

	/**
	 * 取当前月的最后一天
	 * 日期格式：yyyy-MM-dd
	 * @return
     */
	public static String getLastDayOfThisMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//获取前月的最后一天
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
		return format.format(cale.getTime());

	}

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//获取前月的第一天
		Calendar   cal_1=Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());
		System.out.println("-----1------firstDay:"+firstDay);

		//获取前月的最后一天
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
		String lastDay = format.format(cale.getTime());
		System.out.println("-----2------lastDay:"+lastDay);

		System.out.println(getFirstDayOfThisMonth());
		System.out.println(getLastDayOfThisMonth());

	}

}
