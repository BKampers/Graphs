/*
** © Bart Kampers
*/

package bka.graph.finders;

import bka.graph.*;
import java.util.*;

public abstract class DirectedTrailFinderTestBase extends TrailFinderTestBase {

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
                Arrays.asList(AB),
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
                        B, null,
                        Collections.emptyList(),
                        Collections.emptyList()
                    )
                )
            ),
            new TestCase( // fork of two edges
                Arrays.asList(AB, AC),
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
                    ),
                    new ExpectedTrails(
                        B, null,
                        Collections.emptyList(),
                        Collections.emptyList()
                    ),
                    new ExpectedTrails(
                        C, null,
                        Collections.emptyList(),
                        Collections.emptyList()
                    )
                )
            ),
            new TestCase( // trail of two edges
                Arrays.asList(AB, BC),
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
                Arrays.asList(AA),
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
                Arrays.asList(AB, BA),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB), trail(AB, BA))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AB, BA)),
                        Arrays.asList(trail(AB, BA))
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB))
                    ),
                    new ExpectedTrails(
                        B, null,
                        Arrays.asList(trail(BA)),
                        Arrays.asList(trail(BA), trail(BA, AB))
                    ),
                    new ExpectedTrails(
                        B, A,
                        Arrays.asList(trail(BA)),
                        Arrays.asList(trail(BA))
                    ),
                    new ExpectedTrails(
                        B, B,
                        Arrays.asList(trail(BA, AB)),
                        Arrays.asList(trail(BA, AB))
                    )
                )
            ),
            new TestCase( // cycle of three edges
                Arrays.asList(AB, BC, CA),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB), trail(AB, BC)),
                        Arrays.asList(trail(AB), trail(AB, BC), trail(AB, BC, CA))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AB, BC, CA)),
                        Arrays.asList(trail(AB, BC, CA))
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
                    ),
                    new ExpectedTrails(
                        B, null,
                        Arrays.asList(trail(BC), trail(BC, CA)),
                        Arrays.asList(trail(BC), trail(BC, CA), trail(BC, CA, AB))
                    ),
                    new ExpectedTrails(
                        B, C,
                        Arrays.asList(trail(BC)),
                        Arrays.asList(trail(BC))
                    ),
                    new ExpectedTrails(
                        B, A,
                        Arrays.asList(trail(BC, CA)),
                        Arrays.asList(trail(BC, CA))
                    ),
                    new ExpectedTrails(
                        B, B,
                        Arrays.asList(trail(BC, CA, AB)),
                        Arrays.asList(trail(BC, CA, AB))
                    ),
                    new ExpectedTrails(
                        C, null,
                        Arrays.asList(trail(CA), trail(CA, AB)),
                        Arrays.asList(trail(CA), trail(CA, AB), trail(CA, AB, BC))
                    ),
                    new ExpectedTrails(
                        C, A,
                        Arrays.asList(trail(CA)),
                        Arrays.asList(trail(CA))
                    ),
                    new ExpectedTrails(
                        C, B,
                        Arrays.asList(trail(CA, AB)),
                        Arrays.asList(trail(CA, AB))
                    ),
                    new ExpectedTrails(
                        C, C,
                        Arrays.asList(trail(CA, AB, BC)),
                        Arrays.asList(trail(CA, AB, BC))
                    )
                )
            ),
            new TestCase( // loop with fork
                Arrays.asList(AA, AB),
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
                        Collections.emptyList(),
                        Collections.emptyList())
                )
            ),
            new TestCase( // cycle with fork
                Arrays.asList(AB, BC, CA, AD),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB), trail(AB, BC), trail(AD)),
                        Arrays.asList(trail(AB), trail(AB, BC), trail(AB, BC, CA), trail(AB, BC, CA, AD), trail(AD))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AB, BC, CA)),
                        Arrays.asList(trail(AB, BC, CA))
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
                    ),
                    new ExpectedTrails(
                        A, D,
                        Arrays.asList(trail(AD)),
                        Arrays.asList(trail(AB, BC, CA, AD), trail(AD))
                    )
                )
            ),
            new TestCase( // circuit
                Arrays.asList(AB, BA, AC, CA),
                Arrays.asList(
                    new ExpectedTrails(
                        A, null,
                        Arrays.asList(trail(AB), trail(AC)),
                        Arrays.asList(trail(AB), trail(AB, BA), trail(AB, BA, AC), trail(AB, BA, AC, CA), trail(AC), trail(AC, CA), trail(AC, CA, AB), trail(AC, CA, AB, BA))
                    ),
                    new ExpectedTrails(
                        A, A,
                        Arrays.asList(trail(AB, BA), trail(AC, CA)),
                        Arrays.asList(trail(AB, BA), trail(AB, BA, AC, CA), trail(AC, CA), trail(AC, CA, AB, BA))
                    ),
                    new ExpectedTrails(
                        A, B,
                        Arrays.asList(trail(AB)),
                        Arrays.asList(trail(AB), trail(AC, CA, AB))
                    ),
                    new ExpectedTrails(
                        A, C,
                        Arrays.asList(trail(AC)),
                        Arrays.asList(trail(AC), trail(AB, BA, AC))
                    )
                )
            )
        );
    }

    private static final Object A = "a";
    private static final Object B = "b";
    private static final Object C = "c";
    private static final Object D = "d";

    private static final DirectedEdge<Object> AA = new DirectedEdge<>(A, A);
    private static final DirectedEdge<Object> AB = new DirectedEdge<>(A, B);
    private static final DirectedEdge<Object> AC = new DirectedEdge<>(A, C);
    private static final DirectedEdge<Object> AD = new DirectedEdge<>(A, D);
    private static final DirectedEdge<Object> BA = new DirectedEdge<>(B, A);
    private static final DirectedEdge<Object> BC = new DirectedEdge<>(B, C);
    private static final DirectedEdge<Object> CA = new DirectedEdge<>(C, A);

}
