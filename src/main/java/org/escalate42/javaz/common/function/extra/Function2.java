package org.escalate42.javaz.common.function.extra;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.extra.carried.Carry;

/**
 * Created by vdubs
 * on 10/20/14.
 */
public interface Function2<A1, A2, B> {
    public B apply(A1 a1, A2 a2);
    public default Function<A2, B> carry(A1 a1) {
        return Carry.carry(a1, this);
    }
}
