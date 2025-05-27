package com.realworld.test_helpers;

import java.sql.Timestamp;
import java.util.Date;

public class Dates {
    private Dates() {
        // Utility class, prevent instantiation
    }

    public static Timestamp currentWhenInserting() {
        return new Timestamp(new Date().getTime());
    }
}