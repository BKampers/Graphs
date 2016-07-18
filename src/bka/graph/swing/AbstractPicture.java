/*
** Copyright © Bart Kampers
*/

package bka.graph.swing;


import java.awt.*;


abstract class AbstractPicture {
    
    
    public void setDrawColor(Color color) {
        if (color != null) {
            this.drawColor = color;
        }
        else {
            this.drawColor = DEFAULT_DRAW_COLOR;
        }
    }
    
    
    public Color getDrawColor() {
        return drawColor;
    }

    
    public Stroke getStroke() {
        return stroke;
    }

    
    public void setStroke(Stroke stroke) {
        if (stroke != null) {
            this.stroke = stroke;
        }
        else {
            this.stroke = DEFAULT_STROKE;
        }
    }

    
    public abstract void paint(Graphics2D g2d);
    public abstract boolean isLocatedAt(Point point);
        
    
    protected int squareDistance(Point p, Point q) {
        int δx = p.x - q.x;
        int δy = p.y - q.y;
        return δx*δx + δy*δy;
    }
    
    
    protected Rectangle rectangle() {
        int x = xWest();
        int y = yNorth();
        return new Rectangle(x, y, xEast() - x, ySouth() - y);
    }
    

    protected abstract int xWest();
    protected abstract int xEast();
    protected abstract int yNorth();
    protected abstract int ySouth();

    
    
    protected Color drawColor = DEFAULT_DRAW_COLOR;
    protected Stroke stroke = DEFAULT_STROKE;
            
    private static final Color DEFAULT_DRAW_COLOR = Color.BLACK;
    private static final Stroke DEFAULT_STROKE = new BasicStroke();
    
}
