package org.escalate42.javaz.future;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.tuple.Tuple2;
import org.escalate42.javaz.option.Option;
import org.escalate42.javaz.trym.TryM;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by vdubs
 * on 1/23/15.
 */
public interface Future<T> extends Monad<T, Future<?>> {
    @Override <U> Future<U> map(Function<T, U> function);
    @Override <U, MM extends Applicative<Function<T, U>, Future<?>>> Future<U> amap(MM applicativeFunction);
    @Override <U, MM extends Monad<U, Future<?>>> Future<U> flatMap(Function<T, MM> function);
    @Override default void foreach(Applicable<T> function) { onSuccess(function); }
    boolean complete(T value);
    boolean complete(Throwable t);
    boolean complete(TryM<T> t);
    void onSuccess(Applicable<T> onSuccess);
    void onFailure(Applicable<Throwable> onFailure);
    void onComplete(Applicable<TryM<T>> onComplete);
    boolean isCompleted();
    Future<T> recover(Function<Throwable, T> recover);
    Future<T> recoverWith(Function<Throwable, Future<T>> recover);
    Option<TryM<T>> value();
    TryM<T> get();
    TryM<T> get(long timeout, TimeUnit timeUnit);
    <U> Future<Tuple2<T, U>> zip(Future<U> another);
    CompletableFuture<T> toCompletableFuture();
}
