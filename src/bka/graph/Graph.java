/*
** Copyright © Bart Kampers
*/


package bka.graph;


import java.util.*;
import java.util.function.*;


/**
 * A set V of vertices together with a set E of edges, which are 2-element subsets of V.
 * This means  an edge is associated with two vertices.
 * Edges can eiher be oredered (directed) or unordered (undirected.
 * @param <V> vertices
 * @param <E> edges
 */
public class Graph<V extends Vertex, E extends Edge<V>> {


    public Graph() {
    }


    public Graph(Collection<E> edges) {
        addEdges(edges);
    }
    
    
    public Graph(Collection<V> vertices, Collection<E> edges) {
        addEdges(edges);
        addVertices(vertices);
    }


    public final void addVertices(Collection<V> vertices) {
        for (V vertex : vertices) {
            add(vertex);
        }
    }


    public final void addEdges(Collection<E> edges) {
        for (E edge : edges) {
            add(edge);
        }
    }


    public final boolean add(V vertex) {
        return vertices.add(Objects.requireNonNull(vertex));
    }


    public final boolean add(E edge) {
        boolean added = edges.add(Objects.requireNonNull(edge));
        if (added) {
            add(edge.getOrigin());
            add(edge.getTerminus());
        }
        return added;
    }


    public void add(Graph graph) {
        addEdges(graph.edges);
        addVertices(graph.vertices);
    }
    
    
    public boolean remove(V vertex) {
        boolean removed = vertices.remove(vertex);
        if (removed) {
            Iterator<E> it = edges.iterator();
            while (it.hasNext()) {
                E edge = it.next();
                if (vertex.equals(edge.getOrigin()) || vertex.equals(edge.getTerminus())) {
                    it.remove();
                }
            }
        }
        return removed;
    }


    public boolean remove(E edge) {
        return edges.remove(edge);
    }


    public boolean isEmpty() {
        return vertices.isEmpty();
    }


    public Set<V> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }
    
    
    public Set<E> getEdges() {
        return Collections.unmodifiableSet(edges);
    }
    
    
    public boolean contains(V vertex) {
        return vertices.contains(vertex);
    }
    
    
    public boolean contains(E edge) {
        return edges.contains(edge);
    }
    
    
    public boolean isDirected() {
        for (E edge : edges) {
            if (! edge.isDirected()) {
                return false;
            }
        }
        return true;
    }
    
    
    public boolean isUndirected() {
        for (E edge : edges) {
            if (edge.isDirected()) {
                return false;
            }
        }
        return true;
    }
    
    
    public boolean isMixed() {
        return ! (isDirected() || isUndirected());
    }


    public boolean isLeaf(V vertex) {
        return allDirectedEdgesFrom(vertex).isEmpty();
    }


    public Set<E> allDirectedEdgesFrom(V vertex) {
        return allEdges((E edge) -> edge.isDirected() && edge.getOrigin() == vertex);
    }
    
    
    public Set<E> allDirectedEdgesTo(V vertex) {
        return allEdges((E edge) -> edge.isDirected() && edge.getTerminus() == vertex);
    }


    public Set<E> allDirectedEdgesTo(V vertex, Class<? extends DirectedEdge> edgeClass) {
        return allEdges((E edge) -> edge.getClass() == edgeClass);
    }


    public Set<E> allUndirectedEdgesFrom(V vertex) {
        return allEdges((E edge) -> ! edge.isDirected() && (edge.getOrigin() == vertex || edge.getTerminus() == vertex));
    }
    
    
    public Set<V> allVertices(Predicate<V> predicate) {
        Set<V> all = new HashSet<>();
        for (V vertex : vertices) {
            if (predicate.test(vertex)) {
                all.add(vertex);
            }
        }
        return all;
    }


    public Set<E> allEdges(Predicate<E> predicate) {
        Set<E> all = new HashSet<>();
        for (E edge : edges) {
            if (predicate.test(edge)) {
                all.add(edge);
            }
        }
        return all;
    }


    public Graph<V, E> directedGraphFrom(V seed) {
        Graph<V, E> graph = new Graph<>();
        findDirectedGraphFrom(seed, graph);
        return graph;
    }


    public List<V> directedWalk(V start, V end) {
        List<V> walk = new ArrayList<>();
        findDirectedWalk(walk, start, end);
        return walk;
    }


     private void findDirectedWalk(List<V> walk, V start, V end) {
        if (! walk.contains(start)) {
            walk.add(start);
            boolean found = start == end;
            if (! found) {
                Iterator<E> leavingEdges = allDirectedEdgesFrom(start).iterator();
                while (! found && leavingEdges.hasNext()) {
                    E nextEdge = leavingEdges.next();
                    findDirectedWalk(walk, nextEdge.getTerminus(), end);
                    found = walk.contains(end);
                }
            }
            if (! found) {
                walk.remove(start);
            }
        }
    }


    private void findDirectedGraphFrom(V seed, Graph<V, E> graph) {
        for (E edge : allDirectedEdgesFrom(seed)) {
            V terminus = edge.getTerminus();
            boolean searchTerminus = ! graph.contains(terminus);
            graph.add(edge);
            if (searchTerminus) {
                findDirectedGraphFrom(terminus, graph);
            }
        }
    }


    private final Set<V> vertices = new HashSet<>();
    private final Set<E> edges = new HashSet<>();

}
