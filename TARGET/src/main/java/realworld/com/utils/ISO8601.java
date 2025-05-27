package realworld.com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601 {
    private static final String MILLIS_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static Date parse(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(MILLIS_FORMAT_STRING);
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing ISO8601 date: " + dateString, e);
        }
    }

    public static String format(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(MILLIS_FORMAT_STRING);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
}