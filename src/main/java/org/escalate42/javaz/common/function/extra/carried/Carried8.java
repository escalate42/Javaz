package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.extra.*;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carried8<A1, A2, A3, A4, A5, A6, A7, A8, B> implements Function7<A2, A3, A4, A5, A6, A7, A8, B> {
    private final A1 a1;
    private final Function8<A1, A2, A3, A4, A5, A6, A7, A8, B> function8;
    public static <A1, A2, A3, A4, A5, A6, A7, A8, B> Function7<A2, A3, A4, A5, A6, A7, A8, B> carry(A1 a1, Function8<A1, A2, A3, A4, A5, A6, A7, A8, B> function8) {
        return new Carried8<>(a1, function8);
    }
    private Carried8(A1 a1, Function8<A1, A2, A3, A4, A5, A6, A7, A8, B> function8) {
        this.a1 = a1;
        this.function8 = function8;
    }
    @Override public B apply(A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8) {
        return this.function8.apply(a1, a2, a3, a4, a5, a6, a7, a8);
    }
}