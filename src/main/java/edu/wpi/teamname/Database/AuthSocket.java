package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.socketListeners.Initiator;
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
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " error: " + reason);
    }

    @Override
    public void onMessage(String message) {
        JSONObject payload = new JSONObject(message);
        String payloadId = payload.getString("event");

        if (payloadId.equals("init")) {
            ArrayList<UserRegistration> registrationsPayload = Parser.parseUserRegistrations(payload.getJSONArray("registrations"));
            LocalStorage.getInstance().setRegistrations(registrationsPayload);

            ArrayList<GiftDeliveryStorage> giftDeliveries = Parser.parseGiftDeliveryStorages(payload.getJSONArray("giftDeliveries"));
            LocalStorage.getInstance().setGiftDeliveryStorages(giftDeliveries);
            return;
        }

        if (payloadId.equals("submit_check_in")) {
            UserRegistration newRegistration = Parser.parseUserRegistration(payload.getJSONObject("data"));
            LocalStorage.getInstance().addRegistration(newRegistration);
            Initiator.getInstance().triggerRegistration(newRegistration);
            return;
        }

        if (payloadId.equals("submit_gift_delivery")) {
            GiftDeliveryStorage newGiftDelivery = Parser.parseGiftDeliveryStorage(payload.getJSONObject("data"));
            LocalStorage.getInstance().addGiftDeliveryStorage(newGiftDelivery);
            Initiator.getInstance().triggerGiftDelivery(newGiftDelivery);
            return;
        }

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