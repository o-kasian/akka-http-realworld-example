package com.realworld.utils;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMigrationManager {
    private final Flyway flyway;

    public DatabaseMigrationManager(
            @Value("${spring.datasource.url}") String jdbcUrl,
            @Value("${spring.datasource.username}") String dbUser,
            @Value("${spring.datasource.password}") String dbPassword) {
        this.flyway = Flyway.configure()
                .dataSource(jdbcUrl, dbUser, dbPassword)
                .load();
    }

    public void migrateDatabaseSchema() {
        flyway.migrate();
    }

    public void dropDatabase() {
        flyway.clean();
    }
}