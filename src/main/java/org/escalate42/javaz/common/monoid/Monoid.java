package org.escalate42.javaz.common.monoid;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface Monoid<T, M extends Monoid<?, M>> {
    public M mappend(Monoid<T, M> another);
    public T value();
    public MonoidOps<T, M> ops();
}
