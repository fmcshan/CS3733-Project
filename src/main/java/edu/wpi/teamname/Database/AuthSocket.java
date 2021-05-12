package edu.wpi.teamname.Database;

import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.views.manager.Action;
import edu.wpi.teamname.views.manager.Event;
import edu.wpi.teamname.views.manager.Snapshot;
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
            ArrayList<UserRegistration> registrationsPayload = Parser.parseUserRegistrations(payload.getJSONArray("registrations"));
            LocalStorage.getInstance().setRegistrations(registrationsPayload);

            ArrayList<MasterServiceRequestStorage> giftDeliveries = Parser.parseGiftDeliveryStorages(payload.getJSONArray("giftDeliveries"));
            LocalStorage.getInstance().setMasterStorages(giftDeliveries);

            ArrayList<User> users = Parser.parseUsers(payload.getJSONArray("users"));
            LocalStorage.getInstance().setUsers(users);

            ArrayList<Snapshot> snaps = Parser.parseSnapshots(payload.getJSONArray("snapshots"));
            ArrayList<Event> events = Parser.parseEvents(payload.getJSONArray("events"));
            LocalStorage.getInstance().setEvents(events);
            LocalStorage.getInstance().setSnapshots(snaps);

            return;
        }

        if (payloadId.equals("submit_check_in")) {
            payload = payload.getJSONObject("data");
            Change change = new Change("submit_check_in");
            change.setUserRegistration(Parser.parseUserRegistration(payload));

            ChangeManager.getInstance().processChange(change);
            return;
        }

        if (payloadId.equals("check_in_updated")) {
            payload = payload.getJSONObject("data");
            Change change = new Change("check_in_updated");
            change.setUserRegistrations(Parser.parseUserRegistrations(payload.getJSONArray("registrations")));

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
            ChangeManager.getInstance().processChange(change);
            return;
        }

        if (payloadId.equals("reload_employee")) {
            payload = payload.getJSONObject("data");
            Change change = new Change("reload_employee");
            change.setUsers(Parser.parseUsers(payload.getJSONArray("users")));
            ChangeManager.getInstance().processChange(change);
            return;
        }

        if (payloadId.equals("update_employee")) {
            payload = payload.getJSONObject("data");
            Change change = new Change("update_employee");
            change.setUsers(Parser.parseUsers(payload.getJSONArray("users")));
            change.setUser(Parser.parseUser(payload.getJSONObject("user")));
            ChangeManager.getInstance().processChange(change);
            return;
        }

        if (payloadId.equals("update_revisions")) {
            payload = payload.getJSONObject("data");
            ArrayList<Snapshot> snaps = Parser.parseSnapshots(payload.getJSONArray("snapshots"));
            ArrayList<Event> events = Parser.parseEvents(payload.getJSONArray("events"));
            LocalStorage.getInstance().setEvents(events);
            LocalStorage.getInstance().setSnapshots(snaps);
            Initiator.getInstance().triggerRevisionRefresh();
        }
    }

    @Override
    public void onMessage(ByteBuffer message) {
    }

    @Override
    public void onError(Exception e) {
    }
}