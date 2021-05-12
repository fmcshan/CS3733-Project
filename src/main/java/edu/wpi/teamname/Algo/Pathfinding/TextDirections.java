package edu.wpi.teamname.Algo.Pathfinding;

import edu.wpi.teamname.Algo.Algorithms.AStar;
import edu.wpi.teamname.Algo.Algorithms.SearchContext;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Parser;
import edu.wpi.teamname.Algo.Stopwatch;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;

import static edu.wpi.teamname.Algo.Algorithms.AStar.distance;

/**
 * <h1>Text Directions</h1>
 * Class designed to help reduce the complexity of navigation, in terms of floor switching, as well as text directions
 */
public class TextDirections {
    final double SCALE = 3.25;
    private SearchContext pathfinder;
    private ArrayList<String> directionFloors;

    public TextDirections(ArrayList<Node> nodes, Node start, Node goal) {
        pathfinder = new SearchContext(new AStar(nodes, start, goal, false));
        directionFloors = new ArrayList<>();
    }

    public TextDirections(SearchContext searchAlgorithm){
        pathfinder = searchAlgorithm;
        directionFloors = new ArrayList<>();
    }

    public ArrayList<String> getTextDirections(){
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Node> path = pathfinder.getPath();
        double total = 0;
        directionFloors.clear();
        for (int i = 0; i < path.size(); i++) {
            Node node = path.get(i);
            if (i == path.size() - 1) {
                result.add("You have arrived at your destination");
                directionFloors.add(path.get(i).getFloor());
            }
            else {
                Node next = path.get(i + 1);
                if (i > 0) {
                    Node prev = path.get(i - 1);
                    if (node.getNodeType().equals("ELEV") && next.getNodeType().equals("ELEV")){
                        result.add("Take the Elevator to Floor " + next.getFloor());
                        directionFloors.add(next.getFloor());
                    }
                    else if (node.getNodeType().equals("STAI") && next.getNodeType().equals("STAI")){
                        result.add("Take the Stairs to Floor " + next.getFloor());
                        directionFloors.add(next.getFloor());
                    }
                    else if (next.getNodeType().equals("EXIT")) {
                        result.add("Head for " + next.getLongName());
                        directionFloors.add(next.getFloor());
                    }
                    else {
                        if (i > 2 && (i < path.size() - 3)){
                            boolean isPrevStraight = getDirection(getAngle(path.get(i-2), prev), getAngle(prev, node)).equals("Straight ");
                            boolean isNextStraight = getDirection(getAngle(node, next), getAngle(next, path.get(i+2))).equals("Straight ");
                            boolean goStraight = getDirection(getAngle(prev, node), getAngle(node, next)).equals("Straight ");
                            if (!isPrevStraight && goStraight && isNextStraight){
                                total = 0;
                                total += distance(node, next)/SCALE;
                            } else if (isPrevStraight && goStraight && isNextStraight){
                                total += distance(node,next)/SCALE;
                            } else if (isPrevStraight && goStraight && !isNextStraight){
                                if (node.getNodeType().equals("HALL")) {
                                    result.add("Head down the hall for " + (int) Math.ceil(total) + " feet");
                                    directionFloors.add(node.getFloor());
                                }
                                else {
                                    result.add("Go straight for " + (int) Math.ceil(total) + " feet to get to " + next.getLongName());
                                    directionFloors.add(node.getFloor());
                                }
                            } else if (!isPrevStraight && goStraight && !isNextStraight){
                                result.add("Go straight towards " + next.getLongName());
                                directionFloors.add(node.getFloor());
                            } else
                                if (getDirection(getAngle(prev, node), getAngle(node, next)).equals("Turn ")) {
                                    result.add("Turn towards " + next.getLongName());
                                    directionFloors.add(node.getFloor());
                                }
                                else {
                                    result.add(getDirection(getAngle(prev, node), getAngle(node, next)) + next.getLongName());
                                    directionFloors.add(node.getFloor());
                                }
                        } else {
                            result.add(getDirection(getAngle(prev, node), getAngle(node, next)) + next.getLongName());
                            directionFloors.add(node.getFloor());
                        }
                    }
                } else {
                    result.add("Head for " + next.getLongName());
                    directionFloors.add(node.getFloor());
                }
            }
        }
        return result;
    }

    public ArrayList<String> getDirectionFloors(){
        getTextDirections();
        return directionFloors;
    }

    public String getDirection (double a, double b){
        double angle = this.NormalizeAngle(b - a);

        if (angle >= 315 || angle <= 45) {
            return "Go straight towards ";
        }
        else if (angle >= 135 && angle <= 215) {
            return "Turn ";
        }
        else if (angle <= 135 && angle >= 45 ) {
            return "Turn left towards ";
        }
        else if (angle <= 315 && angle >= 215) {
            return "Turn right towards ";
        }
        else
            return "bob";
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
        double hyp = distance(node, node1);

        if (x2 > x1 && y2 > y1)
            return 270 + Math.toDegrees(Math.acos((y2-y1)/hyp));
        else if (x2 > x1 && y2 == y1)
            return 0;
        else if (x2 == x1 && y2 > y1)
            return 270;
        else if (x2 < x1 && y2 > y1)
            return 270 - Math.toDegrees(Math.acos((y2-y1)/hyp));
        else if (x2 < x1 && y2 == y1)
            return 180;
        else if (x2 < x1 && y2 < y1)
            return 180 - Math.toDegrees(Math.acos((x1-x2)/hyp));
        else if (x2 == x1 && y2 < y1)
            return 90;
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
        Node goal = nodes.get(Parser.indexOfNode(nodes, "WELEV00E02"));
        Stopwatch timer = new Stopwatch();
        TextDirections dir = new TextDirections(nodes, start, goal);
//        System.out.println(dir.getDirectionFloors());
    }
}
