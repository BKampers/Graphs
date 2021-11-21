/*
 * © Bart Kampers
 */
package bka.graph.finders;

import org.junit.jupiter.api.*;

public class RecursiveUndirectedTrailFinderTest extends UndirectedTrailFinderTestBase {

    @Test
    public void testFind() {
        testFind(new RecursiveTrailFinder());
    }

}
