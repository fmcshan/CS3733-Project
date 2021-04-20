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
            ArrayList<Node> nodes = Parser.parseNodes(payload);
            ArrayList<Edge> edges = Parser.parseEdges(payload.getJSONArray("edges"));

            LocalStorage.getInstance().setNodes(nodes);
            LocalStorage.getInstance().setEdges(edges);
            return;
        }

        if (payloadId.equals("add_node")) {
            System.out.println("Node added");
            return;
        }

        if (payloadId.equals("edit_node")) {
            System.out.println("Node edited");
            return;
        }

        if (payloadId.equals("remove_node")) {
            System.out.println("Node removed");
            return;
        }

        if (payloadId.equals("add_edge")) {
            System.out.println("Edge added");
            return;
        }

        if (payloadId.equals("edit_edge")) {
            System.out.println("Edge edited");
            return;
        }

        if (payloadId.equals("remove_edge")) {
            System.out.println("Edge removed");
//            return;
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