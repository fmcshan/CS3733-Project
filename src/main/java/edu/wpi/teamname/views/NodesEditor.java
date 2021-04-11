package edu.wpi.teamname.views;

import javafx.beans.property.SimpleStringProperty;

public class NodesEditor {

    private SimpleStringProperty nodeID;
    private int xcoord;
    private int ycoord;
    private SimpleStringProperty floor;
    private SimpleStringProperty age;
    private SimpleStringProperty nodeType;
    private SimpleStringProperty longName;
    private SimpleStringProperty shortName;

    NodesEditor(String nodeID, int xcoord, int ycoord, String floor, String age, String nodeType, String longName, String shortName) {
        this.nodeID = new SimpleStringProperty(nodeID);
        this.xcoord = new int(xcoord);

    }
}
