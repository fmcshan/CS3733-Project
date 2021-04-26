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
    private IAlgorithm searchAlgorithm;

    /**
     * Constructor which we load the search algorithm we'd like to use
     * @param searchAlgorithm the specified search algorithm
     */
    public SearchContext(IAlgorithm searchAlgorithm){
        this.searchAlgorithm = searchAlgorithm;
    }

    /**
     * Switches the search algorithm we'd like to use
     * @param searchAlgorithm the specified search algorithm
     */
    public void setContext(IAlgorithm searchAlgorithm){
        this.searchAlgorithm = searchAlgorithm;
    }

    /**
     * Returns a path based on the specified search algorithm
     * @return Path according to specified search algorithm
     */
    public ArrayList<Node> getPath(){
        return searchAlgorithm.getPath();
    }

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        SearchContext searchAlgorithms = new SearchContext(new AStar(nodes, nodes.get(0), nodes.get(60)));
        ArrayList<Node> aStarPath = searchAlgorithms.getPath();
        searchAlgorithms.setContext(new DFS(nodes, nodes.get(0), nodes.get(60)));
        ArrayList<Node> DFSPath = searchAlgorithms.getPath();
        searchAlgorithms.setContext(new BFS(nodes, nodes.get(0), nodes.get(60)));
        ArrayList<Node> BFSPath = searchAlgorithms.getPath();
        System.out.println("" + aStarPath.size() + " " + DFSPath.size() + " " + BFSPath.size());
    }

}
