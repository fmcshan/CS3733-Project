package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;

public class Event {
    String id;
    String snapshot;
    String author;
    String date;
    String event;
    Node node;
    Edge edge;

    public Event(String id, String snapshot, String author, String date, String event, Node node, Edge edge) {
        this.id=id;
        this.snapshot=snapshot;
        this.author=author;
        this.date=date;
        this.event=event;
        this.node=node;
        this.edge=edge;
    }

    @Override
    public String toString(){
        return "id: "+ id + "\nsnapshot: "+ snapshot + "\nauthor: " + author + "\ndate: " + date + "\nevent: " +event+ "\nnode: "+ node.getLongName()
                + "\nedge: " + edge.getEdgeID();
    }

}
