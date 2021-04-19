package edu.wpi.teamname.Entities.ServiceRequests;

import java.util.List;

public class GiftRequest extends ServiceRequest{
    private List<String> giftRequested;

    /**
     * Creates a new Gift Request
     *
     * @param phoneNumber   Phone number for confirmation
     * @param location      Location for the request
     * @param requesterName Name of the person that requested the service
     */
    public GiftRequest(String phoneNumber, String location, String requesterName) {
        super(phoneNumber, location, requesterName);
    }

    /**
     * Creates a new Gift Requested
     * <p><b>Note: This is an overloaded constructor that allows you to add the gift requested</b></p>
     *
     * @param phoneNumber
     * @param location
     * @param requesterName
     * @param giftRequested
     */
    public GiftRequest(String phoneNumber, String location, String requesterName, List<String> giftRequested) {
        super(phoneNumber, location, requesterName);
        this.giftRequested = giftRequested;
    }

    /**
     * Getter for the gift requested
     * @return the gift requested
     */
    public List<String> getGiftRequested() {
        return giftRequested;
    }
}
