package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
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

    public void submitUserRegistration(UserRegistration _form) {
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

    public void submitGiftDelivery(GiftDeliveryStorage _form) {
        StringBuilder items = new StringBuilder();
        items.append("[");
        _form.getRequestedItems().forEach(r -> items.append("'").append(r).append("', "));
        items.setLength(items.length()-2);
        items.append("]");

        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("id", String.valueOf(_form.getId()));
        data.put("location", _form.getLocation());
        data.put("requestType", _form.getRequestType());
        data.put("requestedItems", items.toString());
        data.put("requestedBy", _form.getRequestedBy());
        data.put("phone", _form.getContact());
        data.put("assignedTo", _form.getAssignTo());
        data.put("completed", String.valueOf(_form.isCompleted()));

        String url = SERVER_URL + "/api/submit-gift-delivery";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void updateGiftDelivery(GiftDeliveryStorage _form) {
        StringBuilder items = new StringBuilder();
        items.append("[");
        _form.getRequestedItems().forEach(r -> items.append("'").append(r).append("', "));
        items.setLength(items.length()-2);
        items.append("]");

        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("id", String.valueOf(_form.getId()));
        data.put("location", _form.getLocation());
        data.put("requestType", _form.getRequestType());
        data.put("requestedItems", items.toString());
        data.put("requestedBy", _form.getRequestedBy());
        data.put("phone", _form.getContact());
        data.put("assignedTo", _form.getAssignTo());
        data.put("completed", String.valueOf(_form.isCompleted()));
        System.out.println(_form.isCompleted());
        System.out.println(String.valueOf(_form.isCompleted()));

        String url = SERVER_URL + "/api/update-gift-delivery";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void modifyNode(Node _form, String modification) {
        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("id", _form.getNodeID());
        data.put("building", _form.getBuilding());
        data.put("level", _form.getFloor());
        data.put("longName", _form.getLongName());
        data.put("shortName", _form.getShortName());
        data.put("type", _form.getNodeType());
        data.put("x", String.valueOf(_form.getX()));
        data.put("y", String.valueOf(_form.getY()));

        String url;

        switch (modification) {
            case "add":
                url = SERVER_URL + "/api/add-node";
                break;
            case "edit":
                url = SERVER_URL + "/api/edit-node";
                break;
            case "remove":
                url = SERVER_URL + "/api/remove-node";
                break;
            default:
                return;
        }

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void addNode(Node _form) {
        modifyNode(_form, "add");
    }

    public void editNode(Node _form) {
        modifyNode(_form, "edit");
    }

    public void removeNode(Node _form) {
        modifyNode(_form, "remove");
    }

    public void modifyEdge(Edge _form, String modification) {
        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("id", _form.getEdgeID());
        data.put("startNode", _form.getStartNode());
        data.put("endNode", _form.getEndNode());

        String url;

        switch (modification) {
            case "add":
                url = SERVER_URL + "/api/add-edge";
                break;
            case "edit":
                url = SERVER_URL + "/api/edit-edge";
                break;
            case "remove":
                url = SERVER_URL + "/api/remove-edge";
                break;
            default:
                return;
        }

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void addEdge(Edge _form ) {
        modifyEdge(_form, "add");
    }

    public void editEdge(Edge _form) {
        modifyEdge(_form, "edit");
    }

    public void removeEdge(Edge _form) {
        modifyEdge(_form, "remove");
    }
}
