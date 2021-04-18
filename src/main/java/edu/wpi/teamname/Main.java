package edu.wpi.teamname;

import edu.wpi.teamname.Database.AsynchronousQueue;
import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.Socket;
import edu.wpi.teamname.Database.SocketManager;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;

public class Main {

    public static void main(String[] args) {
        SocketManager.getInstance().startDataSocket();
        AsynchronousQueue.getInstance().start();
//        DatabaseThread.getInstance().start();
        App.launch(App.class, args);
    }
}
