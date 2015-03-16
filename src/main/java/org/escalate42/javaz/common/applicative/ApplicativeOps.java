package org.escalate42.javaz.common.applicative;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface ApplicativeOps<M extends Applicative<?, M>> {
    <U> M pure(U value);
}
