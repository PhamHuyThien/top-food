package com.datn.topfood.util;

import java.util.Date;

public class DateUtils {
    public static Date timestampToDate(long time) {
        return new Date(time);
    }
}
