/*
** © Bart Kampers
*/

package bka.graph.finders;

import bka.graph.*;
import bka.graph.utils.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;


public class RecursiveTrailFinder<V, E extends Edge<V>> extends AbstractTrailFinder<V, E> {

    @Override
    public void setRestriction(Predicate<List<E>> restriction) {
        this.restriction = restriction;
    }

    @Override
    public void setFilter(Predicate<List<E>> filter) {
        this.filter = filter;
    }

    @Override
    public Collection<List<E>> find(Collection<E> graph, V start, V end, boolean revisitVertices) {
        if (!revisitVertices) {
            Collection<E> noLoops = graph.stream().filter(edge -> !EdgeUtil.isLoop(edge) || edge.getVertices().contains(start)).collect(Collectors.toList());
            return find(noLoops, start, end, revisitVertices, new LinkedList<>());
        }
        return find(graph, start, end, revisitVertices, new LinkedList<>());
    }

    private Collection<List<E>> find(Collection<E> graph, V start, V end, boolean revisitVertices, LinkedList<E> currentTrail) {
        Collection<List<E>> allTrails = new ArrayList<>();
        graph.stream().filter(from(start)).forEach(nextEdge -> {
            currentTrail.add(nextEdge);
            V nextVertex = next(nextEdge, start);
            if (pass(restriction, currentTrail)) {
                if ((end == null || nextVertex.equals(end)) && pass(filter, currentTrail)) {
                    List<E> trail = new LinkedList<>();
                    trail.add(nextEdge);
                    allTrails.add(trail);
                }
                if (revisitVertices || !nextVertex.equals(end)) {
                    Collection<E> remainingEdges = (revisitVertices || start.equals(end)) ? complement(graph, nextEdge) : complement(graph, start);
                    find(remainingEdges, nextVertex, end, revisitVertices, currentTrail).forEach(trail -> {
                        trail.add(0, nextEdge);
                        allTrails.add(trail);
                    });
                }
            }
            currentTrail.removeLast();
        });
        return allTrails;
    }

    protected Predicate<E> from(V start) {
        return EdgeUtil.isIncidentWith(start);
    }

    protected V next(E edge, V start) {
        return EdgeUtil.getAdjacentVertex(edge, start);
    }

    private Predicate<List<E>> restriction;
    private Predicate<List<E>> filter;

}
