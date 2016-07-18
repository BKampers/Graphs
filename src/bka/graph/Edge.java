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
    
    
    public void init(Vertex origin, Vertex terminus) {
        this.origin = origin;
        this.terminus = terminus;
    }
    
    
    public Vertex getOrigin() {
        return origin;
    }
    
    
    public void setOrigin(Vertex vertex) {
        origin = vertex;
    }
    
    
    public Vertex getTerminus() {
        return terminus;
    }
    
    
    public void setTerminus(Vertex vertex) {
        terminus = vertex;
    }
    
    
    public boolean isDirected() {
        return directed;
    }
    
    
    public void setDirected(boolean directed) {
        this.directed = directed;
    }
    
    
    private Vertex origin;
    private Vertex terminus;
    private boolean directed;
    
}
