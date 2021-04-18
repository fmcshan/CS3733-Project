package edu.wpi.teamname;

import edu.wpi.teamname.database.DatabaseThread;
import edu.wpi.teamname.simplify.Requests;

public class Main {

    public static void main(String[] args) {
        DatabaseThread.getInstance().start();
        App.launch(App.class, args);
    }
}
