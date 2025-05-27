package realworld.com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import realworld.com.utils.InMemoryPostgresStorage;
import realworld.com.utils.StorageRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    protected final StorageRunner runner;

    public BaseServiceTest() {
        this.runner = new StorageRunner(InMemoryPostgresStorage.databaseConnector());
    }

    @BeforeEach
    public void setUp() {
        // Add any common setup logic here
    }

    protected <T> T awaitForResult(CompletableFuture<T> futureResult) {
        try {
            return futureResult.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get result", e);
        }
    }

    protected <T> T dbRun(Object dbio) {
        try {
            return awaitForResult(runner.run(dbio));
        } catch (Exception e) {
            throw new RuntimeException("Failed to run database operation", e);
        }
    }

    protected Timestamp currentWhenInserting() {
        return new Timestamp(new Date().getTime());
    }
}