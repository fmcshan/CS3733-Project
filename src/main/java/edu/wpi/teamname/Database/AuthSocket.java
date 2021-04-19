package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class AuthSocket extends WebSocketClient {

    public AuthSocket(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public AuthSocket(URI serverURI) {
        super(serverURI);
    }

    public static void main(String[] args) throws URISyntaxException {

    }

    @Override
    public void onOpen(ServerHandshake data) {
        System.out.println("Non authenticated socket opened.");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " error: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
        JSONObject payload = new JSONObject(message);
        String payloadId = payload.getString("event");

//        if (payloadId.equals("init")) {
//            ArrayList<Node> nodes = Parser.parseNodes(payload);
//            ArrayList<Edge> edges = Parser.parseEdges(payload.getJSONArray("edges"));
//
//            LocalStorage.getInstance().setNodes(nodes);
//            LocalStorage.getInstance().setEdges(edges);
//            return;
//        }

//        if (payloadId.equals("add_node")) {
//            System.out.println("Node added");
//            return;
//        }

    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception e) {
        System.err.println("error:" + e);
    }
}