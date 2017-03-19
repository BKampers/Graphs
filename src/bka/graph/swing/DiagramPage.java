/*
** Copyright © Bart Kampers
*/

package bka.graph.swing;

import java.util.Collection;


public class DiagramPage {

    
    public DiagramPage() {
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
            if (picture.isLocatedAt(vertex.getLocation())) {
                return picture;
            }
        }
        return null;
    }


    private String title;
    private Collection<VertexPicture> vertices;
    private Collection<EdgePicture> edges;

}
