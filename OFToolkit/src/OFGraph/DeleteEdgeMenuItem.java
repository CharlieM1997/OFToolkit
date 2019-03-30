/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OFGraph;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * A class to implement the deletion of an edge from within a 
 * PopupVertexEdgeMenuMousePlugin.
 * @author Dr. Greg M. Bernstein
 */
public class DeleteEdgeMenuItem<E> extends JMenuItem implements EdgeMenuListener<E> {
    private E edge;
    private VisualizationViewer visComp;
    
    /** Creates a new instance of DeleteEdgeMenuItem */
    public DeleteEdgeMenuItem() {
        super("Delete Edge");
        this.addActionListener((ActionEvent e) -> {
            visComp.getPickedEdgeState().pick(edge, false);
            visComp.getGraphLayout().getGraph().removeEdge(edge);
            visComp.repaint();
        });
    }

    /**
     * Implements the EdgeMenuListener interface to update the menu item with info
     * on the currently chosen edge.
     * @param edge 
     * @param visComp 
     */
    public void setEdgeAndView(E edge, VisualizationViewer visComp) {
        this.edge = edge;
        this.visComp = visComp;
        this.setText("Delete " + edge.toString());
    }
    
}