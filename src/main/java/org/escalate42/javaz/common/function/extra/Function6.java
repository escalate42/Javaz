package org.escalate42.javaz.common.function.extra;

/**
 * Created by vadimvd
 * on 21.10.2014 22:02.
 */
public abstract class Function6<A1, A2, A3, A4, A5, A6, B> {
    public abstract B apply6(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6);
    public Function5<A2, A3, A4, A5, A6, B> carry(A1 a1) {
        return new Carried(a1);
    }
    private class Carried extends Function5<A2, A3, A4, A5, A6, B> {
        private final A1 a1;
        private Carried(A1 a1) {
            this.a1 = a1;
        }
        @Override public B apply5(A2 a2, A3 a3, A4 a4, A5 a5, A6 a6) {
            return apply6(a1, a2, a3, a4, a5, a6);
        }
    }
}