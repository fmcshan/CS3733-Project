package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Node;

import java.util.ArrayList;

public class DatabaseThread extends Thread {
    private static final DatabaseThread instance = new DatabaseThread();
    private ArrayList<Node> nodes = new ArrayList<Node>();

    private DatabaseThread() {

    }

    public static synchronized DatabaseThread getInstance() {
        return instance;
    }

    public ArrayList<Node> getNodes() {
        try {
            DatabaseThread.getInstance().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return nodes;
    }

    public void run() {
        try {
            nodes = LocalStorage.getInstance().getNodes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}