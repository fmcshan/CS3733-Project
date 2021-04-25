package edu.wpi.teamname.Algo.Pathfinding;

import edu.wpi.teamname.Algo.Algorithms.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Parser;
import edu.wpi.teamname.Algo.Stopwatch;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;

/**
 * <h1>Navigation Helper</h1>
 * Class designed to help reduce the complexity of navigation, in terms of floor switching, as well as text directions
 */
public class NavigationHelper {
    private AStar pathfinder;

    public NavigationHelper(ArrayList<Node> nodes, Node start, Node goal) {
        pathfinder = new AStar(nodes, start, goal);
    }

    public ArrayList<String> getTextDirections(){
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Node> path = pathfinder.getPath();
        for (int i = 0; i < path.size(); i++) {
            Node node = path.get(i);
            if (i == path.size() - 1)
                result.add("You have arrived at your destination");
            else {
                if (i > 0) {
                    if (node.getNodeType().equals("ELEV") && path.get(i+1).getNodeType().equals("ELEV"))
                        result.add("Take the Elevator to Floor " + path.get(i+1).getFloor());
                    else if (node.getNodeType().equals("EXIT"))
                        result.add("Head for " + node.getLongName());
                    else{
                        Node prev = path.get(i - 1);
                        Node next = path.get(i + 1);
                        //result.add(prev.getLongName() + " to " + node.getLongName() + ": " + Double.toString(getAngle(prev, node)));
                        //result.add(node.getLongName() + " to " + next.getLongName() + ": " + Double.toString(getAngle(node, next)));
                        result.add(getDirection(getAngle(prev, node), getAngle(node, next)) + " to get from " + node.getLongName() + " to " + next.getLongName());
                    }
                }
            }

        }
        return result;
    }

    public String getDirection (double a, double b){
        double angle = this.NormalizeAngle(b - a);

        if (angle >= 315 || angle <= 45)
            return "Straight";
        else if (angle >= 135 && angle <= 215)
            return "Backwards" + a + ", " + b;
        else if (angle <= 135 && angle >= 45 )
            return "Turn Left";
        else
            return "Turn right";

    }

    private double NormalizeAngle(double angle) {
        if (angle < 0)
            return angle + 360;
        else
            return angle;
    }

    private double getAngle(Node node, Node node1) {
        int x1 = node.getX();
        int y1 = node.getY();
        int x2 = node1.getX();
        int y2 = node1.getY();
        double hyp = pathfinder.distance(node, node1);

        if (x2 > x1 && y2 > y1)
            return 270 + Math.toDegrees(Math.acos((y2-y1)/hyp));
        else if (x2 > x1 && y2 == y1)
            return 0;
        else if (x2 == x1 && y2 > y1)
            return 90;
        else if (x2 < x1 && y2 > y1)
            return 270 - Math.toDegrees(Math.acos((y2-y1)/hyp));
        else if (x2 < x1 && y2 == y1)
            return 180;
        else if (x2 < x1 && y2 < y1)
            return 180 + Math.toDegrees(Math.acos((x1-x2)/hyp));
        else if (x2 == x1 && y2 < y1)
            return 270;
        else if (x2 > x1 && y2 < y1)
            return Math.toDegrees(Math.acos((x2-x1)/hyp));
        return 5;
    }

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        //Node start = nodes.get(Parser.indexOfNode(nodes, "ALABS001L2"));
        Node start = nodes.get(Parser.indexOfNode(nodes, "lPARK013GG"));
        Node goal = nodes.get(Parser.indexOfNode(nodes, "WELEV00E01"));
        Stopwatch timer = new Stopwatch();
        NavigationHelper dir = new NavigationHelper(nodes, start, goal);
        System.out.println(dir.getTextDirections());
        ArrayList<String> nodeTypes = new ArrayList<>();
        for (Node node : nodes) {
            if (!nodeTypes.contains(node.getNodeType()))
                nodeTypes.add(node.getNodeType());
        }
        System.out.println(nodeTypes);
    }
}
