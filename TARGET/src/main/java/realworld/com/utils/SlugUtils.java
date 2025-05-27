package realworld.com.utils;

public class SlugUtils {
    public static String slugify(String title) {
        if (title == null) {
            return "";
        }
        return title.toLowerCase().replaceAll("\\s", "-");
    }
}