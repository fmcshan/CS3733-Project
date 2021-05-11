package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Snapshot {
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;
    String Id;
    String author;
    JSONObject data;
    String date;
    ArrayList<Action> actions;

    //    public Snapshot(){
//        nodes = LocalStorage.getInstance().getNodes();
//        edges = LocalStorage.getInstance().getEdges();
////           Id = UUID.randomUUID().toString();
////    }
    public Snapshot(String Id, String author, String date, ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.Id = Id;
        this.author = author;
        this.date = date;
        this.nodes = nodes;
        this.edges = edges;

    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "ID: " + Id + "\nauthor: " + author + "\ndate: " + date + "\nnodes: " + nodes.size() + "\nedges: " + edges.size();


    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snapshot snap = (Snapshot) o;
        return Id.equals(snap.Id);
    }

    public String getId() {
        return Id;
    }

    public String getAuthor() {
        return author;
    }

    public JSONObject getData() {
        return data;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }


    public Snapshot doEvent(Event event) {
        Snapshot returnSnap = new Snapshot(this.Id, this.author, this.date, this.nodes, this.edges);
        ArrayList<Event> events = LocalStorage.getInstance().getEvents();
        ArrayList<Event> correctEvents = new ArrayList<Event>();
        ArrayList<Event> finalEvents = new ArrayList<Event>();
        for (Event e : events) {
            if (e.getSnapshot().equals(Id)) {
                correctEvents.add(e);
            }
        }
        Boolean flag = true;
        Collections.reverse(correctEvents);
        for (Event e : correctEvents) {
            System.out.println("Event " + e.event);
            if (e.id.equals(event.id)) {
                finalEvents.add(e);
                flag = false;
               System.out.println("Made it");
            }
            if (flag) {
                finalEvents.add(e);
            }
        }
        for (Event e : finalEvents) {
            if (e.getEvent().equals("remove_node")) {
                System.out.println("TEST remove node: " + e.getNode());
                returnSnap.nodes.remove(e.node);
            }
            if (e.getEvent().equals("remove_edge")) {
                System.out.println("TEST remove edge: " + e.getEdge());
                returnSnap.edges.remove(e.edge);
            }
            if (e.getEvent().equals("add_node")) {
                System.out.println("TEST add node: " + e.getNode().getLongName());
                returnSnap.nodes.add(e.node);
            }
            if (e.getEvent().equals("add_edge")) {
                System.out.println("TEST add edge: " + e.getEdge().getEdgeID());
                returnSnap.edges.add(e.edge);
            }
        }
        return returnSnap;
    }

}
