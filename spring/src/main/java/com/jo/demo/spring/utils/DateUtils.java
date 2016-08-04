package com.jo.demo.spring.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期处理
 * 
 * @author wujun
 * @date 2014-5-8
 */
public class DateUtils {

    public static final String STD_FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    
    public static final String STD_DATE = "yyyy-MM-dd";
    
    public static final String FULL_DATE_TIME = "yyyyMMddHHmmss";
    
    public static final String DATE = "MMdd";
    
    public static final String TIME = "HHmmss";
    
    public static final String HALF_DATE_TIME = "yyMMddHHmmss";
    
    public static final String SLASH_FULL_DATE_TIME = "yyyy/MM/dd HH:mm:ss";
    
    public static final DateTimeFormatter STD_DATETIME_FORMATTER = DateTimeFormat.forPattern(STD_FULL_DATE_TIME);
    public static final DateTimeFormatter STD_DATE_FORMATTER     = DateTimeFormat.forPattern(STD_DATE);
    public static final DateTimeFormatter FULL_DATE_TIME_FOMATTER = DateTimeFormat.forPattern(FULL_DATE_TIME);
    
    
    //------------------------------DateTime ==============> String---------------------------------//
    
    public static String getFullDateTime(){
      return new DateTime().toString(FULL_DATE_TIME);
    }
    
    public static String getFullDateTime(DateTime dateTime){
      return dateTime.toString(FULL_DATE_TIME);
    }
    
    public static String getStdFullDateTime(){
      return new DateTime().toString(STD_FULL_DATE_TIME);
    }
    
    public static String getStdFullDateTime(DateTime dateTime){
      return dateTime.toString(STD_FULL_DATE_TIME);
    }
    
    public static String getSlashFullDateTime(){
      return new DateTime().toString(SLASH_FULL_DATE_TIME);
    }
    
    public static String getSlashFullDateTime(DateTime dateTime){
      return dateTime.toString(SLASH_FULL_DATE_TIME);
    }
    
    public static String transByDate(Date time, String format){
      return new DateTime(time).toString(format);
    }
    
    
    //------------------------------String ==============> DateTime---------------------------------//
    
    /**
     * 
     * @param dateTime
     * @param formatter
     * @return
     */
    public static DateTime getDateTime(String dateTime, DateTimeFormatter formatter){
      return DateTime.parse(dateTime, formatter);
    }
    
    public static DateTime addDays(String dateTime, DateTimeFormatter formatter, int days){
      return getDateTime(dateTime, formatter).plusDays(days);
    }
    
    public static void main(String[] args) {
      String full = getFullDateTime();
      System.out.println(full);
      String std_full = getStdFullDateTime();
      System.out.println(std_full);
      String slash_full = getSlashFullDateTime();
      System.out.println(slash_full);
      
      DateTime time = DateTime.parse("19911002131212", FULL_DATE_TIME_FOMATTER);
      System.out.println(time.toString());
      
      
    }
    
}