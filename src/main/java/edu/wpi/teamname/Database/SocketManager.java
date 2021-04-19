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

    private SocketManager() {

    }

    public static synchronized SocketManager getInstance() {
        return instance;
    }

    public void startDataSocket() {
        if (this.nonAuthClient == null) {
            try {
                WebSocketClient client = new Socket(new URI("wss://soft-eng-3733-rest-api-9l83t.ondigitalocean.app/ws/pipeline/"));
                this.nonAuthClient = client;
                client.connect();
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public void stopDataSocket() {
        if (this.nonAuthClient != null) {
            this.nonAuthClient.close();
            this.nonAuthClient = null;
        }
    }

    public void startAuthDataSocket() {
        if (this.nonAuthClient == null) {
            try {
                WebSocketClient client = new AuthSocket(new URI("wss://soft-eng-3733-rest-api-9l83t.ondigitalocean.app/ws/auth-pipeline/"));
                if (AuthenticationManager.getInstance().userId() != null) {
                    client.addHeader("fb-auth", AuthenticationManager.getInstance().userId());
                }
                this.authClient = client;
                client.connect();
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public void stopAuthDataSocket() {
        if (this.authClient != null) {
            this.authClient.close();
            this.authClient = null;
        }
    }
}
