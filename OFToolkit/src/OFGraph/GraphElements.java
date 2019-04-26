/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OFGraph;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.commons.collections15.Factory;

/**
 * Class that adds functionality to the topology graph, e.g. creating vertices
 * and edges.
 * @author Dr. Greg M. Bernstein, modified by 164776
 */
public class GraphElements {

    /**
     * Creates a new instance of GraphElements
     */
    public GraphElements() {
    }

    /**
     * Vertex class. Creates a vertex and gives its name, type (Host or Switch),
     * IP and MAC addresses.
     */
    public static class MyVertex {

        private String name;
        private final String type;
        private java.net.Inet4Address vertexIP;
        private String vertexMAC;

        public MyVertex(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Inet4Address getIP() {
            return vertexIP;
        }

        /**
         * Returns the Vertex's IP address as a string.
         * @return The Vertex's IP, or null if it does not have one.
         */
        public String getIPtoString() {
            try {
                return vertexIP.getHostAddress();
            } catch (NullPointerException e) {
                return null;
            }
        }

        public void setIP(String IP) throws UnknownHostException {
            this.vertexIP = (Inet4Address) Inet4Address.getByName(IP);
        }

        public String getMAC() {
            return vertexMAC;
        }

        public void setMAC(String vertexMAC) {
            this.vertexMAC = vertexMAC;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getType() {
            return type;
        }

        /**
         * Used to get the respective image of the Vertex's type for the graph.
         * @return 0 if host, 1 if switch.
         */
        public Number getIconNum() {
            if ("Host".equals(this.getType())) {
                return 0;
            }
            return 1;
        }
    }

    /**
     * The edge class. Edges are also referred to as links interchangeably in
     * the documentation, as edges are the general term and links are the
     * networking term.
     */
    public static class MyEdge {

        private String name;

        public MyEdge(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Factory to create vertices.
     */
    public static class MyVertexFactory implements Factory<MyVertex> {

        private static int hostCount = 1;
        private static int switchCount = 1;
        private static final MyVertexFactory instance = new MyVertexFactory();
        private static final String[] iconNames = {
            "Host",
            "Switch"
        };

        private MyVertexFactory() {
        }

        public static MyVertexFactory getInstance() {
            return instance;
        }

        /**
         * Creates a vertex, populates its fields and sends it to the graph.
         * Increments the host or switch count depending on its value, and
         * automatically assigns IP and MAC addresses based on its number.
         * @return The given vertex.
         */
        @Override
        public GraphElements.MyVertex create() {
            TopologyGraph tpGraph = TopologyGraph.getSharedGraph();
            MyVertex v;
            if (tpGraph.getTypeHost().isSelected()) {
                String name = "h" + hostCount++;
                v = new MyVertex(name, iconNames[0]);
                String hex = Integer.toHexString(hostCount);
                if (hex.length() == 1) {
                    //if the host count is at 9 or less, adds a preceding 0.
                    hex = "0" + hex;
                }
                if (hostCount < 256) {
                    v.setMAC("00:00:00:00:00:" + hex);
                }
                else if (hostCount < 65536) {
                    v.setMAC("00:00:00:00:" + hex.substring(0,1) + ":" + hex.substring(2,3));
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(), "You have reached 65536 hosts in this graph. You cannot create any more hosts.", "Host limit reached", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            else { //if (tpGraph.getTypeSwitch().isSelected())
                String name = "s" + switchCount++;
                v = new MyVertex(name, iconNames[1]);
            }
            return v;
        }

    }

    /**
     * Factory for creating edges.
     */
    public static class MyEdgeFactory implements Factory<MyEdge> {

        private static int linkCount = 1;

        private static final MyEdgeFactory instance = new MyEdgeFactory();

        private MyEdgeFactory() {
        }

        public static MyEdgeFactory getInstance() {
            return instance;
        }

        @Override
        public GraphElements.MyEdge create() {
            String name = "Link" + linkCount++;
            MyEdge link = new MyEdge(name);
            return link;
        }

    }

}
