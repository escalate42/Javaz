package org.escalate42.javaz.common.function;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public class FunctionOps {
    public static <A, B> ThrowableFunction<A, B> asTf(final Function<A, B> function) {
        return function::apply;
    }
    public static <B> ThrowableClosure<B> asTc(final Closure<B> function) {
        return function::apply;
    }
    public static <A> ThrowableApplicable<A> asTa(final Applicable<A> function) {
        return function::apply;
    }
}
