/*
** Copyright © Bart Kampers
*/

package bka.graph;


public class Edge<V extends Vertex> {

    
    public Edge() {
    }


    public Edge(V origin, V terminus) {
        this.origin = origin;
        this.terminus = terminus;
    }
    
    
    public final void init(V origin, V terminus) {
        this.origin = origin;
        this.terminus = terminus;
    }


    public <U extends Vertex> U getVertex() {
        return (U) origin;
    }
    
    
    public final V getOrigin() {
        return origin;
    }
    
    
    public final void setOrigin(V vertex) {
        origin = vertex;
    }
    
    
    public final V getTerminus() {
        return terminus;
    }
    
    
    public final void setTerminus(V vertex) {
        terminus = vertex;
    }
    
    
    public boolean isDirected() {
        return false;
    }
    
    
    private V origin;
    private V terminus;
    
}
