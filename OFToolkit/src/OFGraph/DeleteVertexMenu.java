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
 * A class to implement the deletion of a vertex from within a MenuMousePlugin.
 * @author Dr. Greg M. Bernstein, modified by 164776
 * @param <V> A given vertex.
 */
public class DeleteVertexMenu<V> extends JMenuItem implements VertexMenuListener<V> {
    private V vertex;
    private VisualizationViewer vViewer;
    
    /** Creates a new instance of DeleteVertexMenuItem */
    public DeleteVertexMenu() {
        super("Delete Vertex");
        initComponents();
    }
    
    private void initComponents() {
        this.addActionListener((ActionEvent e) -> {
            vViewer.getPickedVertexState().pick(vertex, false);
            vViewer.getGraphLayout().getGraph().removeVertex(vertex);
            vViewer.repaint();
        });
    }

    /**
     * Implements the VertexMenuListener interface.
     * @param v A given vertex.
     * @param vViewer A visualization viewer.
     */
    @Override
    public void setVertexAndView(V v, VisualizationViewer vViewer) {
        this.vertex = v;
        this.vViewer = vViewer;
        this.setText("Delete " + v.toString());
    }
    
}