/*
** Copyright © Bart Kampers
*/

package bka.graph;


public class DefaultEdge extends Edge {

    
    @Override
    public boolean isDirected() {
        return directed;
    }


    public void setDirected(boolean directed) {
        this.directed = directed;
    }


    private boolean directed;
    
}
