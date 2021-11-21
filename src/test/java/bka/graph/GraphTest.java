/*
 * © Bart Kampers
 */
package bka.graph;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.*;

public class GraphTest {

    @Test
    public void test() {
        LabeledEdge<Character> ab = new LabeledEdge<>('A', 'B', "AB");
        LabeledEdge<Character> ac = new LabeledEdge<>('A', 'C', "AC");
        LabeledEdge<Character> bc = new LabeledEdge<>('B', 'C', "BC");
        LabeledEdge<Character> bd = new LabeledEdge<>('B', 'D', "BD");
        GraphBase<Character, LabeledEdge<Character>> immutable = new ImmutableGraph<>(Arrays.asList(ab, ac));
        MutableGraph<Character, LabeledEdge<Character>> mutable = new MutableGraph<>(immutable);
        assertEquals(immutable.getVertices(), mutable.getVertices());
        assertEquals(immutable.getEdges(), mutable.getEdges());
        mutable.addEdge(bc);
        assertEquals(immutable.getVertices(), mutable.getVertices());
        assertNotEquals(immutable.getEdges(), mutable.getEdges());
        DefaultMutableGraph<Character> default1 = new DefaultMutableGraph<>(immutable);
        assertEquals(immutable.getVertices(), default1.getVertices());
        assertEquals(immutable.getEdges(), default1.getEdges());
        DefaultMutableGraph<Character> default2 = new DefaultMutableGraph<>(mutable.getVertices(), mutable.getEdges());
        assertEquals(mutable.getVertices(), default2.getVertices());
        assertEquals(mutable.getEdges(), default2.getEdges());
        default1.addEdge(bd);
    }


    private class LabeledEdge<V> extends UndirectedEdge<V> {

        public LabeledEdge(V origin, V terminus, String label) {
            super(origin, terminus);
            this.label = label;
        }

        String getLabel() {
            return label;
        }

        private final String label;

    }

}
