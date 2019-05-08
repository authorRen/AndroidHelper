package com.rex.app_library.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间辅助类
 * <p>
 * Created by renzeqiang
 * on 2019/5/7
 */
public class DateUtils {
    private static long calendarLong = 1533081600000L;
    private static String calendar = "CalendarDay{2019-5-7}";

    private static void main(String[] args) {
        System.out.println();
    }

    public static String formatCanlendar(String calendar) {
        return calendar.substring(calendar.indexOf("{") + 1, calendar.indexOf("}"));
    }

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    /* 定义常量 */
    public static final String DATE_JFP_STR = "yyyyMM";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";

    public static final String formatpattern = "yyyy-MM-dd";
    public static final String formatPattern_Short = "yyyyMMdd";

    private static final long serialVersionUID = 1L;

    /**
     * 获取系统时间(格式：yyyyMMddHHmmss)
     * @return 返回时间
     */
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取系统时间(格式：yyyyMMddHHmmss)
     * @return 返回时间
     */
    public static String getStringTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 获取系统时间(格式：yyyyMMddHHmmssSSS)
     * @return 返回时间
     */
    public static String getStringTimeFull() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 判断日期是否属于今天日期(精确到天)
     * @param sDate 日期值
     * @return boolean 返回true表示是，false表示不是
     */
    public static boolean getSysIsToday(String sDate) {
        boolean flag = false;
        try {
            Date date;
            date = dateFormatFull.get().parse(sDate);
            Date today = new Date();
            if (date != null) {
                String nowDate = dateFormat.get().format(today);
                String timeDate = dateFormat.get().format(date);
                if (nowDate.equals(timeDate)) {
                    flag = true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormatFull = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 检查日期是否有效
     * @param year  年
     * @param month 月
     * @param day   日
     * @return boolean
     */
    public static boolean getDateIsTrue(String year, String month, String day) {
        try {
            String data = year + month + day;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern_Short);
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断两个字符串日期的前后
     *
     * @param date1 字符串时间1
     * @param date2 字符串时间2
     * @return boolean
     */
    public static boolean getDateIsBefore(String date1, String date2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FULL_STR);
            return simpleDateFormat.parse(date1).before(simpleDateFormat.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断两个时间日期的前后
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return boolean
     */
    public static boolean getDateIsBefore(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FULL_STR);
        return getDateIsBefore(sdf.format(date1), sdf.format(date2));
    }

    /**
     * 得到当前年
     */
    public static int getCurrentYear() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.YEAR);
    }

    /**
     * 得到当前月份，注意，这里的月份依然是从1开始的
     */
    public static int getCurrentMonth() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.MONTH + 1);
    }

    /**
     * 得到当前日
     */
    public static int getCurrentDay() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前星期
     */
    public static int getCurrentWeek() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到当前小时
     */
    public static int getCurrentHour() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.HOUR);
    }

    /**
     * 得到当前分钟
     */
    public static int getCurrMinute() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.MINUTE);
    }

    /**
     * 得到当前秒
     */
    public static int getCurrSecond() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.SECOND);
    }

    /**
     * Date类型转换为calendar类型
     */
    public static Calendar Date2Calendar(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;
    }

    /**
     * Calendar类型转换为Date类型
     */
    public static Date calendar2Date(Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * 得到当前时间的毫秒数
     */
    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取任意时间后num天的时间
     */
    public static Date nextDate(Date date, int num) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_YEAR, num);
        return instance.getTime();
    }

    /**
     * 获取当前日期是多少周
     */
    public static int getWeekOfYear(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setFirstDayOfWeek(Calendar.MONDAY);
        /**
         * 设置一年中第一个星期所需的最少天数，例如，如果定义第一个星期包含一年第一个月的第一天，则使用值1调用此方法。
         * 如果最少天数必须是一整个星期，则使用值7调用此方法。
         */
        instance.setMinimalDaysInFirstWeek(1);
        instance.setTime(date);
        return instance.get(Calendar.WEEK_OF_YEAR);
    }

}
