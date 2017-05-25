package com.yandong.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by yandong on 2017/5/22.
 */
public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date stringToDate(String dateString,String formatString){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatString);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateString);
        return dateTime.toDate();
    }

    public static String dateToString(Date date,String formatString){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatString);
    }

    public static Date stringToDate(String dateString){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateString);
        return dateTime.toDate();
    }

    public static String dateToString(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

}
