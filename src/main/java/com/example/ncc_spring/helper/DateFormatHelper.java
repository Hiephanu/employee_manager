package com.example.ncc_spring.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatHelper {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatToString(LocalDate localDate) {
        if(localDate == null) {
            return  null;
        }
        return DATE_TIME_FORMATTER.format(localDate);
    }


    public static LocalDate formatToLocalDate(String dateString) {
        if(dateString == null || dateString.isEmpty()) {
            return  null;
        }
        return LocalDate.parse(dateString, DATE_TIME_FORMATTER);
    }

    public static  LocalDate convertFromLocalDateTimeToDateTime(LocalDateTime localDateTime) {
        return  localDateTime.toLocalDate();
    }
}
