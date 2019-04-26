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
 * This class implements the delete link option in MenuMousePlugin.
 *
 * @author Dr. Greg M. Bernstein, modified by 164776
 * @param <E> link The given link.
 */
public class DeleteLinkMenu<E> extends JMenuItem implements LinkMenuListener<E> {

    private E link;
    private VisualizationViewer vViewer;

    /**
     * Creates a new instance of DeleteEdgeMenuItem
     */
    public DeleteLinkMenu() {
        super("Delete Link");
        initComponents();
    }

    private void initComponents() {
        this.addActionListener(
                (ActionEvent e) -> {
                    vViewer.getPickedEdgeState().pick(link, false);
                    vViewer.getGraphLayout().getGraph().removeEdge(link);
                    vViewer.repaint();
                }
        );
    }

    /**
     * Implements the LinkMenuListener interface to update the menu with the
     * given link.
     *
     * @param link The given link.
     * @param vViewer The visualization viewer from the JUNG library.
     */
    @Override
    public void setEdgeAndView(E link, VisualizationViewer vViewer) {
        this.link = link;
        this.vViewer = vViewer;
        this.setText("Delete " + link.toString());
    }

}
