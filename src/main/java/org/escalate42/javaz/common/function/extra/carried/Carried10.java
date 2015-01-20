package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.extra.*;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carried10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> implements Function9<A2, A3, A4, A5, A6, A7, A8, A9, A10, B> {
    private final A1 a1;
    private final Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> function10;
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> Function9<A2, A3, A4, A5, A6, A7, A8, A9, A10, B> carry(A1 a1, Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> function10) {
        return new Carried10<>(a1, function10);
    }
    private Carried10(A1 a1, Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> function10) {
        this.a1 = a1;
        this.function10 = function10;
    }
    @Override public B apply(A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10) {
        return this.function10.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10);
    }
}
