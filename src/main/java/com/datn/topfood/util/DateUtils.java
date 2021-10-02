package com.datn.topfood.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtils {
    public static Date timestampToDate(long time) {
        return new Date(time);
    }
    public static Timestamp currentTimestamp() {
        return new Timestamp(new Date().getTime());
    }
}
