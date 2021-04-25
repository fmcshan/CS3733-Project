package edu.wpi.teamname.Database;

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
        System.out.println("Authenticated socket opened.");
        SocketManager.getInstance().resetReconnectTimeout("auth");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (code != 1000) {
            SocketManager.getInstance().reconnectAuthSocket();
        } else {
            System.out.println("Auth socket closed");
        }
    }

    @Override
    public void onMessage(String message) {
        JSONObject payload = new JSONObject(message);
        String payloadId = payload.getString("event");

        if (payloadId.equals("init")) {
            System.out.println(payload.getJSONArray("giftDeliveries"));
            ArrayList<UserRegistration> registrationsPayload = Parser.parseUserRegistrations(payload.getJSONArray("registrations"));
            LocalStorage.getInstance().setRegistrations(registrationsPayload);

            ArrayList<MasterServiceRequestStorage> giftDeliveries = Parser.parseGiftDeliveryStorages(payload.getJSONArray("giftDeliveries"));
            LocalStorage.getInstance().setGiftDeliveryStorages(giftDeliveries);
            return;
        }

        if (payloadId.equals("submit_check_in")) {
            payload = payload.getJSONObject("data");
            Change change = new Change("submit_check_in");
            change.setUserRegistration(Parser.parseUserRegistration(payload));

            ChangeManager.getInstance().processChange(change);
            return;
        }

        if (payloadId.equals("submit_gift_delivery")) {
            payload = payload.getJSONObject("data");
            Change change = new Change("submit_gift_delivery");
            change.setGiftDelivery(Parser.parseGiftDeliveryStorage(payload));

            ChangeManager.getInstance().processChange(change);
            return;
        }

        if (payloadId.equals("gift_delivery_updated")) {
            payload = payload.getJSONObject("data");
            Change change = new Change("gift_delivery_updated");
            change.setGiftDeliveries(Parser.parseGiftDeliveryStorages(payload.getJSONArray("giftDeliveries")));
            System.out.println(payload.getJSONArray("giftDeliveries"));
            ChangeManager.getInstance().processChange(change);
            return;
        }
    }

    @Override
    public void onMessage(ByteBuffer message) {
    }

    @Override
    public void onError(Exception e) {
        System.out.println("Auth socket error");
    }
}