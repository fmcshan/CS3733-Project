package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Parser;
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

    public void loadNodes(ArrayList<Node> nodes, Node start, Node goal) {this.searchAlgorithm.loadNodes(nodes, start, goal);}

    public String getAlgorithm(){return searchAlgorithm.getClass().getName();}

    public ArrayList<String> getRelevantFloors(){return searchAlgorithm.getRelevantFloors();}

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        SearchContext searchAlgorithms = new SearchContext(new AStar(nodes, nodes.get(Parser.indexOfNode(nodes, "WELEV00M01")), nodes.get(Parser.indexOfNode(nodes, "WELEV00E01")), false));
        ArrayList<Node> aStarPaths = searchAlgorithms.getPath();
        searchAlgorithms.setContext(new DFS(nodes, nodes.get(Parser.indexOfNode(nodes, "WELEV00M01")), nodes.get(Parser.indexOfNode(nodes, "WELEV00E01"))));
        ArrayList<Node> DFSPaths = searchAlgorithms.getPath();
        searchAlgorithms.setContext(new BFS(nodes, nodes.get(Parser.indexOfNode(nodes, "WELEV00M01")), nodes.get(Parser.indexOfNode(nodes, "WELEV00E01"))));
        ArrayList<Node> BFSPaths = searchAlgorithms.getPath();
        searchAlgorithms.setContext(new Dijkstra(nodes, nodes.get(Parser.indexOfNode(nodes, "WELEV00M01")), nodes.get(Parser.indexOfNode(nodes, "WELEV00E01"))));
        ArrayList<Node> DijkstraPaths = searchAlgorithms.getPath();
        searchAlgorithms.setContext(new BestFirstSearch(nodes, nodes.get(Parser.indexOfNode(nodes, "WELEV00M01")), nodes.get(Parser.indexOfNode(nodes, "WELEV00E01"))));
        ArrayList<Node> BestFirstSearchPaths = searchAlgorithms.getPath();
        System.out.println(aStarPaths.size() + " " + DFSPaths.size() + " " + BFSPaths.size() + " " + DijkstraPaths.size() + " " + BestFirstSearchPaths.size());
    }

}
