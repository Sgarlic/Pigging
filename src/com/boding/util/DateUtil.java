package com.boding.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 
	 * @param orgin yyyy-m-d or yyyy-mm-d or yyyy-m-dd or yyyy-mm-dd
	 * @return yyyy-mm-dd
	 */
	public static String formatDateString(String orgin){
		String[] parts = orgin.split("-");
		if(parts.length != 3) return "1991-03-22"; 
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		sb.append("-");
		if(parts[1].length() < 2)
			sb.append("0");
		sb.append(parts[1]).append("-");
		if(parts[2].length() < 2)
			sb.append("0");
		sb.append(parts[2]);
		return sb.toString();
	}
	
	/**
	 * 输入年，月，日
	 * 返回yyyy-mm-dd
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @return
	 */
	public static String getFormatedDate(int year,int month,int dayOfMonth){
		month+=1;
		String selectedDate = "%s-%02d-%02d";
		return String.format(
				selectedDate, String.valueOf(year),month,dayOfMonth);
	}
	
	public static String getFormatedDate(Calendar calendar){
		if(calendar == null)
			return "";
		return getFormatedDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	public static String getFormatedDate(Date date){
		if(date == null)
			return "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getFormatedDate(calendar);
	}
	
	public static String getYearMonthString(Calendar calendar){
		String dateFormat = "%s年%02d";
		return String.format(dateFormat, String.valueOf(calendar.get(Calendar.YEAR)),calendar.get(Calendar.MONTH));
	}
	
	public static String getYearMonthString(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getYearMonthString(calendar);
	}
	
	public static long getMillIsFromDate(String date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String[] temp = date.split("-");
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		newDate.setYear(Integer.valueOf(temp[0]));
		return newDate.getTime();
	}
	
	public static Date getDateFromString(String date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		newDate.setYear(Integer.valueOf(temp[0]));
		return newDate;
	}
	
	//给时间加上冒号， 如0322 -> 03:22
	public static String formatTime(String time){
		return time.substring(0, 2) + ":" + time.substring(2);
	}
		
	public static String addDayToCalendarString(String calendarString, int dayCount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDateFromString(calendarString));
		calendar.add(Calendar.HOUR, dayCount*24);
		return getFormatedDate(calendar);
	}
		
	/**
	 * Compare two date's year and month.
	 * return 1 if date1 > date2
	 * return 0 if date1 == date2
	 * return -1 if date1 < date2
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDateString(String dateString1, String dateString2){
		Date date1 = getDateFromString(dateString1);
		Date date2 = getDateFromString(dateString2);
		int yyyymmdd1 = date1.getYear()*10000 + date1.getMonth()*100 + date1.getDate();
		int yyyymmdd2 = date2.getYear()*10000 + date2.getMonth()*100 + date2.getDate();
		
		if(yyyymmdd1 > yyyymmdd2)
			return 1;
		else if(yyyymmdd1 == yyyymmdd2)
			return 0;
		return -1;
	}
	
	public static Date getNextDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	
	public static Date getNextDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	
	public static String getNextDateString(){
		return getFormatedDate(getNextDate());
	}
	
	/**
	 * 
	 * @param day  yyyy-mm-dd
	 * @return lastday yyyy-mm-dd
	 */
	public static String getLastDay(String day){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDateFromString(day));
		calendar.add(Calendar.DATE, -1);
		return getFormatedDate(calendar);
	}
	
	/**
	 * 
	 * @param day yyyy-mm-dd
	 * @return nextday yyyy-mm-dd
	 */
	public static String getNextDay(String day){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDateFromString(day));
		calendar.add(Calendar.DATE, 1);
		return getFormatedDate(calendar);
	}
	
	public static boolean isDayGone(String day){
		Date date = getDateFromString(day);
		date = getNextDate(date);
		Date today = new Date();
		return date.before(today);
	}
	
	/**
	 * 输入mmss	分秒
	 * 返回mm:ss	分:秒
	 * eg. 输入2000
	 * 返回20:00
	 * @param time
	 * @return
	 */
	public static String getFormatedTime(String time){
		return time.substring(0,2)+":"+time.substring(2,4);
	}
	
	
	/**
	 * 输入mm:ss
	 * 返回mm小时ss分钟
	 * eg. 输入2:25
	 * 返回2小时25分钟
	 * @param duration
	 * @return
	 */
	public static String getFormatedDuration(String duration){
		String[] tempArray = duration.split(":");
		return tempArray[0]+"小时"+tempArray[1]+"分钟";
	}
}
