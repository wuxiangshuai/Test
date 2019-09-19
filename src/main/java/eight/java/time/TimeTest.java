package eight.java.time;

import eight.java.time.utils.TimeUtils;
import org.junit.Test;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * @ClassName: TimeTest
 * @Author: WuXiangShuai
 * @Time: 14:29 2019/4/25.
 * @Description:
 */
public class TimeTest {

    /**
     * 演示两个类
     * DayOfWeek，Month
     * 根据 Locale 显示对应的本地称呼
     */
    @Test
    public void DayOfWeekAndMonth(){
        //获取枚举类 DayOfWeek
//        DayOfWeek dow = DayOfWeek.FRIDAY;
        //通过 int 值获取枚举类 DayOfWeek
//        DayOfWeek dow = DayOfWeek.of(5);
        //通过枚举常量字符串返回 DayOfWeek
        DayOfWeek dow = DayOfWeek.valueOf("FRIDAY");

        //获取本地locale信息
        Locale locale = Locale.getDefault();
        //获取所请求语言环境中字段的显示名称的全称 星期五 Friday
        System.out.println(dow.getDisplayName(TextStyle.FULL, locale));
        //简称 五 F
        System.out.println(dow.getDisplayName(TextStyle.NARROW, locale));
        //较简称 星期五 Fri
        System.out.println(dow.getDisplayName(TextStyle.SHORT, locale));
        //获取星期几的int值，如：星期一 = 1
        System.out.println(dow.getValue());

        //返回这一天经过指定天数后，是星期几
        DayOfWeek plus = dow.plus(10);
        System.out.println(plus.getDisplayName(TextStyle.NARROW, locale));

        LocalDateTime date = LocalDateTime.parse("2017-01-03T10:15:30");
        LocalDateTime result = date.with(DayOfWeek.FRIDAY);//获取最近的周五日期
//        LocalDateTime result = date.with(DayOfWeek.FRIDAY).with(TemporalAdjusters.firstDayOfMonth());//获取本月第一个
//        LocalDateTime result = date.with(Month.JULY).with(TemporalAdjusters.lastDayOfMonth());//获取对应月份的最后一个
        System.out.println(result);


        System.out.println("Month --------------------------");

        Month month = Month.AUGUST;
        //八月
        System.out.println(month.getDisplayName(TextStyle.FULL, locale));
        //8
        System.out.println(month.getDisplayName(TextStyle.NARROW, locale));
        //八月
        System.out.println(month.getDisplayName(TextStyle.SHORT, locale));
        //8
        System.out.println(month.getValue());

        TimeUtils.parseLocalDate("");
    }

}
