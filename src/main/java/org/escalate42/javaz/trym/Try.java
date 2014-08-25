package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.F;
import org.escalate42.javaz.common.function.TF;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public final class Try<A, B> implements F<A, TryM<B>> {

    public final TF<A, B> function;

    public static <AA, BB> Try<AA, BB> tryF(TF<AA, BB> function) { return new Try<AA, BB>(function); }

    public Try(TF<A, B> function) {
        if (function == null) { throw new IllegalArgumentException("Function for Try<A, B> can not be null!"); }
        this.function = function;
    }

    @Override
    public TryM<B> apply(A a) { return TryM.tryM(function, a); }
}
