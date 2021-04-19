package edu.wpi.teamname.Entities.ServiceRequests;

/**
 * <h1>Service Request Entity</h1>
 * Entity containing information about a type of Service Request
 * <p><b>Note: This is an abstract class containing the minimum features of any service request</b></p>
 * @author Emmanuel Ola
 */
public abstract class ServiceRequest {
    /**
     * Phone number to send confirmation for the request
     */
    private String phoneNumber;
    /**
     * Location for the request
     */
    private String location;
    /**
     * Name of the person who requested the service
     */
    private String requesterName;

    /**
     * Creates a new Service Request
     * @param phoneNumber Phone number for confirmation
     * @param location Location for the request
     * @param requesterName Name of the person that requested the service
     */
    public ServiceRequest(String phoneNumber, String location, String requesterName) {
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.requesterName = requesterName;
    }

    /**
     * Retrieves the phone number for the confirmation of this request
     * @return the phone number for the confirmation of this request
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Retrieves the location for this request
     * @return the location of the service
     */
    public String getLocation() {
        return location;
    }

    /**
     * Retrievs the name of the person who requested the service
     * @return the name of the person who requested the service
     */
    public String getRequesterName() {
        return requesterName;
    }
}
