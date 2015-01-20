package org.escalate42.javaz.common.function.extra;

import org.escalate42.javaz.common.function.extra.carried.Carry;

/**
 * Created by vdubs
 * on 10/20/14.
 */
public interface Function4<A1, A2, A3, A4, B> {
    public B apply(A1 a1, A2 a2, A3 a3, A4 a4);
    public default Function3<A2, A3, A4, B> carry(A1 a1) {
        return Carry.carry(a1, this);
    }
}
