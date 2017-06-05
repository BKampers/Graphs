/*
** Copyright © Bart Kampers
*/

package bka.graph.swing;


import bka.awt.*;
import java.awt.*;


public abstract class AbstractPicture {

    public static final String DRAW = "DRAW";
    public static final String FILL = "FILL";

    
    public abstract boolean isLocatedAt(Point point);
    public abstract Shape getShape();

    protected abstract int xWest();
    protected abstract int xEast();
    protected abstract int yNorth();
    protected abstract int ySouth();

   
    public void paint(Graphics2D g2d) {
        Shape shape = getShape();
        DrawStyle drawStyle = DrawStyleManager.getInstance().getDrawStyle(this);
        Paint paint = drawStyle.getPaint(FILL);
        if (paint != null) {
            g2d.setPaint(paint);
            g2d.fill(shape);
        }
        paint = drawStyle.getPaint(DRAW);
        if (paint != null) {
            g2d.setPaint(paint);
            Stroke stroke = drawStyle.getStroke(DRAW);
            g2d.setStroke(stroke);
            g2d.draw(shape);
        }
    }


    public String[] getConfigurablePaints() {
        return new String[] { };
    }


    public Rectangle bounds() {
        int x = xWest();
        int y = yNorth();
        return new Rectangle(x, y, xEast() - x, ySouth() - y);
    }

    
    protected static int squareDistance(Point p, Point q) {
        int δx = p.x - q.x;
        int δy = p.y - q.y;
        return δx*δx + δy*δy;
    }
    
    
}
