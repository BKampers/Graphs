/*
** © Bart Kampers
*/

package bka.graph.finders;

import bka.graph.*;
import bka.graph.utils.*;
import java.util.function.*;


public class RecursiveDirectedTrailFinder<V, E extends DirectedEdge<V>> extends RecursiveTrailFinder<V, E> {

    @Override
    protected Predicate<E> from(V start) {
        return EdgeUtil.from(start);
    }

    @Override
    protected V next(E edge, V start) {
        return edge.getTerminus();
    }

}
