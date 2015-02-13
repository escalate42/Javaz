package org.escalate42.javaz.future;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.ThrowableClosure;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.tuple.Tuple2;
import org.escalate42.javaz.option.Option;
import org.escalate42.javaz.trym.TryM;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.escalate42.javaz.option.OptionImpl.*;
import static org.escalate42.javaz.trym.TryMImpl.*;

/**
 * Created by vdubs
 * on 1/27/15.
 */
@SuppressWarnings("unchecked")
public class FutureImpl<T> implements Future<T> {

    private final Executor executor;
    private final CompletableFuture<T> sourceFuture;
    private final CompletableFuture<T> body;
    private final AtomicReference<Option<TryM<T>>> result = new AtomicReference<>(none());

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

    private FutureImpl(final Executor executor, final CompletableFuture<T> future) {
        this.executor = executor;
        this.sourceFuture = future;
        this.body = future.whenComplete((r, t) -> {
            if (r != null) {
                result.set(some(success(r)));
            } else if (t != null) {
                Throwable cause = t;
                while (cause.getCause() != null) { cause = cause.getCause(); }
                result.set(some(fail(cause)));
            }
        });
    }

    @Override
    public boolean complete(T value) {
        return complete(success(value));
    }

    @Override
    public boolean complete(Throwable t) {
        return complete(fail(t));
    }

    @Override
    public boolean complete(TryM<T> t) {
        return t.isSuccess() ? this.sourceFuture.complete(t.value()) : this.sourceFuture.completeExceptionally(t.throwable());
    }

    @Override
    public void onSuccess(Applicable<T> onSuccess) {
        this.body.whenCompleteAsync((r, t) ->
            this.result.get().foreach((tryM) -> tryM.foreach(onSuccess))
        );
    }

    @Override
    public void onFailure(Applicable<Throwable> onFailure) {
        this.body.whenCompleteAsync((r, t) ->
            this.result.get().foreach((tryM) -> tryM.foreachFailure(onFailure))
        );
    }

    @Override
    public void onComplete(Applicable<TryM<T>> onComplete) {
        this.body.whenCompleteAsync((r, t) -> onComplete.apply(result.get().get()));
    }

    @Override
    public boolean isCompleted() {
        return this.body.isDone();
    }

    @Override
    public Future<T> recover(Function<Throwable, T> recover) {
        return future(this.executor, this.body.exceptionally(recover::apply));
    }

    @Override
    public Future<T> recoverWith(Function<Throwable, Future<T>> recover) {
        final Future<T> future = future(this.executor);
        onFailure((t) -> recover.apply(t).onComplete(future::complete));
        onSuccess(future::complete);
        return future;
    }

    @Override
    public Option<TryM<T>> value() {
        return this.result.get();
    }

    @Override
    public TryM<T> get() {
        try {
            this.body.get();
            return this.result.get().get();
        } catch (Throwable e) {
            if (this.body.isCompletedExceptionally()) {
                return this.result.get().get();
            } else {
                return fail(e);
            }
        }
    }

    @Override
    public TryM<T> get(long timeout, TimeUnit timeUnit) {
        try {
            this.body.get(timeout, timeUnit);
            return this.result.get().get();
        } catch (Throwable e) {
            if (this.body.isCompletedExceptionally()) {
                return this.result.get().get();
            } else {
                return fail(e);
            }
        }
    }

    @Override
    public <U> Future<Tuple2<T, U>> zip(Future<U> another) {
        return flatMap((t) -> another.map((u) -> Tuple2.t(t, u)));
    }

    @Override
    public <U, MM extends Monad<U, Future<?>>> Future<U> flatMap(Function<T, MM> function) {
        final Future<U> future = future(this.executor);
        final Function<T, Future<U>> toApply = (Function<T, Future<U>>)function;
        onComplete((tryM) -> {
            if (tryM.isSuccess()) {toApply.apply(tryM.value()).onComplete(future::complete); }
            else { future.complete(tryM.throwable()); }
        });
        return future;
    }

    @Override
    public <U> Future<U> pure(U value) {
        return completed(value);
    }

    @Override
    public <U, MM extends Applicative<Function<T, U>, Future<?>>> Future<U> amap(MM applicativeFunction) {
        final Future<Function<T, U>> applicative = (Future<Function<T, U>>)applicativeFunction;
        final Future<U> future = future(this.executor);
        applicative.onComplete((tryM) -> {
            if (tryM.isSuccess()) {
                onComplete((iTryM) -> {
                    if (iTryM.isSuccess()) { future.complete(tryM.value().apply(iTryM.value())); }
                    else { future.complete(iTryM.throwable()); }
                });
            } else { future.complete(tryM.throwable()); }
        });
        return future;
    }

    @Override
    public <U> Future<U> map(Function<T, U> function) {
        return future(this.executor, this.body.handleAsync((r, t) -> {
            if (r != null) {
                return function.apply(r);
            } else if (t.getClass().equals(CompletionException.class)) {
                throw (CompletionException)t;
            } else {
                throw new RuntimeException(t);
            }
        }));
    }

    @Override
    public CompletableFuture<T> toCompletableFuture() {
        return this.body;
    }
}