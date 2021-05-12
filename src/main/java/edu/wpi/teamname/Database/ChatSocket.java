package edu.wpi.teamname.Database;

import edu.wpi.teamname.views.manager.NavManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.application.Platform;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

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

        if (payloadId.equals("client_command")) {
            payload = payload.getJSONObject("data");
            if (payload.getString("command").equals("navigate")) {
                JSONObject params = payload.getJSONObject("params");
                String start = params.getString("from");
                String end = params.getString("to");
                NavManager.getInstance().triggerNavigationCommand(start, end);
            } else if (payload.getString("command").equals("checkin")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (SceneManager.getInstance().getDefaultPage().isCheckedIn()) {
                            SceneManager.getInstance().getDefaultPage().toggleCheckIn();
                        }
                    }
                });
            }
            return;
        }
    }

    @Override
    public void onMessage(ByteBuffer message) {
    }

    @Override
    public void onError(Exception e) {
    }
}