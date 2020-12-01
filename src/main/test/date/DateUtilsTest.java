package date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author leofee
 */
class DateUtilsTest {

    static void nowDate() {
        LocalDate nowDate = DateUtils.nowDate();
        System.out.println("当前日期为：" + nowDate);
    }

    static void nationalDayOfYear() {
        LocalDate nationalDayOfYear = DateUtils.nationalDayOfYear(2020);
        System.out.println("2020年国庆节为：" + nationalDayOfYear);
    }

    static void isLeapYear() {
        boolean leapYear = DateUtils.isLeapYear(2020);
        System.out.println("2020年是否是闰年：" + leapYear);
    }

    static void currentMonth() {
        int month = DateUtils.getCurrentMonth();
        System.out.println("当前是第几月：" + month);
    }

    static void currentDayOfYear() {
        int dayOfYear = DateUtils.getCurrentDayOfYear();
        System.out.println("当前是年的第几天：" + dayOfYear);
    }

    static void currentDayOfWeek() {
        int dayOfWeek = DateUtils.getCurrentDayOfWeek();
        System.out.println("当前是周的第几天：" + dayOfWeek);
    }

    static void nowTime() {
        LocalTime localTime = DateUtils.nowTime();
        System.out.println("当前时间为：" + localTime);
        LocalTime tokyoTime = DateUtils.nowTime("Asia/Tokyo");
        System.out.println("当前东京时间为：" + tokyoTime);
    }

    static void nowDateTime() {
        LocalDateTime localDateTime = DateUtils.nowDateTime();
        System.out.println("当前日期 + 时间为：" + localDateTime);
    }

    static void convertFromMilliSecond(Long milliSecond) {
        LocalDateTime dateTime = DateUtils.of(milliSecond);
        System.out.println("根据毫秒获取当前时间：" + dateTime);
    }

    public static void main(String[] args) {
        nowDate();
        nationalDayOfYear();
        isLeapYear();
        currentMonth();
        currentDayOfYear();
        currentDayOfWeek();

        nowTime();

        nowDateTime();
        convertFromMilliSecond(System.currentTimeMillis());
    }
}