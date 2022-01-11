package com.oaker.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * created by fuyd on 2019/3/12
 */
public class FestivalsUtil {

    private static final String FORMAT_YMD = "yyyy-MM-dd";

    public enum WeekEnum {
        /**
         * 周一
         */
        MONDAY(1),
        /**
         * 周二
         */
        TUESDAY(2),

        /**
         * 周三
         */
        WEDNESDAY(3),
        /**
         * 周四
         */
        THURSDAY(4),
        /**
         * 周五
         */
        FRIDAY(5),
        /**
         * 周六
         */
        SATURDAY(6),
        /**
         * 周日
         */
        SUNDAY(7);

        private final int value;

        WeekEnum(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    /**
     * 法定假日
     */
    public static List<String> FESTVIVALS = Collections.unmodifiableList(Arrays.asList(
            "2019-04-05", "2019-05-01", "2019-05-02", "2019-05-03",
            "2019-05-04", "2019-06-07", "2019-09-13", "2019-10-01",
            "2019-10-02", "2019-10-03", "2019-10-04", "2019-10-05",
            "2019-10-06", "2019-10-07",
            "2020-01-01", "2020-01-24", "2020-01-25", "2020-01-26",
            "2020-01-27", "2020-01-28", "2020-01-29", "2020-01-30",
            "2020-04-04", "2020-04-05", "2020-04-06",
            "2020-05-01", "2020-05-02", "2020-05-03", "2020-05-04", "2020-05-05",
            "2020-06-25", "2020-06-26", "2020-06-27",
            "2020-10-01", "2020-10-02", "2020-10-03", "2020-10-04", "2020-10-05", "2020-10-06", "2020-10-07", "2020-10-08",
            "2021-01-01", "2021-01-02", "2021-01-03",
            "2021-02-11", "2021-02-12", "2021-02-13", "2021-02-14", "2021-02-15", "2021-02-16", "2021-02-17",
            "2021-04-03", "2021-04-04", "2021-04-05",
            "2021-05-01", "2021-05-02", "2021-05-03", "2021-05-04", "2021-05-05",
            "2021-06-12", "2021-06-13", "2021-06-14",
            "2021-09-19", "2021-09-20", "2021-09-21",
            "2021-10-01", "2021-10-02", "2021-10-03", "2021-10-04", "2021-10-05", "2021-10-06", "2021-10-07",
            "2022-01-01", "2022-01-02", "2022-01-03", "2022-01-31",
            "2022-02-01", "2022-02-02", "2022-02-03","2022-02-04", "2022-02-05", "2022-02-06",
            "2022-04-03", "2022-04-04", "2022-04-05", "2022-04-30",
            "2022-05-01", "2022-05-02", "2022-05-03", "2022-05-04",
            "2022-06-03", "2022-06-04", "2022-06-05",
            "2022-09-10", "2022-09-11", "2022-09-12",
            "2022-10-01", "2022-10-02", "2022-10-03", "2022-10-04", "2022-10-05", "2022-10-06", "2022-10-07"
    ));
    /**
     * 串休日期
     */
    public static final List<String> BEONDUTY = Collections.unmodifiableList(Arrays.asList(
            "2019-04-28", "2019-05-05", "2019-09-26", "2019-10-12",
            "2020-01-19", "2020-02-01", "2020-04-26", "2020-05-09",
            "2020-06-28", "2020-09-27", "2020-10-10",
            "2021-10-09", "2021-09-18", "2021-09-26", "2021-05-08",
            "2021-02-07", "2021-02-20",
            "2022-01-29", "2022-01-30",
            "2022-04-02", "2022-04-24",
            "2022-05-07", "2022-10-08", "2022-10-09"
    ));

    /**
     * 是否为串休日期
     * @param date
     * @return
     */
    public static Boolean beonduty (String date) {
        return BEONDUTY.contains(date);
    }

    /**
     * 工作日天数
     *
     * @param startDate 开始日期 {@link String}
     * @param endDate   结束日期 {@link String}
     */
    public static long workingDays(String startDate, String endDate) {
        return FestivalsUtil.interval(startDate, endDate).stream().filter(FestivalsUtil::isItAHoliday).count();
    }

    /**
     * 工作日天数
     *
     * @param startDate 开始日期 {@link LocalDate}
     * @param endDate   结束日期 {@link LocalDate}
     */
    public static long workingDays(LocalDate startDate, LocalDate endDate) {
        return FestivalsUtil.interval(startDate, endDate).stream().filter(FestivalsUtil::isItAHoliday).count();
    }

    /**
     * 工作日天数
     *
     * @param startDate 开始日期 {@link Date}
     * @param endDate   结束日期 {@link Date}
     */
    public static long workingDays(Date startDate, Date endDate) {
        return FestivalsUtil.interval(startDate, endDate).stream().filter(FestivalsUtil::isItAHoliday).count();
    }

    /**
     * 工作日期
     *
     * @param startDate 开始日期 {@link LocalDate}
     * @param endDate   结束日期 {@link LocalDate}
     */
    public static List<LocalDate> workingDates(LocalDate startDate, LocalDate endDate) {
        return FestivalsUtil.interval(startDate, endDate).stream().filter(FestivalsUtil::isItAHoliday).collect(toList());
    }

    /**
     * 工作日期
     *
     * @param startDate 开始日期 {@link String}
     * @param endDate   结束日期 {@link String}
     */
    public static List<LocalDate> workingDates(String startDate, String endDate) {
        return FestivalsUtil.interval(startDate, endDate).stream().filter(FestivalsUtil::isItAHoliday).collect(toList());
    }

    /**
     * 工作日期
     *
     * @param startDate 开始日期 {@link Date}
     * @param endDate   结束日期 {@link Date}
     */
    public static List<LocalDate> workingDates(Date startDate, Date endDate) {
        return FestivalsUtil.interval(startDate, endDate).stream().filter(FestivalsUtil::isItAHoliday).collect(toList());
    }

    /**
     * 是不是工作日
     *
     * @param date 日期 {@link LocalDate}
     */
    public static boolean isItAHoliday(LocalDate date) {
        if (FestivalsUtil.isHoliday(date)) {
            return false;
        }
        if (FestivalsUtil.BEONDUTY.contains(date.toString())) {
            return true;
        }
        return !FestivalsUtil.isWeekend(date);
    }

    /**
     * 是不是工作日
     *
     * @param ymd 日期 {@link String}

    public static boolean isItAHoliday(String ymd) {
        if (FestivalsUtil.isHoliday(ymd)) {
            return false;
        }
        if (FestivalsUtil.BEONDUTY.contains(ymd)) {
            return true;
        }
        return !FestivalsUtil.isWeekend(ymd);
    }
     */
    /**
     * 是否为节假日
     * @param ymd
     * @return
     */
    public static boolean isAHoliday(String ymd) {
        if (FestivalsUtil.isHoliday(ymd)) {
            return true;
        }
        if (FestivalsUtil.BEONDUTY.contains(ymd)) {
            return false;
        }
        return FestivalsUtil.isWeekend(ymd);
    }

    /**
     * 获取两个时间内所有日期
     *
     * @param start 开始日期 {@link Date}
     * @param end   结束日期 {@link Date}
     */
    public static List<LocalDate> interval(Date start, Date end) {
        LocalDate startDate = FestivalsUtil.toLocalDateTime(start).toLocalDate();
        LocalDate endDate = FestivalsUtil.toLocalDateTime(end).toLocalDate();
        return FestivalsUtil.interval(startDate, endDate);
    }

    /**
     * 获取两个时间内所有日期
     *
     * @param start 开始日期 {@link String}
     * @param end   结束日期 {@link String}
     */
    public static List<LocalDate> interval(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return FestivalsUtil.interval(startDate, endDate);
    }

    /**
     * 获取两个时间内所有日期
     *
     * @param start 开始日期 {@link LocalDate}
     * @param end   结束日期 {@link LocalDate}
     */
    public static List<LocalDate> interval(LocalDate start, LocalDate end) {
        long between = ChronoUnit.DAYS.between(start, end);
        if (between < 0) {
            return new ArrayList<>();
        }
        return Stream.iterate(start, localDate -> localDate.plusDays(1)).limit(between + 1).collect(toList());
    }

    /**
     * 获取两个时间相差多少小时
     *
     * @param start 开始日期 {@link LocalDateTime}
     * @param end   结束日期  {@link LocalDateTime}
     */
    public static long betweenHours(LocalDateTime start, LocalDateTime end) {
        Duration between = Duration.between(start, end);
        return between.toHours();
    }

    /**
     * 获取两个时间相差多少小时
     *
     * @param start 开始日期 {@link Date}
     * @param end   结束日期 {@link Date}
     */
    public static long betweenHours(Date start, Date end) {
        Duration between = Duration.between(FestivalsUtil.toLocalDateTime(start), FestivalsUtil.toLocalDateTime(end));
        return between.toHours();
    }

    /**
     * 获取两个时间相差多少小时
     *
     * @param start 开始日期 {@link String}
     * @param end   结束日期 {@link String}
     */
    public static long betweenHours(String start, String end) {
        Duration between = Duration.between(LocalDateTime.parse(start), LocalDateTime.parse(end));
        return between.toHours();
    }

    /**
     * 获取两个时间相差多少分钟
     *
     * @param start 开始日期 {@link LocalDateTime}
     * @param end   结束日期  {@link LocalDateTime}
     */
    public static long betweenMinutes(LocalDateTime start, LocalDateTime end) {
        Duration between = Duration.between(start, end);
        return between.toMinutes();
    }

    /**
     * 获取两个时间相差多少分钟
     *
     * @param start 开始日期 {@link Date}
     * @param end   结束日期 {@link Date}
     */
    public static long betweenMinutes(Date start, Date end) {
        Duration between = Duration.between(FestivalsUtil.toLocalDateTime(start), FestivalsUtil.toLocalDateTime(end));
        return between.toMinutes();
    }

    /**
     * 获取两个时间相差多少分钟
     *
     * @param start 开始日期 {@link String}
     * @param end   结束日期 {@link String}
     */
    public static long betweenMinutes(String start, String end) {
        Duration between = Duration.between(LocalDateTime.parse(start), LocalDateTime.parse(end));
        return between.toMinutes();
    }

    /**
     * 获取两个时间相差多少天
     *
     * @param start 开始日期 {@link String}
     * @param end   结束日期 {@link String}
     */
    public static long betweenDays(String start, String end) {
        Period between = Period.between(LocalDate.parse(start), LocalDate.parse(end));
        return between.getDays();
    }

    /**
     * 获取两个时间相差多少天
     *
     * @param start 开始日期 {@link LocalDate}
     * @param end   结束日期 {@link LocalDate}
     */
    public static long betweenDays(LocalDate start, LocalDate end) {
        Period between = Period.between(start, end);
        return between.getDays();
    }

    /**
     * 获取两个时间相差多少天
     *
     * @param start 开始日期 {@link Date}
     * @param end   结束日期 {@link Date}
     */
    public static long betweenDays(Date start, Date end) {
        LocalDate startDate = FestivalsUtil.toLocalDateTime(start).toLocalDate();
        LocalDate endDate = FestivalsUtil.toLocalDateTime(end).toLocalDate();
        return FestivalsUtil.betweenDays(startDate, endDate);
    }


    /**
     * 是否是周末
     *
     * @param date 日期 {@link LocalDate}
     */
    public static boolean isWeekend(LocalDate date) {
        int value = date.getDayOfWeek().getValue();
        return WeekEnum.SATURDAY.value == value || WeekEnum.SUNDAY.value == value;
    }

    /**
     * 是否是周末
     *
     * @param ymd 日期 {@link String}
     */
    public static boolean isWeekend(String ymd) {
        LocalDate date = LocalDate.parse(ymd);
        return FestivalsUtil.isWeekend(date);
    }

    /**
     * 是否是周末
     *
     * @param date 日期 {@link Date}
     */
    public static boolean isWeekend(Date date) {
        return FestivalsUtil.isWeekend(FestivalsUtil.toLocalDateTime(date).toLocalDate());
    }

    /**
     * 是否是节假日
     *
     * @param date 日期 {@link LocalDate}
     */
    public static boolean isHoliday(LocalDate date) {
        return FestivalsUtil.isHoliday(date.toString());
    }

    /**
     * 是否是节假日
     *
     * @param ymd 日期 {@link String}
     */
    public static boolean isHoliday(String ymd) {
        return FESTVIVALS.contains(ymd);
    }

    /**
     * 是否是节假日
     *
     * @param date 日期 {@link Date}
     */
    public static boolean isHoliday(Date date) {
        String format = DateFormatUtils.format(date, FORMAT_YMD);
        return FESTVIVALS.contains(format);
    }

    /**
     * 时间格式化
     *
     * @param date
     * @param ymd
     * @return
     */
    public static String formater(Date date, String ymd) {
        return DateFormatUtils.format(date, ymd);
    }

    /**
     * date转换LocaldateTime
     *
     * @param date 日期 {@link Date}
     */
    private static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

}
