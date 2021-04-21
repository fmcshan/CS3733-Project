package edu.wpi.teamname;

import java.io.IOException;

import edu.wpi.teamname.simplify.Shutdown;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class App extends Application {

  private static Stage primaryStage;



  @Override
  public void init() {
    System.out.println("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) {
    App.primaryStage = primaryStage;
    try {
      primaryStage.setOnCloseRequest(e -> {
        Shutdown.getInstance().exit();
      });
      Parent root = FXMLLoader.load(getClass().getResource("views/MapEditorGraph.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
      Shutdown.getInstance().exit();
    }
  }

  public static Stage getPrimaryStage(){
    return primaryStage;
  }


  @Override
  public void stop() {
    System.out.println("Shutting Down");
  }
}
