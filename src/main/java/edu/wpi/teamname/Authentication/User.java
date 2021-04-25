package edu.wpi.teamname.Authentication;

public class User {
    private final String idToken;
    private final String name;
    private final String email;
    private final String localId;
    private final String phone;
    private final boolean admin;
    private final boolean employee;

    public User(String idToken, String email, String name, String localId, String phone, boolean admin, boolean employee) {
        this.idToken = idToken;
        this.email = email;
        this.name = name;
        this.localId = localId;
        this.phone = phone;
        this.admin = admin;
        this.employee = employee;
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

    public String getPhone() { return phone; }

    public boolean isAdmin() { return admin; }

    public boolean isEmployee() { return employee; }
}
