package realworld.com.utils;

import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class Utils {
    private static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static String toISO8601(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO8601_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    public static Date fromISO8601(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(ISO8601_FORMAT);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse ISO8601 date: " + dateStr, e);
        }
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    public static String slugify(String input) {
        if (input == null) {
            return "";
        }
        return input.toLowerCase().replaceAll("\\s", "-");
    }
}