package edu.wpi.teamname.Database.socketListeners;

import edu.wpi.teamname.Database.MasterServiceRequestStorage;

public interface GiftDeliveryListener {
    void giftDeliveryAdded(MasterServiceRequestStorage _obj);
    void giftDeliveryUpdated();
}
