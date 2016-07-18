package bka.graph;

public class Vertex {
    
    public Vertex() {
    }
    
    
    public Vertex(String name) {
        this.name = name;
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    

    public String toString() {
        if (name != null) {
            return name;
        }
        else {
            return super.toString();
        }
    }
    

    protected String name = null;
    
}
