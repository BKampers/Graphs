/*
** © Bart Kampers
*/

package bka.graph;

import java.util.*;


public class UniqueUndirectedEdge<V> extends UndirectedEdge<V> {

    public UniqueUndirectedEdge(V vertex1, V vertex2) {
        super(vertex1, vertex2);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || !getClass().equals(object.getClass())) {
            return false;
        }
        Collection<?> thisVertices = getVertices();
        Collection<?> otherVertices = ((UniqueUndirectedEdge) object).getVertices();
        return otherVertices.containsAll(thisVertices) && thisVertices.containsAll(otherVertices);
    }

    @Override
    public int hashCode() {
        return getVertices().hashCode();
    }

}
