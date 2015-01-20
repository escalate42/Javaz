package org.escalate42.javaz.common.function;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public class FunctionOps {
    public static <A, B> TryFunction<A, B> asTf(final Function<A, B> function) {
        return function::apply;
    }
}
