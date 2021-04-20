package edu.wpi.teamname.views;

import edu.wpi.teamname.bridge.Bridge;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Controller for Success.fxml
 * @author Lauren Sowerbutts
 */
public class Success extends LoadFXML {

    @FXML
    private UserRegistration userRegistration;

    public Success(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }

    /**
     * load success bar in the Default Page when button is pressed/ make it disappear
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
            openWindow("successBar", root, userRegistration.getSuccessPop()); // open/close success bar
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When close button is pressed close the success page and the form
     */
    public void closeSuccess() {
        userRegistration.getSuccessPop().getChildren().clear(); // clear the successPop vbox
        Bridge.getInstance().close(); // close the window
    }
}
