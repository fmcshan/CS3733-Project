package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;

import java.io.IOException;


public class Success {

    @FXML
    private UserRegistration userRegistration;

    public Success(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }

    /**
     * load Navigation bar in the Default Page when button is pressed/ make it disappear
     */
    public void loadSuccess() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/Success.fxml"));
        try {
            loader.setControllerFactory(type -> {
                if (type == Success.class) {
                    return this ;
                } else {
                    try {
                        return type.newInstance();
                    } catch (RuntimeException e) {
                        throw e ;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Parent root = loader.load();
            userRegistration.openWindowSuccessPop("successBar", root); // open/close navigation bar

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closeSuccess(ActionEvent actionEvent) {
        userRegistration.getSuccessPop().getChildren().clear();
    }
}
