package org.escalate42.javaz.common.functor;

import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface FunctorOps<M extends Functor<?, M>> {
    public <T, U, MM extends Functor<T, M>> M map(MM functor, Function<T, U> function);
    public <T, MM extends Functor<T, M>> void foreach(MM functor, Applicable<T> function);
}
