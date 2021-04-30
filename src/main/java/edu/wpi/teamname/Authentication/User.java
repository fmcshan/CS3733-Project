package edu.wpi.teamname.Authentication;

public class User {
    private String idToken;
    private String name;
    private String email;
    private String localId;
    private String phone;
    private boolean admin;
    private boolean employee;
    private String password;

    public User(String idToken, String email, String name, String localId, String phone, boolean admin, boolean employee) {
        this.idToken = idToken;
        this.email = email;
        this.name = name;
        this.localId = localId;
        this.phone = phone;
        this.admin = admin;
        this.employee = employee;
    }

    public User(String email, String name, String phone, String password) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password; // Only used when creating a new user
    }

    protected String getIdToken() {
        return idToken;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocalId() {
        return localId;
    }

    public String getPhone() { return phone; }

    public String getPassword() { return password; }

    public void clearPassword() { this.password = null; }

    public boolean isAdmin() { return admin; }

    public boolean isEmployee() { return employee; }

    public void setName(String _name) { this.name = _name; }

    public void setEmail(String _email) { this.email = _email; }

    public void setPhone(String _phone) { this.phone = _phone; }


}
