package org.escalate42.javaz.common.functor;

import org.escalate42.javaz.common.function.Function;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface FunctorOps<M extends Functor<?, M>> {
    public <T, U, MM extends Functor<T, M>> M fmap(MM functor, Function<T, U> function);
    public <T, MM extends Functor<T, M>> void foreach(MM functor, Function<T, Void> function);
}
