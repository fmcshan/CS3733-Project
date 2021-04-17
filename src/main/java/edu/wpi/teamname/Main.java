package edu.wpi.teamname;

import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Python.Requests;

public class Main {

    public static void main(String[] args) {
        DatabaseThread.getInstance().start();
//        AuthenticationManager.getInstance().loginWithEmailAndPassword("admin@admin.com", "password");
//        String userToken = AuthenticationManager.getInstance().userId();
//        System.out.println(Requests.get("https://soft-eng-3733-rest-api-9l83t.ondigitalocean.app/api/get-nodes").json());
        App.launch(App.class, args);
    }
}
