package edu.wpi.teamname;
import java.util.ArrayList;
import edu.wpi.teamname.Algo.Algorithms.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Parser;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/**
 * <h1>Algorithm Tests</h1>
 * Test suite for the AStar Algorithm
 * @author emmanuelola
 */
public class AlgoTests {
    ArrayList<Node> nodes = PathFindingDatabaseManager.getInstance().getNodes();
    Node s1 = nodes.get(Parser.indexOfNode(nodes, "lPARK022GG"));
    Node g1 = nodes.get(Parser.indexOfNode(nodes, "lEXIT001GG"));
    Node s2 = nodes.get(Parser.indexOfNode(nodes, "lPARK022GG"));
    Node g2 = nodes.get(Parser.indexOfNode(nodes, "lPARK022GG"));
    Node s3 = nodes.get(Parser.indexOfNode(nodes, "lEXIT001GG"));
    Node g3 = nodes.get(Parser.indexOfNode(nodes, "lPARK022GG"));
    ArrayList<Node> path1 = new ArrayList<>();
    ArrayList<Node> path2 = new ArrayList<>();
    ArrayList<Node> path3 = new ArrayList<>();

    public AlgoTests() {

    }

    @BeforeEach
    void setUp() {
        AStar a = new AStar(nodes, s1, g1);
        path1 = a.getPath();
        AStar b = new AStar(nodes, s2, g2);
        path2 = b.getPath();
        AStar c = new AStar(nodes, s3, g3);
        path3 = c.getPath();
    }

    @Test
    public void pathFindingTest1(){
        ArrayList<Node> testPath = new ArrayList<>();
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lPARK022GG")));
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lWALK001GG")));
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lWALK002GG")));
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lWALK003GG")));
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lEXIT001GG")));
        assertNotSame(testPath, path1);
        assertEquals(testPath, path1);
    }

    @Test
    void pathFindingTest2() {
        ArrayList<Node> test = new ArrayList<>();
        test.add(nodes.get(Parser.indexOfNode(nodes, "lPARK022GG")));
        assertNotSame(test, path2);
        assertEquals(test, path2);
    }

    @Test
    void pathFindingTest3() {
        ArrayList<Node> testPath = new ArrayList<>();
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lEXIT001GG")));
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lWALK003GG")));
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lWALK002GG")));
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lWALK001GG")));
        testPath.add(nodes.get(Parser.indexOfNode(nodes,"lPARK022GG")));
        assertNotSame(testPath, path3);
        assertEquals(testPath, path3);
    }
}
