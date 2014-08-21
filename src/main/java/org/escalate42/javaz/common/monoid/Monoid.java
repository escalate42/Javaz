package org.escalate42.javaz.common.monoid;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface Monoid<T, M extends Monoid<?, M>> {
    public M mzero();
    public <MM extends Monoid<T, M>> M mappend(MM another);
}
