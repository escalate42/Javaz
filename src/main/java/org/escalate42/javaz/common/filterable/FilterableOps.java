package org.escalate42.javaz.common.filterable;

import org.escalate42.javaz.common.function.Function;

/**
 * Created by vadimvd
 * on 05.11.2014 19:44.
 */
public interface FilterableOps<M extends Filterable<?, M>> {
    public <T, MM extends Filterable<T, M>> M filter(MM filterable, Function<T, Boolean> function);
}
