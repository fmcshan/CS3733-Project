package edu.wpi.teamname.Database;

import java.util.ArrayList;

public class GiftDeliveryStorage {
    private String requestType;
    private String location;
    private ArrayList<String> requestedItems;
    private String requestedBy;
    private String contact;
    private String assignTo;
    private boolean completed;
    private int id = 0;


    public GiftDeliveryStorage(int id, String requestType, String location, ArrayList<String> requestedItems, String requestedBy, String contact, String assignTo, boolean completed) {
        this.requestType = requestType;
        this.location = location;
        this.requestedItems = requestedItems;
        this.requestedBy = requestedBy;
        this.contact = contact;
        this.assignTo = assignTo;
        this.id = id;
        this.completed = completed;
    }

    public GiftDeliveryStorage(String requestType, String location, ArrayList<String> requestedItems, String requestedBy, String contact, String assignTo, boolean completed) {
        this.requestType = requestType;
        this.location = location;
        this.requestedItems = requestedItems;
        this.requestedBy = requestedBy;
        this.contact = contact;
        this.assignTo = assignTo;
        this.completed = completed;
    }


    public String getRequestType() {
        return requestType;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getRequestedItems() {
        return requestedItems;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public String getContact() {
        return contact;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public int getId() {
        return id;
    }

    public boolean isCompleted() {
       return completed;
    }
}

