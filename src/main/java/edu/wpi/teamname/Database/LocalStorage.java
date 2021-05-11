package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.views.manager.Event;
import edu.wpi.teamname.views.manager.Snapshot;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class LocalStorage {
    private static final LocalStorage instance = new LocalStorage();

    private LocalStorage() {
    }

    ;

    private List<DataListener> listeners = new ArrayList<DataListener>();
    private ArrayList<Event> events;
    private ArrayList<Snapshot> snapshots;
    private ArrayList<Node> nodes;
    private HashMap<String, Node> nodeMap;
    private HashMap<String, Event> eventMap;
    private HashMap<String, Snapshot> snapMap;
    private ArrayList<Edge> edges;
    private ArrayList<UserRegistration> registrations;
    private ArrayList<MasterServiceRequestStorage> masterStorages;
    private ArrayList<User> users;
    private ArrayList<String> reservedParkingSpaces;

    public void setReservedParkingSpaces(ArrayList<String> spaces) {
        this.reservedParkingSpaces = spaces;
    }

    public ArrayList<String> getReservedParkingSpaces() {
        while (this.reservedParkingSpaces == null) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) 50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (ArrayList<String>) this.reservedParkingSpaces.clone();
    }


    public static synchronized LocalStorage getInstance() {
        return instance;
    }

    public void addListener(DataListener toAdd) {
        listeners.add(toAdd);
    }

    public ArrayList<Node> getNodes() {
        while (this.nodes == null) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) 50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (ArrayList<Node>) this.nodes.clone();
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
        nodeMap = new HashMap<String, Node>();
        nodes.forEach(n -> {
            nodeMap.put(n.getNodeID(), n);
        });

        for (DataListener dl : listeners) {
            try {
                dl.nodesSet(nodes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        eventMap = new HashMap<String, Event>();
        events.forEach(e -> {
            eventMap.put(e.getId(), e);
        });

//        for (DataListener dl : listeners) {
//            try {
//                dl.nodesSet(nodes);
//            } catch (Exception e) { e.printStackTrace(); }
//        }
    }
    public ArrayList<Event> getEvents() {
        return (ArrayList<Event>) this.events.clone();
    }
    public ArrayList<Snapshot> getSnapshots() {
        return (ArrayList<Snapshot>) this.snapshots.clone();
    }
    public void setSnapshots(ArrayList<Snapshot> snapshots) {
        this.snapshots = snapshots;
        snapMap = new HashMap<String, Snapshot>();
        snapshots.forEach(s -> {
            snapMap.put(s.getId(), s);
        });
    }

    public ArrayList<Edge> getEdges() {
        while (this.edges == null) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (ArrayList<Edge>) this.edges.clone();
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
        for (DataListener dl : listeners) {
            try {
                dl.edgesSet(edges);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<UserRegistration> getRegistrations() {
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            return null;
        }

        while (this.registrations == null) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ArrayList<UserRegistration> ret = (ArrayList<UserRegistration>) this.registrations.clone();
        ret.sort(new Comparator<UserRegistration>() {
            public int compare(UserRegistration r1, UserRegistration r2) {
                return (int) (r2.getSubmittedAt() - r1.getSubmittedAt());
            }
        });
        return ret;
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

        while (this.reservedParkingSpaces == null) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ArrayList<MasterServiceRequestStorage> ret = (ArrayList<MasterServiceRequestStorage>) this.masterStorages.clone();
        ret.sort(new Comparator<MasterServiceRequestStorage>() {
            public int compare(MasterServiceRequestStorage r1, MasterServiceRequestStorage r2) {
                return r2.getId() - r1.getId();
            }
        });
        return ret;
    }

    public ArrayList<User> getUsers() {
        if (!AuthenticationManager.getInstance().isAuthenticated() && !LocalFailover.getInstance().hasFailedOver()) {
            return null;
        }

        while (this.users == null) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (ArrayList<User>) this.users.clone();
    }
}
