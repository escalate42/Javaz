package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.extra.*;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carried4<A1, A2, A3, A4, B> implements Function3<A2, A3, A4, B> {
    private final A1 a1;
    private final Function4<A1, A2, A3, A4, B> function4;
    public static <A1, A2, A3, A4, B> Function3<A2, A3, A4, B> carry(A1 a1, Function4<A1, A2, A3, A4, B> function4) {
        return new Carried4<>(a1, function4);
    }
    private Carried4(A1 a1, Function4<A1, A2, A3, A4, B> function4) {
        this.a1 = a1;
        this.function4 = function4;
    }
    @Override public B apply(A2 a2, A3 a3, A4 a4) {
        return this.function4.apply(a1, a2, a3, a4);
    }
}