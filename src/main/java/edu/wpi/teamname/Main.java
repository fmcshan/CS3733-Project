package edu.wpi.teamname;

import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.*;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
       // AuthenticationManager.getInstance().loginWithEmailAndPassword("admin@admin.com", "password");
        AsynchronousQueue.getInstance().start();
  //   DatabaseThread.getInstance().start();
        App.launch(App.class, args);
    }
}
