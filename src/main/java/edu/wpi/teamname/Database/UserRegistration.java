package edu.wpi.teamname.Database;

import java.util.ArrayList;

public class UserRegistration {
    private String name;
    private String date;
    private ArrayList<String> reasonsForVisit;
    private String phoneNumber;

    public UserRegistration(String name, String date, ArrayList<String> reasonsForVisit, String phoneNumber) {
        this.name = name;
        this.date = date;
        this.reasonsForVisit = reasonsForVisit;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String> getReasonsForVisit() {
        return reasonsForVisit;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
