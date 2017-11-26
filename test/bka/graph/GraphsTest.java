/*
 * Copyright © Bart Kampers
 */

package bka.graph;

import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class GraphsTest {
    
    
    @Test
    public void testAddVertex() {
        Graph graph = new Graph(Arrays.asList(D12));
        assertFalse(graph.add(V1));
        assertContainsExactly(graph.getVertices(), V1, V2);
        assertTrue(graph.add(V3));
        assertContainsExactly(graph.getVertices(), V1, V2, V3);
    }

    
    @Test
    public void testAddEdge() {
        Graph graph = new Graph(Arrays.asList(D12));
        assertFalse(graph.add(D12));
        assertContainsExactly(graph.getEdges(), D12);
        assertContainsExactly(graph.getVertices(), V1, V2);
        assertTrue(graph.add(U12));
        assertContainsExactly(graph.getEdges(), D12, U12);
        assertContainsExactly(graph.getVertices(), V1, V2);
    }
    
    
    @Test(expected=RuntimeException.class)
    public void testAddNullVertex() {
        Graph graph = new Graph();
        graph.add((Vertex) null);
    }
    
    
    @Test(expected=RuntimeException.class)
    public void testAddNullEdge() {
        Graph graph = new Graph();
        graph.add((Edge) null);
    }
    
    
    @Test
    public void testAddUniqueEdge() {
        Edge u1 = new UniqueDirectedEdge(V1, V2);
        Edge u2 = new UniqueDirectedEdge(V2, V3);
        Graph graph = new Graph(Arrays.asList(u1));
        assertFalse(graph.add(new UniqueDirectedEdge(V1, V2)));
        assertContainsExactly(graph.getEdges(), u1);
        assertContainsExactly(graph.getVertices(), V1, V2);
        assertTrue(graph.add(u2));
        assertContainsExactly(graph.getEdges(), u1, u2);
        assertContainsExactly(graph.getVertices(), V1, V2, V3);
    }
    
    
    @Test
    public void testAddVertices() {
        Graph graph = new Graph();
        graph.addVertices(Arrays.asList(V0, V1, V2, V0));
        assertContainsExactly(graph.getVertices(), V0, V1, V2);
        graph.addVertices(Arrays.asList(V2, V3));
        assertContainsExactly(graph.getVertices(), V0, V1, V2, V3);
    }
    
    
    @Test
    public void testAddEdges() {
        Graph graph = new Graph();
        graph.addEdges(Arrays.asList(D12, D12, D23));
        assertContainsExactly(graph.getEdges(), D12, D23);
        graph.addEdges(Arrays.asList(D23, D31));
        assertContainsExactly(graph.getEdges(), D12, D23, D31);
    }
    
    
    @Test
    public void testRemoveVertex() {
        Graph graph = new Graph(Arrays.asList(D12, D23));
        assertFalse(graph.remove(V0));
        assertContainsExactly(graph.getVertices(), V1, V2, V3);
        assertContainsExactly(graph.getEdges(), D12, D23);
        assertTrue(graph.remove(V3));
        assertContainsExactly(graph.getVertices(), V1, V2);
        assertContainsExactly(graph.getEdges(), D12);
    }


    @Test
    public void testRemoveEdge() {
        Graph graph = new Graph(Arrays.asList(D12, D23));
        assertFalse(graph.remove(D31));
        assertContainsExactly(graph.getVertices(), V1, V2, V3);
        assertContainsExactly(graph.getEdges(), D12, D23);
        assertTrue(graph.remove(D23));
        assertContainsExactly(graph.getVertices(), V1, V2, V3);
        assertContainsExactly(graph.getEdges(), D12);
    }


    @Test
    public void testContains() {
        Graph graph = new Graph(Arrays.asList(V0), Arrays.asList(U12));
        assertTrue(graph.contains(V0));
        assertTrue(graph.contains(V1));
        assertTrue(graph.contains(V2));
        assertTrue(graph.contains(U12));
    }


    @Test
    public void testIsDirected() {
        assertTrue(new Graph(Arrays.asList(D01, D02)).isDirected());
        assertFalse(new Graph(Arrays.asList(U01, U02)).isDirected());
        assertFalse(new Graph(Arrays.asList(D01, U02)).isDirected());
    }


    @Test
    public void testIsUndirected() {
        assertFalse(new Graph(Arrays.asList(D01, D02)).isUndirected());
        assertTrue(new Graph(Arrays.asList(U01, U02)).isUndirected());
        assertFalse(new Graph(Arrays.asList(D01, U02)).isUndirected());
    }


    @Test
    public void testIsMixed() {
        assertFalse(new Graph(Arrays.asList(D01, D02)).isMixed());
        assertFalse(new Graph(Arrays.asList(U01, U02)).isMixed());
        assertTrue(new Graph(Arrays.asList(D01, U02)).isMixed());
    }


    @Test
    public void testAllUndirectedEdgesFrom() {
        Graph graph = new Graph(Arrays.asList(U01, U02, U03, U12, U23, U31, D01, D02, D03, D12, D23, D31));
        Collection<Edge> edges = graph.allUndirectedEdgesFrom(V0);
        assertContainsExactly(edges, U01, U02, U03);
    }


    @Test
    public void testAllDirectedEdgesFrom() {
        Graph graph = new Graph(Arrays.asList(U01, U02, U03, U12, U23, U31, D01, D02, D03, D12, D23, D31));
        Collection<Edge> edges = graph.allDirectedEdgesFrom(V0);
        assertContainsExactly(edges, D01, D02, D03);
    }


    @Test
    public void testAllDirectedEdgesTo() {
        Graph graph = new Graph(Arrays.asList(U01, U02, U03, U12, U23, U31, D01, D02, D03, D12, D23, D31));
        Collection<Edge> edges = graph.allDirectedEdgesTo(V1);
        assertContainsExactly(edges, D01, D31);
    }


    @Test
    public void testDirectedWalk() {
        Graph graph = new Graph(Arrays.asList(U01, U02, U03, U12, U23, U31, D01, D02, D03, D12, D23, D31));
        List<Vertex> walk = graph.directedWalk(V1, V3);
        assertContainsExactly(walk, V1, V2, V3);
    }


    @Test
    public void testDirectedGraphFrom() {
        Graph graph = new Graph(Arrays.asList(U01, U02, U03, U12, U23, U31, D01, D02, D03, D12, D23, D31));
        Graph directedGraph = graph.directedGraphFrom(V1);
        Collection<Edge> edges = directedGraph.getEdges();
        assertContainsExactly(edges, D12, D23, D31);
    }


    @Test
    public void testFindOrigin() {
        Vertex typed = new TypedVertex();
        Graph graph = new Graph(Arrays.asList(typed), Arrays.asList(D12));
        graph.add(new DirectedEdge(typed, V2));
        assertEquals(typed, graph.findOrigin(V2, TypedVertex.class));
    }


    private static void assertContainsExactly(Collection collection, Object... elements) {
        assertEquals(elements.length, collection.size());
        assertTrue(collection.containsAll(Arrays.asList(elements)));
    }


    private class TypedVertex extends Vertex {}


    private static final Vertex V0 = new Vertex();
    private static final Vertex V1 = new Vertex();
    private static final Vertex V2 = new Vertex();
    private static final Vertex V3 = new Vertex();

    private static final Edge U01 = new Edge(V0, V1);
    private static final Edge U02 = new Edge(V0, V2);
    private static final Edge U03 = new Edge(V0, V3);
    private static final Edge U12 = new Edge(V1, V2);
    private static final Edge U23 = new Edge(V2, V3);
    private static final Edge U31 = new Edge(V3, V1);

    private static final Edge D01 = new DirectedEdge(V0, V1);
    private static final Edge D02 = new DirectedEdge(V0, V2);
    private static final Edge D03 = new DirectedEdge(V0, V3);
    private static final Edge D12 = new DirectedEdge(V1, V2);
    private static final Edge D23 = new DirectedEdge(V2, V3);
    private static final Edge D31 = new DirectedEdge(V3, V1);

}