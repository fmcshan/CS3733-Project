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

public class Socket extends WebSocketClient {

    public Socket(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public Socket(URI serverURI) {
        super(serverURI);
    }

    public static void main(String[] args) throws URISyntaxException {

    }

    @Override
    public void onOpen(ServerHandshake data) {
        System.out.println("Non authenticated socket opened.");
        SocketManager.getInstance().resetReconnectTimeout("user");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (code != 1000) {
            SocketManager.getInstance().reconnectSocket();
        } else {
            System.out.println("User socket closed");
        }
    }

    @Override
    public void onMessage(String message) {
        JSONObject payload = new JSONObject(message);
        String payloadId = payload.getString("event");

        if (payloadId.equals("init")) {
            ArrayList<Node> nodes = Parser.parseNodes(payload); // Uses nodes and edges
            ArrayList<Edge> edges = Parser.parseEdges(payload.getJSONArray("edges"));
            ArrayList<String> spaces = Parser.parseSpaces(payload.getJSONArray("spaces"));
            LocalStorage.getInstance().setReservedParkingSpaces(spaces);
            LocalStorage.getInstance().setNodes(nodes);
            LocalStorage.getInstance().setEdges(edges);
            return;
        }

        if (payloadId.equals("load_nodes") || payloadId.equals("load_edges")) { // Basically the same thing
            payload = payload.getJSONObject("data");
            Change change = new Change(payloadId, payload.getString("CHANGE_ID"));
            change.setNodes(Parser.parseNodes(payload));
            change.setEdges(Parser.parseEdges(payload.getJSONArray("edges")));

            ChangeManager.getInstance().processChange(change);
            return;
        }

        if(payloadId.equals("reserve_parking")){
            System.out.println(payload);
        }

    }


    @Override
    public void onMessage(ByteBuffer message) {
    }

    @Override
    public void onError(Exception e) {
        System.out.println("User socket error");
    }
}