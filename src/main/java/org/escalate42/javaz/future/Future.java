package org.escalate42.javaz.future;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.tuple.Tuple2;
import org.escalate42.javaz.option.Option;
import org.escalate42.javaz.trym.TryM;

import java.util.concurrent.CompletableFuture;

/**
 * Created by vdubs
 * on 1/23/15.
 */
public interface Future<T> extends Monad<T, Future<?>> {
    @Override public <U> Future<U> map(Function<T, U> function);
    @Override public <U> Future<U> pure(U value);
    @Override public <U, MM extends Applicative<Function<T, U>, Future<?>>> Future<U> amap(MM applicativeFunction);
    @Override public <U, MM extends Monad<U, Future<?>>> Future<U> flatMap(Function<T, MM> function);
    @Override public default void foreach(Applicable<T> function) { onSuccess(function); }
    public boolean complete(T value);
    public boolean complete(Throwable t);
    public boolean complete(TryM<T> t);
    public void onSuccess(Applicable<T> onSuccess);
    public void onFailure(Applicable<Throwable> onFailure);
    public void onComplete(Applicable<TryM<T>> onComplete);
    public boolean isCompleted();
    public <U> Future<U> recover(Function<Throwable, U> recover);
    public <U> Future<U> recoverWith(Function<Throwable, Future<U>> recover);
    public Option<TryM<T>> value();
    public <U> Future<Tuple2<T, U>> zip(Future<U> another);
    public CompletableFuture<T> toCompletableFuture();
}
