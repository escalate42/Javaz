package org.escalate42.javaz.future;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.ThrowableClosure;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.tuple.Tuple2;
import org.escalate42.javaz.option.Option;
import org.escalate42.javaz.trym.TryM;

import java.util.concurrent.ExecutorService;

import static org.escalate42.javaz.option.OptionImpl.*;
import static org.escalate42.javaz.trym.TryMImpl.*;

/**
 * Created by vdubs
 * on 1/27/15.
 */
public class FutureImpl<T> implements Future<T> {

    private final ExecutorService executorService;
    private volatile boolean isCompleted;
    private volatile Option<TryM<T>> result = none();

    public FutureImpl(final ExecutorService executorService) {
        this.executorService = executorService;
        this.isCompleted = false;
    }

    public FutureImpl(final ThrowableClosure<T> closure, final ExecutorService executorService) {
        this.executorService = executorService;
        this.isCompleted = false;
    }

    public FutureImpl(T value, ExecutorService executorService) {
        this.executorService = executorService;
        this.isCompleted = true;
        this.result = some(success(value));
    }

    public FutureImpl(Throwable error, ExecutorService executorService) {
        this.executorService = executorService;
        this.isCompleted = true;
        this.result = some(fail(error));
    }

    @Override
    public void onSuccess(Applicable<T> onSuccess) {
        onComplete(new Applicable<TryM<T>>() {
            @Override public void apply(TryM<T> tTryM) {
                tTryM.foreach(onSuccess);
            }
        });
    }

    @Override
    public void onFailure(Applicable<Throwable> onFailure) {
        onComplete(new Applicable<TryM<T>>() {
            @Override public void apply(TryM<T> tTryM) {
                if (tTryM.isFailure()) {
                    onFailure.apply(tTryM.throwable());
                }
            }
        });
    }

    @Override
    public void onComplete(Applicable<TryM<T>> onComplete) {
    }

    @Override
    public boolean isCompleted() {
        return this.isCompleted;
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
    public <U, MM extends Monad<U, Future<?>>> Future<?> flatMap(Function<T, MM> function) {
        return null;
    }

    @Override
    public <U> Future<U> pure(U value) {
        return new FutureImpl<>(value, this.executorService);
    }

    @Override
    public <U, MM extends Applicative<Function<T, U>, Future<?>>> Future<?> amap(MM applicativeFunction) {
        return null;
    }

    @Override
    public <U> Future<U> map(Function<T, U> function) {
        return null;
    }
}
