/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OFGraph;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;

/**
 * A class to implement the deletion of a link from within a 
 * PopupVertexLinkMenuMousePlugin.
 * @author Dr. Greg M. Bernstein, modified by 164776
 * @param <E> A given edge/link.
 */
public class DeleteLinkMenuItem<E> extends JMenuItem implements EdgeMenuListener<E> {
    private E edge;
    private VisualizationViewer vViewer;
    
    /** Creates a new instance of DeleteEdgeMenuItem */
    public DeleteLinkMenuItem() {
        super("Delete Link");
        initComponents();
        
    }
    
    private void initComponents() {
        this.addActionListener((ActionEvent e) -> {
            vViewer.getPickedEdgeState().pick(edge, false);
            vViewer.getGraphLayout().getGraph().removeEdge(edge);
            vViewer.repaint();
        });
    }

    /**
     * Implements the EdgeMenuListener interface to update the menu item with info
     * on the currently chosen edge.
     * @param edge The given edge.
     * @param vViewer The visualization viewer.
     */
    @Override
    public void setEdgeAndView(E edge, VisualizationViewer vViewer) {
        this.edge = edge;
        this.vViewer = vViewer;
        this.setText("Delete " + edge.toString());
    }
    
}