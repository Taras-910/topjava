package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public final class LocalDateFormatter implements Formatter<LocalDate> {

    public String print(LocalDate date, Locale locale) {
        if (date == null) {
            return "";
        }
        return date.toString();
    }

    public LocalDate parse(String text, Locale locale) throws ParseException {
        return parseLocalDate(text);
    }
}
