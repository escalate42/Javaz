package org.escalate42.javaz.common.function.extra;

import org.escalate42.javaz.common.function.extra.carried.Carry;

/**
 * Created by vadimvd
 * on 21.10.2014 22:07.
 */
public interface Function8<A1, A2, A3, A4, A5, A6, A7, A8, B> {
    abstract B apply(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8);
    default Function7<A2, A3, A4, A5, A6, A7, A8, B> carry(A1 a1) {
        return Carry.carry(a1, this);
    }
}
