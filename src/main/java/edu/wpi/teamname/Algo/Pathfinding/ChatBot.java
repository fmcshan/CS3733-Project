package edu.wpi.teamname.Algo.Pathfinding;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.views.AutoCompleteComboBoxListener;
import edu.wpi.teamname.views.LoadFXML;
import edu.wpi.teamname.views.manager.LevelManager;
import edu.wpi.teamname.views.manager.SceneManager;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.ArrayList;

public class ChatBot {
    ArrayList<Node> nodes;
    ArrayList<String> nodeLongNames;
    String startNode;
    String endNode;
    public boolean navActivated = false;

    public ChatBot() {
        this.nodes = LocalStorage.getInstance().getNodes();
        this.nodeLongNames = new ArrayList<>();
        for (Node node : nodes) {
            nodeLongNames.add(node.getLongName());
        }
    }

    public void chatBotNavigation(String start, String end){
        if (FuzzySearch.extractOne(start, nodeLongNames).getScore() > 88)
            startNode = FuzzySearch.extractOne(start, nodeLongNames).getString();
        else {//send error message to chatbot
             }
        if (FuzzySearch.extractOne(end, nodeLongNames).getScore() > 88)
            endNode = FuzzySearch.extractOne(start, nodeLongNames).getString();
        else {//send error message to chatbot
             }

        if(!LoadFXML.getCurrentWindow().equals("navBar"))
            SceneManager.getInstance().getDefaultPage().toggleNav();

        navActivated = true;
    }

    public String getStartNode(){
        return startNode;
    }

    public String getEndNode(){
        return endNode;
    }

    public static void main(String[] args) {
    }
}
