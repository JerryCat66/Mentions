package com.fenguo.library.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类型转换工具�?
 *
 * @author longbh
 */
public class StringUtil {

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * �?��是否是数�?
     *
     * @param chr
     * @return
     */
    public static boolean isNumber(char chr) {
        if (chr < 48 || chr > 57) {
            return false;
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }

    public static String toString(Object obj) {
        if (obj == null)
            return "";
        else
            return obj.toString();
    }

    public static int toInt(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return -1;
        } else {
            return Integer.parseInt(obj.toString());
        }
    }

    public static short toShort(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return -1;
        } else {
            return Short.parseShort(obj.toString());
        }
    }

    public static int toCount(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return 0;
        } else {
            return Integer.parseInt(obj.toString());
        }
    }

    public static float toFloat(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        } else {
            return Float.parseFloat(obj.toString());
        }
    }

    public static double toDouble(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        } else {
            return Double.parseDouble(obj.toString());
        }
    }

    /**
     * 将对象转换成Long�?空对象默认装换成0)
     *
     * @param obj
     * @return
     */
    public static Long toLong(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0L;
        } else {
            return Long.parseLong(obj.toString());
        }
    }

    /**
     * 将对象转换成boolean类型,默认为false
     *
     * @param obj
     * @return
     */
    public static Boolean toBoolean(Object obj) {
        if (obj == null || "".equals(obj)) {
            return false;
        }
        return Boolean.valueOf(obj.toString());
    }

    public static boolean checkStr(String str) {
        boolean bool = true;
        if (str == null || "".equals(str.trim()))
            bool = false;
        return bool;
    }

    public static String buildFirstChar(String str) {
        if (str == null)
            return null;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static double double2point(double ff) {
        int j = (int) Math.round(ff * 10000);
        double k = (double) j / 100.00;

        return k;
    }

    public String snumberFormat(double unm) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(unm);
    }

    public static String delSpaceString(String d) {
        String ret = "";
        if (d != null) {
            ret = d.trim();
        }
        return ret;
    }

    /**
     * 数据定长输出
     *
     * @param pattern 长度及其格式（例如：定长�?0位，不足则前面补零，那么pattern�?0000000000"�?
     * @param number
     * @return
     */
    public static String getDecimalFormat(String pattern, String number) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern(pattern);
        int num = Integer.parseInt(toInt(number) + "");
        // �?��长度
        if ((num + "").length() > pattern.length()) {
            String newNumber = (num + "").substring(0, pattern.length() - 1);
            num = Integer.parseInt(newNumber);
        }
        return myformat.format(num);
    }

    public static String formatDecimal(String pattern, int number) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern(pattern);
        if ((number + "").length() > pattern.length()) {
            String newNumber = (number + "").substring(0, pattern.length() - 1);
            number = Integer.parseInt(newNumber);
        }
        return myformat.format(number);
    }

    public static String bytesToHexString(byte[] src, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || length <= 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static float max(float... values) {
        float max = 0;
        for (float item : values) {
            if (max == 0) {
                max = item;
            } else {
                max = Math.max(max, item);
            }
        }
        return max;
    }

    public static float min(float... values) {
        float min = 0;
        for (float item : values) {
            if (min == 0) {
                min = item;
            } else {
                if (item == 0) {
                    continue;
                }
                min = Math.min(min, item);
            }
        }
        return min;
    }

    public static int level(float value, float level[]) {
        for (int i = 0; i < level.length; i++) {
            if (value < level[i]) {
                return i;
            }
        }
        return level.length;
    }


    /**
     * @param @param  number
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: topercent
     * @Description: 将double类型的数据改为百分数，默认显示两位小数
     */
    public static String toPercent(double number) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(2);
        return percentFormat.format(number);

    }

    public static String toNumberFormat(double number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.format(number);
    }

    public static boolean isMobile(String phoneNumber) {
        //14755767390
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([6-8]|0))|(18[0-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber + "");
        return matcher.matches();
    }

    /**
     * 判断身份证号码格式是否正确
     *
     * @param idNumber
     * @return
     */
    public static boolean isIdNumber(String idNumber) {
        String regex = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(idNumber + "");
        return matcher.matches();
    }

    /**
     * 判断是否是字母+数字
     *
     * @param number
     * @return
     */
    public static boolean isDigitalAndAlphabet(String number) {
        Pattern p1 = Pattern.compile("[0-9]*$");
        Pattern p2 = Pattern.compile("^[A-Za-z]+$");
        if (p1.matcher(number).matches() || p2.matcher(number).matches()) {
            return false;
        }
        return true;
    }

    /**
     * @param @param  phone
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: phone2Unknown
     * @Description: 将手机号中间4位变为****
     */
    public static String phone2Unknown(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
    }

    /**
     * 將數據的后len位置為****
     *
     * @param data
     * @param len
     * @return
     */
    public static String parseToLastUnknown(String data, int len) {
        int count = data.length();
        StringBuffer sb = new StringBuffer();
        if (count > len) {
            for (int i = 0; i < len; i++) {
                sb.append("*");
            }
            return data.substring(0, count - len) + sb.toString();
        } else {
            return data;
        }

    }

    /**
     * 保留前len位
     *
     * @param data
     * @param len
     * @return
     */
    public static String remainFirstWords(String data, int len) {
        int count = data.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append("*");
        }
        if (count < len) {
            return data;
        } else {
            return data.substring(0, count - len) + sb.toString();
        }
    }

    /**
     * @param data
     * @param word
     * @return
     */
    public static String remainForFirstWord(String data, String word) {
        int index = data.indexOf(word);
        if (index == -1) {
            return data;
        }
        return data.substring(0, index);
    }


    /**
     * @param @param  data
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: to2Decimal
     * @Description: 保留两位小数
     */
    public static String to2Decimal(double data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#,##0.00";// 定义要显示的数字的格式
        df.applyPattern(style);
        return df.format(data);
    }

    /**
     * @param @param  data
     * @param @return 设定文件
     * @return double 返回类型
     * @throws
     * @Title: to2Double
     * @Description: 四舍五入，保留两位小数，返回double
     */
    public static double to2Double(double data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#.00";// 定义要显示的数字的格式
        df.applyPattern(style);
        return StringUtil.toDouble(df.format(data));

    }

    public static String toFormatLong(long data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#,###";// 定义要显示的数字的格式
        df.applyPattern(style);
        return df.format(data);
    }

    public static String toHundred(long data) {
        int remainder = (int) (data % 100 / 10);
        if (remainder >= 5) {
            return String.valueOf(((data / 100) + 1) * 100);
        } else {
            return String.valueOf(data / 100 * 100);
        }

    }

    /**
     * @param @param  value
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     * @Title: getStringLength
     * @Description: 获取字符的长度（中文算2，英文算1）
     */
    public static int getStringLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < value.length(); i++) {
            // 获取一个字符
            String temp = value.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 2;
            } else {
                // 其他字符长度为0.5
                valueLength += 1;
            }
        }
        return valueLength;
    }
}
