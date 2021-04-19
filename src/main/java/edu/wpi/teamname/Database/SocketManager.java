package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import org.java_websocket.client.WebSocketClient;

import edu.wpi.teamname.simplify.Config;

import java.net.URI;
import java.util.ArrayList;

public class SocketManager {
    private static final SocketManager instance = new SocketManager();
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private String SOCKET_URL = Config.getInstance().getSocketUrl();

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
                System.out.println(SOCKET_URL);
                WebSocketClient client = new Socket(new URI(SOCKET_URL  +"/ws/pipeline/"));
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
                WebSocketClient client = new AuthSocket(new URI(SOCKET_URL + "/ws/auth-pipeline/"));
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
