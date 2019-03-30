/*
 * GraphElements.java
 *
 * Created on March 21, 2007, 9:57 AM
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */
package OFGraph;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author Dr. Greg M. Bernstein
 */
public class GraphElements {

    /**
     * Creates a new instance of GraphElements
     */
    public GraphElements() {
    }

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

        public Number getIconNum() {
            if ("Host".equals(this.getType())) {
                return 0;
            }
            return 1;
        }
    }

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

    // Single factory for creating Vertices...
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

        @Override
        public GraphElements.MyVertex create() {
            TopologyGraph tpGraph = TopologyGraph.getSharedGraph();
            MyVertex v;
            if (tpGraph.getTypeHost().isSelected()) {
                String name = "h" + hostCount++;
                v = new MyVertex(name, iconNames[0]);
            }
            else { //if (tpGraph.getTypeSwitch().isSelected())
                String name = "s" + switchCount++;
                v = new MyVertex(name, iconNames[1]);
            }
            return v;
        }

    }

    // Singleton factory for creating Edges...
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
