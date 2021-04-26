package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.simplify.Config;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Method;


public class LocalStorage {
    private static final LocalStorage instance = new LocalStorage();
    private LocalStorage() {};

    private List<DataListener> listeners = new ArrayList<DataListener>();

    private ArrayList<Node> nodes;
    private HashMap<String, Node> nodeMap;
    private ArrayList<Edge> edges;
    private ArrayList<UserRegistration> registrations;
    private ArrayList<GiftDeliveryStorage> giftDeliveryStorages;
    private ArrayList<User> users;

    public static synchronized LocalStorage getInstance() {
        return instance;
    }

    public void addListener(DataListener toAdd) {
        listeners.add(toAdd);
    }

    public ArrayList<Node> getNodes() {
        if (this.nodes == null) {
            for (int i = 0; i < 100; i++) {
                if (this.nodes != null) {
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep((long) 50);
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
        nodeMap = new HashMap<String, Node>();
        nodes.forEach( n -> {
            nodeMap.put(n.getNodeID(), n);
        });

        for (DataListener dl : listeners) {
            try {
                dl.nodesSet(nodes);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public ArrayList<Edge> getEdges() {
        if (this.edges == null) {
            for (int i = 0; i < 100; i++) {
                if (this.edges != null) {
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep((long) 50);
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
        for (DataListener dl : listeners) {
            try {
                dl.edgesSet(edges);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public ArrayList<UserRegistration> getRegistrations() {
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            return null;
        }

        if (this.registrations == null) {
            for (int i = 0; i < 100; i++) {
                if (this.registrations != null) {
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep((long) 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.registrations == null) {
            return null;
        } else {
            return (ArrayList<UserRegistration>) this.registrations.clone();
        }
    }

    public void linkEdges() {
        HashMap<String, Node> nodeHashMap = new HashMap<String, Node>();
        this.nodes.forEach(n -> {
            nodeHashMap.put(n.getNodeID(), n);
        });
        this.edges.forEach(e -> {
            if (nodeHashMap.containsKey(e.getStartNode()) && nodeHashMap.containsKey(e.getEndNode())) {
                nodeHashMap.get(e.getStartNode()).addEdge(nodeHashMap.get(e.getEndNode()));
            }
        });
    }

    public void setRegistrations(ArrayList<UserRegistration> _registrations) {
        this.registrations = _registrations;
    }

    public void addRegistration(UserRegistration _registration) {
        this.registrations.add(_registration);
    }

    public void setGiftDeliveryStorages(ArrayList<GiftDeliveryStorage> _giftDeliveryStorages) {
        this.giftDeliveryStorages = _giftDeliveryStorages;
    }

    public void setUsers(ArrayList<User> _users) {
        this.users = _users;
    }

    public void addGiftDeliveryStorage(GiftDeliveryStorage _giftDelivery) {
        this.giftDeliveryStorages.add(_giftDelivery);
    }

    public ArrayList<GiftDeliveryStorage> getGiftDeliveryStorages() {
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            return null;
        }

        if (this.giftDeliveryStorages == null) {
            for (int i = 0; i < 100; i++) {
                if (this.giftDeliveryStorages != null) {
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep((long) 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.giftDeliveryStorages == null) {
            return null;
        } else {
            return (ArrayList<GiftDeliveryStorage>) this.giftDeliveryStorages.clone();
        }
    }

    public ArrayList<User> getUsers() {
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            return null;
        }

        if (this.users == null) {
            for (int i = 0; i < 100; i++) {
                if (this.users != null) {
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep((long) 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.users == null) {
            return null;
        } else {
            return (ArrayList<User>) this.users.clone();
        }
    }
}
