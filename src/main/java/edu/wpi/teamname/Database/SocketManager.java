package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.util.ArrayList;

public class SocketManager {
    private static final SocketManager instance = new SocketManager();
    private ArrayList<Node> nodes = new ArrayList<Node>();

    WebSocketClient nonAuthClient;
    WebSocketClient authClient;

    private boolean dataSocketOpen = false;
    private boolean authDataSocketOpen = false;

    private SocketManager() {

    }

    public static synchronized SocketManager getInstance() {
        return instance;
    }

    public void startDataSocket() {
        if (!dataSocketOpen) {
            try {
                WebSocketClient client = new Socket(new URI("wss://soft-eng-3733-rest-api-9l83t.ondigitalocean.app/ws/pipeline/"));
                this.nonAuthClient = client;
                client.connect();
                dataSocketOpen = true;
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public void stopDataSocket() {
        if (this.nonAuthClient != null) {
            this.nonAuthClient.close();
            this.nonAuthClient = null;
            dataSocketOpen = false;
        }
    }

    public void startAuthDataSocket() {
        if (!authDataSocketOpen) {
            try {
                WebSocketClient client = new AuthSocket(new URI("wss://soft-eng-3733-rest-api-9l83t.ondigitalocean.app/ws/auth-pipeline/"));
                if (AuthenticationManager.getInstance().userId() != null) {
                    client.addHeader("fb-auth", AuthenticationManager.getInstance().userId());
                }
                this.authClient = client;
                client.connect();
                authDataSocketOpen = true;
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public void stopAuthDataSocket() {
        if (this.authClient != null) {
            this.nonAuthClient.close();
            this.authClient = null;
            authDataSocketOpen = false;
        }
    }
}
