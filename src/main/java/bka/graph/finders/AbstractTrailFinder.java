/*
** © Bart Kampers
*/

package bka.graph.finders;

import bka.graph.*;
import bka.graph.utils.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;


public abstract class AbstractTrailFinder<V, E extends Edge<V>> implements TrailFinder<V, E> {

    protected Collection<E> complement(Collection<E> edges, E edge) {
        return edges.stream().filter(Predicate.not(Predicate.isEqual(edge))).collect(Collectors.toList());
    }

    protected Collection<E> complement(Collection<E> edges, V vertex) {
        return edges.stream().filter(Predicate.not(EdgeUtil.isIncidentWith(vertex))).collect(Collectors.toList());
    }

    protected static <T> boolean pass(Predicate<T> predicate, T argument) {
        return predicate == null || predicate.test(argument);
    }

}
