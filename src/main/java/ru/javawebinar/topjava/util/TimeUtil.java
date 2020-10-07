package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String str) {
        return str.isEmpty() ? null : LocalDate.parse(str);
    }

    public static LocalTime parseLocalTime(String str) {
        return str.isEmpty() ? null : LocalTime.parse(str);
    }

    public static LocalDateTime parseLocalDateTime(String str) {
        return str.isEmpty() ? null : LocalDateTime.parse(str);
    }

}

