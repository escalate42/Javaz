package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.extra.*;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carried7<A1, A2, A3, A4, A5, A6, A7, B> implements Function6<A2, A3, A4, A5, A6, A7, B> {
    private final A1 a1;
    private final Function7<A1, A2, A3, A4, A5, A6, A7, B> function7;
    public static <A1, A2, A3, A4, A5, A6, A7, B> Function6<A2, A3, A4, A5, A6, A7, B> carry(A1 a1, Function7<A1, A2, A3, A4, A5, A6, A7, B> function7) {
        return new Carried7<>(a1, function7);
    }
    private Carried7(A1 a1, Function7<A1, A2, A3, A4, A5, A6, A7, B> function7) {
        this.a1 = a1;
        this.function7 = function7;
    }
    @Override public B apply(A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7) {
        return this.function7.apply(a1, a2, a3, a4, a5, a6, a7);
    }
}
