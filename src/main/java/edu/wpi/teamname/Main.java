package edu.wpi.teamname;

import edu.wpi.teamname.Database.AsynchronousQueue;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

public class Main {

    public static void main(String[] args) {
        Config.getInstance().setEnv("debug");
        SocketManager.getInstance().startDataSocket();
        AsynchronousQueue.getInstance().start();
//        DatabaseThread.getInstance().start();
        App.launch(App.class, args);
    }
}
