package edu.wpi.teamname.simplify;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.AsynchronousQueue;
import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.SocketManager;
import javafx.application.Platform;

import java.util.ArrayList;

public class Shutdown {
    private static final Shutdown instance = new Shutdown();

    private Shutdown() {

    }

    public static synchronized Shutdown getInstance() {
        return instance;
    }

    public void exit() {
        Platform.exit();
        SocketManager.getInstance().stopDataSocket();
        SocketManager.getInstance().stopAuthDataSocket();
        AsynchronousQueue.getInstance().stopProcessing();
    }

}
