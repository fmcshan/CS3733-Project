package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.User;

import java.util.ArrayList;
import java.util.UUID;

public class Change {
    private String changeType;
    private String changeId;



    private String authorName;
    private String authorId;
    private String authorEmail;

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    private Node modifiedNode;
    private Edge modifiedEdge;

    private ArrayList<UserRegistration> userRegistrations;
    private UserRegistration userRegistration;

    private ArrayList<MasterServiceRequestStorage> giftDeliveries;
    private MasterServiceRequestStorage giftDelivery;

    private ArrayList<User> users;
    private User user;

    public Change(String changeType, String changeId) {
        this.changeType = changeType;
        this.changeId = changeId;
    }

    public Change(String changeType) {
        this.changeType = changeType;
        this.changeId = UUID.randomUUID().toString();
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

    public ArrayList<MasterServiceRequestStorage> getGiftDeliveries() {
        return giftDeliveries;
    }

    public void setGiftDeliveries(ArrayList<MasterServiceRequestStorage> giftDeliveries) {
        this.giftDeliveries = giftDeliveries;
    }

    public MasterServiceRequestStorage getGiftDelivery() {
        return giftDelivery;
    }

    public void setGiftDelivery(MasterServiceRequestStorage giftDelivery) {
        this.giftDelivery = giftDelivery;
    }

    public Node getModifiedNode() {
        return modifiedNode;
    }

    public void setModifiedNode(Node modifiedNode) {
        this.modifiedNode = modifiedNode;
    }

    public Edge getModifiedEdge() {
        return modifiedEdge;
    }

    public void setModifiedEdge(Edge modifiedEdge) {
        this.modifiedEdge = modifiedEdge;
    }

    public void setUsers(ArrayList<User> users) { this.users = users; }
    public ArrayList<User> getUsers() { return users; }

    public void setUser(User user) { this.user = user; }
    public User getUser() { return user; }

}
