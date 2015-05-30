package com.jun.financialrecords.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName: TimeUtils
 * @Description: 时间帮助类
 * @author shark
 * @date 2014年12月23日 下午4:23:34 
 *
 */
@SuppressLint("SimpleDateFormat") public class TimeUtils {
	public static String TAG = "TimeUtils";

	/** String型当前时间   yyyy-MM-dd HH:mm:ss*/
	public static String getNowTime(){

		Date dt = new Date();							//如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置显示格式
		String nowTime = "";
		nowTime = df.format(dt);						//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
		return nowTime;
	}

	public static String getTimeByDate(Date dt){

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置显示格式
		String nowTime = "";
		nowTime = df.format(dt);						//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
		return nowTime;
	}
	/**
	 * 2014-12-10 14:14:59.0   ---->   刚刚、6分钟前、5小时前、昨天 13:55、12月10日 13:55
	 * @return
	public static String ChangeTimeFormat_MMDD(Context context, String timeStr) {
		if (timeStr == null || timeStr.equals("")) {
			return "";
		}
		String sFormat;    
		String sStr;
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");     
	    Date now = new Date();   
	    Date date;
	    Date nowdd;

	    try {
			date = df.parse(timeStr);
			nowdd = df.parse(df1.format(now) + " 00:00:00");
			long dif = nowdd.getTime() + ( 24 * 60 * 60 * 1000) - date.getTime();
		    long daydd = dif / ( 24 * 60 * 60 * 1000);     
			
		    if (daydd == 1) {
		    	String str = context.getResources().getString(R.string.time_utils_yesterday);    
		    	String str1 = timeStr.substring(timeStr.indexOf(" ") + 1, timeStr.length());
				String [] str2 = str1.split(":");
				str = str + " " + str2[0] + ":" + str2[1];
		    	return str;		//昨天 12:03
			} else {
		    
				long l = now.getTime() - date.getTime();     
			    long day = l / ( 24 * 60 * 60 * 1000);     
			    long hour = (l / (60 * 60 * 1000) - day * 24);     
			    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);     
//			    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);  
//			    String str3 = day + "天" + hour + "小时" + min + "分" + s + "秒";
//			    Logger.w(TAG, str3);
			    
			    if (day == 0) {
					if (hour == 0) {
						if (min <= 5) {
							return context.getResources().getString(R.string.time_utils_just_now);
						} else {
							sFormat = context.getResources().getString(R.string.time_utils_min_before);    
							sStr = String.format(sFormat, min);    
							return sStr;
						}
					} else {
						sFormat = context.getResources().getString(R.string.time_utils_hour_before);    
						sStr = String.format(sFormat, hour);    
						return sStr;
					}
				} else {
					String strDay = timeStr.substring(0, timeStr.indexOf(" "));
					
					String strTime = timeStr.substring(timeStr.indexOf(" ") + 1, timeStr.length());
					String [] strTime2 = strTime.split(":");
					
					String [] str1 = strDay.split("-");
					strDay = str1[1] + "月" + str1[2] + "日" + " " + strTime2[0] + ":" + strTime2[1];;
					return strDay;
				}
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	    
		return timeStr;
	}
	*//**String型当前时间   yyyy-MM-dd HH:mm:ss*//*
	public static String getNowTime(){
		
		Date dt = new Date();							//如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
		String nowTime = "";
		nowTime = df.format(dt);						//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
		return nowTime;
	}
	
	*//**long型当前时间 *//*
	public static long getNowTimeLong(){
		
		Date dt = new Date();							//如果不需要格式,可直接用dt,dt就是当前系统时间
		return dt.getTime();
	}
	
	*//**
	 * 时间差
	 * @return
	 * @throws ParseException 
	 *//*
	public static String getDateDiff(String timeStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");     
	    Date now = new Date();     
	    Date date;
		try {
			date = df.parse(timeStr);
			long l = now.getTime() - date.getTime();     
		    long day = l / ( 24 * 60 * 60 * 1000);     
		    long hour = (l / (60 * 60 * 1000) - day * 24);     
		    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);     
		    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);  
		    String str = day + "天" + hour + "小时" + min + "分" + s + "秒";
			return str;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
		return timeStr;
	}
	
	*//**
	 * 2014-12-10
	 * @param time
	 * @return 12月10日
	 *//*
	public static String getTimeFormat_MMDD(String timeStr) { 
		if (timeStr == null || timeStr.equals("")) {
			return "";
		}
		String str = "";
		String [] str1 = timeStr.split("-");
		str = str1[1] + "月" + str1[2] + "日";
	    
		return str;
	}
	
	*//**
	 * 
	 * @param timeOne
	 * @param timeTwo
	 * @return  timeOne > timeTwo 1
	 * 			timeOne = timeTwo 0
	 * 			timeOne < timeTwo -1
	 *//*
	public static int compareTime(String timeOne, String timeTwo) { 
		if (timeOne == null || timeOne.equals("") ) {
			return -1;
		}
		if ( timeTwo == null || timeTwo.equals("")) {
			return 1;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
		
		try {
			Date timeOneData = df.parse(timeOne);		
			Date timeTwoData = df.parse(timeTwo);
			long l = timeOneData.getTime() - timeTwoData.getTime(); 
			if (l > 0) {
				return 1;
			} else if (l == 0) {
				return 0;
			} else if (l < 0) {
				return -1;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return 1;
	}
	
	*//** 获取月份缩写 *//*
	public static String getMonthAbbreviation(int month) {
		String monthString = null;
		switch (month) {
		case 1:
			monthString = "Jan";
			break;
		case 2:
			monthString = "Feb";
			break;
		case 3:
			monthString = "Mar";
			break;
		case 4:
			monthString = "Apr";
			break;
		case 5:
			monthString = "May";
			break;
		case 6:
			monthString = "Jun";
			break;
		case 7:
			monthString = "Jul";
			break;
		case 8:
			monthString = "Aug";
			break;
		case 9:
			monthString = "Sep";
			break;
		case 10:
			monthString = "Oct";
			break;
		case 11:
			monthString = "Nov";
			break;
		case 12:
			monthString = "Dec";
			break;
		default:
			monthString = "";
			break;
		}
		return monthString;
	}
	
	*//**
	 * 获取你设置的那年那月那日是星期几
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 *//*
	public static int getWeekDay(int year,int month,int day){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		int eachDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		return eachDayOfWeek;
	}
	
	*//** 获取月份缩写 *//*
	public static String getWeekAbbreviation(int week) {
		String monthString = null;
		switch (week) {
		case 1:
			monthString = "Monday";
			break;
		case 2:
			monthString = "Tuesday";
			break;
		case 3:
			monthString = "Wednesday";
			break;
		case 4:
			monthString = "Thursday";
			break;
		case 5:
			monthString = "Friday";
			break;
		case 6:
			monthString = "Saturday";
			break;
		case 0:
			monthString = "Sunday";
			break;
		default:
			monthString = "";
			break;
		}
		return monthString;
	}
	
	*//**
	 * 获取年月日
	 *  @param time  2015-03-20
	 * 	@param type
	 *//*
	public static String getValueOfTimeType(String time, String type) {
		String []value = time.split("-");
		if (type.equals("year")) {
			return value[0];
		} else if (type.equals("month")) {
			return value[1];
		} else if (type.equals("day")) {
			return value[2];
		} 
		return null;
	}
	
	*//** 获取指定格式日期：一月    2015  *//*
	public static String getShowTimeTx(Context context, int month){
		String time = "";
		switch (month) {
		case 1:
			time = context.getResources().getString(R.string.january);
			break;
		case 2:
			time = context.getResources().getString(R.string.february);
			break;
		case 3:
			time = context.getResources().getString(R.string.march);
			break;
		case 4:
			time = context.getResources().getString(R.string.april);
			break;
		case 5:
			time = context.getResources().getString(R.string.may);
			break;
		case 6:
			time = context.getResources().getString(R.string.june);
			break;
		case 7:
			time = context.getResources().getString(R.string.july);
			break;
		case 8:
			time = context.getResources().getString(R.string.august);
			break;
		case 9:
			time = context.getResources().getString(R.string.september);
			break;
		case 10:
			time = context.getResources().getString(R.string.october);
			break;
		case 11:
			time = context.getResources().getString(R.string.november);
			break;
		case 12:
			time = context.getResources().getString(R.string.december);
			break;
		default:
			break;
		}
		return time;
	}*/
}
