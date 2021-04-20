package edu.wpi.teamname.Database;

import java.time.Instant;
import java.util.ArrayList;

public class UserRegistration {
    private String name;
    private String date;
    private ArrayList<String> reasonsForVisit;
    private String phoneNumber;
    private boolean acknowledged;
    private double acknowledgedAt;
    private double submittedAt;

    public UserRegistration(String name, String date, ArrayList<String> reasonsForVisit, String phoneNumber) {
        this.name = name;
        this.date = date;
        this.reasonsForVisit = reasonsForVisit;
        this.phoneNumber = phoneNumber;
        this.acknowledged = false;
        this.acknowledgedAt = 0.0;
        this.submittedAt = Instant.EPOCH.getEpochSecond();
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

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public double getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public double getSubmittedAt() {
        return submittedAt;
    }
}
