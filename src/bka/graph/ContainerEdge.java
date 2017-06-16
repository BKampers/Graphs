/*
** Copyright © Bart Kampers
*/


package bka.graph;


public class ContainerEdge extends DirectedEdge {
    
    
    public ContainerEdge(Vertex container, Vertex contained) {
        super(container, contained);
    }
    

    @Override
    public boolean equals(Object other) {
        return 
            other instanceof ContainerEdge && 
            ((ContainerEdge) other).getOrigin() == getOrigin() && 
            ((ContainerEdge) other).getTerminus() == getTerminus();
    } 


    // <editor-fold defaultstate="collapsed" desc="Generated hashCode">
    @Override
    public int hashCode() {
        return 7;
    }
    // </editor-fold>
    
}
