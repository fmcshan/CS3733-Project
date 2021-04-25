package edu.wpi.teamname.Algo.Pathfinding;

import edu.wpi.teamname.Algo.Algorithms.AStar;
import edu.wpi.teamname.Algo.Node;

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
            if (i == path.size() - 1)
                result.add("You have arrived at your destination");

        }
        return null;
    }
}
