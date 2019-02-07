/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author 164776
 */
public class TopologyGraph extends javax.swing.JFrame {

    public JFrame parent;

    private static Graph graph;

    public TopologyGraph(JFrame parent) {
        this.parent = parent;
        setup();
    }

    private void setup() {
        // Graph<V, E> where V is the type of the vertices
        // and E is the type of the edges
        Graph<Integer, String> g = new SparseMultigraph<>();
        // Add some vertices. From above we defined these to be type Integer.
        g.addVertex((Integer) 1);
        g.addVertex((Integer) 2);
        g.addVertex((Integer) 3);
        // Add some edges. From above we defined these to be of type String
        // Note that the default is for undirected edges.
        g.addEdge("Edge-A", 1, 2); // Note that Java 1.5 auto-boxes primitives
        g.addEdge("Edge-B", 2, 3);

        Layout<Integer, String> layout = new CircleLayout(g);
        layout.setSize(new Dimension(300, 300));

        VisualizationViewer<Integer, String> vv;
        vv = new VisualizationViewer<>(layout);

        Transformer<Integer, Paint> vertexPaint = (Integer i) -> Color.GREEN;

        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer
                = (String s) -> edgeStroke;

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        this.getContentPane().add(vv);
        this.pack();
        this.setVisible(true);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                parent.setVisible(true);
            }
        });
    }
}
