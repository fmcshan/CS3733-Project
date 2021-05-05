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

    HashMap<String, Integer> reconnectTime = new HashMap<String, Integer>();

    private SocketManager() {

    }

    private int getReconnectTimeout(String _socket) {
        if (this.reconnectTime.containsKey(_socket)) {
            if (this.reconnectTime.get(_socket) < 20) {
                this.reconnectTime.put(_socket, this.reconnectTime.get(_socket) + 1);
            }
        } else {
            this.reconnectTime.put(_socket, 1);
        }
        return this.reconnectTime.get(_socket);
    }

    public void resetReconnectTimeout(String _socket) {
        this.reconnectTime.put(_socket, 0);
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

    public void reconnectSocket() {
        if (this.nonAuthClient == null || this.nonAuthClient.isClosed() || this.nonAuthClient.isClosing() || !this.nonAuthClient.isOpen()) {
            try {
                int timeout = getReconnectTimeout("user");
                System.out.println("Attempting user socket reconnect in " + timeout + " seconds...");
                TimeUnit.SECONDS.sleep(timeout);
                this.nonAuthClient = new Socket(new URI(SOCKET_URL  +"/ws/pipeline/user/"));
                this.nonAuthClient.connect();
            } catch (Exception ignored) {}
        }
    }

    public void reconnectChatSocket() {
        if (this.chatClient == null || this.chatClient.isClosed() || this.chatClient.isClosing() || !this.chatClient.isOpen()) {
            try {
                int timeout = getReconnectTimeout("chat");
                System.out.println("Attempting chat socket reconnect in " + timeout + " seconds...");
                TimeUnit.SECONDS.sleep(timeout);
                this.chatClient = new Socket(new URI(SOCKET_URL  +"/ws/chat/" + ChatBot.getInstance().getChatId() + "/"));
                this.chatClient.connect();
            } catch (Exception ignored) {}
        }
    }

    public void reconnectAuthSocket() {
        if (this.authClient == null || this.authClient.isClosed() || this.authClient.isClosing() || !this.nonAuthClient.isOpen()) {
            try {
                int timeout = getReconnectTimeout("auth");
                System.out.println("Attempting auth socket reconnect in " + timeout + " seconds...");
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
