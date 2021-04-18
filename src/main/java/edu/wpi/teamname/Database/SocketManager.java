package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Node;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.util.ArrayList;

public class SocketManager {
    private static final SocketManager instance = new SocketManager();
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private boolean dataSocketOpen = false;
    private boolean authDataSocketOpen;

    private SocketManager() {

    }

    public static synchronized SocketManager getInstance() {
        return instance;
    }

    public void startDataSocket() {
        if (!dataSocketOpen) {
            try {
                WebSocketClient client = new Socket(new URI("ws://localhost:8000/ws/pipeline/"));
                client.connect();
                dataSocketOpen = true;
            } catch (Exception e) {e.printStackTrace();}
        }
    }
}
