package realworld.com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
        // Add any setup code here
    }

    protected <T> T awaitForResult(CompletableFuture<T> futureResult) {
        try {
            return futureResult.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get result", e);
        }
    }

    protected <T> T dbRun(Supplier<T> operation) {
        return transactionTemplate.execute(status -> {
            try {
                return operation.get();
            } catch (Exception e) {
                throw new RuntimeException("Database operation failed", e);
            }
        });
    }

    protected Timestamp currentWhenInserting() {
        return new Timestamp(new Date().getTime());
    }
}