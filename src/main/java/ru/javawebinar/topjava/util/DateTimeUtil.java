package ru.javawebinar.topjava.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
    public static String print(Date dt) {
        return new SimpleDateFormat(DATE_PATTERN).format(dt);
    }
}

/*package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class DateAndLocalTimeFormatters {

    public static class DateFormatter implements Formatter<Date> {

        @Override
        public Date parse(String text, Locale locale) {
            Date localDate;
            try {
                localDate = new SimpleDateFormat("yyyy-MM-dd").parse(text);
            } catch (ParseException e) {
                throw new IllegalArgumentException("error argument localDate=" + text);
            }
            return localDate;
        }

        @Override
        public String print(Date dt, Locale locale) {
            return new SimpleDateFormat("yyyy-MM-dd").format(dt);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) {
            return parseLocalTime(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}
*/



