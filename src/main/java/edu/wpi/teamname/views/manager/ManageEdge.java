package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Database.Submit;

public class ManageEdge implements Action{

    private Edge edge;
    public ManageEdge(Edge edge){
        this.edge =edge;
    }

    @Override
    public void execute() {
        Submit.getInstance().addEdge(edge);
    }

    @Override
    public void undo() {
        Submit.getInstance().removeEdge(edge);
    }

    @Override
    public String getActionName() {
        return "edited edge between:" + edge.getStartNode() + " " +edge.getEndNode();
    }
}
