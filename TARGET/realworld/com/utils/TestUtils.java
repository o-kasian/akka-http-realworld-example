package realworld.com.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestUtils {
    private static final String DB_HOST = "127.0.0.1";
    private static final int DB_PORT = 5999;
    private static final String DB_NAME = "real_world_dev_test";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String JDBC_URL = String.format("jdbc:postgresql://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);

    private static PostgresProcess postgresProcess;
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("org.postgresql.Driver");
            ds.setUrl(JDBC_URL);
            ds.setUsername(DB_USER);
            ds.setPassword(DB_PASSWORD);
            dataSource = ds;
        }
        return dataSource;
    }

    public static void startDatabase() throws Exception {
        PostgresStarter<PostgresExecutable, PostgresProcess> starter = PostgresStarter.getDefaultInstance();
        
        PostgresConfig config = new PostgresConfig(
            Version.V9_6_11,
            new AbstractPostgresConfig.Net(DB_HOST, DB_PORT),
            new AbstractPostgresConfig.Storage(DB_NAME),
            new AbstractPostgresConfig.Timeout(),
            new AbstractPostgresConfig.Credentials(DB_USER, DB_PASSWORD)
        );

        PostgresExecutable executable = starter.prepare(config);
        postgresProcess = executable.start();

        // Wait for DB to start
        TimeUnit.SECONDS.sleep(1);

        // Initialize schema
        DatabaseMigrationManager flywayService = new DatabaseMigrationManager(JDBC_URL, DB_USER, DB_PASSWORD);
        flywayService.dropDatabase();
        flywayService.migrateDatabaseSchema();
    }

    public static void stopDatabase() {
        if (postgresProcess != null) {
            postgresProcess.stop();
        }
    }

    public static void cleanDatabase() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        List<String> tableNames = new ArrayList<>();
        
        try (Connection conn = getDataSource().getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, "public", null, new String[]{"TABLE"});
            
            while (tables.next()) {
                tableNames.add(tables.getString("TABLE_NAME"));
            }
            
            // Disable foreign key checks
            jdbcTemplate.execute("SET CONSTRAINTS ALL DEFERRED");
            
            // Truncate all tables
            for (String tableName : tableNames) {
                jdbcTemplate.execute("TRUNCATE TABLE " + tableName + " CASCADE");
            }
            
            // Re-enable foreign key checks
            jdbcTemplate.execute("SET CONSTRAINTS ALL IMMEDIATE");
            
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean database", e);
        }
    }
}