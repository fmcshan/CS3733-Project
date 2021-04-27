package edu.wpi.teamname.Database.socketListeners;

import edu.wpi.teamname.Authentication.User;

public interface UserListener {
    void refreshUsers();
    void updateUser(User _user);
}
