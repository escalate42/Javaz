package org.escalate42.javaz.common.function;

import org.escalate42.javaz.common.function.extra.carried.Carry;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public interface Function<A, B> {
    B apply(A a);
    default Closure<B> carry(A a) { return Carry.carry(a, this); }
    default Applicable<A> ommit() { return this::apply; }
}
