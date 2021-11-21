/*
 * © Bart Kampers
 */
package bka.graph.finders;

import org.junit.jupiter.api.*;

public class NonRecursiveUndirectedTrailFinderTest extends UndirectedTrailFinderTestBase {

    @Test
    public void testFind() {
        testFind(new NonRecursiveTrailFinder());
    }

}
