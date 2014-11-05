package org.escalate42.javaz.common.monoid;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface Monoid<T, M extends Monoid<?, M>> {
    public M mappend(M another);
    public T value();
}
