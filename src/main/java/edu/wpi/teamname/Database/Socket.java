package edu.wpi.teamname.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

public class Socket extends WebSocketClient {

    public Socket(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public Socket(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake data) {
        System.out.println("Tony has joined the server.");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " debug: " + reason);
    }

    @Override
    public void onMessage(String message) {
        JSONObject payload = new JSONObject(message);
        System.out.println(message);
//        if (payload.getString("event").equals("init")) {
//            Parser.parseNode(payload.getJSONArray("nodes").getJSONObject(0));
//        }
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception e) {
        System.err.println("an error occurred:" + e);
    }

    public static void main(String[] args) throws URISyntaxException {
        WebSocketClient client = new Socket(new URI("ws://localhost:8000/ws/pipeline/"));
        client.connect();
        System.out.println("Done!");
    }
}