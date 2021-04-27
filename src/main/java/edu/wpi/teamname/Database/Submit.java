package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.User;
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

    public void submitGiftDelivery(MasterServiceRequestStorage _form) {
        StringBuilder items = new StringBuilder();
        items.append("[");
        if (_form.getRequestedItems().size() > 0) {
            _form.getRequestedItems().forEach(r -> items.append("'").append(r).append("', "));
            items.setLength(items.length()-2);
        }
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

    public void updateGiftDelivery(MasterServiceRequestStorage _form) {
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

        String url = SERVER_URL + "/api/update-gift-delivery";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void modifyNode(Node _form, String modification) {
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("id", _form.getNodeID());
        data.put("building", _form.getBuilding());
        data.put("level", _form.getFloor());
        data.put("longName", _form.getLongName());
        data.put("shortName", _form.getShortName());
        data.put("type", _form.getNodeType());
        data.put("x", String.valueOf(_form.getX()));
        data.put("y", String.valueOf(_form.getY()));

        String url;
        String action;

        switch (modification) {
            case "add":
                url = SERVER_URL + "/api/add-node";
                action = "add_node";
                break;
            case "edit":
                url = SERVER_URL + "/api/edit-node";
                action = "edit_node";
                break;
            case "remove":
                url = SERVER_URL + "/api/remove-node";
                action = "remove_node";
                break;
            default:
                return;
        }

        Change change = new Change(action, changeId);
        change.setModifiedNode(_form);
        ChangeManager.getInstance().processChange(change);

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
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("id", _form.getEdgeID());
        data.put("startNode", _form.getStartNode());
        data.put("endNode", _form.getEndNode());

        String url;
        String action;

        switch (modification) {
            case "add":
                url = SERVER_URL + "/api/add-edge";
                action = "add_edge";
                break;
            case "edit":
                url = SERVER_URL + "/api/edit-edge";
                action = "edit_edge";
                break;
            case "remove":
                url = SERVER_URL + "/api/remove-edge";
                action = "remove_edge";
                break;
            default:
                return;
        }

        Change change = new Change(action, changeId);
        change.setModifiedEdge(_form);
        ChangeManager.getInstance().processChange(change);

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

    public void newUser(User _form) {
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("name", _form.getName());
        data.put("email", _form.getEmail());
        data.put("phone", _form.getPhone());
        data.put("password", _form.getPassword());
        _form.clearPassword();

        String url = SERVER_URL + "/api/create-user";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void editUser(User _form) {
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("uid", _form.getLocalId());
        data.put("name", _form.getName());
        data.put("email", _form.getEmail());
        data.put("phone", _form.getPhone());

        String url = SERVER_URL + "/api/edit-user";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void grantAdmin(User _form) {
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("uid", _form.getLocalId());

        String url = SERVER_URL + "/api/grant-admin";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void revokeAdmin(User _form) {
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("uid", _form.getLocalId());

        String url = SERVER_URL + "/api/revoke-admin";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void deleteUser(User _form) {
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("uid", _form.getLocalId());

        String url = SERVER_URL + "/api/delete-admin";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }
}
