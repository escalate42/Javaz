package org.escalate42.javaz.common.function.extra;

/**
 * Created by vadimvd
 * on 21.10.2014 22:10.
 */
public abstract class Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> {
    public abstract B apply10(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10);
    public Function9<A2, A3, A4, A5, A6, A7, A8, A9, A10, B> carry(A1 a1) {
        return new Carried(a1);
    }
    private class Carried extends Function9<A2, A3, A4, A5, A6, A7, A8, A9, A10, B> {
        private final A1 a1;
        private Carried(A1 a1) {
            this.a1 = a1;
        }
        @Override public B apply9(A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10) {
            return apply10(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10);
        }
    }
}
