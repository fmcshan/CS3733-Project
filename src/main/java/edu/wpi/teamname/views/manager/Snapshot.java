package edu.wpi.teamname.views.manager;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Snapshot {
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;
    String Id;
    ArrayList<Action> actions;
    public Snapshot(){
        nodes = LocalStorage.getInstance().getNodes();
        edges = LocalStorage.getInstance().getEdges();
           Id = UUID.randomUUID().toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snapshot snap = (Snapshot) o;
        return Id.equals(snap.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

}
