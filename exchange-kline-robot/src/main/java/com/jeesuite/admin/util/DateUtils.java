package com.jeesuite.admin.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * description:日期类
 * author:pc
 * date:2018/5/30
 */
public class DateUtils extends com.jeesuite.common.util.DateUtils {

    public static final String DATE_PATTERN_YMD = "yyyyMMdd";

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取当前日期字符串（yyyy-MM-dd HH:mm:ss）
     *
     * @return
     */
    public static String getNowString() {
        SimpleDateFormat simple = new SimpleDateFormat(TIMESTAMP_PATTERN);
        return simple.format(getNow());
    }

    public static String getNowString(String pattern) {
        if (!org.apache.commons.lang3.StringUtils.isNoneBlank(pattern)) {
            pattern = TIMESTAMP_PATTERN;
        }
        SimpleDateFormat simple = new SimpleDateFormat(pattern);
        return simple.format(getNow());
    }

    public static String getNowStringYmd() {
        SimpleDateFormat simple = new SimpleDateFormat(DATE_PATTERN);
        return simple.format(getNow());
    }

    public static String getNowStringYmd(Date date) {
        SimpleDateFormat simple = new SimpleDateFormat(DATE_PATTERN);
        return simple.format(date);
    }

    public static long getDiffSeconds2(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / 1000L;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStamp2DateString(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_PATTERN);
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 获取UTC格式时间
     *
     * @param date
     * @return
     */
    public static String getUTCTimeString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simple.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simple.format(date);
    }

    /**
     * 获取当前日期前后推移几分钟
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date getNextDateAddMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        date = calendar.getTime();
        return date;
    }

    public static Date getNextDateAddDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        date = calendar.getTime();
        return date;
    }

    public static Date getNextDate(long times) {
        return new Date(System.currentTimeMillis() + times);
    }

}
