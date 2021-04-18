package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LocalStorage {
    private static final LocalStorage instance = new LocalStorage();
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    public static synchronized LocalStorage getInstance() {
        return instance;
    }

    public ArrayList<Node> getNodes() {
        if (this.nodes == null) {
            for (int i = 0; i < 100; i++) {
                if (this.nodes != null) {
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep((long) 0.05);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.nodes == null) {
            return null;
        } else {
            return (ArrayList<Node>) this.nodes.clone();
        }
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Edge> getEdges() {
        if (this.edges == null) {
            for (int i = 0; i < 100; i++) {
                if (this.edges != null) {
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep((long) 0.05);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.edges == null) {
            return null;
        } else {
            return (ArrayList<Edge>) this.edges.clone();
        }
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

}
