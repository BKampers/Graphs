/*
** © Bart Kampers
*/

package bka.graph.example;

import bka.graph.*;
import bka.graph.finders.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.*;


public class ColoredCubePuzzle {


    public ColoredCubePuzzle() {
        graph.addEdge(new OppositeFaces(Color.RED, Color.WHITE, 1));
        graph.addEdge(new OppositeFaces(Color.YELLOW, Color.BLUE, 1));
        graph.addEdge(new OppositeFaces(Color.YELLOW, Color.WHITE, 1));
        graph.addEdge(new OppositeFaces(Color.BLUE, Color.WHITE, 2));
        graph.addEdge(new OppositeFaces(Color.YELLOW, Color.BLUE, 2));
        graph.addEdge(new OppositeFaces(Color.YELLOW, Color.RED, 2));
        graph.addEdge(new OppositeFaces(Color.RED, Color.YELLOW, 3));
        graph.addEdge(new OppositeFaces(Color.BLUE, Color.BLUE, 3));
        graph.addEdge(new OppositeFaces(Color.RED, Color.WHITE, 3));
        graph.addEdge(new OppositeFaces(Color.WHITE, Color.WHITE, 4));
        graph.addEdge(new OppositeFaces(Color.YELLOW, Color.RED, 4));
        graph.addEdge(new OppositeFaces(Color.BLUE, Color.WHITE, 4));
    }


    public static void main(String[] args) {
        Collection<Collection<GraphBase<Color, OppositeFaces>>> resolutions = new ColoredCubePuzzle().resolveByTrails();
        for (Collection<GraphBase<Color, OppositeFaces>> resolution : resolutions) {
            for (GraphBase<Color, OppositeFaces> graph : resolution) {
                print(graph.getEdges());
            }
            System.out.println("======");
        }
    }


    public Collection<Collection<GraphBase<Color, OppositeFaces>>> resolveByCycles() {
        return distinctResolutions(findCycles());
    }


    private List<List<OppositeFaces>> findCycles() {
        return explorer.findAllCycles(graph, Color.RED).stream().filter(this::containsAllCubes).collect(Collectors.toList());
    }


    public Collection<Collection<GraphBase<Color, OppositeFaces>>> resolveByTrails() {
        return distinctResolutions(findTrails());
    }

    private List<List<OppositeFaces>> findTrails() {
        return explorer.findAllTrails(graph, Color.RED, null).stream().filter(t -> t.size() == 4).filter(this::containsAllColors).filter(this::containsAllCubes).collect(Collectors.toList());
    }
    
    private Collection<Collection<GraphBase<Color, OppositeFaces>>> distinctResolutions(List<List<OppositeFaces>> trails) {
        Collection<Collection<GraphBase<Color, OppositeFaces>>> resolutions = new ArrayList<>();
         for (int i = 0; i < trails.size() - 1; ++i) {
            for (int j = i + 1; j < trails.size(); ++j) {
                if (distinct(trails.get(i), trails.get(j))) {
                    Collection<GraphBase<Color, OppositeFaces>> resolution = new ArrayList<>();
                    resolution.add(new MutableGraph<>(trails.get(i)));
                    resolution.add(new MutableGraph<>(trails.get(j)));
                    resolutions.add(resolution);
                }
            }
        }
        findPaths();
        return resolutions;
    }


    private List<List<OppositeFaces>> findPaths() {
        System.out.println("__________");
        List<List<OppositeFaces>> paths = new ArrayList<>();
        for (Color color : new Color[] { Color.RED, Color.WHITE, Color.BLUE, Color.YELLOW }) {
            explorer.findAllPaths(graph, color, null).stream().filter(path -> path.size() == 4).forEach(path -> {
                print(path);
                paths.add(path);
            });
        }
        System.out.println("^^^^^^^^^");
        return paths;
    }


    private boolean containsAllCubes(Collection<OppositeFaces> trail) {
        BitSet cubes = new BitSet();
        trail.forEach(faces -> cubes.set(faces.cube));
        return cubes.cardinality() == 4;
    }


    private boolean containsAllColors(Collection<OppositeFaces> trail) {
        Set<Color> colors = new HashSet<>();
        trail.forEach(faces -> colors.addAll(faces.getVertices()));
        return colors.size() == 4;
    }


    private static boolean distinct(Collection<OppositeFaces> first, Collection<OppositeFaces> second) {
        for (OppositeFaces faces : first) {
            if (second.contains(faces)) {
                return false;
            }
        }
        return true;
    }


    private static void print(Collection<OppositeFaces> trail) {
        trail.forEach(faces -> System.out.println(faces));
        System.out.println("------");
    }


    private static class OppositeFaces extends UndirectedEdge<Color> {

        OppositeFaces(Color face1, Color face2, int cube) {
            super(face1, face2);
            this.cube = cube;
        }

        @Override
        public String toString() {
            return Integer.toString(cube) + ": " + super.toString();
        }

        @Override
        protected String toString(Color color) {
            if (Color.RED.equals(color)) {
                return "Red";
            }
            if (Color.WHITE.equals(color)) {
                return "White";
            }
            if (Color.BLUE.equals(color)) {
                return "Blue";
            }
            if (Color.YELLOW.equals(color)) {
                return "Yellow";
            }
            throw new IllegalArgumentException(color.toString());
        }

        final int cube;

    }


    private final MutableGraph<Color, OppositeFaces> graph = new MutableGraph<>();
    private final GraphExplorer<Color, OppositeFaces> explorer = new GraphExplorer<>(new RecursiveTrailFinder<>());

}
