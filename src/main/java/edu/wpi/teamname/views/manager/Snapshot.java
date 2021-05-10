package edu.wpi.teamname.views.manager;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    public Snapshot(String Id, String author, String date, ArrayList<Node> nodes, ArrayList<Edge> edges){
        this.Id=Id;
        this.author = author;
        this.date=date;
        this.nodes=nodes;
        this.edges=edges;

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
    public String toString(){
        return "ID: " + Id + "\nauthor: " + author + "\ndate: "+ date + "\nnodes: " + nodes.size() + "\nedges: " + edges.size();


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

}
