package realworld.com.utils;

public class SlugUtils {
    
    /**
     * Converts a title to a URL-friendly slug
     * 
     * @param title The title to slugify
     * @return The slugified title
     */
    public static String slugify(String title) {
        if (title == null) {
            return "";
        }
        return title.toLowerCase().replaceAll("\\s", "-");
    }
}