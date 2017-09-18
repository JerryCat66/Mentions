package com.fenguo.library.util;

import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 日期、时间
 *
 * @author zhangyb@ifenguo.com
 * @createDate 2015年3月10日
 */
public class DateUtil {

    public static final String yyMMdd = "yy-MM-dd";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String HHmmss = "HH:mm:ss";
    public static final String HHmm = "HH:mm";
    public static final String yyMMddHHmmss = "yy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static final String yyMMddHHmm = "yy-MM-dd HH:mm";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String MMddHHmm = "MM-dd HH:mm";
    public static final String MMddHHmmss = "MM-dd hh:mm:ss";

    public static final String MMdd = "MM-dd";
    public static final String yyyyMM = "yyyy-MM";

    public static final String yyyymm = "yyyy-MM";

    public static Date parseToDate(String s, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        if (s == null || s.length() < 5)
            return null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param @param  日期字符串
     * @param @param  日期字符串的格式，yyyyMdd等
     * @param @return 设定文件
     * @return long 返回类型
     * @throws
     * @Title: parseToDateLong
     * @Description: 将日期字符串转化为时间戳
     */
    public static long parseToDateLong(String s, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        if (s == null || s.length() < 5)
            return 0;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static Date parseToTimesDate(String sTime) {
        Date date = null;
        if (sTime == null || "".equals(sTime)) {
            return null;
        }
        date = Timestamp.valueOf(sTime);
        return date;
    }

    public static String parseToString(String s, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        String str = null;
        if (s == null || s.length() < 8)
            return null;
        try {
            date = simpleDateFormat.parse(s);
            str = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseToString(Date date, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        String str = null;
        if (date == null)
            return null;
        str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getNowTime
     * @Description: 获取当前的日期，并以“yyyy-MM-dd HH:mm:ss”的形式显示
     */
    public static String getNowTime() {
        Date nowDate = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = formatter.format(now.getTime());
        return str;
    }

    /**
     * @param @param  curentTime
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: parseToString
     * @Description: 将时间戳转化为时间格式的字符串，默认格式为年月日 时分秒
     */
    public static String parseToString(long curentTime) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(curentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = formatter.format(now.getTime());
        return str;

        // Date date = new Date(curentTime);
        // SimpleDateFormat format = new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // return format.format(date);
    }

    public static String parseToStringWithLineBreak(long curentTime) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(curentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = formatter.format(now.getTime());
        // String[] arrays = str.split("\\s+");
        str = str.replace("\\s", "\n");
        return str;
    }

    /**
     * @param @param  curentTime
     * @param @param  style
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: parseToString
     * @Description: 将时间戳转化为指定格式（style）的时间字符串
     */
    public static String parseToString(long curentTime, String style) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(curentTime);
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        String str = formatter.format(now.getTime());
        return str;
    }

    public static String parseToHHString(long curentTime) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(curentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        String str = formatter.format(now.getTime());
        return str;
    }


    public static String parseTommString(long curentTime) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(curentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("mm");
        String str = formatter.format(now.getTime());
        return str;
    }

    public static String parseToyyyyMMString(long curentTime) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(curentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String str = formatter.format(now.getTime());
        return str;
    }

    public static String parseToMMString(long curentTime) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(curentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        int month = Integer.valueOf(formatter.format(now.getTime()));
        return String.valueOf(month) + "月";
    }


    public static String parseToDate(long time) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String str = formatter.format(now.getTime());
        return str;
    }

    public static String parseToMD(long time) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(time);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String str = formatter.format(now.getTime());
        return str;
    }

    /**
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getNowShortTime
     * @Description: 获取当前的日期，并以“yyyy-MM-dd”的形式显示
     */
    public static String getNowShortTime() {
        Date nowDate = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String str = formatter.format(now.getTime());
        return str;
    }

    public static String getNextDate(String ts, int i) {
        Calendar now = Calendar.getInstance();
        Timestamp t = Timestamp.valueOf(ts + " 00:00:00");
        now.setTime(t);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        now.add(Calendar.DAY_OF_MONTH, +(i));
        String dt = formatter.format(now.getTime());
        return dt;
    }

    /**
     * @param @param  ts
     * @param @param  i
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getNextMonth
     * @Description: 获取某个日期一个月前的日期，如获取2014-12-11的前一个月的日期为201411-11
     */
    public static String getNextMonth(String ts, int i) {
        Calendar now = Calendar.getInstance();
        Timestamp t = Timestamp.valueOf(ts + " 00:00:00");
        now.setTime(t);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        now.add(Calendar.MONTH, +(i));
        String dt = formatter.format(now.getTime());
        return dt;
    }

    public static String getNextMonth(int i) {
        return getNextMonth(DateUtil.getNowShortTime(), i);
    }

    public static String getNextTime(String ts, int i) {
        Calendar now = Calendar.getInstance();
        Timestamp t = Timestamp.valueOf(ts);
        System.out.println(t + "     " + ts);
        now.setTime(t);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now.add(Calendar.MINUTE, +(i));
        String dt = formatter.format(now.getTime());
        return dt;
    }

    public static void formatDateTime(Map<String, String> map, String key, String style) {
        String dateTime = StringUtil.toString(map.get(key));
        if (!"".equals(dateTime)) {
            Timestamp tTamp = Timestamp.valueOf(dateTime);
            SimpleDateFormat formatter = new SimpleDateFormat(style);
            String f_dateTime = formatter.format(tTamp);
            map.put(key, f_dateTime);
        }
    }

    public static long formatDateMills(String s, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        if (s == null || s.length() < 5)
            return 0;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getSendTimeDistance1(long sendTime) {
        String timeDistance = "";
        // 时间差
        long time = System.currentTimeMillis() - sendTime;
        // 判断时间是否大于一天
        if (time < (24 * 60 * 60 * 1000)) {
            // 判断时间是否大于一小时
            if (time > (60 * 60 * 1000)) {
                timeDistance = DateUtil.parseToHHString(time) + "小时前";
            } else {
                timeDistance = DateUtil.parseTommString(time) + "分钟前";
            }
        } else {
            timeDistance = DateUtil.parseToMD(sendTime);
        }
        return timeDistance;
    }

    public static String getSendTimeDistance(long sendTime) {
        String timeDistance = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d2 = df.parse(parseToString(sendTime));
            Date d1 = df.parse(parseToString(System.currentTimeMillis()));
            long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别

            // 判断时间是否大于一天
            if (diff < (24 * 60 * 60 * 1000)) {
                // 判断时间是否大于一小时
                if (diff > (60 * 60 * 1000)) {
                    timeDistance = DateUtil.parseToHHString(diff) + "小时前";
                } else {
                    timeDistance = DateUtil.parseTommString(diff) + "分钟前";
                }
            } else {
                timeDistance = DateUtil.parseToMD(sendTime);
            }
        } catch (Exception e) {
            Log.e("", "计算时间错误");
        }
        return timeDistance;
    }


    /**
     * 求两个日期相差天数
     *
     * @param sd 起始日期，格式yyyy-MM-dd
     * @param ed 终止日期，格式yyyy-MM-dd
     * @return 两个日期相差天数
     */
    public static long getIntervalDays(String sd, String ed) {
        return ((java.sql.Date.valueOf(ed)).getTime() - (java.sql.Date.valueOf(sd)).getTime())
                / (3600 * 24 * 1000);
    }

    /**
     * 求两个日期相差天数
     *
     * @param startTime 起始日期，格式yyyy-MM-dd
     * @param endTime   终止日期，格式yyyy-MM-dd
     * @return 两个日期相差天数
     */
    public static long getIntervalDays(long startTime, long endTime) {
        String sd = DateUtil.parseToDate(startTime);
        String ed = DateUtil.parseToDate(endTime);
        return ((java.sql.Date.valueOf(ed)).getTime() - (java.sql.Date.valueOf(sd)).getTime())
                / (3600 * 24 * 1000);
    }

    /**
     * 时间比较, 用于比较一天以内的时间,一个月以内的时间,或者一年以内的时间
     *
     * @param year        用于设置比较时间范围
     * @param month:      0-JANUARY 用于设置比较时间范围
     * @param day
     * @param compareTime 用于比较时间
     * @param type        时间比较 年内(0),月内(1),天内(2)
     * @return
     */
    public static boolean dateCompare(int year, int month, int day, Long compareTime, int type) {
        Calendar c1 = Calendar.getInstance();
        c1.set(year, month, 1, 0, 0, 0);
        Calendar c2 = Calendar.getInstance();
        if (type == 0) {
            c2.set(year - 1, month - 1, day);
            c2.add(Calendar.DAY_OF_MONTH, -1);
            c2.set(Calendar.HOUR_OF_DAY, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
        } else if (type == 1) {
            c2.set(year, month - 1, 1);
            c2.add(Calendar.DAY_OF_MONTH, -1);
            c2.set(Calendar.HOUR_OF_DAY, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
        } else if (type == 2) {
            c1.set(year, month, day, 23, 59, 59);
            c2.set(year, month, day, 0, 0, 0);
        }
        return compareTime < c1.getTimeInMillis() && compareTime > c2.getTimeInMillis();
    }
}