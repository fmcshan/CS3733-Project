package edu.wpi.teamname.Authentication;

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
            user = new User(
                    payload.getString("idToken"),
                    payload.getString("displayName"),
                    payload.getString("email"),
                    payload.getString("localId")
            );

            for (AuthListener ull : listeners) {
                ull.userLogin();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signOut() {
        user = null;
        SocketManager.getInstance().startAuthDataSocket();
    }

    public String userId() {
        if (this.user == null) {
            return null;
        }

        return user.getIdToken();
    }

}