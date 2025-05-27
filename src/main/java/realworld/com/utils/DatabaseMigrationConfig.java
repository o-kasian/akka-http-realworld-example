package realworld.com.utils;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseMigrationConfig {
    
    private final DataSource dataSource;

    public DatabaseMigrationConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .load();
            
        flyway.migrate();
        return flyway;
    }

    public void dropDatabase() {
        flyway().clean();
    }
}