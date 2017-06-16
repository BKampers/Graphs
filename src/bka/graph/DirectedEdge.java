/*
** Copyright © Bart Kampers
*/

package bka.graph;


public class DirectedEdge extends Edge {


    public DirectedEdge() {
    }


    public DirectedEdge(Vertex origin, Vertex terminus) {
        super(origin, terminus);
    }


    @Override
    public boolean isDirected() {
        return true;
    }

    
}
