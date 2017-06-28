/*
** Copyright © Bart Kampers
*/

package bka.graph;


public class DirectedEdge<V extends Vertex> extends Edge<V> {


    public DirectedEdge() {
    }


    public DirectedEdge(V origin, V terminus) {
        super(origin, terminus);
    }


    @Override
    public boolean isDirected() {
        return true;
    }

    
}
