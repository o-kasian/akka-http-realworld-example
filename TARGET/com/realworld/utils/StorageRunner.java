package com.realworld.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

public interface Runner {
    <T> CompletableFuture<T> run(DatabaseAction<T> action);

    <T> CompletableFuture<T> runInTransaction(DatabaseAction<T> action);
}

@Component
public class StorageRunner implements Runner {
    private final DatabaseConnector databaseConnector;

    public StorageRunner(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public <T> CompletableFuture<T> run(DatabaseAction<T> action) {
        return CompletableFuture.supplyAsync(() -> action.execute(databaseConnector.getEntityManager()));
    }

    @Override
    @Transactional
    public <T> CompletableFuture<T> runInTransaction(DatabaseAction<T> action) {
        return CompletableFuture.supplyAsync(() -> action.execute(databaseConnector.getEntityManager()));
    }
}

@FunctionalInterface
interface DatabaseAction<T> {
    T execute(javax.persistence.EntityManager entityManager);
}