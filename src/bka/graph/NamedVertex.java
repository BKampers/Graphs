/*
** Copyright © Bart Kampers
*/

package bka.graph;


public class NamedVertex extends Vertex {


    @Override
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    private String name;

}
