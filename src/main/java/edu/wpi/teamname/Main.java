package edu.wpi.teamname;

import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.*;
import edu.wpi.teamname.bot.ChatBot;
import edu.wpi.teamname.simplify.Config;
import edu.wpi.teamname.views.manager.LevelManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        System.setProperty("javafx.animation.fullspeed", "true");
        Config.getInstance().setEnv("staging"); // dev staging production

        // ========== IN EVENT OF SERVER FAILURE ==========
//        LocalFailover.getInstance().failOver();
        // ========== IN EVENT OF SERVER FAILURE ==========

        SocketManager.getInstance().startChatSocket();
        SocketManager.getInstance().startDataSocket();
        AuthenticationManager.getInstance().loginWithEmailAndPassword("admin@admin.com", "password");
        AsynchronousQueue.getInstance().start();
  //   DatabaseThread.getInstance().start();

        App.launch(App.class, args);
    }
}