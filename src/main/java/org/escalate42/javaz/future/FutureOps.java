package org.escalate42.javaz.future;

import org.escalate42.javaz.common.function.ThrowableClosure;
import org.escalate42.javaz.common.monad.MonadOps;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by vdubs
 * on 2/5/15.
 */
public class FutureOps implements MonadOps<Future<?>> {

    public static <T> FutureImpl<T> future(Executor executor) {
        return new FutureImpl<>(executor, new CompletableFuture<>());
    }

    public static <T> FutureImpl<T> future(Executor executor, CompletableFuture<T> completableFuture) {
        return new FutureImpl<>(executor, completableFuture);
    }

    public static <T> FutureImpl<T> future(Executor executor, ThrowableClosure<T> closure) {
        return new FutureImpl<>(executor, CompletableFuture.supplyAsync(closure.ommitThrow()::apply, executor));
    }

    public static <T> FutureImpl<T> completed(Executor executor, T value) {
        return new FutureImpl<>(executor, CompletableFuture.completedFuture(value));
    }

    public static <T> FutureImpl<T> completed(Executor executor, Throwable t) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(t);
        return new FutureImpl<>(executor, future);
    }

    public static <T> FutureImpl<T> future() {
        return future(ForkJoinPool.commonPool());
    }

    public static <T> FutureImpl<T> future(CompletableFuture<T> completableFuture) {
        return future(ForkJoinPool.commonPool(), completableFuture);
    }

    public static <T> FutureImpl<T> future(ThrowableClosure<T> closure) {
        return future(ForkJoinPool.commonPool(), closure);
    }

    public static <T> FutureImpl<T> completed(T value) {
        return completed(ForkJoinPool.commonPool(), value);
    }

    public static <T> FutureImpl<T> completed(Throwable t) {
        return completed(ForkJoinPool.commonPool(), t);
    }

    private FutureOps() {}

    public static final FutureOps id = new FutureOps();

    @Override
    public <U> Future<?> pure(U value) {
        return completed(value);
    }
}
