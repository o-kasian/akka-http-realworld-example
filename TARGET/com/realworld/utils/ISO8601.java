package com.realworld.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601 {
    private static final String MILLIS_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    
    private ISO8601() {
        // Private constructor to prevent instantiation
    }

    public static Date fromString(String dateStr) throws ParseException {
        return new SimpleDateFormat(MILLIS_FORMAT_STRING).parse(dateStr);
    }

    public static String toString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(MILLIS_FORMAT_STRING);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }
}