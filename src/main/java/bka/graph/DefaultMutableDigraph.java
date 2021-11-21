/*
** © Bart Kampers
*/

package bka.graph;

import java.util.*;

public class DefaultMutableDigraph<V> extends MutableGraph<V, DirectedEdge<V>> implements Digraph<V> {

    public DefaultMutableDigraph() {
        super();
    }

    public DefaultMutableDigraph(GraphBase<V, DirectedEdge<V>> graph) {
        super(graph);
    }

    public DefaultMutableDigraph(Collection<V> vertices, Collection<? extends DirectedEdge<V>> edges) {
        super(vertices, edges);
    }

    public DefaultMutableDigraph(Collection<DirectedEdge<V>> edges) {
        super(edges);
    }

}
