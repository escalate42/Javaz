package org.escalate42.javaz.future;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.ThrowableClosure;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.tuple.Tuple2;
import org.escalate42.javaz.option.Option;
import org.escalate42.javaz.trym.TryM;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import static org.escalate42.javaz.option.OptionImpl.*;
import static org.escalate42.javaz.trym.TryMImpl.*;

/**
 * Created by vdubs
 * on 1/27/15.
 */
@SuppressWarnings("unchecked")
public class FutureImpl<T> implements Future<T> {

    private final Executor executor;
    private final CompletableFuture<T> body;
    private volatile Option<TryM<T>> result = none();

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
        this.body = future.whenComplete((r, t) -> {
            if (r != null) {
                result = some(success(r));
            } else if (t != null) {
                result = some(fail(t.getCause().getCause()));
            }
        });
    }

    @Override
    public boolean complete(T value) {
        return this.body.complete(value);
    }

    @Override
    public boolean complete(Throwable t) {
        return complete(fail(t));
    }

    @Override
    public boolean complete(TryM<T> t) {
        final boolean completed = t.isSuccess() ? this.body.complete(t.value()) : this.body.completeExceptionally(t.throwable());
        if (completed) {this.result = some(t);}
        return completed;
    }

    @Override
    public void onSuccess(Applicable<T> onSuccess) {
        this.body.whenCompleteAsync((r, t) ->
            this.result.foreach((tryM) -> tryM.foreach(onSuccess))
        );
    }

    @Override
    public void onFailure(Applicable<Throwable> onFailure) {
        this.body.whenCompleteAsync((r, t) ->
            this.result.foreach((tryM) -> tryM.foreachFailure(onFailure))
        );
    }

    @Override
    public void onComplete(Applicable<TryM<T>> onComplete) {
        this.body.whenCompleteAsync((r, t) -> onComplete.apply(result.get()));
    }

    @Override
    public boolean isCompleted() {
        return this.body.isDone();
    }

    @Override
    public <U> Future<U> recover(Function<Throwable, U> recover) {
        return null;
    }

    @Override
    public <U> Future<U> recoverWith(Function<Throwable, Future<U>> recover) {
        return null;
    }

    @Override
    public Option<TryM<T>> value() {
        return this.result;
    }

    @Override
    public <U> Future<Tuple2<T, U>> zip(Future<U> another) {
        return null;
    }

    @Override
    public <U, MM extends Monad<U, Future<?>>> Future<U> flatMap(Function<T, MM> function) {
        final Future<U> future = future(this.executor);
        final Function<T, Future<U>> toApply = (Function<T, Future<U>>)function;
        onComplete((tryM) ->{
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
        return null;
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

    public static void main(final String[] args) throws InterruptedException {
        System.out.println("Showtime");
        final Future<String> future = future(() -> {Thread.sleep(1000); return "Vadim";});
        future.onComplete((r) -> System.out.println(r + "->" + future.value()));
        future.onSuccess((r) -> System.out.println(r + "->" + future.value()));
        future.onFailure(Throwable::printStackTrace);
        final Future<String> mapped = future.map(String::toUpperCase);
        final Future<String> flatMapped = future.flatMap((s) -> future((ThrowableClosure<String>)s::toUpperCase));
        flatMapped.onComplete((t) -> System.out.println("FlatMapped just have been completed"));
        Thread.sleep(2000);
        System.out.println(future.value());
        System.out.println(mapped.value());
        System.out.println(flatMapped.value());
    }
}
