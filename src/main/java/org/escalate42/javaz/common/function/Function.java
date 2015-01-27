package org.escalate42.javaz.common.function;

import org.escalate42.javaz.common.function.extra.carried.Carry;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public interface Function<A, B> {
    public B apply(A a);
    public default Closure<B> carry(A a) { return Carry.carry(a, this); }
}
