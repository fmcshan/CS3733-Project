package edu.wpi.teamname.Algo.Pathfinding;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatBotTest {
    ArrayList<String> nodeLongNames;

    @Before
    void setUp(){
        nodeLongNames = new ArrayList<>();
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        for (Node node : nodes) {
            nodeLongNames.add(node.getLongName());
        }
    }

    @Test
    void chatBotNavigation() {
        this.setUp();
        String search = "inovation hyb";
        int threshold = FuzzySearch.extractOne(search, nodeLongNames).getScore();
        String result = FuzzySearch.extractOne(search, nodeLongNames).getString();
        boolean pass = threshold > 88;
        System.out.println("result " + result);
        System.out.println("threshold " + threshold);
        assertTrue(pass);
    }

    @Test
    void chatBotNavigation2() {
        this.setUp();
        String search = "labey atm innovation hub";
        int threshold = FuzzySearch.extractOne(search, nodeLongNames).getScore();
        String result = FuzzySearch.extractOne(search, nodeLongNames).getString();
        boolean pass = threshold > 88;
        System.out.println("result " + result);
        System.out.println("threshold " + threshold);
        assertTrue(pass);
    }

    @Test
    void chatBotNavigation3() {
        this.setUp();
        String search = "parking spot";
        int threshold = FuzzySearch.extractOne(search, nodeLongNames).getScore();
        String result = FuzzySearch.extractOne(search, nodeLongNames).getString();
        boolean pass = threshold > 88;
        System.out.println("result " + result);
        System.out.println("threshold " + threshold);
        assertTrue(pass);
    }

    @Test
    void chatBotNavigation4() {
        this.setUp();
        String search = "room";
        int threshold = FuzzySearch.extractOne(search, nodeLongNames).getScore();
        String result = FuzzySearch.extractOne(search, nodeLongNames).getString();
        boolean pass = threshold > 88;
        System.out.println("result " + result);
        System.out.println("threshold " + threshold);
        assertTrue(pass);
    }
}