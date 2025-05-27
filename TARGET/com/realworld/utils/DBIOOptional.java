package com.realworld.utils;

import org.springframework.scheduling.annotation.Async;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class DBIOOptional<A> {
    private final CompletableFuture<Optional<A>> future;

    private DBIOOptional(CompletableFuture<Optional<A>> future) {
        this.future = future;
    }

    public static <A> DBIOOptional<A> of(CompletableFuture<Optional<A>> future) {
        return new DBIOOptional<>(future);
    }

    @Async
    public <B> DBIOOptional<B> flatMap(Function<A, DBIOOptional<B>> f) {
        CompletableFuture<Optional<B>> newFuture = future.thenCompose(optA ->
            optA.map(a -> f.apply(a).future)
                .orElse(CompletableFuture.completedFuture(Optional.empty()))
        );
        return new DBIOOptional<>(newFuture);
    }

    @Async
    public <B> DBIOOptional<B> map(Function<A, B> f) {
        CompletableFuture<Optional<B>> newFuture = future.thenApply(optA -> optA.map(f));
        return new DBIOOptional<>(newFuture);
    }

    public CompletableFuture<Optional<A>> getFuture() {
        return future;
    }
}