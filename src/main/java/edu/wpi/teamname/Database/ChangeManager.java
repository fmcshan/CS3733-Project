package edu.wpi.teamname.Database;

import edu.wpi.teamname.Database.socketListeners.Initiator;

import java.util.ArrayList;

public class ChangeManager extends Thread {
    private static final ChangeManager instance = new ChangeManager();
    private ArrayList<String> changes = new ArrayList<String>();

    private ChangeManager() {

    }

    public static synchronized ChangeManager getInstance() {
        return instance;
    }

    public void processChange(Change _change) {
        switch (_change.getChangeType()) {
            case "load_nodes":
            case "load_edges":
                if (changes.contains(_change.getChangeId())) { return; }
                LocalStorage.getInstance().setNodes(_change.getNodes());
                LocalStorage.getInstance().setEdges(_change.getEdges());
                LocalStorage.getInstance().linkEdges();
                changes.add(_change.getChangeId());
                break;

            case "submit_check_in":
                LocalStorage.getInstance().addRegistration(_change.getUserRegistration());
                Initiator.getInstance().triggerRegistration(_change.getUserRegistration());
                break;

            case "submit_gift_delivery":
                LocalStorage.getInstance().addGiftDeliveryStorage(_change.getGiftDelivery());
                Initiator.getInstance().triggerGiftDelivery(_change.getGiftDelivery());

            case "gift_delivery_updated":
                LocalStorage.getInstance().setGiftDeliveryStorages(_change.getGiftDeliveries());
                Initiator.getInstance().triggerGiftDeliveryUpdated();
        }
    }
}