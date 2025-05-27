package realworld.com.test_helpers;

import java.sql.Timestamp;
import java.util.Date;

public class TestHelpers {
    public static Timestamp currentWhenInserting() {
        return new Timestamp(new Date().getTime());
    }
}