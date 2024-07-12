package com.example.ncc_spring.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatHelper {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatToString(LocalDateTime localDateTime) {
        if(localDateTime == null) {
            return  null;
        }
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    public static LocalDateTime formatToLocalDatetime(String dateString) {
        if(dateString == null || dateString.isEmpty()) {
            return  null;
        }
        return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
    }
}
