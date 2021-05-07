package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.Submit;

public class ManageNode implements Action{

    private Node node;


    public ManageNode(Node node){
        this.node = node;
    }


    @Override
    public void execute() {
        Submit.getInstance().addNode(node);
    }

    @Override
    public void undo() {
        Submit.getInstance().removeNode(node);
    }

    @Override
    public String getActionName() {
        return "edited "+ node;
    }
}
