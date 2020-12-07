package date;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 */
public class DateUtils {

    private static final Object LOCK = new Object();

    private static final Map<String, ThreadLocal<SimpleDateFormat>> FORMATTER_CACHE = new HashMap<>();

    /**
     * 获取当前日期
     */
    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前是年的第几个月份
     */
    public static int getCurrentMonth() {
        return nowDate().getMonthValue();
    }

    /**
     * 当前是年的第几天
     */
    public static int getCurrentDayOfYear() {
        return nowDate().getDayOfYear();
    }

    /**
     * 当前是周的第几天
     */
    public static int getCurrentDayOfWeek() {
        return nowDate().getDayOfWeek().getValue();
    }

    /**
     * 判断是否是闰年
     *
     * @param year 年份
     * @return true 是/ false 否
     */
    public static boolean isLeapYear(int year) {
        LocalDate date = LocalDate.of(year, Month.JANUARY, 1);
        return date.isLeapYear();
    }

    /**
     * 获取指定年份的国庆节
     */
    public static LocalDate nationalDayOfYear(int year) {
        return LocalDate.of(year, 10, 1);
    }

    /**
     * 当前时间（HH:mm:ss.nnn）
     */
    public static LocalTime nowTime() {
        return LocalTime.now(Clock.systemDefaultZone());
    }

    /**
     * 当前时间（HH:mm:ss.nnn）
     *
     * @param zoneId 时区 {@link ZoneId#SHORT_IDS ZoneId#SHORT_IDS.value}
     */
    public static LocalTime nowTime(String zoneId) {
        return LocalTime.now(ZoneId.of(zoneId));
    }

    /**
     * 当前时间（yyyy-MM-dd HH:mm:ss.nnn）
     */
    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 根据毫秒数返回 {@link LocalDateTime}
     *
     * @param milliSeconds 毫秒
     * @return LocalDateTime
     */
    public static LocalDateTime of(Long milliSeconds) {
        return ofLocalDateTime(Instant.ofEpochMilli(milliSeconds), ZoneId.systemDefault());
    }

    /**
     * 根据毫秒数返回 {@link LocalDateTime}
     *
     * @param milliSeconds 毫秒
     * @param zoneId       毫秒
     * @return LocalDateTime
     */
    public static LocalDateTime of(Long milliSeconds, String zoneId) {
        return ofLocalDateTime(Instant.ofEpochMilli(milliSeconds), ZoneId.of(zoneId));
    }

    /**
     * 获取{@code SimpleDateFormat}
     *
     * @param pattern 日期格式
     * @return formatter
     */
    public static SimpleDateFormat getFormatter(String pattern) {
        return getInstance(pattern);
    }

    public static DateTimeFormatter getNewFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * 日期格式化
     *
     * @param date    日期
     * @param pattern 格式
     * @return 格式化后的日期
     */
    public static String format(Date date, String pattern) {
        return getInstance(pattern).format(date);
    }

    private static LocalDateTime ofLocalDateTime(Instant instant, ZoneId zoneId) {
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    private static SimpleDateFormat getInstance(String pattern) {
        ThreadLocal<SimpleDateFormat> threadLocal = FORMATTER_CACHE.get(pattern);
        if (threadLocal == null) {
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + "获得锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadLocal = FORMATTER_CACHE.get(pattern);
                if (threadLocal == null) {
                    threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
                    FORMATTER_CACHE.put(pattern, threadLocal);
                }
            }
        }
        return threadLocal.get();
    }

}
