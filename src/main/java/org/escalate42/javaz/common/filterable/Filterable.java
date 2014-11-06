package org.escalate42.javaz.common.filterable;

import org.escalate42.javaz.common.function.Function;

/**
 * Created by vadimvd
 * on 05.11.2014 19:44.
 */
public interface Filterable<T, M extends Filterable<?, M>> {
    public M filter(Function<T, Boolean> function);
}
