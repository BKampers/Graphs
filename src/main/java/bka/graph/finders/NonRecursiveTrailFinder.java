/*
** © Bart Kampers
*/

package bka.graph.finders;

import bka.graph.*;
import bka.graph.utils.*;
import java.util.*;
import java.util.function.*;


public class NonRecursiveTrailFinder<V, E extends Edge<V>> extends AbstractTrailFinder<V, E> {

    @Override
    public void setRestriction(Predicate<List<E>> restriction) {
        this.restriction = restriction;
    }

    @Override
    public void setFilter(Predicate<List<E>> filter) {
        this.filter = filter;
    }

    public void setLimiter(Predicate<Collection<List<E>>> limiter) {
        this.limiter = limiter;
    }

    @Override
    public Collection<List<E>> find(Collection<E> graph, V start, V end, boolean revisitVertices) {
        Collection<List<E>> foundTrails = new ArrayList<>();
        Deque<SearchStage> stack = new LinkedList<>();
        SearchStage stage = new SearchStage(graph, start);
        for (;;) {
            if (stage.selectNextEdge()) {
                V nextVertex = stage.getAdjacentVertex();
                TrailBuilder currentTrail = new TrailBuilder(stack, stage);
                if (pass(restriction, currentTrail)) {
                    if ((end == null || nextVertex.equals(end)) && pass(filter, currentTrail)) {
                        foundTrails.add(currentTrail.get());
                        if (limiter != null && limiter.test(foundTrails)) {
                            return foundTrails;
                        }
                    }
                    if (revisitVertices || !nextVertex.equals(end)) {
                        stack.push(stage);
                        stage = stage.createComplement(revisitVertices || isFirstLoop(stack, start, end), nextVertex);
                    }
                }
            }
            else if (!stack.isEmpty()) {
                stage = stack.pop();
            }
            else {
                return foundTrails;
            }
        }
    }
    
    private boolean isFirstLoop(Deque stack, V start, V end) {
        return stack.size() == 1 && start.equals(end);
    }

    private boolean pass(Predicate<List<E>> predicate, TrailBuilder trailBuilder) {
        return predicate == null || predicate.test(trailBuilder.get());
    }

    private class TrailBuilder {

        TrailBuilder(Deque<SearchStage> stack, SearchStage stage) {
            this.stack = stack;
            this.stage = stage;
        }

        List<E> get() {
            if (trail == null) {
                trail = new ArrayList<>(stack.size() + 1);
                for (Iterator<SearchStage> it = stack.descendingIterator(); it.hasNext();) {
                    trail.add(it.next().current);
                }
                trail.add(stage.current);
            }
            return trail;
        }

        private final Deque<SearchStage> stack;
        private final SearchStage stage;
        private List<E> trail;

    }

    private class SearchStage {

        public SearchStage(Collection<E> subgraph, V from) {
            this.subgraph = subgraph;
            this.from = from;
            iterator = subgraph.stream().filter(EdgeUtil.isIncidentWith(from)).iterator();
        }

        public SearchStage createComplement(boolean revisitVertices, V nextVertex) {
            Collection<E> remainingEdges = (revisitVertices) ? complement(subgraph, current) : complement(subgraph, from);
            return new SearchStage(remainingEdges, nextVertex);
        }

        public boolean selectNextEdge() {
            if (! iterator.hasNext()) {
                return false;
            }
            current = iterator.next();
            return true;
        }

        public V getAdjacentVertex() {
            return EdgeUtil.getAdjacentVertex(current, from);
        }

        private final Collection<E> subgraph;
        private final V from;
        private final Iterator<E> iterator;
        private E current;
    }

    private Predicate<List<E>> restriction;
    private Predicate<List<E>> filter;
    private Predicate<Collection<List<E>>> limiter;

}
