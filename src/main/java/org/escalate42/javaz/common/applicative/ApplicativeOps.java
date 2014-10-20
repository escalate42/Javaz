package org.escalate42.javaz.common.applicative;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.functor.FunctorOps;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface ApplicativeOps<M extends Applicative<?, M>> extends FunctorOps<M> {
    public <U> M pure(U value);
    public <T, U, MM extends Applicative<T, M>, MF extends Applicative<Function<T, U>, M>> M amap(MM app, MF appF);
}
