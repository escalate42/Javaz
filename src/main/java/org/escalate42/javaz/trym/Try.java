package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.TryFunction;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public final class Try<A, B> implements Function<A, TryM<B>> {

    public final TryFunction<A, B> function;

    public static <AA, BB> Try<AA, BB> tryF(TryFunction<AA, BB> function) { return new Try<>(function); }

    public Try(TryFunction<A, B> function) {
        if (function == null) { throw new IllegalArgumentException("Function for Try<A, B> can not be null!"); }
        this.function = function;
    }

    @Override
    public TryM<B> apply(A a) { return TryMImpl.tryM(function, a); }
}
