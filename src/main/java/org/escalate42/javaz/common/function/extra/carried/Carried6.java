package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.extra.*;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carried6<A1, A2, A3, A4, A5, A6, B> implements Function5<A2, A3, A4, A5, A6, B> {
    private final A1 a1;
    private final Function6<A1, A2, A3, A4, A5, A6, B> function6;
    public static <A1, A2, A3, A4, A5, A6, B> Function5<A2, A3, A4, A5, A6, B> carry(A1 a1, Function6<A1, A2, A3, A4, A5, A6, B> function6) {
        return new Carried6<>(a1, function6);
    }
    private Carried6(A1 a1, Function6<A1, A2, A3, A4, A5, A6, B> function6) {
        this.a1 = a1;
        this.function6 = function6;
    }
    @Override public B apply(A2 a2, A3 a3, A4 a4, A5 a5, A6 a6) {
        return this.function6.apply(a1, a2, a3, a4, a5, a6);
    }
}