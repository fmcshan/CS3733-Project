package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;

public class Event {
    String id;
    String snapshotId;
    String author;
    String date;
    String event;
    Node node;
    Edge edge;

    public String getId() {
        return id;
    }

    public String getEvent() {
        return event;
    }

    public Event(String id, String snapshotId, String author, String date, String event, Node node, Edge edge) {
        this.id=id;
        this.snapshotId=snapshotId;
        this.author=author;
        this.date=date;
        this.event=event;
        this.node=node;
        this.edge=edge;
    }

    public String getSnapshot() {
        return snapshotId;
    }

    @Override
    public String toString(){
        String theEdge = "null";
        if(!(edge == (null))) {
            theEdge = edge.getEdgeID();
        }
        String theNode = "null";
        if(!(node == (null))) {
            theNode = node.getLongName();
        }
        return "id: "+ id + "\nsnapshot ID: "+ snapshotId + "\nauthor: " + author + "\ndate: " + date + "\nevent: " +event+ "\nnode: "+ theNode
                + "\nedge: " + theEdge;
    }

}
