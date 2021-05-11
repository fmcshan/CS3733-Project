package edu.wpi.teamname.views.manager;

public interface Action {

    void execute();

    void undo();

    boolean isNode();

    String getActionName();

    String getLongName();

    StringBuilder checkChangesDo();

    StringBuilder checkChangesUndo();
}