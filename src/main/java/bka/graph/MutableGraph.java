/*
** © Bart Kampers
*/

package bka.graph;

import java.util.*;


public class MutableGraph<V, E extends Edge<V>> implements GraphBase<V, E> {

    public MutableGraph() {
    }

    public MutableGraph(GraphBase<V, ? extends E> graph) {
        this(graph.getVertices(), graph.getEdges());
    }

    public MutableGraph(Collection<V> vertices, Collection<? extends E> edges) {
        this(edges);
        this.vertices.addAll(vertices);
    }

    public MutableGraph(Collection<? extends E> edges) {
        addEdges(edges);
    }

    public final void clear() {
        edges.clear();
        vertices.clear();
    }

    public final boolean addVertices(Collection<V> vertices) {
        return this.vertices.addAll(vertices);
    }

    public final boolean addVertex(V vertex) {
        return vertices.add(vertex);
    }

    public final boolean addEdges(Collection<? extends E> edges) {
        boolean added = false;
        for (E edge : edges) {
            added |= addEdge(edge);
        }
        return added;
    }

    public final boolean addEdge(E edge) {
        vertices.addAll(edge.getVertices());
        return edges.add(edge);
    }

    public final boolean removeVertices(Collection<V> vertices) {
        boolean removed = false;
        for (V vertex : vertices) {
            removed |= removeVertex(vertex);
        }
        return removed;
    }

    public final boolean removeVertex(V vertex) {
        boolean removed = vertices.remove(vertex);
        if (removed) {
            edges.removeIf(edge -> edge.getVertices().contains(vertex));
        }
        return removed;
    }

    public final boolean removeEdges(Collection<E> edges) {
        return this.edges.removeAll(edges);
    }

    public final boolean removeEdge(E edge) {
        return edges.remove(edge);
    }

    @Override
    public final Set<V> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public final Set<E> getEdges() {
        return Collections.unmodifiableSet(edges);
    }

    private final Set<V> vertices = new HashSet<>();
    private final Set<E> edges = new HashSet<>();

}
