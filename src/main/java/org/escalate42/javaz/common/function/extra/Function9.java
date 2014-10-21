package org.escalate42.javaz.common.function.extra;

/**
 * Created by vadimvd
 * on 21.10.2014 22:09.
 */
public abstract class Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, B> {
    public abstract B apply9(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9);
    public Function8<A2, A3, A4, A5, A6, A7, A8, A9, B> carry(A1 a1) {
        return new Carried(a1);
    }
    private class Carried extends Function8<A2, A3, A4, A5, A6, A7, A8, A9, B> {
        private final A1 a1;
        private Carried(A1 a1) {
            this.a1 = a1;
        }
        @Override public B apply8(A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9) {
            return apply9(a1, a2, a3, a4, a5, a6, a7, a8, a9);
        }
    }
}
