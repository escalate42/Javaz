package org.escalate42.javaz.common.function.extra;

import org.escalate42.javaz.common.function.extra.carried.Carry;

/**
 * Created by vadimvd
 * on 21.10.2014 22:10.
 */
public interface Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> {
    B apply(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10);
    default Function9<A2, A3, A4, A5, A6, A7, A8, A9, A10, B> carry(A1 a1) {
        return Carry.carry(a1, this);
    }
}
