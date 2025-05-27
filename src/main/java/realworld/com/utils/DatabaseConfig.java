package realworld.com.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    private final Config.DatabaseConfig dbConfig;

    public DatabaseConfig(Config config) {
        this.dbConfig = config.getDatabase();
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s",
            dbConfig.getHost(),
            dbConfig.getPort(),
            dbConfig.getDb());
            
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(dbConfig.getUsername());
        hikariConfig.setPassword(dbConfig.getPassword());

        return new HikariDataSource(hikariConfig);
    }
}