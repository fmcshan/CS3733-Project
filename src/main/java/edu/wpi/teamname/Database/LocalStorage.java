package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Authentication.User;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class LocalStorage {
    private static final LocalStorage instance = new LocalStorage();
    private LocalStorage() {};

    private List<DataListener> listeners = new ArrayList<DataListener>();

    private ArrayList<Node> nodes;
    private HashMap<String, Node> nodeMap;
    private ArrayList<Edge> edges;
    private ArrayList<UserRegistration> registrations;
    private ArrayList<MasterServiceRequestStorage> masterStorages;
    private ArrayList<User> users;
    private ArrayList<String> reservedParkingSpaces;

    public void setReservedParkingSpaces(ArrayList<String> spaces){
        this.reservedParkingSpaces = spaces;
    }
    public ArrayList<String> getReservedParkingSpaces() {
        if (this.reservedParkingSpaces == null) {
            for (int i = 0; i < 100; i++) {
                if (this.reservedParkingSpaces != null) {
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep((long) 200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.reservedParkingSpaces == null) {
            return null;
        } else {
            return (ArrayList<String>) this.reservedParkingSpaces.clone();
        }
    }



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
                    TimeUnit.MILLISECONDS.sleep((long) 200);
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
                    TimeUnit.MILLISECONDS.sleep((long) 200);
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
                    TimeUnit.MILLISECONDS.sleep((long) 200);
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

    public void setMasterStorages(ArrayList<MasterServiceRequestStorage> _masterStorages) {
        this.masterStorages = _masterStorages;
    }

    public void setUsers(ArrayList<User> _users) {
        this.users = _users;
    }

    public void addMasterStorage(MasterServiceRequestStorage _masterStorages) {
        this.masterStorages.add(_masterStorages);
    }

    public ArrayList<MasterServiceRequestStorage> getMasterStorages() {
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            return null;
        }

        if (this.masterStorages == null) {
            for (int i = 0; i < 100; i++) {
                if (this.masterStorages != null) {
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep((long) 200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.masterStorages == null) {
            return null;
        } else {
            ArrayList<MasterServiceRequestStorage> ret = (ArrayList<MasterServiceRequestStorage>) this.masterStorages.clone();
            ret.sort(new Comparator<MasterServiceRequestStorage>() {
                public int compare(MasterServiceRequestStorage r1, MasterServiceRequestStorage r2) {
                    return r2.getId() - r1.getId();
                }
            });
            return ret;
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
                    TimeUnit.MILLISECONDS.sleep((long) 200);
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
