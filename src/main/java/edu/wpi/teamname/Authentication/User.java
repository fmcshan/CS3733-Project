package edu.wpi.teamname.Authentication;

public class User {
    private final String idToken;
    private final String name;
    private final String email;
    private final String localId;

    public User(String idToken, String email, String name, String localId) {
        this.idToken = idToken;
        this.email = email;
        this.name = name;
        this.localId = localId;
    }

    protected String getIdToken() {
        return idToken;
    }

    protected String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocalId() {
        return localId;
    }
}
