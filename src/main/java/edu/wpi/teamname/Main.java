package edu.wpi.teamname;

import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.AsynchronousQueue;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

public class Main {

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        AsynchronousQueue.getInstance().start();
        App.launch(App.class, args);
    }
}
