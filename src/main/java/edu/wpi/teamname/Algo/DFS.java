package edu.wpi.teamname.Algo;

import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;

import java.util.ArrayList;

/**
 * <h1>DFS</h1>
 * This program performs Depth-First Search
 * @author Conor McDonough
 */


public class DFS {
    public static void main(String[] args) {
        ArrayList<Node> nodes= new ArrayList<>(); //ArrayList of Nodes

        nodes = LocalStorage.getInstance().getNodes(); //Parses .csv files and loads the nodes ArrayList

        //System.out.println(nodes.get(0).getEdgeTo());
        //System.out.println(nodes.get(28));
        //System.out.println(nodes.get(30));
        Stopwatch DFSwatch = new Stopwatch();

        //To run this code simply insert the nodeID for the start and end nodes
        // as the second parameter for both instances of indexOfNode
        // The first instance would be the starting node and the second is the end node
        ArrayList<String> answer = DoDFS(nodes.get(Parser.indexOfNode(nodes, "CCONF002L1")),
                nodes.get(Parser.indexOfNode(nodes, "WELEV00ML1")));
        Stopwatch loadnodes = new Stopwatch();
        AStar bob = new AStar(nodes, nodes.get(Parser.indexOfNode(nodes,"CCONF002L1")), nodes.get(Parser.indexOfNode(nodes,"WELEV00ML1")));

    }

    public static ArrayList<String> DoDFS(Node Start, Node End){
        ArrayList<String> l= new ArrayList<>(); //ArrayList of Nodes
        ArrayList<Node> o= new ArrayList<>(); //ArrayList of Nodes
        ArrayList<String> a= DFS(Start, End, l, o, 1, Start);
        return a;
    }
    public static ArrayList<String> DFS(Node start, Node end, ArrayList<String> answer, ArrayList<Node> visited, int flag, Node temp) {
        if (flag == 1) { //if first iteration, make answer and visited blank
            answer = new ArrayList<String>();
            visited = new ArrayList<Node>();
            visited.add(start);
            answer.add(start.getNodeInfo().get("longName"));
            flag = 0;
            //System.out.println("start added to visited");

        }
        if (start.getNodeID().equals(end.getNodeID())){
            //System.out.println("answer" + answer);
            return answer;

        }
        int notAllVisited = 0; //checks that all edges are not visited
        //System.out.println(temp);
        // System.out.println("visited " + visited);
        //System.out.println("answer " + answer);
        if(temp.getEdges().size()>0) {
            for (int i = 0; i < temp.getEdges().size(); i++) {  //goes through each edge
                //System.out.println("Going into edge " + temp.getEdgeTo().get(i).getNodeID() + " of node " + temp.getNodeID());
                if (temp.getEdges().get(i).getNodeID().equals(end.getNodeID())) { //checks if edge = to answer, if so adds it to answer and returns
                    answer.add(temp.getEdges().get(i).getNodeInfo().get("longName"));
                    //System.out.println("We Here");
                    //System.out.println("answer" + answer);
                    return answer;
                } else { //if not = answer, check it is not in visited

                    int check = 1;
                    for (int j = 0; j < visited.size(); j++) { //goes through all of the visited list and check if equal to current edge
                        //System.out.println(visited.get(j).getNodeID() + " " + temp.getEdgeTo().get(i).getNodeID());
                        if (visited.get(j).getNodeID().equals(temp.getEdges().get(i).getNodeID())) {
                            check = 0; //if it is in it, sets check to 0
                        } else {
                            //System.out.println("Were are in node " + temp.getNodeID()+ " and edge node "+ temp.getEdgeTo().get(i).getNodeID()+ " is not visited");
                            //for (int s = 0; s < visited.size(); s++) {
                            //   System.out.println("visited " + s + "=" + visited.get(s).getNodeID());
                            // }

                        }


                    }
                    if (check == 1) { //if it was not in visited, adds to visited and answer and sets temp to this edge then goes recursive
                        notAllVisited = 1; //if it is not, not all nodes are visited
                        Node temp2 = temp;
                        temp = temp.getEdges().get(i);
                        temp.setParent(temp2); //sets parent in order to backtrack
                        visited.add(temp);
                        answer.add(temp.getNodeInfo().get("longName"));
                        return DFS(start, end, answer, visited, flag, temp); // runs recursive
                    }
                }

            }
        }
        if (notAllVisited == 0) { //if all nodes visited, removes last node from answer and runs DFS using parent as temp
            //System.out.println("temp " + temp + "edges " + temp.getEdgeTo());
            answer.remove(answer.size()-1);
            return DFS(start, end, answer, visited, flag, temp.getParent());
        }
        return new ArrayList<String>();
    }

}
