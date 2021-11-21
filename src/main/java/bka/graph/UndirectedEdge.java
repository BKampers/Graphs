/*
** © Bart Kampers
*/

package bka.graph;


public class UndirectedEdge<V> extends AbstractEdge<V> {

    public UndirectedEdge(V vertex1, V vertex2) {
        super(vertex1, vertex2);
    }

    public UndirectedEdge(Edge<V> other) {
        super(other);
    }

    @Override
    public final boolean isDirected() {
        return false;
    }

}
