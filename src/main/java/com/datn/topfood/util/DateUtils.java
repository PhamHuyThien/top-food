package com.datn.topfood.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static Date timestampToDate(long time) {
        return new Date(time);
    }

    public static Date timestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    public static Timestamp dateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    public static Date currentDate() {
        return new Date();
    }

    public static long currentLongTime() {
        return currentDate().getTime();
    }

    public static int daysBetweenTwoDates(Timestamp startDate, Timestamp endDate) {
        long diffMilliseconds = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.DAYS.convert(diffMilliseconds, TimeUnit.MILLISECONDS);
    }

    public static int hoursBetweenTwoDates(Timestamp startDate, Timestamp endDate) {
        long diffMilliseconds = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.HOURS.convert(diffMilliseconds, TimeUnit.MILLISECONDS);
    }

    public static int minutesBetweenTwoDates(Timestamp startDate, Timestamp endDate) {
        long diffMilliseconds = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.MINUTES.convert(diffMilliseconds, TimeUnit.MILLISECONDS);
    }

    public static int secondsBetweenTwoDates(Timestamp startDate, Timestamp endDate) {
        long diffMilliseconds = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.SECONDS.convert(diffMilliseconds, TimeUnit.MILLISECONDS);
    }
}
