/*
** Copyright © Bart Kampers
*/

package bka.graph.swing;

import java.util.*;


public class DiagramPage {

    
    public DiagramPage() {
    }


    public static DiagramPage createEmpty() {
        DiagramPage empty = new DiagramPage();
        empty.vertices = new ArrayList<>();
        empty.edges = new ArrayList<>();
        return empty;
    }
    
    
    public String getTitle() {
        return title;
    }

    
    public void setTitle(String title) {
        this.title = title;
    }

    
    public Collection<VertexPicture> getVertices() {
        return vertices;
    }

    
    public void setVertices(Collection<VertexPicture> vertices) {
        this.vertices = vertices;
    }

    
    public Collection<EdgePicture> getEdges() {
        return edges;
    }

    
    public void setEdges(Collection<EdgePicture> edges) {
        this.edges = edges;
    }
    
    
    public VertexPicture findContainer(VertexPicture vertex) {
        for (VertexPicture picture : vertices) {
            if (vertex != picture && picture.isLocatedAt(vertex.getLocation())) {
                return picture;
            }
        }
        return null;
    }


    private String title;
    private Collection<VertexPicture> vertices;
    private Collection<EdgePicture> edges;

}
