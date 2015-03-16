package org.escalate42.javaz.option;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.monad.Monad;

import java.io.Serializable;

import static org.escalate42.javaz.option.OptionOps.*;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public abstract class OptionImpl<T> implements Serializable, Option<T> {

    private static final long serialVersionUID = 0;

    @Override
    public abstract <U> Option<U> map(Function<T, U> function);

    @Override
    public abstract Option<T> filter(Function<T, Boolean> predicate);

    @Override
    public <U, MM extends Applicative<Function<T, U>, Option<?>>> Option<U> amap(MM applicativeFunction) {
        //noinspection unchecked
        return isDefined() ? (Option<U>)applicativeFunction.map(new Function<Function<T,U>, U>() {
            @Override public U apply(Function<T, U> tuFunction) {
                return tuFunction.apply(get());
            }
        }) : none();
    }

    @Override
    public <U, MM extends Monad<U, Option<?>>> Option<U> flatMap(Function<T, MM> function) {
        //noinspection unchecked
        return isDefined() ? (Option<U>)function.apply(get()) : none();
    }

    public abstract boolean isDefined();

    public abstract T get();

    public abstract T orNull();

    public abstract T orElse(T elseValue);

    public abstract Option<T> or(Option<T> elseValue);

    public abstract <U> U fold(U ifNone, Function<T, U> function);
}
