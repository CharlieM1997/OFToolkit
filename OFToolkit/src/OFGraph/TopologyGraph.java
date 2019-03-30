/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OFGraph;

import OFGraph.GraphElements.MyVertex;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.FourPassImageShaper;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.BasicVertexRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author 164776
 */
public class TopologyGraph extends javax.swing.JFrame {

    public JFrame parent;
    private JRadioButtonMenuItem typeHost, typeSwitch;
    private SparseMultigraph g;
    private static TopologyGraph tpGraph;
    private static ControllerSetup cSetup;

    TopologyGraph(JFrame parent) {
        this.parent = parent;
        setup();
    }

    public static TopologyGraph openSharedGraph(JFrame parent) {
        if (tpGraph == null) {
            tpGraph = new TopologyGraph(parent);
        } else {
            tpGraph.setup();
        }
        return tpGraph;
    }

    public static TopologyGraph getSharedGraph() {
        try {
            return tpGraph;
        } catch (NullPointerException e) {
            System.err.println("TopologyGraph has not yet been initialised.");
            return null;
        }
    }

    private void setup() {
        this.setTitle("Topology Graph Editor");
        TopologyGraph.cSetup = null;
        SparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge> g
                = new SparseMultigraph<>();

        Map<Number, Icon> iconMap = new HashMap<>();
        Icon icon0 = new ImageIcon(TopologyGraph.class.getResource("/OFGraph/images/host.png"));
        iconMap.put(0, icon0);
        Icon icon1 = new ImageIcon(TopologyGraph.class.getResource("/OFGraph/images/switch.png"));
        iconMap.put(1, icon1);

        // Layout<V, E>, VisualizationViewer<V,E>
//        Map<GraphElements.MyVertex,Point2D> vertexLocations = new HashMap<GraphElements.MyVertex, Point2D>();
        Layout<GraphElements.MyVertex, GraphElements.MyEdge> layout = new StaticLayout(g);
        layout.setSize(new Dimension(300, 300));
        VisualizationViewer<GraphElements.MyVertex, GraphElements.MyEdge> vv
                = new VisualizationViewer<>(layout);
        vv.setPreferredSize(new Dimension(350, 350));
        // Show vertex and edge labels
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        final IconShapeTransformer<GraphElements.MyVertex> vertexIconShapeTransformer
                = new IconShapeTransformer<>();
        //final IconTransformer<GraphElements.MyVertex> iconTransformer = new IconTransformer<>();

        vertexIconShapeTransformer.setIconMap(iconMap);
        //iconTransformer.setIconMap(iconMap);

        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderContext().setVertexShapeTransformer(vertexIconShapeTransformer);
        //vv.getRenderContext().setVertexIconTransformer(iconTransformer);
        vv.getRenderer().setVertexRenderer(new BasicVertexRenderer<>());

        // Create a graph mouse and add it to the visualization viewer
        EditingModalGraphMouse gm = new EditingModalGraphMouse(vv.getRenderContext(),
                GraphElements.MyVertexFactory.getInstance(),
                GraphElements.MyEdgeFactory.getInstance());
        // Trying out our new popup menu mouse plugin...
        PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
        // Add some popup menus for the edges and vertices to our mouse plugin.
        JPopupMenu edgeMenu = new MyMouseMenus.EdgeMenu(this);
        JPopupMenu vertexMenu = new MyMouseMenus.VertexMenu(this);
        myPlugin.setEdgePopup(edgeMenu);
        myPlugin.setVertexPopup(vertexMenu);
        gm.remove(gm.getPopupEditingPlugin());  // Removes the existing popup editing plugin

        gm.add(myPlugin);   // Add our new plugin to the mouse

        vv.setGraphMouse(gm);

        //JFrame frame = new JFrame("Editing and Mouse Menu Demo");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().add(vv);

        // Let's add a menu for changing mouse modes
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu saveMenu = new JMenu("Save Graph");
        JMenuItem saveAs = new JMenuItem(new SaveAction(g));
        saveMenu.add(saveAs);
        menuBar.add(saveMenu);

        JMenu modeMenu = gm.getModeMenu();
        modeMenu.setText("Mouse Mode");
        modeMenu.setIcon(null); // I'm using this in a main menu
        modeMenu.setPreferredSize(new Dimension(80, 20)); // Change the size so I can see the text
        menuBar.add(modeMenu);

        JMenu typeMenu = new JMenu("Vertex Type");
        ButtonGroup types = new ButtonGroup();
        this.typeHost = new JRadioButtonMenuItem("Host");
        typeHost.setSelected(true);
        typeHost.setMnemonic(KeyEvent.VK_H);
        types.add(typeHost);
        this.typeSwitch = new JRadioButtonMenuItem("Switch");
        typeSwitch.setMnemonic(KeyEvent.VK_S);
        types.add(typeSwitch);
        typeMenu.add(typeHost);
        typeMenu.add(typeSwitch);
        menuBar.add(typeMenu);

        JMenu controllerMenu = new JMenu("Controller Setup");
        JMenuItem controller = new JMenuItem(new ControllerAction(g));
        controllerMenu.add(controller);
        menuBar.add(controllerMenu);

        gm.setMode(ModalGraphMouse.Mode.EDITING); // Start off in editing mode
        this.pack();
        this.setVisible(true);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                parent.setVisible(true);
            }
        });
    }

    Transformer<GraphElements.MyVertex, Paint> vertexColor = (GraphElements.MyVertex v) -> {
        if (v.getType().equals("Host")) {
            return new Color(0, 0, 0, 0);
        } //else if v.getType().equals("Switch));
        return Color.CYAN;
    };

    /**
     * public class IconTransformer<V> extends DefaultVertexIconTransformer<V> {
     *
     * ImageIcon icon; protected Map<Number, Icon> imgIconMap;
     *
     * public IconTransformer() { this.icon = null; }
     *
     * public Icon transform(GraphElements.MyVertex v) { if
     * (v.getType().equals("Host")) { return icon = new
     * ImageIcon(TopologyGraph.class.getResource("/OFGraph/images/host.png")); }
     * //else if (v.getType().equals("Switch")) return icon = new
     * ImageIcon(TopologyGraph.class.getResource("/OFGraph/images/switch.png"));
     * }
     *
     * @Override public void setIconMap(Map iconMap) { this.imgIconMap =
     * iconMap; } }
     */
    public class IconShapeTransformer<V> extends EllipseVertexShapeTransformer<V> {

        protected Map<Image, Shape> shapeMap = new HashMap<>();
        protected Map<Number, Icon> iconMap;
        protected Transformer<V, Shape> delegate;

        /**
         *
         *
         * @param delegate The delegate to set.
         */
        public IconShapeTransformer(Transformer<V, Shape> delegate) {
            this.delegate = delegate;
        }

        private IconShapeTransformer() {

        }

        /**
         * @return Returns the delegate.
         */
        public Transformer<V, Shape> getDelegate() {
            return delegate;
        }

        /**
         * @param delegate The delegate to set.
         */
        public void setDelegate(Transformer<V, Shape> delegate) {
            this.delegate = delegate;
        }

        @Override
        public Shape transform(V v) {
            //this is the sloppiest coding I've ever done but it works
            return myTransform((MyVertex) v);
        }

        public Shape myTransform(GraphElements.MyVertex v) {
            Icon icon = iconMap.get(v.getIconNum());
            if (icon instanceof ImageIcon) {
                Image image = ((ImageIcon) icon).getImage();
                Shape shape = (Shape) shapeMap.get(image);
                if (shape == null) {
                    shape = FourPassImageShaper.getShape(image, 32);
                    if (shape.getBounds().getWidth() > 0
                            && shape.getBounds().getHeight() > 0) {
                        // don't cache a zero-sized shape, wait for the image
                        // to be ready
                        int width = image.getWidth(null);
                        int height = image.getHeight(null);
                        AffineTransform transform = AffineTransform
                                .getTranslateInstance(-width / 2, -height / 2);
                        shape = transform.createTransformedShape(shape);

                        shapeMap.put(image, shape);
                    }
                }
                return shape;
            } else {
                return delegate.transform((V) v);
            }
        }

        public void setIconMap(Map iconMap) {
            this.iconMap = iconMap;
        }

    }

    public JRadioButtonMenuItem getTypeHost() {
        return typeHost;
    }

    public JRadioButtonMenuItem getTypeSwitch() {
        return typeSwitch;
    }

    public class SaveAction extends AbstractAction {

        private final Graph g;

        public SaveAction(Graph g) {
            super("Save As...");

            this.g = g;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            final FileFilter pyFilter = new FileNameExtensionFilter("Python file", "py");

            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(pyFilter);
            String name = "new";
            File output = new File(name + ".py");
            fileChooser.setSelectedFile(output);

            if (fileChooser.showSaveDialog(tpGraph) == JFileChooser.APPROVE_OPTION) {
                Collection<GraphElements.MyVertex> vertices = g.getVertices();
                Collection<GraphElements.MyEdge> links = g.getEdges();

                String script = "from mininet.net import Mininet\n"
                        + "from mininet.topo import Topo\n"
                        + "from mininet.node import Controller, RemoteController, OVSController\n"
                        + "from mininet.node import CPULimitedHost, Host, Node\n"
                        + "from mininet.node import OVSKernelSwitch\n"
                        + "from mininet.cli import CLI\n"
                        + "from mininet.log import setLogLevel, info\n"
                        + "from mininet.link import TCLink, Intf\n"
                        + "from subprocess import call\n"
                        + "\n"
                        + "class " + name + "Topo( Topo ):\n"
                        + "\tdef __init__( self ):\n"
                        + "\t\t# Initialize topology\n"
                        + "\t\tTopo.__init__( self )\n"
                        + "\t\t# Add hosts";

                for (GraphElements.MyVertex v : vertices) {
                    if (v.getType().equals("Host")) {
                        script = script.concat("\n\t\t"
                                + v.getName()
                                + " = self.addHost( '"
                                + v.getName()
                                + "'");
                        if (v.getIP() != null) {
                            script = script.concat(", ip='"
                                    + v.getIPtoString()
                                    + "'");
                        }
                        if (v.getMAC() != null) {
                            script = script.concat(", mac='"
                                    + v.getMAC()
                                    + "'");
                        }
                        script = script.concat(" )");
                    }
                }

                script = script.concat("\n\n\t\t# Add switches");

                for (GraphElements.MyVertex v : vertices) {
                    if (v.getType().equals("Switch")) {
                        script = script.concat("\n\t\t"
                                + v.getName()
                                + " = self.addSwitch( '"
                                + v.getName()
                                + "'");
                        if (v.getIP() != null) {
                            script = script.concat(", ip='"
                                    + v.getIPtoString()
                                    + "'");
                        }
                        if (v.getMAC() != null) {
                            script = script.concat(", mac='"
                                    + v.getMAC()
                                    + "'");
                        }
                        script = script.concat(" )");
                    }
                }

                script = script.concat("\n\n\t\t# Add links");

                for (GraphElements.MyEdge l : links) {
                    Pair<GraphElements.MyVertex> pair = g.getEndpoints(l);
                    script = script.concat("\n\t\t"
                            + "self.addLink( "
                            + pair.getFirst()
                            + ", "
                            + pair.getSecond()
                            + " )");
                }

                script = script.concat("\n\n\ntopos = {"
                        + "\n\t '"
                        + name
                        + "': ( lambda: "
                        + name
                        + "Topo() )\n}");

                script = script.concat("\n\ndef myNetwork():\n\n"
                        + "\tnet = Mininet( topo="
                        + name
                        + " )\n\n\tinfo ( '*** Adding controller\\n' )\n\t"
                        );
                if (ControllerSetup.getSetup() == null) {
                    script = script.concat("c0=net.addController(name='c0',"
                    + "\n\t\tcontroller=RemoteController,"
                    + "\n\t\tip='127.0.0.1',"
                    + "\n\t\tprotocol='tcp',"
                    + "\n\t\tport=6633)\n\t"
                    );
                }
                else {
                    cSetup = ControllerSetup.getSetup();
                   script = script.concat(cSetup.getcName()
                   + "=net.addController(name='" + cSetup.getcName()
                   + "',\n\t\t"
                   + "controller=" + cSetup.getcType()
                   + ",\n\t\t"
                   + "ip='" + cSetup.getIPAddress()
                   + "',\n\t\t"
                   + "protocol='" + cSetup.getProtocol()
                   + "',\n\t\t"
                   + "port=" + cSetup.getPortString()
                   + ")\n\tcmap = { ");
                   DefaultTableModel model = cSetup.getModel();
                   boolean one = false;
                   for (int i = 0; i < model.getRowCount(); i++) {
                       if ((boolean) model.getValueAt(i, 1) == true) {
                           one = true;
                           script = script.concat("' "
                                   + model.getValueAt(i, 0).toString()
                           + "': " + cSetup.getcName() + ", ");
                       }
                   }
                   if (one = false) {
                       script = script.substring(0, script.length() - 9);
                   }
                   else {
                       script = script.substring(0, script.length() - 2);
                       script = script.concat(" }\n\t");
                   }
                }
                script = script.concat("net.start()"
                + "\n\tCLI( net )"
                + "\n\tnet.stop()");

                try {
                    try (PrintStream out = new PrintStream(new FileOutputStream(fileChooser.getSelectedFile()))) {
                        out.print(script);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TopologyGraph.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public class ControllerAction extends AbstractAction {

        private final Graph g;

        public ControllerAction(Graph g) {
            super("Properties...");
            this.g = g;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ControllerSetup.setup(g);
            } catch (UnknownHostException ex) {
                Logger.getLogger(TopologyGraph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
