package eight.java.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * @ClassName: ClockDemo
 * @Author: WuXiangShuai
 * @Time: 17:49 2019/4/25.
 * @Description:
 */
public class ClockDemo {

    @Test
    public void test11(){
        long l = System.currentTimeMillis();
        System.out.println(l);
    }

    @Test
    public void test10(){
        LocalDateTime date = LocalDateTime.parse("2017-01-03T10:15:30");
        LocalDateTime date1 = LocalDateTime.now();
        System.out.println(date.until(date1, ChronoUnit.DAYS));
    }

    @Test
    public void test09(){
        LocalDateTime date = LocalDateTime.parse("2017-02-03T10:15:30");
        LocalDateTime date1 = date.plus(Period.ofDays(-10));
        System.out.println(date);
        System.out.println(date1);
    }

    @Test
    public void test08(){
        LocalDateTime date = LocalDateTime.parse("2017-03-03T12:30:31");
        LocalDateTime date1 = LocalDateTime.parse("2017-03-03T12:30:30");
        System.out.println(date.compareTo(date1));
    }

    @Test
    public void test07(){
        //格式化解析自定义日期
        String day = "2019-04-25";
        String time = "20:12:21";
        System.out.println(check(day));
        System.out.println(check(time));
        System.out.println(LocalDateTime.now());
    }

    public String check(String time){
        if (!checkFormatter(time))
            time = "1970-01-01T00:00:01";
        else
            if (time.length() == 8){
                time = "1970-01-01T" + time;
            } else if (time.length() == 10){
                time += "T00:00:01";
            }
        LocalDateTime date1 = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        return date1.toString();
    }

    public boolean checkFormatter(String time){
        if (time.indexOf("-") ==- 1 && time.indexOf(":") == -1)
            return false;
        return true;
    }

    /**
     * 格式化日期
     */
    @Test
    public void test06(){
        String day = "20140116";
        LocalDate formatted = LocalDate.parse(day,  DateTimeFormatter.BASIC_ISO_DATE);
        System.out.printf("Date generated from String %s is %s %n", day, formatted);

        String day02 = "2014-01-16";
        LocalDate formatted02 = LocalDate.parse(day02,  DateTimeFormatter.ISO_DATE);
        System.out.printf("Date generated from String %s is %s %n", day02, formatted02);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String date = "2017-12-03T10:15:30+01:00";
        LocalDateTime date1 = LocalDateTime.parse(date, dateTimeFormatter);
        System.out.println(date1);

//        String goodFriday = "2017-12-03 10:15:30:000";
        String goodFriday = "2017-12-03 10:15";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime holiday = LocalDateTime.parse(goodFriday, formatter);
            System.out.printf("Successfully parsed String %s, date is %s%n", goodFriday, holiday);
        } catch (DateTimeParseException ex) {
            System.out.printf("%s is not parsable!%n", goodFriday);
            ex.printStackTrace();
        }

    }

    @Test
    public void test05(){
        LocalDate today = LocalDate.now();
        System.out.println(today);
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        System.out.printf("Year : %d  Month : %d  day : %d t %n", year, month, day);
        //获取今年过了多少天
        System.out.println(today.getDayOfYear());
    }

    @Test
    public void test04(){
        //当前日期，仅日期
        LocalDate today = LocalDate.now();
        System.out.println("Today's Local date : " + today);
        //当然时间，仅时间
        LocalTime time = LocalTime.now();
        System.out.println("Today's Local time : " + time);
    }

    @Test
    public void test03(){
        //Z表示格林威治标准时间，即 GMT
        Clock clock = Clock.systemDefaultZone();
        System.out.println(clock.instant());

        Clock clock02 = Clock.system(ZoneId.of("GMT+8"));
        System.out.println(clock02.instant());

        Clock clock03 = Clock.systemUTC();
        //根据时区调整时间
        DateTimeFormatter df= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        String string = df.format(LocalDateTime.ofInstant(clock.instant(), ZoneId.of("GMT+8")));
        String string = df.format(LocalDateTime.ofInstant(Instant.now(clock03), ZoneId.of("GMT+8")));
//        String string = df.format(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("GMT+8")));
        System.out.println(string);
        System.out.println(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("GMT+8")));
        System.out.println(LocalDateTime.ofInstant(Instant.now(clock02), ZoneId.of("GMT+8")));
    }

    @Test
    public void test02(){
        //获取一个从 1970-01-01 00:00:00 到指定长度日期后的时钟
        Clock fixed = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        System.out.println(fixed.toString());

        Clock fixed2 = Clock.fixed(Instant.ofEpochSecond(3600), ZoneId.systemDefault());
        System.out.println(fixed2.toString());
    }

    @Test
    public void test01(){
        Clock clock = Clock.systemDefaultZone();
        Clock clock1 = Clock.systemUTC();
        //获取当前时区的时钟
        System.out.println("Clock 1 Zone: " + clock.getZone());
        System.out.println("Clock 2 Zone: " + clock1.getZone());
        //判断两时钟是否相同
        System.out.println("Clock 1 equals Clock 2: " + clock.equals(clock1));
    }

}
