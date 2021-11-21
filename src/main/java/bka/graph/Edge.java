/*
** © Bart Kampers
*/

package bka.graph;

import java.util.*;


public interface Edge<V> {

    Collection<V> getVertices();
    boolean isDirected();

}
