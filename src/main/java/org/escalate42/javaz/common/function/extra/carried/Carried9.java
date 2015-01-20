package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.extra.*;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carried9<A1, A2, A3, A4, A5, A6, A7, A8, A9, B> implements Function8<A2, A3, A4, A5, A6, A7, A8, A9, B> {
    private final A1 a1;
    private final Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, B> function9;
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, B> Function8<A2, A3, A4, A5, A6, A7, A8, A9, B> carry(A1 a1, Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, B> function9) {
        return new Carried9<>(a1, function9);
    }
    private Carried9(A1 a1, Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, B> function9) {
        this.a1 = a1;
        this.function9 = function9;
    }
    @Override public B apply(A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9) {
        return this.function9.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9);
    }
}
