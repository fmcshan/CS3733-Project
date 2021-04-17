package edu.wpi.teamname.Authentication;

import edu.wpi.teamname.Python.Requests;
import edu.wpi.teamname.Python.Response;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {
    private static final AuthenticationManager instance = new AuthenticationManager();
    public User user = null;

    private AuthenticationManager() {
    }

    public static synchronized AuthenticationManager getInstance() {
        return instance;
    }

    public Boolean isAuthenticated() {
        return (user != null);
    }

    public void loginWithEmailAndPassword(String _email, String _password) {
        JSONObject data = new JSONObject();
        data.put("email", _email);
        data.put("password", _password);
        data.put("returnSecureToken", "true");

        Response res = Requests.post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyDmVqldcnj6B21Ah339Zj_aJgC7p5Jq1zE", data);

        JSONObject payload = res.json();
        user = new User(
                payload.getString("idToken"),
                payload.getString("displayName"),
                payload.getString("email"),
                payload.getString("localId")
        );
    }

    public void signOut() {
        user = null;
    }

    public String userId() {
        if (this.user == null) {
            return null;
        }

        return user.getIdToken();
    }

}