package realworld.com.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
public class TransactionRunner {
    
    public <T> CompletableFuture<T> run(Supplier<T> action) {
        return CompletableFuture.supplyAsync(action);
    }

    @Transactional
    public <T> CompletableFuture<T> runInTransaction(Supplier<T> action) {
        return CompletableFuture.supplyAsync(action);
    }
}