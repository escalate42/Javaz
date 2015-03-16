package org.escalate42.javaz.common.monoid;

/**
 * Created by vadimvd
 * on 05.11.2014 20:43.
 */
public interface MonoidOps<T, M extends Monoid<?, M>> {
    M mempty();
    Monoid<T, M> wrap(T t);
}
