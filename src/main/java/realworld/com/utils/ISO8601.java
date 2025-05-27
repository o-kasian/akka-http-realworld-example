package realworld.com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601 {
    private static final String MILLIS_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    private ISO8601() {} // Utility class

    public static Date parse(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(MILLIS_FORMAT);
        sdf.setTimeZone(UTC);
        return sdf.parse(dateStr);
    }

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(MILLIS_FORMAT);
        sdf.setTimeZone(UTC);
        return sdf.format(date);
    }
}