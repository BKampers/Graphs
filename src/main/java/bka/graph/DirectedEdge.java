/*
** © Bart Kampers
*/

package bka.graph;

public class DirectedEdge<V> extends AbstractEdge<V> {
    
    public DirectedEdge(V origin, V terminus) {
        super(origin, terminus);
    }

    public DirectedEdge(DirectedEdge<V> other) {
        super(other.getOrigin(), other.getTerminus());
    }

    public V getOrigin() {
        return vertices().get(0);
    }

    public V getTerminus() {
        return vertices().get(1);
    }

    @Override
    public final boolean isDirected() {
        return true;
    }

    @Override
    protected String stringPattern() {
        return "(%s,%s)";
    }

}
