/*
** Copyright © Bart Kampers
*/

package bka.graph;


public class Edge {

    
    public Edge() {
    }


    public Edge(Vertex origin, Vertex terminus) {
        this.origin = origin;
        this.terminus = terminus;
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
    
    
    public boolean isDirected() {
        return false;
    }
    
    
    private Vertex origin;
    private Vertex terminus;
    
}
