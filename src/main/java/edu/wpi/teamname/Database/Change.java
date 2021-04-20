package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;

import java.util.ArrayList;

public class Change {
    private String changeType;
    private String changeId;

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    private ArrayList<UserRegistration> userRegistrations;
    private UserRegistration userRegistration;

    private ArrayList<GiftDeliveryStorage> giftDeliveries;
    private GiftDeliveryStorage giftDelivery;

    public Change(String changeType, String changeId) {
        this.changeType = changeType;
        this.changeId = changeId;
    }

    public String getChangeType() {
        return changeType;
    }

    public String getChangeId() {
        return changeId;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public ArrayList<UserRegistration> getUserRegistrations() {
        return userRegistrations;
    }

    public void setUserRegistrations(ArrayList<UserRegistration> userRegistrations) {
        this.userRegistrations = userRegistrations;
    }

    public UserRegistration getUserRegistration() {
        return userRegistration;
    }

    public void setUserRegistration(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }

    public ArrayList<GiftDeliveryStorage> getGiftDeliveries() {
        return giftDeliveries;
    }

    public void setGiftDeliveries(ArrayList<GiftDeliveryStorage> giftDeliveries) {
        this.giftDeliveries = giftDeliveries;
    }

    public GiftDeliveryStorage getGiftDelivery() {
        return giftDelivery;
    }

    public void setGiftDelivery(GiftDeliveryStorage giftDelivery) {
        this.giftDelivery = giftDelivery;
    }
}
