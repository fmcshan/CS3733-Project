package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.Submit;

public class ManageEdit implements Action{
    private Object object;
    public ManageEdit(Object object){
        this.object =object;
    }

    @Override
    public void execute() {
        if(object instanceof Edge){
            Submit.getInstance().editEdge((Edge) object);
        }
        if(object instanceof Node) {
            Submit.getInstance().editNode((Node) object);
        }

    }

    @Override
    public void undo() {
        if(object instanceof Edge){
            Submit.getInstance().addEdge((Edge) object);
        }
        if(object instanceof Node){
            Submit.getInstance().addNode((Node) object);
        }
    }

    @Override
    public String getActionName() {
        if(object instanceof Edge){
            return "deleted edge between:" + ((Edge) object).getStartNode() + " " +((Edge) object).getEndNode();
        }
        if(object instanceof Node){
            return "deleted Node:" + ((Node) object).getLongName();
        }
        return "Hello";
    }
}
