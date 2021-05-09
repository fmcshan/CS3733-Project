package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;

import java.util.ArrayList;

/**
 * <h1>Algorithm Interface</h1>
 * @author Emmanuel Ola
 */
public abstract class Algorithm {

    ArrayList nodes;

    Node start;

    Node goal;

    public Algorithm(ArrayList<Node> nodes, Node start, Node goal) {
        this.resetNodes(nodes);
        this.start = start;
        this.goal = goal;
    }

    public Algorithm(){
        this.nodes = new ArrayList<>();
        this.start = new Node();
        this.goal = new Node();
    }

    public abstract void resetNodes(ArrayList<Node> nodes);

    public abstract ArrayList<Node> getPath();

    //public abstract void loadNodes(ArrayList<Node> nodes, Node start, Node goal);


    public void loadNodes(ArrayList<Node> nodes, Node start, Node goal){
        this.nodes = nodes;
        this.start = start;
        this.goal = goal;
        this.process();
    }

    protected abstract void process();

    /**
     * Returns a list of nodes representing the path specific to the floor provided
     *
     * @param floor the floor we want to get a path for
     * @return list of nodes representing a path specific to the floor
     */
    public final ArrayList<Node> getFloorNodes(String floor) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Node node : this.getPath()) {
            if (node.getFloor().equals(floor))
                nodes.add(node);
        }
        return nodes;
    }

    /**
     * Returns a nested list of nodes representing the different sections of paths by floors
     * @return nested list of nodes representing the different sections of paths by floors
     */
    public final ArrayList<ArrayList<Node>> getAllFloorPaths(){
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<ArrayList<Node>> paths = new ArrayList<>();
        String currentfloor = start.getFloor();
        if (!(this.getPath().size() == 1)) {
            for (Node node : this.getPath()) {
                if (node.getFloor().equals(currentfloor))
                    nodes.add(node);
                else {
                    currentfloor = node.getFloor();
                    paths.add(nodes);
                    nodes = new ArrayList<>();
                    nodes.add(node);
                }
            }
        }
        paths.add(nodes);
        return paths;
    }

    public final ArrayList<ArrayList<Node>> getFloorPaths(String floor) {
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<ArrayList<Node>> paths = new ArrayList<>();
        for (ArrayList<Node> floorPath : getAllFloorPaths()) {
            if (floorPath.get(0).getFloor().equals(floor))
                paths.add(floorPath);
        }
        return paths;
    }

    /**
     * Returns all the floors we reach on our path
     * @return List of floors we reach on our path
     */
    public ArrayList<String> getRelevantFloors() {
        ArrayList<String> result = new ArrayList<>();
        for (Node node : this.getPath()) {
            if (!result.contains(node.getFloor()))
                result.add(node.getFloor());
        }
        return result;
    }
}
