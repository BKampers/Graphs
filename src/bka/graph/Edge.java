/*
** Copyright © Bart Kampers
*/

package bka.graph;


public class Edge {

    
    public Edge() {
    }
    
    
    public Edge(Vertex origin, Vertex terminus, boolean directed) {
        this.origin = origin;
        this.terminus = terminus;
        this.directed = directed;
    }
    
    
    public final void init(Vertex origin, Vertex terminus) {
        this.origin = origin;
        this.terminus = terminus;
    }
    
    
    public final Vertex getOrigin() {
        return origin;
    }
    
    
    public final void setOrigin(Vertex vertex) {
        origin = vertex;
    }
    
    
    public final Vertex getTerminus() {
        return terminus;
    }
    
    
    public final void setTerminus(Vertex vertex) {
        terminus = vertex;
    }
    
    
    public final boolean isDirected() {
        return directed;
    }
    
    
    public final void setDirected(boolean directed) {
        this.directed = directed;
    }
    
    
    private Vertex origin;
    private Vertex terminus;
    private boolean directed;
    
}
