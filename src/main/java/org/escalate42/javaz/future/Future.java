package org.escalate42.javaz.future;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.tuple.Tuple2;
import org.escalate42.javaz.option.Option;
import org.escalate42.javaz.trym.TryM;

/**
 * Created by vdubs
 * on 1/23/15.
 */
public interface Future<T> extends Monad<T, Future<?>> {
    public void onSuccess(Function<T, Void> onSuccess);
    public void onFailure(Function<Throwable, Void> onFailure);
    public void onComplete(Function<TryM<T>, Void> onComplete);
    public boolean isCompleted();
    public <U> Future<U> recover(Function<Throwable, U> recover);
    public <U> Future<U> recoverWith(Function<Throwable, Future<U>> recover);
    public Option<TryM<T>> value();
    public Future<T> ready(long duration);
    public T result(long duration);
    public <U> Future<T> andThen(Function<TryM<T>, U> function);
    public <U> Future<Tuple2<T, U>> zip(Future<U> another);
    public <U> Future<U> transform(Function<T, U> transformer, Function<Throwable, ? extends Throwable> throwableFunction);
}
