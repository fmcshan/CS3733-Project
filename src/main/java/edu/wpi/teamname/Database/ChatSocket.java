package edu.wpi.teamname.Database;

import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.views.manager.SceneManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ChatSocket extends WebSocketClient {

    public ChatSocket(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public ChatSocket(URI serverURI) {
        super(serverURI);
    }

    public static void main(String[] args) throws URISyntaxException {

    }

    @Override
    public void onOpen(ServerHandshake data) {
        System.out.println("Chat socket opened.");
        SocketManager.getInstance().resetReconnectTimeout("chat");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (code != 1000) {
            SocketManager.getInstance().reconnectChatSocket();
        } else {
            System.out.println("Chat socket closed");
        }
    }

    @Override
    public void onMessage(String message) {
        JSONObject payload = new JSONObject(message);
        String payloadId = payload.getString("event");

        if (payloadId.equals("receive_message")) {
            payload = payload.getJSONObject("data");
            SceneManager.getInstance().getDefaultPage().receiveMessage(payload.getString("message"));
            return;
        }


    }

    @Override
    public void onMessage(ByteBuffer message) {
    }

    @Override
    public void onError(Exception e) {
        System.out.println("Chat socket error");
    }
}