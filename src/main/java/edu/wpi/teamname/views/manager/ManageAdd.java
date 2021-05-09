package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.Submit;

public class ManageAdd implements Action{

    private Object object;
//    private Object object1;
//    private Object object2;
   public String   actionName;
   String snapshotUUID;
    public ManageAdd(Object object, String snapshotUUID ){
        this.object =object;
         this.snapshotUUID=snapshotUUID;
    }

//    public ManageAdd(Object object1, Object object2){
//        this.object1=object1;
//        this.object2=object2;
//    }

    @Override
    public void execute() {

        if(object instanceof Edge){
            Submit.getInstance().addEdge((Edge) object);
            actionName = "added edge between:" + ((Edge) object).getStartNode() + " " +((Edge) object).getEndNode();
        }
        if(object instanceof Node) {
            Submit.getInstance().addNode((Node) object);
            actionName = "added Node:" + ((Node) object).getLongName();

        }

    }

    @Override
    public void undo() {
        if(object instanceof Edge){
            System.out.println(object);
            Submit.getInstance().removeEdge((Edge) object);
            actionName = "removed edge between:" + ((Edge) object).getStartNode() + " " +((Edge) object).getEndNode();
        }

        if(object instanceof Node){
           //System.out.println("Add " + object);
            Submit.getInstance().removeNode((Node) object);
            actionName = "removed Node:" + ((Node) object).getLongName();

        }

    }

    public boolean isNode(){
        if(object instanceof Node){
            return true;
        } else{
            return false;
        }
    }

    public String getLongName() {
        return ((Node) object).getLongName();
    }

    @Override
    public StringBuilder checkChangesDo() {
        return null;
    }

    @Override
    public StringBuilder checkChangesUndo() {
        return null;
    }




    @Override
    public String getActionName() {
//        if(object instanceof Edge){
//            return "added edge between:" + ((Edge) object).getStartNode() + " " +((Edge) object).getEndNode();
//        }
//        if(object instanceof Node){
//            return "added Node:" + ((Node) object).getLongName();
//        }
        return actionName;
    }
}
