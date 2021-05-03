package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;

/**
 * <h1>Search Context</h1>
 * Class that helps us switch between search algorithms during run time
 * @author Emmanuel Ola
 */
public class SearchContext {
    /**
     * Interface that allows us to switch between algorithms
     */
    private Algorithm searchAlgorithm;

    /**
     * Constructor which we load the search algorithm we'd like to use
     * @param searchAlgorithm the specified search algorithm
     */
    public SearchContext(Algorithm searchAlgorithm){
        this.searchAlgorithm = searchAlgorithm;
    }

    /**
     * Switches the search algorithm we'd like to use
     * @param searchAlgorithm the specified search algorithm
     */
    public void setContext(Algorithm searchAlgorithm){
        this.searchAlgorithm = searchAlgorithm;
    }

    /**
     * Returns a path based on the specified search algorithm
     * @return Path according to specified search algorithm
     */
    public ArrayList<Node> getPath(){
        return searchAlgorithm.getPath();
    }

    public ArrayList<ArrayList<Node>> getAllFloorPaths() {return searchAlgorithm.getAllFloorPaths();}

    public ArrayList<ArrayList<Node>> getFloorPaths(String floor) {return searchAlgorithm.getFloorPaths(floor);}

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        SearchContext searchAlgorithms = new SearchContext(new AStar(nodes, nodes.get(10), nodes.get(76), false));
        ArrayList<ArrayList<Node>> aStarPaths = searchAlgorithms.getAllFloorPaths();
        searchAlgorithms.setContext(new DFS(nodes, nodes.get(10), nodes.get(76)));
        ArrayList<ArrayList<Node>> DFSPaths = searchAlgorithms.getAllFloorPaths();
        searchAlgorithms.setContext(new BFS(nodes, nodes.get(10), nodes.get(76)));
        ArrayList<ArrayList<Node>> BFSPaths = searchAlgorithms.getAllFloorPaths();
        searchAlgorithms.setContext(new Djikstra(nodes, nodes.get(10), nodes.get(76)));
        ArrayList<ArrayList<Node>> DjikstraPaths = searchAlgorithms.getAllFloorPaths();
        searchAlgorithms.setContext(new BestFirstSearch(nodes, nodes.get(10), nodes.get(76)));
        ArrayList<ArrayList<Node>> BestFirstSearchPaths = searchAlgorithms.getAllFloorPaths();
        System.out.println(aStarPaths.size() + " " + DFSPaths.size() + " " + BFSPaths.size() + " " + DjikstraPaths.size() + " " + BestFirstSearchPaths.size());
    }

}
