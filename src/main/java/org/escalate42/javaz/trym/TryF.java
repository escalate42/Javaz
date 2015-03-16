package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.ThrowableFunction;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public final class TryF<A, B> implements Function<A, TryM<B>> {

    public final ThrowableFunction<A, B> function;

    public static <AA, BB> TryF<AA, BB> tryF(ThrowableFunction<AA, BB> function) { return new TryF<>(function); }

    public TryF(ThrowableFunction<A, B> function) {
        if (function == null) { throw new IllegalArgumentException("Function for TryF<A, B> can not be null!"); }
        this.function = function;
    }

    @Override
    public TryM<B> apply(A a) { return TryMOps.tryM(this.function, a); }
}
