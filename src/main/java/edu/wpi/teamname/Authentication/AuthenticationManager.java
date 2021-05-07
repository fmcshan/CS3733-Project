package edu.wpi.teamname.Authentication;

import com.google.api.client.json.Json;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Requests;
import edu.wpi.teamname.simplify.Response;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {
    private static final AuthenticationManager instance = new AuthenticationManager();
    public User user = null;
    private List<AuthListener> listeners = new ArrayList<AuthListener>();

    private AuthenticationManager() {
    }

    public static synchronized AuthenticationManager getInstance() {
        return instance;
    }

    public Boolean isAuthenticated() {
        return (user != null);
    }

    public Boolean isAdmin() {
        return user.isAdmin();
    }

    public Boolean isEmployee() {
        return user.isEmployee();
    }

    public void addListener(AuthListener _add) {
        listeners.add(_add);
    }

    public void loginWithEmailAndPassword(String _email, String _password) {
        try {
            JSONObject data = new JSONObject();
            data.put("email", _email);
            data.put("password", _password);
            data.put("returnSecureToken", "true");

            // Authentication endpoint
            Response res = Requests.post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyDmVqldcnj6B21Ah339Zj_aJgC7p5Jq1zE", data);

            JSONObject payload = res.json();

            java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
            String[] split_string = payload.getString("idToken").split("\\.");
            String base64EncodedBody = split_string[1];

            JSONObject body = new JSONObject(new String(decoder.decode(base64EncodedBody)));
            boolean isAdmin;
            if (body.has("admin")) {
                isAdmin = body.getBoolean("admin");
            } else { isAdmin = false; }

            boolean isEmployee;
            if (body.has("employee")) {
                isEmployee = body.getBoolean("employee");
            } else { isEmployee = false; }

            user = new User(
                    payload.getString("idToken"),
                    payload.getString("refreshToken"),
                    payload.getString("displayName"),
                    payload.getString("email"),
                    payload.getString("localId"),
                    null,
                    isAdmin,
                    isEmployee
            );

            for (AuthListener ull : listeners) {
                try {
                    ull.userLogin();
                } catch (Exception e) {e.printStackTrace();}
            }

            if (isAdmin || isEmployee) {
                SocketManager.getInstance().startAuthDataSocket();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signOut() {
        user = null;
        for (AuthListener ull : listeners) {
            ull.userLogout();
        }
        SocketManager.getInstance().stopAuthDataSocket();
    }

    public String userId() {
        if (this.user == null) {
            return null;
        }

        return user.getIdToken();
    }

    public void refreshUser() {
        JSONObject data = new JSONObject();
        data.put("grant_type", "refresh_token");
        data.put("refresh_token", user.getRefreshToken());

        // Refresh endpoint
        Response res = Requests.post("https://securetoken.googleapis.com/v1/token?key=AIzaSyDmVqldcnj6B21Ah339Zj_aJgC7p5Jq1zE", data);
        JSONObject payload = res.json();
        user.refresh(payload.getString("id_token"), payload.getString("refresh_token"));
    }
}