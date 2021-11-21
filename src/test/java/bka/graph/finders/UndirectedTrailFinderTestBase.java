/*
** © Bart Kampers
*/

package bka.graph.finders;

import bka.graph.*;
import java.util.*;

public abstract class UndirectedTrailFinderTestBase extends TrailFinderTestBase {

    @Override
    protected List<TestCase> getTestCases() {
        return Arrays.asList(
            new TestCase( // empty graph
                Collections.emptyList(),
                Arrays.asList(
                    new ExpectedTrails(
                        new Object(), null,
                        Collections.emptyList(),
                        Collections.emptyList()
                    ),
                    new ExpectedTrails(
                        new Object(), new Object(),
                        Collections.emptyList(),
                        Collections.emptyList()
                    )
                )
            ),
            new TestCase( // one edge
                graph(AB),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Collections.emptyList(),
                        Collections.emptyList()
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB))
                    ),
                    new ExpectedTrails(
                        B, A,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB))
                    ),
                    new ExpectedTrails(
                        B, B,
                        Collections.emptyList(),
                        Collections.emptyList()
                    )
                )
            ),
            new TestCase( // fork of two edges
                graph(AB, AC),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB), trail(AC)),
                        Arrays.asList(trail(AB), trail(AC))
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB))
                    ),
                    new ExpectedTrails(
                        A, C,
                        Arrays.asList(trail(AC)),
                        Arrays.asList(trail(AC))
                    )
                )
            ),
            new TestCase( // trail of two edges
                graph(AB, BC),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB), trail(AB, BC)),
                        Arrays.asList(trail(AB), trail(AB, BC))
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB))
                    ),
                    new ExpectedTrails(
                        A, C,
                        Arrays.asList(trail(AB, BC)),
                        Arrays.asList(trail(AB, BC))
                    )
                )
            ),
            new TestCase( // loop
                graph(AA),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AA)),
                        Arrays.asList(trail(AA))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AA)),
                        Arrays.asList(trail(AA))
                    )
                )
            ),
            new TestCase( // cycle of two edges
                graph(AB, BA),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB), trail(BA)),
                        Arrays.asList(trail(AB), trail(AB, BA), trail(BA), trail(BA, AB))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AB, BA), trail(BA, AB)),
                        Arrays.asList(trail(AB, BA), trail(BA, AB))
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB), trail(BA)),
                        Arrays.asList(trail(AB), trail(BA))
                    )
                )
            ),
            new TestCase( // cycle of three edges
                graph(AB, BC, CA),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB), trail(CA), trail(AB, BC), trail(CA, BC)),
                        Arrays.asList(trail(AB), trail(CA), trail(AB, BC), trail(CA, BC), trail(AB, BC, CA), trail(CA, BC, AB))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AB, BC, CA), trail(CA, BC, AB)),
                        Arrays.asList(trail(AB, BC, CA), trail(CA, BC, AB))
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB), trail(CA, BC)),
                        Arrays.asList(trail(AB), trail(CA, BC))
                    ),
                    new ExpectedTrails(
                        A, C,
                        Arrays.asList(trail(CA), trail(AB, BC)),
                        Arrays.asList(trail(CA), trail(AB, BC))
                    )
                )
            ),
            new TestCase( // loop with fork
                graph(AA, AB),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AA), trail(AB)),
                        Arrays.asList(trail(AA), trail(AA, AB), trail(AB))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AA)),
                        Arrays.asList(trail(AA))
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AA, AB), trail(AB))
                    ),
                    new ExpectedTrails(
                        B, null,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB), trail(AB, AA))
                    ),
                    new ExpectedTrails(
                        B, A,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB), trail(AB, AA))
                    )
                )
            ),
            new TestCase( // cycle with fork
                graph(AB, BC, CA, AD),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB), trail(CA), trail(CA, BC), trail(AB, BC), trail(AD)),
                        Arrays.asList(trail(AB), trail(AB, BC), trail(AB, BC, CA), trail(AB, BC, CA, AD), trail(CA), trail(CA, BC), trail(CA, BC, AB), trail(CA, BC, AB, AD), trail(AD))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AB, BC, CA), trail(CA, BC, AB)),
                        Arrays.asList(trail(AB, BC, CA), trail(CA, BC, AB))
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB), trail(CA, BC)),
                        Arrays.asList(trail(AB), trail(CA, BC))
                    ),
                    new ExpectedTrails(
                        A, C,
                        Arrays.asList(trail(CA), trail(AB, BC)),
                        Arrays.asList(trail(CA), trail(AB, BC))
                    ),
                    new ExpectedTrails(
                        A, D,
                        Arrays.asList(trail(AD)),
                        Arrays.asList(Arrays.asList(CA, BC, AB, AD), trail(AB, BC, CA, AD), trail(AD))
                    )
                )
            ),
            new TestCase( // circuit
                graph(AB, BA, AC, CA),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB), trail(BA), trail(AC), trail(CA)),
                        Arrays.asList(trail(AB), trail(AB, BA), trail(AB, BA, AC), trail(AB, BA, AC, CA), trail(AB, BA, CA), trail(AB, BA, CA, AC), trail(BA), trail(BA, AB), trail(BA, AB, AC), trail(BA, AB, AC, CA), trail(BA, AB, CA), trail(BA, AB, CA, AC), trail(AC), trail(AC, CA), trail(AC, CA, AB), trail(AC, CA, AB, BA), trail(AC, CA, BA), trail(AC, CA, BA, AB), trail(CA), trail(CA, AC), trail(CA, AC, AB), trail(CA, AC, AB, BA), trail(CA, AC, BA), trail(CA, AC, BA, AB))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AB, BA), trail(BA, AB), trail(AC, CA), trail(CA, AC)),
                        Arrays.asList(trail(AB, BA), trail(AB, BA, AC, CA), trail(AB, BA, CA, AC), trail(BA, AB), trail(BA, AB, AC, CA), trail(BA, AB, CA, AC), trail(AC, CA), trail(AC, CA, AB, BA), trail(AC, CA, BA, AB), trail(CA, AC), trail(CA, AC, AB, BA), trail(CA, AC, BA, AB))
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB), trail(BA)),
                        Arrays.asList(trail(AB), trail(BA), trail(AC, CA, AB), trail(AC, CA, BA), trail(CA, AC, AB), trail(CA, AC, BA))
                    ),
                    new ExpectedTrails(
                        A, C,
                        Arrays.asList(trail(AC), trail(CA)),
                        Arrays.asList(trail(AB, BA, AC), trail(AB, BA, CA), trail(BA, AB, AC), trail(BA, AB, CA), trail(AC), trail(CA))
                    )
                )
            )
        );
    }

    private static final Object A = 'a';
    private static final Object B = 'b';
    private static final Object C = 'c';
    private static final Object D = 'd';

    private static final UndirectedEdge<Object> AA = new UndirectedEdge<>(A, A);
    private static final UndirectedEdge<Object> AB = new UndirectedEdge<>(A, B);
    private static final UndirectedEdge<Object> AC = new UndirectedEdge<>(A, C);
    private static final UndirectedEdge<Object> AD = new UndirectedEdge<>(A, D);
    private static final UndirectedEdge<Object> BA = new UndirectedEdge<>(B, A);
    private static final UndirectedEdge<Object> BC = new UndirectedEdge<>(B, C);
    private static final UndirectedEdge<Object> CA = new UndirectedEdge<>(C, A);

}
