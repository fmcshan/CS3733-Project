package edu.wpi.teamname;
import java.util.ArrayList;
import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Parser;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlgoTests {
    ArrayList<Node> nodes = new ArrayList<Node>();
    Node s1 = nodes.get(Parser.indexOfNode(nodes, ""));
    Node g1 = nodes.get(Parser.indexOfNode(nodes, ""));
    Node s2 = nodes.get(Parser.indexOfNode(nodes, ""));
    Node g2 = nodes.get(Parser.indexOfNode(nodes, ""));
    AStar a = new AStar(nodes, s1, g1);
    ArrayList<Node> path = new ArrayList<>();

    public AlgoTests() {
        path = a.returnPath();
    }

    @Test
    public void pathFindingTest1(){
        ArrayList<Node> testPath = new ArrayList<>();
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"")));
        assertNotSame(testPath, path);
        assertEquals(testPath, path);
    }
}
