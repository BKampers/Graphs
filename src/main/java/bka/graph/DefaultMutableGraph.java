/*
** © Bart Kampers
*/

package bka.graph;

import java.util.*;

public class DefaultMutableGraph<V> extends MutableGraph<V, Edge<V>> implements Graph<V> {

    public DefaultMutableGraph() {
        super();
    }

    public DefaultMutableGraph(GraphBase<V, ? extends Edge<V>> graph) {
        super(graph);
    }

    public DefaultMutableGraph(Collection<V> vertices, Collection<? extends Edge<V>> edges) {
        super(vertices, edges);
    }

    public DefaultMutableGraph(Collection<Edge<V>> edges) {
        super(edges);
    }

}
