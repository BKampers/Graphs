/*
** Copyright © Bart Kampers
*/


package bka.graph;


import java.util.*;


public class Graph {
    
    public Graph() {
    }

    
    public Graph(Collection<Edge> edges) {
        addEdges(edges);
    }
    
    
    public Graph(Collection<Vertex> vertices, Collection<Edge> edges) {
        addEdges(edges);
        addVertices(vertices);
    }
    
    
    public void add(Graph graph) {
        addEdges(graph.edges);
        addVertices(graph.vertices);
    }
    
    
    public final void addVertices(Collection<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            add(vertex);
        }
    }
    
    
    public final void addEdges(Collection<Edge> edges) {
        Iterator<Edge> ie = edges.iterator();
        while (ie.hasNext()) {
            add(ie.next());
        }
    }
    
    
    public void add(Vertex vertex) {
        if (! vertices.contains(vertex)) {
            vertices.add(vertex);
        }
    }
    
    
    public void add(Edge edge) {
        if (! edges.contains(edge)) {
            edges.add(edge);
            add(edge.getOrigin());
            add(edge.getTerminus());
        }
    }
    
    
    public Collection<Vertex> getVertices() {
        return vertices;
    }
    
    
    public Collection<Edge> getEdges() {
        return edges;
    }
    
    
    public boolean contains(Vertex vertex) {
        return vertices.contains(vertex);
    }
    
    
    public boolean contains(Edge edge) {
        return edges.contains(edge);
    }
    
    
    public boolean isDirected() {
        boolean directed = true;
        Iterator<Edge> ie = edges.iterator();
        while (directed && ie.hasNext()) {
            directed = ie.next().isDirected();
        }
        return directed;
    }
    
    
    public boolean isUndirected() {
        boolean undirected = true;
        Iterator<Edge> ie = edges.iterator();
        while (undirected && ie.hasNext()) {
            undirected = ! ie.next().isDirected();
        }
        return undirected;
    }
    
    
    public boolean isMixed() {
        return ! (isDirected() || isUndirected());
    }
    
    
    public Collection<Edge> allDirectedEdgesFrom(Vertex vertex) {
        Collection<Edge> collection = new ArrayList<>();
        Iterator<Edge> ie = edges.iterator();
        while (ie.hasNext()) {
            Edge edge = ie.next();
            if (edge.isDirected() && edge.getOrigin() == vertex) {
                collection.add(edge);
            }
        }
        return collection;
    }
    
    
    public Collection<Edge> allDirectedEdgesTo(Vertex vertex) {
        Collection<Edge> collection = new ArrayList<>();
        Iterator<Edge> ie = edges.iterator();
        while (ie.hasNext()) {
            Edge edge = ie.next();
            if (edge.isDirected() && edge.getTerminus() == vertex) {
                collection.add(edge);
            }
        }
        return collection;
    }
    
    
    public Collection<Edge> allUndirectedEdgesFrom(Vertex vertex) {
        Collection<Edge> collection = new ArrayList<>();
        Iterator<Edge> ie = edges.iterator();
        while (ie.hasNext()) {
            Edge edge = ie.next();
            if (! edge.isDirected() && 
                (edge.getOrigin() == vertex || edge.getTerminus() == vertex)) 
            {
                collection.add(edge);
            }
        }
        return collection;
    }
    
    
    public Graph directedGraphFrom(Vertex seed) {
        Graph graph = new Graph();
        Collection<Edge> leavingEdges = allDirectedEdgesFrom(seed);
        Iterator<Edge> ie = leavingEdges.iterator();
        while (ie.hasNext()) {
            Edge edge = ie.next();
            graph.add(edge.getOrigin());
            Vertex terminus = edge.getTerminus();
            if (! graph.contains(terminus)) {
                graph.add(directedGraphFrom(terminus));
            }
            graph.add(edge);
        }
        return graph;
    }
    
    
    public List<Vertex> directedWalk(Vertex start, Vertex end) {
        List<Vertex> walk = new ArrayList<>();
        findDirectedWalk(walk, start, end);
        return walk;
    }
    
    
    private void findDirectedWalk(List<Vertex> walk, Vertex start, Vertex end) {
        if (! walk.contains(start)) {
            walk.add(start);
            boolean found = start == end;
            if (! found) {
                Iterator<Edge> leavingEdges = allDirectedEdgesFrom(start).iterator();
                while (! found && leavingEdges.hasNext()) {
                    Edge nextEdge = leavingEdges.next();
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
    
    
    public Vertex findContainer(Vertex vertex) {
        for (Edge edge : allDirectedEdgesTo(vertex)) {
            if (edge instanceof ContainerEdge) {
                return edge.getOrigin();
            }
        }
        return null;
    }
    
    
    public Vertex findContainer(Vertex vertex, Class<? extends Vertex> vertexClass) {
        Vertex container = findContainer(vertex);
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


    private final Collection<Vertex> vertices = new ArrayList<>();
    private final Collection<Edge> edges = new ArrayList<>();
    
}
