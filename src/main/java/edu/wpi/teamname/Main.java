package edu.wpi.teamname;

import edu.wpi.teamname.Database.DatabaseThread;

public class Main {

    public static void main(String[] args) {
        DatabaseThread.getInstance().start();
        App.launch(App.class, args);
    }
}
