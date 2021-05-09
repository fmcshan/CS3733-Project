package edu.wpi.teamname.Database.socketListeners;

import edu.wpi.teamname.Database.UserRegistration;

public interface RegistrationListener {
    void registrationAdded(UserRegistration _obj);
    void registrationRefresh();
}
