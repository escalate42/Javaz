package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.Closure;
import org.escalate42.javaz.common.function.ThrowableClosure;

/**
 * Created by vdubs
 * on 1/22/15.
 */
public class TryC<T> implements Closure<TryM<T>> {

    public final ThrowableClosure<T> closure;

    public static <T> TryC<T> tryC(ThrowableClosure<T> closure) { return new TryC<>(closure); }

    public TryC(ThrowableClosure<T> closure) {
        if (closure == null) { throw new IllegalArgumentException("Closure for TryC<T> can not be null!"); }
        this.closure = closure;
    }

    @Override
    public TryM<T> apply() { return TryMOps.tryM(this.closure); }
}

