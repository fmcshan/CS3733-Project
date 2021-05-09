package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.Submit;

public class ManageDelete implements Action{

    private Object object;
    private Object object1;
    private Object object2;

    public ManageDelete(Object object){
        this.object =object;
    }
    public ManageDelete(Object object1, Object object2){
        this.object1=object1;
        this.object2=object2;
        this.object=object1;
    }

    @Override
    public void execute() {
        if(object instanceof Edge){
            Submit.getInstance().removeEdge((Edge) object);
        }
        if(object instanceof Node) {
            Submit.getInstance().removeNode((Node) object);
        }

    }
    public boolean isNode(){
        if(object instanceof Node){
            return true;
        } else{
            return false;
        }
    }

    public StringBuilder checkChangesDo(){
        StringBuilder answer = new StringBuilder();
        if(!(((Node) object1).getNodeID().equals(((Node) object2).getNodeID()))){
            answer.append("Node ID changed from " + ((Node) object1).getNodeID() + " to "  + ((Node) object2).getNodeID() + "\n");
        }
        if(!(((Node) object1).getLongName().equals(((Node) object2).getLongName()))){
            answer.append("Node LongName changed from " + ((Node) object1).getLongName() + " to "  + ((Node) object2).getLongName() + "\n");
        }
        if(!(((Node) object1).getFloor().equals(((Node) object2).getFloor()))){
            answer.append("Floor changed from " + ((Node) object1).getFloor() + " to "  + ((Node) object2).getFloor() + "\n");
        }
        if(!(((Node) object1).getX() == (((Node) object2).getX()))){
            answer.append("X changed from " + ((Node) object1).getX() + " to "  + ((Node) object2).getX() + "\n");
        }
        if(!(((Node) object1).getY() == (((Node) object2).getY()))){
            answer.append("Y changed from " + ((Node) object1).getY() + " to "  + ((Node) object2).getY() + "\n");
        }
        if(!(((Node) object1).getBuilding().equals(((Node) object2).getBuilding()))){
            answer.append("Building changed from " + ((Node) object1).getBuilding() + " to "  + ((Node) object2).getBuilding() + "\n");
        }
        if(!(((Node) object1).getNodeType().equals(((Node) object2).getNodeType()))){
            answer.append("Node Type changed from " + ((Node) object1).getNodeType() + " to "  + ((Node) object2).getNodeType() + "\n");
        }
        return answer;
    }

    public StringBuilder checkChangesUndo(){
        StringBuilder answer = new StringBuilder();
        if(!(((Node) object1).getNodeID().equals(((Node) object2).getNodeID()))){
            answer.append("Node ID changed from " + ((Node) object2).getNodeID() + " to "  + ((Node) object1).getNodeID() + "\n");
        }
        if(!(((Node) object1).getLongName().equals(((Node) object2).getLongName()))){
            answer.append("Node LongName changed from " + ((Node) object2).getLongName() + " to "  + ((Node) object1).getLongName() + "\n");
        }
        if(!(((Node) object1).getFloor().equals(((Node) object2).getFloor()))){
            answer.append("Floor changed from " + ((Node) object2).getFloor() + " to "  + ((Node) object1).getFloor() + "\n");
        }
        if(!(((Node) object1).getX() == (((Node) object2).getX()))){
            answer.append("X changed from " + ((Node) object2).getX() + " to "  + ((Node) object1).getX() + "\n");
        }
        if(!(((Node) object1).getY() == (((Node) object2).getY()))){
            answer.append("Y changed from " + ((Node) object2).getY() + " to "  + ((Node) object1).getY() + "\n");
        }
        if(!(((Node) object1).getBuilding().equals(((Node) object2).getBuilding()))){
            answer.append("Building changed from " + ((Node) object2).getBuilding() + " to "  + ((Node) object1).getBuilding() + "\n");
        }
        if(!(((Node) object1).getNodeType().equals(((Node) object2).getNodeType()))){
            answer.append("Node Type changed from " + ((Node) object2).getNodeType() + " to "  + ((Node) object1).getNodeType() + "\n");
        }
        return answer;
    }

    @Override
    public void undo() {
        System.out.println(object);
        if(object instanceof Edge){
            System.out.println(object);
            Submit.getInstance().addEdge((Edge) object);
        }

        if(object instanceof Node){
           // System.out.println("Delete " + object);
            Submit.getInstance().addNode((Node) object);
        }
    }

    public String getLongName() {
        return ((Node) object).getLongName();
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
