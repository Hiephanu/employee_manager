package com.example.ncc_spring.helper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CompareTimeHelper {
    public static int compareBetweenLocalDateTime(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        Duration duration = Duration.between(localDateTime1, localDateTime2);
        return (int) duration.toMinutes();
    }

    public static int compareLocalDateTimeWithFixTime(LocalDateTime localDateTime, LocalTime localTime) {
        LocalTime dateTimeTime = localDateTime.toLocalTime();
        Duration duration = Duration.between(dateTimeTime, localTime);
        return (int) duration.toMinutes();
    }

}
