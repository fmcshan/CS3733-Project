package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.Submit;

import java.util.HashMap;

public class ManageEdit implements Action {
    private Node newNode;
    private Node oldNode;
    private HashMap<String, Node> oldNodes;
    //    private Object object1;
//    private Object object2;
    public String   actionName;
    public ManageEdit (Node oldNode, Node newNode){

        this.newNode = newNode;
        this.oldNode  = oldNode;

    }
    @Override
    public void execute() {

        if(newNode instanceof Node){
           Submit.getInstance().editNode(newNode);
         //  oldNodes.put(oldNode.getNodeID(), oldNode);
            actionName = "do executed --"+ " "+ oldNode.getNodeID();
        }

    }

    @Override
    public void undo() {
        Submit.getInstance().editNode(oldNode);
        actionName = "redo executed --"+ " "+ oldNode.getNodeID();
    }

    @Override
    public boolean isNode() {
        return true;
    }

    @Override
    public String getActionName() {
        return actionName;
    }

    @Override
    public String getLongName() {
        return null;
    }

    @Override
    public StringBuilder checkChangesDo() {
        return null;
    }

    @Override
    public StringBuilder checkChangesUndo() {
        return null;
    }
}
