package realworld.com.utils;

import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.Credentials;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.Net;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.Storage;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.Timeout;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;

public class InMemoryPostgresStorage {
    public static final String DB_HOST = "127.0.0.1";
    public static final int DB_PORT = 5999;
    public static final String DB_NAME = "real_world_dev_test";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "postgres";
    public static final String JDBC_URL = String.format("jdbc:postgresql://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);

    private static final PostgresConfig PSQL_CONFIG;
    private static final PostgresStarter<PostgresConfig, ? extends ru.yandex.qatools.embed.postgresql.PostgresExecutable, ? extends ru.yandex.qatools.embed.postgresql.PostgresProcess> PSQL_INSTANCE;
    private static final ru.yandex.qatools.embed.postgresql.PostgresProcess PROCESS;
    private static final DataSource DATA_SOURCE;

    static {
        try {
            PSQL_CONFIG = new PostgresConfig(
                Version.V9_6_11,
                new Net(DB_HOST, DB_PORT),
                new Storage(DB_NAME),
                new Timeout(),
                new Credentials(DB_USER, DB_PASSWORD)
            );

            PSQL_INSTANCE = PostgresStarter.getDefaultInstance();
            PROCESS = PSQL_INSTANCE.prepare(PSQL_CONFIG).start();

            // Initialize DataSource
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl(JDBC_URL);
            dataSource.setUsername(DB_USER);
            dataSource.setPassword(DB_PASSWORD);
            DATA_SOURCE = dataSource;

            // Initialize and migrate database
            DatabaseMigrationManager flywayService = new DatabaseMigrationManager(JDBC_URL, DB_USER, DB_PASSWORD);
            flywayService.dropDatabase();
            flywayService.migrateDatabaseSchema();

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize in-memory PostgreSQL", e);
        }
    }

    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }

    public static void shutDown() {
        if (PROCESS != null) {
            PROCESS.stop();
        }
    }
}