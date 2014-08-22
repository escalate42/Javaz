package org.escalate42.javaz.common.function;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public class FOps {
    public static <A, B> TF<A, B> asTf(final F<A, B> f) {
        return new TF<A, B>() { @Override public B apply(A a) throws Throwable { return f.apply(a); } };
    }
}
