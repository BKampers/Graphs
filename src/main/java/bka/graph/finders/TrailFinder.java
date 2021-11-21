/*
** © Bart Kampers
*/

package bka.graph.finders;

import bka.graph.*;
import java.util.*;
import java.util.function.*;


public interface TrailFinder<V, E extends Edge<V>> {

    /**
     * Find all trails or paths in given graph, from the given start edge to the given end edge.
     *
     * @param graph as a collection of edges
     * @param start
     * @param end
     * @param revisitVertices true if all trails may be included in the resolution, false if only paths may be included.
     * @return found resolutions
     */
    Collection<List<E>> find(Collection<E> graph, V start, V end, boolean revisitVertices);

    /**
     * Set condition incomplete trails have to meet in order to continue exploring
     *
     * @param restriction, if null no restrictions apply
     */
    void setRestriction(Predicate<List<E>> restriction);

    /**
     * Set conditon trails have to meet in order to be included in the collection of resolutions
     *
     * @param filter, if null no filters apply
     */
    void setFilter(Predicate<List<E>> filter);

}
