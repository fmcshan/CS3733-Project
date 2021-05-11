package edu.wpi.teamname.Database.socketListeners;

import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.Database.MasterServiceRequestStorage;
import edu.wpi.teamname.Database.UserRegistration;

import java.util.ArrayList;
import java.util.List;

public class Initiator extends Thread {
    private static final Initiator instance = new Initiator();
    private List<RegistrationListener> registrationListeners = new ArrayList<RegistrationListener>();
    private List<GiftDeliveryListener> giftDeliveryListeners = new ArrayList<GiftDeliveryListener>();
    private List<UserListener> userListeners = new ArrayList<UserListener>();

    private Initiator() {

    }

    public static synchronized Initiator getInstance() {
        return instance;
    }

    public void addRegistrationListener(RegistrationListener _toAdd) {
        registrationListeners.add(_toAdd);
    }
    public void addGiftDeliveryListener(GiftDeliveryListener _toAdd) {
        giftDeliveryListeners.add(_toAdd);
    }
    public void addUserListener(UserListener _toAdd) {
        userListeners.add(_toAdd);
    }

    public void triggerRegistration(UserRegistration _obj) {
        for (RegistrationListener l : registrationListeners) {
            try {
                l.registrationAdded(_obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void triggerRegistrationRefresh() {
        for (RegistrationListener l : registrationListeners) {
            try {
                l.registrationRefresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void triggerGiftDelivery(MasterServiceRequestStorage _obj) {
        for (GiftDeliveryListener l : giftDeliveryListeners) {
            try {
                l.giftDeliveryAdded(_obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void triggerGiftDeliveryUpdated() {
        for (GiftDeliveryListener l : giftDeliveryListeners) {
            try {
                l.giftDeliveryUpdated();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void triggerUserRefresh() {
        for (UserListener l : userListeners) {
            try {
                l.refreshUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void triggerUserUpdate(User _user) {
        for (UserListener l : userListeners) {
            try {
                l.updateUser(_user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}