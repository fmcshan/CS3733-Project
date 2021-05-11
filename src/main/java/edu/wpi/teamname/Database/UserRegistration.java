package edu.wpi.teamname.Database;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class UserRegistration {
    private String uuid;
    private String name;
    private String date;
    private ArrayList<String> reasonsForVisit;
    private String phoneNumber;
    private boolean acknowledged;
    private double acknowledgedAt;
    private long submittedAt;
    private boolean cleared;
    private int rating;
    private String details;

    public UserRegistration(String name, String date, ArrayList<String> reasonsForVisit, String phoneNumber) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.date = date;
        this.reasonsForVisit = reasonsForVisit;
        this.phoneNumber = phoneNumber;
        this.acknowledged = false;
        this.acknowledgedAt = 0.0;
        this.submittedAt = System.currentTimeMillis() / 1000L;
        this.cleared = false;
        this.rating = -1;
        this.details = "";
    }

    public UserRegistration(String uuid, String name, String date, ArrayList<String> reasonsForVisit, String phoneNumber) {
        this.uuid = uuid;
        this.name = name;
        this.date = date;
        this.reasonsForVisit = reasonsForVisit;
        this.phoneNumber = phoneNumber;
        this.acknowledged = false;
        this.acknowledgedAt = 0.0;
        this.submittedAt = System.currentTimeMillis() / 1000L;
        this.cleared = false;
        this.rating = -1;
        this.details = "";
    }

    public UserRegistration(String name, String date, ArrayList<String> reasonsForVisit, String phoneNumber, boolean cleared, int rating, String details) {
            this.uuid = UUID.randomUUID().toString();
            this.name = name;
            this.date = date;
            this.reasonsForVisit = reasonsForVisit;
            this.phoneNumber = phoneNumber;
            this.acknowledged = false;
            this.acknowledgedAt = 0.0;
            this.submittedAt = System.currentTimeMillis() / 1000L;
            this.cleared = cleared;
            this.rating = rating;
            this.details = details;
    }

    public UserRegistration(String name, String date, ArrayList<String> reasonsForVisit, String phoneNumber, Boolean acknowledged, double acknowledgedAt, boolean cleared, int rating, String details) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.date = date;
        this.reasonsForVisit = reasonsForVisit;
        this.phoneNumber = phoneNumber;
        this.acknowledged = acknowledged;
        this.acknowledgedAt = acknowledgedAt;
        this.cleared = cleared;
        this.rating = rating;
        this.details = details;
    }

    public UserRegistration(String uuid, String name, String date, long submittedAt, ArrayList<String> reasonsForVisit, String phoneNumber, Boolean acknowledged, double acknowledgedAt, boolean cleared, int rating, String details) {
        this.uuid = uuid;
        this.name = name;
        this.date = date;
        this.submittedAt = submittedAt;
        this.reasonsForVisit = reasonsForVisit;
        this.phoneNumber = phoneNumber;
        this.acknowledged = acknowledged;
        this.acknowledgedAt = acknowledgedAt;
        this.cleared = cleared;
        this.rating = rating;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
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

    public boolean getCleared() {
        return cleared;
    }

    public void setCleared(boolean _cleared) {
        this.cleared = _cleared;
    }

    public int getRating() {
        return rating;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getSubmittedAt() {
        return submittedAt;
    }
}
