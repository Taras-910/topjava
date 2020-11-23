package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public String print(LocalTime localTime, Locale locale) {
        if (localTime == null) {
            return "";
        }
        return localTime.toString();
    }

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return parseLocalTime(text);
    }
}
