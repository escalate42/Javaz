package org.escalate42.javaz.common.applicative;

import org.escalate42.javaz.common.function.F;
import org.escalate42.javaz.common.functor.Functor;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface Applicative<T, M extends Applicative<?, M>> extends Functor<T, M> {
    public <U> M pure(U value);
    public <U, MM extends Applicative<F<T, U>, M>> M amap(MM applicativeFunction);
    // TODO: implement (*>) and (<*)
}
