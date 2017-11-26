/*
** Copyright © Bart Kampers
*/

package bka.graph;

import java.util.*;



public class UniqueDirectedEdge<V extends Vertex> extends DirectedEdge<V> {


    public UniqueDirectedEdge() {
    }


    public UniqueDirectedEdge(V origin, V terminus) {
        super(origin, terminus);
    }


    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || other.getClass() != UniqueDirectedEdge.class) {
            return false;
        }
        UniqueDirectedEdge edge = (UniqueDirectedEdge) other;
        return edge.getOrigin() == getOrigin() && edge.getTerminus() == getTerminus();
    }


    @Override
    public int hashCode() {
        return Objects.hash(getOrigin(), getTerminus());
    }

}

