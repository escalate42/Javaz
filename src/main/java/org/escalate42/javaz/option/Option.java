package org.escalate42.javaz.option;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;

/**
 * Created by vdubs
 * on 10/22/14.
 */
public interface Option<T> extends Monad<T, Option<?>> {
    public <U> Option<U> fmap(Function<T, U> function);
    public <U> Option<U> pure(U value);
    public <U, MM extends Applicative<Function<T, U>, Option<?>>> Option<U> amap(MM applicativeFunction);
    public <U, MM extends Monad<U, Option<?>>> Option<U> mmap(Function<T, MM> function);
    public boolean isDefined();
    public T get();
    public T orNull();
    public T orElse(T elseValue);
    public Option<T> or(Option<T> elseValue);
    public <U> U fold(U ifNone, Function<T, U> function);
}
