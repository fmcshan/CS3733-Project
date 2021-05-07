package edu.wpi.teamname.views.manager;

public interface Action {

    void execute();

    void undo();

    String getActionName();
}