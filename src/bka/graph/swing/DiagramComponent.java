/*
** Copyright © Bart Kampers
*/

package bka.graph.swing;

import bka.graph.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


class DiagramComponent extends JComponent {

    
    protected DiagramComponent(GraphEditor editor) {
        this.editor = editor;
        setSize(0, 0);
        addMouseListener(MOUSE_ADAPTER);
        addMouseMotionListener(MOUSE_ADAPTER);
        addKeyListener(KEY_ADAPTER);
    }
    
    
    protected DiagramComponent(GraphEditor editor, DiagramPage page) {
        this(editor);
        setTitle(page.getTitle());
        setVertexPictures(page.getVertices());
        setEdgePictures(page.getEdges());
    }


    public void paint(Graphics g) {
//        ArrayList<EdgePicture> notVisited = new ArrayList<EdgePicture>(edges);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        for (AbstractPicture picture : pictures) {
            if (picture == highlightedPicture) {
                Color drawColor = picture.getDrawColor();
                Stroke normalStroke = g2d.getStroke();
                BasicStroke highlightStroke = new BasicStroke(2.0f);
                g2d.setStroke(highlightStroke);
                picture.setDrawColor(new Color(0, 255, 0, 127));
                picture.paint(g2d);
                picture.setDrawColor(drawColor);
                g2d.setStroke(normalStroke);
            }
            else if (picture == selectedPicture) {
                Color drawColor = picture.getDrawColor();
                picture.setDrawColor(Color.BLUE);
                picture.paint(g2d);
                picture.setDrawColor(drawColor);
            }
            else {
                picture.paint(g2d);
            }
        }
//        for (VertexPicture vertexPicture : vertices) {
//            if (vertexPicture == highlightedPicture) {
//                Color drawColor = vertexPicture.getDrawColor();
//                Stroke normalStroke = g2d.getStroke();
//                BasicStroke highlightStroke = new BasicStroke(2.0f);
//                g2d.setStroke(highlightStroke);
//                vertexPicture.setDrawColor(new Color(0, 255, 0, 127));
//                vertexPicture.paint(g2d);
//                vertexPicture.setDrawColor(drawColor);
//                g2d.setStroke(normalStroke);
//            }
//            else if (vertexPicture == selectedPicture) {
//                Color drawColor = vertexPicture.getDrawColor();
//                vertexPicture.setDrawColor(Color.BLUE);
//                vertexPicture.paint(g2d);
//                vertexPicture.setDrawColor(drawColor);
//            }
//            else {
//                vertexPicture.paint(g2d);
//            }
//            
//            //* Paint edge pictures together with their top most vertex
//            for (EdgePicture edgePicture : edges) {
//                VertexPicture origin = edgePicture.getOriginPicture();
//                VertexPicture terminus = edgePicture.getTerminusPicture();
//                if (origin == vertexPicture || terminus == vertexPicture) {
//                    boolean removed = notVisited.remove(edgePicture);
//                    if (! removed || origin == terminus) {
//                        if (edgePicture == selectedPicture) {
//                            Color drawColor = edgePicture.getDrawColor();
//                            edgePicture.setDrawColor(Color.BLUE);
//                            edgePicture.paint(g2d);
//                            edgePicture.setDrawColor(drawColor);
//                        }
//                        else if (hoverInfo != null && hoverInfo.picture == edgePicture) {
//                            Color drawColor = edgePicture.getDrawColor();
//                            edgePicture.setDrawColor(Color.GREEN);
//                            edgePicture.paint(g2d);
//                            edgePicture.setDrawColor(drawColor);
//                        }
//                        else {
//                            edgePicture.paint(g2d);
//                        }
//                    }
//                    if (edgePicture.getTerminusPicture() == null) {
//                        //* New edge picture being dragged
//                        Color drawColor = edgePicture.getDrawColor();
//                        edgePicture.setDrawColor(Color.GRAY);
//                        edgePicture.paint(g2d);
//                        edgePicture.setDrawColor(drawColor);
//                    }
//                }
//            }
//        }
        if (attachmentPoint != null) {
            g2d.setColor(attachmentPointColor);
            g2d.fillOval(attachmentPoint.x - attachmentPointWidth / 2, attachmentPoint.y - attachmentPointHeight / 2, attachmentPointWidth, attachmentPointHeight);
        }
    }
    
    
    protected final String getTitle() {
        return title;
    }

    
    protected final void setTitle(String title) {
        this.title = title;
    }

    
    protected final ArrayList<VertexPicture> getVertexPictures() {
        ArrayList<VertexPicture> vertices = new ArrayList<VertexPicture>();
        for (AbstractPicture picture : pictures) {
            if (picture instanceof VertexPicture) {
                vertices.add((VertexPicture) picture);
            }
        }
        return vertices;
    }
    
    
    protected final void setVertexPictures(Collection<VertexPicture> vertexPictures) {
        if (vertexPictures != null) {
            pictures.addAll(vertexPictures);
//            vertices.addAll(pictures);
            int southMost = Integer.MIN_VALUE;
            int eastMost = Integer.MIN_VALUE;
            for (AbstractPicture picture : pictures) {
                southMost = Math.max(picture.ySouth(), southMost);
                eastMost = Math.max(picture.xEast(), eastMost);
            }
            setComponentSize(eastMost, southMost);
        }
    }


    protected final ArrayList<EdgePicture> getEdgePictures() {
        ArrayList<EdgePicture> edges = new ArrayList<EdgePicture>();
        for (AbstractPicture picture : pictures) {
            if (picture instanceof EdgePicture) {
                edges.add((EdgePicture) picture);
            }
        }
        return edges;
    }

    
    protected final void setEdgePictures(Collection<EdgePicture> edgePictures) {
        if (edgePictures != null) {
//            edges.addAll(pictures);
//            int southMost = Integer.MIN_VALUE;
//            int eastMost = Integer.MIN_VALUE;
            for (EdgePicture picture : edgePictures) {
                addEdgePicture(picture);
            }
//                southMost = Math.max(picture.ySouth(), southMost);
//                eastMost = Math.max(picture.xEast(), eastMost);
//                for (int i = paintOrder.size() - 1; i >= 0;  --i) {
//                    AbstractPicture abstractPicture = paintOrder.get(i);
//                    if (abstractPicture instanceof VertexPicture && (picture.getOriginPicture() == abstractPicture || picture.getTerminusPicture() == abstractPicture)) {
//                        paintOrder.add(i, picture);
//                        break;
//                    }
//                }
//            }
//            setComponentSize(eastMost, southMost);
        }
    }
    
    
    protected final void addEdgePicture(EdgePicture edgePicture) {
        for (int i = pictures.size() - 1; i >= 0;  --i) {
            AbstractPicture abstractPicture = pictures.get(i);
            if (edgePicture.getOriginPicture() == abstractPicture || edgePicture.getTerminusPicture() == abstractPicture) {
                pictures.add(i, edgePicture);
                setComponentSize(edgePicture.xEast(), edgePicture.ySouth());
                break;
            }
        }
    }

    
    protected void setHighlighted(VertexPicture picture) {
        highlightedPicture = picture;
    }
    
    
    protected VertexPicture getHighlighted() {
        return highlightedPicture;
    }
    

    protected void addVertex(VertexPicture picture) {
//        vertices.add(picture);
        pictures.add(picture);
        setComponentSize(picture.xEast(), picture.ySouth());
    }
    
    
    protected VertexPicture findContainer(VertexPicture vertexPicture) {
        int index = pictures.indexOf(vertexPicture);
        while (index > 0) {
            index--;
            AbstractPicture picture = pictures.get(index);
            if (picture instanceof VertexPicture && picture.isLocatedAt(vertexPicture.getLocation())) {
                return (VertexPicture) picture;
            }
        }
        return null;
    }
    
    
    protected ArrayList<VertexPicture> containerPath(VertexPicture vertexPicture) {
        ArrayList<VertexPicture> path = new ArrayList<VertexPicture>();
        while (vertexPicture != null) {
            VertexPicture container = findContainer(vertexPicture);
            if (container != null) {
                path.add(container);
            }
            vertexPicture = container;
        }
        return path;
    }
    
    
    protected void clearHoverInfo() {
        //Rectangle repaintRectangle = hoverInfo.picture.rectangle();
        hoverInfo = null;
        repaint(/*repaintRectangle*/);
    }

    
    protected void setSelected(AbstractPicture picture) {
        selectedPicture = picture;
    }
    
    
    void addVertexPictureCopy(VertexPicture vertexPicture, Point locationOnScreen) {
        assert(! pictures.contains(vertexPicture));
        try {
            Point diagramLocation = getLocationOnScreen();
            VertexPicture copy = (VertexPicture) vertexPicture.getClass().newInstance();
            copy.setVertex(vertexPicture.getVertex());
            copy.setLocation(new Point(locationOnScreen.x - diagramLocation.x, locationOnScreen.y - diagramLocation.y));
            copy.setSize(new Dimension(vertexPicture.getSize()));
            vertexPictureAdded(copy);
        }
        catch (Exception ex) { // InstantiationException, IllegalAccessException
            ex.printStackTrace(System.err);
        }
    }
    
    
    private void vertexPictureClicked(VertexPicture vertexPicture, int count) {
        if (count == 1) {
            setSelected(vertexPicture);
        }
        else {
            vertexPanel = vertexPicture.getEditPanel();
            if (vertexPanel != null) {
                openDialog(vertexPicture.vertex);
                editor.vertexPictureModified(vertexPicture);
            }                
        }
        editor.vertexPictureClicked(vertexPicture, count);
    }
    
    
    private void addNewVertexPicture(Class vertexPictureClass, Point point) {
        try {
            VertexPicture vertexPicture = (VertexPicture) vertexPictureClass.newInstance();
            vertexPicture.init(point);
            setVertexLocation(vertexPicture, point);
            vertexPictureAdded(vertexPicture);
        }
        catch (Exception ex) { // InstantiationException, IllegalAccessException
            ex.printStackTrace(System.err);
        }
    }
    
    
    private void vertexPictureAdded(VertexPicture vertexPicture) {
        addVertex(vertexPicture);
        setComponentSize(vertexPicture.xEast(), vertexPicture.ySouth());
        selectedPicture = vertexPicture;
        repaint(/*vertexPicture.rectangle()*/);
        editor.vetrexPictureAdded(vertexPicture);
    }
    
    
    private void startDrag(Point point) {
        dragInfo = new DragInfo();
        dragInfo.startPoint = point;
        Class edgePictureClass = editor.selectedEdgePictureClass();
        if (edgePictureClass != null) {
            prepareEdgeDragging(edgePictureClass);
        }
        else {
            dragInfo.edge = getEdgePicture(dragInfo.startPoint);
            if (dragInfo.edge != null) {
                initializeEdgeDragging();
            }
            else {
                dragInfo.vertex = getVertexPicture(dragInfo.startPoint);
                if (dragInfo.vertex != null) {
                    initializeVertexDragging();
                }
            }
        }
        requestFocus();
    }

    
    private void dragNewEdge(Class edgePictureClass, VertexPicture originPicture, Point point) {
        try {
            dragInfo.edge = (EdgePicture) edgePictureClass.newInstance();
            dragInfo.edge.setOrigin(originPicture, point);
            dragInfo.edge.setEndPoint(point);
            //edges.add(dragInfo.edge);
            addEdgePicture(dragInfo.edge);
            selectedPicture = dragInfo.edge;
            repaint(/*dragInfo.edge.rectangle()*/);
        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    
    private void edgePictureClicked(EdgePicture edgePicture) {
        setSelected(edgePicture);
    }
    
    
    private void cleanupEdges() {
        for (EdgePicture edgePicture : getEdgePictures()) {
            if (edgePicture.getOriginPicture() == dragInfo.vertex || edgePicture.getTerminusPicture() == dragInfo.vertex) {
                edgePicture.cleanup();
            }
        }
    }
    

    private void prepareEdgeDragging(Class edgePictureClass) {
        VertexPicture vertexPicture = getVertexPicture(dragInfo.startPoint);
        if (vertexPicture != null) {
            if (dragInfo.edge != null) {
                dragInfo.edge.setTerminus(vertexPicture, dragInfo.startPoint);
            }
            else {
                dragNewEdge(edgePictureClass, vertexPicture, dragInfo.startPoint);
            }
        }
    }
    
    
    private void initializeEdgeDragging() {
        if (dragInfo.edge.getTerminusPicture() != null) {
            dragInfo.edge.selectDragPoint(dragInfo.startPoint);
        }
    }
    
    
    private void dragEdge(Point point) {
//        Rectangle repaintRectangle = dragInfo.edge.rectangle();
        if (dragInfo.edge.hasDragPoint()) {
            dragInfo.edge.setDragLocation(point);
        }
        else {
            VertexPicture vertexPicture = getVertexPicture(point);
            if (vertexPicture != null) {
                attachmentPoint = vertexPicture.nearestAttachmentPoint(point);
//                repaintRectangle.add(new Rectangle(attachmentPoint.x - attachmentPointWidth / 2, attachmentPoint.y - attachmentPointHeight / 2, attachmentPointWidth, attachmentPointHeight));
            }
            else if (attachmentPoint != null) {
//                repaintRectangle.add(new Rectangle(attachmentPoint.x - attachmentPointWidth / 2, attachmentPoint.y - attachmentPointHeight / 2, attachmentPointWidth, attachmentPointHeight));
                attachmentPoint = null;
            }
            if (dragInfo.edge.getTerminusPicture() == null) {
                dragInfo.edge.setEndPoint(point);
            }
        }
        setComponentSize(dragInfo.edge.xEast(), dragInfo.edge.ySouth());
//        repaintRectangle.add(dragInfo.edge.rectangle());
        repaint(/*repaintRectangle*/);
    }
    
    
    private void finishEdgeDragging(Point point) {
        if (dragInfo.edge.hasDragPoint()) {
            dragInfo.edge.finishDrag();
        }
        else {
            VertexPicture terminusPicture = getVertexPicture(point);
            if (terminusPicture != null) {
                int terminusAttachmentIndex = terminusPicture.nearestAttachmentIndex(point);
                if (! dragInfo.edge.hasOrigin(terminusPicture, terminusAttachmentIndex)) {
                    dragInfo.edge.setTerminus(terminusPicture, terminusAttachmentIndex);
                }
                else {
                    pictures.remove(dragInfo.edge);
                }
            }
            else {
                pictures.remove(dragInfo.edge);
            }
        }
    }
    
    
    private void initializeVertexDragging() {
        assert hoverInfo != null;
        if (hoverInfo.location == VertexPicture.INTERIOR) {
            dragInfo.distance = new Point(dragInfo.vertex.getLocation().x - dragInfo.startPoint.x, dragInfo.vertex.getLocation().y - dragInfo.startPoint.y);
            ArrayList<VertexPicture> contained = allContainedVertices(dragInfo.vertex);
            ensureDrawnLast(dragInfo.vertex);
            for (VertexPicture picture : contained) {
                ensureDrawnLast(picture);
            }
        }
    }

    
    private void dragVertex(Point point) {
//        Rectangle repaintRectangle = rectangle(dragInfo.vertex);
        if (hoverInfo.location == VertexPicture.INTERIOR) {
            setCursor(Cursor.MOVE_CURSOR);
            moveDraggingVertexPicture(new Point(point.x + dragInfo.distance.x, point.y + dragInfo.distance.y));
        }
        else {
            dragInfo.vertex.resize(hoverInfo.location, point);
            correctEndPoints(dragInfo.vertex);
        }
        if (attachmentPoint != null) {
//            repaintRectangle.add(new Rectangle(attachmentPoint.x - attachmentPointWidth / 2, attachmentPoint.y - attachmentPointHeight / 2, attachmentPointWidth, attachmentPointHeight));
            attachmentPoint = null;
        }
//        repaintRectangle.add(rectangle(dragInfo.vertex));
        repaint(/*repaintRectangle*/);
    }
    
    
    private void moveDraggingVertexPicture(Point point) {
        Point containerPoint = dragInfo.vertex.getLocation();
        int δx = point.x - containerPoint.x;
        int δy = point.y - containerPoint.y;
        setVertexLocation(dragInfo.vertex, point);
        ArrayList<VertexPicture> vertices = getVertexPictures();
        ArrayList<EdgePicture> edges = getEdgePictures();
        boolean containerPassed = false;
        for (VertexPicture vertexPicture : vertices) {
            if (containerPassed) {
                Point containedLocation = new Point(vertexPicture.getLocation());
                containedLocation.x += δx;
                containedLocation.y += δy;
                setVertexLocation(vertexPicture, containedLocation);
                for (EdgePicture edgePicture : edges) {
                    if (edgePicture.getOriginPicture() == vertexPicture) {
                        ArrayList containers = containerPath(edgePicture.getTerminusPicture());
                        if (containers.contains(dragInfo.vertex)) {
                            edgePicture.move(δx, δy);
                        }
                    }
                }
            }
            else if (vertexPicture == dragInfo.vertex) {
                containerPassed = true;
            }
        }
    }
    

    private void setToolTip(AbstractPicture picture) {
        if (picture instanceof VertexPicture) {
            setToolTipText(editor.toolTipText((VertexPicture) picture));
        }
        else if (picture instanceof EdgePicture) {
            setToolTipText(editor.toolTipText((EdgePicture) picture));
        }
        else {
            setToolTipText(null);
        }
    }
    
    
    private void setDiagramCursor(AbstractPicture picture, Point point) {
        if (picture instanceof VertexPicture) {
            setVertexCursor();
        }
        else if (picture instanceof EdgePicture) {
            setCursor(Cursor.HAND_CURSOR);
        }
        else {
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }
    
    
    private void setAttachmentPoint(AbstractPicture picture, Point point) {
        if (picture instanceof VertexPicture && editor.selectedEdgePictureClass() != null) {
            attachmentPoint = ((VertexPicture) picture).nearestAttachmentPoint(point);
            repaint(/*new Rectangle(attachmentPoint.x - attachmentPointWidth / 2, attachmentPoint.y - attachmentPointHeight / 2, attachmentPointWidth, attachmentPointHeight)*/);
        }
        else if (attachmentPoint != null) {
//            Rectangle repaintRectangle = new Rectangle(attachmentPoint.x - attachmentPointWidth / 2, attachmentPoint.y - attachmentPointHeight / 2, attachmentPointWidth, attachmentPointHeight);
            attachmentPoint = null;
            repaint(/*repaintRectangle*/);
        }
    }
    
    
    private void setVertexLocation(VertexPicture vertexPicture, Point point) {
        vertexPicture.setLocation(point);
        correctEndPoints(vertexPicture);
        setComponentSize(vertexPicture.xEast(), vertexPicture.ySouth());
    }
    
    
    private void correctEndPoints(VertexPicture vertexPicture) {
        for (EdgePicture edgePicture : getEdgePictures()) {
            if (edgePicture.getOriginPicture() == vertexPicture || edgePicture.getTerminusPicture() == vertexPicture) {
                edgePicture.correctEndPoint(vertexPicture);
            }
        }
    }
    
    
    private Rectangle rectangle(VertexPicture vertexPicture) {
        Rectangle rectangle = vertexPicture.rectangle();
        rectangle.width += 1;
        rectangle.height += 1;
        ArrayList<VertexPicture> children = allContainedVertices(vertexPicture);
        for (EdgePicture edgePicture : getEdgePictures()) {
            VertexPicture origin = edgePicture.getOriginPicture();
            VertexPicture terminus = edgePicture.getTerminusPicture();
            if (origin == vertexPicture || terminus == vertexPicture || children.contains(origin) || children.contains(terminus)) {
                rectangle.add(edgePicture.rectangle());
            }
        }
        return rectangle;
    }
    
    
    private void openDialog(Vertex vertex) {
        vertexPanel.setEnvironment(editor); 
        String dialogTitle = vertex.getClass().getName();
        int nameIndex = dialogTitle.lastIndexOf('.') + 1;
        if (0 < nameIndex && nameIndex < dialogTitle.length()) {
            dialogTitle = dialogTitle.substring(nameIndex);
        }
        String name = vertex.getName();
        if (name != null) {
            dialogTitle += ": " + name;
        }
        EditDialog dialog = new EditDialog(editor, dialogTitle, vertexPanel);
        dialog.setVisible(true);
        repaint(); // Any picture might be changed, repaint entire diagram.
    }
    
    
    private void diagramClicked(int count, Point point) {
        Class vertexPictureClass = editor.selectedVertexPictureClass();
        if (count == 1 && vertexPictureClass != null) {
            addNewVertexPicture(vertexPictureClass, point);
        }
        else {
            VertexPicture vertexPicture = getVertexPicture(point);
            if (vertexPicture != null) {
                vertexPictureClicked(vertexPicture, count);
            }
            else {
                EdgePicture edgePicture = getEdgePicture(point);
                if (edgePicture != null) {
                    edgePictureClicked(edgePicture);
                }
            }
        }
    }

    
    private void hoverDiagram(Point point) {
        boolean needRepaint = false;
//        Rectangle repaintRectangle = new Rectangle();
        if (hoverInfo != null && hoverInfo.picture instanceof EdgePicture) {
            ((EdgePicture) hoverInfo.picture).setHoverPoint(null);
            needRepaint = true;
//            repaintRectangle.add(hoverInfo.picture.rectangle());
        }
        int location = -1;
        AbstractPicture picture = getEdgePicture(point);
        if (picture == null) {
            picture = getVertexPicture(point);
            if (picture != null) {
                location = ((VertexPicture) picture).locationOf(point);
            }
        }
        else {
            ((EdgePicture) picture).setHoverPoint(point);
            needRepaint = true;
//            repaintRectangle.add(picture.rectangle());
        }
        if (picture != null) {
            if (hoverInfo == null) {
                hoverInfo = new HoverInfo();
            }
            hoverInfo.picture = picture;
            hoverInfo.location = location;
        }
        else {
            hoverInfo = null;
        }
        if (editor.selectedEdgePictureClass() == null) {
            setDiagramCursor(picture, point);
        }
        else {
            setAttachmentPoint(picture, point);
            needRepaint = true;
//            if (picture != null) {
//                repaintRectangle.add(picture.rectangle());
//            }
        }
        setToolTip(picture);
        if (needRepaint/*! repaintRectangle.isEmpty()*/) {
            repaint(/*repaintRectangle*/);
        }
    }

    
    private void popupContextMenu(Point point) {
        EdgePicture edgePicture = getEdgePicture(point);
        if (edgePicture != null) {
            JPopupMenu menu = editor.getEdgeMenu(edgePicture);
            menu.show(this, point.x, point.y);
        }
    }
    
    
    /**
     * @param point
     * @return Top most VertexPicture with point inside.
     */
    private VertexPicture getVertexPicture(Point point) {
        int i = pictures.size() - 1;
        while (i >= 0) {
            AbstractPicture picture = pictures.get(i);
            if (picture instanceof VertexPicture) {
                int location = ((VertexPicture) picture).locationOf(point);
                if (location != VertexPicture.EXTERN) {
                    return (VertexPicture) picture;
                }
            }
            --i;
        }
        return null;
    }
    
    
    private EdgePicture getEdgePicture(Point point) {
        for (AbstractPicture picture : pictures) {
            if (picture instanceof EdgePicture && picture.isLocatedAt(point)) {
                return (EdgePicture) picture;
            }
        }
        return null;
    }
    
    
    private void ensureDrawnLast(VertexPicture vertexPicture) {
//        vertices.remove(vertexPicture);
//        vertices.add(vertexPicture);
        ArrayList<AbstractPicture> picturesToMove = new ArrayList<AbstractPicture>();
        picturesToMove.add(vertexPicture);
        for (AbstractPicture picture : pictures) {
            if (picture instanceof EdgePicture && (((EdgePicture) picture).getOriginPicture() == vertexPicture || ((EdgePicture) picture).getTerminusPicture() == vertexPicture)) {
                picturesToMove.add(picture);
            }
        }
        pictures.removeAll(picturesToMove);
        pictures.addAll(picturesToMove);
    }
    
    
    private ArrayList<VertexPicture> containedVertices(VertexPicture container) {
        ArrayList<VertexPicture> contained = new ArrayList<VertexPicture>();
        for (AbstractPicture picture : pictures) {
            if (picture instanceof VertexPicture && findContainer((VertexPicture) picture) == container) {
                contained.add((VertexPicture) picture);
            }
        }
        return contained;
    }

    
    private ArrayList<VertexPicture> allContainedVertices(VertexPicture container) {
        ArrayList<VertexPicture> all = new ArrayList<VertexPicture>();
        ArrayList<VertexPicture> contained = containedVertices(container);
        all.addAll(contained);
        for (VertexPicture vertex : contained) {
            all.addAll(allContainedVertices(vertex));
        }
        return all;
    }

    
    private void setComponentSize(int width, int height) {
        Dimension dimension = getSize();
        dimension.width = Math.max(dimension.width, width);
        dimension.height = Math.max(dimension.height, height);
        setPreferredSize(dimension);
        setSize(dimension);
    }
    
    
    private void setCursor(int type) {
        if (getCursor().getType() != type) {
            setCursor(new Cursor(type));
        }
    }
    
    
    private void setVertexCursor() {
        switch (hoverInfo.location) {
            case VertexPicture.INTERIOR   : setCursor(Cursor.HAND_CURSOR);      break;
            case VertexPicture.NORTH      : setCursor(Cursor.N_RESIZE_CURSOR);  break;
            case VertexPicture.SOUTH      : setCursor(Cursor.S_RESIZE_CURSOR);  break;
            case VertexPicture.WEST       : setCursor(Cursor.W_RESIZE_CURSOR);  break;
            case VertexPicture.EAST       : setCursor(Cursor.E_RESIZE_CURSOR);  break;
            case VertexPicture.NORTH_WEST : setCursor(Cursor.NW_RESIZE_CURSOR); break;
            case VertexPicture.NORTH_EAST : setCursor(Cursor.NE_RESIZE_CURSOR); break;
            case VertexPicture.SOUTH_WEST : setCursor(Cursor.SW_RESIZE_CURSOR); break;
            case VertexPicture.SOUTH_EAST : setCursor(Cursor.SE_RESIZE_CURSOR); break;                
        }
    }
    
    
    private class DragInfo {
        VertexPicture vertex = null;
        EdgePicture edge = null;
        Point startPoint = null;
        Point distance = null;
    }
    
    
    private class HoverInfo {
        AbstractPicture picture = null;
        int location = Integer.MIN_VALUE;
    }
    
    
    private final MouseAdapter MOUSE_ADAPTER = new MouseAdapter() {

        public void mouseClicked(MouseEvent evt) {
            switch (evt.getButton()) {
                case MouseEvent.BUTTON1:
                    diagramClicked(evt.getClickCount(), evt.getPoint());
                    break;
                case MouseEvent.BUTTON3:
                    popupContextMenu(evt.getPoint());
                    break;
            }
        }
        
        public void mousePressed(MouseEvent evt) {
            if (evt.getButton() == MouseEvent.BUTTON1) {
                startDrag(evt.getPoint());
            }
        }
        
        public void mouseReleased(MouseEvent evt) {
            if (evt.getButton() == MouseEvent.BUTTON1) {
                Point point = evt.getPoint();
                if (dragInfo != null) {
                    if (dragInfo.vertex != null) {
                        cleanupEdges();
                    }
                    if (dragInfo.edge != null) {
                        finishEdgeDragging(point);
                    }
                    dragInfo = null;
                    setCursor(Cursor.DEFAULT_CURSOR);
                    repaint(); // Selection might be changed, repaint entire diagram;
                }
            }
        }
        
        public void mouseMoved(MouseEvent evt) {
            hoverDiagram(evt.getPoint());
        }
        
        public void mouseDragged(MouseEvent evt) {
            assert dragInfo != null;
            if (dragInfo.vertex != null) {
                dragVertex(evt.getPoint());
            }
            else if (dragInfo.edge != null) {
                dragEdge(evt.getPoint());
            }
        }
        
        public void mouseEntered(MouseEvent evt) {
            editor.diagramEntered(evt);
        }

        public void mouseExited(MouseEvent evt) {
            editor.diagramExited();
        }

    };
    
    
    private KeyAdapter KEY_ADAPTER = new KeyAdapter() {

        public void keyReleased(KeyEvent evt) {
            if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
//                ArrayList<VertexPicture> verticesToDelete = new ArrayList<VertexPicture>();
//                ArrayList<EdgePicture> edgesToDelete = new ArrayList<EdgePicture>();
                ArrayList<AbstractPicture> picturesToDelete = new ArrayList<AbstractPicture>();
                for (AbstractPicture picture : pictures) {
                    if (picture == selectedPicture) {
                        picturesToDelete.add(picture);
                        if (picture instanceof VertexPicture) {
                            editor.vertexPictureRemoved((VertexPicture) picture);
                        }
                    }
                }
//                for (EdgePicture picture : edges) {
//                    if (picture == selectedPicture || picture.getOriginPicture() == selectedPicture || picture.getTerminusPicture() == selectedPicture) {
//                        edgesToDelete.add(picture);
//                    }
//                }
//                vertices.removeAll(verticesToDelete);
//                edges.removeAll(edgesToDelete);
                pictures.removeAll(picturesToDelete);
                repaint();
            }
       }

    };

    
    private GraphEditor editor;

    private String title = null;
    
//    private ArrayList<EdgePicture> edges = new ArrayList<EdgePicture>();
//    private ArrayList<VertexPicture> vertices = new ArrayList<VertexPicture>();
    
    private ArrayList<AbstractPicture> pictures = new ArrayList<AbstractPicture>();

//    public ArrayList<AbstractPicture> getPictures() {
//        return pictures;
//    }
//
//    public void setPictures(ArrayList<AbstractPicture> pictures) {
//        this.pictures = pictures;
//    }

    private AbstractPicture selectedPicture = null;

    private DragInfo dragInfo = null;     
    private HoverInfo hoverInfo = null;
    
    private VertexPicture highlightedPicture = null;

    private Point attachmentPoint = null;
    
    private Color attachmentPointColor = Color.RED;
    private int attachmentPointWidth = 4;
    private int attachmentPointHeight = 4;
    
    private AbstractEditPanel vertexPanel;    

}
