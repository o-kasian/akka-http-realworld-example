package com.realworld.utils;

import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.Credentials;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.Net;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.Storage;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.Timeout;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Profile("test")
public class InMemoryPostgresStorage {
    private static final String DB_HOST = "127.0.0.1";
    private static final int DB_PORT = 5999;
    private static final String DB_NAME = "real_world_dev_test";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String JDBC_URL = String.format("jdbc:postgresql://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);

    private final PostgresConfig postgresConfig;
    private final PostgresStarter<PostgresConfig, ? extends ru.yandex.qatools.embed.postgresql.PostgresExecutable, ? extends ru.yandex.qatools.embed.postgresql.PostgresProcess> postgresStarter;
    private final DatabaseMigrationManager flywayService;
    private final DatabaseConnector databaseConnector;
    private ru.yandex.qatools.embed.postgresql.PostgresProcess process;

    public InMemoryPostgresStorage() {
        this.postgresConfig = new PostgresConfig(
            Version.V9_6_11,
            new Net(DB_HOST, DB_PORT),
            new Storage(DB_NAME),
            new Timeout(),
            new Credentials(DB_USER, DB_PASSWORD)
        );
        this.postgresStarter = PostgresStarter.getDefaultInstance();
        this.flywayService = new DatabaseMigrationManager(JDBC_URL, DB_USER, DB_PASSWORD);
        this.databaseConnector = new DatabaseConnector(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    @PostConstruct
    public void startDatabase() throws Exception {
        process = postgresStarter.prepare(postgresConfig).start();
        flywayService.dropDatabase();
        flywayService.migrateDatabaseSchema();
    }

    @PreDestroy
    public void stopDatabase() {
        if (process != null) {
            process.stop();
        }
    }

    public String getJdbcUrl() {
        return JDBC_URL;
    }

    public String getDbUser() {
        return DB_USER;
    }

    public String getDbPassword() {
        return DB_PASSWORD;
    }

    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }
}