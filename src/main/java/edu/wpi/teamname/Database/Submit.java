package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.simplify.Config;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class Submit {
    private static final Submit instance = new Submit();
    String SERVER_URL = Config.getInstance().getServerUrl();

    private Submit() {
    }

    public static synchronized Submit getInstance() {
        return instance;
    }

    public void submitUserRegistration(UserRegistration _form) {
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<UserRegistration> registrations = LocalStorage.getInstance().getRegistrations();
            registrations.add(_form);
            LocalFailover.getInstance().setCheckins(registrations);
            return;
        }
        StringBuilder reasons = new StringBuilder();
        reasons.append("[");
        _form.getReasonsForVisit().forEach(r -> reasons.append("'").append(r).append("', "));
        reasons.setLength(reasons.length() - 2);
        reasons.append("]");

        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("uuid", _form.getUuid());
        data.put("name", _form.getName());
        data.put("submittedAt", String.valueOf(_form.getSubmittedAt()));
        data.put("reasons", reasons.toString());
        data.put("date", _form.getDate());
        data.put("phone", _form.getPhoneNumber());
        data.put("ack", String.valueOf(_form.isAcknowledged()));
        data.put("ackAt", String.valueOf(_form.getAcknowledgedAt()));

        String url = SERVER_URL + "/api/submit-check-in";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void editUserRegistration(UserRegistration _form) {
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<UserRegistration> registrations = LocalStorage.getInstance().getRegistrations();
            for (int i = 0; i < registrations.size(); i++) {
                UserRegistration reg = registrations.get(i);
                if (reg.getUuid().equals(_form.getUuid())) {
                    reg.setCleared(_form.getCleared());
                    reg.setDetails(_form.getDetails());
                    reg.setRating(_form.getRating());
                    registrations.set(i, reg);
                    break;
                }
            }
            LocalFailover.getInstance().setCheckins(registrations);
            return;
        }
        StringBuilder reasons = new StringBuilder();
        reasons.append("[");
        try {
            _form.getReasonsForVisit().forEach(r -> reasons.append("'").append(r).append("', "));
            reasons.setLength(reasons.length() - 2);
        } catch (Exception ignored) {}
        reasons.append("]");

        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("uuid", _form.getUuid());
        data.put("name", _form.getName());
        data.put("reasons", reasons.toString());
        data.put("rating", String.valueOf(_form.getRating()));
        data.put("details", _form.getDetails());
        data.put("cleared", String.valueOf(_form.getCleared()));
        data.put("date", _form.getDate());
        data.put("phone", _form.getPhoneNumber());
        data.put("ack", String.valueOf(_form.isAcknowledged()));
        data.put("ackAt", String.valueOf(_form.getAcknowledgedAt()));

        String url = SERVER_URL + "/api/edit-check-in";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void submitGiftDelivery(MasterServiceRequestStorage _form) {
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<MasterServiceRequestStorage> requests = LocalStorage.getInstance().getMasterStorages();
            _form.setUUID();
            requests.add(_form);
            LocalFailover.getInstance().setRequests(requests);
            return;
        }
        StringBuilder items = new StringBuilder();
        items.append("[");
        if (_form.getRequestedItems().size() > 0) {
            _form.getRequestedItems().forEach(r -> items.append("'").append(r).append("', "));
            items.setLength(items.length() - 2);
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
        data.put("description", _form.getDescription());
        data.put("assignedTo", _form.getAssignTo());
        data.put("completed", String.valueOf(_form.isCompleted()));

        String url = SERVER_URL + "/api/submit-gift-delivery";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void updateGiftDelivery(MasterServiceRequestStorage _form) {
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<MasterServiceRequestStorage> requests = LocalStorage.getInstance().getMasterStorages();
            for (int i = 0; i < requests.size(); i++) {
                MasterServiceRequestStorage req = requests.get(i);
                if (req.getUUID().equals(_form.getUUID())) {
                    req.setAssignTo(_form.getAssignTo());
                    req.setCompleted(_form.isCompleted());
                    break;
                }
            }
            LocalFailover.getInstance().setRequests(requests);
            return;
        }
        StringBuilder items = new StringBuilder();
        items.append("[");
        _form.getRequestedItems().forEach(r -> items.append("'").append(r).append("', "));
        items.setLength(items.length() - 2);
        items.append("]");

        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("id", String.valueOf(_form.getId()));
        data.put("location", _form.getLocation());
        data.put("requestType", _form.getRequestType());
        data.put("requestedItems", items.toString());
        data.put("requestedBy", _form.getRequestedBy());
        data.put("phone", _form.getContact());
        data.put("description", _form.getDescription());
        data.put("assignedTo", _form.getAssignTo());
        data.put("completed", String.valueOf(_form.isCompleted()));

        String url = SERVER_URL + "/api/update-gift-delivery";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void deleteGiftDelivery(MasterServiceRequestStorage _form) {
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<MasterServiceRequestStorage> requests = LocalStorage.getInstance().getMasterStorages();
            for (int i = 0; i < requests.size(); i++) {
                MasterServiceRequestStorage req = requests.get(i);
                if (req.getUUID().equals(_form.getUUID())) {
                    requests.remove(req);
                    break;
                }
            }
            LocalFailover.getInstance().setRequests(requests);
            return;
        }
        JSONObject data = new JSONObject();
        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("id", String.valueOf(_form.getId()));

        String url = SERVER_URL + "/api/delete-gift-delivery";

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

    public void addEdge(Edge _form) {
        modifyEdge(_form, "add");
    }

    public void editEdge(Edge _form) {
        modifyEdge(_form, "edit");
    }

    public void removeEdge(Edge _form) {
        modifyEdge(_form, "remove");
    }

    public void newUser(User _form) {
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<User> users = LocalStorage.getInstance().getUsers();
            _form.setFailoverId(UUID.randomUUID().toString());
            users.add(_form);
            LocalFailover.getInstance().setUsers(users);
            return;
        }
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
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<User> users = LocalStorage.getInstance().getUsers();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getLocalId().equals(_form.getLocalId())) {
                    user.setPhone(_form.getPhone());
                    user.setEmail(_form.getEmail());
                    user.setName(_form.getName());
                    users.set(i, user);
                    break;
                }
            }
            LocalFailover.getInstance().setUsers(users);
            return;
        }
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
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<User> users = LocalStorage.getInstance().getUsers();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getLocalId().equals(_form.getLocalId())) {
                    user.grantAdmin();
                    users.set(i, user);
                    break;
                }
            }
            LocalFailover.getInstance().setUsers(users);
            return;
        }
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("uid", _form.getLocalId());

        String url = SERVER_URL + "/api/grant-admin";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void revokeAdmin(User _form) {
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<User> users = LocalStorage.getInstance().getUsers();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getLocalId().equals(_form.getLocalId())) {
                    user.revokeAdmin();
                    users.set(i, user);
                    break;
                }
            }
            LocalFailover.getInstance().setUsers(users);
            return;
        }
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("uid", _form.getLocalId());

        String url = SERVER_URL + "/api/revoke-admin";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void deleteUser(User _form) {
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<User> users = LocalStorage.getInstance().getUsers();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getLocalId().equals(_form.getLocalId())) {
                    users.remove(i);
                    break;
                }
            }
            LocalFailover.getInstance().setUsers(users);
            return;
        }
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("uid", _form.getLocalId());

        String url = SERVER_URL + "/api/delete-user";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void reserveParking(String toReserve) {
        if (LocalFailover.getInstance().hasFailedOver()) {
            ArrayList<String> spaces = LocalStorage.getInstance().getReservedParkingSpaces();
            spaces.add(toReserve);
            LocalFailover.getInstance().setSpaces(spaces);
            return;
        }
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("nodeId", toReserve);

        String url = SERVER_URL + "/api/reserve-parking";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void removeParking(String toReserve) {
        JSONObject data = new JSONObject();
        String changeId = UUID.randomUUID().toString();
        data.put("CHANGE_ID", changeId);
        data.put("nodeId", toReserve);

        String url = SERVER_URL + "/api/remove-parking";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    public void sendChatMessage(String _id, String _msg) {
        JSONObject data = new JSONObject();
        data.put("CLIENT_ID", _id);
        data.put("message", _msg);

        String url = SERVER_URL + "/api/send-message";

        AsynchronousTask task = new AsynchronousTask(url, data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }
}
