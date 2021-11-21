/*
 * © Bart Kampers
 */
package bka.graph;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.*;
import org.mockito.*;

@SuppressWarnings("unchecked")
public class MutableGraphTest {


    @Test
    public void testInitial() {
        graph = new MutableGraph();
        assertTrue(graph.getEdges().isEmpty());
        assertTrue(graph.getVertices().isEmpty());
    }

    @Test
    public void testAddVertex() {
        graph = new MutableGraph();
        assertTrue(graph.addVertex(1));
        assertEqualCollections(Arrays.asList(1), graph.getVertices());
        assertTrue(graph.addVertex(2));
        assertEqualCollections(Arrays.asList(1, 2), graph.getVertices());
        assertFalse(graph.addVertex(1));
        assertEqualCollections(Arrays.asList(1, 2), graph.getVertices());
        assertTrue(graph.getEdges().isEmpty());
    }

    @Test
    public void testAddVertices() {
        graph = new MutableGraph();
        assertTrue(graph.addVertices(Arrays.asList('a', 'b')));
        assertEqualCollections(Arrays.asList('a', 'b'), graph.getVertices());
        assertTrue(graph.addVertices(Arrays.asList('b', 'c', 'd')));
        assertEqualCollections(Arrays.asList('a', 'b', 'c', 'd'), graph.getVertices());
        assertFalse(graph.addVertices(Arrays.asList('a', 'b', 'c', 'd')));
        assertEqualCollections(Arrays.asList('a', 'b', 'c', 'd'), graph.getVertices());
        assertTrue(graph.getEdges().isEmpty());
    }

    @Test
    public void testAddEdge() {
        Edge ab = edge("A", "B");
        Edge bc = edge("B", "C");
        graph = new MutableGraph();
        assertTrue(graph.addEdge(ab));
        assertEqualCollections(Arrays.asList(ab), graph.getEdges());
        assertEqualCollections(Arrays.asList("A", "B"), graph.getVertices());
        assertTrue(graph.addEdge(bc));
        assertEqualCollections(Arrays.asList(ab, bc), graph.getEdges());
        assertEqualCollections(Arrays.asList("A", "B", "C"), graph.getVertices());
        assertFalse(graph.addEdge(bc));
        assertEqualCollections(Arrays.asList(ab, bc), graph.getEdges());
        assertEqualCollections(Arrays.asList("A", "B", "C"), graph.getVertices());
    }

    @Test
    public void testAddEdges() {
        Edge ab = edge("A", "B");
        Edge ac = edge("A", "C");
        Edge bc = edge("B", "C");
        graph = new MutableGraph();
        assertTrue(graph.addEdges(Arrays.asList(ab, ac)));
        assertEqualCollections(Arrays.asList(ab, ac), graph.getEdges());
        assertEqualCollections(Arrays.asList("A", "B", "C"), graph.getVertices());
        assertTrue(graph.addEdges(Arrays.asList(ab, bc)));
        assertEqualCollections(Arrays.asList(ab, ac, bc), graph.getEdges());
        assertEqualCollections(Arrays.asList("A", "B", "C"), graph.getVertices());
        assertFalse(graph.addEdges(Arrays.asList(ac, bc)));
        assertEqualCollections(Arrays.asList(ab, ac, bc), graph.getEdges());
        assertEqualCollections(Arrays.asList("A", "B", "C"), graph.getVertices());
    }

    @Test
    public void testRemoveVertex() {
        Edge ab = edge('a', 'b');
        Edge ac = edge('a', 'c');
        Edge bc = edge('b', 'c');
        graph = new MutableGraph(Arrays.asList(ab, ac, bc));
        assertTrue(graph.removeVertex('c'));
        assertEqualCollections(Arrays.asList('a', 'b'), graph.getVertices());
        assertEqualCollections(Arrays.asList(ab), graph.getEdges());
        assertTrue(graph.removeVertex('b'));
        assertEqualCollections(Arrays.asList('a'), graph.getVertices());
        assertEqualCollections(Arrays.asList(), graph.getEdges());
        assertFalse(graph.removeVertex('b'));
        assertEqualCollections(Arrays.asList('a'), graph.getVertices());
        assertEqualCollections(Arrays.asList(), graph.getEdges());
    }

    @Test
    public void testRemoveVertices() {
        Edge ab = edge('a', 'b');
        Edge ac = edge('a', 'c');
        Edge bc = edge('b', 'c');
        graph = new MutableGraph(Arrays.asList(ab, ac, bc));
        assertTrue(graph.removeVertices(Arrays.asList('a', 'b')));
        assertEqualCollections(Arrays.asList('c'), graph.getVertices());
        assertEqualCollections(Arrays.asList(), graph.getEdges());
        assertFalse(graph.removeVertices(Arrays.asList('a', 'b')));
        assertEqualCollections(Arrays.asList('c'), graph.getVertices());
        assertEqualCollections(Arrays.asList(), graph.getEdges());
        assertTrue(graph.removeVertices(Arrays.asList('c', 'd')));
        assertEqualCollections(Arrays.asList(), graph.getVertices());
        assertEqualCollections(Arrays.asList(), graph.getEdges());
    }

    @Test
    public void testRemoveEdge() {
        Edge ab = edge('a', 'b');
        Edge ac = edge('a', 'c');
        Edge bc = edge('b', 'c');
        graph = new MutableGraph(Arrays.asList(ab, ac, bc));
        assertTrue(graph.removeEdge(ab));
        assertEqualCollections(Arrays.asList(ac, bc), graph.getEdges());
        assertEqualCollections(Arrays.asList('a', 'b', 'c'), graph.getVertices());
        assertFalse(graph.removeEdge(ab));
        assertEqualCollections(Arrays.asList(ac, bc), graph.getEdges());
        assertEqualCollections(Arrays.asList('a', 'b', 'c'), graph.getVertices());
        assertTrue(graph.removeEdge(bc));
        assertEqualCollections(Arrays.asList(ac), graph.getEdges());
        assertEqualCollections(Arrays.asList('a', 'b', 'c'), graph.getVertices());
        assertTrue(graph.removeEdge(ac));
        assertEqualCollections(Arrays.asList(), graph.getEdges());
        assertEqualCollections(Arrays.asList('a', 'b', 'c'), graph.getVertices());
    }

    @Test
    public void testRemoveEdges() {
        Edge ab = edge('a', 'b');
        Edge ac = edge('a', 'c');
        Edge bc = edge('b', 'c');
        graph = new MutableGraph(Arrays.asList(ab, ac, bc));
        assertTrue(graph.removeEdges(Arrays.asList(ab, bc)));
        assertEqualCollections(Arrays.asList(ac), graph.getEdges());
        assertEqualCollections(Arrays.asList('a', 'b', 'c'), graph.getVertices());
        assertTrue(graph.removeEdges(Arrays.asList(ab, ac)));
        assertEqualCollections(Arrays.asList(), graph.getEdges());
        assertEqualCollections(Arrays.asList('a', 'b', 'c'), graph.getVertices());
        assertFalse(graph.removeEdges(Arrays.asList(ab, ac, bc)));
        assertEqualCollections(Arrays.asList(), graph.getEdges());
        assertEqualCollections(Arrays.asList('a', 'b', 'c'), graph.getVertices());
    }

    @Test
    public void testClear() {
        graph = new MutableGraph(
            Arrays.asList(Float.POSITIVE_INFINITY, Double.NaN, Boolean.TRUE),
            Arrays.asList(edge('a', 0), edge('a', "B"), edge("B", new Object())));
        graph.clear();
        assertTrue(graph.getEdges().isEmpty());
        assertTrue(graph.getVertices().isEmpty());
    }

    private static <T> void assertEqualCollections(Collection<T> expected, Set<T> actual) {
        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }

    private Edge edge(Object v1, Object v2) {
        Edge edge = Mockito.mock(Edge.class);
        Mockito.when(edge.getVertices()).thenReturn(Arrays.asList(v1, v2));
        return edge;
    }

    private MutableGraph graph;


}
