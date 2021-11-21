/*
** © Bart Kampers
*/

package bka.graph.utils;

import bka.graph.*;
import java.util.*;
import java.util.function.*;


public final class EdgeUtil {

    private EdgeUtil() {
    }

    public static <V, E extends Edge<V>> V getAdjacentVertex(E edge, V vertex) {
        if (!isIncidentWith(edge, vertex)) {
            throw new NoSuchElementException("Edge " + edge + " is not incident with vertex " + vertex);
        }
        return edge.getVertices().stream().filter(v -> !vertex.equals(v)).findFirst().orElse(vertex);
    }

    public static <V, E extends Edge<V>> Predicate<E> isIncidentWith(V v) {
        return e -> isIncidentWith(e, v);
    }

    public static <V, E extends Edge<V>> boolean isIncidentWith(E edge, V vertex) {
        return edge.getVertices().contains(vertex);
    }

    public static <V, E extends Edge<V>> boolean isLoop(E edge) {
        return edge.getVertices().stream().distinct().count() == 1;
    }

    public static <V, E extends DirectedEdge<V>> Predicate<E> from(V origin) {
        return edge -> origin.equals(edge.getOrigin());
    }

    public static <V, E extends DirectedEdge<V>> Predicate<E> to(V terminus) {
        return edge -> terminus.equals(edge.getTerminus());
    }

}
