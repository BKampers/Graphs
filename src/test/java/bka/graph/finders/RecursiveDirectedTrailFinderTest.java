/*
 * © Bart Kampers
 */
package bka.graph.finders;

import org.junit.jupiter.api.*;

public class RecursiveDirectedTrailFinderTest extends DirectedTrailFinderTestBase {

    @Test
    public void testFind() {
        testFind(new RecursiveDirectedTrailFinder());
    }
}
