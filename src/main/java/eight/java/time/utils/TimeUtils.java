package eight.java.time.utils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.*;

/**
 * @ClassName: TimeUtils
 * @Author: WuXiangShuai
 * @Time: 18:34 2019/4/25. @Version：2.0
 * @Description: 时间工具类 需要 jdk 1.8 以上版本
 * <p>
 * 待添加： 1、若存在按年折扣结算，余下的按天结算，这样就要剔除两段时间内闰年多出的时间。 2、异常处理
 * @Error： parseLocalDateTimeMilli(String time)，解析格式为
 * yyyyMMddHHmmssSSS 的字符串在 jdk8 中存在bug，jdk9 解决
 */
@SuppressWarnings("unused")
public class TimeUtils {

    private static final String[] CN_UPPER_NUMBER = {"", "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] CN_SHORT_NUMBER = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    // 毫秒，秒，分，小时，天
    private static final long[] NUMBER_LEVEL_NUMBER = {1, 1000, 60000, 3600000, 86400000};
    private static final String[] NUMBER_LEVEL = {"millis", "seconds", "minutes", "hours", "days"};

    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";// 基础格式
    public static final String FORMAT_ORIGIN = "yyyyMMddHHmmss";// 基础格式，原生格式
    public static final String FORMATMILLI = "yyyy-MM-dd HH:mm:ss.SSS";// 毫秒格式
    public static final String FORMATMILLI_ORIGIN = "yyyyMMddHHmmssSSS";
    public static final String FORMATDAY = "yyyy-MM-dd";// 日期格式
    public static final String FORMATDAY_ORIGIN = "yyyyMMdd";

    /**
     * 根据身份证号返回年龄
     */
    public static String getAgeByIdNumber(String id) {
        StringBuilder builder = new StringBuilder();
        builder.append(id.substring(6, 10));
        builder.append(id.substring(10, 12));
        builder.append(id.substring(12, 14));
        Map<String, Long> param = TimeUtils.periodInfoMap(builder.toString());
        return String.valueOf(param.get("years"));
    }

    /**
     * 解析时间长度，如100秒 = 一分钟四十秒
     */
    public static Map<String, Long> resolveTime(long time, int level) {
        if (time < 0) {
            time = -time;
        }
        time *= NUMBER_LEVEL_NUMBER[level];
        Map<String, Long> map = new HashMap<>();
        for (int i = NUMBER_LEVEL_NUMBER.length - 1; i >= 0; i--) {
            long val = 0l;
            if (time > 0) {
                val = time / NUMBER_LEVEL_NUMBER[i];
                time = time % NUMBER_LEVEL_NUMBER[i];
            }
            map.put(NUMBER_LEVEL[i], val);
        }
        return map;
    }

    /**
     * 返回给定时间到现在的期间的全部信息
     *
     * @return
     * @Param c 间隔符
     */
    public static String durationInfo(String time, char c) {
        return durationInfo(getNowLocalDateTime(), parseLocalDateTimeMilli(time), c);
    }

    /**
     * 返回给定时间之间的期间的全部信息
     *
     * @return
     * @Param c 间隔符
     */
    public static String durationInfo(String time01, String time02, char c) {
        return durationInfo(parseLocalDateTimeMilli(time01), parseLocalDateTimeMilli(time02), c);
    }

    // durationInfo 根方法
    public static String durationInfo(LocalDateTime t1, LocalDateTime t2, char c) {
        Map<String, Long> map = durationInfoMap(t1, t2);
        StringBuffer bf = new StringBuffer();
        bf.append(map.get("days") + "日" + c);
        bf.append(map.get("hours") + "小时" + c);
        bf.append(map.get("minutes") + "分" + c);
        bf.append(map.get("seconds") + "秒" + c);
        bf.append(map.get("millis") + "毫秒");
        return bf.toString();
    }

    /**
     * 返回给定时间到现在的期间的全部信息
     *
     * @return
     */
    public static Map<String, Long> durationInfoMap(String time) {
        return durationInfoMap(getNowLocalDateTime(), parseLocalDateTimeMilli(time));
    }

    /**
     * 返回给定时间之间的期间的全部信息
     *
     * @return
     */
    public static Map<String, Long> durationInfoMap(String time01, String time02) {
        return durationInfoMap(parseLocalDateTimeMilli(time01), parseLocalDateTimeMilli(time02));
    }

    /**
     * 返回给定时间到现在的期间的全部信息的根方法 如 days = 8095，hours = 194281，minutes = 11656872，seconds
     * = 699412333，millis = 699412333123
     */
    public static Map<String, Long> durationInfoMap(LocalDateTime time01, LocalDateTime time02) {
        long d = duration(time01, time02);
        // 通过 Duration 类计算期间的各单位的差
        Duration duration = Duration.ofMillis(d);
        HashMap<String, Long> map = new HashMap<>();
        map.put("millis", longToUnsigned(duration.toMillis()).longValue());
        // Duration 类未提供 秒级 的运算
        long seconds = d / 1000;
        map.put("seconds", Long.valueOf(seconds));
        map.put("minutes", Long.valueOf(duration.toMinutes()));
        map.put("hours", Long.valueOf(duration.toHours()));
        map.put("days", Long.valueOf(duration.toDays()));
        return map;
    }

    /**
     * 获取给定时间与当前时间之间的周期
     *
     * @param c 间隔符
     * @return
     */
    public static String periodInfo(String time, char c) {
        return periodInfo(getNowLocalDateTime(), parseLocalDateTimeMilli(time), c);
    }

    /**
     * 获取给定时间之间的周期
     *
     * @param c 间隔符
     * @return
     */
    public static String periodInfo(String time01, String time02, char c) {
        return periodInfo(parseLocalDateTimeMilli(time01), parseLocalDateTimeMilli(time02), c);
    }

    // 获取Str周期样式的根方法
    public static String periodInfo(LocalDateTime time01, LocalDateTime time02, char c) {
        Map<String, Long> map = periodInfoMap(time01, time02);
        StringBuffer bf = new StringBuffer();
        bf.append(map.get("years") + "年" + c);
        bf.append(map.get("months") + "月" + c);
        bf.append(map.get("days") + "日" + c);
        bf.append(map.get("hours") + "小时" + c);
        bf.append(map.get("minutes") + "分" + c);
        bf.append(map.get("seconds") + "秒" + c);
        bf.append(map.get("millis") + "毫秒");
        return bf.toString();
    }

    // 获取给定时间与当前时间之间的周期
    public static Map<String, Long> periodInfoMap(String time) {
        return periodInfoMap(getNowLocalDateTime(), parseLocalDateTimeMilli(time));
    }

    // 获取给定时间之间的周期
    public static Map<String, Long> periodInfoMap(String time01, String time02) {
        return periodInfoMap(parseLocalDateTimeMilli(time01), parseLocalDateTimeMilli(time02));
    }

    // 获取Map周期样式的根方法
    public static Map<String, Long> periodInfoMap(LocalDateTime time01, LocalDateTime time02) {
        Period between = Period.between(time01.toLocalDate(), time02.toLocalDate());
        HashMap<String, Long> map = new HashMap<>();
        map.put("years", Long.valueOf(between.getYears()));
        map.put("months", Long.valueOf(Long.valueOf(between.getMonths())));
        map.put("days", Long.valueOf(between.getDays()));
        // 计算时、分、秒、毫秒
        long d = duration(time01, time02);
        long millis = d % 1000;
        d /= 1000;
        long seconds = d % 60;
        d /= 60;
        long minutes = d % 60;
        d /= 60;
        long hours = d % 24;
        map.put("millis", Long.valueOf(millis));
        map.put("seconds", Long.valueOf(seconds));
        map.put("minutes", Long.valueOf(minutes));
        map.put("hours", Long.valueOf(hours));
        return map;
    }

    /**
     * 年级的两个给定时间的时间差
     */
    public static Long untilYears(String time01, String time02) {
        return until(time01, time02, ChronoUnit.YEARS);
    }

    /**
     * 年级的当前时间与给定时间的时间差
     */
    public static Long untilYears(String time) {
        return until(time, ChronoUnit.YEARS);
    }

    /**
     * 月级的两个给定时间的时间差
     */
    public static Long untilMonths(String time01, String time02) {
        return until(time01, time02, ChronoUnit.MONTHS);
    }

    /**
     * 月级的当前时间与给定时间的时间差
     */
    public static Long untilMonths(String time) {
        return until(time, ChronoUnit.MONTHS);
    }

    /**
     * 周级的两个给定时间的时间差
     */
    public static Long untilWeeks(String time01, String time02) {
        return until(time01, time02, ChronoUnit.WEEKS);
    }

    /**
     * 周级的当前时间与给定时间的时间差
     */
    public static Long untilWeeks(String time) {
        return until(time, ChronoUnit.WEEKS);
    }

    /**
     * 天级的两个给定时间的时间差
     */
    public static Long untilDays(String time01, String time02) {
        return until(time01, time02, ChronoUnit.DAYS);
    }

    /**
     * 天级的当前时间与给定时间的时间差
     */
    public static Long untilDays(String time) {
        return until(time, ChronoUnit.DAYS);
    }

    /**
     * 半天级的两个给定时间的时间差
     */
    public static Long untilHalfDays(String time01, String time02) {
        return until(time01, time02, ChronoUnit.HALF_DAYS);
    }

    /**
     * 半天级的当前时间与给定时间的时间差
     */
    public static Long untilHalfDays(String time) {
        return until(time, ChronoUnit.HALF_DAYS);
    }

    /**
     * 小时级的两个给定时间的时间差
     */
    public static Long untilHours(String time01, String time02) {
        return until(time01, time02, ChronoUnit.HOURS);
    }

    /**
     * 小时级的当前时间与给定时间的时间差
     */
    public static Long untilHours(String time) {
        return until(time, ChronoUnit.HOURS);
    }

    /**
     * 分钟级的两个给定时间的时间差
     */
    public static Long untilMinutes(String time01, String time02) {
        return until(time01, time02, ChronoUnit.MINUTES);
    }

    /**
     * 分钟级的当前时间与给定时间的时间差
     */
    public static Long untilMinutes(String time) {
        return until(time, ChronoUnit.MINUTES);
    }

    /**
     * 秒级的两个给定时间的时间差
     */
    public static Long untilSeconds(String time01, String time02) {
        return until(time01, time02, ChronoUnit.SECONDS);
    }

    /**
     * 秒级的当前时间与给定时间的时间差
     */
    public static Long untilSeconds(String time) {
        return until(time, ChronoUnit.SECONDS);
    }

    /**
     * 毫秒级的两个给定时间的时间差
     */
    public static Long untilMillis(String time01, String time02) {
        return duration(time01, time02);
    }

    /**
     * 毫秒级的当前时间与给定时间的时间差
     */
    public static Long untilMillis(String time) {
//        return until(time, ChronoUnit.MILLIS);
        return duration(time);
    }

    /**
     * 两个给定时间的时间差，毫秒级，使用 Duration 类
     */
    public static Long duration(String time01, String time02) {
        LocalDateTime t1 = parseLocalDateTimeMilli(time01);
        LocalDateTime t2 = parseLocalDateTimeMilli(time02);
        return duration(t1, t2);
    }

    /**
     * 当前时间与给定时间的时间差，使用 Duration 类
     */
    public static Long duration(String time) {
        return duration(getNowLocalDateTime(), parseLocalDateTimeMilli(time));
    }

    // duration 方法根方法
    public static Long duration(LocalDateTime t1, LocalDateTime t2) {
        return t1.isAfter(t2) ? -Duration.between(t1, t2).toMillis() : Duration.between(t1, t2).toMillis();
    }

    /**
     * 两个给定时间的时间差，使用 LocalDateTime.until方法 貌似仅支持到 秒级，毫秒级需要使用 Duration 类处理
     */
    public static Long until(String time01, String time02, TemporalUnit unit) {
        LocalDateTime t1 = parseLocalDateTime(time01);
        LocalDateTime t2 = parseLocalDateTime(time02);
        return until(t1, t2, unit);
    }

    /**
     * 当前时间与给定时间的时间差，使用 LocalDateTime.until方法
     */
    public static Long until(String time, TemporalUnit unit) {
        return until(getNowLocalDateTime(), parseLocalDateTime(time), unit);
    }

    // until 方法根方法
    public static Long until(LocalDateTime t1, LocalDateTime t2, TemporalUnit unit) {
        try {
            long until = t1.isAfter(t2) ? -t1.until(t2, unit) : t1.until(t2, unit);
//            return longToUnsigned(until).toString();
            return until;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * long 类型越界的处理
     *
     * @param signed
     * @return
     */
    public static BigInteger longToUnsigned(long signed) {
        if (signed < 0L) {
            return BigInteger.valueOf(signed & Long.MAX_VALUE).setBit(63);
        }
        return BigInteger.valueOf(signed);
    }

    /**
     * 年级修改给定的时间
     */
    public static String plusYears(String time, long amountToAdd) {
        return plus(parseLocalDateTime(time), amountToAdd, ChronoUnit.YEARS).toString();
    }

    /**
     * 年级修改当前时间
     */
    public static String plusYears(long amountToAdd) {
        return plus(amountToAdd, ChronoUnit.YEARS);
    }

    /**
     * 月级修改给定的时间
     */
    public static String plusMonths(String time, long amountToAdd) {
        return plus(parseLocalDateTime(time), amountToAdd, ChronoUnit.MONTHS).toString();
    }

    /**
     * 月级修改当前时间
     */
    public static String plusMonths(long amountToAdd) {
        return plus(amountToAdd, ChronoUnit.MONTHS);
    }

    /**
     * 周级修改给定的时间
     */
    public static String plusWeeks(String time, long amountToAdd) {
        return plus(parseLocalDateTime(time), amountToAdd, ChronoUnit.WEEKS).toString();
    }

    /**
     * 周级修改当前时间
     */
    public static String plusWeeks(long amountToAdd) {
        return plus(amountToAdd, ChronoUnit.WEEKS);
    }

    /**
     * 天级修改给定的时间
     */
    public static String plusDays(String time, long amountToAdd) {
        return plus(parseLocalDateTime(time), amountToAdd, ChronoUnit.DAYS).toString();
    }

    /**
     * 天级修改当前时间
     */
    public static String plusDays(long amountToAdd) {
        return plus(amountToAdd, ChronoUnit.DAYS);
    }

    /**
     * 半天级修改给定的时间
     */
    public static String plusHalfDays(String time, long amountToAdd) {
        return plus(parseLocalDateTime(time), amountToAdd, ChronoUnit.HALF_DAYS).toString();
    }

    /**
     * 半天级修改当前时间
     */
    public static String plusHalfDays(long amountToAdd) {
        return plus(amountToAdd, ChronoUnit.HALF_DAYS);
    }

    /**
     * 小时级修改给定的时间
     */
    public static String plusHours(String time, long amountToAdd) {
        return plus(parseLocalDateTime(time), amountToAdd, ChronoUnit.HOURS).toString();
    }

    /**
     * 小时级修改当前时间
     */
    public static String plusHours(long amountToAdd) {
        return plus(amountToAdd, ChronoUnit.HOURS);
    }

    /**
     * 分钟级修改给定的时间
     */
    public static String plusMinutes(String time, long amountToAdd) {
        return plus(parseLocalDateTime(time), amountToAdd, ChronoUnit.MINUTES).toString();
    }

    /**
     * 分钟级修改当前时间
     */
    public static String plusMinutes(long amountToAdd) {
        return plus(amountToAdd, ChronoUnit.MINUTES);
    }

    /**
     * 秒级修改给定的时间
     */
    public static String plusSeconds(String time, long amountToAdd) {
        return plus(parseLocalDateTime(time), amountToAdd, ChronoUnit.SECONDS).toString();
    }

    /**
     * 秒级修改当前时间
     */
    public static String plusSeconds(long amountToAdd) {
        return plus(amountToAdd, ChronoUnit.SECONDS);
    }

    /**
     * 毫秒级修改给定的时间
     */
    public static String plusMILLIS(String time, long amountToAdd) {
        return plus(parseLocalDateTime(time), amountToAdd, ChronoUnit.MILLIS).toString();
    }

    /**
     * 毫秒级修改当前时间
     */
    public static String plusMILLIS(long amountToAdd) {
        return plus(amountToAdd, ChronoUnit.MILLIS);
    }

    /**
     * 修改给定的时间
     */
    public static String plus(LocalDateTime date, long amountToAdd, TemporalUnit unit) {
        try {
            return formatStr(date.plus(amountToAdd, unit));
        } catch (UnsupportedTemporalTypeException e) {
            e.printStackTrace();
        } catch (DateTimeException e) {
            e.printStackTrace();
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改当前时间
     */
    public static String plus(long amountToAdd, TemporalUnit unit) {
        return plus(getNowLocalDateTime(), amountToAdd, unit);
    }

    /**
     * 判断两时间是否相同
     */
    public static boolean isEqualTime(String time01, String time02) {
        int c = parseLocalDateTime(time01).compareTo(parseLocalDateTime(time02));
        return c == 0;
    }

    /**
     * 判断 time01 是否在 time02 之后 true， time01 在 time02 之后 false, time01 在 time02 之前
     */
    public static boolean isAfterTime(String time01, String time02) {
        int c = parseLocalDateTime(time01).compareTo(parseLocalDateTime(time02));
        return c > 0;
    }

    /**
     * 判断 time01 是否在 time02 之前 true， time01 在 time02 之前 false, time01 在 time02 之后
     */
    public static boolean isBeforeTime(String time01, String time02) {
        int c = parseLocalDateTime(time01).compareTo(parseLocalDateTime(time02));
        return c < 0;
    }

    /**
     * 判断两日期是否相同
     */
    public static boolean isEqual(String day01, String day02) {
        return parseLocalDateTime(day01).isEqual(parseLocalDateTime(day02));
    }

    /**
     * 判断 day01 是否在 dya02 之后 true， day01在day02之后 false, day01在day02之前
     */
    public static boolean isAfter(String day01, String day02) {
        return parseLocalDateTime(day01).isAfter(parseLocalDateTime(day02));
    }

    /**
     * 判断 当前时间 是否在 dya02 之后 true， 是 false, 不是
     */
    public static boolean nowIsAfter(String day) {
        return getNowLocalDateTime().isAfter(parseLocalDateTime(day));
    }

    /**
     * 判断 当前时间 是否在 dya02 之后 true， 是 false, 不是
     */
    public static boolean nowIsAfterDays(String day) {
        return getNowLocalDateTime().toLocalDate().isAfter(parseLocalDateTime(day).toLocalDate());
    }

    /**
     * 判断 day01 是否在 dya02 之前 true， day01在day02之前 false, day01在day02之后
     */
    public static boolean isBefore(String day01, String day02) {
        return parseLocalDateTime(day01).isBefore(parseLocalDateTime(day02));
    }

    /**
     * 判断 当前时间 是否在 dya 之前 true， 是 false, 不是
     */
    public static boolean nowIsBefore(String day) {
        return getNowLocalDateTime().isBefore(parseLocalDateTime(day));
    }

    /**
     * 判断 当前时间 是否在 dya 之前 true， 是 false, 不是
     */
    public static boolean nowIsBeforeDays(String day) {
        return getNowLocalDateTime().toLocalDate().isBefore(parseLocalDateTime(day).toLocalDate());
    }

    /**
     * 返回输入日期是周几
     *
     * @return
     */
    public static String getMonthLocal(String day) {
//        //1997022601 + 或 1997-02-26 01 +
//        if (!checkFormatterWay(day))
//            //DateTime
//            return parseLocalDateTime(day).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
//        //19970226 或 1997-02-26
//        //Date
//        return parseLocalDate(day).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        return parseLocalDateTime(day).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 返回传入月份，本地格式
     *
     * @return
     */
    public static String getMonthLocal(LocalDateTime time) {
        return time.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 返回传入月份，本地格式
     *
     * @return
     */
    public static String getMonthLocal(LocalDate day) {
        return day.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 返回本月份，本地格式
     *
     * @return
     */
    public static String getMonthLocal() {
        return LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 返回输入日期是周几
     *
     * @return
     */
    public static String getWeekLocal(String day) {
//        //1997022601 + 或 1997-02-26 01 +
//        if (!checkFormatterWay(day))
//            //DateTime
//            return parseLocalDateTime(day).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
//        //19970226 或 1997-02-26
//        //Date
//        return parseLocalDate(day).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        return parseLocalDateTime(day).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 返回传入时间是周几，本地格式
     *
     * @return
     */
    public static String getWeekLocal(LocalDateTime time) {
        return time.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 返回传入时间是周几，本地格式
     *
     * @return
     */
    public static String getWeekLocal(LocalDate day) {
        return day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 返回今天是周几，本地格式
     *
     * @return
     */
    public static String getWeekLocal() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 返回输入年月份的本日是本年的第几天
     *
     * @return
     */
    public static int getDayOfYear(String day) {
//        //1997022601 + 或 1997-02-26 01 +
//        if (!checkFormatterWay(day))
//            //DateTime
//            return parseLocalDateTime(day).getDayOfYear();
//        //19970226 或 1997-02-26
//        //Date
//        return parseLocalDate(day).getDayOfYear();
        return parseLocalDateTime(day).getDayOfYear();
    }

    /**
     * 返回传入的日期是本年的第几天
     *
     * @return
     */
    public static int getDayOfYear(LocalDateTime time) {
        return time.getDayOfYear();
    }

    /**
     * 返回传入的日期是本年的第几天
     *
     * @return
     */
    public static int getDayOfYear(LocalDate day) {
        return day.getDayOfYear();
    }

    /**
     * 返回今天是本年的第几天
     *
     * @return
     */
    public static int getDayOfYear() {
        return LocalDate.now().getDayOfYear();
    }

    /**
     * 返回输入年月份的当日是当月的第几天
     *
     * @return
     */
    public static int getDayOfMonth(String day) {
//        //1997022601 + 或 1997-02-26 01 +
//        if (!checkFormatterWay(day))
//            //DateTime
//            return parseLocalDateTime(day).getDayOfMonth();
//        //19970226 或 1997-02-26
//        //Date
//        return parseLocalDate(day).getDayOfMonth();
        return parseLocalDateTime(day).getDayOfMonth();
    }

    /**
     * 返回传入的日期是本月的第几天
     *
     * @return
     */
    public static int getDayOfMonth(LocalDateTime time) {
        return time.getDayOfMonth();
    }

    /**
     * 返回传入的日期是本月的第几天
     *
     * @return
     */
    public static int getDayOfMonth(LocalDate day) {
        return day.getDayOfMonth();
    }

    /**
     * 返回今天是本月的第几天
     *
     * @return
     */
    public static int getDayOfMonth() {
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * 返回输入年月日的当日是当周的第几天
     *
     * @return
     */
    public static int getDayOfWeek(String day) {
//        //1997022601 + 或 1997-02-26 01 +
//        if (!checkFormatterWay(day))
//            //DateTime
//            return parseLocalDateTime(day).getDayOfWeek().getValue();
//        //19970226 或 1997-02-26
//        //Date
//        return parseLocalDate(day).getDayOfWeek().getValue();
        return parseLocalDateTime(day).getDayOfWeek().getValue();
    }

    /**
     * 返回今天是输入的周的第几天
     *
     * @return
     */
    public static int getDayOfWeek(LocalDateTime time) {
        return time.getDayOfWeek().getValue();
    }

    /**
     * 返回输入的是周的第几天
     *
     * @return
     */
    public static int getDayOfWeek(LocalDate day) {
        return day.getDayOfWeek().getValue();
    }

    /**
     * 返回输入的是周的第几天
     *
     * @return
     */
    public static int getDayOfWeek() {
        return LocalDate.now().getDayOfWeek().getValue();
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getEpochMilli() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 获取当前毫秒
     *
     * @return
     */
    public static int getMilli() {
        // 纳秒 / 1000 = 微秒 / 1000 = 毫秒
        return getNowLocalDateTime().getNano() / 1000000;
    }

    /**
     * 获取当前时
     *
     * @return
     */
    public static int getSecond() {
        return getDate("s");
    }

    /**
     * 获取当前时
     *
     * @return
     */
    public static int getMinute() {
        return getDate("m");
    }

    /**
     * 获取当前时
     *
     * @return
     */
    public static int getHours() {
        return getDate("H");
    }

    /**
     * 获取当前日
     *
     * @return
     */
    public static int getDay() {
        return getDate("d");
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        return getDate("M");
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        return getDate("y");
    }

    /**
     * 获取输入的年、月、日 "20140116" 或 "2014-01-16"
     *
     * @return
     */
    public static int getDate(String day, String input) {
        return getDate(parseLocalDateTime(day), input);
    }

    /**
     * 获取当前年、月、日、时、分、秒
     *
     * @return
     */
    public static int getDate(String input) {
        return getDate(LocalDateTime.now(), input);
    }

    /**
     * 根据获得的日期获取年、月、日
     *
     * @return
     */
    public static int getDate(LocalDateTime today, String input) {
        int data = 0;
        switch (input) {
            case "y":
            case "year":
                data = today.getYear();
                break;
            case "M":
            case "month":
                data = today.getMonthValue();
                break;
            case "d":
            case "day":
                data = today.getDayOfMonth();
                break;
            case "H":
            case "hour":
            case "hours":
                data = today.getHour();
                break;
            case "m":
            case "min":
            case "minute":
                data = today.getMinute();
                break;
            case "s":
            case "sec":
            case "second":
                data = today.getSecond();
                break;
        }
        return data;
    }

    /**
     * 转换日期格式
     *
     * @param time [1970-01-01 00:00:01]
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String time) {
        if (time == null)
            return null;
        String dfStr = "";
        // 19970226121212
        if (!checkFormatter(time, 3)) {
            if (time.length() == 0)
                time += "19700101000000";
            else if (time.length() == 4)
                time += "0101000000";
            else if (time.length() == 6)
                time += "01000000";
            else if (time.length() == 8)
                time += "000000";
            else if (time.length() == 8)
                time += "000000";
            else if (time.length() == 10)
                time += "0000";
            else if (time.length() == 12)
                time += "00";
            dfStr = "yyyyMMddHHmmss";
        } else {
            // 1997-02-26 12:12:12
            if (time.length() == 0)
                time += "1970-01-01 00:00:00";
            else if (time.length() == 4)
                time += "-01-01 00:00:00";
            else if (time.length() == 7)
                time += "-01 00:00:00";
            else if (time.length() == 10)
                time += " 00:00:00";
            else if (time.length() == 13)
                time += ":00:00";
            else if (time.length() == 16)
                time += ":00";
            dfStr = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dfStr);
        try {
            return LocalDateTime.parse(time, df);
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static LocalDateTime parseLocalDateTime(String time, String dfStr) {
        if (time == null)
            return null;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dfStr);
        try {
            return LocalDateTime.parse(time, df);
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 转换日期格式
     *
     * @param time [1970-01-01 00:00:01.123]
     * @return
     */
    public static LocalDateTime parseLocalDateTimeMilli(String time) {
        if (time == null)
            return null;
        String dfStr = "";
        // 19970226121212123
        /**
         * 注意！jdk8在解析 yyyyMMddHHmmssSSS 存在bug，jdk9中修复
         */
        if (!checkFormatter(time, 3)) {
            if (time.length() == 0)
                time += "19700101000000000";
            else if (time.length() == 4)
                time += "0101000000000";
            else if (time.length() == 6)
                time += "01000000000";
            else if (time.length() == 8)
                time += "000000000";
            else if (time.length() == 8)
                time += "000000000";
            else if (time.length() == 10)
                time += "0000000";
            else if (time.length() == 12)
                time += "00000";
            dfStr = "yyyyMMddHHmmssSSS";
        } else {
            // 1997-02-26 12:12:12
            if (time.length() == 0)
                time += "1970-01-01 00:00:00.000";
            else if (time.length() == 4)
                time += "-01-01 00:00:00.000";
            else if (time.length() == 7)
                time += "-01 00:00:00.000";
            else if (time.length() == 10)
                time += " 00:00:00.000";
            else if (time.length() == 13)
                time += ":00:00.000";
            else if (time.length() == 16)
                time += ":00.000";
            else if (time.length() == 19)
                time += ".000";
            else if (time.length() == 21)
                time += "00";
            dfStr = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dfStr);
        try {
            return LocalDateTime.parse(time, df);
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static LocalDateTime parseLocalDateTimeBasic(String time) {
        if (time == null)
            return null;
        if (!checkFormatter(time, 3)) {
            time = "1970-01-01T00:00:01";
        } else {
            if (time.length() == 8) {
                time = "1970-01-01T" + time;
            } else if (time.length() == 10) {
                time += "T00:00:01";
            }
        }
        try {
            // 1970-01-01T00:00:01
            return LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 转换日期格式
     *
     * @param day
     * @return
     */
    public static LocalDate parseLocalDate(String day) {
        if (day == null)
            return null;
        LocalDate today;
        try {
            // 判断是否是标准格式
            if (!checkFormatter(day, 2))
                today = LocalDate.parse(day, DateTimeFormatter.BASIC_ISO_DATE);// 20111203
            else
                today = LocalDate.parse(day, DateTimeFormatter.ISO_DATE);// 2011-12-03
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            today = null;
        }
        return today;
    }

    /**
     * 转换日期格式
     *
     * @param day
     * @return
     */
    public static LocalTime parseLocalTime(String day) {
        if (day == null)
            return null;
        try {
            if (!checkFormatter(day, 1)) {
                return LocalTime.parse(day, DateTimeFormatter.ofPattern("HHmmss"));
            }
            return LocalTime.parse(day, DateTimeFormatter.ISO_LOCAL_TIME);// 10：15 | 10：15：30
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 检验日期格式化
     *
     * @param time
     * @return
     */
    public static boolean checkFormatter(String time, int i) {
        if (i == 1) {// time
            if (time.indexOf(":") == -1)
                return false;
        } else if (i == 2) {// date
            if (time.indexOf("-") == -1)
                return false;
        } else if (i == 3)// DataTime
            if (time.indexOf("-") == -1 && time.indexOf(":") == -1)
                return false;
        return true;
    }

    /**
     * 判断已何种方式解析
     *
     * @param time
     * @return
     */
//    public static boolean checkFormatterWay(String time){
//        //DateTime：1997022601 + 或 1997-02-26 01 +
//        if (!checkFormatter(time, 2) && time.length() > 8 || checkFormatter(time, 2) && time.length() > 10)
//            return false;
//        //Date：19970226 或 1997-02-26
//        return true;
//    }

    /**
     * 获取当前日期，仅日期
     */
    public static String getDate() {
        return LocalDate.now().toString();
    }

    /**
     * 获取当前时间，仅时间
     *
     * @return
     */
    public static String getTime() {
        return LocalTime.now().toString();
    }

    /**
     * 获取北京时区时间，本地化
     *
     * @return
     */
    public static String nowLocal() {
        LocalDateTime time = getNowLocalDateTime();
        int dayOfMonthIntVal = getDayOfMonth(time);
        StringBuffer dayOfMonthStr = new StringBuffer();
        dayOfMonthStr.append(CN_SHORT_NUMBER[dayOfMonthIntVal / 10]).append("十")
                .append(CN_SHORT_NUMBER[dayOfMonthIntVal % 10]);
        return time.getYear() + "年 " + getMonthLocal(time) + " " + dayOfMonthStr + "日 " + getWeekLocal(time);
//        return time.getYear() + "年" + getMonthLocal(time) + "" + dayOfMonthStr + "日" + getWeekLocal(time);
    }

    /**
     * 获取传入的时区的时间
     *
     * @return
     */
    public static String nowForZone(String zId) {
        return formatStr(getNowLocalDateTime(zId));
    }

    /**
     * 获取北京时区时间
     *
     * @return
     */
    public static String nowForMilli() {
        return formatStrMilli(getNowLocalDateTime());
    }

    /**
     * 获取北京时区时间
     *
     * @return
     */
    public static String now() {
        return formatStr(getNowLocalDateTime());
    }

    /**
     * 获取自定义当前时间
     *
     * @param hm
     * @return
     */
    public static String now(String hm) {
        return formatStr(getNowLocalDateTime(), hm);
    }

    /**
     * 获取当前格式化日期
     *
     * @return
     */
    public static String formatStr() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(getNowLocalDateTime());
    }

    /**
     * 获取当前格式化日期
     *
     * @return
     */
    public static String formatStr(String hm) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(hm);
        return df.format(getNowLocalDateTime());
    }

    /**
     * 格式化日期
     *
     * @param time
     * @return
     */
    public static String formatStr(LocalDateTime time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(time);
    }

    /**
     * 格式化日期
     *
     * @param time
     * @return
     */
    public static String formatStr(String time, String hm) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(hm);
        return df.format(parseLocalDateTimeMilli(time));
    }

    /**
     * 格式化日期
     *
     * @param time
     * @return
     */
    public static String formatStr(LocalDateTime time, String hm) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(hm);
        return df.format(time);
    }

    /**
     * 格式化日期
     *
     * @param time
     * @return
     */
    public static String formatStrMilli(LocalDateTime time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return df.format(time);
    }

    // 获取指定时区的 LcalDateTime对象
    public static LocalDateTime getNowLocalDateTime(String zId) {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(zId));
    }

    // 获取当前时区的 LcalDateTime对象
    public static LocalDateTime getNowLocalDateTime() {
        // 指定时钟获取LocalDateTime
//        Clock clock = Clock.systemUTC();
//        return LocalDateTime.ofInstant(Instant.now(clock), ZoneId.of("GMT+8"));
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of("GMT+8"));
    }

    /**
     * 获得当前日期
     *
     * @return 一个包含年月日的<code>String</code>型日期。yyyymmdd
     */
    public static String getCurrDateS() {
        StringBuffer now_ = new StringBuffer();
        now_.append(getYear());
        now_.append(getMonth());
        now_.append(getDay());
        return now_.toString();
    }

    /**
     * 获得当前日期
     *
     * @return 一个包含年月日时分秒的<code>String</code>型日期。yyyymmddhhmmss
     */
    public static String getCurrDateStr_() {
        StringBuffer now_ = new StringBuffer();
        now_.append(getYear());
        now_.append(getMonth());
        now_.append(getDay());
        now_.append(format(getCurrDate(), "HH"));
        now_.append(format(getCurrDate(), "MM"));
        now_.append(format(getCurrDate(), "SS"));
        return now_.toString();
    }

    /**
     * 日期格式化－将<code>Date</code>类型的日期格式化为<code>String</code>型
     *
     * @param date    待格式化的日期
     * @param pattern 时间样式
     * @return 一个被格式化了的<code>String</code>日期
     */
    public static String format(Date date, String pattern) {
        if (date == null)
            return "";
        else
            return getFormatter(pattern).format(date);
    }

    /**
     * 获取一个简单的日期格式化对象
     *
     * @return 一个简单的日期格式化对象
     */
    private static SimpleDateFormat getFormatter(String parttern) {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 获取当前日期
     *
     * @return 一个包含年月日的<code>Date</code>型日期
     */
    public static synchronized Date getCurrDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
}
