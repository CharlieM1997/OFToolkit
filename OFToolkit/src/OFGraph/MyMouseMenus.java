/*
 * MyMouseMenus.java
 *
 * Created on March 21, 2007, 3:34 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */

package OFGraph;

import OFGraph.GraphElements;
import OFGraph.GraphElements.MyEdge;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * A collection of classes used to assemble popup mouse menus for the custom
 * edges and vertices developed in this example.
 * @author Dr. Greg M. Bernstein
 */
public class MyMouseMenus {
    
    public static class EdgeMenu extends JPopupMenu {        
        // private JFrame frame; 
        public EdgeMenu(final JFrame frame) {
            super("Edge Menu");
            // this.frame = frame;
            this.add(new DeleteLinkMenu<GraphElements.MyEdge>());        
        }
        
    }
    
    public static class EdgePropItem extends JMenuItem implements LinkMenuListener<OFGraph.GraphElements.MyEdge>,
            MenuPointListener {
        GraphElements.MyEdge edge;
        VisualizationViewer visComp;
        Point2D point;
        
        @Override
        public void setEdgeAndView(GraphElements.MyEdge edge, VisualizationViewer visComp) {
            this.edge = edge;
            this.visComp = visComp;
        }

        @Override
        public void setPoint(Point2D point) {
            this.point = point;
        }        
    }
    
    public static class VertexMenu extends JPopupMenu {
        public VertexMenu(final JFrame frame) {
            super("Vertex Menu");
            this.add(new VertexPropItem(frame));
            this.addSeparator();
            this.add(new DeleteVertexMenu<GraphElements.MyVertex>());
        }
    }
    
    public static class VertexPropItem extends JMenuItem implements VertexMenuListener<OFGraph.GraphElements.MyVertex>,
            MenuPointListener {
        GraphElements.MyVertex vertex;
        VisualizationViewer visComp;
        Point2D point;
        
        @Override
        public void setVertexAndView(GraphElements.MyVertex vertex, VisualizationViewer visComp) {
            this.vertex = vertex;
            this.visComp = visComp;
        }

        @Override
        public void setPoint(Point2D point) {
            this.point = point;
        }
        
        public VertexPropItem(final JFrame frame) {            
            super("Edit Properties...");
            this.addActionListener((ActionEvent e) -> {
                VertexPropertyDialog dialog = new VertexPropertyDialog(frame, vertex);
                dialog.setLocation((int)point.getX()+ frame.getX(), (int)point.getY()+ frame.getY());
                dialog.setVisible(true);
            });
        }
        
    }
    
    
}
