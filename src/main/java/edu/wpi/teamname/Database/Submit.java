package edu.wpi.teamname.Database;

import edu.wpi.teamname.simplify.Config;
import org.json.JSONObject;

import java.util.UUID;

public class Submit {
    private static final Submit instance = new Submit();
    private Submit() {}

    public static synchronized Submit getInstance() {
        return instance;
    }

    String SERVER_URL = Config.getInstance().getServerUrl();

    public void UserRegistration(UserRegistration _form) {
        StringBuilder reasons = new StringBuilder();
        reasons.append("[");
        _form.getReasonsForVisit().forEach(r -> reasons.append("'").append(r).append("', "));
        reasons.setLength(reasons.length()-2);
        reasons.append("]");

        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("name", _form.getName());
        data.put("reasons", reasons.toString());
        data.put("date", _form.getDate());
        data.put("phone", _form.getPhoneNumber());
        data.put("ack", String.valueOf(_form.isAcknowledged()));
        data.put("ackAt", String.valueOf(_form.getAcknowledgedAt()));

        String url = SERVER_URL + "/api/submit-check-in";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void GiftDeliveryStorage(GiftDeliveryStorage _form) {
        StringBuilder items = new StringBuilder();
        items.append("[");
        _form.getRequestedItems().forEach(r -> items.append("'").append(r).append("', "));
        items.setLength(items.length()-2);
        items.append("]");

        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("location", _form.getLocation());
        data.put("requestType", _form.getRequestType());
        data.put("requestedItems", items.toString());
        data.put("requestedBy", _form.getRequestedBy());
        data.put("phone", _form.getContact());
        data.put("assignedTo", _form.getAssignTo());
        data.put("completed", _form.isCompleted());

        String url = SERVER_URL + "/api/submit-gift-delivery";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }
}
