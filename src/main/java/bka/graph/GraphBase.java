/*
** © Bart Kampers
*/

package bka.graph;

import java.util.*;


public interface GraphBase<V, E extends Edge<V>> {

    Set<V> getVertices();
    Set<E> getEdges();

}
