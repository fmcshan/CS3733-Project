package edu.wpi.teamname;

import edu.wpi.teamname.Database.AsynchronousQueue;
import edu.wpi.teamname.Database.AsynchronousTask;
import edu.wpi.teamname.Database.DatabaseThread;

public class Main {

    public static void main(String[] args) {
        DatabaseThread.getInstance().start();
        AsynchronousQueue.getInstance().start();

        App.launch(App.class, args);
    }
}
