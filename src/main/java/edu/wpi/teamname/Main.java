package edu.wpi.teamname;

import edu.wpi.teamname.Database.PathFindingDatabaseManager;

public class Main {

  public static void main(String[] args) {
//    App.launch(App.class, args);
    PathFindingDatabaseManager.getInstance().startDb();
  }
}
