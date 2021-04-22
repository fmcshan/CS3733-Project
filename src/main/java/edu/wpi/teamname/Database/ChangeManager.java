package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.socketListeners.Initiator;

import java.util.ArrayList;

public class ChangeManager extends Thread {
    private static final ChangeManager instance = new ChangeManager();
    private ArrayList<String> changes = new ArrayList<String>();

    private ChangeManager() {

    }

    public static synchronized ChangeManager getInstance() {
        return instance;
    }

    public void processChange(Change _change) {
        if (changes.contains(_change.getChangeId())) { return; }
        switch (_change.getChangeType()) {
            case "load_nodes":
            case "load_edges":
                if (changes.contains(_change.getChangeId())) { return; }
                LocalStorage.getInstance().setNodes(_change.getNodes());
                LocalStorage.getInstance().setEdges(_change.getEdges());
                LocalStorage.getInstance().linkEdges();
                break;

            case "add_node":
                ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
                nodes.add(_change.getModifiedNode());
                LocalStorage.getInstance().setNodes(nodes);
                LocalStorage.getInstance().linkEdges();
                break;

            case "edit_node":
                nodes = LocalStorage.getInstance().getNodes();
                nodes.forEach(n -> {
                    if (n.getNodeID().equals(_change.getModifiedNode().getNodeID())) {
                        nodes.remove(n);
                        nodes.add(_change.getModifiedNode());
                    }
                });
                LocalStorage.getInstance().setNodes(nodes);
                LocalStorage.getInstance().linkEdges();
                break;

            case "remove_node":
                nodes = LocalStorage.getInstance().getNodes();
                nodes.forEach(n -> {
                    if (n.getNodeID().equals(_change.getModifiedNode().getNodeID())) {
                        nodes.remove(n);
                    }
                });
                LocalStorage.getInstance().setNodes(nodes);
                LocalStorage.getInstance().linkEdges();
                break;

            case "add_edge":
                ArrayList<Edge> edges = LocalStorage.getInstance().getEdges();
                edges.add(_change.getModifiedEdge());
                LocalStorage.getInstance().setEdges(edges);
                LocalStorage.getInstance().linkEdges();
                break;

            case "edit_edge":
                edges = LocalStorage.getInstance().getEdges();
                edges.forEach(e -> {
                    if (e.getEdgeID().equals(_change.getModifiedEdge().getEdgeID())) {
                        edges.remove(e);
                        edges.add(_change.getModifiedEdge());
                    }
                });
                LocalStorage.getInstance().setEdges(edges);
                LocalStorage.getInstance().linkEdges();
                break;

            case "remove_edge":
               edges = LocalStorage.getInstance().getEdges();
                edges.forEach(e -> {
                    if (e.getEdgeID().equals(_change.getModifiedEdge().getEdgeID())) {
                        edges.remove(e);
                    }
                });
                LocalStorage.getInstance().setEdges(edges);
                LocalStorage.getInstance().linkEdges();
                break;

            case "submit_check_in":
                LocalStorage.getInstance().addRegistration(_change.getUserRegistration());
                Initiator.getInstance().triggerRegistration(_change.getUserRegistration());
                break;

            case "submit_gift_delivery":
                LocalStorage.getInstance().addGiftDeliveryStorage(_change.getGiftDelivery());
                Initiator.getInstance().triggerGiftDelivery(_change.getGiftDelivery());
                break;

            case "gift_delivery_updated":
                LocalStorage.getInstance().setGiftDeliveryStorages(_change.getGiftDeliveries());
                Initiator.getInstance().triggerGiftDeliveryUpdated();
                break;
        }
        changes.add(_change.getChangeId());
    }
}