/*
** Copyright © Bart Kampers
*/


package bka.graph;


import java.util.*;


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
    

    public void add(Graph graph) {
        addEdges(graph.edges);
        addVertices(graph.vertices);
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
    
    
    public void add(V vertex) {
        if (! vertices.contains(vertex)) {
            vertices.add(vertex);
        }
    }
    
    
    public void add(E edge) {
        if (! edges.contains(edge)) {
            edges.add(edge);
            add(edge.getOrigin());
            add(edge.getTerminus());
        }
    }
    
    
    public Collection<V> getVertices() {
        return new ArrayList<>(vertices);
    }
    
    
    public Collection<E> getEdges() {
        return new ArrayList<>(edges);
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
    
    
    public Collection<E> allDirectedEdgesFrom(V vertex) {
        Collection<E> collection = new ArrayList<>();
        for (E edge : edges) {
            if (edge.isDirected() && edge.getOrigin() == vertex) {
                collection.add(edge);
            }
        }
        return collection;
    }
    
    
    public Collection<E> allDirectedEdgesTo(V vertex) {
        Collection<E> collection = new ArrayList<>();
        for (E edge : edges) {
            if (edge.isDirected() && edge.getTerminus() == vertex) {
                collection.add(edge);
            }
        }
        return collection;
    }
    
    
    public Collection<E> allUndirectedEdgesFrom(V vertex) {
        Collection<E> collection = new ArrayList<>();
        for (E edge : edges) {
            if (! edge.isDirected() && (edge.getOrigin() == vertex || edge.getTerminus() == vertex)) {
                collection.add(edge);
            }
        }
        return collection;
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


    public V findContainer(V vertex) {
        for (E edge : allDirectedEdgesTo(vertex)) {
            if (edge instanceof ContainerEdge) {
                return edge.getOrigin();
            }
        }
        return null;
    }
    
    
    public Vertex findContainer(V vertex, Class<? extends Vertex> vertexClass) {
        V container = findContainer(vertex);
        if (container != null) {
            if (container.getClass() == vertexClass) {
                return container;
            }
            else {
                return findContainer(container, vertexClass);
            }
        }
        else {
            return null;
        }
    }


    private void findDirectedWalk(List<V> walk, V start, V end) {
        if (! walk.contains(start)) {
            walk.add(start);
            boolean found = start == end;
            if (! found) {
                Iterator<E> leavingEdges = allDirectedEdgesFrom(start).iterator();
                while (! found && leavingEdges.hasNext()) {
                    E nextEdge = leavingEdges.next();
                    if (nextEdge.isDirected()) {
                        findDirectedWalk(walk, nextEdge.getTerminus(), end);
                        found = walk.contains(end);
                    }
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


    private final Collection<V> vertices = new ArrayList<>();
    private final Collection<E> edges = new ArrayList<>();
    
}
