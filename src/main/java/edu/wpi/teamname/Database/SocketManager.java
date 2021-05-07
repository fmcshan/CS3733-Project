package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.bot.ChatBot;
import org.java_websocket.client.WebSocketClient;

import edu.wpi.teamname.simplify.Config;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SocketManager {
    private static final SocketManager instance = new SocketManager();
    private String SOCKET_URL = Config.getInstance().getSocketUrl();

    WebSocketClient nonAuthClient = null;
    WebSocketClient authClient = null;
    WebSocketClient chatClient = null;
    int failedConnections = 0;

    HashMap<String, Integer> reconnectTime = new HashMap<String, Integer>();

    private SocketManager() {

    }

    private int getReconnectTimeout(String _socket) {
        if (failedConnections == 5) {
            if (AuthenticationManager.getInstance().isAuthenticated()) {
                System.out.println("Possible authentication error, attempting token refresh.");
                AuthenticationManager.getInstance().refreshUser();
            }
        }
        if (failedConnections == 10) {
            System.out.println("Failing over seems probable. Attempting socket connection ~10 more times.");
        }
        if (failedConnections >= 20 && !LocalFailover.getInstance().hasFailedOver()) {
            LocalFailover.getInstance().failOver();
            return -1;
        }
        if (this.reconnectTime.containsKey(_socket)) {
            if (this.reconnectTime.get(_socket) < 10) {
                this.reconnectTime.put(_socket, this.reconnectTime.get(_socket) + 1);
            }
        } else {
            this.reconnectTime.put(_socket, 1);
        }
        failedConnections += 1;
        return this.reconnectTime.get(_socket);
    }

    public void resetReconnectTimeout(String _socket) {
        this.reconnectTime.put(_socket, 0);
        failedConnections = 0;
    }

    public static synchronized SocketManager getInstance() {
        return instance;
    }

    public void startDataSocket() {
        if (this.nonAuthClient == null) {
            try {
                this.nonAuthClient = new Socket(new URI(SOCKET_URL  +"/ws/pipeline/user/"));
                this.nonAuthClient.connect();
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public void stopDataSocket() {
        if (this.nonAuthClient != null) {
            this.nonAuthClient.close();
            this.nonAuthClient = null;
        }
    }

    public void startChatSocket() {
        if (this.chatClient == null) {
            try {
                this.chatClient = new ChatSocket(new URI(SOCKET_URL  +"/ws/chat/" + ChatBot.getInstance().getChatId() + "/"));
                this.chatClient.connect();
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public void stopChatSocket() {
        if (this.chatClient != null) {
            this.chatClient.close();
            this.chatClient = null;
        }
    }

    public void startAuthDataSocket() {
        if (this.authClient == null) {
            try {
                WebSocketClient client = new AuthSocket(new URI(SOCKET_URL + "/ws/pipeline/authenticated/"));
                if (AuthenticationManager.getInstance().isAuthenticated()) {
                    client.addHeader("fb-auth", AuthenticationManager.getInstance().userId());
                }
                this.authClient = client;
                this.authClient.connect();
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public void stopAuthDataSocket() {
        if (this.authClient != null) {
            this.authClient.close();
            this.authClient = null;
        }
    }

    private String getSecondsText(int _seconds) {
        if (_seconds == 1) {
            return " second...";
        } else {
            return " seconds...";
        }
    }

    public void reconnectSocket() {
        if (LocalFailover.getInstance().hasFailedOver()) { return; }
        if (this.nonAuthClient == null || this.nonAuthClient.isClosed() || this.nonAuthClient.isClosing() || !this.nonAuthClient.isOpen()) {
            try {
                int timeout = getReconnectTimeout("user");
                if (timeout == -1) { return; }
                System.out.println("Attempting user socket reconnect in " + timeout + getSecondsText(timeout));
                TimeUnit.SECONDS.sleep(timeout);
                this.nonAuthClient = new Socket(new URI(SOCKET_URL  +"/ws/pipeline/user/"));
                this.nonAuthClient.connect();
            } catch (Exception ignored) {}
        }
    }

    public void reconnectChatSocket() {
        if (LocalFailover.getInstance().hasFailedOver()) { return; }
        if (this.chatClient == null || this.chatClient.isClosed() || this.chatClient.isClosing() || !this.chatClient.isOpen()) {
            try {
                int timeout = getReconnectTimeout("chat");
                if (timeout == -1) { return; }
                System.out.println("Attempting chat socket reconnect in " + timeout + getSecondsText(timeout));
                TimeUnit.SECONDS.sleep(timeout);
                this.chatClient = new ChatSocket(new URI(SOCKET_URL  +"/ws/chat/" + ChatBot.getInstance().getChatId() + "/"));
                this.chatClient.connect();
            } catch (Exception ignored) {}
        }
    }

    public void reconnectAuthSocket() {
        if (LocalFailover.getInstance().hasFailedOver()) { return; }
        if (this.authClient == null || this.authClient.isClosed() || this.authClient.isClosing() || !this.nonAuthClient.isOpen()) {
            try {
                int timeout = getReconnectTimeout("auth");
                if (timeout == -1) { return; }
                System.out.println("Attempting auth socket reconnect in " + timeout + getSecondsText(timeout));
                TimeUnit.SECONDS.sleep(timeout);
                WebSocketClient client = new AuthSocket(new URI(SOCKET_URL + "/ws/pipeline/authenticated/"));
                if (AuthenticationManager.getInstance().isAuthenticated()) {
                    client.addHeader("fb-auth", AuthenticationManager.getInstance().userId());
                } else {return;} // Socket will reject
                this.authClient = client;
                this.authClient.connect();
            } catch (Exception ignored) {}
        }
    }

}
